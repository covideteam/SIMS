package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserMaster;

public interface UserAdminDao {

	UserMaster getLoginUsersRecord(String username);

	UserMaster getLoginUsersRecordBasedOnId(long userId);

	boolean updateUserDetails(UserMaster userPojo);

	RoleMaster getRoleMasterRecordBasedOnRole(String string);

	UserMaster getLoginUsersListExceptSuperAdmin(Long userId);

	

}
