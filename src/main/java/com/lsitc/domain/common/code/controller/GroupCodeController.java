package com.lsitc.domain.common.code.controller;

import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.service.CodeService;
import com.lsitc.domain.common.code.vo.GroupCodeAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListGetResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListModifyRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListModifyResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListRemoveRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListRemoveResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListSearchRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListSearchResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveResponseVO;
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
@RequestMapping("/common/group-code")
@RestController
@RequiredArgsConstructor
public class GroupCodeController {

  private final CodeService codeService;

  @GetMapping
  public GroupCodeInfoGetResponseVO getGroupCodeInfo(
      @Valid final GroupCodeInfoGetRequestVO groupCodeInfoGetRequestVO) throws CodeException {
    log.info(groupCodeInfoGetRequestVO.toString());
    GroupCodeInfoGetResponseVO groupCodeInfoGetResponseVO = codeService.getGroupCodeInfo(
        groupCodeInfoGetRequestVO);
    log.info(groupCodeInfoGetResponseVO.toString());
    return groupCodeInfoGetResponseVO;
  }

  @GetMapping("/list")
  public List<GroupCodeListGetResponseVO> getGroupCodeList() throws CodeException {
    List<GroupCodeListGetResponseVO> groupCodeListGetResponseVOList = codeService.getGroupCodeList();
    log.info(groupCodeListGetResponseVOList.toString());
    return groupCodeListGetResponseVOList;
  }

  @GetMapping("/search")
  public List<GroupCodeListSearchResponseVO> searchGroupCodeList(
      @Valid final GroupCodeListSearchRequestVO groupCodeListSearchRequestVO) throws CodeException {
    List<GroupCodeListSearchResponseVO> groupCodeListSearchGetResponseVO
        = codeService.searchGroupCodeList(groupCodeListSearchRequestVO);
    log.info(groupCodeListSearchGetResponseVO.toString());
    return groupCodeListSearchGetResponseVO;
  }

  @PostMapping
  public GroupCodeAddResponseVO addGroupCode(
      @RequestBody @Valid final GroupCodeAddRequestVO groupCodeAddRequestVO) throws CodeException {
    log.info(groupCodeAddRequestVO.toString());
    GroupCodeAddResponseVO groupCodeAddResponseVO = codeService.addGroupCode(groupCodeAddRequestVO);
    log.info(groupCodeAddResponseVO.toString());
    return groupCodeAddResponseVO;
  }

  @PostMapping("/list")
  public GroupCodeListAddResponseVO addGroupCodeList(
      @RequestBody @Valid final List<GroupCodeListAddRequestVO> groupCodeListAddRequestVOList)
      throws CodeException {
    GroupCodeListAddResponseVO groupCodeListAddResponseVO = codeService.addGroupCodeList(
        groupCodeListAddRequestVOList);
    log.info(groupCodeListAddResponseVO.toString());
    return groupCodeListAddResponseVO;
  }

  @PutMapping
  public GroupCodeModifyResponseVO modifyGroupCode(
      @RequestBody @Valid final GroupCodeModifyRequestVO groupCodeModifyRequestVO)
      throws CodeException {
    log.info(groupCodeModifyRequestVO.toString());
    GroupCodeModifyResponseVO groupCodeModifyResponseVO = codeService.modifyGroupCode(
        groupCodeModifyRequestVO);
    log.info(groupCodeModifyResponseVO.toString());
    return groupCodeModifyResponseVO;
  }

  @PutMapping("/list")
  public GroupCodeListModifyResponseVO modifyGroupCodeList(
      @RequestBody @Valid final List<GroupCodeListModifyRequestVO> groupCodeListModifyRequestVOList)
      throws CodeException {
    GroupCodeListModifyResponseVO groupCodeListModifyResponseVO = codeService.modifyGroupCodeList(
        groupCodeListModifyRequestVOList);
    log.info(groupCodeListModifyResponseVO.toString());
    return groupCodeListModifyResponseVO;
  }

  @DeleteMapping
  public GroupCodeRemoveResponseVO removeGroupCode(
      @RequestBody @Valid final GroupCodeRemoveRequestVO groupCodeRemoveRequestVO)
      throws CodeException {
    log.info(groupCodeRemoveRequestVO.toString());
    GroupCodeRemoveResponseVO groupCodeRemoveResponseVO = codeService.removeGroupCode(
        groupCodeRemoveRequestVO);
    log.info(groupCodeRemoveResponseVO.toString());
    return groupCodeRemoveResponseVO;
  }

  @DeleteMapping("/list")
  public GroupCodeListRemoveResponseVO removeGroupCodeList(
      @RequestBody @Valid final List<GroupCodeListRemoveRequestVO> groupCodeListRemoveRequestVOList)
      throws CodeException {
    GroupCodeListRemoveResponseVO groupCodeListRemoveResponseVO = codeService.removeGroupCodeList(
        groupCodeListRemoveRequestVOList);
    log.info(groupCodeListRemoveResponseVO.toString());
    return groupCodeListRemoveResponseVO;
  }
}
