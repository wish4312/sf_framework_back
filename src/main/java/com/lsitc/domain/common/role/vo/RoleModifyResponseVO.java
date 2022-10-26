package com.lsitc.domain.common.role.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleModifyResponseVO {

  private final String result;

  @Builder
  private RoleModifyResponseVO(String result) {
    this.result = result;
  }

  public static RoleModifyResponseVO of(int upsertRows) {
    String result = 0 < upsertRows ? "success" : "failure";
    return builder().result(result).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
