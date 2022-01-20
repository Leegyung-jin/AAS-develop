package kr.co.hulan.aas.mvc.api.sensorTrace.service;

import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.ExportExcelHelper;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ExportSensorTraceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ListSensorTraceRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.NotifySensorTraceAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.response.ListSensorTraceResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogTraceDao;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SensorTraceService {

    private Logger logger = LoggerFactory.getLogger(SensorTraceService.class);

    @Autowired
    private SensorLogTraceDao sensorLogTraceDao;

    @Autowired
    private PushService pushService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public ListSensorTraceResponse findListPage(ListSensorTraceRequest request) {
        return new ListSensorTraceResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<SensorLogTraceDto> findListByCondition(Map<String,Object> conditionMap) {
        return sensorLogTraceDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return sensorLogTraceDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    /*
    public void downloadExcel(HttpServletResponse response, ExportSensorTraceDataRequest request){

        String excelTitle = "위치세부기록_"+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String[] dataHeaders = new String[]{
                "시간","IN","OUT","건설사","공사명","협력사","아이디","성명","유형","구역","위치1","위치2"
        };
        ExportExcelHelper helper = new ExportExcelHelper(excelTitle, dataHeaders);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            helper.initialize();

            sqlSession.select("kr.co.hulan.aas.mvc.dao.mapper.SensorLogTraceDao.findListByCondition", AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()), new ResultHandler<SensorLogTraceDto>(){
                @Autowired
                public void handleResult(ResultContext<? extends SensorLogTraceDto> context){
                    SensorLogTraceDto logTraceDto = context.getResultObject();
                    helper.addReport(logTraceDto.toArray());
                }
            });

            helper.flushData(response);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if( sqlSession != null ){
                try{ sqlSession.close(); }catch(Exception e){}
            }
            if( helper != null ){
                try{ helper.close(); }catch(Exception e){}
            }
        }
    }

*/

    public void downloadExcel(HttpServletResponse response, ExportSensorTraceDataRequest request){

        String excelTitle = "위치세부기록_"+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String[] dataHeaders = new String[]{
            "시간","IN","OUT","건설사","공사명","협력사","아이디","성명","유형","구역","위치1","위치2"
        };
        ExportExcelHelper helper = new ExportExcelHelper(excelTitle, dataHeaders);
        try{
            helper.initialize();

            int startRowIndex = 0;

            long totalDataCount = findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()));
            if (totalDataCount > 0) {
                Map<String, Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
                condition.put("pageSize", helper.getFetchSize());
                while(startRowIndex <= totalDataCount){
                    condition.put("startRow", startRowIndex);
                    List<SensorLogTraceDto> dataList = findListByCondition(condition);
                    if (dataList.size() == 0 || dataList.isEmpty()) {
                        break;
                    }
                    else {
                        List<Object[]> convertedList = dataList.stream().map(traceDto -> traceDto.toArray()).collect(Collectors.toList());
                        helper.addReport(convertedList);
                    }
                    startRowIndex = startRowIndex + helper.getFetchSize();
                }
            }
            helper.flushData(response);
        }
        finally{
            helper.close();
        }
    }



    public List<SensorLogTraceDto> findByIdList(List<Integer> sltIdxs) {
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("sltIdxs", sltIdxs);
        return sensorLogTraceDao.findByIdList(conditionMap);
    }

    public void sendAlarm(NotifySensorTraceAlarmRequest request){
        List<SensorLogTraceDto> SensorLogTraceList = findByIdList(request.getSltIdxs());
        PushData push = new PushData();
        push.setSubject( request.getPuSubject());
        push.setBody( request.getPuContent());
        push.setCode( request.getPuChk() == 1 ? "warn" : "notice");
        for( SensorLogTraceDto logDto : SensorLogTraceList ){
            if( logDto != null && logDto.isAllowedPushDanger() && StringUtils.isNotBlank(logDto.getDeviceId())){
                push.addTargetInfo(logDto.getMbId(), logDto.getWpId() ,logDto.getDeviceId(), logDto.getAppVersion());
            }
        }

        if( push.targetInfoSize() > 0 ){
            pushService.sendPush(push);
        }
    }
}
