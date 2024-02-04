package com.covideinfo.dto;

import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.service.ActivityLockedService;

public class DataRemove implements Runnable {
	 private ActivityLockedService activityLockedService;
	 private  ActivityLockedDao activityLockedDao;
	 private  SubjectDataRemove sdr;
	
	public DataRemove(SubjectDataRemove sdr, ActivityLockedService activityLockedService, ActivityLockedDao activityLockedDao) {
		super();
		this.activityLockedService = activityLockedService;
		this.activityLockedDao = activityLockedDao;
		this.sdr = sdr;
	}
	
	@Override
	public void run() {
		try {
			sdr.deleteActivityLockedData(activityLockedService,activityLockedDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 /*while (true) {
			   try {
				 long start = System.currentTimeMillis();
			     Thread.sleep(240000);//two minute
			     System.out.println("Sleep time in ms = "+(System.currentTimeMillis()-start));
				sdr.deleteActivityLockedData(activityLockedService,activityLockedDao);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
		   }*/
		
		
	}

}
