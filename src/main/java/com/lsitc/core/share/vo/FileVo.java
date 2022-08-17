package com.lsitc.core.share.vo;

import java.math.BigDecimal;

import com.lsitc.core.base.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVo extends BaseVo<FileVo> {
	//그룹	
	private String apndFileUuid;
	private String menuId;
	private String useTableNm;

	//파일
	private String apndFileId;
	private String apndFileNm;
	private String apndFileExt;
	private BigDecimal apndFileSize;
	private String apndFilePath;
	private BigDecimal sortSeq;
	private String useFg;
	private String rmrk;
}
