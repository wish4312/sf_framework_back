package com.lsitc.domain.common.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lsitc.domain.common.menu.dao.MenuDAO;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.domain.common.menu.exception.MenuException;
import com.lsitc.domain.common.menu.vo.MenuAddRequestVO;
import com.lsitc.domain.common.menu.vo.MenuAddResponseVO;
import com.lsitc.domain.common.menu.vo.MenuListGetResponseVO;
import com.lsitc.domain.common.menu.vo.MenuModifyRequestVO;
import com.lsitc.domain.common.menu.vo.MenuModifyResponseVO;
import com.lsitc.domain.common.menu.vo.MenuRemoveRequestVO;
import com.lsitc.domain.common.menu.vo.MenuRemoveResponseVO;
import com.lsitc.domain.common.menu.vo.MenuSearchListGetRequestVO;
import com.lsitc.domain.common.menu.vo.MenuSearchListGetResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MenuService {

  private final MenuDAO menuDAO;

  public MenuListGetResponseVO getMenuList() throws MenuException {
    List<MenuEntity> menuEntityList = menuDAO.selectAll();
    return MenuListGetResponseVO.of(menuEntityList);
  }

  public MenuSearchListGetResponseVO searchMenuList(
      final MenuSearchListGetRequestVO menuSearchListGetRequestVO) {
    MenuEntity menuEntity = menuSearchListGetRequestVO.toEntity();
    List<MenuEntity> menuEntityList = menuDAO.selectMenuByConditions(menuEntity);
    return MenuSearchListGetResponseVO.of(menuEntityList);
  }

  @Transactional
  public MenuAddResponseVO addMenu(final List<MenuAddRequestVO> menuAddRequestVO) {
    List<MenuEntity> menuEntityList =
        menuAddRequestVO.stream().map(MenuAddRequestVO::toEntity).collect(Collectors.toList());
    log.info(menuEntityList.toString());

    int addRows = menuDAO.insertMenuList(menuEntityList);
    return MenuAddResponseVO.of(addRows);
  }

  @Transactional
  public MenuModifyResponseVO modifyMenu(final List<MenuModifyRequestVO> menuModifyRequestVO) {
    List<MenuEntity> menuEntityList = menuModifyRequestVO.stream()
        .map(MenuModifyRequestVO::toEntity).collect(Collectors.toList());

    List<MenuEntity> updateList = new ArrayList<>();
    List<MenuEntity> insertList = new ArrayList<>();

    menuEntityList.forEach(menuEntity -> {
      if (isUpdate(menuEntity)) {
        updateList.add(menuEntity);
      } else {
        insertList.add(menuEntity);
      }
    });

    int upsertRows = (updateList.size() > 0 ? menuDAO.updateMenuById(updateList) : 0)
        + (insertList.size() > 0 ? menuDAO.insertMenuWithId(insertList) : 0);

    log.info(menuEntityList.toString());
    return MenuModifyResponseVO.of(upsertRows);
  }

  private boolean isUpdate(MenuEntity targetEntity) {
    MenuEntity menuEntity = menuDAO.selectMenuById(targetEntity);
    return menuEntity != null;
  }

  @Transactional
  public MenuRemoveResponseVO removeMenu(List<MenuRemoveRequestVO> menuRemoveRequestVO) {
    List<MenuEntity> menuEntityList = menuRemoveRequestVO.stream()
        .map(MenuRemoveRequestVO::toEntity).collect(Collectors.toList());
    log.info(menuEntityList.toString());
    int deleteRows = menuDAO.deleteMenuById(menuEntityList);
    return MenuRemoveResponseVO.of(deleteRows);
  }

}
