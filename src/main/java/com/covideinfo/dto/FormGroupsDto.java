package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FormGroupsDto implements Serializable, Comparable<FormGroupsDto> {
	
	private Long groupId;
	private  String groupName;
	private Integer orderNo;
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Override
	public int compareTo(FormGroupsDto o) {
		return Integer.compare(this.orderNo, o.orderNo);
	}
	

}
