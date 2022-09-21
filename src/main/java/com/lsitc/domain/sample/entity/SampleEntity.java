package com.lsitc.domain.sample.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleEntity {

  private long id;
  private String sampleKey;
  private String sampleValue;
  private String comment;
  private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;

  @Builder
  public SampleEntity(long id, String sampleKey, String sampleValue, String comment) {
    this.id = id;
    this.sampleKey = sampleKey;
    this.sampleValue = sampleValue;
    this.comment = comment;
  }
}