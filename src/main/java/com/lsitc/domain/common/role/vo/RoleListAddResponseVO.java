package com.lsitc.domain.common.role.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleListAddResponseVO {

  private final String result;

  @Builder
  private RoleListAddResponseVO(String result) {
    this.result = result;
  }

  public static RoleListAddResponseVO of(final int tryRow, final int addRows) {
    String result = tryRow == addRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
