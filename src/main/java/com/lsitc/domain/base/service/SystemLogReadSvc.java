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
package com.lsitc.domain.base.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;

@Service
public class SystemLogReadSvc extends BaseSvc {
    private static Boolean MENU_CNCT_LOG_ENABLE = false;

    @Value("${logging.menuCnctLogEnable:FEMS_SESSION}")
    private void setMenuCnctLogEnable(Boolean menuCnctLogEnable) {
        MENU_CNCT_LOG_ENABLE = menuCnctLogEnable;
    }

    /**
     * @methodName  : saveMenuCnctLog
     * @date        : 2021.05.04
     * @desc        : 메뉴접근로그 저장
     * @param menuId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveMenuCnctLog(String menuId) {
        if( MENU_CNCT_LOG_ENABLE ) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("logId", getUUID());
            paramMap.put("menuId", menuId);
            dao.insert("comm.base.SystemLogRead.insertMenuCnctLog", paramMap);
        }
    }
    
    /**
     * @methodName  : getUUID
     * @date        : 2021.05.04
     * @desc        : UUID반환
     * @return
     */
    private String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
