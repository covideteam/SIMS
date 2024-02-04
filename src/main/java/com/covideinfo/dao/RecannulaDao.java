package com.covideinfo.dao;

import com.covideinfo.dto.RecannulationDataDto;
import com.covideinfo.dto.RecannulationDto;

public interface RecannulaDao {

	RecannulationDto getRecannulationDtoDetails(Long studyId, String subjNo);

	String saveRecannulationData(RecannulationDataDto rcDto, Long userId);

}
