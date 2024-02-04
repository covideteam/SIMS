package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.StudyMaster;

public interface PeriodChekoutDao {

	List<StudyMaster> getStudyMasterList();

	String getStudySubjectsRecordsList(StudyMaster sm);

	String processToPeriodColose(StudyMaster sm);

}
