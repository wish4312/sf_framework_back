package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SampleModifyResponseVO {

  private final String result;

  @Builder
  private SampleModifyResponseVO(String result) {
    this.result = result;
  }

  public static SampleModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "SampleModifyResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}