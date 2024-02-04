package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.Volunteer;

@SuppressWarnings("serial")
public class StudyExecutionDto implements Serializable {
	private LanguageSpecificGlobalActivityDetails lsga;
	private List<StudyVolunteerReporting> svrList;
	private List<StudyPeriodMaster> spmList;
	private List<StudySubjectPeriods> sspList;
	private List<SubjectRandamization> srzList;
	private Long treatmentOneId;
	
	public LanguageSpecificGlobalActivityDetails getLsga() {
		return lsga;
	}
	public void setLsga(LanguageSpecificGlobalActivityDetails lsga) {
		this.lsga = lsga;
	}
	public List<StudyVolunteerReporting> getSvrList() {
		return svrList;
	}
	public void setSvrList(List<StudyVolunteerReporting> svrList) {
		this.svrList = svrList;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public List<StudySubjectPeriods> getSspList() {
		return sspList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public void setSspList(List<StudySubjectPeriods> sspList) {
		this.sspList = sspList;
	}
	public List<SubjectRandamization> getSrzList() {
		return srzList;
	}
	public Long getTreatmentOneId() {
		return treatmentOneId;
	}
	public void setSrzList(List<SubjectRandamization> srzList) {
		this.srzList = srzList;
	}
	public void setTreatmentOneId(Long treatmentOneId) {
		this.treatmentOneId = treatmentOneId;
	}
	
	
	
}
