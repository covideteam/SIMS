package com.covideinfo.dto;

import java.io.Serializable;

public class ActivityUpdateDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7812389342357030342L;
	private Long activityId;
	private int orderNo;
	private String roleIds;
	
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	
	

}
