package com.covideinfo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dto.StudyDesignFromDto;
import com.covideinfo.model.ActivityLockedData;
import com.covideinfo.service.ActivityLockedService;

@Service("/activityLockedService")
public class ActivityLockedServiceImpl implements ActivityLockedService {

	@Autowired
	ActivityLockedDao activityLockedDao;
	
	@Override
	public boolean saveActivityLockedData(StudyDesignFromDto sdform,String username) {
		
		ActivityLockedData ald=new ActivityLockedData();
		ald.setActivityName(sdform.getActivityName());
		ald.setCreatedBy(username);
		ald.setCreatedOn(new Date());
		ald.setStubjectId(Long.parseLong(sdform.getSubjectId()));
		ald.setStudyId(Long.parseLong(sdform.getStudyId()));
		
		return activityLockedDao.saveActivityLockedData(ald);
	}

	@Override
	public ActivityLockedData getActivityLockedDataExitOrNot(StudyDesignFromDto sdfrom) {
		return activityLockedDao.getActivityLockedDataExitCheck(sdfrom);
	}

	@Override
	public boolean deleteActivityLockingData(ActivityLockedData aldpojo) {
		return activityLockedDao.deleteActivityLockingData(aldpojo);
	}

	@Override
	public boolean removeData(ActivityLockedService activityLockedService,SimpleDateFormat obj, ActivityLockedDao actlDao) {
		boolean flag =true;
		List<ActivityLockedData> lockdata= actlDao.getActivityLockedDataList();
		List<ActivityLockedData> lockedDataList=new ArrayList<>();
		try {
			if(lockdata != null && lockdata.size() > 0) {
				for(ActivityLockedData ll:lockdata) {
					// Use parse method to get date object of both dates 
		            Date date1 =ll.getCreatedOn();   
		            Date date = Calendar.getInstance().getTime();  
	                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                String strDate = dateFormat.format(date);  
	                Date date2 = dateFormat.parse(strDate);   
		            // Calucalte time difference in milliseconds   
		            long time_difference = date2.getTime() - date1.getTime();  
		           if(time_difference>600000) {
		            	lockedDataList.add(ll);
		            }
		         }
				if(lockedDataList != null && lockedDataList.size() > 0)
					flag=actlDao.removeData(lockedDataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
