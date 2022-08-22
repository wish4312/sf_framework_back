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
package com.lsitc.fems.comm.auth.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.lsitc.core.base.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVo extends BaseVo<UserInfoVo> {
	private String comId; // COM_ID (회사코드)
	private String deptId; // DEPT_ID (부서코드)
	private String userNo; // USER_NO (사용자NO)
	private String userStatCd; // USER_STAT_CD (유저상태코드??)
	private String userLoginId; // USER_LOGIN_ID (사용자로그인ID)
	private String userPswd; // USER_PSWD (비밀번호)
	private BigDecimal pswdErrCnt; // PSWD_ERR_CNT (비밀번호 오류횟수)
	private Date pswdModDttm; // PSWD_MOD_DTTM (비밀번호 변경일시)
	private String userNm; // USER_NM (사용자명)
	private String email; // EMAIL (이메일)
	private String moblphon; // MOBLPHON (휴대전화번호)
	private String officeTelno; // OFFICE_TELNO (사무실 전화번호)
	private String faxTelno; // FAX_TELNO (FAX번호)
	private String postNo; // POST_NO (우편번호)
	private String postAdres; // POST_ADRES (우편번호주소)
	private String adresDetail; // ADRES_DETAIL (주소상세)
	private String blocId;
}