package kr.co.hulan.aas.mvc.api.monitor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MeasureEnvironmentType;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.config.properties.HttpClientProperties;
import kr.co.hulan.aas.config.properties.WorkplaceProperties;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.accident.service.FallingAccidentService;
import kr.co.hulan.aas.mvc.api.board.service.NoticeBoardService;
import kr.co.hulan.aas.mvc.api.component.service.UiComponentService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AnalyticInfoMgrRequest;
import kr.co.hulan.aas.mvc.api.bls.service.BlsMonitoringService;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.FieldManagerService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AssignWorkplaceUiComponentRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.CommonMonitoringRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.UpdateGasDevicePopupRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleCrossSectionDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleFloorDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleViewMapDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceExternalLink;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureDeviceVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.GpsObjectVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.WorkplaceSummaryDto;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkPlaceUiComponentService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;

import kr.co.hulan.aas.mvc.dao.mapper.BlsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.GasLogDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsLocationDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.ImageAnalyticInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogInoutDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkerCheckDao;
import kr.co.hulan.aas.mvc.dao.repository.GasLogRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.ImageAnalyticInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.GasLogRecent;
import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfo;
import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfoKey;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import kr.co.hulan.aas.mvc.model.dto.ImageAnalyticInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import kr.co.hulan.aas.mvc.service.weather.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonMonitoringService {

    private Logger logger = LoggerFactory.getLogger(CommonMonitoringService.class);

    @Autowired
    HttpClientProperties httpClientProperties;

    @Autowired
    private CloseableHttpClient client;

    @Autowired
    private WorkplaceProperties workplaceProperties;

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    FieldManagerService fieldManagerService;

    @Autowired
    BlsMonitoringService blsMonitoringService;

    @Autowired
    SensorLogInoutDao sensorLogInoutDao;

    @Autowired
    WorkerCheckDao workerCheckDao;

    @Autowired
    WeatherService weatherService;

    @Autowired
    GasLogDao gasLogDao;

    @Autowired
    GasLogRecentRepository gasLogRecentRepository;

    @Autowired
    FallingAccidentService fallingAccidentService;

    @Autowired
    BlsMonitoringDao blsMonitoringDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ImageAnalyticInfoRepository imageAnalyticInfoRepository;

    @Autowired
    ImageAnalyticInfoDao imageAnalyticInfoDao;

    public Map searchCommonMonitor(CommonMonitoringRequest request) {

        WorkPlaceDto dto = workplaceService.findById(request.getWpId());
        if( dto == null ){
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "존재하지 않는 현장입니다.");
        }
        FieldManagerDto manager = fieldManagerService.findFieldManagerByMbId(dto.getManMbId());
        if( manager != null ){
            request.setMbId(StringUtils.replace(manager.getTelephone(), "-", ""));
        }

        Map<String,Object> resultMap = null;
        HttpResponse response = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            HttpPost postRequest = new HttpPost(httpClientProperties.getCommonMonitorRequestUrl()); //POST 메소드 URL 새성

            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-Type", "application/json;charset=UTF-8");

            postRequest.setEntity(new StringEntity(mapper.writeValueAsString(request), Consts.UTF_8)); //json 메시지 입력

            response = client.execute(postRequest);
            if( response != null && response.getStatusLine() != null ){
                String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                resultMap = JsonUtil.toStringMap(result);
                return resultMap;
            }
        } catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INTERNAL SERVER ERROR OCCURED", e);
        } finally{
            HttpClientUtils.closeQuietly(response);
        }

        throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "GPS 정보를 가져올 수 없습니다.");
    }

    public Map<String,Object> getCommonMonitoringInfo(WorkPlaceDto dto){

        String wpId = dto.getWpId();

        Map<String,Object> resultMap = new HashMap<String,Object>();

        Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
        conditionByUser.put("wpId", wpId);

        resultMap.put("sl_total", sensorLogInoutDao.getWorkCount(conditionByUser));

        List<Map<String, Object>> worker_check = workerCheckDao.selectWorkCheckSensorTrace(wpId);
        resultMap.put("worker_check", worker_check != null && worker_check.size() > 0 ? worker_check : Collections
            .emptyList());

        List<Map<String, Object>> worker_danger_area = workerCheckDao.selectDangerAreaWorkerSensorTrace(wpId);
        resultMap.put("worker_danger_area", worker_danger_area != null && worker_danger_area.size() > 0 ? worker_danger_area : Collections.emptyList());

        String memo = workplaceProperties.getMemo(wpId);
        resultMap.put("memo", StringUtils.defaultIfEmpty(memo, ""));

        List<WorkplaceExternalLink> linkInfo = new ArrayList<WorkplaceExternalLink>();
        if( dto != null ){
            resultMap.put("monitor_config", modelMapper.map(dto, WorkPlaceMonitorConfigDto.class) );

            resultMap.put("workplace", modelMapper.map(dto, WorkplaceSummaryDto.class) );

            if( StringUtils.isNotEmpty(dto.getLinkWearable1())){
                WorkplaceExternalLink link = WorkplaceExternalLink.builder()
                    .linkNo(1)
                    .linkName("#1")
                    .linkUrl(dto.getLinkWearable1())
                    .linkStatus( dto.getLinkWearable1Status() == null ? 0 :dto.getLinkWearable1Status() )
                    .build();
                linkInfo.add(link);
            }
            if( StringUtils.isNotEmpty(dto.getLinkWearable2())){
                WorkplaceExternalLink link = WorkplaceExternalLink.builder()
                    .linkNo(2)
                    .linkName("#2")
                    .linkUrl(dto.getLinkWearable2())
                    .linkStatus( dto.getLinkWearable2Status() == null ? 0 :dto.getLinkWearable2Status() )
                    .build();
                linkInfo.add(link);
            }
        }
        resultMap.put("link_info", linkInfo );

        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        List<ImageAnalyticInfoDto> analyticList = imageAnalyticInfoDao.findList(condition);
        if( analyticList != null && analyticList.size() > 0 ){
            resultMap.put("analytic_info", analyticList.get(0));
        }

        List<EnvironmentMeasureDeviceVo> deviceList = new ArrayList<EnvironmentMeasureDeviceVo>();

        Map<Integer, GasSafeRangeDto> safeRangeMap = gasLogDao.findGasSafeRangeDtoList(wpId).stream().collect(
            Collectors.toMap(GasSafeRangeDto::getCategory, Function.identity()));

        List<GasLogDto> gasList = gasLogDao.findRecentGasLog(wpId);
        for(GasLogDto gasDto : gasList ){
            EnvironmentMeasureDeviceVo vo = modelMapper.map(gasDto, EnvironmentMeasureDeviceVo.class);
            List<EnvironmentMeasureVo> measureList = new ArrayList<EnvironmentMeasureVo>();
            if(  gasDto.getO2() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.OXYGEN, gasDto.getO2(), gasDto.getO2Level(), safeRangeMap.get(MeasureEnvironmentType.OXYGEN.getCode()) ));
            }
            if(  gasDto.getH2s() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.HYDROGEN_SULFIDE, gasDto.getH2s(), gasDto.getH2sLevel(), safeRangeMap.get(MeasureEnvironmentType.HYDROGEN_SULFIDE.getCode())  ));
            }
            if(  gasDto.getCo() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.CARBON_MONOXIDE, gasDto.getCo(), gasDto.getCoLevel(), safeRangeMap.get(MeasureEnvironmentType.CARBON_MONOXIDE.getCode())  ));
            }
            if(  gasDto.getCh4() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.METHANE, gasDto.getCh4(), gasDto.getCh4Level(), safeRangeMap.get(MeasureEnvironmentType.METHANE.getCode())  ));
            }
            if(  gasDto.getTemperature() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.TEMPERATURE, gasDto.getTemperature(), gasDto.getTemperatureLevel(), safeRangeMap.get(MeasureEnvironmentType.TEMPERATURE.getCode())  ));
            }

            if(  gasDto.getHumidity() != null ){
                measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.HUMIDITY, gasDto.getHumidity(), 0, safeRangeMap.get(MeasureEnvironmentType.HUMIDITY.getCode())  ));
            }

            vo.setHazardPhrase(gasDto.getHazardPhrase());
            vo.setList(measureList);
            deviceList.add(vo);
        }
        resultMap.put("hazard_measure_device", deviceList);

        resultMap.put("falling_accident", fallingAccidentService.findFallingAccidentRecentListByWpId(wpId));

        /* 날씨 정보 */
        WeatherInfo info = weatherService.getWeatherInfo(wpId);
        resultMap.put("weather_info", info != null ?  info : new WeatherInfo() );
        return resultMap;
    }


    @Transactional("transactionManager")
    public void updateAnalyticViewInfo(AnalyticInfoMgrRequest request){
        ImageAnalyticInfoKey key = modelMapper.map(request, ImageAnalyticInfoKey.class);
        ImageAnalyticInfo info = imageAnalyticInfoRepository.findById(key).orElse(null);
        if( info != null ){
            info.setEventView(request.getEventView());
            imageAnalyticInfoRepository.save(info);
        }

    }

    @Transactional("transactionManager")
    public void updateGasDevicePopup(UpdateGasDevicePopupRequest request){
        GasLogRecent recent = gasLogRecentRepository.findById(request.getMacAddress()).orElse(null);
        if( recent != null ){
            recent.setDashboardPopup(request.getDashboardPopup());
            gasLogRecentRepository.save(recent);
        }
    }
}
