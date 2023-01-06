package com.lsitc.domain.common.menu.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.lsitc.domain.common.menu.entity.MenuEntity;

@Mapper
public interface MenuDAO {
  
  List<MenuEntity> selectMainMenu();

  MenuEntity selectMenuById(MenuEntity menuEntity);

  List<MenuEntity> selectAll();
  
  int insertMenuList(List<MenuEntity> menuEntityList);

  int updateMenuById(List<MenuEntity> menuEntity);

  int insertMenuWithId(List<MenuEntity> menuEntity);

  int deleteMenuById(List<MenuEntity> menuEntity);

}