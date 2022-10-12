package com.lsitc.domain.common.role.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleAddResponseVO {

  private final String result;

  @Builder
  private RoleAddResponseVO(String result) {
    this.result = result;
  }

  public static RoleAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "RoleAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}