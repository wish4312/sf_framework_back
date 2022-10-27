package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupCodeInfoGetResponseVO {

  private final String commGrpCdId;
  private final String commGrpCd;
  private final String commGrpNm;
  private final String useFg;
  private final String rmrk;

  @Builder
  public GroupCodeInfoGetResponseVO(String commGrpCdId, String commGrpCd, String commGrpNm,
      String useFg,
      String rmrk) {
    this.commGrpCdId = commGrpCdId;
    this.commGrpCd = commGrpCd;
    this.commGrpNm = commGrpNm;
    this.useFg = useFg;
    this.rmrk = rmrk;
  }

  public static GroupCodeInfoGetResponseVO of(GroupCodeEntity groupCodeEntity) {
    return builder()
        .commGrpCdId(String.valueOf(groupCodeEntity.getId()))
        .commGrpCd(String.valueOf(groupCodeEntity.getCode()))
        .commGrpNm(groupCodeEntity.getName())
        .useFg(groupCodeEntity.isUsed()?"1":"0")
        .rmrk(groupCodeEntity.getRemark())
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeInfoGetResponseVO{" +
        "commGrpCdId='" + commGrpCdId + '\'' +
        ", commGrpCd='" + commGrpCd + '\'' +
        ", commGrpNm='" + commGrpNm + '\'' +
        ", useFg='" + useFg + '\'' +
        ", rmrk='" + rmrk + '\'' +
        '}';
  }
}
