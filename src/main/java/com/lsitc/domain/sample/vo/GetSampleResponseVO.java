package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetSampleResponseVO {

  private final String foo;
  private final String bar;
  private final String comment;

  @Builder
  public GetSampleResponseVO(String foo, String bar, String comment) {
    this.foo = foo;
    this.bar = bar;
    this.comment = comment;
  }

  public static GetSampleResponseVO of(SampleEntity resultEntity) {
    return builder().foo(resultEntity.getFoo())
        .bar(resultEntity.getBar())
        .comment(resultEntity.getComment())
        .build();
  }

  @Override
  public String toString() {
    return "GetSampleResponseVO{" +
        "foo='" + foo + '\'' +
        ", bar='" + bar + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}