package com.covideinfo.model;

import org.springframework.core.env.Environment;

import com.covideinfo.util.DateFormatUtil;

public class DiscrepancyModel {
	private String comments;
	private String response;
	private String commentedBy;
	private String respondedBy;
	private String commentedOn;
	private String respondedOn;
	private boolean responseSubmitted;
	private Long id;
	public DiscrepancyModel() {
		
	}
	
	public DiscrepancyModel(StudyDiscrepancy studyDiscrepancy,Environment environment) {
		setComments(studyDiscrepancy.getComments());
		if(studyDiscrepancy.getResponse()!=null) {
			setResponse(studyDiscrepancy.getResponse());
		}
		
		setCommentedBy(studyDiscrepancy.getCreatedBy());
		setCommentedOn(DateFormatUtil.ConvertDate(studyDiscrepancy.getCreatedOn(), environment.getRequiredProperty("dateFormat")));
		if(studyDiscrepancy.getUpdateBy()!=null) {
			setRespondedBy(studyDiscrepancy.getUpdateBy());
		}
		else {
			setRespondedBy("");
		}
		if(studyDiscrepancy.getUpdateOn()!=null) {
			setRespondedOn(DateFormatUtil.ConvertDate(studyDiscrepancy.getUpdateOn(), environment.getRequiredProperty("dateFormat")));
		}
		else {
			setRespondedOn("");
		}
		setId(studyDiscrepancy.getId());
		setResponseSubmitted(studyDiscrepancy.isResponseSubmitted());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}
	public String getRespondedBy() {
		return respondedBy;
	}
	public void setRespondedBy(String respondedBy) {
		this.respondedBy = respondedBy;
	}
	public String getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(String commentedOn) {
		this.commentedOn = commentedOn;
	}
	public String getRespondedOn() {
		return respondedOn;
	}
	public void setRespondedOn(String respondedOn) {
		this.respondedOn = respondedOn;
	}

	public boolean isResponseSubmitted() {
		return responseSubmitted;
	}

	public void setResponseSubmitted(boolean responseSubmitted) {
		this.responseSubmitted = responseSubmitted;
	}
}
