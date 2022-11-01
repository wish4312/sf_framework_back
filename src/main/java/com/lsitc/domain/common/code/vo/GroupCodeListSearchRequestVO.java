package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupCodeListSearchRequestVO {

  private String commGrpCd;
  private String commGrpNm;
  private String useFg;

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .code(commGrpCd)
        .name(commGrpNm)
        .isUsed("1".equals(useFg))
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeListSearchRequestVO{" +
        "commGrpCd='" + commGrpCd + '\'' +
        ", commGrpNm='" + commGrpNm + '\'' +
        ", useFg='" + useFg + '\'' +
        '}';
  }
}
