package com.covideinfo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.AuditLogDao;
import com.covideinfo.model.AuditLog;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

@Repository("auditLogDao")
public class AuditLogDaoImpl extends AbstractDao<Long, AuditLog> implements AuditLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> getUserMasterList() {
		List<UserMaster> list=null;
		list=getSession().createCriteria(UserMaster.class).list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterList() {
		List<StudyMaster> slist=null;
		slist=getSession().createCriteria(StudyMaster.class).list();
		
		return slist;
	}

}
