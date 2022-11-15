package com.lsitc.domain.common.auth.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthFailureGetRequestVO {

  private final String userId;
  private final String password;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
