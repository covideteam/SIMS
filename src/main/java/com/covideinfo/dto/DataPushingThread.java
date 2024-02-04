package com.covideinfo.dto;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.service.ActivityLockedService;

public class DataPushingThread {
	
	public static void dataPushing(ActivityLockedService activityLockedService, ActivityLockedDao activityLockedDao) {
		SubjectDataRemove sdr=new SubjectDataRemove();
		DataRemove dr=new DataRemove(sdr,activityLockedService,activityLockedDao);
		Thread t1 = new Thread(dr);
		t1.start();
	}

}
