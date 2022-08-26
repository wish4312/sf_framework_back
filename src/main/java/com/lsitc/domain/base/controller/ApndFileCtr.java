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


import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lsitc.domain.base.service.ApndFileSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lsitc.global.common.BaseParam;
import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.common.CamelHashMap;
import com.lsitc.global.util.FileUtils;

@RequestMapping("/comm/base/ApndFileCtr")
@Controller
public class ApndFileCtr{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private ApndFileSvc apndFileSvc;
    
	/**
	 * 
	 * @methodName  : uploadFile
	 * @date        : 2021.07.14
	 * @desc        : 파일 업로드
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/saveFile", method=RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(MultipartHttpServletRequest mtfRequest){
		BaseResponse result = new BaseResponse();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		params.put("bordNo", mtfRequest.getParameter("bordNo"));
		params.put("postNo", mtfRequest.getParameter("postNo"));
		String apndFileUuid = apndFileSvc.uploadFile(fileList, params);
		
		if(fileList.size() > 0) {
			result.add("apndFileUuid", apndFileUuid);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @methodName  : selectApndFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 목록 반환
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/selectApndFile", method=RequestMethod.POST)
	@ResponseBody
	public Object selectApndFile(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		result.add("apndFile", apndFileSvc.selectApndFile(paramMap.getParams()));
		return result;
	}
	
	/**
	 * 
	 * @methodName  : deleteApndFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 삭제
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/deleteApndFile", method=RequestMethod.POST)
	@ResponseBody
	public Object deleteApndFile(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		apndFileSvc.deleteApndFile(paramMap.getParams());
		return result;
	}
	
	/**
	 * 
	 * @methodName  : selectDownloadApndFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 다운로드
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/selectDownloadApndFile", method=RequestMethod.GET)
	public void selectDownloadApndFile(@RequestParam String apndFileId, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("apndFileId", apndFileId);
		logger.debug("apndFileId : {}", apndFileId);
		List<CamelHashMap> list = apndFileSvc.selectApndFile(params);
		
//		String apndFileId = (String) list.get(0).get("apndFileId");
		String fileName = (String) list.get(0).get("apndFileNm");
		String fileExt = (String) list.get(0).get("apndFileExt");
//		String filePath = (String) list.get(0).get("apndFilePath") + "\\" + fileName + "." + fileExt ;
//		String filePath = (String) list.get(0).get("apndFilePath") + "\\" + apndFileId + "." + fileExt;
		String filePath = (String) list.get(0).get("apndFilePath") + File.separator + apndFileId + "." + fileExt;
		
		
		FileUtils fileUtils = new FileUtils();
		fileUtils.sendFileStream(request, response, fileName+"."+fileExt, filePath);
	}
	
}