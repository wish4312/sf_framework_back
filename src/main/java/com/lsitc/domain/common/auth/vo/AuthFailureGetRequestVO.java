package com.lsitc.domain.common.auth.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthFailureGetRequestVO {

  private final String userId;
  private final String password;

  @Builder
  private AuthFailureGetRequestVO(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

  public static AuthFailureGetRequestVO of(String userId, String password) {
    return builder().userId(userId).password(password).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
