package com.lsitc.global.common.share.vo;

import com.lsitc.global.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApndFileGrpVo extends BaseVo<ApndFileGrpVo> {
	private String apndFileUuid;
	private String menuId;
	private String useTableNm;
	private String useFg;
	private String rmrk;
}
