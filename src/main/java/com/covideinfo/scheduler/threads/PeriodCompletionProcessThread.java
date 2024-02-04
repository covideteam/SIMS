package com.covideinfo.scheduler.threads;
/*package com.covideinfo.scheduler.threads;

import java.util.List;

import com.covideinfo.dao.PeriodChekoutDao;
import com.covideinfo.model.StudyMaster;

public class PeriodCompletionProcessThread implements Runnable {
	
	private PeriodChekoutDao periodCheckOutDao;

	public PeriodCompletionProcessThread(PeriodChekoutDao periodCheckOutDao) {
		this.periodCheckOutDao = periodCheckOutDao;
	}
	@Override
	public void run() {
		
		List<StudyMaster> smList = periodCheckOutDao.getStudyMasterList();
		if(smList.size() > 0) {
			for(StudyMaster sm : smList) {
				String result = periodCheckOutDao.processToPeriodColose(sm);
				System.out.println("PeriodCompletionProcessThread :"+result);
			}
		}

	}

}
*/