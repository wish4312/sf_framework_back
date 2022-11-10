package com.lsitc.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Profile("local")
public class LocalSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .loginProcessingUrl("/signin")
        .successForwardUrl("/common/auth/success")
        .failureForwardUrl("/common/auth/failure")
        .usernameParameter("userId")
        .passwordParameter("password")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder encoderPwd() {
    return new BCryptPasswordEncoder();
  }

}
