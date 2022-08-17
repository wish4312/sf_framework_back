package com.lsitc.core.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lsitc.core.exception.BisiExcp;
import com.lsitc.core.mybatis.AbstractMapper;
import com.lsitc.core.utils.ExcelUtils;

@Component(value = "commonDao")
public class CommonDao extends AbstractMapper implements ICommonDao, IStreamCommonDao {
    
    @Autowired
    ExcelUtils excelUtils;

    /**
     * @methodName  : selectList
     * @date        : 2021.02.19
     * @desc        : 여러건의 데이터를 List<Object>형태로 리턴한다.
     * @param <T>   : 반환하고자 하는 Object의 형태(class)
     * @param queryId : mapperId 
     * @param param : 인자
     * @return
     */
	@Override
	public <T> List<T> selectList(String queryId, Object param) {
		return super.selectList(queryId, param);
	}

	/**
	 * @methodName  : selectOne
	 * @date        : 2021.02.19
	 * @desc        : 단건의 데이터를 Object형태로 리턴한다. 없으면 null
	 * @param <T>   : 반환하고자 하는 Object의 형태(class)
	 * @param queryId
	 * @param param
	 * @return
	 */
	@Override
	public <T> T selectOne(String queryId, Object param) {
		return super.selectOne(queryId, param);
	}

    /**
     * @methodName  : insert
     * @date        : 2021.02.19
     * @desc        : 데이터를 추가한다.
     * @param queryId : mapperId
     * @param param : 인자
     * @return
     */
	@Override
	public int insert(String queryId, Object param) {
		return super.insert(queryId, param);
	}

	/**
	 * @methodName  : update
	 * @date        : 2021.02.19
	 * @desc        : 데이터를 업데이트하거나, SP를 호출할 수 있다.
     * @param queryId : mapperId
     * @param param : 인자
	 * @return
	 */
	@Override
	public int update(String queryId, Object param) {
		return super.update(queryId, param);
	}

    /**
     * @methodName  : update
     * @date        : 2021.02.19
     * @desc        : 데이터를 업데이트하거나, SP를 호출할 수 있다.
     * @param queryId : mapperId
     * @param param : 인자
     * @return
     */
	@Override
	public int delete(String queryId, Object param) {
		return super.delete(queryId, param);
	}

	/**
	 * @methodName         : selectExcel
	 * @date               : 2021.02.19
	 * @desc               : 쿼리의 결과를 엑셀로 반환한다. 
	 * @param queryId      : mapperId
	 * @param param        : 인자
	 * @param title        : 다운로드 될 엑셀의 파일명
	 * @param aliasHead    : Map형태의 header정보 
	 * @param request      : HttpServletRequest
	 * @param response     : HttpServletResponse
	 */
	@Override
	public void selectExcel(
			String queryId, Object param, String title, LinkedHashMap<String, String> aliasHead, 
			HttpServletRequest request, HttpServletResponse response) 
	{
		//queryResult
		List<Map<String, Object>> resultList = super.selectList(queryId, param);
		
		Map<String, Object> model = new HashMap<>();		
    	model.put("targeList", resultList);
    	model.put("headerMap", aliasHead);
    	model.put("fileName", title);
        try {
        	//make excel & download
        	excelUtils.render(model, request, response);
        } catch (Exception e) {
        	//FIXME 다국어처리
            throw new BisiExcp("엑셀생성 프로세스에 문제가 발생하였습니다.");
        } 
	}
	
	/**
	 * @methodName         : procudeCall
	 * @date               : 2022.06.01
	 * @desc               : PreparedStatement를 통한 쿼리 수행(프로시져 Call용)
	 * @param sql		   : 수행할 쿼리 (프로시져 SQL)
	 * @param param		   : PreparedStatement의 parameter( key : 숫자로 매칭 키의 순서, value : 대입할 값. )
	 * @return
	 * @throws Exception
	 */
	public int procudeCallUpdate(String sql, HashMap<Integer, Object> param) throws Exception {
		int retVal = -1;
		Connection conn = super.getSqlSession().getConnection();
		PreparedStatement pstmt = null;

		// 키로 정렬
		Object[] mapkey = param.keySet().toArray();
		Arrays.sort(mapkey);
		
		try {
			pstmt =	conn.prepareStatement(sql);
			for( Integer key : param.keySet() ){
				Object value = param.get(key);
				if(value instanceof Integer) {
					pstmt.setInt(key, (int) value);
				}else if(value instanceof String) {
					pstmt.setString(key, (String) value);
				}else if(value instanceof Long) {
					pstmt.setLong(key, (Long) value);
				}else if(value instanceof Date) {
					pstmt.setDate(key, (Date) value);
				}else {
					pstmt.setString(key, value.toString());
				}
			}
			retVal = pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				pstmt = null;
			}
		}
		
