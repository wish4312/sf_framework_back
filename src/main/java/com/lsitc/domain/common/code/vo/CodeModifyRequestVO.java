package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.CodeEntity;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeModifyRequestVO {

  @NotBlank(message = "코드 ID는 필수값 입니다.")
  private final String commCdId;
  @NotBlank(message = "그룹코드 ID는 필수값 입니다.")
  private final String commGrpCdId;
  @NotBlank(message = "코드는 필수값 입니다.")
  private final String commCd;
  @NotBlank(message = "코드명은 필수값 입니다.")
  private final String commCdNm;
  private final String sortSeq;
  private final String useFg;
  private final String rmrk;

  public CodeEntity toEntity() {
    return CodeEntity.builder()
        .id(Long.valueOf(commCdId))
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
    return "CodeModifyRequestVO{" +
        "commCdId='" + commCdId + '\'' +
        ", commGrpCdId='" + commGrpCdId + '\'' +
        ", commCd='" + commCd + '\'' +
        ", commCdNm='" + commCdNm + '\'' +
        ", sortSeq='" + sortSeq + '\'' +
        ", useFg='" + useFg + '\'' +
        ", rmrk='" + rmrk + '\'' +
        '}';
  }
}
