package com.lsitc.domain.common.code.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import com.lsitc.domain.model.BooleanState;
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
  private GroupCodeListSearchResponseVO(String commGrpCdId, String commGrpCd, String commGrpNm,
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
        .useFg(convertBoolean(groupCodeEntity.getIsUsed()))
        .rmrk(groupCodeEntity.getRemark())
        .build();
  }

  private static String convertBoolean(Boolean booleanValue) {
    return BooleanState.of(booleanValue).getStringValue();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
