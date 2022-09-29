package com.lsitc.domain.common.user.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAddResponseVO {

  private final String result;

  @Builder
  public UserAddResponseVO(String result) {
    this.result = result;
  }

  public static UserAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
