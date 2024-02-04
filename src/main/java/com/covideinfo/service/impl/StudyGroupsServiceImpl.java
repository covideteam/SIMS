package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.GroupsDao;
import com.covideinfo.dao.StudyGroupsDao;
import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.StudyGroupsService;

@Service("studyGroupsService")
public class StudyGroupsServiceImpl implements StudyGroupsService {
	
	@Autowired
	GroupsDao groupsDao;
	@Autowired
	LanguageService languageService;
	@Autowired
	StudyGroupsDao studyGroupsDao;
	
	@Override
	public List<StudyMaster> getStudyMasterWithActiveOnly(List<StudyGroups> sgList) {
		List<Long> ids=new ArrayList<>();
		for(StudyGroups sg:sgList) {
			ids.add(sg.getStudy().getId());
		}
		return studyGroupsDao.getStudyMasterWithActiveOnly(ids);
	}

	@Override
	public boolean saveStudyGroups(StudyGroups stugs, String userName) {
		stugs.setCreatedBy(userName);
		stugs.setCreatedOn(new Date());
		stugs.setGroupDate(new Date());
		return studyGroupsDao.saveStudyGroups(stugs);
	}

	@Override
	public List<StudyGroups> getStudyGroupsAll() {
		return studyGroupsDao.getStudyGroupsAll();
	}

	@Override
	public String getAvailableSubjectCount(long proid,long sgid) {
		Integer countVal=0;
		Integer subVal=0;
		String countFinal="";
		try {
			StudyMaster pojo=studyGroupsDao.getStudyMasterWithId(proid);
			StudyGroups sgpojo=studyGroupsDao.getStudyGroupsWithId(sgid);
			Integer count=studyGroupsDao.getStudyGroupsSubjectCountWithStudyId(proid,sgid);
			countVal=pojo.getNoOfSubjects()-count;
			subVal=studyGroupsDao.getStudySubjectsWithCreateDate(sgpojo);
			countFinal=subVal+"##"+countVal;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countFinal;
	}

	@Override
	public List<StudyGroups> getstudyGroupsListWithStuddyId(long proid) {
		return studyGroupsDao.getstudyGroupsListWithStuddyId(proid);
	}

	@Override
	public StudyGroups getStudyGroupsWithId(long groupid) {
		return studyGroupsDao.getStudyGroupsWithId(groupid);
	}

	@Override
	public boolean updateStudyGroups(StudyGroups updatedata, String userName) {
		updatedata.setUpdatedBy(userName);
		updatedata.setUpdatedOn(new Date());
		updatedata.setGroupDate(new Date());
		return studyGroupsDao.updateStudyGroups(updatedata);
	}

	@Override
	public boolean studyGroupsStatusChnage(StudyGroups pojo) {
		boolean flag=false;
		pojo.setStatus("InActive");
		flag=studyGroupsDao.studyGroupsStatusChnage(pojo);
		return flag;
	}

	@Override
	public List<StudyGroups> getStudyGroupsCheckVal(StudyGroups stugs) {
		List<StudyGroups> list = null;
		try {
			 
			 Date fdate = stugs.getCreatedOn();
			 Calendar c = Calendar.getInstance(); 
			 c.setTime(fdate); 
			 c.add(Calendar.DATE, 1);
			 fdate = c.getTime();
			 Date tdate = new Date();
			 list = studyGroupsDao.getStudyGroupsCheckVal(stugs,fdate,tdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getAvailableSubjectCountTwo(long proid) {
		Integer countVal=0;
		String countFinal="";
		try {
			StudyMaster pojo=studyGroupsDao.getStudyMasterWithId(proid);
			Integer count=studyGroupsDao.getStudyGroupsSubjectCountAvelable(proid);
			countVal=pojo.getNoOfSubjects()-count;
			countFinal=""+countVal;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countFinal;
	}

	@Override
	public StudyMaster getStudyMasterWithId(long proid) {
		return studyGroupsDao.getStudyMasterWithId(proid);
	}

	

	
}
