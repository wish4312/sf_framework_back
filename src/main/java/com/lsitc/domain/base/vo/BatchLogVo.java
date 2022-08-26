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
package com.lsitc.domain.base.vo;


import java.util.Date;

import com.lsitc.global.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchLogVo extends BaseVo<BatchLogVo> {

	private Integer batchLogSeq; // BATCH_LOG_SEQ (배치 로그 순번)

	private String batchId; // BATCH_ID (배치 ID)
	
	private String batchNm; // BATCH_NM (배치명)

	private String execDt; // EXEC_DT (실행 일자)

	private String execRsltCd; // EXEC_RSLT_CD (실행 결과 코드)

	private String execLogCont1; // EXEC_LOG_CONT_1 (실행 로그 1)

	private String execLogCont2; // EXEC_LOG_CONT_2 (실행 로그 2)

	private Date batchStrtDttm; // BATCH_STRT_DTTM (배치 시작 일시)

	private Date batchEndDttm; // BATCH_END_DTTM (배치 종료 일시)
}
