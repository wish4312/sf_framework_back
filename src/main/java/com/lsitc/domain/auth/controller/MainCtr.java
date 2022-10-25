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
package com.lsitc.domain.auth.controller;

import java.util.ArrayList;
import java.util.List;

import com.lsitc.domain.common.menu.MenuMngSvc;
import com.lsitc.domain.base.service.SystemLogReadSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lsitc.global.util.CommonUtils;
import com.lsitc.domain.auth.vo.CommMenuVo;

@Controller
public class MainCtr {
    @Autowired
    private MenuMngSvc commMenuSvc;
    
    @Autowired
    private SystemLogReadSvc systemLogReadSvc;
    
    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav) {
        List<CommMenuVo> menuList = commMenuSvc.selectAuthMenu();
        List<CommMenuVo> treeMenuList = new ArrayList<CommMenuVo>();
        
        treeMenuList = CommonUtils.getTree(menuList, "menuId", "upMenuId");
        menuList.remove(0);
        mav.addObject("menuList", menuList);
        mav.addObject("treeMenuList", treeMenuList.get(0).getChildeVo());
        mav.setViewName("index");
        return mav;
    }
    
    @GetMapping("/{busiDiv}/{PageNm:.+[Page]$}")
    public ModelAndView getPage(@PathVariable String busiDiv, 
    		@PathVariable String PageNm, 
    		@RequestParam(name="$menuId", defaultValue = "NONE") String menuId, 
    		ModelAndView mav) {
    	
        mav.setViewName(busiDiv + "/" + PageNm.substring(0, PageNm.length() - 4));
        systemLogReadSvc.saveMenuCnctLog(menuId); //메뉴접근로그 저장
        mav.addObject("$menuId", menuId);
        return mav;
    }
}
