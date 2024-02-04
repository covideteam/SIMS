package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.DeviationsDto;
import com.covideinfo.model.StudyMaster;

public interface DeviationsDao {

	List<StudyMaster> getStudyMastersList();

	DeviationsDto getgetDeviationRecords(Long projectId, Long roleId, String type);

	String saveDeviationRecords(Long deviationId, Long roleId, String type, String comments, Long userId, String submitType);

}
