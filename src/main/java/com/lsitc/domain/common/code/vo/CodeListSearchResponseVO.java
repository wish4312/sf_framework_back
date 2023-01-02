package com.lsitc.domain.common.code.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.code.entity.CodeEntity;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CodeListSearchResponseVO {

  private final String commCdId;
  private final String commGrpCdId;
  private final String commGrpCd;
  private final String commCd;
  private final String commCdNm;
  private final String sortSeq;
  private final String useFg;
  private final String rmrk;

  @Builder
  private CodeListSearchResponseVO(String commCdId, String commGrpCdId, String commGrpCd,
      String commCd, String commCdNm, String sortSeq, String useFg, String rmrk) {
    this.commCdId = commCdId;
    this.commGrpCdId = commGrpCdId;
    this.commGrpCd = commGrpCd;
    this.commCd = commCd;
    this.commCdNm = commCdNm;
    this.sortSeq = sortSeq;
    this.useFg = useFg;
    this.rmrk = rmrk;
  }

  public static CodeListSearchResponseVO of(CodeEntity codeEntity,
      GroupCodeEntity groupCodeEntity) {
    return builder()
        .commCdId(String.valueOf(codeEntity.getId()))
        .commCd(codeEntity.getCode())
        .commCdNm(codeEntity.getName())
        .commGrpCdId(String.valueOf(codeEntity.getGroupCodeId()))
        .commGrpCd(groupCodeEntity.getCode())
        .sortSeq(String.valueOf(codeEntity.getSortSequence()))
        .useFg(codeEntity.isUsed() ? "1" : "0")
        .rmrk(codeEntity.getRemark())
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