		return retVal;
	}
	

	@SuppressWarnings("unchecked")
	public boolean procudeCall(String sql, HashMap<Integer, Object> param) throws Exception {
		boolean retVal = false;
		SqlSession session = super.getSqlSessionFactory().openSession();
		Connection conn = session.getConnection();
		CallableStatement cstmt = null;

		// 키로 정렬
		Object[] mapkey = param.keySet().toArray();
		Arrays.sort(mapkey);
		
		try {
			cstmt =	conn.prepareCall(sql);
			for( Integer key : param.keySet() ){
				HashMap<String, Object> value = (HashMap<String, Object>) param.get(key);
				/*
				 { 1: {"id": "comId",   "mode": "IN", "type": "str", "val": "COM01"}, 
				   2: {"id": "toStrDt", "mode": "IN", "type": "function", "val": "DATE_FORMAT(NOW(), '%Y%h%D')"}, 
				   3: {"id": "userId",  "mode": "IN", "type": "str", "val": "3"}}
				 */
				if(value.containsKey("mode") && "OUT".equals(value.get("mode").toString().toUpperCase())) {
					// out
					if("STR".equals(value.get("type").toString().toUpperCase()) || "STRING".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.registerOutParameter(key, java.sql.Types.VARCHAR);
					}else if("INT".equals(value.get("type").toString().toUpperCase()) || "INTEGER".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.registerOutParameter(key, java.sql.Types.INTEGER);
					}else if("NUM".equals(value.get("type").toString().toUpperCase()) || "NUMERIC".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.registerOutParameter(key, java.sql.Types.NUMERIC);
					}else if("DATE".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.registerOutParameter(key, java.sql.Types.DATE);
					}else {
						cstmt.registerOutParameter(key, java.sql.Types.VARCHAR);
					}
				}else {
					if("STR".equals(value.get("type").toString().toUpperCase()) || "STRING".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.setString(key, value.get("val").toString());
					}else if("INT".equals(value.get("type").toString().toUpperCase()) || "INTEGER".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.setInt(key, (Integer) value.get("val"));
					}else if("NUM".equals(value.get("type").toString().toUpperCase()) || "NUMERIC".equals(value.get("type").toString().toUpperCase()) ) {
						cstmt.setDouble(key, (Double) value.get("val"));
					}else {
						cstmt.setString(key, value.get("val").toString());
					}
				}
			}
			retVal = cstmt.execute();
			
			conn.commit();
			// 리턴 값이 있는지 확인하고, 해당 값을 param에 넣어 준다.
			for( Integer key : param.keySet() ){
				HashMap<String, Object> value = (HashMap<String, Object>) param.get(key);
				
				if(value.containsKey("mode") && "OUT".equals(value.get("mode").toString().toUpperCase())) {
					if("STR".equals(value.get("type").toString().toUpperCase()) || "STRING".equals(value.get("type").toString().toUpperCase()) ) {
						value.put("val", cstmt.getString(key));
					}else if("INT".equals(value.get("type").toString().toUpperCase()) || "INTEGER".equals(value.get("type").toString().toUpperCase()) ) {
						value.put("val", cstmt.getInt(key));
					}else if("NUM".equals(value.get("type").toString().toUpperCase()) || "NUMERIC".equals(value.get("type").toString().toUpperCase()) ) {
						value.put("val", cstmt.getDouble(key));
					}else {
						value.put("val", cstmt.getString(key));
					}
				}
			}
			
			retVal = true;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally {
			try {
				cstmt.close();
			} catch (Exception e) {
				cstmt = null;
			}
			try {
				conn.close();
			} catch (Exception e) {
				conn = null;
			}
			try {
				session.close();
			} catch (Exception e) {
				session = null;
			}
		}
		
		return retVal;
	}
}
