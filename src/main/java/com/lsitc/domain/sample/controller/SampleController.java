package com.lsitc.domain.sample.controller;

import com.lsitc.domain.sample.exception.SampleException;
import com.lsitc.domain.sample.service.SampleService;
import com.lsitc.domain.sample.vo.SampleAddRequestVO;
import com.lsitc.domain.sample.vo.SampleAddResponseVO;
import com.lsitc.domain.sample.vo.SampleInfoGetRequestVO;
import com.lsitc.domain.sample.vo.SampleInfoGetResponseVO;
import com.lsitc.domain.sample.vo.SampleListGetResponseVO;
import com.lsitc.domain.sample.vo.SampleModifyRequestVO;
import com.lsitc.domain.sample.vo.SampleModifyResponseVO;
import com.lsitc.domain.sample.vo.SampleRemoveRequestVO;
import com.lsitc.domain.sample.vo.SampleRemoveResponseVO;
import com.lsitc.global.error.exception.ErrorCode;
import com.lsitc.global.paging.Pageable;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Slf4j
@RequestMapping("/sample")
@RestController
@RequiredArgsConstructor
public class SampleController {

  private final SampleService sampleService;

  @GetMapping("/info")
  public SampleInfoGetResponseVO getSampleInfo(
      @Valid final SampleInfoGetRequestVO sampleInfoGetRequestVO) throws SampleException {
    log.info("get 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleInfoGetRequestVO.getFoo())) {
      throw new SampleException("오류발생", ErrorCode.INTERNAL_SERVER_ERROR);
    }
    log.info(sampleInfoGetRequestVO.toString());
    SampleInfoGetResponseVO sampleInfoGetResponseVO = sampleService.getSampleInfo(
        sampleInfoGetRequestVO);
    log.info(sampleInfoGetResponseVO.toString());
    return sampleInfoGetResponseVO;
  }

  @GetMapping("/list")
  public List<SampleListGetResponseVO> getSampleList(@Valid final Pageable pageable) {
    log.info("get 메소드가 호출되었습니다.");
    List<SampleListGetResponseVO> sampleListGetResponseVOList = sampleService.getSampleList(
        pageable);
    log.info(sampleListGetResponseVOList.toString());
    return sampleListGetResponseVOList;
  }

  @PostMapping
  public SampleAddResponseVO addSample(
      @RequestBody @Valid final List<SampleAddRequestVO> sampleAddRequestVO)
      throws SampleException {
    log.info("post 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleAddRequestVO.get(0).getFoo())) {
      throw new SampleException("오류발생", ErrorCode.INTERNAL_SERVER_ERROR);
    }
    log.info(sampleAddRequestVO.toString());
    SampleAddResponseVO sampleAddResponseVO = sampleService.addSample(sampleAddRequestVO);
    log.info(sampleAddResponseVO.toString());
    return sampleAddResponseVO;
  }

  @PutMapping
  public SampleModifyResponseVO modifySample(
      @RequestBody @Valid final List<SampleModifyRequestVO> sampleModifyRequestVO)
      throws SampleException {
    log.info("put 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleModifyRequestVO.get(0).getFoo())) {
      throw new SampleException("오류발생", ErrorCode.INTERNAL_SERVER_ERROR);
    }
    log.info(sampleModifyRequestVO.toString());
    SampleModifyResponseVO sampleModifyResponseVO = sampleService.modifySample(
        sampleModifyRequestVO);
    log.info(sampleModifyResponseVO.toString());
    return sampleModifyResponseVO;
  }

  @DeleteMapping("/query-string")
  public SampleRemoveResponseVO removeSampleWithQueryString(
      @Valid final SampleRemoveRequestVO sampleRemoveRequestVO) throws SampleException {
    log.info("delete 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleRemoveRequestVO.getFoo())) {
      throw new SampleException("오류발생", ErrorCode.INTERNAL_SERVER_ERROR);
    }
    log.info(sampleRemoveRequestVO.toString());
    SampleRemoveResponseVO sampleRemoveResponseVO = sampleService.removeSample(
        sampleRemoveRequestVO);
    log.info(sampleRemoveResponseVO.toString());
    return sampleRemoveResponseVO;
  }

  @DeleteMapping
  public SampleRemoveResponseVO removeSample(
      @RequestBody @Valid final List<SampleRemoveRequestVO> sampleRemoveRequestVO)
      throws SampleException {
    log.info("delete 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleRemoveRequestVO.get(0).getFoo())) {
      throw new SampleException("오류발생", ErrorCode.INTERNAL_SERVER_ERROR);
    }
    log.info(sampleRemoveRequestVO.toString());
    SampleRemoveResponseVO sampleRemoveResponseVO = sampleService.removeSample(
        sampleRemoveRequestVO);
    log.info(sampleRemoveResponseVO.toString());
    return sampleRemoveResponseVO;
  }

  @PostMapping("/files-upload")
  public void uploadSampleFiles(MultipartFile[] file) {
    sampleService.uploadSampleFiles(file);
  }

  @GetMapping("/files-download")
  public ResponseEntity<StreamingResponseBody> downloadSampleFiles(
      @RequestParam List<String> filename) {

    File file = sampleService.downloadSampleFiles(filename);
    StreamingResponseBody responseBody = outputStream -> {
      Files.copy(file.toPath(), outputStream);
    };
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(file.length())
        .body(responseBody);
  }
}