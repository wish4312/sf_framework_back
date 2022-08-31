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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.domain.base.service.CommSvc;
import com.lsitc.domain.base.vo.BlocMstrVo;

@RequestMapping("/comm/base/CommCtr")
@Controller
public class CommCtr {

	@Autowired
	private CommSvc commSvc;

	/**
     * @methodName		: selectBlocMstrCodeList
     * @date			: 2021.06.08
     * @desc			: 사업장 코드 정보 조회
	 * @param paramMap
	 * @return
	 */
    @RequestMapping(value="/selectBlocMstrCodeList", method=RequestMethod.POST)
    @ResponseBody
    public Object selectBlocMstrCodeList(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
    	List<BlocMstrVo> codeResult =  commSvc.selectBlocMstr(paramMap.getParams());
        result.add("blocCodeLists", codeResult);
        return result;
    	
    }
    
}