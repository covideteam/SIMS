package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DosingActivityDao;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DosingActivityService;
import com.covideinfo.service.StudyDesignService;
import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;

@Service("dosingActivityService")
public class DosingActivityServiceImpl implements DosingActivityService {

	@Autowired
	DosingActivityDao dosingActivityDao;
	@Autowired
	StudyDesignService studyDesignService;
	

	@Override
	public List<StudyMaster> getStudyMasterList() {
		
		return dosingActivityDao.getStudyMasterList();
	}

	@Override
	public List<StudySubjects> getStudySubjects(Long id) {
		return dosingActivityDao.getStudySubjects(id);
	}

	@Override
	public boolean saveStudyGroupForProject(StudyMaster sm,UserMaster user) {
		
		boolean flag=false;
		List<StudyGroup> studyglist=dosingActivityDao.getStudyGroupWithProid(sm.getId());
		List<StudyPeriodMaster> spmList=dosingActivityDao.getStudyPeriodMasterWithStudyId(sm.getId());
		Collections.sort(spmList);
		List<StudyGroupPeriodMaster> sgpmList=new ArrayList<>();
		StudyGroup sg=new StudyGroup();
		sg.setCreatedBy(user.getFullName());
		sg.setCreatedOn(new Date());
		sg.setStudy(sm);
		sg.setGroupno(studyglist.size()+1);
		sg.setGroupName("G"+sg.getGroupno());
		
		for(StudyPeriodMaster period:spmList){
			StudyGroupPeriodMaster sgpmPojo=new StudyGroupPeriodMaster();
			sgpmPojo.setCreatedBy(user.getUsername());
			sgpmPojo.setCreatedOn(new Date());
			sgpmPojo.setPeriod(period);
			sgpmPojo.setPeriodName(period.getPeriodName());
			if(period.getPeriodNo()==1) {
				StatusMaster statusm=studyDesignService.getStatusMasterForSubmit(StudyStatus.ACTIVE.toString());
				sgpmPojo.setPeriodStatus(statusm);
			}else {
				StatusMaster statusm=studyDesignService.getStatusMasterForSubmit(StudyStatus.NEW.toString());
				sgpmPojo.setPeriodStatus(statusm);
			}
			sgpmPojo.setPeriodNo(period.getPeriodNo());
			sgpmPojo.setStudyGroup(sg);
			sgpmPojo.setStudyId(sm.getId());
			sgpmList.add(sgpmPojo);
		}
		
		flag =dosingActivityDao.saveStudyGroupForProjectAndStudyGroupPeriodMaster(sg,sgpmList);
		return flag;
	}

	@Override
	public UserMaster getUserdetails(String username) {
		return dosingActivityDao.getUserdetails(username);
	}

	@Override
	public StudyMaster findByStudyId(long proid) {
		
		return dosingActivityDao.findByStudyId(proid);
	}

	@Override
	public StudyMaster getStudyMasterWithId(Long id) {
		return dosingActivityDao.getStudyMasterWithId(id);
	}

	

	
}
