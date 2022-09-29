package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SampleInfoGetRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final Long id;

  @NotBlank(message = "foo에 빈값이 들어왔습니다.")
  @Size(max = 5)
  private final String foo;

  public SampleEntity toEntity() {
    return SampleEntity.builder()
        .id(id)
        .foo(foo)
        .build();
  }

  @Override
  public String toString() {
    return "SampleInfoGetRequestVO{" +
        "id=" + id +
        ", foo='" + foo + '\'' +
        '}';
  }
}