package com.lsitc.domain.common.bloc.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlocAddResponseVO {

  private final String result;

  @Builder
  private BlocAddResponseVO(String result) {
    this.result = result;
  }

  public static BlocAddResponseVO of(final int addRows) {
    String result = 0 < addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "BlocAddResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
