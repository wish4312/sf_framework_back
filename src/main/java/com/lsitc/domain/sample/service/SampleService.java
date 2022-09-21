package com.lsitc.domain.sample.service;

import com.lsitc.domain.sample.dao.SampleDAO;
import com.lsitc.domain.sample.entity.SampleEntity;
import com.lsitc.domain.sample.vo.GetSampleRequestVO;
import com.lsitc.domain.sample.vo.GetSampleResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SampleService {

  private final SampleDAO sampleDAO;

  public GetSampleResponseVO getSample(final GetSampleRequestVO getSampleRequestVO) {
    SampleEntity sampleEntity = getSampleRequestVO.toEntity();
    log.info(sampleEntity.toString());
    SampleEntity resultEntity = sampleDAO.selectSampleById(sampleEntity);
    return GetSampleResponseVO.of(resultEntity);
  }
}