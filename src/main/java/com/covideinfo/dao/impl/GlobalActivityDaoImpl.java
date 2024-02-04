package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.GlobalActivityDao;
import com.covideinfo.dto.GlobalActivityDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.DefaultActivitys;
import com.covideinfo.model.DynamicFormUrls;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;

@Repository("globalActivityDao")
public class GlobalActivityDaoImpl extends AbstractDao<Long, GlobalValues> implements GlobalActivityDao {

	@SuppressWarnings("unchecked")
	@Override
	public GlobalActivityDto getGlobalActivityDetails(Long langId) {
		GlobalActivityDto gaDto = null;
		List<InternationalizaionLanguages> inlList = null;
	    List<LanguageSpecificGlobalActivityDetails> lsgList = null;
	    List<RoleMaster> roleList = null;
	    List<LanguageSpecificGroupDetails> gbList = null;
	    InternationalizaionLanguages inlag = null;
	    StatusMaster stm =(StatusMaster) getSession().createCriteria(StatusMaster.class)
		.add(Restrictions.eq("statusCode", "ACTIVE")).uniqueResult();
		try {
			inlag = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			
			inlList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			lsgList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("inlagId.id", langId)).list();
			roleList = getSession().createCriteria(RoleMaster.class)
					.add(Restrictions.eq("roleStatus", stm)).list();
			gbList = getSession().createCriteria(LanguageSpecificGroupDetails.class)
					.add(Restrictions.eq("inlagId", inlag)).list();
					
			gaDto = new GlobalActivityDto();
			gaDto.setInlList(inlList);
			gaDto.setLsgList(lsgList);
			gaDto.setRolesList(roleList);
			gaDto.setGbList(gbList);
			gaDto.setInlag(inlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gaDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivity(String code) {
		return  getSession().createCriteria(GlobalActivity.class).add(Restrictions.eq("activityCode", code)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivities(List<String> codes) {
		return (List<GlobalActivity>)getSession().createCriteria(GlobalActivity.class).add(Restrictions.in("activityCode", codes)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getStudyDesignActivities() {
		ArrayList<String> activities =new ArrayList<String>();
		activities.add(StudyActivities.SkinSensivity.toString());
		activities.add(StudyActivities.SkinAdhesion.toString());
		activities.add(StudyActivities.InclusionCriteria.toString());
		activities.add(StudyActivities.ExclusionCriteria.toString());
		activities.add(StudyActivities.StudyExecutionVitals.toString());
		
		return (List<GlobalActivity>)getSession().createCriteria(GlobalActivity.class).add(Restrictions.not(Restrictions.in("activityCode", activities))).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@Override
	public String saveGlobalValesRecords(GlobalActivity ga, List<LanguageSpecificGlobalActivityDetails> lsadList, Long groupId) {
		String result ="Failed";
		long gaNo =0;
		GlobalGroups gp = null;
		DynamicFormUrls durl = null;
		try {
			
			durl = (DynamicFormUrls) getSession().createCriteria(DynamicFormUrls.class)
					    .add(Restrictions.eq("activityCode", ga.getActivityCode())).uniqueResult();
			if(durl != null) {
				if(durl.getGetUrl() != null && !durl.getGetUrl().equals(""))
					ga.setGetUrl(durl.getGetUrl());
				if(durl.getSaveUrl() != null && !durl.getSaveUrl().equals(""))
					ga.setSaveUrl(durl.getSaveUrl());
			}
			
			gp = (GlobalGroups) getSession().createCriteria(GlobalGroups.class)
					.add(Restrictions.eq("id", groupId)).uniqueResult();
			ga.setGroupId(gp);
			gaNo = (long) getSession().save(ga);
			if(gaNo > 0) {
				for(LanguageSpecificGlobalActivityDetails lsad : lsadList) {
					lsad.setGlobalActivity(ga);
					getSession().save(lsad);
					result = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public GlobalActivity getGlobalActivityRecord(String actName) {
		return (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.eq("name", actName)).uniqueResult();
	}

	@Override
	public GlobalActivity getGlobalActivityByCode(String activityCode) {
		return (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.eq("activityCode", activityCode)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalActivityDetails> getOtherActivities(Long langId) {
		List<String> strList = new ArrayList<>();
		List<Long> actIds = null;
		List<Long> dfactIds = null;
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		try {
			strList.add("SkinAdhesion");
			strList.add("SkinSensivity");
			strList.add("RestrictionCompliance");
			strList.add("DosingCollection");
			strList.add("MealsCollection");
			strList.add("StudyExecutionVitals");
			strList.add("SampleCollection");
			strList.add("InclusionCriteria");
			strList.add("ExclusionCriteria");
			strList.add("BAGGAGERETURN");
			strList.add("EcgTimePoints");
			Criteria criteria = getSession().createCriteria(GlobalActivity.class);
			
			dfactIds = getSession().createCriteria(DefaultActivitys.class)
					.setProjection(Projections.property("activity.id")).list();
			Criterion c1 = Restrictions.not(Restrictions.in("id", dfactIds));
			Criterion c2 = Restrictions.not(Restrictions.in("activityCode", strList));
			Criterion c3 = Restrictions.and(c1, c2);
			actIds = criteria.add(c3).setProjection(Projections.property("id")).list();
			if(actIds != null && actIds.size() > 0) {
				lspgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
						.add(Restrictions.in("globalActivity.id", actIds))
						.add(Restrictions.eq("inlagId.id", langId)).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgaList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificGlobalActivityDetails> getDefaultActivities(Long langId) {
		List<String> strList = new ArrayList<>();
		List<Long> dfactIds = null;
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		try {
			strList.add("SkinAdhesion");
			strList.add("SkinSensivity");
			strList.add("RestrictionCompliance");
			strList.add("DosingCollection");
			strList.add("MealsCollection");
			strList.add("StudyExecutionVitals");
			strList.add("SampleCollection");
			strList.add("InclusionCriteria");
			strList.add("ExclusionCriteria");
//			strList.add("BAGGAGERETURN");
			
			
			List<Long> actIds = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("activityCode", strList))
					.setProjection(Projections.property("id")).list();
			
			dfactIds = getSession().createCriteria(DefaultActivitys.class)
					.add(Restrictions.not(Restrictions.in("activity.id", actIds)))
					.setProjection(Projections.property("activity.id")).list();
			
			if(dfactIds != null && dfactIds.size() > 0) {
				lspgaList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
						.add(Restrictions.in("globalActivity.id", dfactIds))
						.add(Restrictions.eq("inlagId.id", langId)).list();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lspgaList;
	}
}
