package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;

@SuppressWarnings("serial")
public class BarcodeDetailsDto implements Serializable {
	
	private StudyMaster study;
	private List<StudyPeriodMaster> spmList;
	private List<TreatmentInfo> treatmentList;
	private List<SampleTimePoints> stpList;
	private List<VitalTimePoints> vtpList;
	private List<MealsTimePoints> mtpList;
	private List<DoseTimePoints> dosetpList;
	private List<SubjectRandamization> srzList;
	private List<Integer> studyGroupStanbys;
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public List<StudyPeriodMaster> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodMaster> spmList) {
		this.spmList = spmList;
	}
	public List<TreatmentInfo> getTreatmentList() {
		return treatmentList;
	}
	public void setTreatmentList(List<TreatmentInfo> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public List<SampleTimePoints> getStpList() {
		return stpList;
	}
	public void setStpList(List<SampleTimePoints> stpList) {
		this.stpList = stpList;
	}
	public List<VitalTimePoints> getVtpList() {
		return vtpList;
	}
	public void setVtpList(List<VitalTimePoints> vtpList) {
		this.vtpList = vtpList;
	}
	public List<MealsTimePoints> getMtpList() {
		return mtpList;
	}
	public void setMtpList(List<MealsTimePoints> mtpList) {
		this.mtpList = mtpList;
	}
	public List<DoseTimePoints> getDosetpList() {
		return dosetpList;
	}
	public void setDosetpList(List<DoseTimePoints> dosetpList) {
		this.dosetpList = dosetpList;
	}
	public List<SubjectRandamization> getSrzList() {
		return srzList;
	}
	public void setSrzList(List<SubjectRandamization> srzList) {
		this.srzList = srzList;
	}
	public List<Integer> getStudyGroupStanbys() {
		return studyGroupStanbys;
	}
	public void setStudyGroupStanbys(List<Integer> studyGroupStanbys) {
		this.studyGroupStanbys = studyGroupStanbys;
	}
	
	

}
