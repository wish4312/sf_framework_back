package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SampleAddRequestVO {

  @NotBlank(message = "foo에 빈값을 넣을 수 없습니다.")
  @Size(max = 5)
  private final String foo;

  @NotBlank
  private final String bar;

  private final String comment;

  public SampleEntity toEntity() {
    return SampleEntity.builder()
        .foo(foo)
        .bar(bar)
        .comment(comment)
        .build();
  }

  @Override
  public String toString() {
    return "SampleAddRequestVO{" +
        "foo='" + foo + '\'' +
        ", bar='" + bar + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}