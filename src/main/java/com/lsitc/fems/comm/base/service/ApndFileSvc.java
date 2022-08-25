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
package com.lsitc.fems.comm.base.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.global.common.CamelHashMap;
import com.lsitc.global.util.FileUtils;


@Service
public class ApndFileSvc extends BaseSvc {
    /**
	 * 
	 * @methodName  : selectApndFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 정보 조회
	 * @param params
	 * @return
	 */
	public List<CamelHashMap> selectApndFile(HashMap<String, Object> params){
		return dao.selectList("comm.base.apndFile.selectApndFile", params);
	}
	
	/**
	 * 
	 * @methodName  : deleteApndFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 삭제
	 * @param params
	 * @return
	 */
	@Transactional
	public void deleteApndFile(HashMap<String, Object> params){
		List<HashMap<String, String>> apndFile_List = (List<HashMap<String, String>>)params.get("apndFile");
		
		for(HashMap<String, String> eachRow : apndFile_List) {
			dao.update("comm.base.apndFile.deleteApndFile", eachRow);
		}
	}
	
	/**
	 * 
	 * @methodName  : uploadFile
	 * @date        : 2021.07.12
	 * @desc        : 파일 업로드
	 * @param params
	 * @return
	 */
	@Transactional
	public String uploadFile(List<MultipartFile> fileList, HashMap<String, Object> param){
		FileUtils fileUtils = new FileUtils();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		String apndFileUuid = "";
		String bordNo = param.get("bordNo").toString();
		String postNo = param.get("postNo").toString();
		
		//Apnd_File_Grp 처리
		if(postNo != null && !postNo.equals("")) {
			HashMap<String, Object> paramsForUuid = new HashMap<String, Object>();
			paramsForUuid.put("bordNo", bordNo);
			paramsForUuid.put("postNo", postNo);
			List<CamelHashMap> tempList = dao.selectList("comm.base.noticeMng.selectPost", paramsForUuid);
			if(tempList.get(0).get("apndFileUuid") != null) {
				apndFileUuid = (String) tempList.get(0).get("apndFileUuid");
			}
		}

		if(apndFileUuid.isEmpty() || apndFileUuid == null || postNo == null || postNo.equals("")) {
			apndFileUuid = fileUtils.getUUID();
			params.put("apndFileUuid", apndFileUuid);
			dao.insert("comm.base.apndFile.insertApndFileGrp", params);
		}
		
		String apndFileId = "";
		String apndFileNm = "";
		String apndFileExt = "";
		Long apndFileSize = (long) 0;
		String apndFilePath = "";
		
		for(MultipartFile eachFile : fileList) {
			HashMap<String, Object> fileParams = new HashMap<String, Object>();
			
			apndFileId = fileUtils.getUUID();
			apndFileNm = FileNameUtils.getBaseName(eachFile.getOriginalFilename());
			apndFileExt = FileNameUtils.getExtension(eachFile.getOriginalFilename());
			apndFileSize = eachFile.getSize();
//			apndFilePath = FileUtils.FILE_PATH + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());;
			apndFilePath = FileUtils.FILE_PATH + File.separator + new SimpleDateFormat("yyyyMMdd").format(new Date());;
			
			fileParams.put("apndFileUuid", apndFileUuid);
			fileParams.put("apndFileId", apndFileId);
			fileParams.put("apndFileNm", apndFileNm);
			fileParams.put("apndFileExt", apndFileExt);
			fileParams.put("apndFileSize", apndFileSize);
			fileParams.put("apndFilePath", apndFilePath);
			
			makeDirectory(apndFilePath);
			uploadFile(eachFile, apndFilePath, apndFileId, apndFileExt);
//====================================================================================================================================
//			core - uploadFile
//====================================================================================================================================			
//			try {
//				fileUtils.uplaodFile(eachFile, apndFilePath);
//				
//			} catch (IOException e) {
//				logger.debug("error in uploadFileConstructionWorkLog ...");
//				e.printStackTrace();
//			}
//====================================================================================================================================
			dao.insert("comm.base.apndFile.insertApndFile", fileParams);
		}
		return apndFileUuid;
	}
	
	public void uploadFile(MultipartFile file, String path, String uuId, String fileExt) {
//		String filePath = path + "/" + file.getOriginalFilename();
//		String filePath = path + "/" + uuId + "." + fileExt;
		String filePath = path + File.separator + uuId + "." + fileExt;
		
		try {
			file.transferTo(new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeDirectory(String path) {
		Path directoryPath = Paths.get(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		try {
			// 디렉토리 생성
			Files.createDirectories(directoryPath);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}