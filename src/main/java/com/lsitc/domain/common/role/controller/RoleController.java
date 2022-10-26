package com.lsitc.domain.common.role.controller;

import com.lsitc.domain.common.role.exception.RoleException;
import com.lsitc.domain.common.role.service.RoleService;
import com.lsitc.domain.common.role.vo.RoleAddRequestVO;
import com.lsitc.domain.common.role.vo.RoleAddResponseVO;
import com.lsitc.domain.common.role.vo.RoleInfoGetRequestVO;
import com.lsitc.domain.common.role.vo.RoleInfoGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleListGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleModifyRequestVO;
import com.lsitc.domain.common.role.vo.RoleModifyResponseVO;
import com.lsitc.domain.common.role.vo.RoleRemoveRequestVO;
import com.lsitc.domain.common.role.vo.RoleRemoveResponseVO;
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
@RequestMapping("/common/role")
@RestController
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/info")
  public RoleInfoGetResponseVO getRoleInfo(@Valid final RoleInfoGetRequestVO roleInfoGetRequestVO)
      throws RoleException {
    log.info(roleInfoGetRequestVO.toString());
    RoleInfoGetResponseVO roleInfoGetResponseVO = roleService.getRoleInfo(roleInfoGetRequestVO);
    log.info(roleInfoGetResponseVO.toString());
    return roleInfoGetResponseVO;
  }

  @GetMapping("/list")
  public List<RoleListGetResponseVO> getRoleList() throws RoleException {
    List<RoleListGetResponseVO> roleListGetResponseVOList = roleService.getRoleList();
    log.info(roleListGetResponseVOList.toString());
    return roleListGetResponseVOList;
  }

  @PostMapping
  public RoleAddResponseVO addRole(@RequestBody @Valid final RoleAddRequestVO roleAddRequestVO)
      throws RoleException {
    log.info(roleAddRequestVO.toString());
    RoleAddResponseVO roleAddResponseVO = roleService.addRole(roleAddRequestVO);
    log.info(roleAddResponseVO.toString());
    return roleAddResponseVO;
  }

  @PutMapping
  public RoleModifyResponseVO modifyRole(
      @RequestBody @Valid final RoleModifyRequestVO roleModifyRequestVO) throws RoleException {
    log.info(roleModifyRequestVO.toString());
    RoleModifyResponseVO roleModifyResponseVO = roleService.modifyRole(roleModifyRequestVO);
    log.info(roleModifyResponseVO.toString());
    return roleModifyResponseVO;
  }

  @DeleteMapping
  public RoleRemoveResponseVO removeRole(@Valid final RoleRemoveRequestVO roleRemoveRequestVO)
      throws RoleException {
    log.info(roleRemoveRequestVO.toString());
    RoleRemoveResponseVO roleRemoveResponseVO = roleService.removeRole(roleRemoveRequestVO);
    log.info(roleRemoveResponseVO.toString());
    return roleRemoveResponseVO;
  }
}
