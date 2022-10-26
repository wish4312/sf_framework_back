package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeListGetResponseVO {

  private final String commGrpCdId;
  private final String commGrpCd;
  private final String commGrpNm;
  private final String useFg;
  private final String rmrk;

  @Builder
  public GroupCodeListGetResponseVO(String commGrpCdId, String commGrpCd, String commGrpNm,
      String useFg, String rmrk) {
    this.commGrpCdId = commGrpCdId;
    this.commGrpCd = commGrpCd;
    this.commGrpNm = commGrpNm;
    this.useFg = useFg;
    this.rmrk = rmrk;
  }

  public static GroupCodeListGetResponseVO of(GroupCodeEntity groupCodeEntity) {
    return builder()
        .commGrpCdId(String.valueOf(groupCodeEntity.getId()))
        .commGrpCd(String.valueOf(groupCodeEntity.getCode()))
        .commGrpNm(groupCodeEntity.getName())
        .useFg(groupCodeEntity.isUsed()?"Y":"N")
        .rmrk(groupCodeEntity.getRemark())
        .build();
  }
}
