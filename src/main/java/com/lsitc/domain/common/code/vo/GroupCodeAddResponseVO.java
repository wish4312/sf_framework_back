package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeAddResponseVO {

  private final String result;

  @Builder
  private GroupCodeAddResponseVO(String result) {
    this.result = result;
  }

  public static GroupCodeAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "GroupCodeAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
