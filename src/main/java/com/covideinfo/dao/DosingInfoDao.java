package com.covideinfo.dao;

import java.util.Date;
import java.util.List;

import com.covideinfo.dto.DataInfoPdfGenerationDto;
import com.covideinfo.dto.DosingDataInfoDto;
import com.covideinfo.dto.TimePointDtoDetails;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.DosingInfoData;
import com.covideinfo.model.StudyMaster;

public interface DosingInfoDao {

	TimePointDtoDetails getTimePointDtoDetails(long projectId);

	DosingDataInfoDto getDosingInfoRecord(Long projectId);

	String saveDoseInfoData(DosingInfo doseinfo, List<DosingInfoData> finalDoseDataList);

	String updateDosingInfoData(DosingInfo doseinfo, List<DosingInfoData> updatefinalDoseDataList,
			boolean updateDosiInfoFlag, boolean updateDoseDataInfoFlag);

	DataInfoPdfGenerationDto getDataInfoPdfGenerationDtoDetails(Long projectId, Long langId, Long userId);

	Date dosingInfo(StudyMaster study, Long statusId);

}
