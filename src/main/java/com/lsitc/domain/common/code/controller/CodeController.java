package com.lsitc.domain.common.code.controller;

import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.service.CodeService;
import com.lsitc.domain.common.code.vo.GroupCodeAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListGetResponseVO;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/common")
@RestController
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  @GetMapping("/group-code")
  public GroupCodeInfoGetResponseVO getGroupCodeInfo(
      @Valid final GroupCodeInfoGetRequestVO groupCodeInfoGetRequestVO) throws CodeException {
    log.info(groupCodeInfoGetRequestVO.toString());
    GroupCodeInfoGetResponseVO groupCodeInfoGetResponseVO = codeService.getGroupCodeInfo(
        groupCodeInfoGetRequestVO);
    log.info(groupCodeInfoGetResponseVO.toString());
    return groupCodeInfoGetResponseVO;
  }

  @GetMapping("/group-code/list")
  public List<GroupCodeListGetResponseVO> getGroupCodeList() throws CodeException {
    List<GroupCodeListGetResponseVO> groupCodeListGetResponseVOList = codeService.getGroupCodeList();
    log.info(groupCodeListGetResponseVOList.toString());
    return groupCodeListGetResponseVOList;
  }

  @PostMapping("/group-code")
  public GroupCodeAddResponseVO addGroupCode(
      @RequestBody @Valid final GroupCodeAddRequestVO groupCodeAddRequestVO) throws CodeException {
    log.info(groupCodeAddRequestVO.toString());
    GroupCodeAddResponseVO groupCodeAddResponseVO = codeService.addGroupCode(groupCodeAddRequestVO);
    log.info(groupCodeAddResponseVO.toString());
    return groupCodeAddResponseVO;
  }
}
