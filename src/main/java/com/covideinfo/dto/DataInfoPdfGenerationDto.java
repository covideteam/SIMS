package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class DataInfoPdfGenerationDto implements Serializable {
	
	private StudyMaster study;
	private List<SubjectRandamization> subRandomList;
	private List<StudySubjects> stdSubjList;
	private UserMaster user;
	
	

	public List<SubjectRandamization> getSubRandomList() {
		return subRandomList;
	}

	public void setSubRandomList(List<SubjectRandamization> subRandomList) {
		this.subRandomList = subRandomList;
	}

	public List<StudySubjects> getStdSubjList() {
		return stdSubjList;
	}

	public void setStdSubjList(List<StudySubjects> stdSubjList) {
		this.stdSubjList = stdSubjList;
	}

	public StudyMaster getStudy() {
		return study;
	}

	public void setStudy(StudyMaster study) {
		this.study = study;
	}

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}
	

}
