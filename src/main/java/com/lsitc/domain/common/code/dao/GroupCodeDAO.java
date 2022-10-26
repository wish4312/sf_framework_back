package com.lsitc.domain.common.code.dao;

import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupCodeDAO {

  GroupCodeEntity selectGroupCodeById(GroupCodeEntity groupCodeEntity);

  List<GroupCodeEntity> selectAll();
}
