package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeListModifyResponseVO {

  private final String result;

  @Builder
  private GroupCodeListModifyResponseVO(String result) {
    this.result = result;
  }

  public static GroupCodeListModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "GroupCodeListModifyResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
