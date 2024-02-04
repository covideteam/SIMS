package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ActivityLockedDao;
import com.covideinfo.dto.StudyDesignFromDto;
import com.covideinfo.model.ActivityLockedData;

@Repository("activityLockedDao")
public class ActivityLockedDaoImpl extends AbstractDao<Long, ActivityLockedData> implements ActivityLockedDao {

	@Override
	public boolean saveActivityLockedData(ActivityLockedData ald) {
		boolean flag=false;
		try {
			getSession().save(ald);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public ActivityLockedData getActivityLockedDataExitCheck(StudyDesignFromDto sdform) {
		ActivityLockedData ald=null;
		if(sdform.getSubjectId() != null && !sdform.getSubjectId().equals("")) {
			if(Long.parseLong(sdform.getSubjectId()) > 0) {
				ald=(ActivityLockedData) getSession().createCriteria(ActivityLockedData.class)
						.add(Restrictions.eq("studyId", Long.parseLong(sdform.getStudyId())))
						.add(Restrictions.eq("stubjectId", Long.parseLong(sdform.getSubjectId())))
						.add(Restrictions.eq("activityName", sdform.getActivityName())).uniqueResult();
			}else {
				ald=(ActivityLockedData) getSession().createCriteria(ActivityLockedData.class)
						.add(Restrictions.eq("studyId", Long.parseLong(sdform.getStudyId())))
						.add(Restrictions.eq("activityName", sdform.getActivityName())).uniqueResult();
			}
		}
		return ald;
	}

	@Override
	public boolean deleteActivityLockingData(ActivityLockedData aldpojo) {
		boolean flag=false;
		try {
			if(aldpojo != null)
				getSession().delete(aldpojo);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityLockedData> getActivityLockedDataList() {
		List<ActivityLockedData> list=null;
		list=getSession().createCriteria(ActivityLockedData.class).list();
		return list;
	}

	@Override
	public boolean removeData(List<ActivityLockedData> lockdataremo) {
		boolean flag=false;
		try {
			for(ActivityLockedData dd:lockdataremo) {
				getSession().delete(dd);
				flag=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

}
