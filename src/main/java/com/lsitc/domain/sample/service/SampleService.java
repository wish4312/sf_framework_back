package com.lsitc.domain.sample.service;

import com.lsitc.domain.sample.dao.SampleDAO;
import com.lsitc.domain.sample.entity.SampleEntity;
import com.lsitc.domain.sample.vo.SampleAddRequestVO;
import com.lsitc.domain.sample.vo.SampleAddResponseVO;
import com.lsitc.domain.sample.vo.SampleInfoGetRequestVO;
import com.lsitc.domain.sample.vo.SampleInfoGetResponseVO;
import com.lsitc.domain.sample.vo.SampleListGetResponseVO;
import com.lsitc.domain.sample.vo.SampleModifyRequestVO;
import com.lsitc.domain.sample.vo.SampleModifyResponseVO;
import com.lsitc.domain.sample.vo.SampleRemoveRequestVO;
import com.lsitc.domain.sample.vo.SampleRemoveResponseVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SampleService {

  private final SampleDAO sampleDAO;

  public SampleInfoGetResponseVO getSampleInfo(
      final SampleInfoGetRequestVO sampleInfoGetRequestVO) {
    SampleEntity sampleEntity = sampleInfoGetRequestVO.toEntity();
    log.info(sampleEntity.toString());
    SampleEntity resultEntity = sampleDAO.selectSampleById(sampleEntity);
    return SampleInfoGetResponseVO.of(resultEntity);
  }

  public List<SampleListGetResponseVO> getSampleList() {
    List<SampleEntity> resultEntityList = sampleDAO.selectAll();
    return resultEntityList.stream().map(SampleListGetResponseVO::of).collect(Collectors.toList());
  }

  public SampleAddResponseVO addSample(final SampleAddRequestVO sampleAddRequestVO) {
    SampleEntity sampleEntity = sampleAddRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int addRows = sampleDAO.insertSample(sampleEntity);
    log.info("sample entity id: {}", sampleEntity.getId());
    return SampleAddResponseVO.of(addRows);
  }

  public SampleModifyResponseVO modifySample(final SampleModifyRequestVO sampleModifyRequestVO) {
    SampleEntity sampleEntity = sampleModifyRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int upsertRows = upsertSample(sampleEntity);
    log.info("sample entity id: {}", sampleEntity.getId());
    return SampleModifyResponseVO.of(upsertRows);
  }

  private int upsertSample(SampleEntity targetEntity) {
    SampleEntity sampleEntity = sampleDAO.selectSampleById(targetEntity);
    return sampleEntity != null ? sampleDAO.updateSampleById(targetEntity)
        : sampleDAO.insertSampleWithId(targetEntity);
  }

  public SampleRemoveResponseVO removeSample(SampleRemoveRequestVO sampleRemoveRequestVO) {
    SampleEntity sampleEntity = sampleRemoveRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int deleteRows = sampleDAO.deleteSampleById(sampleEntity);
    return SampleRemoveResponseVO.of(deleteRows);
  }
}