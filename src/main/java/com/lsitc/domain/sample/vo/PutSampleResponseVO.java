package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PutSampleResponseVO {

  private final String result;

  @Builder
  public PutSampleResponseVO(String result) {
    this.result = result;
  }

  public static PutSampleResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "PutSampleResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}