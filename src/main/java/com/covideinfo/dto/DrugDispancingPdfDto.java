package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.UserMaster;

public class DrugDispancingPdfDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2262188197018642280L;
	private List<StudyMasterDto> smList;
	private List<PeriodMasterDto> spmList;
	private List<TreatmentsDto> treatmentsList;
	private List<DosingInfoDetails> dinfdList;
	private List<SubjectRandomizationDto> subRazList; 
	private UserMaster user;
	private PeriodMasterDto period;
	
	public List<StudyMasterDto> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMasterDto> smList) {
		this.smList = smList;
	}
	public List<PeriodMasterDto> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<PeriodMasterDto> spmList) {
		this.spmList = spmList;
	}
	public List<TreatmentsDto> getTreatmentsList() {
		return treatmentsList;
	}
	public void setTreatmentsList(List<TreatmentsDto> treatmentsList) {
		this.treatmentsList = treatmentsList;
	}
	public List<DosingInfoDetails> getDinfdList() {
		return dinfdList;
	}
	public void setDinfdList(List<DosingInfoDetails> dinfdList) {
		this.dinfdList = dinfdList;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public PeriodMasterDto getPeriod() {
		return period;
	}
	public void setPeriod(PeriodMasterDto period) {
		this.period = period;
	}
	public List<SubjectRandomizationDto> getSubRazList() {
		return subRazList;
	}
	public void setSubRazList(List<SubjectRandomizationDto> subRazList) {
		this.subRazList = subRazList;
	}
	
	
	
	
	

}
