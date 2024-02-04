package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ActivityDao;
import com.covideinfo.model.DynamicActivity;
import com.covideinfo.model.DynamicActivityDetails;
import com.covideinfo.model.InternationalizaionLanguages;

@Repository("activityDao")
public class ActivityDaoImpl extends AbstractDao<Long, DynamicActivity> implements ActivityDao {

	

	@Override
	public boolean saveActivityData(DynamicActivity atpojo, List<DynamicActivityDetails> adlist) {
		boolean flag=false;
		try {
			getSession().save(atpojo);
			for(DynamicActivityDetails ad:adlist) {
				getSession().save(ad);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DynamicActivity> getActivityList() {
		List<DynamicActivity> atlist=null;
		atlist=getSession().createCriteria(DynamicActivity.class).list();
		return atlist;
	}

	@Override
	public DynamicActivity getActivityWithId(long id) {
		return (DynamicActivity) getSession().createCriteria(DynamicActivity.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public boolean updateStatus(DynamicActivity at) {
		boolean flag=false;
		try {
			getSession().update(at);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList() {
		List<InternationalizaionLanguages> list=null;
		list=getSession().createCriteria(InternationalizaionLanguages.class).list();
		return list;
	}

	@Override
	public boolean saveActivityData(List<DynamicActivity> alist) {
		boolean flag=false;
		try {
			for(DynamicActivity ad:alist) {
				getSession().save(ad);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean saveActivityDetails(List<DynamicActivityDetails> adlist) {
		boolean flag=false;
		try {
			for(DynamicActivityDetails adp:adlist) {
				getSession().save(adp);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DynamicActivityDetails> getDynamicActivityDetailsList() {
		List<DynamicActivityDetails> atlist=null;
		atlist=getSession().createCriteria(DynamicActivityDetails.class).list();
		return atlist;
	}

	@Override
	public boolean updateDynamicActivity(DynamicActivity dac) {
		boolean flag=false;
		try {
				getSession().update(dac);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public DynamicActivityDetails getDynamicActivityDetailsWithId(long id) {
		
		return null;
	}

	

}
