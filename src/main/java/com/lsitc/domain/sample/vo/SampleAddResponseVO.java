package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SampleAddResponseVO {

  private final String result;

  @Builder
  private SampleAddResponseVO(String result) {
    this.result = result;
  }

  public static SampleAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "SampleAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}