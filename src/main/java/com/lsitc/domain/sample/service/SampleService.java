package com.lsitc.domain.sample.service;

import com.lsitc.domain.sample.dao.SampleDAO;
import com.lsitc.domain.sample.entity.SampleEntity;
import com.lsitc.domain.sample.vo.GetSampleRequestVO;
import com.lsitc.domain.sample.vo.GetSampleResponseVO;
import com.lsitc.domain.sample.vo.PostSampleRequestVO;
import com.lsitc.domain.sample.vo.PostSampleResponseVO;
import com.lsitc.domain.sample.vo.PutSampleRequestVO;
import com.lsitc.domain.sample.vo.PutSampleResponseVO;
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

  public PostSampleResponseVO postSample(final PostSampleRequestVO postSampleRequestVO) {
    SampleEntity sampleEntity = postSampleRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int addRows = sampleDAO.insertSample(sampleEntity);
    log.info("sample entity id: {}", sampleEntity.getId());
    return PostSampleResponseVO.of(addRows);
  }

  public PutSampleResponseVO putSample(final PutSampleRequestVO putSampleRequestVO) {
    SampleEntity sampleEntity = putSampleRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int upsertRows = upsertSample(sampleEntity);
    log.info("sample entity id: {}", sampleEntity.getId());
    return PutSampleResponseVO.of(upsertRows);
  }

  private int upsertSample(SampleEntity targetEntity) {
    SampleEntity sampleEntity = sampleDAO.selectSampleById(targetEntity);
    return sampleEntity != null ? sampleDAO.updateSampleById(targetEntity)
        : sampleDAO.insertSampleWithId(targetEntity);
  }
}