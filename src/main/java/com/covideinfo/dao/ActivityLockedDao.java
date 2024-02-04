package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.StudyDesignFromDto;
import com.covideinfo.model.ActivityLockedData;

public interface ActivityLockedDao {

	boolean saveActivityLockedData(ActivityLockedData ald);

	ActivityLockedData getActivityLockedDataExitCheck(StudyDesignFromDto sdfrom);

	boolean deleteActivityLockingData(ActivityLockedData aldpojo);

	List<ActivityLockedData> getActivityLockedDataList();

	boolean removeData(List<ActivityLockedData> lockdataremo);

	
}
