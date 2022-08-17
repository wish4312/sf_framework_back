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
package com.lsitc.fems.comm.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.core.base.BaseParam;
import com.lsitc.core.base.BaseResponse;
import com.lsitc.fems.comm.auth.service.UserRoleMngSvc;
import com.lsitc.fems.comm.auth.vo.RoleUserVo;
import com.lsitc.fems.comm.auth.vo.UserInfoVo;

@RequestMapping("/comm/auth/UserRoleMngCtr")
@Controller
public class UserRoleMngCtr {
    @Autowired
    private UserRoleMngSvc userRoleMngSvc;
    
    /**
     * @methodName  : selectRoleUser
     * @date        : 2021.04.26
     * @desc        : 역할별 사용자 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectRoleUser", method=RequestMethod.POST)
    @ResponseBody
    public Object selectRoleUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("roleUserData", userRoleMngSvc.selectRoleUser(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : selectUnAsgnRoleByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 미배정 역할 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectUnAsgnRoleByUser", method=RequestMethod.POST)
    @ResponseBody
    public Object selectUnAsgnRoleByUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("roleUserData", userRoleMngSvc.selectUnAsgnRoleByUser(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : selectAsgnRoleByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 역할 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectAsgnRoleByUser", method=RequestMethod.POST)
    @ResponseBody
    public Object selectAsgnRoleByUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("roleUserData", userRoleMngSvc.selectAsgnRoleByUser(paramMap.getParams()));
        return result;
    }
    
    /**
     * 
     * @methodName  : selectAsgnMenuByUser
     * @date        : 2021.04.26
     * @desc        : 사용자의 메뉴 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectAsgnMenuByUser", method=RequestMethod.POST)
    @ResponseBody
    public Object selectAsgnMenuByUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("roleUserData", userRoleMngSvc.selectAsgnMenuByUser(paramMap.getParams()));
        return result;
    }

    /**
     * @methodName  : selectUser
     * @date        : 2021.04.23
     * @desc        : 사용자를 조회한다.
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectUser", method=RequestMethod.POST)
    @ResponseBody
    public Object selectUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("userData", userRoleMngSvc.selectUser(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveRoleUser
     * @date        : 2021.04.26
     * @desc        : 역할별 사용자 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveRoleUser", method=RequestMethod.POST)
    @ResponseBody
    public Object saveRoleUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        userRoleMngSvc.saveRoleUser(paramMap.getDs("dsRoleUser", RoleUserVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectUserPop
     * @date        : 2021.06.09
     * @desc        : [팝업]사용자 조회 
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectUserPop", method=RequestMethod.POST)
    @ResponseBody
    public Object selectUserPop(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("userData", userRoleMngSvc.selectUserPop(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveUser
     * @date        : 2021.04.23
     * @desc        : 사용자를 저장한다.
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveUser", method=RequestMethod.POST)
    @ResponseBody
    public Object saveUser(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        Map<String, String> inputUserInfo = new HashMap<String, String>();
        userRoleMngSvc.saveUser(paramMap.getDs("dsUser", UserInfoVo.class));
        return result;
    }
    
    @RequestMapping(value="/selectComparePswd", method=RequestMethod.POST)
    @ResponseBody
    public Object selectComparePswd(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("roleUserData", userRoleMngSvc.selectComparePswd(paramMap.getParams()));
        return result;
    }
    
    @RequestMapping(value="/updatePswd", method=RequestMethod.POST)
    @ResponseBody
    public Object updatePswd(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        Map<String, String> inputUserInfo = new HashMap<String, String>();
        userRoleMngSvc.updatePswd(paramMap.getDs("dsUser", UserInfoVo.class));
        return result;
    }
    
    
}
