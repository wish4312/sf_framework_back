package com.lsitc.domain.sample.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteSampleResponseVO {

  private final String result;

  @Builder
  private DeleteSampleResponseVO(String result) {
    this.result = result;
  }

  public static DeleteSampleResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "DeleteSampleResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
