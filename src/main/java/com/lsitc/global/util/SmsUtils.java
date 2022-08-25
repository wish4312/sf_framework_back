package com.lsitc.global.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.lsitc.infra.sms.vo.SmsReceiverVo;
import com.lsitc.infra.sms.vo.SmsVo;

@Component
public class SmsUtils {
    protected final static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
    
    private static String SMS_USER_ID = "test";
    private static String SMS_API_KEY = "test"; //"test";
    private static String SMS_SND_URL = "http://localhost";

    @Value("${sms.userid:test}")
    private void setSmsUserId(String smsUserId) {
    	SMS_USER_ID = smsUserId;
    };
    @Value("${sms.apikey:test")
    private void setSmsApiKey(String smsApiKey) {
    	SMS_API_KEY = smsApiKey;
    };
    @Value("${sms.sndUrl:http://localhost}")
    private void setSmsSndUrl(String smsSndUrl) {
    	SMS_SND_URL = smsSndUrl;
    };

    public static String sendSms(SmsVo smsInfo)  throws Exception {
    	HashMap<String, Object> retVal = new HashMap<String, Object>();
    	
    	HttpHeaders httpHeaders = RestUtils.getDefaultHttHeaders(); //기본적인 header를 만들어 리턴하되, 필요에 따라 customizing할 수 있음.
        httpHeaders.add("Cache-Control", "no-cache");
        httpHeaders.add("Content-Type", "application/json;charset=utf-8");
        httpHeaders.add("Accept", "application/json");
        
        Map<String, Object> bodyMap = new HashMap<>();
        
        if(!"".equals(smsInfo.getTitle())  && smsInfo.getTitle() != null) {
        	bodyMap.put("title", smsInfo.getTitle());
        }

        if(!"".equals(smsInfo.getMessage())  && smsInfo.getMessage() != null) {
        	bodyMap.put("message", smsInfo.getMessage());
        }

        if(!"".equals(smsInfo.getSender())  && smsInfo.getSender() != null) {
        	bodyMap.put("sender", smsInfo.getSender());
        }

        bodyMap.put("receiver", smsInfo.getReceiver());
        
        bodyMap.put("username", SMS_USER_ID);
        bodyMap.put("key", SMS_API_KEY);
        bodyMap.put("type", "java");
        
        
        retVal = RestUtils.requestRestCall(SMS_SND_URL, HttpMethod.POST, httpHeaders, bodyMap, 10, HashMap.class);
    	
    	
    	return retVal.toString();
    }
    
    public static void testSendSms() throws Exception {

    	SmsVo test = new SmsVo();
    	test.setTitle("SMS 발송 테스트");
    	test.setMessage("SMS 발송 테스트 [$MOBILE]. [$NOTE1]"); // message의 가변 값은 NAME, MOBILE, NOTE1~NOTE5	까지 설정 가능
    	test.setSender("01032727132"); // 발송번호는 사전에 등록되어이는 번호만 사용 가능.. 추가 필요시 사전 등록 처리 필수.
    	
    	SmsReceiverVo tmpSmsReceiverVo = new SmsReceiverVo();
    	tmpSmsReceiverVo.setMobile("01011112222"); // 수신 받는 사람 전화번호.
    	tmpSmsReceiverVo.setName("홍길동");
    	tmpSmsReceiverVo.setNote1("dfadfas"); // note는 1~5번까지 설정 가능.
    	test.addReceiver(tmpSmsReceiverVo);
//    	test.addReceiver("01033335555"); // 핸드폰 번호만 사용해서 발송시는 폰번호만 등록.
    	
    	String retVal = SmsUtils.sendSms(test);
    	
    	logger.debug("testSendSms : " + retVal);
    }
}
