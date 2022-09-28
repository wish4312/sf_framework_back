package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SampleRemoveResponseVO {

  private final String result;

  @Builder
  private SampleRemoveResponseVO(String result) {
    this.result = result;
  }

  public static SampleRemoveResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "SampleRemoveResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
