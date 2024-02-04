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

import com.covideinfo.dao.ConditionDao;
import com.covideinfo.dto.ConditionDto;
import com.covideinfo.model.ConditionParameter;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificCondition;
import com.covideinfo.service.ConditionService;

@Service("subjectWithdrawActivityService")
public class ConditionServiceImpl implements ConditionService  {
	
	@Autowired
	ConditionDao conditionDao;

	@Override
	public ConditionDto getLanguageDetails(MessageSource messageSource,
			List<InternationalizaionLanguages> inLagList, Locale currentLocale,Long langId) {
		
		ConditionDto gvDto = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<Long, ConditionParameter> gvMap = new HashMap<>();
		Map<Long, List<LanguageSpecificCondition>> lsMap = new HashMap<>();
		InternationalizaionLanguages inlag = null;
		try {
			inlag = conditionDao.getInternationalizaionLanguageRecord(langId);
			if(inLagList.size() > 0) {
				for(InternationalizaionLanguages inl : inLagList) {
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
				String title =messageSource.getMessage("label.swaTitle", null,currentLocale);
				String addBtn = messageSource.getMessage("label.addswaBtn", null,currentLocale);
				String name =messageSource.getMessage("label.swaname", null,currentLocale);
				String gvListTitle =messageSource.getMessage("label.swaListTitle", null,currentLocale);
				String drop =messageSource.getMessage("label.drop", null,currentLocale);
				String swastatus =messageSource.getMessage("label.swastatus", null,currentLocale);
				
				outlanMap.put("name", name);
				outlanMap.put("title", title);
				outlanMap.put("Add", addBtn);
				outlanMap.put("swaListTitle", gvListTitle);
				outlanMap.put("drop", drop);
				outlanMap.put("swastatus", swastatus);
				
				List<LanguageSpecificCondition> lsvList = conditionDao.getLanguageSpecificSubjectWithdrawActivityList(); 
				List<LanguageSpecificCondition> tempList = null;
				if(lsvList != null && lsvList.size() > 0) {
					for(LanguageSpecificCondition lsvd : lsvList) {
						if(!gvMap.containsKey(lsvd.getSujectWithdrawActivity().getId())) {
							gvMap.put(lsvd.getSujectWithdrawActivity().getId(), lsvd.getSujectWithdrawActivity());
						}
						if(lsMap.containsKey(lsvd.getSujectWithdrawActivity().getId())) {
							tempList = lsMap.get(lsvd.getSujectWithdrawActivity().getId());
							tempList.add(lsvd);
							lsMap.put(lsvd.getSujectWithdrawActivity().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsvd);
							lsMap.put(lsvd.getSujectWithdrawActivity().getId(), tempList);
						}
					}
				}
				
				gvDto = new ConditionDto();
				gvDto.setLanMap(lanMap);
				gvDto.setOutLanMap(outlanMap);
				gvDto.setSwaMap(gvMap);
				gvDto.setLsMap(lsMap);
				gvDto.setInlag(inlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gvDto;
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
	public String saveSubjectWithdrawActivity(String name, List<String> pvalList, List<Long> lagList, String userName,String dropDown) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		List<LanguageSpecificCondition> lsvdList = new ArrayList<>();
		try {
			inlagList = conditionDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagList.size() > 0) {
				ConditionParameter swa = new ConditionParameter();
				swa.setName(name);
				swa.setDropDown(dropDown);
				swa.setCreatedBy(userName);
				swa.setCreatedOn(new Date());
				for(int i=0; i<lagList.size(); i++) {
					LanguageSpecificCondition lsvd = new LanguageSpecificCondition();
					lsvd.setCreatedBy(userName);
					lsvd.setCreatedOn(new Date());
					lsvd.setInlagId(inlagMap.get(lagList.get(i)));
					lsvd.setName(pvalList.get(i));
					lsvdList.add(lsvd);
				}
				if(lsvdList.size() > 0)
					result = conditionDao.saveSubjectWithdrawActivity(swa, lsvdList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<LanguageSpecificCondition> getLanguageSpecificConditions(Locale currentLocale) {
		// TODO Auto-generated method stub
		return conditionDao.getLanguageSpecificConditions(currentLocale);
	}

	@Override
	public ConditionParameter getConditionParameterWithId(long id) {
		return conditionDao.getConditionParameterWithId(id);
	}

	@Override
	public boolean updateStatus(ConditionParameter cp) {
		return conditionDao.updateStatus(cp);
	}

	@Override
	public ConditionParameter conditionNameCheckExitOrNot(String value) {
		return conditionDao.conditionNameCheckExitOrNot(value);
	}

}
