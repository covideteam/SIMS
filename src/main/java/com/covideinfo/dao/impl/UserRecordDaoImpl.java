package com.covideinfo.dao.impl;

import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.UserRecordDao;
import com.covideinfo.model.UserMaster;

@Repository("userRecordDao")
public class UserRecordDaoImpl extends AbstractDao<Long, UserMaster> implements UserRecordDao {

	@Override
	public UserMaster getUserRecord(Long userId) {
		return getByKey(userId);
	}
	
	

}
