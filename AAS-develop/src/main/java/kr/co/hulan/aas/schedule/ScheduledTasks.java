package kr.co.hulan.aas.schedule;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceSafeyScoreDao;
import kr.co.hulan.aas.mvc.service.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private TokenService tokenService;

  @Autowired
  private FileService fileService;

  @Autowired
  private WorkPlaceSafeyScoreDao workPlaceSafeyScoreDao;

  // 매일 새벽 1시 실행
  @Scheduled(cron = "0 0 01 * * ?")
  public void everyDayOneSchedule() {
    logger.info("===============================================================");
    logger.info("만료 토큰 삭제 시작");
    tokenService.deleteExpiredToken();
    fileService.deleteTempFiles();
    logger.info("만료 토큰 삭제 종료");
    logger.info("===============================================================");
  }

  /*
  ----------------------------------------------------------------------------------------------
  일별 안전점수 산정 방식
      - 기본 점수 ± 가감점수 방식 (총 100점)
      - 기본 점수: 75점. 매월 0시에 75점 부여
      - 앱 사용률: ±10점. 출력 인원 대비 50% 사용시 0점. 1% 단위로 0.2점 가감 (100% 사용시 50 * 0.2점으로 총 10점 추가)
        ==> 출력인원은 출입센서 로그 기준
      - 도입 안전 장비류: +5점.
        ==> 개구부 개폐 감지(X), 기울기 감지(X) , 유해물질 측정, CCTV 관제, 출입 게이트 등 종류 별로 +1점. 최대 5점
      - 무재해 시작일 변경: -30점
  당일은 실시간
  일자별 통계 생성하나 30일 기준 안전점수 추출시에는 5인 이상 출근한 날만 표시
  일별 사용자 앱 사용 여부는 차후 김이사 완료 후 적용
  ----------------------------------------------------------------------------------------------
  */
  @Scheduled(cron = "1 * 4-22 * * ?")
  public void updateSafetyScore() {
    LocalTime start = LocalTime.now();
    try{
      logger.info(this.getClass().getSimpleName()+"|updateSafetyScore|Start|");
      workPlaceSafeyScoreDao.updateSafetyScore();
    }
    catch(Exception e){
      logger.error(this.getClass().getSimpleName()+"|updateSafetyScore|Error|"+e.getMessage(), e);
    }
    finally{
      Duration duration = Duration.between(start, LocalTime.now());
      logger.info( this.getClass().getSimpleName()+"|updateSafetyScore|Finish|elapsedTime=" + Duration.of(duration.getNano(), ChronoUnit.NANOS).toMillis());
    }
  }
}
