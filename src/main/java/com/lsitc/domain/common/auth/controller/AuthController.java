package com.lsitc.domain.common.auth.controller;

import javax.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lsitc.domain.common.auth.vo.AuthFailureGetRequestVO;
import com.lsitc.domain.common.auth.vo.AuthFailureGetResponseVO;
import com.lsitc.domain.common.auth.vo.AuthSuccessGetResponseVO;
import com.lsitc.domain.common.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/common/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  @GetMapping("/success")
  public AuthSuccessGetResponseVO loginSuccess() {
    UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("login success {}", userEntity.getName());
    return AuthSuccessGetResponseVO.of(userEntity);
  }
  
  @PostMapping("/failure")
  public AuthFailureGetResponseVO loginFailure(@Valid final AuthFailureGetRequestVO authFailureGetRequestVO) {
    log.info("login failure {}", authFailureGetRequestVO);
    return AuthFailureGetResponseVO.of("로그인에 실패했습니다.");
  }
}
