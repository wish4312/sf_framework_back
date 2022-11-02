package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.CodeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeListSearchRequestVO {

  private String commGrpCdId;

  public CodeEntity toEntity() {
    return CodeEntity.builder()
        .groupCodeId(Long.valueOf(commGrpCdId))
        .build();
  }

  @Override
  public String toString() {
    return "CodeListSearchRequestVO{" +
        "commGrpCdId='" + commGrpCdId + '\'' +
        '}';
  }
}
