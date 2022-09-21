package com.lsitc.domain.sample.vo;

import com.lsitc.domain.sample.entity.SampleEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteSampleRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final long id;

  private final String key;

  public SampleEntity toEntity() {
    return SampleEntity.builder()
        .id(id)
        .sampleKey(key)
        .build();
  }

  @Override
  public String toString() {
    return "GetSampleRequestVO{" +
        "id=" + id +
        ", key='" + key + '\'' +
        '}';
  }
}
