package com.lsitc.domain.common.code.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GroupCodeListRemoveRequestVO {

  @NotNull
  private final String commGrpCdId;

  @JsonCreator
  public GroupCodeListRemoveRequestVO(String commGrpCdId) {
    this.commGrpCdId = commGrpCdId;
  }

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .id(Long.valueOf(commGrpCdId))
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeListRemoveRequestVO{" +
        "id=" + commGrpCdId +
        '}';
  }
}
