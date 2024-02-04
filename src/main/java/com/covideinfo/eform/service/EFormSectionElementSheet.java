package com.covideinfo.eform.service;

public class EFormSectionElementSheet {
	private int lineNo =  1;
	private String sno = "";
	private String name ="NAME";
	private String leftDesc = "LEFT DESC";
	private String rightDesc = "RIGHT DESC";
	private String middeDesc = "MIDDLE DESC";
	private String totalDesc = "TOTAL DESC";
	private String columnNo = "COLUMN NO";
	private String rowNo = "ROW NO";
	private String topDesc = "TOP DESC";
	private String bottomDesc="BOTTOM DESC";
	private String type = "TYPE";
	private String responseType = "RESPONSE TYPE";	
	private String display = "DISPLAY";
	private String values = "VALUES";
	private String pattren="PATTREN";
	private String required = "REQUIRED";
	private String sectionName = "SECTION NAME";
	private String dataType = "Data Type";
	private String discrepnecy = "Discrepnecy";
	private String commentRequired = "Comment Required";
	private String commentValue = "Comment Value";
	private String targetField = "Target Field";
	private String typeOfTime = "";
	private String visibility = "";
	private String roles = "";
	private String controllerWidth;
	private String lableWidth;
	private String columnWidth;
	private String pdfControllerWidth;
	private String pdflLableWidth;
	private String pdfColumnWidth;
	
	
	public EFormSectionElementSheet() {
		super();
	}
	public EFormSectionElementSheet(int lineNo) {
		super();
		this.lineNo = lineNo;
		this.name = "<font color='red'>Required</font>";
		this.leftDesc = "";
		this.rightDesc = "";
		this.middeDesc = "";
		this.totalDesc = "";
		this.columnNo = "<font color='red'>Required</font>";
		this.rowNo = "<font color='red'>Required</font>";
		this.type = "<font color='red'>Required</font>";
		this.required = "<font color='red'>Required</font>";
		this.sectionName = "<font color='red'>Required</font>";
		this.dataType = "<font color='red'>Required</font>";
		this.discrepnecy = "<font color='red'>Required</font>";
		this.commentRequired = "<font color='red'>Required</font>";
		this.visibility = "<font color='red'>Required</font>";
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
	public String getLeftDesc() {
		return leftDesc;
	}
	public void setLeftDesc(String leftDesc) {
		this.leftDesc = leftDesc;
	}
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	public String getMiddeDesc() {
		return middeDesc;
	}
	public void setMiddeDesc(String middeDesc) {
		this.middeDesc = middeDesc;
	}
	public String getTotalDesc() {
		return totalDesc;
	}
	public void setTotalDesc(String totalDesc) {
		this.totalDesc = totalDesc;
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
	public String getTopDesc() {
		return topDesc;
	}
	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
	}
	public String getBottomDesc() {
		return bottomDesc;
	}
	public void setBottomDesc(String bottomDesc) {
		this.bottomDesc = bottomDesc;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getControllerWidth() {
		return controllerWidth;
	}
	public void setControllerWidth(String controllerWidth) {
		this.controllerWidth = controllerWidth;
	}
	public String getLableWidth() {
		return lableWidth;
	}
	public void setLableWidth(String lableWidth) {
		this.lableWidth = lableWidth;
	}
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	public String getPdfControllerWidth() {
		return pdfControllerWidth;
	}
	public void setPdfControllerWidth(String pdfControllerWidth) {
		this.pdfControllerWidth = pdfControllerWidth;
	}
	public String getPdflLableWidth() {
		return pdflLableWidth;
	}
	public void setPdflLableWidth(String pdflLableWidth) {
		this.pdflLableWidth = pdflLableWidth;
	}
	public String getPdfColumnWidth() {
		return pdfColumnWidth;
	}
	public void setPdfColumnWidth(String pdfColumnWidth) {
		this.pdfColumnWidth = pdfColumnWidth;
	}
	
	
}
