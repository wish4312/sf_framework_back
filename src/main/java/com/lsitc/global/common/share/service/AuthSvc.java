package com.lsitc.global.common.share.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lsitc.global.common.BaseSvc;

@Service
public class AuthSvc extends BaseSvc {
	
	
    /**
     * @methodName  : selectAuthMenu
     * @date        : 2021.02.19
     * @desc        : [캐싱처리]권한이 있는 메뉴 정보를 반환한다.
     * @param menuId : 메뉴ID
     * @param userNo : 사용자No
     * @return
     */
    //FIXME 캐싱됨
	@Cacheable(value="selectAuthMenu", key="{#menuId, #userNo}")
	public Map<String, Object> selectAuthMenu(String menuId, String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuId", menuId);
		paramMap.put("userNo", userNo);
		return dao.selectOne("core.auth.selectMenuAuth", paramMap);
	}
	
	/**
	 * @methodName  : selectAuthMenuBreadcrumb
	 * @date        : 2021.02.19
	 * @desc        : breadcrumb 정보를 받아준다.
	 * @param menuId
	 * @param userNo
	 * @return
	 */
	//FIXME 향후 수정..
	public List<Map<String, Object>> selectAuthMenuBreadcrumb(String menuId, String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuId", menuId);
		paramMap.put("userNo", userNo);
		return dao.selectList("core.auth.selectAuthMenuBreadcrumb", paramMap);
	}
}
