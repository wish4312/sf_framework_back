/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 LS ITC에 있으며,
* LS ITC가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* LS ITC의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2021 LS ITC. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* LS ITC Business unit. LS ITC Business unit., owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2021 LS ITC Business unit. All Rights Reserved| Confidential)
* Author    : LS ITC
* Created   : 2021-04-23 17:58:11
*/
package com.lsitc.fems.comm.base.vo;


import java.math.BigDecimal;

import com.lsitc.core.base.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApndFileVo extends BaseVo<ApndFileVo> {

	private String apndFileUuid; // APND_FILE_UUID (첨부파일UUID)

	private String apndFileId; // APND_FILE_ID (첨부파일ID(UUID))

	private String apndFileNm; // APND_FILE_NM (첨부파일명)

	private String apndFileExt; // APND_FILE_EXT (첨부파일확장자)

	private BigDecimal apndFileSize; // APND_FILE_SIZE (첨부파일사이즈)

	private String apndFilePath; // APND_FILE_PATH (첨부파일경로)

	private String useFg; // USE_FG (사용여부)

	private BigDecimal sortSeq; // SORT_SEQ (정렬순서)

	private String rmrk; // RMRK (비고)
}
