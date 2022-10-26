package com.lsitc.domain.common.role.vo;

import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.role.entity.RoleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleInfoGetResponseVO {

  private final String roleId;
  private final String roleNm;
  private final String isDeleted;
  private final String rmrk;
  private final String regUserNo;
  private final String regDttm;
  private final String procUserNo;
  private final String procDttm;

  @Builder
  private RoleInfoGetResponseVO(String roleId, String roleNm, String isDeleted, String rmrk,
      String regUserNo, String regDttm, String procUserNo, String procDttm) {
    this.roleId = roleId;
    this.roleNm = roleNm;
    this.isDeleted = isDeleted;
    this.rmrk = rmrk;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }

  public static RoleInfoGetResponseVO of(RoleEntity roleInfo) {
    return builder()
        .roleId(String.valueOf(roleInfo.getId()))
        .roleNm(roleInfo.getName())
        .isDeleted((roleInfo.isDeleted() ? "1" : "0"))
        .rmrk(roleInfo.getRemark())
        .regUserNo(String.valueOf(roleInfo.getCreatedBy()))
        .regDttm(roleInfo.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .procUserNo(String.valueOf(roleInfo.getLastModifiedBy()))
        .procDttm(roleInfo.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
