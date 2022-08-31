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
 
package com.lsitc.domain.common.menu;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.util.CommonUtils;
import com.lsitc.domain.auth.vo.CommMenuVo;

@RequestMapping("/comm/base/MenuMngCtr")
@Controller
public class MenuMngCtr{

    @Autowired
    private MenuMngSvc menuMngSvc;

    /**
     * @methodName  : selectMenu
     * @date        : 2021.06.07
     * @desc        : 메뉴 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMenu", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMenu(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("menuData", menuMngSvc.selectMenu(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveMenu
     * @date        : 2021.06.07
     * @desc        : 메뉴 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveMenu", method=RequestMethod.POST)
    @ResponseBody
    public Object saveMenu(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        menuMngSvc.saveMenu(paramMap.getDs("dsMenu", MenuVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectMenuPop
     * @date        : 2021.06.07
     * @desc        : (팝업)메뉴 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMenuPop", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMenuPop(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("menuData", menuMngSvc.selectMenuPop(paramMap.getParams()));
        return result;
    }
    /**
     * @methodName  : selectAuthMenu
     * @date        : 2021.06.07
     * @desc        : (팝업)메뉴 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectAuthMenu", method=RequestMethod.POST)
    @ResponseBody
    public Object selectAuthMenu(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        List<CommMenuVo> menuList = menuMngSvc.selectAuthMenu();
        List<CommMenuVo> treeMenuList = new ArrayList<CommMenuVo>();
        
        treeMenuList = CommonUtils.getTree(menuList, "menuId", "upMenuId");
        menuList.remove(0);
        result.add("menuList", menuList);
        result.add("treeMenuList",  treeMenuList.get(0).getChildeVo());
        return result;
    } 
 
    /**
     * @methodName  : saveBookmark
     * @date        : 2021.06.07
     * @desc        : 메뉴 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveBookmark", method=RequestMethod.POST)
    @ResponseBody
    public Object saveBookmark(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        menuMngSvc.saveBookmark(paramMap.getDs("bookmarkDt", MenuVo.class));
        return result;
    }
    
    /**
     * @methodName  : deleteBookmark
     * @date        : 2021.06.07
     * @desc        : 메뉴 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/deleteBookmark", method=RequestMethod.POST)
    @ResponseBody
    public Object deleteBookmark(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        menuMngSvc.deleteBookmark(paramMap.getDs("bookmarkDt", MenuVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectAuthMenu
     * @date        : 2021.06.07
     * @desc        : (팝업)메뉴 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMyMenu", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMyMenu(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("menuData", menuMngSvc.selectMyMenu(paramMap.getParams()));
        
        return result;
    }
    
}