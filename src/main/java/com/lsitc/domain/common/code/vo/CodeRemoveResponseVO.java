package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeRemoveResponseVO {

  private final String result;

  @Builder
  private CodeRemoveResponseVO(String result) {
    this.result = result;
  }

  public static CodeRemoveResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "CodeRemoveResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }

}
