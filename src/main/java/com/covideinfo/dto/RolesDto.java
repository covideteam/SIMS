package com.covideinfo.dto;

import java.io.Serializable;

public class RolesDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 112632479698455232L;
	private Long roleId;
	private String roleName;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	

}
