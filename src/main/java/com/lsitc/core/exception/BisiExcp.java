package com.lsitc.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class BisiExcp extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int errorCd;
    private String errorMsg;

    /**
     * @param errorMsg : 에러 메시지
     */
    public BisiExcp(String errorMsg) {
        //FIXME Error Code 체크
        this.errorCd = -10001;
        this.errorMsg = errorMsg;
    }
    
    /**
     * @param errorCd : 에러코드(-1001보다 작은수를 입력해야하고, 그렇지 않은 경우 에러가 발생한다.) 
     * @param errorMsg : 에러 메시지
     */
    public BisiExcp(int errorCd, String errorMsg)  {
        if(errorCd > -10001) {
            //FIXME 다국어 처리
            throw new BisiExcp("bizExcetion은 -10001보다 작은 수를 사용하세요.");
        }
        this.errorCd = errorCd;
        this.errorMsg = errorMsg;        
    }
}
