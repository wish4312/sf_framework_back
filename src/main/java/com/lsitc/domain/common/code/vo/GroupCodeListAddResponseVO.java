package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeListAddResponseVO {

  private final String result;

  @Builder
  private GroupCodeListAddResponseVO(String result) {
    this.result = result;
  }

  public static GroupCodeListAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "GroupCodeListAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
