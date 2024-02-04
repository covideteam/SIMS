package com.covideinfo.service;

import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.ActivityUpdateDataDto;

public interface ActivityUpdateService {

	ActivityDetailsDto getActivityDetails(Long activityId);

	String saveUpdatedActivityData(ActivityUpdateDataDto actUpDto, Long userId);

}
