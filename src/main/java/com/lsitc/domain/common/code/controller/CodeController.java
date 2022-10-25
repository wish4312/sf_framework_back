package com.lsitc.domain.common.code.controller;

import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.service.CodeService;
import com.lsitc.domain.common.code.vo.GroupCodeListGetResponseVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/common")
@RestController
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  @GetMapping("/group-code/list")
  public List<GroupCodeListGetResponseVO> getGroupCodeList() throws CodeException {
    List<GroupCodeListGetResponseVO> groupCodeListGetResponseVOList = codeService.getGroupCodeList();
    log.info(groupCodeListGetResponseVOList.toString());
    return groupCodeListGetResponseVOList;
  }
}
