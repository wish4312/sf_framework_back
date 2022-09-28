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
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
      throw new SampleException();
    }
    log.info(sampleInfoGetRequestVO.toString());
    SampleInfoGetResponseVO sampleInfoGetResponseVO = sampleService.getSampleInfo(
        sampleInfoGetRequestVO);
    log.info(sampleInfoGetResponseVO.toString());
    return sampleInfoGetResponseVO;
  }

  @GetMapping("/list")
  public List<SampleListGetResponseVO> getSampleList() {
    log.info("get 메소드가 호출되었습니다.");
    List<SampleListGetResponseVO> sampleListGetResponseVOList = sampleService.getSampleList();
    log.info(sampleListGetResponseVOList.toString());
    return sampleListGetResponseVOList;
  }

  @PostMapping("")
  public SampleAddResponseVO addSample(
      @RequestBody @Valid final SampleAddRequestVO sampleAddRequestVO) throws SampleException {
    log.info("post 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleAddRequestVO.getFoo())) {
      throw new SampleException();
    }
    log.info(sampleAddRequestVO.toString());
    SampleAddResponseVO sampleAddResponseVO = sampleService.addSample(sampleAddRequestVO);
    log.info(sampleAddResponseVO.toString());
    return sampleAddResponseVO;
  }

  @PutMapping("")
  public SampleModifyResponseVO modifySample(
      @RequestBody @Valid final SampleModifyRequestVO sampleModifyRequestVO)
      throws SampleException {
    log.info("put 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleModifyRequestVO.getFoo())) {
      throw new SampleException();
    }
    log.info(sampleModifyRequestVO.toString());
    SampleModifyResponseVO sampleModifyResponseVO = sampleService.modifySample(
        sampleModifyRequestVO);
    log.info(sampleModifyResponseVO.toString());
    return sampleModifyResponseVO;
  }

  @DeleteMapping("")
  public SampleRemoveResponseVO removeSample(
      @Valid final SampleRemoveRequestVO sampleRemoveRequestVO) throws SampleException {
    log.info("delete 메소드가 호출되었습니다.");
    if ("ERROR".equals(sampleRemoveRequestVO.getFoo())) {
      throw new SampleException();
    }
    log.info(sampleRemoveRequestVO.toString());
    SampleRemoveResponseVO sampleRemoveResponseVO = sampleService.removeSample(
        sampleRemoveRequestVO);
    log.info(sampleRemoveResponseVO.toString());
    return sampleRemoveResponseVO;
  }
}