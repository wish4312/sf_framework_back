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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.common.CamelHashMap;
import com.lsitc.domain.base.service.NoticeMngSvc;

@RequestMapping("/comm/base/NoticeMngCtr")
@Controller
public class NoticeMngCtr{

    @Autowired
    private NoticeMngSvc noticeMngSvc;

    /**
     * @methodName  : selectPostList
     * @date        : 2021.07.28
     * @desc        : 게시물 목록 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectPostList", method=RequestMethod.POST)
    @ResponseBody
    public Object selectPostList(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();

		List<HashMap<String, Object>> finalValue = new ArrayList<HashMap<String, Object>>();
        List<CamelHashMap> postList = noticeMngSvc.selectPostList(paramMap.getParams());
        
        //제목앞에 RE달기
//	    for(int i=0; i<postList.size(); i++){
//			HashMap<String, Object> tempAvg = new HashMap<String, Object>();
//			String str = "";
//			String bef = "";
//			for(String key : postList.get(i).keySet()) {
//				if(key.equals("title")) {
//					if(Integer.parseInt(postList.get(i).get("postGrpOrd").toString()) > 0 && Integer.parseInt(postList.get(i).get("postGrpLayer").toString()) > 0) {
//						for(int j=0; j<Integer.parseInt(postList.get(i).get("postGrpLayer").toString()); j++) {
//							bef += " ";
//							str += "RE:";
//						}
//						
//						str +=postList.get(i).get("postTitl").toString();
//						tempAvg.put("title", bef+str);
//					}else {
//						tempAvg.put(key, postList.get(i).get(key));						
//					}
//				}else {
//					tempAvg.put(key, postList.get(i).get(key));
//				}			
//			}
//		    finalValue.add(tempAvg);
//		}    
//        result.add("postData", finalValue);
        result.add("postData", postList);
        return result;
    }
    
    /**
     * @methodName  : selectPost
     * @date        : 2021.07.28
     * @desc        : 게시물 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectPost", method=RequestMethod.POST)
    @ResponseBody
    public Object selectPost(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        
        List<HashMap<String, Object>> finalValue = new ArrayList<HashMap<String, Object>>();
        List<CamelHashMap> postList = noticeMngSvc.selectPost(paramMap.getParams());
        
        //제목앞에 RE달기
//	    for(int i=0; i<postList.size(); i++){
//			HashMap<String, Object> tempAvg = new HashMap<String, Object>();
//			String str = "";
//			for(String key : postList.get(i).keySet()) {
//				if(key.equals("title")) {
//					if(Integer.parseInt(postList.get(i).get("postGrpOrd").toString()) > 0 && Integer.parseInt(postList.get(i).get("postGrpLayer").toString()) > 0) {
//						for(int j=0; j<Integer.parseInt(postList.get(i).get("postGrpLayer").toString()); j++) {
//							str += "RE:";
//						}
//						str +=postList.get(i).get("postTitl").toString();
//						tempAvg.put("title", str);
//					}else {
//						tempAvg.put(key, postList.get(i).get(key));						
//					}
//				}else {
//					tempAvg.put(key, postList.get(i).get(key));
//				}			
//			}
//		    finalValue.add(tempAvg);
//		}
//        result.add("postData", finalValue);
        
        result.add("postData", postList);
        //게시글 조회수
		noticeMngSvc.updateViewCnt(paramMap.getParams());
        
        return result;
    }   
    
	/**
	 * 
	 * @methodName  : insertPost
     * @date        : 2021.07.28
	 * @desc        : 게시물 등록
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/insertPost", method=RequestMethod.POST)
	@ResponseBody
	public Object insertPost(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();		
		noticeMngSvc.insertPost(paramMap.getParams());
		return result;
	}

	/**
	 * 
	 * @methodName  : insertRplPost
     * @date        : 2021.07.28
	 * @desc        : 게시물 답글 등록
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/insertRplPost", method=RequestMethod.POST)
	@ResponseBody
	public Object insertRplPost(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		noticeMngSvc.updateRplPostGrpOrd(paramMap.getParams());
		noticeMngSvc.insertRplPost(paramMap.getParams());
		return result;
	}
    
	/**
	 * 
	 * @methodName  : updatePost
     * @date        : 2021.07.28
	 * @desc        : 게시물 수정
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/updatePost", method=RequestMethod.POST)
	@ResponseBody
	public Object updatePost(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		noticeMngSvc.updatePost(paramMap.getParams());
		return result;
	}
	
	/**
	 * 
	 * @methodName  : deletePostList
     * @date        : 2021.07.28
	 * @desc        : 게시물 삭제
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/deletePost", method=RequestMethod.POST)
	@ResponseBody
	public Object deletePost(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		noticeMngSvc.deletePost(paramMap.getParams());
		return result;
	}
	
}