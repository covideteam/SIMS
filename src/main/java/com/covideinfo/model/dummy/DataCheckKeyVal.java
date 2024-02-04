package com.covideinfo.model.dummy;

public class DataCheckKeyVal {

	//Discrepancy Open
	private long studyActivityDataId;
	private long studyActivityDataDetailsId;
	private String comments;
	
	//Discrepancy Close
	private long discrepancyId;
	private String response;
	private String value;
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public long getStudyActivityDataId() {
		return studyActivityDataId;
	}
	public void setStudyActivityDataId(long studyActivityDataId) {
		this.studyActivityDataId = studyActivityDataId;
	}
	public long getStudyActivityDataDetailsId() {
		return studyActivityDataDetailsId;
	}
	public void setStudyActivityDataDetailsId(long studyActivityDataDetailsId) {
		this.studyActivityDataDetailsId = studyActivityDataDetailsId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public long getDiscrepancyId() {
		return discrepancyId;
	}
	public void setDiscrepancyId(long discrepancyId) {
		this.discrepancyId = discrepancyId;
	}
	
	
	
	
	
	
	
	
	
}
