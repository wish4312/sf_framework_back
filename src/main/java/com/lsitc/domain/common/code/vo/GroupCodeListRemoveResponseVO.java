package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeListRemoveResponseVO {

  private final String result;

  @Builder
  private GroupCodeListRemoveResponseVO(String result) {
    this.result = result;
  }

  public static GroupCodeListRemoveResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "GroupCodeListRemoveResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
