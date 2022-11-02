package com.lsitc.domain.common.menu.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lsitc.domain.common.menu.exception.MenuException;
import com.lsitc.domain.common.menu.service.MenuService;
import com.lsitc.domain.common.menu.vo.MenuAddRequestVO;
import com.lsitc.domain.common.menu.vo.MenuAddResponseVO;
import com.lsitc.domain.common.menu.vo.MenuListGetResponseVO;
import com.lsitc.domain.common.menu.vo.MenuModifyRequestVO;
import com.lsitc.domain.common.menu.vo.MenuModifyResponseVO;
import com.lsitc.domain.common.menu.vo.MenuRemoveRequestVO;
import com.lsitc.domain.common.menu.vo.MenuRemoveResponseVO;
import com.lsitc.domain.common.menu.vo.MenuSearchListGetRequestVO;
import com.lsitc.domain.common.menu.vo.MenuSearchListGetResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/common/menu")
@RestController
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  @GetMapping("/list")
  public MenuListGetResponseVO getMenuList() throws MenuException {
    MenuListGetResponseVO menuListGetResponseVOList = menuService.getMenuList();
    log.info(menuListGetResponseVOList.toString());
    return menuListGetResponseVOList;
  }

  @GetMapping("/search")
  public MenuSearchListGetResponseVO searchMenuList(
      @Valid final MenuSearchListGetRequestVO menuSearchListGetRequestVO) throws MenuException {
    log.info(menuSearchListGetRequestVO.toString());
    MenuSearchListGetResponseVO menuSearchListGetResponseVO =
        menuService.searchMenuList(menuSearchListGetRequestVO);
    log.info(menuSearchListGetResponseVO.toString());
    return menuSearchListGetResponseVO;
  }

  @PostMapping
  public MenuAddResponseVO addMenu(
      @RequestBody @Valid final List<MenuAddRequestVO> menuAddRequestVO) throws MenuException {
    log.info(menuAddRequestVO.toString());
    MenuAddResponseVO menuAddResponseVO = menuService.addMenu(menuAddRequestVO);
    log.info(menuAddResponseVO.toString());
    return menuAddResponseVO;
  }

  @PutMapping
  public MenuModifyResponseVO modifyMenu(
      @RequestBody @Valid final List<MenuModifyRequestVO> menuModifyRequestVO)
      throws MenuException {
    log.info(menuModifyRequestVO.toString());
    MenuModifyResponseVO menuModifyResponseVO = menuService.modifyMenu(menuModifyRequestVO);
    log.info(menuModifyResponseVO.toString());
    return menuModifyResponseVO;
  }

  @DeleteMapping
  public MenuRemoveResponseVO removeMenu(
      @RequestBody @Valid final List<MenuRemoveRequestVO> menuRemoveRequestVO)
      throws MenuException {
    log.info(menuRemoveRequestVO.toString());
    MenuRemoveResponseVO menuRemoveResponseVO = menuService.removeMenu(menuRemoveRequestVO);
    log.info(menuRemoveResponseVO.toString());
    return menuRemoveResponseVO;
  }

}
