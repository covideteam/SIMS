package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.SubjectAllotmentDto;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.SubjectActivityData;

public interface SubjectAllotmentDao {

	String subjectAllotment(SubjectActivityData sad, String userName, String subjectNo);

	List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList);

	SubjectAllotmentDto getSubjectAllotmentDtoDetails(Long studyId);

	CustomActivityParameters getCustomActivityParametersRecord(Long activityId);

}
