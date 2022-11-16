package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import com.lsitc.domain.model.BooleanState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupCodeListSearchRequestVO {

  private final String commGrpCd;
  private final String commGrpNm;
  private final int useFg;

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .code(commGrpCd)
        .name(commGrpNm)
        .isUsed(convertUseFg())
        .build();
  }

  private Boolean convertUseFg() {
    return BooleanState.of(String.valueOf(this.useFg)).getBooleanValue();
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
