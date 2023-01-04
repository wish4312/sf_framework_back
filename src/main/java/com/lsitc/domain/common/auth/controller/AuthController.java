package com.lsitc.domain.common.auth.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/success")
  public AuthSuccessGetResponseVO loginSuccess(HttpSession session) {
    UserEntity userEntity =
        (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("login success : {}({}) - {}", userEntity.getUserId(), userEntity.getName(),
        session.getId());
    return AuthSuccessGetResponseVO.of(userEntity);
  }

  @PostMapping("/failure")
  public AuthFailureGetResponseVO loginFailure(
      @Valid final AuthFailureGetRequestVO authFailureGetRequestVO, HttpServletResponse response) {
    log.info("login failure : {} / {}", authFailureGetRequestVO.getUserId(),
        authFailureGetRequestVO.getPassword());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    return AuthFailureGetResponseVO.of("로그인에 실패했습니다.");
  }

  @GetMapping("/logout")
  public ResponseEntity<Void> logout(HttpSession session) {
    return ResponseEntity.ok().build();
  }
}
