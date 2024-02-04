package com.covideinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.AuditLogDao;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.AuditLogService;

@Service("auditLogService")
public class AuditLogServiceImpl implements AuditLogService{
	
	@Autowired
	AuditLogDao auditLogDao;

	@Override
	public List<UserMaster> getUserMasterList() {
		return auditLogDao.getUserMasterList();
	}

	@Override
	public List<StudyMaster> getStudyMasterList() {
		return auditLogDao.getStudyMasterList();
	}

}
