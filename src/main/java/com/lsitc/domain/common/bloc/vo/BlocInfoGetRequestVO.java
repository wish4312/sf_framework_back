package com.lsitc.domain.common.bloc.vo;

import com.lsitc.domain.common.bloc.entity.BlocEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlocInfoGetRequestVO {

  @NotNull
  @PositiveOrZero(message = "사업장 ID에는 음수가 들어올 수 없습니다.")
  private final long id;

  public BlocEntity toEntity() {
    return BlocEntity.builder()
        .id(id)
        .build();
  }

  @Override
  public String toString() {
    return "BlocInfoGetRequestVO{" +
        "id=" + id +
        '}';
  }
}
