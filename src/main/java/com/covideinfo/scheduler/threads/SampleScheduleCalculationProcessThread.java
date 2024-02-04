package com.covideinfo.scheduler.threads;

import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.service.SampleScheduleCalculationService;

public class SampleScheduleCalculationProcessThread implements Runnable {
	
	private SampleScheduleCalculationService sscService;
	private SubjectDoseTimePoints sdtp;
	private Long userId;
	private String operation;
	
	public SampleScheduleCalculationProcessThread(SampleScheduleCalculationService sscService, SubjectDoseTimePoints sdtp, Long userId, String operation) {
		this.sdtp = sdtp;
		this.sscService = sscService;
		this.userId = userId;
		this.operation = operation;
	}

	@Override
	public void run() {
		String result = sscService.calculatescheduleTimes(sdtp, userId, operation);
		System.out.println(result);
	}

}
