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

import com.covideinfo.dao.GlobalActivityDao;
import com.covideinfo.dto.ActvitityDetailsDto;
import com.covideinfo.dto.GlobalActivityDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.service.GlobalActivityService;

@Service("globalActivityService")
public class GlobalActivityServiceImpl implements GlobalActivityService {
	
	@Autowired
	GlobalActivityDao globalActivityDao;

	@Override
	public GlobalActivityDto getGlobalActivityDetails(MessageSource messageSource, Locale currentLocale, Long langId) {
		GlobalActivityDto gaDto = null;
		List<InternationalizaionLanguages> inlList = null;
		List<LanguageSpecificGlobalActivityDetails> lsgaList = null;
		Map<Long, Map<String, String>> lanMap = new HashMap<>();
		Map<String, String> outlanMap = new HashMap<>();
		Map<Long, GlobalActivity> ggMap = new HashMap<>();
		Map<Long, List<Long>> gauserIdMap = new HashMap<>();
		Map<Long, List<LanguageSpecificGlobalActivityDetails>> lsMap = new HashMap<>();
		Map<Long, String> roleMap = new HashMap<>();
		List<RoleMaster> roleList = null;
		InternationalizaionLanguages inlag = null;
		try {
			gaDto = globalActivityDao.getGlobalActivityDetails(langId);
			if(gaDto != null) {
				inlList = gaDto.getInlList();
				lsgaList = gaDto.getLsgList();
				roleList = gaDto.getRolesList();
				inlag = gaDto.getInlag();
				if(roleList != null && roleList.size() > 0) {
					for(RoleMaster role : roleList) {
						roleMap.put(role.getId(), role.getRole());
					}
				}
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
					String title =messageSource.getMessage("label.gaTitle", null,currentLocale);
					String addBtn = messageSource.getMessage("label.addBtn", null,currentLocale);
					String name =messageSource.getMessage("label.name", null,currentLocale);
					String gaListTitle =messageSource.getMessage("label.gaListTitle", null,currentLocale);
					
					outlanMap.put("name", name);
					outlanMap.put("title", title);
					outlanMap.put("Add", addBtn);
					outlanMap.put("gaListTitle", gaListTitle);
				}
				if(lsgaList != null && lsgaList.size() > 0) {
					List<LanguageSpecificGlobalActivityDetails> tempList = null;
					for(LanguageSpecificGlobalActivityDetails lsgad : lsgaList) {
						String roleIds = lsgad.getGlobalActivity().getRoleIds();
						List<Long> roleIdList = null;
						if(roleIds != null && !roleIds.equals("")) {
							String[] tempArr = roleIds.split("\\,");
							roleIdList = new ArrayList<>();
							if(tempArr.length > 0) {
								for(String st : tempArr) {
									if(st != null && !st.equals(""))
										roleIdList.add(Long.parseLong(st));
									else roleIdList.add(1L);
								}
							}
						}
						gauserIdMap.put(lsgad.getGlobalActivity().getId(), roleIdList);
						if(!ggMap.containsKey(lsgad.getGlobalActivity().getId())) {
							ggMap.put(lsgad.getGlobalActivity().getId(), lsgad.getGlobalActivity());
						}
						if(lsMap.containsKey(lsgad.getGlobalActivity().getId())) {
							tempList = lsMap.get(lsgad.getGlobalActivity().getId());
							tempList.add(lsgad);
							lsMap.put(lsgad.getGlobalActivity().getId(), tempList);
						}else {
							tempList = new ArrayList<>();
							tempList.add(lsgad);
							lsMap.put(lsgad.getGlobalActivity().getId(), tempList);
						}
					}
					
				}
				gaDto.setLanMap(lanMap);
				gaDto.setOutlanMap(outlanMap);
				gaDto.setLsMap(lsMap);
				gaDto.setGgMap(ggMap);
				gaDto.setRoleMap(roleMap);
				gaDto.setGauserIdMap(gauserIdMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gaDto;
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
	public List<GlobalActivity> getGlobalActivity(String code) {
		return globalActivityDao.getGlobalActivity(code);
	}
	
	@Override
	public List<GlobalActivity> getGlobalActivities(List<String> codes) {
		return globalActivityDao.getGlobalActivities(codes);
	}
	
	@Override
	public List<GlobalActivity> getStudyDesignActivities() {
		return globalActivityDao.getStudyDesignActivities();
	}
	 
	@Override
	public String saveGlobalActivityDetails(String name, List<String> pvalList, List<Long> lagList, String userName, 
			String allowMultiple, List<Long> roleIdsList, 
			String userRole, Long groupId, String actCode, int orderNo, String headdingVal,String showInPDFVal,String subjectsInput,
			String eligibleForNextProcessVal) {
		String result ="Failed";
		List<InternationalizaionLanguages> inlagList = null;
		Map<Long, InternationalizaionLanguages> inlagMap = new HashMap<>();
		List<LanguageSpecificGlobalActivityDetails> lsadList = new ArrayList<>();
		try {
			inlagList = globalActivityDao.getInternationalizaionLanguages();
			if(inlagList != null && inlagList.size() > 0) {
				for(InternationalizaionLanguages inl : inlagList) {
					inlagMap.put(inl.getId(), inl);
				}
			}
			if(lagList.size() > 0) {
				GlobalActivity ga = new GlobalActivity();
				ga.setName(name);
				ga.setCreatedBy(userName);
				ga.setCreatedOn(new Date());
				ga.setAllowMultiple(allowMultiple);
				ga.setOrderNo(orderNo);
				ga.setGetUrl("0");
				ga.setSaveUrl("0");
				ga.setRejectUrl("0");
				if(headdingVal.equals("Yes"))
					ga.setHeadding(true);
				else ga.setHeadding(false);
				
				if(showInPDFVal.equals("Yes"))
					ga.setShowInPDF(true);
				else ga.setShowInPDF(false);
				
				if(subjectsInput.equals("Yes"))
					ga.setSubjectsInput(true);
				else ga.setSubjectsInput(false);
				
				if(eligibleForNextProcessVal.equals("Yes"))
					ga.setEligibleForNextProcess(true);
				else ga.setEligibleForNextProcess(false);
				
				
				String roleIds = "";
				for(Long no : roleIdsList) {
					if(roleIds.equals(""))
						roleIds = no+"";
					else roleIds = roleIds+","+no;
					
				}
				ga.setRoleIds(roleIds);
				String actCodeVal ="";
				List<Integer> intList = new ArrayList<>();
				intList.add(3);
				intList.add(5);
				intList.add(1);
				intList.add(4);
				intList.add(6);
				intList.add(2);
				if(!userRole.equals("SUPERADMIN")) {
					if(name.length() > 6) {
						for(Integer no : intList)
							if(actCode.equals(""))
								actCodeVal = name.charAt(no)+"";
							else actCodeVal = actCodeVal +name.charAt(no)+"";
					}else {
						actCodeVal = name;
					}
				}else {
					if(actCode != null)
						actCodeVal = actCode;
				}
				ga.setActivityCode(actCodeVal);
				for(int i=0; i<lagList.size(); i++) {
					LanguageSpecificGlobalActivityDetails lsad = new LanguageSpecificGlobalActivityDetails();
					lsad.setCreatedBy(userName);
					lsad.setCreatedOn(new Date());
					lsad.setInlagId(inlagMap.get(lagList.get(i)));
					lsad.setOrderNo(ga.getOrderNo());
					lsad.setName(pvalList.get(i));
					lsadList.add(lsad);
				}
				if(lsadList.size() > 0)
					result = globalActivityDao.saveGlobalValesRecords(ga, lsadList, groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public GlobalActivity getGlobalActivityRecord(String actName) {
		return globalActivityDao.getGlobalActivityRecord(actName);
	}

	@Override
	public List<ActvitityDetailsDto> getOtherActivities(Long langId) {
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		List<ActvitityDetailsDto> actDtoList = new ArrayList<>();
		try {
			lspgaList = globalActivityDao.getOtherActivities(langId);
			if(lspgaList != null && lspgaList.size() > 0) {
				for(LanguageSpecificGlobalActivityDetails lsgad : lspgaList) {
					ActvitityDetailsDto adDto = new ActvitityDetailsDto();
					adDto.setActivityName(lsgad.getName());
					adDto.setActivityId(lsgad.getGlobalActivity().getId());
					actDtoList.add(adDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actDtoList;
	}

	@Override
	public List<ActvitityDetailsDto> getDefaultActivities(Long langId) {
		List<LanguageSpecificGlobalActivityDetails> lspgaList = null;
		List<ActvitityDetailsDto> actDtoList = new ArrayList<>();
		try {
			lspgaList = globalActivityDao.getDefaultActivities(langId);
			if(lspgaList != null && lspgaList.size() > 0) {
				for(LanguageSpecificGlobalActivityDetails lsgad : lspgaList) {
					if(lsgad.getGlobalActivity() !=null && (lsgad.getGlobalActivity().getGetUrl() == null || lsgad.getGlobalActivity().getGetUrl().length() < 5)) {
						ActvitityDetailsDto adDto = new ActvitityDetailsDto();
						adDto.setActivityName(lsgad.getName());
						adDto.setActivityId(lsgad.getGlobalActivity().getId());
						actDtoList.add(adDto);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actDtoList;
	}

}
