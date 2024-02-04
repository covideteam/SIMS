package com.covideinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.covideinfo.audittrail.AuditToken;
import com.covideinfo.dao.RoleMasterDao;
import com.covideinfo.dao.UserDao;
import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserActivity;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.dummy.LoginFieldDummyForm;
import com.covideinfo.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;
	
	@Autowired
	RoleMasterDao roleMasterDao;
	
	@Override
	public UserMaster findByUsername(String username) {
		// TODO Auto-generated method stub
		UserMaster user = userdao.findByusername(username);
		return user;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserMaster findById(Long id) {
		return userdao.findById(id);
	}


	@Override
	public List<UserMaster> findAllUsers() {
		return userdao.findAllUsers();
	}

	@Override
	public boolean isUserSSOUnique(Long id, String username) {
		UserMaster user = findByUsername(username);
		return (user == null || ((id != null) && (user.getId() == id)));
	}


	

	@Override
	@AuditToken
	public boolean generateLoginInfo(LoginFieldDummyForm loginFieldDummyForm, String username) {
		// TODO Auto-generated method stub
		try {
			UserMaster loginUsers = new UserMaster();
//			loginUsers.setCreatedBy(username);
			loginUsers.setCreatedOn(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1); // to get previous year add -1
			Date nextYear = cal.getTime();
			loginUsers.setAccountexprie(nextYear);
			loginUsers.setAccountNotDisable(true);
			loginUsers.setAccountNotLock(true);
			loginUsers.setUsername(loginFieldDummyForm.getEmpId());
			RoleMaster role = roleMasterDao.findById(loginFieldDummyForm.getRole().getId());
			loginUsers.setRole(role);
//			String password = new SimpleDateFormat("yyyyMMdd").format(loginFieldDummyForm.getBirthDate());
//			System.out.println("Password : "+password);
//			loginUsers.setPassword(passwordEncoder.encode("login"+password));
			loginUsers.setPassword(passwordEncoder.encode(loginUsers.getUsername()+"@123"));
				loginUsers.setTranPassword(passwordEncoder.encode(loginUsers.getUsername()+"@12345"));
			
			System.out.println(loginUsers.toString());
			
			//Employee Activity
		    /*UserMaster emppojo = userdao.findByUsername(username);*/
			
			
		
		    boolean flag = userdao.generateLoginDetails(loginUsers);
		    if(flag) {
			return true;
			}else{
			return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public LoginFieldDummyForm checkDuplicate(String empId) {
		// TODO Auto-generated method stub
		UserMaster loginUsers = userdao.findByusername(empId);
		if(loginUsers!=null) {
			LoginFieldDummyForm loginFieldDummyForm = new LoginFieldDummyForm();
			loginFieldDummyForm.setId(loginUsers.getId());
			loginFieldDummyForm.setAccountNotDisable(loginUsers.isAccountNotDisable());
			loginFieldDummyForm.setAccountNotLock(loginUsers.isAccountNotLock());
			loginFieldDummyForm.setEmpId(empId);
			loginFieldDummyForm.setLoginCredentials(true);
			loginFieldDummyForm.setRole(loginUsers.getRole());
			if(loginUsers.getTranPassword()!=null && !loginUsers.getTranPassword().equals(""))
				loginFieldDummyForm.setTranPasswordflag(true);
			else
				loginFieldDummyForm.setTranPasswordflag(false);
			return loginFieldDummyForm;
		}
		return null;
	}


	@Override
	@AuditToken
	public boolean updateLoginInfo(UserMaster checkLoginUser, LoginFieldDummyForm loginFieldDummyForm, String username) {
		// TODO Auto-generated method stub
		boolean flag = false;
		RoleMaster role = null;
		if(checkLoginUser.getRole()!=null) {
			if(checkLoginUser.getRole().getId() != loginFieldDummyForm.getRole().getId()) {
				role = roleMasterDao.findById(loginFieldDummyForm.getRole().getId());
				checkLoginUser.setRole(role);
				flag = true;
			}else {
				role = checkLoginUser.getRole();
			}
		}else {
			role = roleMasterDao.findById(loginFieldDummyForm.getRole().getId());
			checkLoginUser.setRole(role);
			flag = true;
		}
//		String password = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		password= "reset"+password;
		if(role!=null && flag) {
			if(loginFieldDummyForm.isResetPassword()) {
				checkLoginUser.setPassword(passwordEncoder.encode(username+"@123"));
//				checkLoginUser.setPassword(passwordEncoder.encode(password));
			}
			if(loginFieldDummyForm.isResetTranPassword() || loginFieldDummyForm.isTranPasswordflag()) {
				checkLoginUser.setTranPassword(passwordEncoder.encode(username+"@12345"));
			}
		}else {
			if(loginFieldDummyForm.isResetPassword()) {
				checkLoginUser.setPassword(passwordEncoder.encode(username+"@123"));
//				checkLoginUser.setPassword(passwordEncoder.encode(password));
				flag = true;
			}
			if(loginFieldDummyForm.isResetTranPassword()) {
				checkLoginUser.setTranPassword(passwordEncoder.encode(username+"@12345"));
//				checkLoginUser.setTranPassword(passwordEncoder.encode(password));
				flag = true;
			}
		}
		if(flag) {
//			checkLoginUser.setUpdatedBy(username);
			checkLoginUser.setUpdatedOn(new Date());
			userdao.updateLoginCredentials(checkLoginUser);
			return true;
		}
		return false;
	}


	@Override
	public List<UserMaster> findAllActiveUsers() {
		return userdao.findAllActiveUsers();
	}


	@SuppressWarnings("unused")
	@Override
	public boolean updateLoginPassword(UserMaster checkLoginUser, String password) {
		boolean flag = false;
		try {
		checkLoginUser.setPassword(passwordEncoder.encode(password));
		checkLoginUser.setUpdatedBy(checkLoginUser);
		checkLoginUser.setUpdatedOn(new Date());
		userdao.updateLoginCredentials(checkLoginUser);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public UserMaster getUserdetails(String username) {
		return userdao.getUserdetails(username);
	}


	@Override
	public List<UserMaster> findUserSerchList(String userid) {
		return userdao.findUserSerchList(userid);
	}


	@Override
	public UserMaster findUserDetails(String userid) {
		return userdao.findUserDetails(userid);
	}


	@Override
	public boolean passResetSave(UserMaster pojo,String userName) {
		pojo.setPassword(passwordEncoder.encode("password@123"));
//		pojo.setUpdatedBy(userName);
		pojo.setUpdatedOn(new Date());
		
		return userdao.passResetSave(pojo);
		
		
	}
	@Override
	public List<UserMaster> findUserSerchList2(String userid) {
		return userdao.findUserSerchList2(userid);
	}


	@Override
	public boolean passTrsResetSave(UserMaster pojo, String userName) {
		pojo.setPassword(passwordEncoder.encode("trans@123"));
//		pojo.setUpdatedBy(userName);
		pojo.setUpdatedOn(new Date());
		
		return userdao.passTrsResetSave(pojo);
	}


	@Override
	public boolean accountDisableSave(UserMaster pojo, String userName) {
		pojo.setAccountNotDisable(false);
		return userdao.accountDisableSave(pojo);
	}


	@Override
	public List<UserMaster> findUserAllList() {
		return userdao.findUserAllList();
	}


	@Override
	public boolean accountEnableSave(UserMaster pojo, String userName) {
		pojo.setAccountNotDisable(true);
		return userdao.accountEnableSave(pojo);
	}

	@Override
	public void checkAndCreateConfiguredTables(StatusMaster activeStatus) {
		userdao.checkAndCreateConfiguredTables(activeStatus);
	}

@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList() {
		return userdao.getInternationalizaionLanguagesRecrdsList();
	}
	
	@Override
	public List<FromStaticData> fromStaticDataWithFieldName(String fieldName) {
		// TODO Auto-generated method stub
		return userdao.fromStaticDataWithFieldName(fieldName);
	}


	@Override
	public List<RolesWiseModules> getApplicationMenus(UserMaster user) {
		return userdao.getApplicationMenus(user);
	}


	@Override
	public List<FromStaticData> studyStaticFieldValues() {
		// TODO Auto-generated method stub
		return userdao.studyStaticFieldValues();
	}


	@Override
	public long saveUserActivityRecord(UserActivity ua) {
		// TODO Auto-generated method stub
		return userdao.saveUserActivityRecord(ua);
	}


	@Override
	public UserActivity getUserActivityRecord(long id) {
		return userdao.getUserActivityRecord(id);
	}


	@Override
	public boolean updateUserActivityRecord(UserActivity uaPojo) {
		return userdao.updateUserActivityRecord(uaPojo);
	}


	@Override
	public UserMaster findByUsernameData(String username) {
		return userdao.findByUsernameData(username);
	}


	@Override
	public List<UserActivity> getUserActivityList(String fromDate, String toDate, String userName) {
		List<UserActivity> uaList = null;
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 fromDate = fromDate +" 00:00:00";
			 toDate = toDate +" 23:59:00";
			 Date fdate = sdf.parse(fromDate);
			 Date tdate = sdf.parse(toDate);
			 uaList = userdao.getParticularUserActivityRecords(userName, fdate, tdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return uaList;
	}


	@Override
	public List<UserMaster> getUserMasterList() {
		return userdao.getUserMasterList();
	}


	@Override
	public List<AuditLog> getAuditLogList(String fromDate, String toDate,Long studyid) {
		List<AuditLog> list = null;
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 fromDate = fromDate +" 00:00:00";
			 toDate = toDate +" 23:59:00";
			 Date fdate = sdf.parse(fromDate);
			 Date tdate = sdf.parse(toDate);
			 list = userdao.getAuditLogList(fdate, tdate,studyid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<ActivityLog> getActivityLogList(String fromDate, String toDate, Long volunteer, Long studyid,
			Long subjectid) {
		List<ActivityLog> list = null;
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 fromDate = fromDate +" 00:00:00";
			 toDate = toDate +" 23:59:00";
			 Date fdate = sdf.parse(fromDate);
			 Date tdate = sdf.parse(toDate);
			 list = userdao.getActivityLogList(volunteer, fdate, tdate,studyid,subjectid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<StudySubjects> getSubjetDataWithStudyId(Long studyid) {
		// TODO Auto-generated method stub
		return userdao.getSubjetDataWithStudyId(studyid);
	}


	@Override
	public List<StudyVolunteerReporting> getVolunteerDataWithStudyId(Long studyid) {
		return userdao.getVolunteerDataWithStudyId(studyid);
	}

    //Here Getting RoleId from RoleWiseModules
	@Override
	public List<RolesWiseModules> getRoleWiseListWithRoleId(Long roleid) {
		return userdao.getRoleWiseListWithRoleId(roleid);
	}


	//Here checking with roleid from Rolewise Modules and getting AppMenus with that id 
	@Override
	public List<ApplicationMenus> getApplicationMenusWithRoleId(Long roleid) {
		
		List<ApplicationMenus> list=new ArrayList<>();
		List<Long> ids=new ArrayList<>();
		List<RolesWiseModules> roleList=null;
		roleList=userdao.getRoleWiseListWithRoleId(roleid);
		for(RolesWiseModules adddata:roleList) {
			if(!ids.contains(adddata.getUsermenu().getId())) {
		    ids.add(adddata.getUsermenu().getId());
			list.add(adddata.getUsermenu());
		    }
		}
		return list;
	}


	//Here checking with roleid from Rolewise Modules and getting AppSideMenus with that id 
	@Override
	public List<ApplictionSideMenus> getApplictionSideMenusWithRoleId(Long roleid) {
		List<ApplictionSideMenus> list=new ArrayList<>();
		List<RolesWiseModules> roleList=null;
		roleList=userdao.getRoleWiseListWithRoleId(roleid);
		for(RolesWiseModules adddata:roleList) {
			list.add(adddata.getAppsideMenu());
		}
		return list;
	}


	@Override
	public long getSidemenusCount() {
		return userdao.getSidemenusCount();
	}
	
	

}