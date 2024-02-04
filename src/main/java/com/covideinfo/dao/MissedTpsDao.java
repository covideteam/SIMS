package com.covideinfo.dao;

import java.util.Locale;

import com.covideinfo.dto.MissedTimePointsDetailsDto;
import com.covideinfo.dto.MissedTimePointsDto;

public interface MissedTpsDao {

	MissedTimePointsDto getMissedTimePointsDtoDetails();

	MissedTimePointsDetailsDto generateMissedTimepointsReport(Long studyId, Locale currentLocale);

}
