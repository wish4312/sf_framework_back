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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lsitc.core.base.BaseResponse;
import com.lsitc.core.share.service.FileSvc;

@Controller
public class FileCtr {
	
	@Autowired
	FileSvc fileSvc;

	//업로드 파일 조회
	@RequestMapping("/comm/base/selectUploadFile")
    @ResponseBody
	public Object selectUploadFile() {//@RequestBody(required = false) Map<String, String> paramMap
		BaseResponse result = new BaseResponse();
		Map<String, String> paramMap = new HashMap<String, String>();
		
		result.add("uploadFileList", fileSvc.selectUploadFile(paramMap));
		return result;
	}

	//파일업로드(임시)
	@RequestMapping("/comm/base/saveUploadFileToTemp")
    @ResponseBody
    public Object saveUploadFileToTemp(@RequestParam("upload_file") MultipartFile uploadfile, 
    							 @RequestParam(name = "apndFileUuid", required = false, defaultValue = "new")String apndFileUuid,
    							 //FIXME menuID는 어떻게 할지..
    							 @RequestParam(name = "menuId", required = false, defaultValue = "test")String menuId,
    							 @RequestParam(name = "sortSeq", required = false, defaultValue = "1")String sortSeq) throws Exception {
    	BaseResponse result = new BaseResponse();
    	
    	result.add("fileVo", fileSvc.saveUploadFileToTemp(apndFileUuid, menuId, sortSeq, uploadfile));
    	
    	return result;
    }

	//파일업로드(기존에 존재하는것)
	@RequestMapping("/comm/base/saveUploadFile")
    @ResponseBody
    public Object saveUploadFile(@RequestParam("upload_file") MultipartFile uploadfile, 
    							 @RequestParam(name = "apndFileUuid")String apndFileUuid,
    							 //FIXME menuID는 어떻게 할지..
    							 @RequestParam(name = "menuId", required = false, defaultValue = "test")String menuId,
    							 @RequestParam(name = "sortSeq", required = false, defaultValue = "1")String sortSeq) throws Exception {
    	BaseResponse result = new BaseResponse();
    	
    	result.add("fileVo", fileSvc.saveUploadFile(apndFileUuid, menuId, sortSeq, uploadfile));
    	
    	return result;
    }
	
	//파일삭제
	@RequestMapping("/comm/base/deleteUploadFile")
    @ResponseBody
    public Object deleteUploadFile(@RequestParam(name = "apndFileUuid")String apndFileUuid,
    							   @RequestParam(name = "apndFileId", required = false)String apndFileId) throws Exception {
    	BaseResponse result = new BaseResponse();
    	fileSvc.deleteUploadFile(apndFileUuid, apndFileId);
    	return result;
    }

	//파일다운로드
	@RequestMapping("/comm/base/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "apndFileUuid", required = false) String apndFileUuid,
    							   @RequestParam(name = "apndFileId", required = false) String apndFileId) throws Exception {
    	fileSvc.downloadFile(request, response, apndFileUuid, apndFileId);
    }
	//파일다운로드(zip)
	@RequestMapping("/comm/base/downloadFiles")
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "apndFileUuid", required = false) String apndFileUuid,
    							   @RequestParam(name = "fileNm", required = false) String fileNm) throws Exception {
    	fileSvc.downloadFiles(request, response, apndFileUuid, fileNm);
    }
}
