package com.covideinfo.scheduler.threads;
/*package com.covideinfo.scheduler.threads;

import java.util.List;

import com.covideinfo.dao.PeriodChekoutDao;
import com.covideinfo.model.StudyMaster;

public class PeriodCheckoutProcessThread implements Runnable {

	private PeriodChekoutDao periodCheckoutDao;

	public PeriodCheckoutProcessThread(PeriodChekoutDao periodCheckoutDao) {
		this.periodCheckoutDao = periodCheckoutDao;
	}

	@Override
	public void run() {
		List<StudyMaster> smList = periodCheckoutDao.getStudyMasterList();
		if(smList != null && smList.size() > 0) {
			for(StudyMaster sm : smList) {
				String result = periodCheckoutDao.getStudySubjectsRecordsList(sm);
				System.out.println(result);
			}
		}
	}

}
*/