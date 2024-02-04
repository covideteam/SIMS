package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjectDeviationReviewDetails;
import com.covideinfo.model.StudySubjectDeviations;

@SuppressWarnings("serial")
public class DeviationsDto implements Serializable {
	private StudyMaster study;
	private List<StudySubjectDeviations> subDevList;
	private List<StudySubjectDeviationReviewDetails> ssdrdList;

	public List<StudySubjectDeviations> getSubDevList() {
		return subDevList;
	}

	public void setSubDevList(List<StudySubjectDeviations> subDevList) {
		this.subDevList = subDevList;
	}

	public StudyMaster getStudy() {
		return study;
	}

	public void setStudy(StudyMaster study) {
		this.study = study;
	}

	public List<StudySubjectDeviationReviewDetails> getSsdrdList() {
		return ssdrdList;
	}

	public void setSsdrdList(List<StudySubjectDeviationReviewDetails> ssdrdList) {
		this.ssdrdList = ssdrdList;
	}
	
	

}
