package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.UsersExportDto;
import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

public interface UserCreationDao {

	List<DepartmentMaster> getDepartmentMasterList();

	List<RoleMaster> getRolesMasterRecordsList();

	long saveLoginUser(UserMaster user);

	UserMaster checkUserDuplication(String userId);

	StudyMaster getStudyMasterDefaltStudy();

	UserMaster getUserMasterWithName(String username);

	List<UserMaster> getUserMasterList();

	UserMaster getUserMasterWithId(Long id);

	boolean userUpdate(UserMaster um);

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
	
	UsersExportDto getuserExportDetails(Long userId);

}
