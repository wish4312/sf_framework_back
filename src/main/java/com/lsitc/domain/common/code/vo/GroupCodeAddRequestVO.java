package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupCodeAddRequestVO {

  @NotBlank(message = "사업장명이 필요합니다.")
  private final String commGrpCd;

  private final String commGrpNm;

  private final String useFg;

  private String rmrk;

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .code(commGrpCd)
        .name(commGrpNm)
        .isUsed("Y".equals(useFg))
        .remark(rmrk)
        .build();
  }
}
