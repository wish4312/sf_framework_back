package com.lsitc.core.share.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lsitc.core.base.BaseSvc;
import com.lsitc.core.exception.BisiExcp;
import com.lsitc.core.share.vo.ApndFileGrpVo;
import com.lsitc.core.share.vo.ApndFileVo;
import com.lsitc.core.share.vo.FileVo;
import com.lsitc.core.utils.FileUtils;

@Service
public class FileSvc extends BaseSvc {
	@Autowired
	private FileUtils fileUtils;
	
	/**
	 * @methodName  : selectUploadFile
	 * @date        : 2021.02.19
	 * @desc        : desc
	 * @param paramMap
	 * @return
	 */
	public List<FileVo> selectUploadFile(Map<String, String> paramMap) {
		return dao.selectList("core.uploadFile.selectUploadFile", paramMap);
	}

	/**
	 * @methodName  : saveUploadFileToTemp
	 * @date        : 2021.02.19
	 * @desc        : 파일을 임시폴더로 업로드 하고, DB에 저장한다.
	 * @param apndFileUuid : 첨부파일 그룹번호, 없으면 신규채번 
	 * @param menuId : 메뉴ID
	 * @param sortSeq : 정렬순서
	 * @param uploadfile : 업로드된 파일
	 * @return
	 */
	@Transactional
	public Object saveUploadFileToTemp(String apndFileUuid, String menuId, String sortSeq, MultipartFile uploadfile) {
		FileVo fileVo = new FileVo();

		//Path 셋팅(tmp로 보낸다.)
		fileVo.setApndFilePath(FileUtils.FILE_TMP_PATH);
		
		if ( "new".equals(apndFileUuid) ) {
			//그룹마저 신규라면 채번하여 생성한다
			apndFileUuid = fileUtils.getUUID();
			fileVo.setApndFileUuid(apndFileUuid);
			fileVo.setMenuId(menuId);
			//신규 파일의 사용여부는 무조건'n'이다
			fileVo.setUseFg("N");
			//그룹코드를 db에 추가하고
			dao.insert("core.uploadFile.insertUploadFileGrp", fileVo);
		} else {
			//신규가 아니라면 기존 grpID셋팅
			fileVo.setApndFileUuid(apndFileUuid);
		}
		
		String fileNm = uploadfile.getOriginalFilename();
		String ext = fileNm.substring(fileNm.lastIndexOf(".")+1); 
		//파일ID 셋팅
		String apndFileId = fileUtils.getUUID();
		fileVo.setApndFileId(apndFileId);
		//파일명 셋팅
		fileVo.setApndFileNm(fileNm.substring(0, fileNm.lastIndexOf(".")));
		//확장자 셋팅
		fileVo.setApndFileExt(ext);
		//사이즈 셋팅
		fileVo.setApndFileSize(new BigDecimal(uploadfile.getSize()));
		//순서 셋팅
		fileVo.setSortSeq(new BigDecimal(sortSeq));
		//사용여부는 Y로 셋팅한다.
		fileVo.setUseFg("Y");
		//신규파일 추가하고
		dao.insert("core.uploadFile.insertUploadFile", fileVo);
		
		//신규파일 tmp에 파일생성
		try {
			fileUtils.uplaodFile(uploadfile, FileUtils.FILE_TMP_PATH + File.separator + apndFileId);
		} catch (IOException e) {
			//FIXME 다국어 처리
			throw new BisiExcp("파일 업로드 시 에러 발생");
		}
		
		return fileVo;
	}
	
