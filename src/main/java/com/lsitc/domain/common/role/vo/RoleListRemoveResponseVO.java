package com.lsitc.domain.common.role.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleListRemoveResponseVO {

  private final String result;

  @Builder
  private RoleListRemoveResponseVO(String result) {
    this.result = result;
  }

  public static RoleListRemoveResponseVO of(final int deleteRows) {
    String result = 0 < deleteRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
