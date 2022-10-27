package com.lsitc.domain.common.role.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lsitc.domain.common.role.exception.RoleException;
import com.lsitc.domain.common.role.service.RoleService;
import com.lsitc.domain.common.role.vo.RoleListAddRequestVO;
import com.lsitc.domain.common.role.vo.RoleListAddResponseVO;
import com.lsitc.domain.common.role.vo.RoleListGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleListModifyRequestVO;
import com.lsitc.domain.common.role.vo.RoleListModifyResponseVO;
import com.lsitc.domain.common.role.vo.RoleListRemoveRequestVO;
import com.lsitc.domain.common.role.vo.RoleListRemoveResponseVO;
import com.lsitc.domain.common.role.vo.RoleSearchListGetRequestVO;
import com.lsitc.domain.common.role.vo.RoleSearchListGetResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/common/role")
@RestController
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/list")
  public List<RoleListGetResponseVO> getRoleList() throws RoleException {
    List<RoleListGetResponseVO> roleListGetResponseVOList = roleService.getRoleList();
    log.info(roleListGetResponseVOList.toString());
    return roleListGetResponseVOList;
  }

  @GetMapping("/search")
  public List<RoleSearchListGetResponseVO> searchRoleList(
      @Valid final RoleSearchListGetRequestVO deptInfoGetRequestVO) throws RoleException {
    List<RoleSearchListGetResponseVO> roleListGetResponseVOList =
        roleService.searchRoleList(deptInfoGetRequestVO);
    log.info(roleListGetResponseVOList.toString());
    return roleListGetResponseVOList;
  }

  @PostMapping("/list")
  public RoleListAddResponseVO addRoleList(
      @RequestBody @Valid final List<RoleListAddRequestVO> roleListAddRequestVO)
      throws RoleException {
    log.info(roleListAddRequestVO.toString());
    RoleListAddResponseVO roleListAddResponseVO = roleService.addRoleList(roleListAddRequestVO);
    log.info(roleListAddResponseVO.toString());
    return roleListAddResponseVO;
  }

  @PutMapping("/list")
  public RoleListModifyResponseVO modifyRoleList(
      @RequestBody @Valid final List<RoleListModifyRequestVO> roleListModifyRequestVO)
      throws RoleException {
    log.info(roleListModifyRequestVO.toString());
    RoleListModifyResponseVO roleListModifyResponseVO =
        roleService.modifyRoleList(roleListModifyRequestVO);
    log.info(roleListModifyResponseVO.toString());
    return roleListModifyResponseVO;
  }

  @DeleteMapping("/list")
  public RoleListRemoveResponseVO removeRoleList(
      @RequestBody @Valid final List<RoleListRemoveRequestVO> roleListRemoveRequestVO)
      throws RoleException {
    log.info(roleListRemoveRequestVO.toString());
    RoleListRemoveResponseVO roleListRemoveResponseVO =
        roleService.removeRoleList(roleListRemoveRequestVO);
    log.info(roleListRemoveResponseVO.toString());
    return roleListRemoveResponseVO;
  }

}
