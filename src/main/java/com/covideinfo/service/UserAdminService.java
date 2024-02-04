package com.covideinfo.service;

import com.covideinfo.model.UserMaster;

public interface UserAdminService {

	UserMaster getLoginUsersRecord(String username);

	boolean checkOldPwdMatching(long userId, String pwd);

	boolean saveChangedPassword(long userId, String newPwd, String tranPwd, String username, String passwordExpDays);

	UserMaster getLoginUsersListExceptSuperAdmin(Long userId);

	String saveResetPassword(long userId, String reason, String username, String passwordExpDays, String resetPassword);

}
