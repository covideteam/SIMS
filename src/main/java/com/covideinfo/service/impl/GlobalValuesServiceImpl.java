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

import com.covideinfo.dao.GlobalValuesDao;
import com.covideinfo.dao.UserRecordDao;
import com.covideinfo.dto.GlobalValuesDto;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.GlobalValuesService;

@Service
public class GlobalValuesServiceImpl implements GlobalValuesService {
	
	@Autowired
	GlobalValuesDao globalValuesDao;
	
	@Autowired
	UserRecordDao userRecordDao;

	@Override
	public List<InternationalizaionLanguages> getInternationalizaionLanguages() {
		return globalValuesDao.getInternationalizaionLanguages();
	}

	@Override
	public GlobalValuesDto getLanguageDetails(MessageSource messageSource,
			List<InternationalizaionLanguages> inLagList, Locale currentLocale, Long langId) {
		GlobalValuesDto gvDto = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<Long, GlobalValues> gvMap = new HashMap<>();
		Map<Long, List<LanguageSpecificValueDetails>> lsMap = new HashMap<>();
		InternationalizaionLanguages inlag = null;
		try {
			inlag = globalValuesDao.getInternationalizationLanguageRecord(langId);
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
				String title =messageSource.getMessage("label.gvTitle", null,currentLocale);
				String addBtn = messageSource.getMessage("label.addBtn", null,currentLocale);
				String name =messageSource.getMessage("label.name", null,currentLocale);
				String gvListTitle =messageSource.getMessage("label.gvListTitle", null,currentLocale);
				
				outlanMap.put("name", name);
				outlanMap.put("title", title);
				outlanMap.put("Add", addBtn);
				outlanMap.put("gvListTitle", gvListTitle);
				
				List<LanguageSpecificValueDetails> lsvList = globalValuesDao.getLanguageSpecificValueDetailsList(langId); 
				List<LanguageSpecificValueDetails> tempList = null;
				if(lsvList != null && lsvList.size() > 0) {
					for(LanguageSpecificValueDetails lsvd : lsvList) {
						if(!gvMap.containsKey(lsvd.getGlobalValId().getId())) {
							gvMap.put(lsvd.getGlobalValId().getId(), lsvd.getGlobalValId());
						}
						if(lsMap.containsKey(lsvd.getGlobalValId().getId())) {
							tempList = lsMap.get(lsvd.getGlobalValId().getId());
							tempList.add(lsvd);
							lsMap.put(lsvd.getGlobalValId().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsvd);
							lsMap.put(lsvd.getGlobalValId().getId(), tempList);
						}
					}
				}
				
				gvDto = new GlobalValuesDto();
				gvDto.setLanMap(lanMap);
				gvDto.setOutLanMap(outlanMap);
				gvDto.setGvMap(gvMap);
				gvDto.setLsMap(lsMap);
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
	public String saveGlobalValues(String name, List<String> pvalList, List<Long> lagList, Long userId, int orderNo) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		List<LanguageSpecificValueDetails> lsvdList = new ArrayList<>();
	    UserMaster user = userRecordDao.getUserRecord(userId);
		try {
			inlagList = globalValuesDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagList.size() > 0) {
				GlobalValues gv = new GlobalValues();
				gv.setName(name);
				gv.setCreatedBy(user);
				gv.setCreatedOn(new Date());
				gv.setOrderNo(orderNo);
				for(int i=0; i<lagList.size(); i++) {
					LanguageSpecificValueDetails lsvd = new LanguageSpecificValueDetails();
					lsvd.setCreatedBy(user);
					lsvd.setCreatedOn(new Date());
					lsvd.setInlagId(inlagMap.get(lagList.get(i)));
					lsvd.setName(pvalList.get(i));
					lsvdList.add(lsvd);
				}
				if(lsvdList.size() > 0)
					result = globalValuesDao.saveGlobalValesRecords(gv, lsvdList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public GlobalValues getGlobalValuesWithName(String value) {
		return globalValuesDao.getGlobalValuesWithName(value);
	}

}
