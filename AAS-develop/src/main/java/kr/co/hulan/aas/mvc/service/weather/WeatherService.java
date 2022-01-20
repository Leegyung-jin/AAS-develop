package kr.co.hulan.aas.mvc.service.weather;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import kr.co.hulan.aas.common.code.PrecipitationForm;
import kr.co.hulan.aas.common.code.SkyForm;
import kr.co.hulan.aas.common.code.WeatherForecastForm;
import kr.co.hulan.aas.common.code.WeatherItemType;
import kr.co.hulan.aas.common.utils.GpsGridUtils;
import kr.co.hulan.aas.infra.vworld.VworldClient;
import kr.co.hulan.aas.infra.weather.WeatherClient;
import kr.co.hulan.aas.infra.weather.WeatherGridXY;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.infra.weather.request.WeatherForecastRequest;
import kr.co.hulan.aas.infra.weather.request.WeatherGridXYRequest;
import kr.co.hulan.aas.infra.weather.response.KmaResponse;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidTaItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherVilageFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWetherBodyItemResponse;
import kr.co.hulan.aas.infra.weather.response.KmaWetherBodyResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringDto;
import kr.co.hulan.aas.mvc.api.region.vo.RegionSidoVo;
import kr.co.hulan.aas.mvc.dao.mapper.AirEnvironmentDao;
import kr.co.hulan.aas.mvc.dao.mapper.RegionDao;
import kr.co.hulan.aas.mvc.dao.mapper.WeatherForecastDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWeatherDao;
import kr.co.hulan.aas.mvc.dao.repository.SidoWeatherAtmosphereRepository;
import kr.co.hulan.aas.mvc.dao.repository.WeatherForecastByGridRepository;
import kr.co.hulan.aas.mvc.dao.repository.WeatherForecastByRegionRepository;
import kr.co.hulan.aas.mvc.dao.repository.WeatherRegionRepository;
import kr.co.hulan.aas.mvc.dao.repository.WeatherForecastByPointRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceWeatherRepository;
import kr.co.hulan.aas.mvc.model.domain.SidoWeatherAtmosphere;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByGrid;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByGridKey;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherRegion;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceWeather;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByRegion;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByRegionKey;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByPoint;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByPointKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WeatherService {

  private static Logger log = LoggerFactory.getLogger(WeatherService.class);

  @Autowired
  private ModelMapper modelMapper;

  private ModelMapper modelMapperNotNull;

  @Autowired
  private WeatherClient weatherClient;

  @Autowired
  private RegionDao regionDao;

  @Autowired
  private SidoWeatherAtmosphereRepository sidoWeatherAtmosphereRepository;

  @Autowired
  private AirEnvironmentDao airEnvironmentDao;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private WorkPlaceWeatherDao workPlaceWeatherDao;

  @Autowired
  private WeatherForecastDao weatherForecastDao;

  @Autowired
  private WorkPlaceWeatherRepository workPlaceWeatherRepository;

  @Autowired
  private WeatherRegionRepository weatherRegionRepository;

  @Autowired
  private WeatherForecastByRegionRepository weatherForecastByRegionRepository;

  @Autowired
  private WeatherForecastByPointRepository weatherForecastByPointRepository;

  @Autowired
  private WeatherForecastByGridRepository weatherForecastByGridRepository;


  @PostConstruct
  public void init(){
    modelMapperNotNull = new ModelMapper();
    modelMapperNotNull.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapperNotNull.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public WeatherInfo findWeather(String wpId){
    WeatherInfo weather = workPlaceWeatherDao.findWeather(wpId);
    return weather;
  }

  public WeatherInfo getWeatherInfo(String wpId){
    return findWeather(wpId);
  }


  @Transactional("mybatisTransactionManager")
  public void updateSidoAirEnvironment(){
    try{
      airEnvironmentDao.updateSidoAirEnvironment();
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|updateSidoAirEnvironment| |Error|"+e.getMessage(), e);
    }
  }


  @Transactional("transactionManager")
  public void saveMidLandFcstResult(LocalDateTime now, List<KmaWeatherMidLandFcstItem> items){
    try{
      if( items != null && items.size() > 0 ){
        KmaWeatherMidLandFcstItem item = items.get(0);

        int dayAfter=3;
        LocalDateTime dayAfterDatetime = now.plusDays(dayAfter);

        while( dayAfter <= 10 ){
          WeatherForecastByRegionKey key = WeatherForecastByRegionKey.builder()
              .wfrCd(item.getRegId())
              .forecastDay(dayAfterDatetime.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
              .build();
          WeatherForecastByRegion forecast = weatherForecastByRegionRepository.findById(key).orElse(null);
          if( forecast == null ){
            forecast = modelMapper.map(key, WeatherForecastByRegion.class);
          }

          String rnstAm = item.getRnStAm(dayAfter);
          if( StringUtils.isNotBlank(rnstAm) ){
            forecast.setAmRainfall( rnstAm );
          }

          String rnstPm = item.getRnStPm(dayAfter);
          if( StringUtils.isNotBlank(rnstPm) ){
            forecast.setPmRainfall( rnstPm );
          }

          String wfAm = item.getWfAm(dayAfter);
          if( StringUtils.isNotBlank(wfAm)){
            forecast.setAmWfFormName( wfAm );
            WeatherForecastForm wfForm = WeatherForecastForm.getByName(wfAm);
            if( wfForm != null ){
              forecast.setAmSkyForm(""+wfForm.getSkyForm().getCode());
              forecast.setAmPrecipitationForm(""+wfForm.getPrecipitationForm().getCode());
            }
          }

          String wfPm = item.getWfPm(dayAfter);
          if( StringUtils.isNotBlank(wfPm)){
            forecast.setPmWfFormName( wfPm );
            WeatherForecastForm wfForm = WeatherForecastForm.getByName(wfPm);
            if( wfForm != null ){
              forecast.setPmSkyForm(""+wfForm.getSkyForm().getCode());
              forecast.setPmPrecipitationForm(""+wfForm.getPrecipitationForm().getCode());
            }
          }

          forecast.setUpdateDate(new Date());
          weatherForecastByRegionRepository.save(forecast);

          dayAfterDatetime = dayAfterDatetime.plusDays(1);
          dayAfter++;
        }
      }

    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|saveMidLandFcstResult| |Error|"+e.getMessage(), e);
    }
  }

  @Transactional("transactionManager")
  public void saveMidTaResult(LocalDateTime now, List<KmaWeatherMidTaItem> items){
    try{
      if( items != null && items.size() > 0 ){
        KmaWeatherMidTaItem item = items.get(0);

        int dayAfter=3;
        LocalDateTime dayAfterDatetime = now.plusDays(dayAfter);

        while( dayAfter <= 10 ){
          WeatherForecastByPointKey key = WeatherForecastByPointKey.builder()
              .wfpCd(item.getRegId())
              .forecastDay(dayAfterDatetime.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
              .build();
          WeatherForecastByPoint forecast = weatherForecastByPointRepository.findById(key).orElse(null);
          if( forecast == null ){
            forecast = modelMapper.map(key, WeatherForecastByPoint.class);
          }

          String taMax = item.getTaMax(dayAfter);
          if( StringUtils.isNotBlank(taMax) ){
            forecast.setTemperatureHigh( taMax );
          }

          String taMin = item.getTaMin(dayAfter);
          if( StringUtils.isNotBlank(taMin) ){
            forecast.setTemperatureLow( taMin );
          }
          forecast.setUpdateDate(new Date());
          weatherForecastByPointRepository.save(forecast);

          dayAfterDatetime = dayAfterDatetime.plusDays(1);
          dayAfter++;
        }
      }

    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|saveMidLandFcstResult| |Error|"+e.getMessage(), e);
    }
  }


  @Transactional("transactionManager")
  public void saveLandFcstResult(LocalDateTime now, List<KmaWeatherLandFcstItem> items){
    try{
      if( items != null && items.size() > 0 ){
        Map<String, WeatherForecastByPoint> saveMap = new HashMap<String, WeatherForecastByPoint>();
        for(KmaWeatherLandFcstItem item : items){
          if( item.isTodayForecasting() ){
            continue;
          }

          String forecastDay = item.getForecastDate();
          WeatherForecastByPoint forecast = saveMap.get(forecastDay);
          if( forecast == null ){
            WeatherForecastByPointKey key = WeatherForecastByPointKey.builder()
                .wfpCd(item.getRegId())
                .forecastDay(forecastDay)
                .build();
            forecast = weatherForecastByPointRepository.findById(key).orElse(null);
            if( forecast == null ){
              forecast = modelMapper.map(key, WeatherForecastByPoint.class);
              forecast.setCreateDate(new Date());
            }
            saveMap.put(forecastDay, forecast);
          }

          SkyForm skyForm = SkyForm.get(item.getWfCd());
          if( item.isMorning() ){
            forecast.setAmPrecipitationForm(item.getRnYn());
            forecast.setAmRainfall(item.getRnSt());
            forecast.setAmSkyForm( skyForm != null ? ""+skyForm.getCode() : item.getWfCd());
            forecast.setAmWfFormName(item.getWf());
            if( item.getTa() != null ){
              forecast.setTemperatureLow(item.getTa());
            }
          }
          else {
            forecast.setPmPrecipitationForm(item.getRnYn());
            forecast.setPmRainfall(item.getRnSt());
            forecast.setPmSkyForm( skyForm != null ? ""+skyForm.getCode() : item.getWfCd());
            forecast.setPmWfFormName(item.getWf());
            if( item.getTa() != null ){
              forecast.setTemperatureHigh(item.getTa());
            }
          }
          forecast.setUpdateDate(new Date());
        }

        if( saveMap.size() > 0 ){
          weatherForecastByPointRepository.saveAll(saveMap.values());
        }
      }

    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|saveMidLandFcstResult| |Error|"+e.getMessage(), e);
    }
  }

  @Transactional("transactionManager")
  public void saveVilageFcstResult(List<KmaWeatherVilageFcstItem> items){
    try{

      if( items != null && items.size() > 0 ){
        Map<String, HashMap<Integer, WeatherForecastByGrid>> saveMap = new HashMap<String, HashMap<Integer, WeatherForecastByGrid>>();
        for (KmaWeatherVilageFcstItem item : items ) {
          LocalDateTime fcstDateTime = item.getFcstDateTime();
          HashMap<Integer, WeatherForecastByGrid> fcstDateMap = saveMap.get(item.getFcstDate());
          if( fcstDateMap == null ){
            fcstDateMap = new HashMap<Integer, WeatherForecastByGrid>();
            saveMap.put(item.getFcstDate(), fcstDateMap);
          }
          WeatherForecastByGrid forecast = fcstDateMap.get(fcstDateTime.getHour());
          if( forecast == null ){
            WeatherForecastByGridKey gridKey = WeatherForecastByGridKey.builder()
                .nx(item.getNx())
                .ny(item.getNy())
                .forecastDay(item.getFcstDate())
                .forecastHour(fcstDateTime.getHour())
                .build();

            forecast = weatherForecastByGridRepository.findById(gridKey).orElse(null);
            if( forecast == null ){
              forecast = modelMapper.map(gridKey, WeatherForecastByGrid.class);
              forecast.setCreateDate(new Date());
            }
            fcstDateMap.put(fcstDateTime.getHour(), forecast);
            forecast.setUpdateDate(new Date());
          }

          WeatherItemType type = WeatherItemType.get(item.getCategory());
          if( type ==  WeatherItemType.RAINFALL ){
            forecast.setRainfall(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.PRECIPITATION_FORM ){
            forecast.setPrecipitationForm(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.PRECIPITATION_PCP || type ==  WeatherItemType.PRECIPITATION ){
            forecast.setPrecipitation(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.HUMIDITY ){
            forecast.setHumidity(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.SNOW ){
            forecast.setAmountSnow(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.SKY ){
            forecast.setSkyForm(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.TEMPERATURE_TMP || type ==  WeatherItemType.TEMPERATURE ){
            forecast.setTemperature(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.WIND_DIRECTION ){
            forecast.setWindDirection(item.getFcstValue());
          }
          else if( type ==  WeatherItemType.WIND_SPEED ){
            forecast.setWindSpeed(item.getFcstValue());
          }
        }
        if( saveMap.size() > 0 ){
          List<WeatherForecastByGrid> saveList = new ArrayList<WeatherForecastByGrid>();
          for( HashMap<Integer, WeatherForecastByGrid> map : saveMap.values() ){
            saveList.addAll(map.values());
          }
          if( saveList.size() > 0 ){
            weatherForecastByGridRepository.saveAll(saveList);
          }
        }
      }
    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|saveVilageFcstResult| |Error|"+e.getMessage(), e);
    }
  }

  @Transactional("transactionManager")
  public void saveUltraFcstResult(List<KmaWeatherVilageFcstItem> items){
    saveVilageFcstResult(items);
  }


  @Transactional("transactionManager")
  public void saveUltraNcstResult(LocalDateTime now, List<KmaWetherBodyItemResponse> items){
    try{

      if( items != null && items.size() > 0 ){
        Map<String, HashMap<Integer, WeatherForecastByGrid>> saveMap = new HashMap<String, HashMap<Integer, WeatherForecastByGrid>>();
        for (KmaWetherBodyItemResponse item : items ) {
          LocalDateTime baseDateTime = item.getBaseDateTime();
          HashMap<Integer, WeatherForecastByGrid> fcstDateMap = saveMap.get(item.getBaseDate());
          if( fcstDateMap == null ){
            fcstDateMap = new HashMap<Integer, WeatherForecastByGrid>();
            saveMap.put(item.getBaseDate(), fcstDateMap);
          }
          WeatherForecastByGrid forecast = fcstDateMap.get(baseDateTime.getHour());
          if( forecast == null ){
            WeatherForecastByGridKey gridKey = WeatherForecastByGridKey.builder()
                .nx(NumberUtils.toInt(item.getNx()))
                .ny(NumberUtils.toInt(item.getNy()))
                .forecastDay(item.getBaseDate())
                .forecastHour(baseDateTime.getHour())
                .build();

            forecast = weatherForecastByGridRepository.findById(gridKey).orElse(null);
            if( forecast == null ){
              forecast = modelMapper.map(gridKey, WeatherForecastByGrid.class);
              forecast.setCreateDate(new Date());
            }
            fcstDateMap.put(baseDateTime.getHour(), forecast);
            forecast.setUpdateDate(new Date());
          }

          WeatherItemType type = WeatherItemType.get(item.getCategory());
          if( type ==  WeatherItemType.RAINFALL ){
            forecast.setRainfall(item.getValue());
          }
          else if( type ==  WeatherItemType.PRECIPITATION_FORM ){
            forecast.setPrecipitationForm(item.getValue());
          }
          else if( type ==  WeatherItemType.PRECIPITATION_PCP || type ==  WeatherItemType.PRECIPITATION ){
            forecast.setPrecipitation(item.getValue());
          }
          else if( type ==  WeatherItemType.HUMIDITY ){
            forecast.setHumidity(item.getValue());
          }
          else if( type ==  WeatherItemType.SNOW ){
            forecast.setAmountSnow(item.getValue());
          }
          else if( type ==  WeatherItemType.SKY ){
            forecast.setSkyForm(item.getValue());
          }
          else if( type ==  WeatherItemType.TEMPERATURE_TMP || type ==  WeatherItemType.TEMPERATURE ){
            forecast.setTemperature(item.getValue());
          }
          else if( type ==  WeatherItemType.WIND_DIRECTION ){
            forecast.setWindDirection(item.getValue());
          }
          else if( type ==  WeatherItemType.WIND_SPEED ){
            forecast.setWindSpeed(item.getValue());
          }
        }
        if( saveMap.size() > 0 ){
          List<WeatherForecastByGrid> saveList = new ArrayList<WeatherForecastByGrid>();
          for( HashMap<Integer, WeatherForecastByGrid> map : saveMap.values() ){
            saveList.addAll(map.values());
          }
          if( saveList.size() > 0 ){
            weatherForecastByGridRepository.saveAll(saveList);
          }
        }
      }
    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|saveUltraNcstResult| |Error|"+e.getMessage(), e);
    }
  }

  @Transactional("transactionManager")
  public void updateWorkplaceWeather(WorkplaceSupportMonitoringDto workplace, String baseDate, String baseTime ){
    LocalDateTime baseDateTime = LocalDateTime.parse(baseDate+baseTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    try{
      WeatherForecastByGrid forecast = weatherForecastByGridRepository.findById(WeatherForecastByGridKey.builder()
          .nx(workplace.getNx())
          .ny(workplace.getNy())
          .forecastDay(baseDate)
          .forecastHour(baseDateTime.getHour())
          .build()
      ).orElse(null);
      if( forecast != null ){
        WorkPlaceWeather wpWeather = workPlaceWeatherRepository.findById(workplace.getWpId()).orElse(null);
        if( wpWeather != null ){
          modelMapperNotNull.map(forecast, wpWeather);
        }
        else {
          wpWeather = modelMapperNotNull.map(forecast, WorkPlaceWeather.class);
          wpWeather.setWpId(workplace.getWpId());
          wpWeather.setHumidityUnit(WeatherItemType.HUMIDITY.getUnit());
          wpWeather.setWindSpeedUnit(WeatherItemType.WIND_SPEED.getUnit());
          wpWeather.setPrecipitationUnit(WeatherItemType.PRECIPITATION.getUnit());
          wpWeather.setTemperatureUnit(WeatherItemType.TEMPERATURE.getUnit());
          wpWeather.setRainfallUnit(WeatherItemType.RAINFALL.getUnit());
        }
        long dateTimeLong = baseDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        wpWeather.setBaseDate(new java.sql.Date(dateTimeLong));
        wpWeather.setBaseTime(new java.sql.Time(dateTimeLong));
        PrecipitationForm pform = PrecipitationForm.get(forecast.getPrecipitationForm());
        if( pform != null ){
          wpWeather.setPrecipitationFormName(pform.getName());
        }
        SkyForm sform = SkyForm.get(forecast.getSkyForm());
        if( sform != null ){
          wpWeather.setSkyFormName(sform.getName());
        }
        wpWeather.setUpdateDate(new Date());
        workPlaceWeatherRepository.save(wpWeather);
      }
    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|updateWorkplaceWeather| |Error|"+e.getMessage(), e);
    }
  }

  @Transactional("transactionManager")
  public void updateSidoWeather(RegionSidoVo sido, String baseDate, String baseTime ){
    LocalDateTime baseDateTime = LocalDateTime.parse(baseDate+baseTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    try{
      WeatherForecastByGrid forecast = weatherForecastByGridRepository.findById(WeatherForecastByGridKey.builder()
          .nx(sido.getNx())
          .ny(sido.getNy())
          .forecastDay(baseDate)
          .forecastHour(baseDateTime.getHour())
          .build()
      ).orElse(null);
      if( forecast != null ){
        SidoWeatherAtmosphere sidoWeather = sidoWeatherAtmosphereRepository.findById(sido.getSidoCd()).orElse(null);
        if( sidoWeather != null ){
          modelMapperNotNull.map(forecast, sidoWeather);
        }
        else {
          sidoWeather = modelMapperNotNull.map(forecast, SidoWeatherAtmosphere.class);
          sidoWeather.setSidoCd(sido.getSidoCd());
        }
        PrecipitationForm pform = PrecipitationForm.get(forecast.getPrecipitationForm());
        if( pform != null ){
          sidoWeather.setPrecipitationFormName(pform.getName());
        }
        SkyForm sform = SkyForm.get(forecast.getSkyForm());
        if( sform != null ){
          sidoWeather.setSkyFormName(sform.getName());
        }
        sidoWeather.setWeatherUltraUpdate(new Date());
        sidoWeather.setWeatherVilUpdate(new Date());
        sidoWeather.setUpdateDate(new Date());
        sidoWeatherAtmosphereRepository.save(sidoWeather);
      }
    } catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|updateSidoWeather| |Error|"+e.getMessage(), e);
    }
  }

  public void collectMidLandFcst(LocalDateTime now) {

    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;

    DateTimeFormatter formatter = now.getHour() >= 6 && now.getHour() < 18
        ? DateTimeFormatter.ofPattern("yyyyMMdd0600")
        : DateTimeFormatter.ofPattern("yyyyMMdd1800");
    String tmFc = now.format(formatter);

    try{
      List<WeatherRegion> regions = weatherRegionRepository.findAll();
      if( regions != null && regions.size() > 0 ){
        totalCount = regions.size();
        for( WeatherRegion region : regions ){
          WeatherForecastRequest request = new WeatherForecastRequest(region.getWfrCd(), tmFc);
          KmaResponse<KmaWeatherMidLandFcstItem> response = weatherClient.findWeatherMidLandFcst(request);
          if( response != null && response.isResultOk() ){
            saveMidLandFcstResult(now, response.getResponse().getBody().getItems().getItem());
            successCount++;
          }
          else {
            log.error(this.getClass().getSimpleName()+"|collectMidLandFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectMidLandFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectMidLandFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }
  }

  public void collectMidLandTaFcst(LocalDateTime now) {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;

    DateTimeFormatter formatter = now.getHour() >= 6 && now.getHour() < 18
        ? DateTimeFormatter.ofPattern("yyyyMMdd0600")
        : DateTimeFormatter.ofPattern("yyyyMMdd1800");
    String tmFc = now.format(formatter);

    try{
      List<String> forecastPointCds = weatherForecastDao.findWeatherPointRelatedWorkplace();
      if( forecastPointCds != null && forecastPointCds.size() > 0 ){
        totalCount = forecastPointCds.size();
        for( String wfpCd : forecastPointCds ){
          WeatherForecastRequest request = new WeatherForecastRequest(wfpCd, tmFc);
          KmaResponse<KmaWeatherMidTaItem> response = weatherClient.findWeatherMidTa(request);
          if( response != null && response.isResultOk() ){
            saveMidTaResult(now, response.getResponse().getBody().getItems().getItem());
            successCount++;
          }
          else {
            log.error(this.getClass().getSimpleName()+"|collectMidLandTaFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectMidLandTaFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectMidLandTaFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }
  }


  public void collectLandFcst() {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;

    try{
      List<String> forecastPointCds = weatherForecastDao.findWeatherPointRelatedWorkplace();
      if( forecastPointCds != null && forecastPointCds.size() > 0 ){
        totalCount = forecastPointCds.size();
        for( String wfpCd : forecastPointCds ){
          WeatherForecastRequest request = new WeatherForecastRequest(wfpCd, "");
          request.setNumOfRows(1000);

          KmaResponse<KmaWeatherLandFcstItem> response = weatherClient.findWeatherLandFcst(request);
          if( response != null && response.isResultOk() ){
            saveLandFcstResult(LocalDateTime.now(), response.getResponse().getBody().getItems().getItem());
            successCount++;
          }
          else {
            log.error(this.getClass().getSimpleName()+"|collectLandFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectLandFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectLandFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }

  }

  public void collectWorkplaceVilageFcst(LocalDateTime now){
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

      List<WorkplaceSupportMonitoringDto> workplaceList = workPlaceDao.findWorkplaceSupportMonitoring(Collections.emptyMap());
      if( workplaceList != null && workplaceList.size() > 0 ){
        totalCount = workplaceList.size();
        for( WorkplaceSupportMonitoringDto workplace : workplaceList ){
          WeatherGridXYRequest request = null;
          if( workplace.getNx() != null && workplace.getNy() != null  ){
            request = new WeatherGridXYRequest(baseDate, baseTime, workplace.getNx(), workplace.getNy());
          }
          else if( workplace.getActivationGeofenceLatitude() != null && workplace.getActivationGeofenceLongitude() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getActivationGeofenceLatitude(), workplace.getActivationGeofenceLongitude() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else if( workplace.getGpsCenterLat() != null && workplace.getGpsCenterLong() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getGpsCenterLat(), workplace.getGpsCenterLong() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else {
            log.warn(this.getClass().getSimpleName()+"|collectWorkplaceVilageFcst| |Warn|NoGPSCoordinates "+workplace.getWpName()+"["+workplace.getWpId()+"]");
            continue;
          }
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWeatherVilageFcstItem> response = weatherClient.findWeatherVilageFcst(request);
            if( response != null && response.isResultOk() ){
              saveVilageFcstResult(response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWeatherVilageFcstItem> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectWorkplaceVilageFcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectWorkplaceVilageFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }
        }
      }

    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectWorkplaceVilageFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectWorkplaceVilageFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }
  }

  public void collectSidoVilageFcst(LocalDateTime now){
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

      List<RegionSidoVo> sidoList = regionDao.findSidoList();
      if( sidoList != null && sidoList.size() != 0 ){
        totalCount = sidoList.size();
        for(RegionSidoVo dto :  sidoList ){
          if( dto.getNx() == null || dto.getNy() == null ){
            log.debug(this.getClass().getSimpleName()+"|refreshSidoWeathers|FAIL|InvalidNxNy("+ dto.getSidoCd() +")|");
            continue;
          }
          WeatherGridXYRequest request = new WeatherGridXYRequest(baseDate, baseTime, dto.getNx(), dto.getNy());;
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWeatherVilageFcstItem> response = weatherClient.findWeatherVilageFcst(request);
            if( response != null && response.isResultOk() ){
              saveVilageFcstResult(response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWeatherVilageFcstItem> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectSidoVilageFcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectSidoVilageFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectSidoVilageFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectSidoVilageFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }
  }

  public void collectWorkplaceUltraFcst(LocalDateTime now) {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH30"));

      List<WorkplaceSupportMonitoringDto> workplaceList = workPlaceDao.findWorkplaceSupportMonitoring(Collections.emptyMap());
      if( workplaceList != null && workplaceList.size() != 0 ){
        totalCount = workplaceList.size();
        for( WorkplaceSupportMonitoringDto workplace : workplaceList ){
          WeatherGridXYRequest request = null;
          if( workplace.getNx() != null && workplace.getNy() != null  ){
            request = new WeatherGridXYRequest(baseDate, baseTime, workplace.getNx(), workplace.getNy());
          }
          else if( workplace.getActivationGeofenceLatitude() != null && workplace.getActivationGeofenceLongitude() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getActivationGeofenceLatitude(), workplace.getActivationGeofenceLongitude() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else if( workplace.getGpsCenterLat() != null && workplace.getGpsCenterLong() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getGpsCenterLat(), workplace.getGpsCenterLong() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else {
            log.warn(this.getClass().getSimpleName()+"|collectWorkplaceUltraFcst| |Warn|NoGPSCoordinates "+workplace.getWpName()+"["+workplace.getWpId()+"]");
            continue;
          }
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWeatherVilageFcstItem> response = weatherClient.findWeatherUltraFcst(request);
            if( response != null && response.isResultOk() ){
              saveUltraFcstResult(response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWeatherVilageFcstItem> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraFcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectWorkplaceUltraFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }

  }


  public void collectSidoUltraFcst(LocalDateTime now) {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH30"));

      List<RegionSidoVo> sidoList = regionDao.findSidoList();
      if( sidoList != null && sidoList.size() != 0 ){
        totalCount = sidoList.size();
        for(RegionSidoVo dto :  sidoList ){
          if( dto.getNx() == null || dto.getNy() == null ){
            log.debug(this.getClass().getSimpleName()+"|collectSidoUltraFcst|FAIL|InvalidNxNy("+ dto.getSidoCd() +")|");
            continue;
          }
          WeatherGridXYRequest request = new WeatherGridXYRequest(baseDate, baseTime, dto.getNx(), dto.getNy());;
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWeatherVilageFcstItem> response = weatherClient.findWeatherUltraFcst(request);
            if( response != null && response.isResultOk() ){
              saveUltraFcstResult(response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWeatherVilageFcstItem> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectSidoUltraFcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectSidoUltraFcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectSidoUltraFcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectSidoUltraFcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }
  }


  public void collectWorkplaceUltraNcst(LocalDateTime now) {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

      List<WorkplaceSupportMonitoringDto> workplaceList = workPlaceDao.findWorkplaceSupportMonitoring(Collections.emptyMap());
      if( workplaceList != null && workplaceList.size() != 0 ){
        totalCount = workplaceList.size();
        for( WorkplaceSupportMonitoringDto workplace : workplaceList ){
          WeatherGridXYRequest request = null;
          if( workplace.getNx() != null && workplace.getNy() != null  ){
            request = new WeatherGridXYRequest(baseDate, baseTime, workplace.getNx(), workplace.getNy());
          }
          else if( workplace.getActivationGeofenceLatitude() != null && workplace.getActivationGeofenceLongitude() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getActivationGeofenceLatitude(), workplace.getActivationGeofenceLongitude() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else if( workplace.getGpsCenterLat() != null && workplace.getGpsCenterLong() != null  ){
            WeatherGridXY grid = GpsGridUtils.getWeatherGrid(workplace.getGpsCenterLat(), workplace.getGpsCenterLong() );
            request = new WeatherGridXYRequest(baseDate, baseTime, grid.getNx(), grid.getNy());
          }
          else {
            log.warn(this.getClass().getSimpleName()+"|collectWorkplaceUltraNcst| |Warn|NoGPSCoordinates "+workplace.getWpName()+"["+workplace.getWpId()+"]");
            continue;
          }
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWetherBodyItemResponse> response = weatherClient.findWeatherUltraNcst(request);
            if( response != null && response.isResultOk() ){
              saveUltraNcstResult(LocalDateTime.now(), response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWetherBodyItemResponse> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraNcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              updateWorkplaceWeather(workplace, baseDate, baseTime);
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraNcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }

        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectWorkplaceUltraNcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      log.warn( this.getClass().getSimpleName()+"|collectWorkplaceUltraNcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
    }

  }

  public void collectSidoUltraNcst(LocalDateTime now) {
    LocalTime start = LocalTime.now();
    int successCount = 0;
    int totalCount = 0;
    try{
      String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

      List<RegionSidoVo> sidoList = regionDao.findSidoList();
      if( sidoList != null && sidoList.size() != 0 ){
        totalCount = sidoList.size();
        for(RegionSidoVo dto :  sidoList ){
          if( dto.getNx() == null || dto.getNy() == null ){
            log.debug(this.getClass().getSimpleName()+"|collectSidoUltraFcst|FAIL|InvalidNxNy("+ dto.getSidoCd() +")|");
            continue;
          }
          WeatherGridXYRequest request = new WeatherGridXYRequest(baseDate, baseTime, dto.getNx(), dto.getNy());;
          request.setNumOfRows(1000);

          while(true){
            KmaResponse<KmaWetherBodyItemResponse> response = weatherClient.findWeatherUltraNcst(request);
            if( response != null && response.isResultOk() ){
              saveUltraNcstResult(LocalDateTime.now(), response.getResponse().getBody().getItems().getItem());
              KmaWetherBodyResponse<KmaWetherBodyItemResponse> body = response.getResponse().getBody();
              if( !body.isEndResult() ){
                log.error(this.getClass().getSimpleName()+"|collectSidoUltraNcst| |Continue|"+body.getPageNo()+"/"+body.getTotalCount());
                request.setPageNo( request.getPageNo() + 1);
                continue;
              }
              updateSidoWeather(dto, baseDate, baseTime);
              successCount++;
            }
            else {
              log.error(this.getClass().getSimpleName()+"|collectSidoUltraNcst| |Error|"+request.getUrlParams()+"|"+( response != null ? response.getResult() : "response is null"));
            }
            break;
          }
        }
      }
    }catch(Exception e){
      log.error(this.getClass().getSimpleName()+"|collectSidoUltraNcst| |Error|"+e.getMessage(), e);
    }finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|collectSidoUltraNcst|"+successCount+"/"+totalCount+"|ElapsedTime|" + duration.getSeconds());
      }
    }

  }


  // 매일 06시, 18시
  // 기상청 중기육상예보 구축
  // 기상청 중기기온예보 구축
  @Scheduled(cron = "0 5 6,18 * * ?")
  public void executeCollectMidLandFcst() {
    LocalDateTime now = LocalDateTime.now();
    collectMidLandFcst(now);
    collectMidLandTaFcst(now);
  }


  // 매일 05시, 11시, 17시
  // 기상청 동네예보통보문 육상예보 ( 3일 이내 정보 )
  @Scheduled(cron = "0 5 5,11,17 * * ?")
  public void executeCollectLandFcst() {
    collectLandFcst();
  }

  // 기상청 단기예보 시간별 지점 육상예보 ( 3일 이내 정보 )
  @Scheduled(cron = "0 15 2,5,8,11,14,17,20,23 * * ?")
  public void executeCollectVilageFcst() {
    LocalDateTime now = LocalDateTime.now();
    collectWorkplaceVilageFcst(now);
    collectSidoVilageFcst(now);
  }

  // 기상청 초단기예보 ( 6시간 예보 )
  @Scheduled(cron = "0 50 * * * ?")
  public void executeCollectUltraFcst() {
    LocalDateTime now = LocalDateTime.now();
    collectWorkplaceUltraFcst(now);
    collectSidoUltraFcst(now);
  }

  // 기상청 초단기실황
  @Scheduled(cron = "0 45 * * * ?")
  public void executeCollectUltraNcst() {
    LocalDateTime now = LocalDateTime.now();
    collectWorkplaceUltraNcst(now);
    collectSidoUltraNcst(now);
  }
}
