package com.covideinfo.eform.service;

public class EFormSheet {
	private int lineNo =  1;
	private String name ="NAME";
	private String desc = "DESC";
	private String type = "TYPE";
	private String gender = "GENDER";
	private String version = "VERSION";
	private String eformKey;
	public EFormSheet() {
		super();
	}
	
	
	public EFormSheet(int lineNo) {
		super();
		this.lineNo = lineNo;
		this.name = "<font color='red'>Required</font>";
		this.desc = "<font color='red'>Required</font>";
		this.type = "<font color='red'>Required</font>";
		this.gender = "<font color='red'>Required</font>";
		this.version = "<font color='red'>Required</font>";
	}


	public String getEformKey() {
		return eformKey;
	}


	public void setEformKey(String eformKey) {
		this.eformKey = eformKey;
	}


	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
