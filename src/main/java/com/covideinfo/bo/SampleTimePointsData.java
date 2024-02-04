package com.covideinfo.bo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleTimePointsData implements Comparable<SampleTimePointsData>{
	private String subjectNo;
	private String treatmentName;
	private List<String> timePoint;
	private List<TimePointInfo> timePointInfo = new ArrayList<>();
	
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public List<String> getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(List<String> timePoint) {
		this.timePoint = timePoint;
	}
	public List<TimePointInfo> getTimePointInfo() {
		return timePointInfo;
	}
	public void setTimePointInfo(List<TimePointInfo> timePointInfo) {
		this.timePointInfo = timePointInfo;
	}
	@Override
	public int compareTo(SampleTimePointsData o) {
		// TODO Auto-generated method stub
		return this.subjectNo.compareTo(o.subjectNo);
	}
	
}
