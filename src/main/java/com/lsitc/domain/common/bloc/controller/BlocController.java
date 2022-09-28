package com.lsitc.domain.common.bloc.controller;

import com.lsitc.domain.common.bloc.exception.BlocException;
import com.lsitc.domain.common.bloc.service.BlocService;
import com.lsitc.domain.common.bloc.vo.BlocAddRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocAddResponseVO;
import com.lsitc.domain.common.bloc.vo.BlocInfoRequestVO;
import com.lsitc.domain.common.bloc.vo.BlocInfoResponseVO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/comm/bloc")
@RestController
@RequiredArgsConstructor
public class BlocController {

  private final BlocService blocService;

  @GetMapping("/info")
  public BlocInfoResponseVO blocInfo(@Valid final BlocInfoRequestVO blocInfoRequestVO)
      throws BlocException {
    log.info(blocInfoRequestVO.toString());
    BlocInfoResponseVO blocInfoResponseVO = blocService.getBlocInfo(blocInfoRequestVO);
    log.info(blocInfoResponseVO.toString());
    return blocInfoResponseVO;
  }

  @PostMapping("")
  public BlocAddResponseVO addBloc(@RequestBody @Valid final BlocAddRequestVO blocAddRequestVO)
      throws BlocException {
    log.info(blocAddRequestVO.toString());
    BlocAddResponseVO blocAddResponseVO = blocService.addBloc(blocAddRequestVO);
    log.info(blocAddResponseVO.toString());
    return blocAddResponseVO;
  }
}
