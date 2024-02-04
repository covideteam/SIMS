package com.covideinfo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.covideinfo.dao.PeriodChekoutDao;

@Configuration
@EnableScheduling
public class ScheduledConfiguration {
	
	@Autowired
	PeriodChekoutDao periodChkDao;
	
//	@Scheduled(cron="*/5 * * * * ?")
//	@Scheduled(cron="0 0 */1 * * ?")
    /*public void periodCheoutScheduler() {
		System.out.println("Every 1 hour periodCheoutScheduler Task");
		PeriodCheckoutThread.periodcheckoutProcess(periodChkDao);
	}*/

//	@Scheduled(cron="*/5 * * * * ?")
//	@Scheduled(cron="0 0 */1 * * ?")
   /* public void periodCompletionScheduler() {
		System.out.println("Every 1 hour periodCompletionScheduler Task");
		PeriodCheckoutProcessBasedOnWashoutPeriodsThread.periodCheckoutProcessBasedOnStudyWashoutPeriod(periodChkDao);
	}*/
	
   
}
