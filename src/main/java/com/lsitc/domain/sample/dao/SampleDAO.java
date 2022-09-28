package com.lsitc.domain.sample.dao;

import com.lsitc.domain.sample.entity.SampleEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleDAO {

  SampleEntity selectSampleById(SampleEntity sampleEntity);

  List<SampleEntity> selectAll();

  int insertSample(SampleEntity sampleEntity);

  int updateSampleById(SampleEntity sampleEntity);

  int insertSampleWithId(SampleEntity sampleEntity);

  int deleteSampleById(SampleEntity sampleEntity);
}