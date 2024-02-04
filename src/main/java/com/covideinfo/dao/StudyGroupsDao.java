package com.covideinfo.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;

public interface StudyGroupsDao {

	List<StudyMaster> getStudyMasterWithActiveOnly(List<Long> ids);

	boolean saveStudyGroups(StudyGroups stugs);

	List<StudyGroups> getStudyGroupsAll();

	StudyMaster getStudyMasterWithId(long proid);

	Integer getStudyGroupsSubjectCountWithStudyId(long proid,long sgid);

	List<StudyGroups> getstudyGroupsListWithStuddyId(long proid);

	StudyGroups getStudyGroupsWithId(long groupid);

	boolean updateStudyGroups(StudyGroups updatedata);

	boolean studyGroupsStatusChnage(StudyGroups pojo);

	List<StudyGroups> getStudyGroupsCheckVal(StudyGroups stugs, Date fdate, Date tdate);

	Integer getStudySubjectsWithCreateDate(StudyGroups sgpojo) throws ParseException;

	Integer getStudyGroupsSubjectCountAvelable(long proid);

	

}
