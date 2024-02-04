package com.covideinfo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.GroupsDao;
import com.covideinfo.dto.GlobalGroupsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GroupDesignActivity;
import com.covideinfo.model.GroupDesignActivityDetails;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;

@Repository("groupsDao")
public class GroupsDaoImpl extends AbstractDao<Long, LanguageSpecificGroupDetails> implements GroupsDao  {

	@SuppressWarnings("unchecked")
	@Override
	public GlobalGroupsDto getGlobalGroupsDtoDetails(Long langId) {
		GlobalGroupsDto ggdto = null;
		List<InternationalizaionLanguages> inlList = null;
		List<LanguageSpecificGroupDetails> lsgList = null;
		InternationalizaionLanguages inalg = null;
		try {
			inlList = getSession().createCriteria(InternationalizaionLanguages.class).list();
			lsgList = getSession().createCriteria(LanguageSpecificGroupDetails.class).list();
			inalg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("id", langId)).uniqueResult();
			ggdto = new GlobalGroupsDto();
			ggdto.setInlList(inlList);
			ggdto.setLsgList(lsgList);
			ggdto.setInlag(inalg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ggdto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return getSession().createCriteria(InternationalizaionLanguages.class).list();
	}

	@Override
	public String saveGlobalValesRecords(GlobalGroups gd, List<LanguageSpecificGroupDetails> lsgdList) {
		String result ="Failed";
		long gdNo =0;
		try {
			gdNo = (long) getSession().save(gd);
			if(gdNo > 0) {
				for(LanguageSpecificGroupDetails lsgd : lsgdList) {
					lsgd.setGloblgroupId(gd);
					getSession().save(lsgd);
					result = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalActivity> getGlobalActivityList() {
		List<GlobalActivity> list=null;
		list=getSession().createCriteria(GlobalActivity.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalGroups> getGlobalGroupsList() {
		int val=0;
		List<GlobalGroups> list=null;
		list=getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.ne("noofcolums", val)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalParameterList() {
		List<GlobalParameter> list=null;
		list=getSession().createCriteria(GlobalParameter.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalParameterWithGroupId(long id,long actid) {
		List<GlobalParameter> list=null;
		list=getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.eq("groups.id", id))
				.add(Restrictions.eq("activity.id", actid))
				.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalGroups> getGlobalGroupsWithActivityId(long id) {
		List<GlobalGroups> list=null;
		list=getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.eq("groups.id", id)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalParameterWithActivityId(long id) {
		List<GlobalParameter> list=null;
		list=getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.eq("activity.id", id)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalGroups> getGlobalGroupsWithParameterBased(List<Long> ids) {
		int val=0;
		List<GlobalGroups> list=null;
		list=getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.in("id", ids))
				.add(Restrictions.ne("noofcolums", val)).list();
		return list;
	}

	@Override
	public GlobalGroups getGroupRowsAndColumsCount(long id) {
		GlobalGroups ggpojo=null;
		ggpojo=(GlobalGroups) getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return ggpojo;
	}

	@Override
	public GlobalActivity getGlobalActivityWithId(long id) {
		GlobalActivity pojo=null;
		pojo=(GlobalActivity) getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public GlobalGroups getGlobalGroupsWithId(long id) {
		GlobalGroups pojo=null;
		pojo=(GlobalGroups) getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public GlobalParameter getGlobalParameterWithId(long id) {
		GlobalParameter pojo=null;
		pojo=(GlobalParameter) getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@Override
	public boolean saveGlobalGroupDesignActivity(GroupDesignActivity gdaPojo,
			List<GroupDesignActivityDetails> gdadList,List<GroupDesignActivityDetails> gdUpdateList) {
		boolean flag=false;
		try {
			getSession().save(gdaPojo);
			for(GroupDesignActivityDetails upp:gdUpdateList) {
				getSession().update(upp);
			}
			for(GroupDesignActivityDetails add:gdadList) {
				getSession().save(add);
			}
			
			flag=true;
		} catch (Exception e) {
			flag=true;
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivityDetailsList(
			InternationalizaionLanguages language) {
		Map<Long, LanguageSpecificGlobalActivityDetails> map=new HashMap<>();
		List<LanguageSpecificGlobalActivityDetails> list=getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
				.add(Restrictions.eq("inlagId", language)).list();
		for(LanguageSpecificGlobalActivityDetails add:list) {
			map.put(add.getGlobalActivity().getId(), add);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, LanguageSpecificGroupDetails> getLanguageSpecificGroupDetailsList(
			InternationalizaionLanguages language) {
		Map<Long, LanguageSpecificGroupDetails> map=new HashMap<>();
		List<LanguageSpecificGroupDetails> list=getSession().createCriteria(LanguageSpecificGroupDetails.class)
				.add(Restrictions.eq("inlagId", language)).list();
		for(LanguageSpecificGroupDetails add:list) {
			map.put(add.getGloblgroupId().getId(), add);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParameterDetailsList(
			InternationalizaionLanguages language) {
		Map<Long, LanguageSpecificGlobalParameterDetails> map=new HashMap<>();
		List<LanguageSpecificGlobalParameterDetails> list=getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
				.add(Restrictions.eq("inlagId", language)).list();
		for(LanguageSpecificGlobalParameterDetails add:list) {
			map.put(add.getParameterId().getId(), add);
		}
		return map;
	}

	@Override
	public GlobalGroups getGlobalGroupWithName(String value) {
		GlobalGroups pojo=null;
		pojo=(GlobalGroups) getSession().createCriteria(GlobalGroups.class)
				.add(Restrictions.eq("name", value)).uniqueResult();
		return pojo;
	}

	@Override
	public GroupDesignActivityDetails getGroupDesignActivityDetailsWithParamAndActivity(GlobalParameter gppojo,
			GlobalActivity activitypojo) {
		GroupDesignActivityDetails pojo=null;
		pojo=(GroupDesignActivityDetails) getSession().createCriteria(GroupDesignActivityDetails.class)
				.add(Restrictions.eq("parameterId", gppojo))
				.add(Restrictions.eq("activityId", activitypojo)).uniqueResult();
		return pojo;
	}

	
	

}
