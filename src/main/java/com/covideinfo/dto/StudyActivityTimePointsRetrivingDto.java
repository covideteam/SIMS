package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityTimePoints;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.TreatmentInfo;

@SuppressWarnings("serial")
public class StudyActivityTimePointsRetrivingDto implements Serializable {
	
	private LanguageSpecificGlobalActivityDetails lsga;
	private List<LanguageSpecificGlobalParameterDetails> lspgpdList;
	private List<StudyActivityTimePoints> satpList;
	private List<SubjectDoseTimePoints> dosetpList;
	private StudyPeriodMaster spm;
	private StudyActivities sa;
	private TreatmentInfo tfinfo;
	private GlobalActivity ga;
	
	public LanguageSpecificGlobalActivityDetails getLsga() {
		return lsga;
	}
	public List<LanguageSpecificGlobalParameterDetails> getLspgpdList() {
		return lspgpdList;
	}
	public List<StudyActivityTimePoints> getSatpList() {
		return satpList;
	}
	public void setLsga(LanguageSpecificGlobalActivityDetails lsga) {
		this.lsga = lsga;
	}
	public void setLspgpdList(List<LanguageSpecificGlobalParameterDetails> lspgpdList) {
		this.lspgpdList = lspgpdList;
	}
	public void setSatpList(List<StudyActivityTimePoints> satpList) {
		this.satpList = satpList;
	}
	public List<SubjectDoseTimePoints> getDosetpList() {
		return dosetpList;
	}
	public void setDosetpList(List<SubjectDoseTimePoints> dosetpList) {
		this.dosetpList = dosetpList;
	}
	public StudyPeriodMaster getSpm() {
		return spm;
	}
	public StudyActivities getSa() {
		return sa;
	}
	public void setSpm(StudyPeriodMaster spm) {
		this.spm = spm;
	}
	public void setSa(StudyActivities sa) {
		this.sa = sa;
	}
	public TreatmentInfo getTfinfo() {
		return tfinfo;
	}
	public void setTfinfo(TreatmentInfo tfinfo) {
		this.tfinfo = tfinfo;
	}
	public GlobalActivity getGa() {
		return ga;
	}
	public void setGa(GlobalActivity ga) {
		this.ga = ga;
	}
	
	
	
	

}
