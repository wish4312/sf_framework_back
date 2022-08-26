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
package com.lsitc.domain.base.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.domain.base.service.SystemLogReadSvc;

@RequestMapping("/comm/base/SystemLogReadCtr")
@Controller
public class SystemLogReadCtr {
    @Autowired
    private SystemLogReadSvc systemLogReadSvc;
    
    /**
     * @methodName  : selectLoginLog
     * @date        : 2021.05.04
     * @desc        : 로그인로그 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectLoginLog", method=RequestMethod.POST)
    @ResponseBody
    public Object selectLoginLog(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
        result.add("loginLogData", systemLogReadSvc.selectLoginLog(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : selectMenuCnctLog
     * @date        : 2021.05.04
     * @desc        : 메뉴접근로그 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMenuCnctLog", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMenuCnctLog(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
        result.add("menuCnctLogData", systemLogReadSvc.selectMenuCnctLog(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveMenuCnctLog
     * @date        : 2021.05.04
     * @desc        : 메뉴접근로그 저장
     * @param param
     * @return
     */
    @RequestMapping("/saveMenuCnctLog")
    @ResponseBody
    public Object saveMenuCnctLog(@RequestBody Map<String, Object> param) {
        BaseResponse ret = new BaseResponse();
        systemLogReadSvc.saveMenuCnctLog(param.get("menuId").toString());
        return ret;
    }
}
