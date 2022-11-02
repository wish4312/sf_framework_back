package com.lsitc.domain.common.code.controller;

import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.service.CodeService;
import com.lsitc.domain.common.code.vo.CodeAddRequestVO;
import com.lsitc.domain.common.code.vo.CodeAddResponseVO;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/common/code")
@RestController
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  @PostMapping
  public CodeAddResponseVO addCode(
      @RequestBody @Valid final List<CodeAddRequestVO> codeListAddRequestVO)
      throws CodeException {
    CodeAddResponseVO codeAddResponseVO = codeService.addCode(codeListAddRequestVO);
    log.info(codeAddResponseVO.toString());
    return codeAddResponseVO;
  }
}
