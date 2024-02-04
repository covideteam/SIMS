package com.covideinfo.service;

import java.text.SimpleDateFormat;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dto.StudyDesignFromDto;
import com.covideinfo.model.ActivityLockedData;

public interface ActivityLockedService {

	boolean saveActivityLockedData(StudyDesignFromDto sdfrom, String username);

	ActivityLockedData getActivityLockedDataExitOrNot(StudyDesignFromDto sdfrom);

	boolean deleteActivityLockingData(ActivityLockedData aldpojo);

	boolean removeData(ActivityLockedService activityLockedService, SimpleDateFormat obj, ActivityLockedDao activityLockedDao);

}
