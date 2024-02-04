package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

public interface AuditLogDao {

	List<UserMaster> getUserMasterList();

	List<StudyMaster> getStudyMasterList();

}
