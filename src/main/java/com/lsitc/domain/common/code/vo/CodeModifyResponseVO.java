package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeModifyResponseVO {

  private final String result;

  @Builder
  private CodeModifyResponseVO(String result) {
    this.result = result;
  }

  public static CodeModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "CodeModifyResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
