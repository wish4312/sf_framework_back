package com.lsitc.domain.common.code.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GroupCodeRemoveRequestVO {

  @NotNull
  private final String commGrpCdIdid;

  @JsonCreator
  public GroupCodeRemoveRequestVO(String commGrpCdIdid) {
    this.commGrpCdIdid = commGrpCdIdid;
  }

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .id(Long.valueOf(commGrpCdIdid))
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeRemoveRequestVO{" +
        "id=" + commGrpCdIdid +
        '}';
  }
}
