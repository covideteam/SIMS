package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;

@SuppressWarnings("serial")
public class RoleWiseListDto implements Serializable {
	
	 List<ApplicationMenus> appMenu;
	 List<ApplictionSideMenus> sideMenu;
	 
	public List<ApplicationMenus> getAppMenu() {
		return appMenu;
	}
	public void setAppMenu(List<ApplicationMenus> appMenu) {
		this.appMenu = appMenu;
	}
	public List<ApplictionSideMenus> getSideMenu() {
		return sideMenu;
	}
	public void setSideMenu(List<ApplictionSideMenus> sideMenu) {
		this.sideMenu = sideMenu;
	}
	
	
	

}
