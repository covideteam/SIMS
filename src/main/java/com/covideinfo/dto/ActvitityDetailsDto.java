package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActvitityDetailsDto implements Serializable, Comparable<ActvitityDetailsDto> {
	private Long studyActivityId;
	private Long activityId;
	private String activityName;
	private String getUrl;
	private String postUrl;
	private String rejectUrl;
	private boolean showSubject;
	private int orderNo;
	public Long getActivityId() {
		return activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public String getRejectUrl() {
		return rejectUrl;
	}
	public void setRejectUrl(String rejectUrl) {
		this.rejectUrl = rejectUrl;
	}
	public boolean isShowSubject() {
		return showSubject;
	}
	public void setShowSubject(boolean showSubject) {
		this.showSubject = showSubject;
	}
	@Override
	public int compareTo(ActvitityDetailsDto o) {
		return Integer.compare(this.orderNo, o.orderNo);
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	
	

}
