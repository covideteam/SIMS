package com.covideinfo.service;

import com.covideinfo.dto.StudyWitdrawalSavingDto;
import com.covideinfo.dto.StudyWithDrawDto;
import com.covideinfo.model.SubjectActivityData;

public interface SubjectDiscontinueService {

	String updateStudySubjectRecords(SubjectActivityData sad, String userName);

	StudyWithDrawDto getStudyWithDrawDtoDetails(Long studyId);

	String saveStaticActivityDataDetails(StudyWitdrawalSavingDto swsDto, Long userId, String dateFormat);

}
