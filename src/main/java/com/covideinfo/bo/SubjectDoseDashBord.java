package com.covideinfo.bo;

import java.util.HashMap;
import java.util.Map;

import com.covideinfo.model.SubjectDoseTimePoints;

public class SubjectDoseDashBord implements Comparable<SubjectDoseDashBord> {
	String timepoint = "";
	String treatment = "";
	int timepointNo;
	Map<Integer, SubjectDoseTimePoints> timePointSubjectData = new HashMap<>();

	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public Map<Integer, SubjectDoseTimePoints> getTimePointSubjectData() {
		return timePointSubjectData;
	}

	public void setTimePointSubjectData(Map<Integer, SubjectDoseTimePoints> timePointSubjectData) {
		this.timePointSubjectData = timePointSubjectData;
	}

	public int getTimepointNo() {
		return timepointNo;
	}

	public void setTimepointNo(int timepointNo) {
		this.timepointNo = timepointNo;
	}

	@Override
	public int compareTo(SubjectDoseDashBord o) {
		if (this.timepointNo > o.timepointNo) {
			return 1;
		} else if (this.timepointNo == o.timepointNo) {
			return 0;
		} else {
			return -1;
		}
	}
}
