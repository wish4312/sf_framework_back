package com.lsitc.domain.sample.service;

import com.lsitc.domain.sample.dao.SampleDAO;
import com.lsitc.domain.sample.entity.SampleEntity;
import com.lsitc.domain.sample.exception.SampleException;
import com.lsitc.domain.sample.vo.SampleAddRequestVO;
import com.lsitc.domain.sample.vo.SampleAddResponseVO;
import com.lsitc.domain.sample.vo.SampleInfoGetRequestVO;
import com.lsitc.domain.sample.vo.SampleInfoGetResponseVO;
import com.lsitc.domain.sample.vo.SampleListGetResponseVO;
import com.lsitc.domain.sample.vo.SampleModifyRequestVO;
import com.lsitc.domain.sample.vo.SampleModifyResponseVO;
import com.lsitc.domain.sample.vo.SampleRemoveRequestVO;
import com.lsitc.domain.sample.vo.SampleRemoveResponseVO;
import com.lsitc.global.paging.Pageable;
import com.lsitc.global.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

  public List<SampleListGetResponseVO> getSampleList(final Pageable pageable) {
    List<SampleEntity> resultEntityList = sampleDAO.selectAll(pageable);
    return resultEntityList.stream().map(SampleListGetResponseVO::of).collect(Collectors.toList());
  }

  @Transactional
  public SampleAddResponseVO addSample(final SampleAddRequestVO sampleAddRequestVO) {
    SampleEntity sampleEntity = sampleAddRequestVO.toEntity();
    log.info(sampleEntity.toString());
    int addRows = sampleDAO.insertSample(sampleEntity);
    log.info("sample entity id: {}", sampleEntity.getId());
    return SampleAddResponseVO.of(addRows);
  }

  @Transactional
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

  @Transactional
  public SampleRemoveResponseVO removeSample(SampleRemoveRequestVO sampleRemoveRequestVO) {
    SampleEntity sampleEntity = sampleRemoveRequestVO.toEntity();
    log.info(sampleEntity.toString());
//    int deleteRows = hardDeleteSample(sampleEntity);
    int deleteRows = softDeleteSample(sampleEntity);
    return SampleRemoveResponseVO.of(deleteRows);
  }

  private int hardDeleteSample(SampleEntity targetEntity) {
    return sampleDAO.deleteSampleById(targetEntity);
  }

  private int softDeleteSample(SampleEntity targetEntity) {
    SampleEntity sampleEntity = sampleDAO.selectSampleById(targetEntity);
    if (sampleEntity == null) {
      throw new SampleException("sampleEntity is null");
    }
    sampleEntity.delete();
    log.info(sampleEntity.toString());
    return sampleDAO.updateSampleIsDeletedById(sampleEntity);
  }

  public void uploadSampleFiles(MultipartFile[] files) {
    for (MultipartFile file : files) {
      uploadSampleFile(file);
    }
  }

  public void uploadSampleFile(MultipartFile file) {
    FileUtils fileUtils = new FileUtils();
    String originalFilename = getOriginalFilename(file);
    String filename = UUID.randomUUID().toString();
    String extension = FileUtils.getExtension(originalFilename);

    if (!fileUtils.isValidFile(file)) {
      throw new SampleException("This file is invalid.");
    }

    try {
      File targetFile = new File(FileUtils.FILE_TMP_PATH + File.separator + filename);
      FileUtils.touch(targetFile);
      file.transferTo(targetFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String getOriginalFilename(MultipartFile file) {
    return Optional.ofNullable(file.getOriginalFilename()).filter(StringUtils::isNotBlank)
        .orElseThrow(() -> new SampleException("Filename is empty"));
  }
}