package com.lsitc.domain.common.code.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GroupCodeRemoveRequestVO {

  @NotNull
  private final String id;

  @JsonCreator
  public GroupCodeRemoveRequestVO(String id) {
    this.id = id;
  }

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .id(Long.valueOf(id))
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeRemoveRequestVO{" +
        "id=" + id +
        '}';
  }
}
