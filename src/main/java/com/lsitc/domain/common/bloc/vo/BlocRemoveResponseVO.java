package com.lsitc.domain.common.bloc.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlocRemoveResponseVO {

  private final String result;

  @Builder
  private BlocRemoveResponseVO(String result) {
    this.result = result;
  }

  public static BlocRemoveResponseVO of(int deleteRows) {
    String result = 0 < deleteRows ? "success" : "no data";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "BlocRemoveResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
