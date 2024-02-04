package com.covideinfo.eform.service;

public class EFormGroupElementSheet {
	private int lineNo =  1;
	private String sno = "";
	private String name ="NAME";
	private String desc = "DESC";
	private String columnNo = "COLUMN NO";
	private String rowNo = "ROW NO";
	private String type = "TYPE";
	private String responseType = "RESPONSE TYPE";	
	private String display = "DISPLAY";
	private String values = "VALUES";
	private String pattren="PATTREN";
	private String required = "REQUIRED";
	private String sectionName = "SECTION NAME";
	private String groupName = "GROUP NAME";
	private String dataType = "Data Type";
	private String discrepnecy = "Discrepnecy";
	private String commentRequired = "Comment Required";
	private String commentValue = "Comment Value";
	private String targetField = "Target Field";
	private String typeOfTime = "";
	private String visibility = "";
	private String roles = "";
	public EFormGroupElementSheet() {
		super();
	}
	public EFormGroupElementSheet(int lineNo) {
		super();
		this.lineNo = lineNo;
		this.name = "<font color='red'>Required</font>";
		this.desc = "";
		this.columnNo = "<font color='red'>Required</font>";
		this.rowNo = "<font color='red'>Required</font>";
		this.type = "<font color='red'>Required</font>";
		this.required = "<font color='red'>Required</font>";
		this.sectionName = "<font color='red'>Required</font>";
		this.groupName = "<font color='red'>Required</font>";
		this.dataType = "<font color='red'>Required</font>";
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
	public String getColumnNo() {
		return columnNo;
	}
	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String getPattren() {
		return pattren;
	}
	public void setPattren(String pattren) {
		this.pattren = pattren;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getDiscrepnecy() {
		return discrepnecy;
	}
	public void setDiscrepnecy(String discrepnecy) {
		this.discrepnecy = discrepnecy;
	}
	public String getCommentRequired() {
		return commentRequired;
	}
	public void setCommentRequired(String commentRequired) {
		this.commentRequired = commentRequired;
	}
	public String getCommentValue() {
		return commentValue;
	}
	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}
	public String getTargetField() {
		return targetField;
	}
	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}
	public String getTypeOfTime() {
		return typeOfTime;
	}
	public void setTypeOfTime(String typeOfTime) {
		this.typeOfTime = typeOfTime;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}