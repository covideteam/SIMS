package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UserAdminDao;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.UserMaster;

@Repository("userAdminDao")
public class UserAdminDaoImpl extends AbstractDao<Long, UserMaster> implements UserAdminDao {

	@Override
	public UserMaster getLoginUsersRecord(String userName) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", userName)).uniqueResult();
	}
	
	@Override
	public boolean updateUserDetails(UserMaster userPojo) {
		boolean flag = false;
		try {
			getSession().update(userPojo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public UserMaster getLoginUsersRecordBasedOnId(long userId) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("id", userId)).uniqueResult();
	}
	
	@Override
	public RoleMaster getRoleMasterRecordBasedOnRole(String roleString) {
		return (RoleMaster) getSession().createCriteria(RoleMaster.class)
				.add(Restrictions.eq("role", roleString)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserMaster getLoginUsersListExceptSuperAdmin(Long id) {
		UserMaster user = null;
		try {
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					.add(Restrictions.eq("accountNotLock", true))
					.add(Restrictions.eq("id", id)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	

}
