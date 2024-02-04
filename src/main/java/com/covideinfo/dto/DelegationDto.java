package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@SuppressWarnings("serial")
public class DelegationDto implements Serializable {

	private List<UserMaster> usersList;
	private List<StudyMaster> smList;
	private List<UserWiseStudiesAsignMaster> usmList;
	private List<RoleMaster> roleList;
	private Map<Long, String> rolesMap;
	
	public List<UserMaster> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<UserMaster> usersList) {
		this.usersList = usersList;
	}
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public List<UserWiseStudiesAsignMaster> getUsmList() {
		return usmList;
	}
	public void setUsmList(List<UserWiseStudiesAsignMaster> usmList) {
		this.usmList = usmList;
	}
	public List<RoleMaster> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<RoleMaster> roleList) {
		this.roleList = roleList;
	}
	public Map<Long, String> getRolesMap() {
		return rolesMap;
	}
	public void setRolesMap(Map<Long, String> rolesMap) {
		this.rolesMap = rolesMap;
	}
	
	
	
}
