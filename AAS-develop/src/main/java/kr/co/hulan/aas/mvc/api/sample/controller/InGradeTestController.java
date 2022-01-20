package kr.co.hulan.aas.mvc.api.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.aspect.grade.ValidGrade;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/grade/test")
@Api(tags = "권한 Aspect 테스트")
public class InGradeTestController {


  // g5_member 의 모든 mb_level
  @ValidGrade(grades = {2, 3, 4, 10})
  @ApiOperation(value = "권한 성공", notes = "권한 성공 테스트")
  @PostMapping("ok")
  public DefaultHttpRes okGrade() {
    return new DefaultHttpRes<>(BaseCode.SUCCESS);
  }

  // g5_member 에 없는 mb_level
  @ValidGrade(grades = {1})
  @ApiOperation(value = "권한 실패", notes = "권한 실패 테스트")
  @PostMapping("no")
  public DefaultHttpRes noGrade() {
    return new DefaultHttpRes<>(BaseCode.SUCCESS);
  }
}
