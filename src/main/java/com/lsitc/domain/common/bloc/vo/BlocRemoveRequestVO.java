package com.lsitc.domain.common.bloc.vo;

import com.lsitc.domain.common.bloc.entity.BlocEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlocRemoveRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final Long id;

  public BlocEntity toEntity() {
    return BlocEntity.builder()
        .id(id)
        .build();
  }

  @Override
  public String toString() {
    return "BlocRemoveRequestVO{" +
        "id=" + id +
        '}';
  }
}
