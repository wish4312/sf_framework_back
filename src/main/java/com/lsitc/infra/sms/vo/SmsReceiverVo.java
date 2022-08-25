package com.lsitc.infra.sms.vo;

import java.util.HashMap;

public class SmsReceiverVo {
	private String mobile;
	private String name;
	private HashMap<String, String> receiveAddInfo;
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNote1(String note) {
		if(receiveAddInfo == null) {
			receiveAddInfo = new HashMap<String, String>();
		}
		
		if(note == null && receiveAddInfo.containsKey("note1") && receiveAddInfo.get("note1") != null) {
			receiveAddInfo.remove("note1");
		}else if(note != null) {
			receiveAddInfo.put("note1", note);
		}
	}
	
	public void setNote2(String note) {
		if(receiveAddInfo == null) {
			receiveAddInfo = new HashMap<String, String>();
		}
		
		if(note == null && receiveAddInfo.containsKey("note2") && receiveAddInfo.get("note2") != null) {
			receiveAddInfo.remove("note2");
		}else if(note != null) {
			receiveAddInfo.put("note2", note);
		}
	}
	
	public void setNote3(String note) {
		if(receiveAddInfo == null) {
			receiveAddInfo = new HashMap<String, String>();
		}
		
		if(note == null && receiveAddInfo.containsKey("note3") && receiveAddInfo.get("note3") != null) {
			receiveAddInfo.remove("note3");
		}else if(note != null) {
			receiveAddInfo.put("note3", note);
		}
	}
	
	public void setNote4(String note) {
		if(receiveAddInfo == null) {
			receiveAddInfo = new HashMap<String, String>();
		}
		
		if(note == null && receiveAddInfo.containsKey("note4") && receiveAddInfo.get("note4") != null) {
			receiveAddInfo.remove("note4");
		}else if(note != null) {
			receiveAddInfo.put("note4", note);
		}
	}
	
	public void setNote5(String note) {
		if(receiveAddInfo == null) {
			receiveAddInfo = new HashMap<String, String>();
		}
		
		if(note == null && receiveAddInfo.containsKey("note5") && receiveAddInfo.get("note5") != null) {
			receiveAddInfo.remove("note5");
		}else if(note != null) {
			receiveAddInfo.put("note5", note);
		}
	}
	
//	public HashMap<String, String> getReceiveAddInfo(){
//		if(receiveAddInfo == null) {
//			receiveAddInfo = new HashMap<String, String>();
//		}
//		return this.receiveAddInfo;
//	}
//	
//	public void setReceiveAddInfo(HashMap<String, String> receiveAddInfo) {
//		this.receiveAddInfo = receiveAddInfo;
//	}
//	
//	public void addReceiveAddInfo(String key, String value) {
//		if(receiveAddInfo == null) {
//			receiveAddInfo = new HashMap<String, String>();
//		}
//		this.receiveAddInfo.put(key, value);
//	}
//	
//	public void removeReceiveAddInfo(String key) {
//		if(receiveAddInfo != null && receiveAddInfo.containsKey(key)) {
//			receiveAddInfo.remove(key);
//		}
//	}
	
	public HashMap<String, String> getReceiver(){
		HashMap<String, String> retVal = new HashMap<String, String>();
		retVal.put("mobile", this.mobile);
		
		if(this.name != null && !"".equals(this.name)) {
			retVal.put("name", this.name);
		}
		
		if(this.receiveAddInfo != null && this.receiveAddInfo.size() > 0) {
			for( String strKey : this.receiveAddInfo.keySet() ){
				String strValue = this.receiveAddInfo.get(strKey);
				retVal.put(strKey, strValue);
			}
		}

		return retVal;
	}
}
