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
package com.lsitc.domain.base.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsitc.global.common.BaseSvc;
import com.lsitc.global.common.CamelHashMap;


@Service
public class NoticeMngSvc extends BaseSvc {
    /**
     * @methodName  : selectPost
     * @date        : 2021.07.28
     * @desc        : 게시물 목록 조회
     * @param paramMap
     * @return
     */
    public List<CamelHashMap> selectPostList(HashMap<String, Object> param){
        return dao.selectList("comm.base.noticeMng.selectPostList", param);
    }
    
    /**
     * @methodName  : selectPost
     * @date        : 2021.07.28
     * @desc        : 게시물 조회
     * @param paramMap
     * @return
     */
    public List<CamelHashMap> selectPost(HashMap<String, Object> param){
        return dao.selectList("comm.base.noticeMng.selectPost", param);
    }
    
    /**
     * @methodName  : insertPost
     * @date        : 2021.07.28
     * @desc        : 게시물 입력
     * @param paramMap
     * @return
     */
    public void insertPost(HashMap<String, Object> param) {
//    	logger.debug("parameter...");
//    	Iterator it = param.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            logger.info(pair.getKey() + " = " + pair.getValue());
//        }
        dao.insert("comm.base.noticeMng.insertPost", param);
    }

    /**
     * @methodName  : insertRplPost
     * @date        : 2021.07.28
     * @desc        : 게시물 답글 입력
     * @param paramMap
     * @return
     */
    public void insertRplPost(HashMap<String, Object> param) {
        dao.insert("comm.base.noticeMng.insertRplPost", param);
    }
    
    /**
     * @methodName  : updatePost
     * @date        : 2021.07.28
     * @desc        : 게시물 수정
     * @param paramMap
     * @return
     */
    public void updatePost(HashMap<String, Object> param) {
        dao.update("comm.base.noticeMng.updatePost", param);
    }
    
    /**
     * @methodName  : updateRplPostGrpOrd
     * @date        : 2021.07.28
     * @desc        : 게시물 답글 추가될때 POST_GRP_ORD UPDATE
     * @param paramMap
     * @return
     */
    public void updateRplPostGrpOrd(HashMap<String, Object> param) {
        dao.update("comm.base.noticeMng.updateRplPostGrpOrd", param);
    }
    
    /**
     * @methodName  : updateViewCnt
     * @date        : 2021.07.28
     * @desc        : 게시물 조회수 업데이트
     * @param paramMap
     * @return
     */
    public void updateViewCnt(HashMap<String, Object> param) {
        dao.update("comm.base.noticeMng.updateViewCnt", param);
    }
    
	/**
	 * 
	 * @methodName  : deletePostList
	 * @date        : 2021.07.13
	 * @desc        : 게시물 삭제
	 * @param paramMap
	 * @return
	 */
    @Transactional
	public void deletePost(HashMap<String, Object> param){
		//logger.debug("###########param : {}", param);
		dao.delete("comm.base.noticeMng.deletePost", param);
		if(param.get("apndFileUuid").toString() != null || param.get("apndFileUuid").toString() != ""){
	        dao.delete("comm.base.apndFile.deleteCommApndFileGrp", param);       
	        dao.delete("comm.base.apndFile.deleteCommApndFile", param);			
		}
	}
	
}