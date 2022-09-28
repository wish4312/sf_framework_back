package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SampleInfoGetResponseVO {

  private final String foo;
  private final String bar;
  private final String comment;

  @Builder
  private SampleInfoGetResponseVO(String foo, String bar, String comment) {
    this.foo = foo;
    this.bar = bar;
    this.comment = comment;
  }

  public static SampleInfoGetResponseVO of(SampleEntity resultEntity) {
    return builder().foo(resultEntity.getFoo())
        .bar(resultEntity.getBar())
        .comment(resultEntity.getComment())
        .build();
  }

  @Override
  public String toString() {
    return "SampleInfoGetResponseVO{" +
        "foo='" + foo + '\'' +
        ", bar='" + bar + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}