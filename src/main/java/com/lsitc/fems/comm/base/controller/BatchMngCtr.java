/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 K-FEMS 사업단에 있으며,
* K-FEMS 사업단가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* K-FEMS 사업단의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2020 K-FEMS 사업단. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* K-FEMS Business unit. K-FEMS Business unit., owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2020 K-FEMS Business unit. All Rights Reserved| Confidential)
* Author    : djnine9
* E-mail    : 
* Created   : 2021-02-16 12:26:57
*/
package com.lsitc.fems.comm.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.common.BaseSvc;
import com.lsitc.fems.comm.base.service.BatchMngSvc;
import com.lsitc.fems.comm.base.vo.BatchMngVo;

@RequestMapping("/comm/base/BatchMngCtr")
@Controller
public class BatchMngCtr extends BaseSvc {

	@Autowired
	private BatchMngSvc batchMngSvc;
	
    /**
     * @methodName  : selectBatchMng
     * @date        : 2021.09.09
     * @desc        : 배치 리스트 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectBatchMng", method=RequestMethod.POST)
    @ResponseBody
    public Object selectBatchMng(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("batchMngData", batchMngSvc.selectBatchMng(paramMap.getParams()));
        return result;
    }
    
    /**
	 * @methodName : saveBatchMng
     * @date        : 2021.09.09
     * @desc        : 배치 상세 저장
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/saveBatchMng", method=RequestMethod.POST)
    @ResponseBody
    public Object saveBatchMgn(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        batchMngSvc.saveBatchMng(paramMap.getDs("dsBatchMng", BatchMngVo.class));
        return result;
    }
    

    /**
     * @methodName  : selectFllwBatchList
     * @date        : 2021.09.09
     * @desc        : 후속 배치 리스트 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectFllwBatchList", method=RequestMethod.POST)
    @ResponseBody
    public Object selectFllwBatchList(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("fllwBatchListData", batchMngSvc.selectFllwBatchList(paramMap.getParams()));
        return result;
    }
    
}
