package com.lsitc.domain.common.bloc.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlocModifyResponseVO {

  private final String result;

  @Builder
  private BlocModifyResponseVO(String result) {
    this.result = result;
  }

  public static BlocModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return "BlocModifyResponseVO{" +
        "result='" + result + '\'' +
        '}';
  }
}
