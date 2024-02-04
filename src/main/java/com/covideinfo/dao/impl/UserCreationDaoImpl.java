package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UserCreationDao;
import com.covideinfo.dto.UsersExportDto;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

@Repository("userCreationDao")
public class UserCreationDaoImpl extends AbstractDao<Long, DepartmentMaster> implements UserCreationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentMaster> getDepartmentMasterList() {
		return getSession().createCriteria(DepartmentMaster.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getRolesMasterRecordsList() {
		StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
				.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
		return getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.ne("role", "SUPERADMIN"))
				.add(Restrictions.eq("roleStatus", stm)).list();
	}

	@Override
	public long saveLoginUser(UserMaster user) {
		return (Long) getSession().save(user);
	}

	@Override
	public UserMaster checkUserDuplication(String userId) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", userId).ignoreCase()).uniqueResult();
	}

	@Override
	public StudyMaster getStudyMasterDefaltStudy() {
		return (StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("id", 1l)).uniqueResult();
	}

	@Override
	public UserMaster getUserMasterWithName(String username) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> getUserMasterList() {
		 List<UserMaster> uslist =null;
		 uslist= getSession().createCriteria(UserMaster.class).list();
		 
		 return uslist;
	}

	@Override
	public UserMaster getUserMasterWithId(Long id) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public boolean userUpdate(UserMaster um) {
		boolean flag=false;
		try {
			getSession().update(um);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findOnlyAdminRoles() {
		RoleMaster pojo=(RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("role","ADMIN")).uniqueResult();
		List<UserMaster> list=null;
		list=getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("role", pojo))
				.add(Restrictions.eq("accountNotLock",true ))
				.add(Restrictions.eq("accountNotDisable",true )).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findRolesWithOutAdminRoles() {
		List<String> roList=new ArrayList<String>();
		roList.add("SUPERADMIN");
		roList.add("ADMIN");
		List<Long> ids=new ArrayList<Long>();
		List<RoleMaster> roleList=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.in("role", roList)).list();
		for(RoleMaster data:roleList) {
			ids.add(data.getId());
		}
		
		List<UserMaster> list=null;
		list=getSession().createCriteria(UserMaster.class)
				.add(Restrictions.not(Restrictions.in("role.id", ids)))
				.add(Restrictions.eq("accountNotLock",true ))
				.add(Restrictions.eq("accountNotDisable",true )).list();
		return list;
	}


	@Override
	public boolean updateUserStatus(UserMaster um) {
		boolean flag=false;
		try {
			getSession().update(um);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getRolesMasterRecordsListWithOutAdmin(RoleMaster role) {
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.ne("role",role.getRole()))
				.add(Restrictions.ne("role","SUPERADMIN")).list();
		return list;
	}

	@Override
	public RoleMaster getRoleMasterWithId(Long rid) {
		return (RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("id", rid)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findOnlyAdminRolesForRoles() {
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("role","ADMIN")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> findRolesWithOutAdminRolesForRoles() {
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.ne("role","SUPERADMIN"))
				.add(Restrictions.ne("role","ADMIN")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> findUserListAll() {
		List<UserMaster> list=null;
		list=getSession().createCriteria(UserMaster.class).list();
		return list;
	}

	@Override
	public boolean updateRoleStatus(RoleMaster rm) {
		boolean flag=false;
		try {
			getSession().update(rm);
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getRolesMasterRecordsListOnlyActive(StatusMaster stm) {
		List<RoleMaster> list=null;
		list=getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("roleStatus", stm))
				.add(Restrictions.ne("role", "SUPERADMIN")).list();
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> checkUsersIsAvelableInThisRole(Long value) {
		List<UserMaster> list=null;
		list=getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("role.id", value)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UsersExportDto getuserExportDetails(Long userId) {
		UsersExportDto userExportDto = null;
		StatusMaster sm  = null;
		ApplicationConfiguration appConfig = null;
		List<UserMaster> userList=null;
		UserMaster user = null;
		try {
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					.add(Restrictions.eq("id", userId))
					.uniqueResult();
			
			sm = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
			appConfig =  (ApplicationConfiguration) getSession().createCriteria(ApplicationConfiguration.class)
					.add(Restrictions.eq("configuration", "Pdf Header"))
					.add(Restrictions.eq("status", sm))
					.uniqueResult();
			userList = getSession().createCriteria(UserMaster.class)
					.addOrder(Order.asc("id")).list();
			if(appConfig != null &&(userList != null && userList.size() > 0)) {
				userExportDto = new UsersExportDto();
				userExportDto.setAppConfig(appConfig);
				userExportDto.setUsersList(userList);
				userExportDto.setUser(user);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userExportDto;
	}

}
