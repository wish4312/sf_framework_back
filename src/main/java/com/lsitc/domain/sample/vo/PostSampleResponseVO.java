package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSampleResponseVO {

  private final String result;

  @Builder
  public PostSampleResponseVO(String result) {
    this.result = result;
  }

  public static PostSampleResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "PostSampleResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}