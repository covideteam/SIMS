package com.covideinfo.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dao.StudyGroupsDao;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityLog;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyGroups;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;

@Repository("studyGroupsDao")
public class StudyGroupsDaoImpl extends AbstractDao<Long, StudyGroups> implements StudyGroupsDao  {
    @Autowired
	StudyCreationDao studyCreationDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterWithActiveOnly(List<Long> ids) {
		StatusMaster activeStatus = null;
		activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyMaster> slist=null;
		try {
			/*if(ids.size()>0){
			slist=(List<StudyMaster>) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("studyState",activeStatus))
					.add(Restrictions.not(Restrictions.in("id", ids))).list();
			}else {*/
				slist=(List<StudyMaster>) getSession().createCriteria(StudyMaster.class)
						.add(Restrictions.eq("studyState",activeStatus)).list();
			/*}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return slist;
	}

	@Override
	public boolean saveStudyGroups(StudyGroups stugs) {
		boolean flag=false;
		try {
			getSession().save(stugs);
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyGroups> getStudyGroupsAll() {
		List<StudyGroups> glist=null;
		glist=getSession().createCriteria(StudyGroups.class)
				.add(Restrictions.eq("status", "Active")).list();
		return glist;
	}

	@Override
	public StudyMaster getStudyMasterWithId(long proid) {
		StudyMaster pojo=null;
		pojo=(StudyMaster) getSession().createCriteria(StudyMaster.class)
				.add(Restrictions.eq("id", proid)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getStudyGroupsSubjectCountWithStudyId(long proid,long sgid) {
		Integer scount=0;
		try {
			List<StudyGroups> list= new ArrayList<>();
			list=getSession().createCriteria(StudyGroups.class)
					.add(Restrictions.eq("study.id", proid))
					.add(Restrictions.ne("id", sgid)).list();
			for(StudyGroups data:list) {
				scount=scount+data.getNoofSubjects();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyGroups> getstudyGroupsListWithStuddyId(long proid) {
		List<StudyGroups> list= new ArrayList<>();
		list=getSession().createCriteria(StudyGroups.class)
				.add(Restrictions.eq("study.id", proid))
				.add(Restrictions.eq("status", "Active")).list();
		return list;
	}

	@Override
	public StudyGroups getStudyGroupsWithId(long groupid) {
		StudyGroups pojo=null;
		pojo=(StudyGroups) getSession().createCriteria(StudyGroups.class)
				.add(Restrictions.eq("id", groupid)).uniqueResult();
		return pojo;
	}

	@Override
	public boolean updateStudyGroups(StudyGroups updatedata) {
		boolean flag=false;
		try {
			getSession().update(updatedata);
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}
	@Override
	public boolean studyGroupsStatusChnage(StudyGroups updatedata) {
		boolean flag=false;
		try {
			getSession().update(updatedata);
			flag=true;
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyGroups> getStudyGroupsCheckVal(StudyGroups stugs, Date fdate, Date tdate) {
		List<StudyGroups> list= new ArrayList<>();
		list= getSession().createCriteria(StudyGroups.class)
				.add(Restrictions.eq("study.id", stugs.getStudy().getId()))
				.add(Restrictions.between("createdOn", fdate, tdate))
				.addOrder(Order.asc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getStudySubjectsWithCreateDate(StudyGroups sgpojo) throws ParseException {
		List<StudySubjects> list=new ArrayList<>();
		
		Integer subVal=0;
		String fromDate="";
		String toDate="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		fromDate = dateFormat.format(sgpojo.getCreatedOn());
		toDate = dateFormat.format(sgpojo.getGroupDate());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fromDate = fromDate +" 00:00:00";
		toDate = toDate +" 23:59:00";
		Date fdate = sdf.parse(fromDate);
		Date tdate = sdf.parse(toDate);
		 
		list= getSession().createCriteria(StudySubjects.class)
				.add(Restrictions.between("createdOn", fdate, tdate))
				.addOrder(Order.asc("id")).list();
		subVal=list.size();
		return subVal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getStudyGroupsSubjectCountAvelable(long proid) {
		Integer scount=0;
		try {
			List<StudyGroups> list= new ArrayList<>();
			list=getSession().createCriteria(StudyGroups.class)
					.add(Restrictions.eq("study.id", proid)).list();
			for(StudyGroups data:list) {
				scount=scount+data.getNoofSubjects();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scount;
	}

	
	

}
