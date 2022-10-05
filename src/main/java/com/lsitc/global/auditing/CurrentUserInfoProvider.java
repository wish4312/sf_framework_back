package com.lsitc.global.auditing;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;

public enum CurrentUserInfoProvider implements UserProvider<Map<String, String>, Long> {
  INSTANCE;

  @Override
  public Map<String, String> getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof Map) {
      return (Map<String, String>) principal;
    } else {
      return new HashMap<String, String>() {{
        put("userNo", null);
        put("userNm", "anonymous");
      }};
    }
  }

  @Override
  public Long getId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof Map) {
      Map<String, String> userInfo = (Map<String, String>) principal;
      return Long.valueOf(userInfo.get("userNo"));
    } else {
      return null;
    }
  }
}
