package kr.co.hulan.aas.mvc.api.sensorTrace.service;

import kr.co.hulan.aas.common.code.SensorType;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.ExportExcelHelper;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ExportDangerZoneSensorTraceDataRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.ListDangerZoneSensorTraceRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.request.NotifySensorTraceAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorTrace.controller.response.ListDangerZoneSensorTraceResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogTraceDao;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DangerZoneSensorTraceService {

    @Autowired
    private SensorLogTraceDao sensorLogTraceDao;

    @Autowired
    private PushService pushService;

    public ListDangerZoneSensorTraceResponse findListPage(ListDangerZoneSensorTraceRequest request) {
        return new ListDangerZoneSensorTraceResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorLogTraceDto> findListByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("siType", SensorType.DANGER_ZONE.getName());
        return sensorLogTraceDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("siType", SensorType.DANGER_ZONE.getName());
        return sensorLogTraceDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public void downloadExcel(HttpServletResponse response, ExportDangerZoneSensorTraceDataRequest request){

        String excelTitle = "위험지역기록"+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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

    /*
    public void downloadExcel(HttpServletResponse response, ExportDangerZoneSensorTraceDataRequest request){
        String excelTitle = "위험지역기록"+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String[] dataHeaders = new String[]{
                "시간","IN","OUT","건설사","공사명","협력사","아이디","성명","유형","구역","위치1","위치2"
        };

        PrintWriter outputStream = null;
        try{
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(excelTitle, "UTF-8") + ".csv");
            response.setHeader("Set-Cookie" , "fileDownload=true; path=/");
            response.setContentType("text/csv; charset=UTF-8");

            String delimeter = ", ";
            outputStream = new PrintWriter(response.getOutputStream());

            outputStream.println(String.join(delimeter, dataHeaders));
            outputStream.flush();

            int startRowIndex = 0;
            int fetchSize = 10000;

            long totalDataCount = findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()));
            if (totalDataCount > 0) {
                Map<String, Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap());
                condition.put("pageSize", fetchSize);
                while(startRowIndex <= totalDataCount){
                    condition.put("startRow", startRowIndex);
                    List<SensorLogTraceDto> dataList = findListByCondition(condition);
                    if (dataList.size() == 0 || dataList.isEmpty()) {
                        break;
                    }
                    else {
                        for(SensorLogTraceDto dto : dataList){
                            outputStream.println(String.join(delimeter, dto.toArray()));
                        }
                        outputStream.flush();
                    }
                    startRowIndex = startRowIndex + fetchSize;
                }
            }
            outputStream.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if( outputStream != null ){
                try{
                    outputStream.close();
                }
                catch(Exception e){}
            }
        }
    }
     */

    public List<SensorLogTraceDto> findByIdList(List<Integer> sltIdxs) {
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("sltIdxs", sltIdxs);
        return sensorLogTraceDao.findByIdList(conditionMap);
    }

    public void sendAlarm(NotifySensorTraceAlarmRequest request){
        List<SensorLogTraceDto> sensorLogList = findByIdList(request.getSltIdxs());
        PushData push = new PushData();
        push.setSubject( request.getPuSubject());
        push.setBody( request.getPuContent());
        push.setCode( request.getPuChk() == 1 ? "warn" : "notice");
        for( SensorLogTraceDto logDto : sensorLogList ){
            if( logDto != null && logDto.isAllowedPushDanger() && StringUtils.isNotBlank(logDto.getDeviceId())){
                push.addTargetInfo(logDto.getMbId(), logDto.getWpId() ,logDto.getDeviceId(), logDto.getAppVersion());
            }
        }

        if( push.targetInfoSize() > 0 ){
            pushService.sendPush(push);
        }

    }
}
