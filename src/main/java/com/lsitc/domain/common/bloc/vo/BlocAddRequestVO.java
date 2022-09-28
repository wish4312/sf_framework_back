package com.lsitc.domain.common.bloc.vo;

import com.lsitc.domain.common.bloc.entity.BlocEntity;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlocAddRequestVO {

  @NotBlank(message = "사업장명이 필요합니다.")
  private final String name;

  private final String remark;

  public BlocEntity toEntity() {
    return BlocEntity.builder()
        .name(name)
        .remark(remark)
        .build();
  }

  @Override
  public String toString() {
    return "BlocAddRequestVO{" +
        "name='" + name + '\'' +
        ", remark='" + remark + '\'' +
        '}';
  }
}
