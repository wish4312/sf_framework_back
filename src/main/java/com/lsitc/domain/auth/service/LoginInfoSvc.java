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
package com.lsitc.domain.auth.service;


import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.lsitc.global.common.BaseSvc;


@Service
public class LoginInfoSvc extends BaseSvc {    
    public Map<String, Object> loginCheck(Map<String, String> inputUserInfo) {
        return dao.selectOne("comm.auth.loginInfo.selectLoginInfoForLogin", inputUserInfo);
    }
    

    public boolean selectMenuAuth(Map<String, String> paramMap) {
        Map<String, Object> menuAuth = dao.selectOne("comm.auth.loginInfo.selectMenuAuth", paramMap);        

        Pattern FIND_PATTERN = Pattern.compile("(.*)/select");
        Pattern INSER_PATTERN = Pattern.compile("(.*)/insert");
        Pattern UPDATE_PATTERN = Pattern.compile("(.*)/update");
        Pattern DELETE_PATTERN = Pattern.compile("(.*)/delete");
        Pattern SAVE_PATTERN = Pattern.compile("(.*)/save");
        Pattern GET_UI_PATTERN = Pattern.compile("(.*)/.+Page$");
        Pattern DOWNLOAD_PATTERN = Pattern.compile("(.*)/download");
        
        //요청하는 URI
        String requestUri = paramMap.get("requestURI");
        //사용자가 가진 권한
        String authCd = menuAuth != null && menuAuth.get("authCd") != null ? menuAuth.get("authCd").toString() : "";
        
        if ( FIND_PATTERN.matcher(requestUri).find() ) {
            logger.debug("select패턴");
            return authCd.indexOf("R") >= 0;
        } else if ( SAVE_PATTERN.matcher(requestUri).find() ) {
            logger.debug("save패턴");
            return authCd.indexOf("S") >= 0;
        } else if ( INSER_PATTERN.matcher(requestUri).find() ){
            logger.debug("inser패턴");
            return authCd.indexOf("C") >= 0;
        } else if ( UPDATE_PATTERN.matcher(requestUri).find() ){
            logger.debug("update패턴");
            return authCd.indexOf("U") >= 0;
        } else if ( DELETE_PATTERN.matcher(requestUri).find() ){
            logger.debug("delete패턴");
            return authCd.indexOf("D") >= 0;
        } else if ( GET_UI_PATTERN.matcher(requestUri).find() ){
            logger.debug("UI패턴");
            return authCd.indexOf("R") >= 0;
        } else if ( DOWNLOAD_PATTERN.matcher(requestUri).find() ) {
            logger.debug("다운로드패턴");
            return authCd.indexOf("R") >= 0;
        } else {
            return false;
        }
    }
}