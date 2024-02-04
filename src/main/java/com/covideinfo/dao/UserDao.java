package com.covideinfo.dao;

import java.util.Date;
import java.util.List;

import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;

public interface UserDao {

	UserMaster findById(Long id);

	UserMaster findByusername(String sso);

	List<UserMaster> findAllUsers();

	void updateStudy(UserMaster user);

	boolean generateLoginDetails(UserMaster loginUsers);

	void updateLoginCredentials(UserMaster checkLoginUser);

	List<UserMaster> findAllActiveUsers();

	UserMaster getUserdetails(String username);

	List<UserMaster> findUserSerchList(String userid);

	UserMaster findUserDetails(String userid);

	boolean passResetSave(UserMaster pojo);

	List<UserMaster> findUserSerchList2(String userid);

	boolean passTrsResetSave(UserMaster pojo);

	UserMaster findByUsername(String username);

	boolean accountDisableSave(UserMaster pojo);

	List<UserMaster> findUserAllList();

	boolean accountEnableSave(UserMaster pojo);

	void checkAndCreateConfiguredTables(StatusMaster activeStatus);

	List<StudyMaster> allUserActiveStudys(Long userId);

	List<FromStaticData> fromStaticDataWithFieldName(String fieldName);

	List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList();

	List<RolesWiseModules> getApplicationMenus(UserMaster user);

	List<FromStaticData> studyStaticFieldValues();

	long saveUserActivityRecord(UserActivity ua);

	UserActivity getUserActivityRecord(long id);

	boolean updateUserActivityRecord(UserActivity uaPojo);

	UserMaster findByUsernameData(String username);

	List<UserActivity> getParticularUserActivityRecords(String userName, Date fdate, Date tdate);

	List<UserMaster> getUserMasterList();

	List<AuditLog> getAuditLogList(Date fdate, Date tdate, Long studyid);

	List<ActivityLog> getActivityLogList(Long volunteer, Date fdate, Date tdate, Long studyid, Long subjectid);

	List<StudySubjects> getSubjetDataWithStudyId(Long studyid);

	List<StudyVolunteerReporting> getVolunteerDataWithStudyId(Long studyid);

	List<RolesWiseModules> getRoleWiseListWithRoleId(Long roleid);

	long getSidemenusCount();

}


