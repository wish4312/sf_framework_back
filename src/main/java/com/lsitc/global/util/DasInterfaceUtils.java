package com.lsitc.global.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.lsitc.global.common.CamelHashMap;

@Component
public class DasInterfaceUtils {
	protected final static Logger logger = LoggerFactory.getLogger(DasInterfaceUtils.class);
	
	@Value("${api.das.mode:http}")
	private static String apiDasMode;
	
	@Value("${api.das.host:localhost}")
	private static String apiDasHost;
	
	@Value("${api.das.port:30011}")
	private static String apiDasPort;
	
	@Value("${api.das.query.dps.prefix:/tsdb/query/fems/v1/dps/tag}")
	private static String apiDasDpsPrefix;
	
	@Value("${api.das.query.last.prefix:/tsdb/query/fems/v1/last/tag}")
	private static String apiDasLastPrefix;
	

	@SuppressWarnings("static-access")
	@Value("${api.das.mode:http}")
	private void setApiDasMode(String apiDasMode) {
		this.apiDasMode = apiDasMode;
	};

	@SuppressWarnings("static-access")
	@Value("${api.das.host:localhost}")
	private void setApiDasHost(String apiDasHost) {
		this.apiDasHost = apiDasHost;
	};

	@SuppressWarnings("static-access")
	@Value("${api.das.port:30011}")
	private void setApiDasPort(String apiDasPort) {
		this.apiDasPort = apiDasPort;
	};

	@SuppressWarnings("static-access")
	@Value("${api.das.query.dps.prefix:/tsdb/query/fems/v1/dps/tag}")
	private void setApiDasDpsPrefix(String apiDasDpsPrefix) {
		this.apiDasDpsPrefix = apiDasDpsPrefix;
	};

	@SuppressWarnings("static-access")
	@Value("${api.das.query.last.prefix:/tsdb/query/fems/v1/last/tag}")
	private void setApiDasLastPrefix(String apiDasLastPrefix) {
		this.apiDasLastPrefix = apiDasLastPrefix;
	};
	
	public static enum DAS_IF_TYPE{
		LAST, DPS
	}
	
	public static JSONObject getDasQuery(DAS_IF_TYPE type, String queryTag) {
		JSONObject dasJsonData = null;
		String apiDasPrefix = apiDasLastPrefix;
		if(DAS_IF_TYPE.LAST == type) {
			apiDasPrefix = apiDasLastPrefix;
		}else if(DAS_IF_TYPE.DPS == type) {
			apiDasPrefix = apiDasDpsPrefix;
		}
		String url = String.format("%s://%s:%s%s/%s", apiDasMode, apiDasHost, apiDasPort, apiDasPrefix, queryTag);
		HttpHeaders httpHeaders = RestUtils.getDefaultHttHeaders(); //기본적인 header를 만들어 리턴하되, 필요에 따라 customizing할 수 있음.

        Map<String, Object> bodyMap = new HashMap<>();
        
		try {
			String reqBody = RestUtils.requestRestCall(url, HttpMethod.GET, httpHeaders, bodyMap, 10, String.class);
			JSONObject dasData =  new JSONObject(reqBody);
			dasJsonData = (JSONObject) dasData.get("results");
		}catch(Exception e) {
			dasJsonData = new JSONObject("{'retCd':'-1', 'retMsg':'" + e.getMessage() + "'}");
        }
		return dasJsonData;
	}
	
	public static List<CamelHashMap> getDasLastData(List<CamelHashMap> tagInfo){
		List<CamelHashMap> result = tagInfo;
		
		JSONObject dasJsonData = getDasQuery(DasInterfaceUtils.DAS_IF_TYPE.LAST, result.get(0).get("mainTagMax").toString());

        String datetime = null;
        String searchDate = null;
		if( result.size() > 0) {

	        boolean isGetDate = false;
	        Long timestamp = null;
	        for(int idx = 0; idx < result.size(); idx ++) {
	        	JSONObject sub01 = null;
	        	JSONObject sub02 = null;
	        	JSONObject data = null;
	        	JSONObject data01 = null;
        		Double addupVal = null;
        		Double instantVal = null;
        		
        		// 조회 결과의 시간 및 Timestamp 가져오기
    	        if(result.get(idx).containsKey("sub01") && dasJsonData.has(result.get(idx).get("sub01").toString())) {
    	        	sub01 = dasJsonData.getJSONObject(result.get(idx).get("sub01").toString());
    	        	if(result.get(idx).containsKey("sub02") && sub01.has(result.get(idx).get("sub02").toString())) {
    	        		sub02 = sub01.getJSONObject(result.get(idx).get("sub02").toString());
    	        		// 최상위 Row의 처리 시간처리
    	        		if(!isGetDate) {
	    	        		if(sub02.has("service")) {
			        			timestamp = sub02.getJSONObject("service").getLong("timestamp");
			        			datetime = sub02.getJSONObject("service").getString("datetime");
			        			isGetDate = true;
	    	        		}
    	        		}
    	        		
    	        		if(sub02.has("data")) {
    	        			data = sub02.getJSONObject("data");
    	        			if(result.get(idx).containsKey("data01") && data.has(result.get(idx).get("data01").toString())) {
    	        				data01 = data.getJSONObject(result.get(idx).get("data01").toString());

            					addupVal = data01.getDouble(result.get(idx).get("data02").toString());
            					instantVal =  data01.getDouble(result.get(idx).get("data02").toString());
            					
    	        				if(result.get(idx).containsKey("data02") && "KWH".equals(result.get(idx).get("data02").toString())) {
    	        					instantVal = data01.getDouble("KW");
    	        					instantVal = instantVal / 1000;
    	        				}
    	        			}
    	        		}
    	        	}
    	        }

    	        result.get(idx).put("ADDUP_VAL", addupVal);
    	        result.get(idx).put("INSTANT_VAL", instantVal);
	        }
	        
		}else if(result.size() > 0) {
			for(int idx = 0; idx < result.size(); idx ++) {
				result.get(idx).put("ADDUP_VAL", 0.00001);
				result.get(idx).put("INSTANT_VAL", 0.00001);
        	}

	        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" , Locale.KOREA );
	        searchDate = sdf.format( new Date());
	        datetime = sdf.format( new Date());
		}
		
		for(int idx = 0; idx < result.size(); idx ++) {
			result.get(idx).put("searchDate", searchDate);
			result.get(idx).put("datetime", datetime);
		}
		
		return result;
	}

}
