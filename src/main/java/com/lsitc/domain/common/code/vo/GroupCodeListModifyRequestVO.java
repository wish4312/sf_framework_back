package com.lsitc.domain.common.code.vo;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupCodeListModifyRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final String commGrpCdId;

  @NotBlank(message = "공통 코드가 필요합니다.")
  private final String commGrpCd;

  private final String commGrpNm;

  private final String useFg;

  private String rmrk;

  public GroupCodeEntity toEntity() {
    return GroupCodeEntity.builder()
        .id(Long.valueOf(commGrpCdId))
        .code(commGrpCd)
        .name(commGrpNm)
        .isUsed("1".equals(useFg))
        .remark(rmrk)
        .build();
  }

  @Override
  public String toString() {
    return "GroupCodeListModifyRequestVO{" +
        "commGrpCdId='" + commGrpCdId + '\'' +
        ", commGrpCd='" + commGrpCd + '\'' +
        ", commGrpNm='" + commGrpNm + '\'' +
        ", useFg='" + useFg + '\'' +
        ", rmrk='" + rmrk + '\'' +
        '}';
  }
}
