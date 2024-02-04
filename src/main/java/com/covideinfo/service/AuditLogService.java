package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

public interface AuditLogService {

	List<UserMaster> getUserMasterList();

	List<StudyMaster> getStudyMasterList();

}
