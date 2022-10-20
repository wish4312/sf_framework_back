package com.lsitc.domain.base.controller;

import com.lsitc.global.common.BaseResponse;
import com.lsitc.global.common.share.service.FileService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileCtr {
	
	@Autowired
	FileService fileService;

	//업로드 파일 조회
	@RequestMapping("/comm/base/selectUploadFile")
    @ResponseBody
	public Object selectUploadFile() {//@RequestBody(required = false) Map<String, String> paramMap
		BaseResponse result = new BaseResponse();
		Map<String, String> paramMap = new HashMap<String, String>();
		
		result.add("uploadFileList", fileService.selectUploadFile(paramMap));
		return result;
	}

	//파일삭제
	@RequestMapping("/comm/base/deleteUploadFile")
    @ResponseBody
    public Object deleteUploadFile(@RequestParam(name = "apndFileUuid")String apndFileUuid,
    							   @RequestParam(name = "apndFileId", required = false)String apndFileId) throws Exception {
    	BaseResponse result = new BaseResponse();
    	fileService.deleteUploadFile(apndFileUuid, apndFileId);
    	return result;
    }

	//파일다운로드
	@RequestMapping("/comm/base/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "apndFileUuid", required = false) String apndFileUuid,
    							   @RequestParam(name = "apndFileId", required = false) String apndFileId) throws Exception {
    	fileService.downloadFile(request, response, apndFileUuid, apndFileId);
    }
	//파일다운로드(zip)
	@RequestMapping("/comm/base/downloadFiles")
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "apndFileUuid", required = false) String apndFileUuid,
    							   @RequestParam(name = "fileNm", required = false) String fileNm) throws Exception {
    	fileService.downloadFiles(request, response, apndFileUuid, fileNm);
    }
}
