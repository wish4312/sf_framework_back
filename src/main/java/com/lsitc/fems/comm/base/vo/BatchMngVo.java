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


import com.lsitc.global.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchMngVo extends BaseVo<BatchMngVo> {

	private String batchId; // BATCH_ID (배치 ID)

	private String batchNm; // BATCH_NM (배치명)

	private String batchExecTp; // BATCH_EXEC_TP (배치 실행 구분)

	private String batchDupExecFg; // BATCH_DUP_EXEC_FG (배치 중복 실행 여부)

	private String execCyclSecVal; // EXEC_CYCL_SEC_VAL (실행주가(초))

	private String execCyclMinVal; // EXEC_CYCL_MIN_VAL (실행주기(분))

	private String execCyclHhVal; // EXEC_CYCL_HH_VAL (실행주기(시간))

	private String execCyclDdVal; // EXEC_CYCL_DD_VAL (실행주기(일))

	private String execCyclMmVal; // EXEC_CYCL_MM_VAL (실행주기(월))

	private String execCyclWdayVal; // EXEC_CYCL_WDAY_VAL (실행주기(요일))

	private String execCyclYyyyVal; // EXEC_CYCL_YYYY_VAL (실행주기(년))

	private String execCmnd; // EXEC_CMND (실행 명령)

	private String execCmndPath; // EXEC_CMND_PATH (실행 명령 경로)
	
	private String fllwBatchId; // FLLW_BATCH_ID (후속배치ID)

	private String refVal1; // REF_VAL_1 (참조값 1)

	private String refVal2; // REF_VAL_2 (참조값 2)

	private String refVal3; // REF_VAL_3 (참조값 3)

	private String useFg; // USE_FG (사용여부)
}
