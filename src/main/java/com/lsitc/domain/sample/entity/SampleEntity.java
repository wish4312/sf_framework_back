package com.lsitc.domain.sample.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleEntity {

  private Long id;
  private String foo;
  private String bar;
  private String comment;
  private Long createdBy;
  private LocalDateTime createdDate;
  private Long lastModifiedBy;
  private LocalDateTime lastModifiedDate;

  @Builder
  private SampleEntity(long id, String foo, String bar, String comment) {
    this.id = id;
    this.foo = foo;
    this.bar = bar;
    this.comment = comment;
  }
}