	/**
	 * @methodName  : saveUploadFile
	 * @date        : 2021.02.19
	 * @desc        : 업로드된 파일을 실제 파일저장 위치로 업로드 한다.
	 * @param apndFileUuid : 첨부파일 그룹번호
	 * @param menuId : 메뉴ID
	 * @param sortSeq : 정렬순서
	 * @param uploadfile : 업로드 파일
	 * @return
	 */
	@Transactional
	public Object saveUploadFile(String apndFileUuid, String menuId, String sortSeq, MultipartFile uploadfile) {
		FileVo fileVo = new FileVo();

		//Path 셋팅(tmp로 보낸다.)
		fileVo.setApndFilePath(FileUtils.FILE_PATH);
		//그룹코드
		fileVo.setApndFileUuid(apndFileUuid);
		
		String fileNm = uploadfile.getOriginalFilename();
		String ext = fileNm.substring(fileNm.lastIndexOf(".")+1); 
		//파일ID 셋팅
		String apndFileId = fileUtils.getUUID();
		fileVo.setApndFileId(apndFileId);
		//파일명 셋팅
		fileVo.setApndFileNm(fileNm.substring(0, fileNm.lastIndexOf(".")));
		//확장자 셋팅
		fileVo.setApndFileExt(ext);
		//사이즈 셋팅
		fileVo.setApndFileSize(new BigDecimal(uploadfile.getSize()));
		//순서 셋팅
		fileVo.setSortSeq(new BigDecimal(sortSeq));
		//사용여부는 Y로 셋팅한다.
		fileVo.setUseFg("Y");
		//신규파일 추가하고
		dao.insert("core.uploadFile.insertUploadFile", fileVo);
		
		//신규파일 tmp에 파일생성
		try {
			fileUtils.uplaodFile(uploadfile, FileUtils.FILE_TMP_PATH + File.separator + apndFileId);
		} catch (IOException e) {
			//FIXME 다국어 처리
			throw new BisiExcp("파일 업로드 시 에러 발생");
		}
		
		return fileVo;
	}

	/**
	 * @methodName  : deleteUploadFile
	 * @date        : 2021.02.19
	 * @desc        : 파일 삭제
	 * @param apndFileUuid : 첨부파일 그룹번호
	 * @param apndFileId : 첨부파일 번호
	 */
	@Transactional
	public void deleteUploadFile(String apndFileUuid, String apndFileId) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("apndFileUuid", apndFileUuid);
		paramMap.put("apndFileId", apndFileId);
		
		List<FileVo> fileList = dao.selectList("core.uploadFile.selectUploadFile", paramMap);
		
		StringBuilder sb = new StringBuilder();
		
