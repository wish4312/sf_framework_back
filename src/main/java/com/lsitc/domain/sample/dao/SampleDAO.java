package com.lsitc.domain.sample.dao;

import com.lsitc.domain.sample.entity.SampleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleDAO {

  SampleEntity selectSampleById(SampleEntity sampleEntity);

  int insertSample(SampleEntity sampleEntity);
}