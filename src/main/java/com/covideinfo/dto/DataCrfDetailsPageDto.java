package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;

public class DataCrfDetailsPageDto implements Serializable {
	
	
	private static final long serialVersionUID = 127610931141763658L;
	private List<StudyMaster> smList;
	private List<StudySubjects> studySubList;
	private List<StudyPeriodMaster> spmList;
	private List<LanguageSpecificGlobalActivityDetails> lspgaList;
	private List<StudyVolunteerReporting> stdVolList;
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public List<StudySubjects> getStudySubList() {
		return studySubList;
	}
	public void setStudySubList(List<StudySubjects> studySubList) {
		this.studySubList = studySubList;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public List<LanguageSpecificGlobalActivityDetails> getLspgaList() {
		return lspgaList;
	}
	public void setLspgaList(List<LanguageSpecificGlobalActivityDetails> lspgaList) {
		this.lspgaList = lspgaList;
	}
	public List<StudyVolunteerReporting> getStdVolList() {
		return stdVolList;
	}
	public void setStdVolList(List<StudyVolunteerReporting> stdVolList) {
		this.stdVolList = stdVolList;
	}
	
	
	
	
	
	
	

}
