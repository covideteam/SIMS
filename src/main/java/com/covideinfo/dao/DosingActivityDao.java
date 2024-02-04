package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;

public interface DosingActivityDao {

	List<StudyMaster> getStudyMasterList();

	List<StudySubjects> getStudySubjects(Long id);

	List<StudyGroup> getStudyGroupWithProid(long proid);

	StudyMaster findByStudyId(long proid);

	UserMaster getUserdetails(String username);

	boolean saveStudyGroupForProject(StudyGroup sg);

	StudyMaster getStudyMasterWithId(Long id);

	List<StudyPeriodMaster> getStudyPeriodMasterWithStudyId(long id);

	boolean saveStudyGroupForProjectAndStudyGroupPeriodMaster(StudyGroup sg, List<StudyGroupPeriodMaster> sgpmList);

	


}
