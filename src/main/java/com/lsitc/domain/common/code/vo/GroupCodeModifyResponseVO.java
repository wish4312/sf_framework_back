package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeModifyResponseVO {

  private final String result;

  @Builder
  private GroupCodeModifyResponseVO(String result) {
    this.result = result;
  }

  public static GroupCodeModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "GroupCodeModifyResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
