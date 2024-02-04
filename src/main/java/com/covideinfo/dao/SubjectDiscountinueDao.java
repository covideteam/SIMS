package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.StudyWitdrawalSavingDto;
import com.covideinfo.dto.StudyWithDrawDetailsDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StudySubjects;

public interface SubjectDiscountinueDao {

	List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList);

	
	CustomActivityParameters getCustomActivityParametersRecord(Long activityId);

	StudySubjects getStudySubjectRecord(Long volId);

	String updateStudySubjectRecord(StudySubjects ssub);


	StudyWithDrawDetailsDto getStudyWithDrawDetailsDtoDetails(Long studyId);


	String saveStaticActivityDataDetails(StudyWitdrawalSavingDto swsDto, Long userId);

}
