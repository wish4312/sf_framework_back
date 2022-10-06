package com.lsitc.global.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomFilterDsl extends AbstractHttpConfigurer<CustomFilterDsl, HttpSecurity> {
  
  private final String filterProcessesUrl;
  
  public CustomFilterDsl(String filterProcessesUrl) {
    this.filterProcessesUrl = filterProcessesUrl;
  }
  
  @Override
  public void configure(HttpSecurity http) throws Exception {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
      authenticationFilter.setAuthenticationManager(authenticationManager);
      authenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
      
      http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public static CustomFilterDsl customFilterDsl(String filterProcessesUrl) {
      return new CustomFilterDsl(filterProcessesUrl);
  }
}
