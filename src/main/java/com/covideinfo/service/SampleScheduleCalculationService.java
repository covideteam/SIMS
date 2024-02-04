package com.covideinfo.service;

import com.covideinfo.model.SubjectDoseTimePoints;

public interface SampleScheduleCalculationService {

	String calculatescheduleTimes(SubjectDoseTimePoints sdtp, Long userId, String operation);

}
