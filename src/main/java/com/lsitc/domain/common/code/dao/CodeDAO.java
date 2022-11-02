package com.lsitc.domain.common.code.dao;

import com.lsitc.domain.common.code.entity.CodeEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeDAO {

  int insertCode(List<CodeEntity> codeEntityList);
}
