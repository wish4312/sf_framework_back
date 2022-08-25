package com.lsitc.infra.sms.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lsitc.global.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsVo extends BaseVo<SmsVo> {
	
	private String title;
	private String message;
	private String sender;
	private List<SmsReceiverVo> receiver;
	private String address_books;
	
	private List<String> receiverList = new ArrayList<String>();
	private String sms_type = "NORMAL";
	private String start_reserve_time;
	private String end_reserve_time;
	
	private int remained_count = 1;
	
	private int returnURL = -1;
	
	private String attaches;
	
	public void addReceiver(String phoneno) {
		if(receiver == null) {
			this.receiver = new ArrayList<SmsReceiverVo>();
		}
		
		SmsReceiverVo mobile = new SmsReceiverVo();
		mobile.setMobile(phoneno);
		
		this.receiver.add(mobile);
	}
	public void setReceiver(int idx, String phoneno) {
		if(receiver == null) {
			this.receiver = new ArrayList<SmsReceiverVo>();
		}
		
		if(this.receiver.size() - 1 > idx) {
			this.receiver.get(idx).setMobile(phoneno);
		}
	}
	
	public void addReceiver(SmsReceiverVo smsReceiver) {
		if(receiver == null) {
			this.receiver = new ArrayList<SmsReceiverVo>();
		}
		this.receiver.add(smsReceiver);
	}
	
	public void setReceiver(int idx, SmsReceiverVo smsReceiver) {
		if (receiver == null) {
			this.receiver = new ArrayList<SmsReceiverVo>();
		}

		if (this.receiver.size() - 1 > idx) {
			this.receiver.set(idx, smsReceiver);
		}
		
	}

//	public void addReceiver(String phoneno, HashMap<String, String> receiveAddInfo) {
//		if(receiver == null) {
//			this.receiver = new ArrayList<SmsReceiverVo>();
//		}
//
//		SmsReceiverVo mobile = new SmsReceiverVo();
//		mobile.setMobile(phoneno);
//		mobile.setReceiveAddInfo(receiveAddInfo);
//		this.receiver.add(mobile);
//	}
	
//	public void setReceiver(int idx, String phoneno, HashMap<String, String> receiveAddInfo) {
//		if(receiver == null) {
//			this.receiver = new ArrayList<SmsReceiverVo>();
//		}
//		
//		if(this.receiver.size() - 1 > idx) {
//			this.receiver.get(idx).setMobile(phoneno);
//			this.receiver.get(idx).setReceiveAddInfo(receiveAddInfo);
//		}
//		
//	}
	
	public List<HashMap<String, String>> getReceiver(){
		List<HashMap<String, String>> retVal = new ArrayList<HashMap<String, String>>();
		
		for(SmsReceiverVo tmpReceive : this.receiver) {
			retVal.add(tmpReceive.getReceiver());
		}
		
		return retVal;
	}
}
