package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.CodeEntity;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeAddRequestVO {

  @NotBlank(message = "그룹 코드가 필요합니다.")
  private String commGrpCdId;
  @NotBlank(message = "코드가 필요합니다.")
  private String commCd;
  private String commCdNm;
  private String sortSeq;
  private String useFg;
  private String rmrk;

  public CodeEntity toEntity() {
    return CodeEntity.builder()
        .groupCodeId(Long.valueOf(commGrpCdId))
        .code(commCd)
        .name(commCdNm)
        .sortSequence(Long.valueOf(sortSeq))
        .isUsed("1".equals(useFg))
        .remark(rmrk)
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
