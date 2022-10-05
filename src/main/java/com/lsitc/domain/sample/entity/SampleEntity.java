package com.lsitc.domain.sample.entity;

import com.lsitc.global.common.BaseAbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleEntity extends BaseAbstractEntity {

  private Long id;
  private String foo;
  private String bar;
  private String comment;

  @Builder
  private SampleEntity(long id, String foo, String bar, String comment) {
    this.id = id;
    this.foo = foo;
    this.bar = bar;
    this.comment = comment;
  }
}