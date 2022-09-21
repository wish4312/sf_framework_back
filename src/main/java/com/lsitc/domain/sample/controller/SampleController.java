package com.lsitc.domain.sample.controller;

import com.lsitc.domain.sample.exception.SampleException;
import com.lsitc.domain.sample.service.SampleService;
import com.lsitc.domain.sample.vo.GetSampleRequestVO;
import com.lsitc.domain.sample.vo.GetSampleResponseVO;
import com.lsitc.domain.sample.vo.PostSampleRequestVO;
import com.lsitc.domain.sample.vo.PostSampleResponseVO;
import com.lsitc.domain.sample.vo.PutSampleRequestVO;
import com.lsitc.domain.sample.vo.PutSampleResponseVO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  @GetMapping("/get-method")
  public GetSampleResponseVO sample(@Valid final GetSampleRequestVO getSampleRequestVO)
      throws SampleException {
    log.info("get 메소드가 호출되었습니다.");
    if ("ERROR".equals(getSampleRequestVO.getKey())) {
      throw new SampleException();
    }
    log.info(getSampleRequestVO.toString());
    GetSampleResponseVO getSampleResponseVO = sampleService.getSample(getSampleRequestVO);
    log.info(getSampleResponseVO.toString());
    return getSampleResponseVO;
  }

  @PostMapping("/post-method")
  public PostSampleResponseVO sample(
      @RequestBody @Valid final PostSampleRequestVO postSampleRequestVO)
      throws SampleException {
    log.info("post 메소드가 호출되었습니다.");
    if ("ERROR".equals(postSampleRequestVO.getKey())) {
      throw new SampleException();
    }
    log.info(postSampleRequestVO.toString());
    PostSampleResponseVO postSampleResponseVO = sampleService.postSample(postSampleRequestVO);
    log.info(postSampleResponseVO.toString());
    return postSampleResponseVO;
  }

  @PutMapping("/put-method")
  public PutSampleResponseVO sample(@RequestBody @Valid final PutSampleRequestVO putSampleRequestVO)
      throws SampleException {
    log.info("put 메소드가 호출되었습니다.");
    if ("ERROR".equals(putSampleRequestVO.getKey())) {
      throw new SampleException();
    }
    log.info(putSampleRequestVO.toString());
    PutSampleResponseVO putSampleResponseVO = sampleService.putSample(putSampleRequestVO);
    log.info(putSampleResponseVO.toString());
    return putSampleResponseVO;
  }
}