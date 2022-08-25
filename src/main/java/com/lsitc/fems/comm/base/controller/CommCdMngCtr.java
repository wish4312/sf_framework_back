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
package com.lsitc.fems.comm.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.fems.comm.base.service.CommCdMngSvc;
import com.lsitc.fems.comm.base.vo.CommCdVo;
import com.lsitc.fems.comm.base.vo.CommGrpCdVo;

@RequestMapping("/comm/base/CommCdMngCtr")
@Controller
public class CommCdMngCtr {
    @Autowired
    private CommCdMngSvc commCdMngSvc;

    /**
     * @methodName  : selectCommGrpCd
     * @date        : 2021.06.07
     * @desc        : 공통그룹코드 조회
     * @param paramMap
     * @return
     */
    @RequestMapping("/selectCommGrpCd")
    @ResponseBody
    public Object selectCommGrpCd(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
        result.add("commGrpCdData", commCdMngSvc.selectCommGrpCd(paramMap.getParams()));
        return result;
    }

    /**
     * @methodName  : saveCommGrpCd
     * @date        : 2021.06.07
     * @desc        : 공통그룹코드 저장
     * @param paramMap
     * @return
     */
    @RequestMapping("/saveCommGrpCd")
    @ResponseBody
    public Object saveCommGrpCd(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
        commCdMngSvc.saveCommGrpCd(paramMap.getDs("dsGrpCd", CommGrpCdVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectCommCd
     * @date        : 2021.06.07
     * @desc        : 공통코드 조회
     * @param paramMap
     * @return
     */
    @RequestMapping("/selectCommCd")
    @ResponseBody
    public Object selectCommCd(@RequestBody(required = false) BaseParam paramMap ) {
        BaseResponse result = new BaseResponse();
        result.add("commCdData", commCdMngSvc.selectCommCd(paramMap));
        return result;
    }

    /**
     * @methodName  : saveCommCd
     * @date        : 2021.06.07
     * @desc        : 공통코드 저장
     * @param paramMap
     * @return
     */
    @RequestMapping("/saveCommCd")
    @ResponseBody
    public Object saveCommCd(@RequestBody BaseParam paramMap) {
        BaseResponse result = new BaseResponse();
        commCdMngSvc.saveCommCd(paramMap.getDs("dsCommCd", CommCdVo.class));
        return result;
    }
    
    /**
     * @methodName  : selectCodeList
     * @date        : 2021.06.08
     * @desc        : 화면용 공통코드 조회
     * @param paramMap
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/selectCodeList")
    @ResponseBody
    public Object selectCodeList(@RequestBody(required = false) BaseParam paramMap) throws JsonProcessingException {
        BaseResponse result = new BaseResponse();
    	List<CommCdVo> codeResult =  commCdMngSvc.selectCommCd(paramMap);
        result.add("codeLists", codeResult);
        return result;
    }
}
