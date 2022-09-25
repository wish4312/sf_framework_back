package com.lsitc.domain.common.bloc.service;

import com.lsitc.domain.common.bloc.dao.BlocDAO;
import com.lsitc.domain.common.bloc.entity.BlocEntity;
import com.lsitc.domain.common.bloc.vo.BlocInfoRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocInfoResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BlocService {

  private final BlocDAO blocDAO;

  public BlocInfoResponseVO getBlocInfo(BlocInfoRequestVO blocInfoRequestVO) {
    BlocEntity blocEntity = blocInfoRequestVO.toEntity();
    log.info(blocEntity.toString());
    BlocEntity blocInfo = blocDAO.selectBlocById(blocEntity);
    return BlocInfoResponseVO.of(blocInfo);
  }
}
