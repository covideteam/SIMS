package com.covideinfo.dao;

import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.model.GlobalActivity;

public interface ActivityUpdateDao {

	ActivityDetailsDto getActivityDetails(Long activityId);

	GlobalActivity getActivityRecord(Long activityId);

	String updateActivity(GlobalActivity ga, Long userId);


}
