package com.lsitc.domain.common.code.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeAddResponseVO {

  private final String result;

  @Builder
  private CodeAddResponseVO(String result) {
    this.result = result;
  }

  public static CodeAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "CodeAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
