package com.covideinfo.bo;
public class TimePointInfo {
	private String id  = "";
	private String scheduleTime = "";
	private String collected = "";
	private String deviationMsg = "";
	private String deviation = "";
	private String collectedBy = "";
	private String color = "blue";
	private boolean requiredReview;
	private String reviewUrlId;
	private String start;
	private String end;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public boolean isRequiredReview() {
		return requiredReview;
	}
	public void setRequiredReview(boolean requiredReview) {
		this.requiredReview = requiredReview;
	}
	
	public String getReviewUrlId() {
		return reviewUrlId;
	}
	public void setReviewUrlId(String reviewUrlId) {
		this.reviewUrlId = reviewUrlId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getCollected() {
		return collected;
	}
	public void setCollected(String collected) {
		this.collected = collected;
	}
	public String getDeviationMsg() {
		return deviationMsg;
	}
	public void setDeviationMsg(String deviationMsg) {
		this.deviationMsg = deviationMsg;
	}
	public String getDeviation() {
		return deviation;
	}
	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}
	public String getCollectedBy() {
		return collectedBy;
	}
	public void setCollectedBy(String collectedBy) {
		this.collectedBy = collectedBy;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
