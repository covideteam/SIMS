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

import com.covideinfo.dao.PrametesDao;
import com.covideinfo.dto.GlobalParameterDto;
import com.covideinfo.dto.ParameterDto;
import com.covideinfo.model.ControlType;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.ParameterService;

@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {
	
	@Autowired
	PrametesDao parametersDao;

	@Override
	public GlobalParameterDto getPrameterDetails(MessageSource messageSource, Locale currentLocale,Long langId) {
		GlobalParameterDto gpDto = null;
		List<InternationalizaionLanguages> inlList = null;
		List<LanguageSpecificGlobalParameterDetails> lsgpList = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<Long, GlobalParameter> ggMap = new HashMap<>();
		Map<Long, List<LanguageSpecificGlobalParameterDetails>> lsMap = new HashMap<>();
		List<ParameterControlTypeValues> pctvList = null;
		Map<Long, Map<Long, List<GlobalValues>>> pctvMap = new HashMap<>();
		InternationalizaionLanguages inlag = null;
		try {
			gpDto = parametersDao.getGlobalParameterDtoDetails(langId);
			if(gpDto != null) {
				inlList = gpDto.getInlList();
				lsgpList = gpDto.getLspdList();
				pctvList = gpDto.getPctvList();
				inlag = gpDto.getInlag();
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
					String title =messageSource.getMessage("label.gpTitle", null,currentLocale);
					String addBtn = messageSource.getMessage("label.addBtn", null,currentLocale);
					String name =messageSource.getMessage("label.name", null,currentLocale);
					String ggListTitle =messageSource.getMessage("label.gpListTitle", null,currentLocale);
					
					
					outlanMap.put("name", name);
					outlanMap.put("title", title);
					outlanMap.put("Add", addBtn);
					outlanMap.put("gpListTitle", ggListTitle);
				}
				if(lsgpList != null && lsgpList.size() > 0) {
					List<LanguageSpecificGlobalParameterDetails> tempList = null;
					for(LanguageSpecificGlobalParameterDetails lsgpd : lsgpList) {
						if(!ggMap.containsKey(lsgpd.getParameterId().getId())) {
							ggMap.put(lsgpd.getParameterId().getId(), lsgpd.getParameterId());
						}
						if(lsMap.containsKey(lsgpd.getParameterId().getId())) {
							tempList = lsMap.get(lsgpd.getParameterId().getId());
							tempList.add(lsgpd);
							lsMap.put(lsgpd.getParameterId().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgpd);
							lsMap.put(lsgpd.getParameterId().getId(), tempList);
						}
					}
					
				}
				if(pctvList != null && pctvList.size() > 0) {
					List<GlobalValues> gvList = null;
					Map<Long, List<GlobalValues>> tMap = null;
					for(ParameterControlTypeValues pctv : pctvList) {
					    if(pctvMap.containsKey(pctv.getGlobalParameter().getId())) {
					    	if(tMap.containsKey(pctv.getControlType().getId())) {
					    	     gvList = tMap.get(pctv.getControlType().getId());
					    	     gvList.add(pctv.getGlobalValues());
					    	     tMap.put(pctv.getControlType().getId(), gvList);
					    	     pctvMap.put(pctv.getGlobalParameter().getId(), tMap);
					    	}else {
					    		 gvList = new ArrayList<>();
					    	     gvList.add(pctv.getGlobalValues());
					    	     tMap.put(pctv.getControlType().getId(), gvList);
					    	     pctvMap.put(pctv.getGlobalParameter().getId(), tMap);
					    	}
					    }else {
					    	gvList = new ArrayList<>();
					    	tMap = new HashMap<>();
					    	gvList.add(pctv.getGlobalValues());
					    	tMap.put(pctv.getControlType().getId(), gvList);
					    	pctvMap.put(pctv.getGlobalParameter().getId(), tMap);
					    }
					}
				}
				gpDto.setLanMap(lanMap);
				gpDto.setOutlanMap(outlanMap);
				gpDto.setLsMap(lsMap);
				gpDto.setGgMap(ggMap);
				gpDto.setPctvList(pctvList);
				gpDto.setPctvMap(pctvMap);
				gpDto.setInlag(inlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDto;
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
	public String saveGlobalParameterDetails(String name, List<String> pvalList, List<Long> lagList, Long groupId, String controlId, 
			List<Long> valIds, String bindVal,Long activityId, String userName, int order,String controlName, Long unitsId, List<String> phaseList, String paramMandatoryVal) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		List<LanguageSpecificGlobalParameterDetails> lsgpdList = new ArrayList<>();
		List<ParameterControlTypeValues> pctvList = new ArrayList<>();
		try {
			inlagList = parametersDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagList.size() > 0) {
				GlobalGroups group = null;
				ControlType ctPojo = null;
				List<GlobalValues> gvList = null;
				GlobalActivity gaPojo = null;
				UnitsMaster unitsPojo = null;
				GlobalParameterDto gpDto = parametersDao.getGlobalParameterDetails(groupId, controlId, valIds, activityId, bindVal, unitsId);
				if(gpDto != null) {
					group = gpDto.getGroup();
					ctPojo = gpDto.getCtPojo();
					gvList = gpDto.getGvList();
					gaPojo = gpDto.getGaPojo();
					unitsPojo = gpDto.getUnitsPojo();
				}
				String paramPhases = "";
				if(phaseList.size() > 0) {
					for(String st : phaseList) {
						if(paramPhases.equals(""))
							paramPhases = st;
						else paramPhases = paramPhases +","+st;
					}
				}
				GlobalParameter gp = new GlobalParameter();
				gp.setParameterName(name);
				gp.setCreatedBy(userName);
				gp.setCreatedOn(new Date());
				gp.setGroups(group);
				gp.setContentType(ctPojo);
				if(bindVal.equals("Yes")) {
					gp.setBindActivity(true);
				}else gp.setBindActivity(false);
				gp.setActivity(gaPojo);
				gp.setOrderNo(order);
				gp.setControlName(controlName);
				gp.setUnitsId(unitsPojo);
				gp.setParameterPhase(paramPhases);
				if(paramMandatoryVal.equals("Yes"))
					gp.setMandatory(true);
				else gp.setMandatory(false);
				
				for(int i=0; i<lagList.size(); i++) {
					LanguageSpecificGlobalParameterDetails lsgpd = new LanguageSpecificGlobalParameterDetails();
					lsgpd.setCreatedBy(userName);
					lsgpd.setCreatedOn(new Date());
					lsgpd.setInlagId(inlagMap.get(lagList.get(i)));
					lsgpd.setName(pvalList.get(i));
					lsgpd.setOrderNo(gp.getOrderNo());
					lsgpdList.add(lsgpd);
				}
				
				if(gvList != null && gvList.size() > 0) {
					for(GlobalValues gv : gvList) {
						ParameterControlTypeValues pctv = new ParameterControlTypeValues();
						pctv.setControlType(ctPojo);
						pctv.setCreatedBy(userName);
						pctv.setCreatedOn(new Date());
						pctv.setGlobalValues(gv);
						pctvList.add(pctv);
					}
				}
				
				if(lsgpdList.size() > 0)
					result = parametersDao.saveGlobalParameterRecords(gp, lsgpdList, pctvList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Gets language specific activity parameters
	 * @param activityId - activity id
	 * @param langId - language id
	 *
	 * @List<ParameterDto> - list of language specific activity parameters
	 */
	@Override
	public  List<ParameterDto> getActivityDefaultParameters(Long activityId,Long langId){
		return parametersDao.getActivityDefaultParameters(activityId, langId);
	}

	/*
	 * Gets language specific multiple activity parameters
	 * @param activityIds - activity id
	 * @param langId - language id
	 *
	 * @List<ParameterDto> - list of language specific activity parameters
	 */
	@Override
	public  List<ParameterDto> getActivityDefaultParameters(Long[] activityIds,Long langId){
		return parametersDao.getActivityDefaultParameters(activityIds, langId);
	}

	@Override
	public GlobalParameter parameterNameCheckExitOrNot(String value) {
		return parametersDao.parameterNameCheckExitOrNot(value);
	}
}

