package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;

public interface StudyGroupsService {

	List<StudyMaster> getStudyMasterWithActiveOnly(List<StudyGroups> sgList);

	boolean saveStudyGroups(StudyGroups stugs, String userName);

	List<StudyGroups> getStudyGroupsAll();

	String getAvailableSubjectCount(long proid, long sgid);

	List<StudyGroups> getstudyGroupsListWithStuddyId(long proid);

	StudyGroups getStudyGroupsWithId(long groupid);

	boolean updateStudyGroups(StudyGroups updatedata, String userName);

	boolean studyGroupsStatusChnage(StudyGroups pojo);

	List<StudyGroups> getStudyGroupsCheckVal(StudyGroups stugs);

	String getAvailableSubjectCountTwo(long proid);

	StudyMaster getStudyMasterWithId(long proid);

	

}
