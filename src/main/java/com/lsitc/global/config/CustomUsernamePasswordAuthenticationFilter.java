package com.lsitc.global.config;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsitc.domain.common.user.entity.UserEntity;

public class CustomUsernamePasswordAuthenticationFilter
    extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {

      UsernamePasswordAuthenticationToken authRequest = null;

      try (InputStream is = request.getInputStream()) {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity userEntity = mapper.readValue(is, UserEntity.class);
        authRequest = new UsernamePasswordAuthenticationToken(userEntity.getUserId(), userEntity.getPassword());
      } catch (IOException e) {
        throw new AuthenticationServiceException(
            "Request Content-Type(application/json) Parsing Error");
      }
      setDetails(request, authRequest);
      return super.getAuthenticationManager().authenticate(authRequest);
    } else {
      super.setUsernameParameter("userId");
      super.setPasswordParameter("password");
      return super.attemptAuthentication(request, response);
    }
  }

}
