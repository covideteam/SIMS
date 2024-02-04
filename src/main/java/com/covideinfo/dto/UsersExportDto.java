package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.UserMaster;

public class UsersExportDto implements Serializable {

	private static final long serialVersionUID = 5828113912660889351L;
	private ApplicationConfiguration appConfig;
	private List<UserMaster> usersList;
	private UserMaster user;
	
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public ApplicationConfiguration getAppConfig() {
		return appConfig;
	}
	public void setAppConfig(ApplicationConfiguration appConfig) {
		this.appConfig = appConfig;
	}
	public List<UserMaster> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<UserMaster> usersList) {
		this.usersList = usersList;
	}
	
	
	
}
