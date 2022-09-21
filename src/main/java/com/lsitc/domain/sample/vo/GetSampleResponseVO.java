package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetSampleResponseVO {

  private final String key;
  private final String value;
  private final String comment;

  @Builder
  public GetSampleResponseVO(String key, String value, String comment) {
    this.key = key;
    this.value = value;
    this.comment = comment;
  }

  public static GetSampleResponseVO of(SampleEntity resultEntity) {
    return builder().key(resultEntity.getSampleKey())
        .value(resultEntity.getSampleValue())
        .comment(resultEntity.getComment())
        .build();
  }

  @Override
  public String toString() {
    return "GetSampleResponseVO{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}