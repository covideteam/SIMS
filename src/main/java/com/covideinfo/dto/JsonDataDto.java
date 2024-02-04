package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JsonDataDto implements Serializable {
	
	private String result;
	private String msg;
	public String getResult() {
		return result;
	}
	public String getMsg() {
		return msg;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
