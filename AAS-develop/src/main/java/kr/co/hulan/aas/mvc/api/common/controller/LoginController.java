package kr.co.hulan.aas.mvc.api.common.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import kr.co.hulan.aas.mvc.api.common.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FrameworkEndpoint
@Controller
@RequestMapping("/common/login")
public class LoginController {

  @Autowired
  private LoginManager loginManager;

  @PostMapping("/imos")
  public ResponseEntity<String> imosLogin (
      HttpServletRequest request
      ,@RequestParam(value = "username")  String username
      ,@RequestParam(value = "password")  String password
      ,@RequestParam(value = "uuid", required = false)  String uuid
      ,@RequestParam(value = "otp", required = false)  String otp
  ) throws HttpRequestMethodNotSupportedException, IOException {
    return loginManager.imosLogin(request, username, password, uuid, otp);
  }

  @PostMapping(value="/hicc", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> hiccLogin (
      HttpServletRequest request
      ,@RequestParam(value = "username")  String username
      ,@RequestParam(value = "password")  String password
  ) throws HttpRequestMethodNotSupportedException, IOException {
    return loginManager.hiccLogin(request, username, password);
  }

}
