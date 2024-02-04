package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.UserMaster;

public interface UserCreationService {

	List<DepartmentMaster> getDepartmentMasterList();

	List<RoleMaster> getRolesMasterRecordsList();

	long saveUser(String username, UserMaster user, String passwordExpiredDays, String email);

	UserMaster checkUserDuplication(String userId);

	List<UserMaster> getUserMasterList();

	UserMaster getUserMasterWithId(Long id);

	boolean userUpdate(String username, UserMaster um);

	List<UserMaster> findOnlyAdminRoles();

	List<UserMaster> findRolesWithOutAdminRoles();

	boolean updateUserStatus(UserMaster um);

	List<RoleMaster> getRolesMasterRecordsListWithOutAdmin(RoleMaster role);

	RoleMaster getRoleMasterWithId(Long rid);

	List<RoleMaster> findOnlyAdminRolesForRoles();

	List<RoleMaster> findRolesWithOutAdminRolesForRoles();

	List<UserMaster> findUserListAll();

	boolean updateRoleStatus(RoleMaster rm);

	List<RoleMaster> getRolesMasterRecordsListOnlyActive(StatusMaster stm);

	List<UserMaster> checkUsersIsAvelableInThisRole(Long value);

	String generateUsersListPdf(HttpServletRequest request, HttpServletResponse response, Long userId, String dateFormat, MessageSource messageSource, Locale currentLocale);

}
