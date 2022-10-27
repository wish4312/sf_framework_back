package com.lsitc.domain.common.role.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.role.entity.RoleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleListGetResponseVO {

  private final String roleId;
  private final String roleNm;
  private final String isDeleted;
  private final String deletedBy;
  private final String deletedDate;
  private final String rmrk;
  private final String regUserNo;
  private final String regDttm;
  private final String procUserNo;
  private final String procDttm;

  @Builder
  private RoleListGetResponseVO(String roleId, String roleNm, String isDeleted, String deletedBy,
      String deletedDate, String rmrk, String regUserNo, String regDttm, String procUserNo,
      String procDttm) {
    this.roleId = roleId;
    this.roleNm = roleNm;
    this.isDeleted = isDeleted;
    this.deletedBy = deletedBy;
    this.deletedDate = deletedDate;
    this.rmrk = rmrk;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }

  public static RoleListGetResponseVO of(RoleEntity roleInfo) {
    return builder()
        .roleId(String.valueOf(roleInfo.getId()))
        .roleNm(roleInfo.getName())
        .isDeleted((roleInfo.isDeleted() ? "1" : "0"))
        .deletedBy(String.valueOf(roleInfo.getDeletedBy()))
        .deletedDate(String.valueOf(roleInfo.getDeletedDate()))
        .rmrk(roleInfo.getRemark())
        .regUserNo(String.valueOf(roleInfo.getCreatedBy()))
        .regDttm(String.valueOf(roleInfo.getCreatedDate()))
        .procUserNo(String.valueOf(roleInfo.getLastModifiedBy()))
        .procDttm(String.valueOf(roleInfo.getLastModifiedDate()))
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
