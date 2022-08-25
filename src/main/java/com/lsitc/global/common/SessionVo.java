package com.lsitc.global.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * SessionVo
 * @desc 세션정보를 담고 있는 객체로, mybatis intercepter에서 해당 객체에 세션정보를 담아 주고, jwt uitl에서도 이 객체에 담아 세션정보를 반환한다.
 */
@Getter
@Setter
public class SessionVo implements Serializable {
    
    private static final long serialVersionUID = -3445039379704372423L;
    
    private String userNo;  //사용자No
    private String userNm;  //사용자명
    private String comId;   //회사코드
    private String blocId;  //사업장코드
    
    private String locale;  //locale
}
