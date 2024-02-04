package com.covideinfo.service;

import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.RecannulationDto;

public interface RecannulaService {

	RecannulationDto getRecannulationDtoDetails(Long studyId, String subjNo);

	String saveRecannulationData(RecannulationDataDto rcDto, Long userId);

}