		for (FileVo eachFile : fileList) {
			//FIXME DB에서 삭제안할 것인지?(YN처리)
			//db삭제
			dao.delete("core.uploadFile.deleteUploadFile", eachFile);
			
			sb.append(eachFile.getApndFilePath());
			sb.append(File.separator);	
			sb.append(eachFile.getApndFileId());
			//FIXME 물리파일 삭제안할 것인지?
			//물리file 삭제
			fileUtils.deleteFile(sb.toString());
			//stringBuilder 초기화
			sb.setLength(0); 
		}
	}

	/**
	 * 
	 * @methodName  : apndFileUpdateUseYn
	 * @date        : 2021.02.19
	 * @desc        : 업로드된 파일을 실제 파일저장 위치로 옮기고, 상태값을 변경한다.
	 * @param apndFileUuid
	 * @param useTableNm
	 * @param useFg
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void apndFileUpdateUseYn(String apndFileUuid, String useTableNm, String useFg) {
		ApndFileGrpVo apndFileGrpVo = new ApndFileGrpVo();
		apndFileGrpVo.setApndFileUuid(apndFileUuid);
		apndFileGrpVo.setUseTableNm(useTableNm);
		apndFileGrpVo.setUseFg(useFg);
		//grp사용여부 및 사용테이블 업데이트
		dao.update("core.uploadFile.updateUploadFileGrp02", apndFileGrpVo);		
		//옮길 파일 목록 조회
		List<ApndFileVo> fileList = dao.selectList("core.uploadFile.selectApndFile02", apndFileGrpVo);
		
		for(ApndFileVo eachFile : fileList ) {
			try {
				if("Y".equals(useFg)) {
					//사용할 때, 비지니스 로직에러로 인해 이미 처리된(존재하지 않는) 파일을 옮기지 않는다.
					if ( fileUtils.isExists(eachFile.getApndFilePath() + File.separator + eachFile.getApndFileId()) ) {
						//파일을 tmp-> real로 이동한 후 tmp삭제 
						fileUtils.copyFile(eachFile.getApndFilePath() + File.separator + eachFile.getApndFileId()
										   , FileUtils.FILE_PATH + File.separator + eachFile.getApndFileId()
										   , true);
					}
					//tmp->real로 변경
					eachFile.setApndFilePath(FileUtils.FILE_PATH);
				} 				
				//"Y"값 변경..
				dao.update("core.uploadFile.updateUploadFile", eachFile);
			} catch (IOException e) {
				e.printStackTrace();
				//TODO 다국어 처리
				throw new BisiExcp("파일 업로드 실패(tmp->real)");
			}
		}
	}
	
	/**
	 * @methodName  : copyFile
	 * @date        : 2021.02.19
	 * @desc        : 첨부파일을 복제한다.
	 * @param apndFileUuid : 첨부파일 그룹번호
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String copyFile(String apndFileUuid) {
		//복제할 파일 그룹목록
		ApndFileGrpVo fileGrpVo = dao.selectOne("core.uploadFile.selectApndFileGrp01", apndFileUuid);
		//복제할 파일목록
		List<ApndFileVo> fileList = dao.selectList("core.uploadFile.selectApndFile02", apndFileUuid);
		
		//신규 grpId채번하여 insert
		String destApndFileUuid = fileUtils.getUUID();
		fileGrpVo.setApndFileUuid(destApndFileUuid);
		dao.insert("core.uploadFile.insertUploadFileGrp", fileGrpVo);
		
		for(ApndFileVo eachFile : fileList) {
			//신규 fileId
			String destApndFileId = fileUtils.getUUID();
			try {
				//복제하고, 이전값은 삭제하지 않는다.
				fileUtils.copyFile(eachFile.getApndFilePath() + File.separator + eachFile.getApndFileId(),
								   eachFile.getApndFilePath() + File.separator + destApndFileId
								   , false);
			} catch (IOException e) {
				//TODO 다국어 처리
				throw new BisiExcp("파일 복제 실패");
			}
			eachFile.setApndFileUuid(destApndFileUuid);
			eachFile.setApndFileId(destApndFileId);
			dao.insert("core.uploadFile.insertUploadFile", eachFile);
		}
		
		return destApndFileUuid;
	}
	
	/**
	 * @methodName  : downloadFile
	 * @date        : 2021.02.19
	 * @desc        : 파일을 다운로드 한다.
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @param apndFileUuid : 첨부파일 그룹번호
	 * @param apndFileId : 파일번호
	 * @throws IOException
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String apndFileUuid, String apndFileId) throws IOException {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("apndFileUuid", apndFileUuid);
		paramMap.put("apndFileId", apndFileId);
		//다운받을 파일 조회
		ApndFileVo fileVo = dao.selectOne("core.uploadFile.selectApndFile01", paramMap);
		//파일 정보
        String fileNm = fileVo.getApndFileNm() + "." + fileVo.getApndFileExt();
        String filePath = fileVo.getApndFilePath() + File.separator + fileVo.getApndFileId();
		//다운로드
		fileUtils.sendFileStream(request, response, fileNm, filePath);
	}
	
	/**
	 * @methodName  : downloadFiles
	 * @date        : 2021.02.19
	 * @desc        : 다중 다운로드
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @param apndFileUuid : 첨부파일 그룹번호
	 * @param fileNm : 파일명
	 * @throws IOException
	 */
	public void downloadFiles(HttpServletRequest request, HttpServletResponse response, String apndFileUuid, String fileNm) throws IOException {
		//첨부파일 List
		List<ApndFileVo> list = dao.selectList("core.uploadFile.selectApndFile02", apndFileUuid);
		//다운로드
		fileUtils.sendFileStream(request, response, fileNm, list);
	}
}
