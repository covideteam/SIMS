package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;

public interface DosingActivityService {

	List<StudyMaster> getStudyMasterList();

	List<StudySubjects> getStudySubjects(Long id);

	boolean saveStudyGroupForProject(StudyMaster sm, UserMaster user);

	UserMaster getUserdetails(String username);

	StudyMaster findByStudyId(long proid);

	StudyMaster getStudyMasterWithId(Long id);


	

}
