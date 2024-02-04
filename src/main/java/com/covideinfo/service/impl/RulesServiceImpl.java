package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.RulesDao;
import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.ConditionRulesDto;
import com.covideinfo.dto.DependentActivitiesDto;
import com.covideinfo.dto.DependentRulesDto;
import com.covideinfo.dto.ParameterDetailDto;
import com.covideinfo.dto.RulesControlDto;
import com.covideinfo.dto.RulesDetails;
import com.covideinfo.dto.RulesDetailsDto;
import com.covideinfo.dto.RulesDto;
import com.covideinfo.dto.RulesGlobalParameterDto;
import com.covideinfo.dto.RulesValuesDto;
import com.covideinfo.dto.StudyActivityRulesDetailsDto;
import com.covideinfo.dto.ValidationRulesDto;
import com.covideinfo.dto.ValuesDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityRuleMessages;
import com.covideinfo.model.StudyActivityRules;
import com.covideinfo.service.RulesService;

@Service("rulesService")
public class RulesServiceImpl implements RulesService {
	
	@Autowired
	RulesDao rulesDao;
	
	@Override
	public RulesDetailsDto getRulesDetailsDtoDetails(Long langId) {
		return rulesDao.getRulesDetailsDtoDetails(langId);
	}

	@Override
	public List<RulesGlobalParameterDto> getActivityParametersList(Long activityId, Long langId) {
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		List<RulesGlobalParameterDto> rgpDtoList = new ArrayList<>();
		try {
			lspgpdList = rulesDao.getActivityParametersList(activityId, langId);
			if(lspgpdList != null && lspgpdList.size() > 0) {
				for(LanguageSpecificGlobalParameterDetails lsgpd : lspgpdList) {
					RulesGlobalParameterDto rgpdto = new RulesGlobalParameterDto();
					RulesControlDto rcDto = new RulesControlDto();
					rcDto.setControlType(lsgpd.getParameterId().getContentType().getCode());
					rcDto.setControlTypeId(lsgpd.getParameterId().getContentType().getId());
					rgpdto.setControlType(rcDto);
					rgpdto.setLspParameterId(lsgpd.getId());
					rgpdto.setParameterId(lsgpd.getParameterId().getId());
					rgpdto.setParameterName(lsgpd.getName());
					rgpDtoList.add(rgpdto);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rgpDtoList;
	}

	@Override
	public List<ValuesDto> getValuesDetails(Long parameterId, Locale currentLocale, Long controlId) {
		List<ValuesDto> valDtoList = new ArrayList<>();
		RulesValuesDto rvDto = null;
		LanguageSpecificGlobalParameterDetails gp = null;
		List<LanguageSpecificValueDetails> lsvdList = null;
		try {
			rvDto = rulesDao.getRulesValuesDtoDetails(parameterId, currentLocale, controlId);
			if(rvDto != null) {
				lsvdList = rvDto.getLspgvList();
				gp = rvDto.getGp();
				if(lsvdList != null) {
					for(LanguageSpecificValueDetails lsvd : lsvdList) {
						ValuesDto vd = new ValuesDto();
						vd.setParameterId(gp.getParameterId().getId());
						vd.setLanspecificvalueId(lsvd.getId());
						vd.setValueName(lsvd.getName());
						valDtoList.add(vd);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valDtoList;
	}

	@Override
	public String saveActivityRules(Long sourceActivity, String ruleType, String validationCon, Long sourcePramId,
			String conditionVal, Long destActivity, String applyRuleFor, String depconditionVal, Long destRuleParam,
			String paramAction, List<Long> multiParam, String valueOne, String valueTwo, List<String> ruleMsgList,
			List<Long> lagList, Long checkedLspId, String userName, Long depParameter, Long checkedLspId2,
			Long selectboxVal, Long depcheckedLspId, String depvalueOne, String deparamconditionVal, String tbCondition,
			Long depselectboxVal, List<Long> destMultList, Long depcheckedLspId2) {
		String result = "Failed";
		StudyActivityRules sarule = null;
		RulesDto rulesDto = null;
		Map<Long, InternationalizaionLanguages> inlMap = new HashMap<>();
		List<InternationalizaionLanguages> lanList = null;
		GlobalValues gv = null;
		Long gvId = 0L;
		try {
			if(ruleType != null && !ruleType.equals("")) {
				rulesDto = rulesDao.getRulesDtoDetails(sourceActivity, sourcePramId, lagList);
				if(rulesDto != null) {
					lanList = rulesDto.getInlagList();
					if(lanList != null && lanList.size() > 0) {
						for(InternationalizaionLanguages inlag : lanList) {
							inlMap.put(inlag.getId(), inlag);
						}
					}
					if(ruleType.equals("req")) {
						sarule = new StudyActivityRules();
						sarule.setCreatedBy(userName);
						sarule.setCreatedOn(new Date());
						sarule.setRuleType("Validation");
						sarule.setCondition(validationCon);
						sarule.setSourceActivity(rulesDto.getSourceActivity());
						sarule.setSourceParameter(rulesDto.getSourceParameter());
						
						List<StudyActivityRuleMessages> sarmList = new ArrayList<>();
						if(ruleMsgList != null) {
							for(int i=0; i<ruleMsgList.size(); i++) {
								StudyActivityRuleMessages sarm = new StudyActivityRuleMessages();
								sarm.setCreatedBy(userName);
								sarm.setCreatedOn(new Date());
								sarm.setLang(inlMap.get(lagList.get(i)));
								sarm.setRuleMessage(ruleMsgList.get(i));
								sarmList.add(sarm);
								
							}
						}
						boolean flag = rulesDao.saveRulesData(sarule, sarmList);
						if(flag)
							result ="success";
					
				}else if(ruleType.equals("con")) {
					rulesDto = rulesDao.getRulesDtoDetails(sourceActivity, sourcePramId, lagList);
					if(rulesDto != null) {
						sarule = new StudyActivityRules();
						sarule.setCreatedBy(userName);
						sarule.setCreatedOn(new Date());
						sarule.setRuleType("Condition");
						sarule.setCondition(conditionVal);
						sarule.setSourceActivity(rulesDto.getSourceActivity());
						sarule.setSourceParameter(rulesDto.getSourceParameter());
						if(!valueOne.equals("0"))
							sarule.setConFirstVal(valueOne);
						if(!valueTwo.equals("0"))
							sarule.setConSecondVal(valueTwo);
						
						if(checkedLspId != null && checkedLspId != 0)
							gvId = checkedLspId;
						if(checkedLspId2 != null && checkedLspId2 != 0)
							gvId = checkedLspId2;
						if(selectboxVal != null && selectboxVal != 0)
							gvId = selectboxVal;
						if(gvId != null && gvId != 0)
							gv = rulesDao.getglobalValueRecord(gvId);
						sarule.setGlobalValId(gv);
						List<StudyActivityRuleMessages> sarmList = new ArrayList<>();
						if(ruleMsgList != null) {
							for(int i=0; i<ruleMsgList.size(); i++) {
								StudyActivityRuleMessages sarm = new StudyActivityRuleMessages();
								sarm.setCreatedBy(userName);
								sarm.setCreatedOn(new Date());
								sarm.setLang(inlMap.get(lagList.get(i)));
								sarm.setRuleMessage(ruleMsgList.get(i));
								sarmList.add(sarm);
								
							}
						}
//						long conditionNo  = rulesDao.saveRulesConditionData(sarule);
						boolean flag = rulesDao.saveRulesData(sarule, sarmList);
						if(flag)
							result = "success";
					}
				}else if(ruleType.equals("DepAct")) {
					rulesDto = rulesDao.getRulesDtoForDepedentActivities(sourceActivity, destMultList);
					if(rulesDto != null) {
						sarule = new StudyActivityRules();
						sarule.setCreatedBy(userName);
						sarule.setCreatedOn(new Date());
						sarule.setRuleType("DependentActivities");
						sarule.setSourceActivity(rulesDto.getSourceActivity());
						String destIds = "";
						if(destMultList.size() > 0) {
							for(Long id : destMultList) {
								if(destIds.equals(""))
									destIds = id+"";
								else destIds = destIds+","+id;
							}
						}
						sarule.setDependentActivities(destIds);
						long saruleNo = rulesDao.saveRulesConditionData(sarule);
						if(saruleNo > 0)
							result = "success";
					}
				}else if(ruleType.equals("Dep")) {
					rulesDto = rulesDao.getRulesDtoDetailsForDependentActivities(sourceActivity, sourcePramId, lagList, depParameter, multiParam, destActivity, destRuleParam);
					if(rulesDto != null) {
						List<GlobalParameter> gpList = rulesDto.getGpList();
						Map<Long, GlobalParameter> gpMap = new HashMap<>();
						if(gpList != null && gpList.size() > 0) {
							for(GlobalParameter gp : gpList) {
								gpMap.put(gp.getId(), gp);
							}
						}
						sarule = new StudyActivityRules();
						sarule.setCreatedBy(userName);
						sarule.setCreatedOn(new Date());
						sarule.setRuleType("Dependent");
						sarule.setDependentapplyFor(applyRuleFor);
						sarule.setSourceActivity(rulesDto.getSourceActivity());
						if(applyRuleFor.equals("sameAct")) {
							GlobalParameter gp = gpMap.get(sourcePramId);
							sarule.setSourceParameter(gp);
							String controlType = gp.getContentType().getCode();
							if(controlType.equals("RB") || controlType.equals("CB") || controlType.equals("SB")) {
								GlobalValues gvPojo = null;
								if(controlType.equals("RB"))
									gvPojo = rulesDao.getglobalValueRecord(checkedLspId);
								if(controlType.equals("CB"))
									gvPojo = rulesDao.getglobalValueRecord(checkedLspId2);
								if(controlType.equals("SB"))
									gvPojo = rulesDao.getglobalValueRecord(selectboxVal);
								sarule.setGlobalValId(gvPojo);
								sarule.setCondition(depconditionVal);
								sarule.setParameterAction(paramAction);
								if(paramAction != null && !paramAction.equals("")) {
									if(paramAction.equals("setvalue")) {
										GlobalParameter gpPojo2 = gpMap.get(depParameter);
										String ctType = gpPojo2.getContentType().getCode();
										sarule.setDestParameter(gpPojo2);
										if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
											if(ctType.equals("RB") || ctType.equals("CB"))
												sarule.setDestparameterGlobalLspId(depcheckedLspId);
											else 
												sarule.setDestparameterGlobalLspId(depselectboxVal);
										}else
											sarule.setDeptbvalue(depvalueOne);
									}else {
										String multParamIds = "";
										if(multiParam != null && multiParam.size() > 0) {
											for(Long pid : multiParam) {
												if(multParamIds.equals(""))
													multParamIds = pid+"";
												else multParamIds = multParamIds +","+pid;
											}
										}
										sarule.setMultiParam(multParamIds);
									}
								}
							}else {
								if(deparamconditionVal != null && !deparamconditionVal.equals("") && !deparamconditionVal.equals("0")) {
									if(deparamconditionVal.equals("ge") || deparamconditionVal.equals("le") || deparamconditionVal.equals("ltAndgt") || deparamconditionVal.equals("leAndge")) {
										sarule.setCondition(deparamconditionVal);
										sarule.setFromRange(valueOne);
										sarule.setToRange(valueTwo);
										sarule.setTbCondition(tbCondition);
										sarule.setParameterAction(paramAction);
										if(paramAction != null && !paramAction.equals("")) {
											if(paramAction.equals("setvalue")) {
												GlobalParameter gpPojo2 = gpMap.get(depParameter);
												String ctType = gpPojo2.getContentType().getCode();
												sarule.setDestParameter(gpPojo2);
												if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
													if(ctType.equals("RB") || ctType.equals("CB"))
														sarule.setDestparameterGlobalLspId(depcheckedLspId);
													else 
														sarule.setDestparameterGlobalLspId(depselectboxVal);
												}else
													sarule.setDeptbvalue(depvalueOne);
											}else {
												String multParamIds = "";
												if(multiParam != null && multiParam.size() > 0) {
													for(Long pid : multiParam) {
														if(multParamIds.equals(""))
															multParamIds = pid+"";
														else multParamIds = multParamIds +","+pid;
													}
												}
												sarule.setMultiParam(multParamIds);
											}
										}
									}else {
										sarule.setFromRange(valueOne);
										sarule.setDestParamCodntiion(depconditionVal);
										sarule.setParameterAction(paramAction);
										if(paramAction != null && !paramAction.equals("")) {
											if(paramAction.equals("setvalue")) {
												GlobalParameter gpPojo2 = gpMap.get(depParameter);
												String ctType = gpPojo2.getContentType().getCode();
												sarule.setDestParameter(gpPojo2);
												if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
													if(ctType.equals("RB") || ctType.equals("CB"))
														sarule.setDestparameterGlobalLspId(depcheckedLspId);
													else 
														sarule.setDestparameterGlobalLspId(depselectboxVal);
												}else
													sarule.setDeptbvalue(depvalueOne);
											}else {
												String multParamIds = "";
												if(multiParam != null && multiParam.size() > 0) {
													for(Long pid : multiParam) {
														if(multParamIds.equals(""))
															multParamIds = pid+"";
														else multParamIds = multParamIds +","+pid;
													}
												}
												sarule.setMultiParam(multParamIds);
											}
										}
									}
								}
							}
							long saruleNo = rulesDao.saveRulesConditionData(sarule);
							if(saruleNo > 0)
								result = "success";
						}else if(applyRuleFor.equals("differentAct")) {
							sarule.setDestinationActivity(rulesDto.getDestActivity());
							GlobalParameter gp = gpMap.get(destRuleParam);
							sarule.setSourceParameter(gp);
							String controlType = gp.getContentType().getCode();
							if(controlType.equals("RB") || controlType.equals("CB") || controlType.equals("SB")) {
								GlobalValues gvPojo = null;
								if(controlType.equals("RB"))
									gvPojo = rulesDao.getglobalValueRecord(checkedLspId);
								if(controlType.equals("CB"))
									gvPojo = rulesDao.getglobalValueRecord(checkedLspId2);
								if(controlType.equals("SB"))
									gvPojo = rulesDao.getglobalValueRecord(selectboxVal);
								sarule.setGlobalValId(gvPojo);
								sarule.setCondition(depconditionVal);
								sarule.setParameterAction(paramAction);
								if(paramAction != null && !paramAction.equals("")) {
									if(paramAction.equals("setvalue")) {
										GlobalParameter gpPojo2 = gpMap.get(depParameter);
										String ctType = gpPojo2.getContentType().getCode();
										sarule.setDestParameter(gpPojo2);
										if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
											if(ctType.equals("RB") || ctType.equals("CB"))
												sarule.setDestparameterGlobalLspId(depcheckedLspId);
											else 
												sarule.setDestparameterGlobalLspId(depselectboxVal);
										}else
											sarule.setDeptbvalue(depvalueOne);
									}else {
										String multParamIds = "";
										if(multiParam != null && multiParam.size() > 0) {
											for(Long pid : multiParam) {
												if(multParamIds.equals(""))
													multParamIds = pid+"";
												else multParamIds = multParamIds +","+pid;
											}
										}
										sarule.setMultiParam(multParamIds);
									}
								}
							}else {
								if(deparamconditionVal != null && !deparamconditionVal.equals("") && !deparamconditionVal.equals("0")) {
									if(deparamconditionVal.equals("ge") || deparamconditionVal.equals("le") || deparamconditionVal.equals("ltAndgt") || deparamconditionVal.equals("leAndge")) {
										sarule.setCondition(deparamconditionVal);
										sarule.setFromRange(valueOne);
										sarule.setToRange(valueTwo);
										sarule.setTbCondition(tbCondition);
										sarule.setParameterAction(paramAction);
										if(paramAction != null && !paramAction.equals("")) {
											if(paramAction.equals("setvalue")) {
												GlobalParameter gpPojo2 = gpMap.get(depParameter);
												String ctType = gpPojo2.getContentType().getCode();
												sarule.setDestParameter(gpPojo2);
												if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
													if(ctType.equals("RB") || ctType.equals("CB"))
														sarule.setDestparameterGlobalLspId(depcheckedLspId);
													else 
														sarule.setDestparameterGlobalLspId(depselectboxVal);
												}else
													sarule.setDeptbvalue(depvalueOne);
											}else {
												String multParamIds = "";
												if(multiParam != null && multiParam.size() > 0) {
													for(Long pid : multiParam) {
														if(multParamIds.equals(""))
															multParamIds = pid+"";
														else multParamIds = multParamIds +","+pid;
													}
												}
												sarule.setMultiParam(multParamIds);
											}
										}
									}else {
										sarule.setFromRange(valueOne);
										sarule.setDestParamCodntiion(depconditionVal);
										sarule.setParameterAction(paramAction);
										if(paramAction != null && !paramAction.equals("")) {
											if(paramAction.equals("setvalue")) {
												GlobalParameter gpPojo2 = gpMap.get(depParameter);
												String ctType = gpPojo2.getContentType().getCode();
												sarule.setDestParameter(gpPojo2);
												if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
													if(ctType.equals("RB") || ctType.equals("CB"))
														sarule.setDestparameterGlobalLspId(depcheckedLspId);
													else 
														sarule.setDestparameterGlobalLspId(depselectboxVal);
												}else
													sarule.setDeptbvalue(depvalueOne);
											}else {
												String multParamIds = "";
												if(multiParam != null && multiParam.size() > 0) {
													for(Long pid : multiParam) {
														if(multParamIds.equals(""))
															multParamIds = pid+"";
														else multParamIds = multParamIds +","+pid;
													}
												}
												sarule.setMultiParam(multParamIds);
											}
										}
									}
								}
							} 
							long saruleNo = rulesDao.saveRulesConditionData(sarule);
							if(saruleNo > 0)
								result = "success";
						}
					}
				}
			  }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public RulesDetails getRulesDetails(Long activityId, Long langId) {
		RulesDetails rules = null;
		StudyActivityRulesDetailsDto sardDto = null;
		List<StudyActivityRules> stdactRList = null;
		List<StudyActivityRuleMessages> stdactRMList = null;
		Map<Long, StudyActivityRuleMessages> sarmMap = new HashMap<>();
		List<ValidationRulesDto> valRulesList = new ArrayList<>();
		List<ConditionRulesDto> crdtoList = new ArrayList<>();
		List<DependentRulesDto> dependentRulesList = new ArrayList<>();
		List<DependentActivitiesDto> dependentActList = new ArrayList<>();
		try {
			sardDto = rulesDao.getStudyActivityRulesDetailsDto(activityId,langId);
			if(sardDto != null) {
				stdactRList = sardDto.getStdactRList();
				stdactRMList = sardDto.getStdactRMList();
				if(stdactRMList != null && stdactRMList.size() > 0) {
					for(StudyActivityRuleMessages sarm : stdactRMList) {
						sarmMap.put(sarm.getRule().getId(), sarm);
					}
				}
				if(stdactRList != null && stdactRList.size() > 0) {
					for(StudyActivityRules sar : stdactRList) {
						if(sar.getRuleType().equals("Validation")) {
							ValidationRulesDto valrDto = new ValidationRulesDto();
							valrDto.setRuleType("Validation");
							valrDto.setActivityId(sar.getSourceActivity().getId());
							valrDto.setActivityName(sar.getSourceActivity().getName());
							valrDto.setCondition(sar.getCondition());
							StudyActivityRuleMessages srm = sarmMap.get(sar.getId());
							String message ="";
							if(srm != null) {
								message = srm.getRuleMessage();
							}
							valrDto.setMessage(message);
							valrDto.setSourceParamId(sar.getSourceParameter().getId());
							valrDto.setSourceParamName(sar.getSourceParameter().getParameterName());
							valRulesList.add(valrDto);
						}else if(sar.getRuleType().equals("Condition")) {
							ConditionRulesDto crdto = new ConditionRulesDto();
							crdto.setRuleType("Condition");
							crdto.setCondition(sar.getCondition());
							crdto.setFirstValue(sar.getConFirstVal());
							crdto.setSecondValue(sar.getConSecondVal());
							crdto.setSourceActivityId(sar.getSourceActivity().getId());
							crdto.setSourceActivityName(sar.getSourceActivity().getName());
							crdto.setSourceParameterId(sar.getSourceParameter().getId());
							crdto.setSourceParameterName(sar.getSourceParameter().getParameterName());
							LanguageSpecificValueDetails lsgv = null;
							if(sar.getGlobalValId() != null)
								lsgv = rulesDao.getlsgvdetails(sar.getGlobalValId().getId(), langId);
							if(lsgv != null) {
								crdto.setLsgvName(lsgv.getName());
								crdto.setGlobalValueId(lsgv.getGlobalValId().getId());
							}
							StudyActivityRuleMessages srm = sarmMap.get(sar.getId());
							if(srm != null) {
								crdto.setLsMessage(srm.getRuleMessage());
							}
							crdtoList.add(crdto);
						}else if(sar.getRuleType().equals("Dependent")) {
							DependentRulesDto drdto = new DependentRulesDto();
							LanguageSpecificGlobalActivityDetails lsga = rulesDao.getlanguageSpecificGlobalActivitiesDetailsRecord(sar.getSourceActivity(), langId);
							drdto.setSourceActivityId(lsga.getGlobalActivity().getId());
							drdto.setSourceActivityName(lsga.getName());
							drdto.setRuleType("Dependent");
							String applyFor = sar.getDependentapplyFor();
							drdto.setDependentApplyFor(applyFor);
							if(applyFor != null && !applyFor.equals("")) {
								if(applyFor.equals("sameAct")) {
									LanguageSpecificGlobalParameterDetails lsp = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getSourceParameter(), langId);
									if(lsp != null) {
										drdto.setSourceParameterId(lsp.getParameterId().getId());
										drdto.setSourceParameterName(lsp.getName());
									}
									drdto.setCondition(sar.getCondition());
									String ctType = sar.getSourceParameter().getContentType().getCode();
									drdto.setControlType(ctType);
									if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB")) {
										drdto.setControlTypeLspId(sar.getGlobalValId().getId());
										LanguageSpecificValueDetails lsgv = rulesDao.getlsgvdetails(sar.getGlobalValId().getId(), langId);
										drdto.setControlTypeVal(lsgv.getName());
										drdto.setDestParameterCondition(sar.getDestParamCodntiion());
										drdto.setParamterAction(sar.getParameterAction());
										if(sar.getParameterAction().equals("setvalue")) {
											drdto.setDestinationParameterId(sar.getDestParameter().getId());
											LanguageSpecificGlobalParameterDetails lsgp = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
											drdto.setDestinationParameterName(lsgp.getName());
											String type = lsgp.getParameterId().getContentType().getCode();
											drdto.setDestinationcontrolType(type);
											if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
												LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
												drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
												drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
											}else
												drdto.setDestParamTbVal(sar.getDeptbvalue());
										}else {
											List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
											String parameterIds = "";
											String parameterNames = "";
											if(gpList != null && gpList.size() > 0) {
												for(LanguageSpecificGlobalParameterDetails gp : gpList) {
													if(parameterIds.equals(""))
														parameterIds = gp.getParameterId().getId()+"";
													else parameterIds = parameterIds+","+gp.getParameterId().getId();
													if(parameterNames.equals(""))
														parameterNames = gp.getName();
													else parameterNames = parameterNames+","+gp.getName();
												}
											}
											drdto.setEnableOrDiableParameterNames(parameterNames);
											drdto.setEnableOrDiableParameterIds(parameterIds);
										}
									}else {
										String condition = sar.getCondition();
										drdto.setCondition(condition);
										if(condition != null) {
											if(condition.equals("le") || condition.equals("ge") || condition.equals("ltAndgt") || condition.equals("leAndge")) {
												drdto.setFromRange(sar.getFromRange());
												drdto.setToRange(sar.getToRange());
												drdto.setDestParameterCondition(sar.getTbCondition());
												drdto.setParamterAction(sar.getParameterAction());
												if(sar.getParameterAction().equals("setvalue")) {
													LanguageSpecificGlobalParameterDetails lsgp = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
													drdto.setDestinationParameterId(lsgp.getParameterId().getId());
													drdto.setDestinationParameterName(lsgp.getName());
													String type = lsgp.getParameterId().getContentType().getCode();
													drdto.setDestinationcontrolType(type);
													if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
														LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
														drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
														drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
													}else
														drdto.setDestParamTbVal(sar.getDeptbvalue());
												}else {
													List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
													String parameterIds = "";
													String parameterNames = "";
													if(gpList != null && gpList.size() > 0) {
														for(LanguageSpecificGlobalParameterDetails gp : gpList) {
															if(parameterIds.equals(""))
																parameterIds = gp.getParameterId().getId()+"";
															else parameterIds = parameterIds+","+gp.getParameterId().getId();
															if(parameterNames.equals(""))
																parameterNames = gp.getName();
															else parameterNames = parameterNames+","+gp.getName();
														}
													}
													drdto.setEnableOrDiableParameterNames(parameterNames);
													drdto.setEnableOrDiableParameterIds(parameterIds);
												}
											}else {
												drdto.setFromRange(sar.getFromRange());
												drdto.setDestParameterCondition(sar.getDestParamCodntiion());
												drdto.setParamterAction(sar.getParameterAction());
												if(sar.getParameterAction().equals("setvalue")) {
													LanguageSpecificGlobalParameterDetails lsgpd = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
													drdto.setDestinationParameterId(lsgpd.getParameterId().getId());
													drdto.setDestinationParameterName(lsgpd.getName());
													String type = lsgpd.getParameterId().getContentType().getCode();
													drdto.setDestinationcontrolType(type);
													if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
														LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
														drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
														drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
													}else
														drdto.setDestParamTbVal(sar.getDeptbvalue());
												}else {
													List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
													String parameterIds = "";
													String parameterNames = "";
													if(gpList != null && gpList.size() > 0) {
														for(LanguageSpecificGlobalParameterDetails gp : gpList) {
															if(parameterIds.equals(""))
																parameterIds = gp.getParameterId().getId()+"";
															else parameterIds = parameterIds+","+gp.getParameterId().getId();
															if(parameterNames.equals(""))
																parameterNames = gp.getName();
															else parameterNames = parameterNames+","+gp.getName();
														}
													}
													drdto.setEnableOrDiableParameterNames(parameterNames);
													drdto.setEnableOrDiableParameterIds(parameterIds);
												}
											}
										}
									}
								}else {
									LanguageSpecificGlobalActivityDetails lspgad = rulesDao.getlanguageSpecificGlobalActivitiesDetailsRecord(sar.getDestinationActivity(), langId);
									drdto.setDestinationActivityId(lspgad.getGlobalActivity().getId());
									drdto.setDestinationActivityName(lspgad.getName());
									LanguageSpecificGlobalParameterDetails lsgpPojo = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getSourceParameter(), langId);
									drdto.setSourceParameterId(lsgpPojo.getParameterId().getId());
									drdto.setSourceParameterName(lsgpPojo.getName());
									String ctType = lsgpPojo.getParameterId().getContentType().getCode();
									drdto.setControlType(ctType);
									if(ctType.equals("RB") || ctType.equals("CB") || ctType.equals("SB"))  {
										drdto.setControlTypeLspId(sar.getGlobalValId().getId());
										LanguageSpecificValueDetails lsgv = rulesDao.getlsgvdetails(sar.getGlobalValId().getId(), langId);
										drdto.setControlTypeVal(lsgv.getName());
										drdto.setCondition(sar.getCondition());
										drdto.setParamterAction(sar.getParameterAction());
										if(sar.getParameterAction().equals("setvalue")) {
											drdto.setDestinationParameterId(sar.getDestParameter().getId());
											LanguageSpecificGlobalParameterDetails lsgp = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
											drdto.setDestinationParameterName(lsgp.getName());
											String type = lsgp.getParameterId().getContentType().getCode();
											drdto.setDestinationcontrolType(type);
											if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
												LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
												drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
												drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
											}else
												drdto.setDestParamTbVal(sar.getDeptbvalue());
										}else {
											List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
											String parameterIds = "";
											String parameterNames = "";
											if(gpList != null && gpList.size() > 0) {
												for(LanguageSpecificGlobalParameterDetails gp : gpList) {
													if(parameterIds.equals(""))
														parameterIds = gp.getParameterId().getId()+"";
													else parameterIds = parameterIds+","+gp.getParameterId().getId();
													if(parameterNames.equals(""))
														parameterNames = gp.getName();
													else parameterNames = parameterNames+","+gp.getName();
												}
											}
											drdto.setEnableOrDiableParameterNames(parameterNames);
											drdto.setEnableOrDiableParameterIds(parameterIds);
										}
									}else {
										String condition = sar.getCondition();
										drdto.setCondition(sar.getCondition());
										if(condition != null) {
											if(condition.equals("le") || condition.equals("ge") || condition.equals("ltAndgt") || condition.equals("leAndge")) {
												drdto.setFromRange(sar.getFromRange());
												drdto.setToRange(sar.getToRange());
												drdto.setDestParameterCondition(sar.getTbCondition());
												drdto.setParamterAction(sar.getParameterAction());
												if(sar.getParameterAction().equals("setvalue")) {
													LanguageSpecificGlobalParameterDetails lsgp = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
													drdto.setDestinationParameterId(lsgp.getParameterId().getId());
													drdto.setDestinationParameterName(lsgp.getName());
													String type = lsgp.getParameterId().getContentType().getCode();
													if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
														LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
														drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
														drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
													}else
														drdto.setDestParamTbVal(sar.getDeptbvalue());
												}else {
													List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
													String parameterIds = "";
													String parameterNames = "";
													if(gpList != null && gpList.size() > 0) {
														for(LanguageSpecificGlobalParameterDetails gp : gpList) {
															if(parameterIds.equals(""))
																parameterIds = gp.getParameterId().getId()+"";
															else parameterIds = parameterIds+","+gp.getParameterId().getId();
															if(parameterNames.equals(""))
																parameterNames = gp.getName();
															else parameterNames = parameterNames+","+gp.getName();
														}
													}
													drdto.setEnableOrDiableParameterNames(parameterNames);
													drdto.setEnableOrDiableParameterIds(parameterIds);
												}
											}else {
												drdto.setFromRange(sar.getFromRange());
												drdto.setDestParameterCondition(sar.getDestParamCodntiion());
												drdto.setParamterAction(sar.getParameterAction());
												if(sar.getParameterAction().equals("setvalue")) {
													LanguageSpecificGlobalParameterDetails lsgpd = rulesDao.getlanguageSpecificGlobalParameterDetails(sar.getDestParameter(), langId);
													drdto.setDestinationParameterId(lsgpd.getParameterId().getId());
													drdto.setDestinationParameterName(lsgpd.getName());
													String type = lsgpd.getParameterId().getContentType().getCode();
													drdto.setDestinationcontrolType(type);
													if(type.equals("RB") || type.equals("CB") || type.equals("SB")) {
														LanguageSpecificValueDetails lsgvPojo = rulesDao.getlsgvdetails(sar.getDestparameterGlobalLspId(), langId);
														drdto.setDestinationcontrolTypeVal(lsgvPojo.getName());
														drdto.setDestinationcontrolTypeLspId(lsgvPojo.getGlobalValId().getId());
													}else
														drdto.setDestParamTbVal(sar.getDeptbvalue());
												}else {
													List<LanguageSpecificGlobalParameterDetails> gpList = rulesDao.getParameterListBasedOnIdsList(sar.getMultiParam(), langId);
													String parameterIds = "";
													String parameterNames = "";
													if(gpList != null && gpList.size() > 0) {
														for(LanguageSpecificGlobalParameterDetails gp : gpList) {
															if(parameterIds.equals(""))
																parameterIds = gp.getParameterId().getId()+"";
															else parameterIds = parameterIds+","+gp.getParameterId().getId();
															if(parameterNames.equals(""))
																parameterNames = gp.getName();
															else parameterNames = parameterNames+","+gp.getName();
														}
													}
													drdto.setEnableOrDiableParameterNames(parameterNames);
													drdto.setEnableOrDiableParameterIds(parameterIds);
												}
											}
										}
									}
									
								}
							}
							dependentRulesList.add(drdto);
						}else if(sar.getRuleType().equals("DependentActivities")) {
							DependentActivitiesDto dadto = new DependentActivitiesDto();
							dadto.setActivityId(sar.getSourceActivity().getId());
							dadto.setActivityName(sar.getSourceActivity().getName());
							dadto.setRuleType("Dependent Activities");
							String depIdsStr = sar.getDependentActivities();
							List<Long> depIds = new ArrayList<>();
							if(depIdsStr != null && !depIdsStr.equals("")) {
								String[] tempArr = depIdsStr.split("\\,");
								if(tempArr.length > 0) {
									for(String st : tempArr) {
										if(!depIds.contains(Long.parseLong(st)))
											depIds.add(Long.parseLong(st));
									}
								}
							}
							List<ActivityDetailsDto> addList = new ArrayList<>();
							if(depIds.size() > 0) {
//								List<GlobalActivity> gpList = rulesDao.getGlobalActivitiesListBasedOnIds(depIds);
								List<LanguageSpecificGlobalActivityDetails> lsgaList = rulesDao.getLanguageSpecificGlobalActivitiesList(depIds, langId);
								if(lsgaList != null && lsgaList.size() > 0) {
									for(LanguageSpecificGlobalActivityDetails ga : lsgaList) {
										ActivityDetailsDto add = new ActivityDetailsDto();
										add.setActId(ga.getGlobalActivity().getId());
										add.setActName(ga.getName());
										addList.add(add);
									}
								}
							}
							dadto.setDependentActList(addList);
							dependentActList.add(dadto);
						}
					}
				}
				rules = new RulesDetails();
				rules.setCrdtoList(crdtoList);
				rules.setDependentRulesList(dependentRulesList);
				rules.setValRulesList(valRulesList);
				rules.setDependentActList(dependentActList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rules;
	}

	@Override
	public List<GlobalActivity> getGlobalActivityList(Long acivityId) {
		return rulesDao.getGlobalActivityList(acivityId);
	}

	@Override
	public ParameterDetailDto getParameterType(Long parameterId, Long langId) {
		return rulesDao.getParameterType(parameterId, langId);
	}

	

}
