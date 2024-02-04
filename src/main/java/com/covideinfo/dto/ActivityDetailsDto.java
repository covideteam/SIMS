package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ActivityDetailsDto implements Serializable {
	
	private Long actId;
	private String actName;
	private String activityCode;
	private List<RolesDto> rolesList;
	private String existingRoles;
	private int orderNo;
	private List<Integer> ordersNoList;
	public Long getActId() {
		return actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public List<RolesDto> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<RolesDto> rolesList) {
		this.rolesList = rolesList;
	}
	public String getExistingRoles() {
		return existingRoles;
	}
	public void setExistingRoles(String existingRoles) {
		this.existingRoles = existingRoles;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public List<Integer> getOrdersNoList() {
		return ordersNoList;
	}
	public void setOrdersNoList(List<Integer> ordersNoList) {
		this.ordersNoList = ordersNoList;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
    
}
