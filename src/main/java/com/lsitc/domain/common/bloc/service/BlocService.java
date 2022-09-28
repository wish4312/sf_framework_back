package com.lsitc.domain.common.bloc.service;

import com.lsitc.domain.common.bloc.dao.BlocDAO;
import com.lsitc.domain.common.bloc.entity.BlocEntity;
import com.lsitc.domain.common.bloc.vo.BlocAddRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocAddResponseVO;
import com.lsitc.domain.common.bloc.vo.BlocInfoGetRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocInfoGetResponseVO;
import com.lsitc.domain.common.bloc.vo.BlocModifyRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocModifyResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BlocService {

  private final BlocDAO blocDAO;

  public BlocInfoGetResponseVO getBlocInfo(BlocInfoGetRequestVO blocInfoGetRequestVO) {
    BlocEntity blocEntity = blocInfoGetRequestVO.toEntity();
    log.info(blocEntity.toString());
    BlocEntity blocInfo = blocDAO.selectBlocById(blocEntity);
    return BlocInfoGetResponseVO.of(blocInfo);
  }

  public BlocAddResponseVO addBloc(BlocAddRequestVO blocAddRequestVO) {
    BlocEntity blocEntity = blocAddRequestVO.toEntity();
    log.info(blocEntity.toString());
    int addRows = blocDAO.insertBloc(blocEntity);
    return BlocAddResponseVO.of(addRows);
  }

  public BlocModifyResponseVO modifyBloc(BlocModifyRequestVO blocModifyRequestVO) {
    BlocEntity blocEntity = blocModifyRequestVO.toEntity();
    int upsertRows = upsertBloc(blocEntity);
    log.info(blocEntity.toString());
    return BlocModifyResponseVO.of(upsertRows);
  }

  private int upsertBloc(BlocEntity targetEntity) {
    BlocEntity blocEntity = blocDAO.selectBlocById(targetEntity);
    return blocEntity != null ? blocDAO.updateBlocById(targetEntity)
        : blocDAO.insertBlocWithId(targetEntity);
  }
}
