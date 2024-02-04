package com.covideinfo.dto;

import java.text.SimpleDateFormat;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.service.ActivityLockedService;

public class SubjectDataRemove {

	synchronized void deleteActivityLockedData(ActivityLockedService activityLockedService, ActivityLockedDao activityLockedDao) {
		SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		boolean flag=activityLockedService.removeData(activityLockedService,obj, activityLockedDao);
	        
		
	}

}
