package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.LoginFieldDummyForm;


public interface UserService {

	UserMaster findByUsername(String username);

	UserMaster findById(Long id);

	List<UserMaster> findAllUsers();

	boolean isUserSSOUnique(Long id, String sso);

	boolean generateLoginInfo(LoginFieldDummyForm loginFieldDummyForm, String username);

	LoginFieldDummyForm checkDuplicate(String empId);

	boolean updateLoginInfo(UserMaster checkLoginUser, LoginFieldDummyForm loginFieldDummyForm, String username);

	List<UserMaster> findAllActiveUsers();

	boolean updateLoginPassword(UserMaster checkLoginUser, String password);

	UserMaster getUserdetails(String username);

	List<UserMaster> findUserSerchList(String userid);

	UserMaster findUserDetails(String userid);

	boolean passResetSave(UserMaster pojo,String userName);

	List<UserMaster> findUserSerchList2(String userid);

	boolean passTrsResetSave(UserMaster pojo, String userName);

	boolean accountDisableSave(UserMaster pojo, String userName);

	List<UserMaster> findUserAllList();

	boolean accountEnableSave(UserMaster pojo, String userName);

	void checkAndCreateConfiguredTables(StatusMaster activeStatus);

	List<FromStaticData> fromStaticDataWithFieldName(String fieldName);

	List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList();

	List<RolesWiseModules> getApplicationMenus(UserMaster user);
	
    List<FromStaticData> studyStaticFieldValues();

    long saveUserActivityRecord(UserActivity ua);

	UserActivity getUserActivityRecord(long id);

	boolean updateUserActivityRecord(UserActivity uaPojo);

	UserMaster findByUsernameData(String username);

	List<UserActivity> getUserActivityList(String fromDate, String toDate, String userName);

	List<UserMaster> getUserMasterList();

	List<AuditLog> getAuditLogList(String fromDate, String toDate,Long studyid);

	List<ActivityLog> getActivityLogList(String fromDate, String toDate, Long volunteer, Long studyid, Long subjectid);

	List<StudySubjects> getSubjetDataWithStudyId(Long studyid);

	List<StudyVolunteerReporting> getVolunteerDataWithStudyId(Long studyid);

	List<RolesWiseModules> getRoleWiseListWithRoleId(Long roleid);

	List<ApplicationMenus> getApplicationMenusWithRoleId(Long roleid);

	List<ApplictionSideMenus> getApplictionSideMenusWithRoleId(Long roleid);

	long getSidemenusCount();

}

