package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeListSearchResponseVO {

  private final String commGrpCdId;
  private final String commGrpCd;
  private final String commGrpNm;
  private final String useFg;
  private final String rmrk;

  @Builder
  public GroupCodeListSearchResponseVO(String commGrpCdId, String commGrpCd, String commGrpNm,
      String useFg, String rmrk) {
    this.commGrpCdId = commGrpCdId;
    this.commGrpCd = commGrpCd;
    this.commGrpNm = commGrpNm;
    this.useFg = useFg;
    this.rmrk = rmrk;
  }

  public static GroupCodeListSearchResponseVO of(GroupCodeEntity groupCodeEntity) {
    return builder()
        .commGrpCdId(String.valueOf(groupCodeEntity.getId()))
        .commGrpCd(String.valueOf(groupCodeEntity.getCode()))
        .commGrpNm(groupCodeEntity.getName())
        .useFg(groupCodeEntity.isUsed()?"1":"0")
        .rmrk(groupCodeEntity.getRemark())
        .build();
  }
}
