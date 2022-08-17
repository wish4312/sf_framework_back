package com.lsitc.core.base;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * CamelHashMap
 * @desc : mybatis에서 service로 Map형태로 리턴할 때, column(key) 명을 camleCase로 변환하여 반환하도록 하는 객체
 *
 */
public class CamelHashMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 3973097367004179053L;
	
	/**
	 * @methodName  : put
	 * @date        : 2021.02.19
	 * @desc        : HasMap의 put을 override하여 camleCase로 변환된 key로 셋팅한다.
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName((String) key), value);
	}
}