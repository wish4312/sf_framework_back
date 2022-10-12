package com.lsitc.domain.common.role.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleRemoveResponseVO {

  private final String result;

  @Builder
  private RoleRemoveResponseVO(String result) {
    this.result = result;
  }

  public static RoleRemoveResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "RoleRemoveResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
