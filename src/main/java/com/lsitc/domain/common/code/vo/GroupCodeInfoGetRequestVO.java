package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupCodeInfoGetRequestVO {

  @NotNull
  @PositiveOrZero(message = "그룹코드 ID에는 음수가 들어올 수 없습니다.")
  private final Long commGrpCdId;

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .id(commGrpCdId)
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeInfoGetRequestVO{" +
        "commGrpCdId='" + commGrpCdId + '\'' +
        '}';
  }
}
