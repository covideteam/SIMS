package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MessageDto implements Serializable {
	private boolean msgFlag = false;
	private String mealsMsg;
	public boolean isMsgFlag() {
		return msgFlag;
	}
	public String getMealsMsg() {
		return mealsMsg;
	}
	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}
	public void setMealsMsg(String mealsMsg) {
		this.mealsMsg = mealsMsg;
	}
	
}
