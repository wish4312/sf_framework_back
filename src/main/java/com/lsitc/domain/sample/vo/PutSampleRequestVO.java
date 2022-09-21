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
public class PutSampleRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final long id;

  @NotBlank(message = "키에 빈값을 넣을 수 없습니다.")
  @Size(max = 5)
  private final String key;

  @NotBlank
  private final String value;

  private final String comment;

  public SampleEntity toEntity() {
    return SampleEntity.builder()
        .id(id)
        .sampleKey(key)
        .sampleValue(value)
        .comment(comment)
        .build();
  }

  @Override
  public String toString() {
    return "PutSampleRequestVO{" +
        "id=" + id +
        ", key='" + key + '\'' +
        ", value='" + value + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}