package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSampleRequestVO {

  @NotBlank(message = "키에 빈값을 넣을 수 없습니다.")
  @Size(max = 5)
  private final String key;

  @NotBlank
  private final String value;

  private final String comment;

  public SampleEntity toEntity() {
    return SampleEntity.builder()
        .sampleKey(key)
        .sampleValue(value)
        .comment(comment)
        .build();
  }

  @Override
  public String toString() {
    return "PostSampleRequestVO{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}