package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.StudyMaster;

@SuppressWarnings("serial")
public class DosingDto implements Serializable {
	
	private List<DeviationMessage> devMsgList;
    private StudyMaster study;
    private Map<String, Long> devionCodeMap;//developerCode, deviationId
	public List<DeviationMessage> getDevMsgList() {
		return devMsgList;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setDevMsgList(List<DeviationMessage> devMsgList) {
		this.devMsgList = devMsgList;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public Map<String, Long> getDevionCodeMap() {
		return devionCodeMap;
	}
	public void setDevionCodeMap(Map<String, Long> devionCodeMap) {
		this.devionCodeMap = devionCodeMap;
	}
	
		
}
