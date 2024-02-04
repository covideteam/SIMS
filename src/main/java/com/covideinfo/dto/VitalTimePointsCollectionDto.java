package com.covideinfo.dto;

import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.VitalTimePoints;

public class VitalTimePointsCollectionDto {
	
	private List<VitalTimePoints> vtpList;
	private List<SubjectVitalTimePointsData> svtpDataList;
	private  Map<String, Map<Long, String>> subjectDoseMap; //Subject, periodId, Done/NotDone
	private List<StudyPeriodMaster> smpList = null;
	public List<SubjectVitalTimePointsData> getSvtpDataList() {
		return svtpDataList;
	}
	public Map<String, Map<Long, String>> getSubjectDoseMap() {
		return subjectDoseMap;
	}
	public void setSvtpDataList(List<SubjectVitalTimePointsData> svtpDataList) {
		this.svtpDataList = svtpDataList;
	}
	public void setSubjectDoseMap(Map<String, Map<Long, String>> subjectDoseMap) {
		this.subjectDoseMap = subjectDoseMap;
	}
	public List<VitalTimePoints> getVtpList() {
		return vtpList;
	}
	public List<SubjectVitalTimePointsData> getSvtpData() {
		return svtpDataList;
	}
	public void setVtpList(List<VitalTimePoints> vtpList) {
		this.vtpList = vtpList;
	}
	public void setSvtpData(List<SubjectVitalTimePointsData> svtpDataList) {
		this.svtpDataList = svtpDataList;
	}
	public List<StudyPeriodMaster> getSmpList() {
		return smpList;
	}
	public void setSmpList(List<StudyPeriodMaster> smpList) {
		this.smpList = smpList;
	}
	
	
}
