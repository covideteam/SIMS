package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class StudyDesignDto implements Serializable {
	
	private Projects poject;
	private String noOfSubjects;
	private String noofperiod;
	private RandomizationFileStatus rpojo;
	private List<ProjectsDetails> randomizationCode;
	private List<ProjectDetailsRandomization> pdrlist;
	private UserMaster checkLoginUser;
	private DraftReviewStage drs;
	private StatusMaster sm;
	public Projects getPoject() {
		return poject;
	}
	public String getNoOfSubjects() {
		return noOfSubjects;
	}
	public String getNoofperiod() {
		return noofperiod;
	}
	public RandomizationFileStatus getRpojo() {
		return rpojo;
	}
	public List<ProjectsDetails> getRandomizationCode() {
		return randomizationCode;
	}
	public List<ProjectDetailsRandomization> getPdrlist() {
		return pdrlist;
	}
	public UserMaster getCheckLoginUser() {
		return checkLoginUser;
	}
	public void setPoject(Projects poject) {
		this.poject = poject;
	}
	public void setNoOfSubjects(String noOfSubjects) {
		this.noOfSubjects = noOfSubjects;
	}
	public void setNoofperiod(String noofperiod) {
		this.noofperiod = noofperiod;
	}
	public void setRpojo(RandomizationFileStatus rpojo) {
		this.rpojo = rpojo;
	}
	public void setRandomizationCode(List<ProjectsDetails> randomizationCode) {
		this.randomizationCode = randomizationCode;
	}
	public void setPdrlist(List<ProjectDetailsRandomization> pdrlist) {
		this.pdrlist = pdrlist;
	}
	public void setCheckLoginUser(UserMaster checkLoginUser) {
		this.checkLoginUser = checkLoginUser;
	}
	public DraftReviewStage getDrs() {
		return drs;
	}
	public void setDrs(DraftReviewStage drs) {
		this.drs = drs;
	}
	public StatusMaster getSm() {
		return sm;
	}
	public void setSm(StatusMaster sm) {
		this.sm = sm;
	}
	

}
