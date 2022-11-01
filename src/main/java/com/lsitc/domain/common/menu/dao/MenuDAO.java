package com.lsitc.domain.common.menu.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.lsitc.domain.common.menu.entity.MenuEntity;

@Mapper
public interface MenuDAO {
  
  MenuEntity selectMenuById(MenuEntity menuEntity);

  List<MenuEntity> selectAll();

  List<MenuEntity> selectMenuByConditions(MenuEntity roleEntity);
  
  int insertMenuList(List<MenuEntity> menuEntityList);

  int updateMenuById(List<MenuEntity> menuEntity);

  int insertMenuListWithId(List<MenuEntity> menuEntity);

  int deleteMenuById(List<MenuEntity> menuEntity);

}