package com.covideinfo.scheduler.threads;

import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.service.SampleScheduleCalculationService;

public class SampleScheduleTimesCalculationThread {
	
	public static void calculateScheduleTimesBasedOnSubjectDoseTime(SubjectDoseTimePoints sdtp, SampleScheduleCalculationService sscService, Long userId, String operation) {
		SampleScheduleCalculationProcessThread sscpt = new SampleScheduleCalculationProcessThread(sscService, sdtp, userId, operation);
		Thread t1 = new Thread(sscpt);
		t1.start();
	}

}
