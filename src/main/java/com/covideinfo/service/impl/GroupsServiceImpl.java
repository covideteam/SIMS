package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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
import com.covideinfo.service.GroupsService;

@Service("groupsService")
public class GroupsServiceImpl implements GroupsService {
	
	@Autowired
	GroupsDao groupsDao;
	@Autowired
	LanguageService languageService;

	@Override
	public GlobalGroupsDto getGroupsDetails(MessageSource messageSource, Locale currentLocale,Long langId) {
		GlobalGroupsDto ggDto = null;
		List<InternationalizaionLanguages> inlList = null;
		List<LanguageSpecificGroupDetails> lsgList = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<Long, GlobalGroups> ggMap = new HashMap<>();
		Map<Long, List<LanguageSpecificGroupDetails>> lsMap = new HashMap<>();
		InternationalizaionLanguages inlag = null;
		try {
			ggDto = groupsDao.getGlobalGroupsDtoDetails(langId);
			if(ggDto != null) {
				inlList = ggDto.getInlList();
				lsgList = ggDto.getLsgList();
				inlag = ggDto.getInlag();
				if(inlList != null && inlList.size() > 0) {
					for(InternationalizaionLanguages inl : inlList) {
						Locale locale = new Locale(inlag.getLangCode(), inlag.getCountryCode());
						String label = messageSource.getMessage("label.gvName", null,locale);
						String langName = "";
						if(inl.getLanguage().equals(inlag.getLanguage())) {
							if(inl.getLanguage().equals("ENGLISH")) {
								langName = messageSource.getMessage("label.gvNameLag", null,locale);
								lanMap = getLanguageMap(inl, langName, lanMap, label);
							}else {
								langName = messageSource.getMessage("label.gvNameSpanish", null,locale);
								lanMap = getLanguageMap(inl, langName, lanMap, label);
							}
						}else {
							if(inl.getLanguage().equals("ENGLISH")) {
								langName = messageSource.getMessage("label.gvNameLag", null,locale);
								lanMap = getLanguageMap(inl, langName, lanMap, label);
							}else {
								langName = messageSource.getMessage("label.gvNameSpanish", null,locale);
								lanMap = getLanguageMap(inl, langName, lanMap, label);
							}
						}
					}
					String title =messageSource.getMessage("label.ggTitle", null,currentLocale);
					String addBtn = messageSource.getMessage("label.addBtn", null,currentLocale);
					String name =messageSource.getMessage("label.name", null,currentLocale);
					String ggListTitle =messageSource.getMessage("label.ggListTitle", null,currentLocale);
					
					outlanMap.put("name", name);
					outlanMap.put("title", title);
					outlanMap.put("Add", addBtn);
					outlanMap.put("ggListTitle", ggListTitle);
				}
				if(lsgList != null && lsgList.size() > 0) {
					List<LanguageSpecificGroupDetails> tempList = null;
					for(LanguageSpecificGroupDetails lsgd : lsgList) {
						if(!ggMap.containsKey(lsgd.getGloblgroupId().getId())) {
							ggMap.put(lsgd.getGloblgroupId().getId(), lsgd.getGloblgroupId());
						}
						if(lsMap.containsKey(lsgd.getGloblgroupId().getId())) {
							tempList = lsMap.get(lsgd.getGloblgroupId().getId());
							tempList.add(lsgd);
							lsMap.put(lsgd.getGloblgroupId().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgd);
							lsMap.put(lsgd.getGloblgroupId().getId(), tempList);
						}
					}
					
				}
				ggDto.setLanMap(lanMap);
				ggDto.setOutlanMap(outlanMap);
				ggDto.setLsMap(lsMap);
				ggDto.setGgMap(ggMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ggDto;
	}

	private Map<Long, Map<String, String>> getLanguageMap(InternationalizaionLanguages inl, String langName, 
			Map<Long, Map<String, String>> lanMap, String label) {
		Map<String, String> tempMap = null;
		try {
			if(lanMap.containsKey(inl.getId())) {
				tempMap = lanMap.get(inl.getId());
				tempMap.put("label", label);
				tempMap.put("langName", langName);
				lanMap.put(inl.getId(), tempMap);
			}else {
				tempMap = new HashMap<>();
				tempMap.put("label", label);
				tempMap.put("langName", langName);
				lanMap.put(inl.getId(), tempMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lanMap;
	}

	@Override
	public String saveGlobalGroup(String name, List<String> pvalList, List<Long> lagList, String userName, int order, String type,int noofrows,int noofcolums) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		List<LanguageSpecificGroupDetails> lsgdList = new ArrayList<>();
		try {
			inlagList = groupsDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagList.size() > 0) {
				GlobalGroups gd = new GlobalGroups();
				gd.setName(name);
				gd.setCreatedBy(userName);
				gd.setCreatedOn(new Date());
				gd.setOrderNo(order);
				gd.setType(type);
				gd.setNoofrows(noofrows);
				gd.setNoofcolums(noofcolums);
				
				for(int i=0; i<lagList.size(); i++) {
					LanguageSpecificGroupDetails lsgd = new LanguageSpecificGroupDetails();
					lsgd.setCreatedBy(userName);
					lsgd.setCreatedOn(new Date());
					lsgd.setInlagId(inlagMap.get(lagList.get(i)));
					lsgd.setName(pvalList.get(i));
					lsgdList.add(lsgd);
				}
				if(lsgdList.size() > 0)
					result = groupsDao.saveGlobalValesRecords(gd, lsgdList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<GlobalActivity> getGlobalActivityList() {
		return groupsDao.getGlobalActivityList();
	}

	@Override
	public List<GlobalGroups> getGlobalGroupsList() {
		return groupsDao.getGlobalGroupsList();
	}

	@Override
	public List<GlobalParameter> getGlobalParameterList() {
		return groupsDao.getGlobalParameterList();
	}

	@Override
	public List<GlobalParameter> getGlobalParameterWithGroupId(long id,long actid,Locale currentLocale) {
		List<GlobalParameter> list=null;
        list=groupsDao.getGlobalParameterWithGroupId(id,actid);
		InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
		Map<Long ,LanguageSpecificGlobalParameterDetails>
		      actl=groupsDao.getLanguageSpecificGlobalParameterDetailsList(language);
		for(GlobalParameter add:list) {
			LanguageSpecificGlobalParameterDetails lagval=actl.get(add.getId());
		     add.setParameterName(lagval.getName());
           }
		return list;
	}

	@Override
	public List<GlobalGroups> getGlobalGroupsWithActivityId(long id) {
		return groupsDao.getGlobalGroupsWithActivityId(id);
	}

	@Override
	public List<GlobalParameter> getGlobalParameterWithActivityId(long id) {
		return groupsDao.getGlobalParameterWithActivityId(id);
	}

	@Override
	public List<GlobalGroups> getGlobalGroupsWithParameterBased(List<Long> ids ,Locale currentLocale) {
		List<GlobalGroups> list=null;
		list=groupsDao.getGlobalGroupsWithParameterBased(ids);
		InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
		Map<Long ,LanguageSpecificGroupDetails>
		      actl=groupsDao.getLanguageSpecificGroupDetailsList(language);
		for(GlobalGroups add:list) {
			LanguageSpecificGroupDetails lagval=actl.get(add.getId());
		     add.setName(lagval.getName());
           }
		return list;
	}

	@Override
	public GlobalGroups getGroupRowsAndColumsCount(long id) {
		return groupsDao.getGroupRowsAndColumsCount(id);
	}

	@Override
	public GlobalActivity getGlobalActivityWithId(long id) {
		return groupsDao.getGlobalActivityWithId(id);
	}

	@Override
	public GlobalGroups getGlobalGroupsWithId(long id) {
		return groupsDao.getGlobalGroupsWithId(id);
	}

	@Override
	public GlobalParameter getGlobalParameterWithId(long id) {
		return groupsDao.getGlobalParameterWithId(id);
	}

	@Override
	public boolean saveGlobalGroupDesignActivity(GroupDesignActivity gdaPojo,
			List<GroupDesignActivityDetails> gdadList, List<GroupDesignActivityDetails> gdUpdateList) {
		return groupsDao.saveGlobalGroupDesignActivity(gdaPojo,gdadList,gdUpdateList);
	}

	@Override
	public List<GlobalActivity> getGlobalActivityList(MessageSource messageSource, Locale currentLocale) {
		List<GlobalActivity> list=null;
		                     list=groupsDao.getGlobalActivityList();
		InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
		Map<Long ,LanguageSpecificGlobalActivityDetails>
		                   actl=groupsDao.getLanguageSpecificGlobalActivityDetailsList(language);
		for(GlobalActivity add:list) {
			LanguageSpecificGlobalActivityDetails lagval=actl.get(add.getId());
			add.setName(lagval.getName());
		}
		return list;
	}

	@Override
	public List<GlobalGroups> getGlobalGroupsList(MessageSource messageSource, Locale currentLocale) {
		List<GlobalGroups> list=null;
        list=groupsDao.getGlobalGroupsList();
		InternationalizaionLanguages language =  languageService.getLanguage(currentLocale);
		Map<Long ,LanguageSpecificGroupDetails>
		      actl=groupsDao.getLanguageSpecificGroupDetailsList(language);
		for(GlobalGroups add:list) {
			LanguageSpecificGroupDetails lagval=actl.get(add.getId());
		     add.setName(lagval.getName());
           }
		return list;
	}

	@Override
	public GlobalGroups getGlobalGroupWithName(String value) {
		return groupsDao.getGlobalGroupWithName(value);
	}

	@Override
	public GroupDesignActivityDetails getGroupDesignActivityDetailsWithParamAndActivity(GlobalParameter gppojo,
			GlobalActivity activitypojo) {
		return groupsDao.getGroupDesignActivityDetailsWithParamAndActivity(gppojo,activitypojo);
	}


}
