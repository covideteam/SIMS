package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.DosingActivityDao;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserMaster;

@Repository("dosingActivityDao")
public class DosingActivityDaoImpl extends AbstractDao<Long, StudyGroup> implements DosingActivityDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterList() {
		List<StudyMaster> sml=null;
		sml=getSession().createCriteria(StudyMaster.class).list();
		return sml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudySubjects> getStudySubjects(Long id) {
		List<StudySubjects> sml=new ArrayList<>();
	try {
		List<StudyVolunteerReporting> svrList=getSession().createCriteria(StudyVolunteerReporting.class)
				.add(Restrictions.eq("sm.id", id)).list();
		List<Long> ids=new ArrayList<>();
		for(StudyVolunteerReporting sv:svrList) {
			ids.add(sv.getId());
		}
		
		
        if(ids.size()>0) {
        	sml=getSession().createCriteria(StudySubjects.class)
    				.add(Restrictions.in("reportingId.id", ids)).list();
		}
        
	} catch (Exception e) {
		e.printStackTrace();
	}
	return sml;	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyGroup> getStudyGroupWithProid(long proid) {
		List<StudyGroup> list=null;
		list=getSession().createCriteria(StudyGroup.class)
				.add(Restrictions.eq("study.id",proid)).list();
		return list;
	}

	@Override
	public StudyMaster findByStudyId(long proid) {
		
		return (StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("id", proid)).uniqueResult();
	}

	@Override
	public UserMaster getUserdetails(String username) {
		return (UserMaster) getSession().createCriteria(UserMaster.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	public boolean saveStudyGroupForProject(StudyGroup sg) {
		boolean flag=false;
		try {
			getSession().save(sg);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public StudyMaster getStudyMasterWithId(Long id) {
		StudyMaster pojo=null;
		pojo=(StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterWithStudyId(long id) {
		List<StudyPeriodMaster> list=null;
		list=getSession().createCriteria(StudyPeriodMaster.class)
				.add(Restrictions.eq("study.id", id)).list();
		return list;
	}

	@Override
	public boolean saveStudyGroupForProjectAndStudyGroupPeriodMaster(StudyGroup sg,
			List<StudyGroupPeriodMaster> sgpmList) {
		boolean flag=false;
		try {
			getSession().save(sg);
			for(StudyGroupPeriodMaster add:sgpmList) {
				getSession().save(add);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	

	

	
}
