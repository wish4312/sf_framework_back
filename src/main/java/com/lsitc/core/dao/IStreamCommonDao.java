package com.lsitc.core.dao;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IStreamCommonDao {
	public void selectExcel(
		String queryId, Object paramMap, String title, LinkedHashMap<String, String> aliasHead,
		HttpServletRequest request, HttpServletResponse response);
}
