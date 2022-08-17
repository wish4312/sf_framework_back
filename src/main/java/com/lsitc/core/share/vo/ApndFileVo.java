package com.lsitc.core.share.vo;

import java.math.BigDecimal;

import com.lsitc.core.base.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApndFileVo extends BaseVo<ApndFileVo> {
	private String apndFileUuid;
	private String apndFileId;
	private String apndFileNm;
	private String apndFileExt;
	private BigDecimal apndFileSize;
	private String apndFilePath;
	private String useFg;
	private BigDecimal sortSeq;
	private String rmrk;
}
