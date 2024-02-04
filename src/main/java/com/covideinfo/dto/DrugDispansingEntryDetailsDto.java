package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class DrugDispansingEntryDetailsDto implements Serializable {
	
	private List<StudyMaster> smList;
	private List<DosingInfoDetails> difodList;
	private List<TreatmentInfo> tinfoList;
	private DosingInfoDetails dinfodetails;
	private StudyMaster study = null;
	private UserMaster user;
	private Map<Long, DosingInfoDetails> dinfMap;
	private TreatmentInfo tinf;
	private Map<Long, TreatmentInfo> treatmentMap;
	private long dosecount =0;

	public List<StudyMaster> getSmList() {
		return smList;
	}

	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}

	public List<DosingInfoDetails> getDifodList() {
		return difodList;
	}

	public void setDifodList(List<DosingInfoDetails> difodList) {
		this.difodList = difodList;
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

	public List<TreatmentInfo> getTinfoList() {
		return tinfoList;
	}

	public void setTinfoList(List<TreatmentInfo> tinfoList) {
		this.tinfoList = tinfoList;
	}

	public Map<Long, DosingInfoDetails> getDinfMap() {
		return dinfMap;
	}

	public void setDinfMap(Map<Long, DosingInfoDetails> dinfMap) {
		this.dinfMap = dinfMap;
	}

	public DosingInfoDetails getDinfodetails() {
		return dinfodetails;
	}

	public void setDinfodetails(DosingInfoDetails dinfodetails) {
		this.dinfodetails = dinfodetails;
	}

	public TreatmentInfo getTinf() {
		return tinf;
	}

	public void setTinf(TreatmentInfo tinf) {
		this.tinf = tinf;
	}

	public long getDosecount() {
		return dosecount;
	}

	public void setDosecount(long dosecount) {
		this.dosecount = dosecount;
	}

	public Map<Long, TreatmentInfo> getTreatmentMap() {
		return treatmentMap;
	}

	public void setTreatmentMap(Map<Long, TreatmentInfo> treatmentMap) {
		this.treatmentMap = treatmentMap;
	}

	
	
	

}
