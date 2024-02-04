package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DosingInfoDao;
import com.covideinfo.dto.DataInfoPdfGenerationDto;
import com.covideinfo.dto.DosingDataInfoDto;
import com.covideinfo.dto.DosingInfoDetialsDto;
import com.covideinfo.dto.DosingInfoSavingDetailsDto;
import com.covideinfo.dto.ProjectDetialsDto;
import com.covideinfo.dto.TimePointDtoDetails;
import com.covideinfo.dto.TimePointsDto;
import com.covideinfo.dto.TreatmentsDto;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.DosingInfoData;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.DosingInfoService;

@Service("dosingInfoService")
public class DosingInfoServiceImpl implements DosingInfoService {
	
	@Autowired
	DosingInfoDao dosingInfDao;
	
	private boolean trwDoseFlag = false;
	private boolean trwSampleFlag = false;
	private boolean trwMealsFlag = false;
	private boolean trwVitalFlag = false;
	private boolean trwSkinFlag = false;
	private boolean trwSkinAdhFlag = false;
	private boolean trwEcgFlag = false;
	private boolean trwOtherFlag = false;
	
	@Override
	public DosingInfoDetialsDto getDosingInfoDetialsDto(long projectId) {
		DosingInfoDetialsDto dinfDto = new DosingInfoDetialsDto();
		Projects project = null;
		Map<String, Map<String, List<TimePointsDto>>> timePointsMap = new HashMap<>(); //type, treatment, List of tp
		Map<String, Map<Integer, List<Long>>> parametersMap = new HashMap<>(); // type, treatment, List of paramIds
		Map<Integer, TreatmentsDto> treatmentMap = new HashMap<>();//treatmentNo, treatmentName
		TimePointDtoDetails tpDto = null;
		List<ProjectsDetails> doseList = null;
		List<ProjectsDetails> sampleList = null;
		List<ProjectsDetails> mealsList = null;
		List<ProjectsDetails> vitalsList = null;
		List<ProjectsDetails> treatmentList = null;
		List<ProjectsDetails> skinSensList = null;
		List<ProjectsDetails> skinAdhList = null;
		List<ProjectsDetails> ecgList = null;
		List<ProjectsDetails> otherActList = null;
		Map<String, List<ProjectsDetails>> paramMap = null; //type, List of Parameters
		DosingInfo doseInfo = null;
		List<DosingInfoData> dosInfoDataList = null;
		Map<String, Map<String, Integer>> dosingDataMap = new HashMap<>();
		int noOfSubjects = 0;
		int noOfStandbySubjects = 0;
		Map<String, String> trCodeMap = new HashMap<>();//trementName, randomizationCode
		List<String> trCodeList = new ArrayList<>();
		Map<Integer, List<ProjectsDetails>> proMap = new HashMap<>();
		try {
			tpDto = dosingInfDao.getTimePointDtoDetails(projectId);
			if(tpDto != null) {
				project = tpDto.getProject();
				doseList = tpDto.getDoseList();
				treatmentList = tpDto.getTreatmentList();
				sampleList = tpDto.getSampleList();
				mealsList = tpDto.getMealsList();
				vitalsList = tpDto.getVitalsList();
				doseInfo = tpDto.getDoseInfo();
				dosInfoDataList = tpDto.getDosInfoDataList();
				skinAdhList = tpDto.getSkinAdhList();
				skinSensList = tpDto.getSkinSensList();
				ecgList = tpDto.getEcgList();
				otherActList = tpDto.getOtherActList();
				paramMap = tpDto.getParamMap();
				noOfSubjects = tpDto.getNoOfSubjects();
				noOfStandbySubjects = tpDto.getNoOfStandbySubjects();
				
				if(doseInfo != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(doseInfo.getDosingDate());
					
//					DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
					DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		        	String timeString = dateFormat.format(doseInfo.getDosingDate()).toString();
		        	doseInfo.setDoseDateStr(date);
		        	doseInfo.setDoseTimeStr(timeString);
				}
				List<ProjectsDetails> projList = null;
				if(treatmentList != null && treatmentList.size() > 0) {
					for(ProjectsDetails pd : treatmentList) {
						if(proMap.containsKey(pd.getSubRowNo())) {
							projList = proMap.get(pd.getSubRowNo());
							projList.add(pd);
							proMap.put(pd.getSubRowNo(), projList);
						}else {
							projList = new ArrayList<>();
							projList.add(pd);
							proMap.put(pd.getSubRowNo(), projList);
						}
					}
				}
				if(proMap.size() > 0) {
					for(Map.Entry<Integer, List<ProjectsDetails>> pmap : proMap.entrySet()) {
						List<ProjectsDetails> pList = pmap.getValue();
						String treatmentName = "";
						String randomizationCode = "";
						int treatmentNo = 0;
						if(pList != null && pList.size() > 0) {
							treatmentName = "";
							randomizationCode = "";
						    treatmentNo = 0;
							for(ProjectsDetails p : pList) {
								if(p.getFieldName().equals("treatmentName"))
									treatmentName = p.getFieldValue();
								if(p.getFieldName().equals("randomizationCode"))
									randomizationCode = p.getFieldValue();
								if(p.getFieldName().equals("TREATMENT"))
									treatmentNo = Integer.parseInt(p.getFieldValue());
							}
							if(!treatmentName.equals("") && !randomizationCode.equals("")) {
								if(!trCodeList.contains(randomizationCode)) {
									TreatmentsDto trDto = new TreatmentsDto();
									trDto.setRandomizationCode(randomizationCode);
									trDto.setTreatmentName(treatmentName.trim());
									treatmentMap.put(pmap.getKey(), trDto);
									trCodeMap.put(treatmentName.trim(), randomizationCode);
									trCodeList.add(randomizationCode);
								}
							}
						}
					}
				}
//						treatmentMap.put(Integer.parseInt(pd.getSubRowNo()+""), pd.getFieldValue());
					
			    List<String> doseInfoTrNamesList = new ArrayList<>();
				Map<String, Integer> tempMap = null;
				if(dosInfoDataList != null && dosInfoDataList.size() > 0) {
					for(DosingInfoData dinf : dosInfoDataList) {
						if(dosingDataMap.containsKey(dinf.getType())) {
							tempMap = dosingDataMap.get(dinf.getType());
							tempMap.put(dinf.getTimepoint()+"_"+trCodeMap.get(dinf.getTreatment().trim()), dinf.getDifferenceBetweenSubjects());
							dosingDataMap.put(dinf.getType(), tempMap);
						}else {
							tempMap = new HashMap<>();
							tempMap.put(dinf.getTimepoint()+"_"+trCodeMap.get(dinf.getTreatment().trim()), dinf.getDifferenceBetweenSubjects());
							dosingDataMap.put(dinf.getType(), tempMap);
						}
						if(!doseInfoTrNamesList.contains(dinf.getTreatment().trim()))
							doseInfoTrNamesList.add(dinf.getTreatment().trim());
					}
				}
				
				timePointsMap = getTimpointsDetails(doseList, "DosingTimepoints", treatmentMap, dosingDataMap, timePointsMap, trwDoseFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(sampleList, "sampleTimePoins", treatmentMap, dosingDataMap, timePointsMap, trwSampleFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(mealsList, "mealsTimePointInformation", treatmentMap, dosingDataMap, timePointsMap, trwMealsFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(vitalsList, "vitalTimepointInformation", treatmentMap, dosingDataMap, timePointsMap, trwVitalFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(skinSensList, "SkinSensivity", treatmentMap, dosingDataMap,  timePointsMap, trwSkinFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(skinAdhList, "SkinAdhesion", treatmentMap, dosingDataMap,  timePointsMap, trwSkinAdhFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(ecgList, "ecgTimePoins", treatmentMap, dosingDataMap,  timePointsMap, trwEcgFlag, doseInfoTrNamesList);
				timePointsMap = getTimpointsDetails(otherActList, "OtherActivity", treatmentMap, dosingDataMap,  timePointsMap, trwOtherFlag, doseInfoTrNamesList);
				
				
				//Prameters
//				Map<String, Map<String, List<Long>>> parametersMap = new HashMap<>();
				Map<Integer, List<Long>> trparmMap = null;
				List<Long> paramIds = null;
				if(paramMap != null && paramMap.size() > 0) {
					for(Map.Entry<String, List<ProjectsDetails>> parMap : paramMap.entrySet()) {
						List<ProjectsDetails> proList = parMap.getValue();
						if(proList != null && proList.size() > 0) {
							for(ProjectsDetails pd : proList) {
								String[] strArr = null;
								if(pd.getFieldValue() != null && !pd.getFieldValue().equals("")) {
									strArr = pd.getFieldValue().split("\\,");
								}
								if(strArr != null && strArr.length > 0) {
									for(String st : strArr) {
										if(parametersMap.containsKey(parMap.getKey())) {
											trparmMap = parametersMap.get(parMap.getKey());
											int treatmentNo =0;
											if(pd.getTreatmentNo() != null)
												treatmentNo = Integer.parseInt(pd.getTreatmentNo()+"");
											if(trparmMap.containsKey(treatmentNo)) {
												paramIds = trparmMap.get(treatmentNo);
												paramIds.add(Long.parseLong(st));
												trparmMap.put(treatmentNo, paramIds);
												parametersMap.put(pd.getType(), trparmMap);
											}else {
												paramIds = new ArrayList<>();
												paramIds.add(Long.parseLong(st));
												trparmMap.put(treatmentNo, paramIds);
												parametersMap.put(pd.getType(), trparmMap);
											}
										}else {
											int treatmentNo =0;
											if(pd.getTreatmentNo() != null)
												treatmentNo = Integer.parseInt(pd.getTreatmentNo()+"");
											trparmMap = new HashMap<>();
											paramIds = new ArrayList<>();
											paramIds.add(Long.parseLong(st));
											trparmMap.put(treatmentNo, paramIds);
											parametersMap.put(pd.getType(), trparmMap);
										}
									}
								}
							}
						}
					}
				}
				
				dinfDto.setProject(project);
				dinfDto.setTimePointsMap(timePointsMap);
				dinfDto.setDoseInfo(doseInfo);
				dinfDto.setParametersMap(parametersMap);
				dinfDto.setNoOfSubjects(noOfSubjects);
				dinfDto.setNoOfStandbySubjects(noOfStandbySubjects);
				dinfDto.setTrwDoseFlag(trwDoseFlag);
				dinfDto.setTrwEcgFlag(trwEcgFlag);
				dinfDto.setTrwMealsFlag(trwMealsFlag);
				dinfDto.setTrwOtherFlag(trwOtherFlag);
				dinfDto.setTrwSampleFlag(trwSampleFlag);
				dinfDto.setTrwSkinAdhFlag(trwSkinAdhFlag);
				dinfDto.setTrwSkinFlag(trwSkinFlag);
				dinfDto.setTrwVitalFlag(trwVitalFlag);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dinfDto;
	}

	
	private Map<String, Map<String, List<TimePointsDto>>> getTimpointsDetails(List<ProjectsDetails> doseList, String type,
			Map<Integer, TreatmentsDto> treatmentMap, Map<String, Map<String, Integer>> dosingDataMap, Map<String, Map<String, List<TimePointsDto>>> map, boolean flag, List<String> doseInfoTrNamesList) {
		Map<String, List<TimePointsDto>> trtypeMap = null;
		List<TimePointsDto> tempList = null;
		List<String> tr1tpList = null;
		Map<String, List<String>> trtpMap = new HashMap<>();
		try {
			
			List<ProjectDetialsDto> pdDtoList = new ArrayList<>();
			//DoseTimePoints
			if(doseList != null && doseList.size() > 0) {
				for(ProjectsDetails pd : doseList) {
					ProjectDetialsDto  pdDto = new ProjectDetialsDto();
					BeanUtils.copyProperties(pd, pdDto);
					pdDtoList.add(pdDto);
					
				}
				Map<String, Integer> doseIntravelMap = null;
				if(dosingDataMap.get(type) != null && dosingDataMap.get(type).size() > 0) {
					doseIntravelMap = dosingDataMap.get(type);
				}
				Collections.sort(pdDtoList);
				for(ProjectDetialsDto pd : pdDtoList) {
					String tpStr = pd.getFieldValue();
					String[] tpArr = tpStr.split("\\,");
					for(String st : tpArr) {
						if(pd.getTreatmentNo() == 0) {
							int count =1;
							for(Map.Entry<Integer, TreatmentsDto> trMap : treatmentMap.entrySet()) {
								if(doseInfoTrNamesList.size()== 0 || doseInfoTrNamesList.contains(trMap.getValue().getTreatmentName().trim())){
									if(count == 1) {
										map = getMapDetails(map, trtypeMap, "AllTreatments", trMap.getValue(), st, pd, doseIntravelMap, tempList, type);
										count++;
									}
								}
							}
						}else {
							if(type.equals("DosingTimepoints"))
								flag = true; 
							if(type.equals("sampleTimePoins"))
								flag = true; 
							if(type.equals("mealsTimePointInformation"))
								flag = true; 
							if(type.equals("vitalTimepointInformation"))
								flag = true; 
							if(type.equals("SkinSensivity"))
								flag = true; 
							if(type.equals("SkinAdhesion"))
								flag = true; 
							if(type.equals("ecgTimePoins"))
								flag = true; 
							if(type.equals("OtherActivity"))
								flag = true; 
//							String treatmentName = treatmentMap.get(Integer.parseInt(pd.getTreatmentNo()+""));
							TreatmentsDto trDto = treatmentMap.get(pd.getSubRowNo());
							Map<String, List<TimePointsDto>> allTrMap = map.get(type);
							
							if(allTrMap != null && allTrMap.size() > 0) {
								List<TimePointsDto> tpDtoList = allTrMap.get("AllTreatments");
								if(tpDtoList != null && tpDtoList.size() > 0) {
									for(TimePointsDto tp : tpDtoList) {
										tr1tpList = trtpMap.get(trDto.getTreatmentName());
										if(tr1tpList == null)
											tr1tpList = new ArrayList<>();
										if(!tr1tpList.contains(tp.getTpVal())) {
											tr1tpList.add(tp.getTpVal());
											trtpMap.put(trDto.getRandomizationCode(), tr1tpList);
											map = getMapDetails(map, trtypeMap, trDto.getTreatmentName(), trDto, tp.getTpVal(), pd, doseIntravelMap, tempList, type);
											Map<String, List<TimePointsDto>> tempMap = map.get(type);
											tempMap.remove("AllTreatments");
											map.put(type, tempMap);
										}
									}
								}
								
							}
							tr1tpList = trtpMap.get(trDto.getRandomizationCode());
							if(tr1tpList == null)
								tr1tpList = new ArrayList<>();
							if(!tr1tpList.contains(st)) {
								tr1tpList.add(st);
								trtpMap.put(trDto.getRandomizationCode(), tr1tpList);
								map = getMapDetails(map, trtypeMap, trDto.getTreatmentName(), trDto, st, pd, doseIntravelMap, tempList, type);
						
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	private Map<String, Map<String, List<TimePointsDto>>> getMapDetails(
			Map<String, Map<String, List<TimePointsDto>>> map, Map<String, List<TimePointsDto>> trtypeMap,
			String trspecificString, TreatmentsDto trDto, String st, ProjectDetialsDto pd, Map<String, Integer> doseIntravelMap,
			List<TimePointsDto> tempList, String type) {
		TreeMap<String, Map<String, List<TimePointsDto>>> sortMap = new TreeMap<>();
		try {
			TimePointsDto dto = new TimePointsDto();
			if(type.equals("DosingTimepoints"))
				dto.setTypeStr("dose");
			if(type.equals("sampleTimePoins"))
				dto.setTypeStr("samp");
			if(type.equals("mealsTimePointInformation"))
				dto.setTypeStr("meal");
			if(type.equals("vitalTimepointInformation"))
				dto.setTypeStr("vital");
			if(type.equals("SkinSensivity"))
				dto.setTypeStr("skinsen");
			if(type.equals("SkinAdhesion"))
				dto.setTypeStr("skinAdh");
			if(type.equals("ecgTimePoins"))
				dto.setTypeStr("ecg");
			if(type.equals("OtherActivity"))
				dto.setTypeStr("other");
			if(doseIntravelMap != null && doseIntravelMap.size() > 0) {
				if(doseIntravelMap.get(st.trim()+"_"+trDto.getRandomizationCode()) != null)
					dto.setIntravelBetweenSubjecs(doseIntravelMap.get(st.trim()+"_"+trDto.getRandomizationCode()));
			}
			
			if(map.containsKey(type)) {
				trtypeMap = map.get(type);
				if(trtypeMap.containsKey(trspecificString)) {
					tempList = trtypeMap.get(trspecificString);
					dto.setTimePoint(Double.parseDouble(st));
					dto.setTreatmentName(trDto.getTreatmentName());
					dto.setTreatmentCode(trDto.getRandomizationCode());
					dto.setTpVal(st);
					dto.setSubRowNo(pd.getSubRowNo());
					tempList.add(dto);
					Collections.sort(tempList);
					trtypeMap.put(trspecificString, tempList);
					TreeMap<String, List<TimePointsDto>> sorted = new TreeMap<>();
					sorted.putAll(trtypeMap);
					map.put(type, sorted);
					sortMap.putAll(map);
				}else {
					tempList = new ArrayList<>();
					dto.setTimePoint(Double.parseDouble(st));
					dto.setTreatmentName(trDto.getTreatmentName());
					dto.setTreatmentCode(trDto.getRandomizationCode());
					dto.setTpVal(st);
					dto.setSubRowNo(pd.getSubRowNo());
					tempList.add(dto);
					Collections.sort(tempList);
					trtypeMap.put(trspecificString, tempList);
					TreeMap<String, List<TimePointsDto>> sorted = new TreeMap<>();
					sorted.putAll(trtypeMap);
					map.put(type, sorted);
					sortMap.putAll(map);
					
				}
			}else {
				trtypeMap = new HashMap<>();
				tempList = new ArrayList<>();
				dto.setTimePoint(Double.parseDouble(st));
				dto.setTreatmentName(trDto.getTreatmentName());
				dto.setTreatmentCode(trDto.getRandomizationCode());
				dto.setTpVal(st);
				dto.setSubRowNo(pd.getSubRowNo());
				tempList.add(dto);
				Collections.sort(tempList);
				trtypeMap.put(trspecificString, tempList);
				TreeMap<String, List<TimePointsDto>> sorted = new TreeMap<>();
				sorted.putAll(trtypeMap);
				map.put(type, sorted);
				sortMap.putAll(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sortMap;
	}


	@Override
	public String saveDosingInfDetails(DosingInfoSavingDetailsDto dinfDto, String userName) {
		String result = "Failed";
		DosingInfo doseinfo = null;
		List<DosingInfoData> dosinDataList = null;
		DosingDataInfoDto doseInfDto = null;
		Projects project = null;
		boolean updateDosiInfoFlag = false;
		boolean updateDoseDataInfoFlag = false;
		Map<String, String> treatMentsMap = new HashMap<>();
		try {
			if(dinfDto != null) {
				List<String> treatmentNamesList = dinfDto.getTreatmentNamesList();
				if(treatmentNamesList != null && treatmentNamesList.size() > 0) {
					for(String st : treatmentNamesList) {
						if(st != null && !st.equals("")) {
							String[] tempArr = st.split("\\@@@");
							if(tempArr.length > 0) {
								treatMentsMap.put(tempArr[0], tempArr[1]);
							}
						}
					}
				}
			}
			
			doseInfDto = dosingInfDao.getDosingInfoRecord(dinfDto.getProjectId());
			if(doseInfDto != null) {
				doseinfo = doseInfDto.getDosingInfo();
				dosinDataList = doseInfDto.getDosinDataList();
				project = doseInfDto.getPorject();
				if(doseinfo == null) {
					//New Records Insertion
					List<DosingInfoData> finalDoseDataList = new ArrayList<>();
 				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String doseDateTime = ""; 
				    Date doseDate = null;
					if(dinfDto.getDosingDate() != null && !dinfDto.getDosingDate().equals("")) {
						doseDateTime = dinfDto.getDosingDate()+" "+dinfDto.getDosingTime()+":00";
						doseDate = sdf.parse(doseDateTime);
					}
					doseinfo = new DosingInfo();
					doseinfo.setCreatedBy(userName);
					doseinfo.setCreatedOn(new Date());
					doseinfo.setDosingDate(doseDate);
					doseinfo.setDsdifferenceBetweenSubjects(dinfDto.getDsdifferenceBetweenSubjects());
					doseinfo.setNoOfStations(dinfDto.getNoOfStations());
					doseinfo.setProjects(project);
					
				    List<String> doseList = dinfDto.getDoseTpVals();
				    List<String> sampList = dinfDto.getSamplesTpVals();
				    List<String> mealsList = dinfDto.getMealsTpVal();
				    List<String> vitalsList = dinfDto.getVitalsTpVal();
				    List<String> skinSensList = dinfDto.getSkinSenTpVals();
					List<String> skinAdhList = dinfDto.getSkinAdhTpVals();
					List<String> ecgList = dinfDto.getEcgTpVals();
					List<String> ohtersList = dinfDto.getOhterTpVals();
					
				    finalDoseDataList = getFinalDosInfoDataList(doseList, userName, "DosingTimepoints", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(sampList, userName, "sampleTimePoins", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(mealsList, userName, "mealsTimePointInformation", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(vitalsList, userName, "vitalTimepointInformation", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(skinSensList, userName, "SkinSensivity", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(skinAdhList, userName, "SkinAdhesion", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(ecgList, userName, "ecgTimePoins", doseinfo, project, finalDoseDataList, treatMentsMap);
				    finalDoseDataList = getFinalDosInfoDataList(ohtersList, userName, "OtherActivity", doseinfo, project, finalDoseDataList, treatMentsMap);
					            
				    
				    result = dosingInfDao.saveDoseInfoData(doseinfo, finalDoseDataList);
				}else {
					List<DosingInfoData> updatefinalDoseDataList = new ArrayList<>();
					Map<String, Map<String, Map<String, DosingInfoData>>> doseDataMap = new HashMap<>();
					Map<String, Map<String, DosingInfoData>> twDataMap = null;
					 Map<String, DosingInfoData> tpMap = null;
					if(dosinDataList != null && dosinDataList.size() > 0) {
						for(DosingInfoData dinf : dosinDataList) {
							if(doseDataMap.containsKey(dinf.getType())) {
								twDataMap = doseDataMap.get(dinf.getType());
								if(twDataMap.containsKey(dinf.getTreatment())) {
									tpMap = twDataMap.get(dinf.getTreatment());
									tpMap.put(dinf.getTimepoint(), dinf);
									twDataMap.put(dinf.getTreatment(), tpMap);
									doseDataMap.put(dinf.getType(), twDataMap);
								}else {
									tpMap = new HashMap<>();
									tpMap.put(dinf.getTimepoint(), dinf);
									twDataMap.put(dinf.getTreatment(), tpMap);
									doseDataMap.put(dinf.getType(), twDataMap);
								}
							}else {
								tpMap = new HashMap<>();
								twDataMap = new HashMap<>();
								tpMap.put(dinf.getTimepoint(), dinf);
								twDataMap.put(dinf.getTreatment(), tpMap);
								doseDataMap.put(dinf.getType(), twDataMap);
							}
						}
					}
					//Records Updation
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String dateStr = sdf.format(doseinfo.getDosingDate());
					
//					DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
					DateFormat dateFormat = new SimpleDateFormat("hh:mm");
		        	String timeString = dateFormat.format(doseinfo.getDosingDate()).toString();
					if(!dateStr.equals(dinfDto.getDosingDate()) || !timeString.equals(dinfDto.getDosingTime())) {
						Date formatDate = sdf2.parse(dinfDto.getDosingDate()+" "+dinfDto.getDosingTime()+":00");
						doseinfo.setDosingDate(formatDate);
						updateDosiInfoFlag = true;
					}
					if(doseinfo.getDsdifferenceBetweenSubjects() != dinfDto.getDsdifferenceBetweenSubjects()) {
						doseinfo.setDsdifferenceBetweenSubjects(dinfDto.getDsdifferenceBetweenSubjects());
						updateDosiInfoFlag = true;
					}
					if(doseinfo.getNoOfStations() != dinfDto.getNoOfStations()) {
						doseinfo.setNoOfStations(dinfDto.getNoOfStations());
						updateDosiInfoFlag = true;
					}
					List<String> doseList = dinfDto.getDoseTpVals();
				    List<String> sampList = dinfDto.getSamplesTpVals();
				    List<String> mealsList = dinfDto.getMealsTpVal();
				    List<String> vitalsList = dinfDto.getVitalsTpVal();
				    List<String> skinSensList = dinfDto.getSkinSenTpVals();
					List<String> skinAdhList = dinfDto.getSkinAdhTpVals();
					List<String> ecgList = dinfDto.getEcgTpVals();
					List<String> ohtersList = dinfDto.getOhterTpVals();
				    
					updatefinalDoseDataList = getUpdateDoseInfoDataList(doseList, userName, "DosingTimepoints", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(sampList, userName, "sampleTimePoins", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(mealsList, userName, "mealsTimePointInformation", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(vitalsList, userName, "vitalTimepointInformation", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(skinSensList, userName, "SkinSensivity", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(skinAdhList, userName, "SkinAdhesion", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(ecgList, userName, "ecgTimePoins", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
					updatefinalDoseDataList = getUpdateDoseInfoDataList(ohtersList, userName, "OtherActivity", doseinfo, project, updatefinalDoseDataList, doseDataMap, treatMentsMap);
				    
					if(updatefinalDoseDataList.size() > 0)
						updateDoseDataInfoFlag = true;
					
					if(updateDosiInfoFlag || updateDoseDataInfoFlag) {
						doseinfo.setUpdatedBy(userName);
						doseinfo.setUpdatedOn(new Date());
						result = dosingInfDao.updateDosingInfoData(doseinfo, updatefinalDoseDataList, updateDosiInfoFlag, updateDoseDataInfoFlag);
					}else result = "updteNotRequired";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}


	private List<DosingInfoData> getUpdateDoseInfoDataList(List<String> list, String userName, String type,
			DosingInfo doseinfo, Projects project, List<DosingInfoData> updatefinalDoseDataList, Map<String, Map<String, Map<String, DosingInfoData>>> doseDataMap, Map<String, String> treatMentsMap) {
		try {
			if(list != null && list.size() > 0) {
		    	for(String st : list) {
		    		if(st != null && !st.equals("0")) {
		    			String[] tempArr = st.split("\\@@");
		    			if(tempArr.length > 0) {
		    				if(doseDataMap != null && doseDataMap.size() > 0) {
		    					DosingInfoData dinfData = doseDataMap.get(type).get(treatMentsMap.get(tempArr[2])).get(tempArr[0]);
			    				if(dinfData != null && dinfData.getDifferenceBetweenSubjects() != Integer.parseInt(tempArr[1])) {
			    					dinfData.setDifferenceBetweenSubjects(Integer.parseInt(tempArr[1]));
					    			dinfData.setUpdatedBy(userName);
					    			dinfData.setUpdatedOn(new Date());
					    			updatefinalDoseDataList.add(dinfData);
			    				}
		    				}/*else {
		    					DosingInfoData dinfData = new DosingInfoData();
		    					dinfData.setDifferenceBetweenSubjects(Integer.parseInt(tempArr[1]));
				    			dinfData.setUpdatedBy(userName);
				    			dinfData.setUpdatedOn(new Date());
				    			updatefinalDoseDataList.add(dinfData);
		    				}*/
		    			}
		    		}
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatefinalDoseDataList;
	}


	private List<DosingInfoData> getFinalDosInfoDataList(List<String> list, String userName, String type,
			DosingInfo doseinfo, Projects project, List<DosingInfoData> finalDoseDataList, Map<String, String> treatmentsMap) {
		try {
			if(list != null && list.size() > 0) {
		    	for(String st : list) {
		    		if(st != null && !st.equals("0")) {
		    			String[] tempArr = st.split("\\@@");
		    			if(tempArr.length > 0) {
		    				DosingInfoData dinfData = new DosingInfoData();
			    			dinfData.setDifferenceBetweenSubjects(Integer.parseInt(tempArr[1]));
			    			dinfData.setDosingInfo(doseinfo);
			    			dinfData.setProjectId(project.getProjectId());
			    			dinfData.setProjectNo(project.getProjectNo());
			    			dinfData.setTimepoint(tempArr[0]);
			    			dinfData.setTreatment(treatmentsMap.get(tempArr[2]));
			    			dinfData.setType(type);
			    			dinfData.setCreatedBy(userName);
			    			dinfData.setCreatedOn(new Date());
			    			finalDoseDataList.add(dinfData);
		    			}
		    		}
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalDoseDataList;
	}


	@Override
	public void generatePdfforDosingData(MessageSource messageSource, Long langId, String dateStr,
			Locale currentLocale, HttpServletRequest request, HttpServletResponse response, Long projectId, Long userId) {
		DosingInfoDetialsDto dinfDto = null;
		DataInfoPdfGenerationDto dinfPdfDto = null;
		DosingInfo doseInfo = null;
		boolean dataFlag = false;
		StudyMaster study = null;
		List<SubjectRandamization> subRandomList = null;
		List<StudySubjects> stdSubjList = null;
		Map<String, TreatmentInfo> subTrMap = new HashMap<>();
		int noOfSubjects = 0;
		int noOfStanbys = 0;
		UserMaster user = null;
		try {
			dinfDto = getDosingInfoDetialsDto(projectId);
			dinfPdfDto = dosingInfDao.getDataInfoPdfGenerationDtoDetails(projectId, langId, userId);
			if(dinfPdfDto != null) {
				study= dinfPdfDto.getStudy();
				subRandomList = dinfPdfDto.getSubRandomList();
				stdSubjList = dinfPdfDto.getStdSubjList();
				user = dinfPdfDto.getUser();
				
				if(subRandomList != null && subRandomList.size() > 0) {
					for(SubjectRandamization srz : subRandomList) {
						subTrMap.put(srz.getSubjectNo(), srz.getTreatmentInfo());
					}
				}
			}
			Workbook wb = new XSSFWorkbook(); 
			// Creating Font and settings  
            Font font = wb.createFont();  
            font.setFontHeightInPoints((short)15);  
            font.setFontName("Calibri");  
            font.setColor(Font.COLOR_NORMAL);
            // Applying font to the style  
            CellStyle style = wb.createCellStyle(); // Creating Style  
            style.setFont(font);
            Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			String filepath = "";
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String path = realPath+"//SheduledTimePoints";
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/ScheduledTps.xlsx";
			if(dinfDto != null) {
				doseInfo = dinfDto.getDoseInfo();
				noOfStanbys = dinfDto.getNoOfStandbySubjects();
				noOfSubjects = dinfDto.getNoOfSubjects();
				if(doseInfo != null) {
					dataFlag = true;
				}else dataFlag = false;
			}else dataFlag = false;
			if(dataFlag == false || subTrMap.size() == 0) {
				if(dataFlag == false) {
					String sheetName = messageSource.getMessage("label.sheetName", null, currentLocale);
					String errorMsg = messageSource.getMessage("label.sheetErrorMsg_NoData", null, currentLocale);
					sheet = wb.createSheet(sheetName);
					row = sheet.createRow(1);
					cell = row.createCell(1);
					cell.setCellValue(errorMsg);
					style.setWrapText(false);
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setBorderBottom(BorderStyle.THIN);
					style.setBorderTop(BorderStyle.THIN);
					style.setBorderLeft(BorderStyle.THIN);
					style.setBorderRight(BorderStyle.THIN);
					style.setLocked(true);
					font.setColor(Font.COLOR_RED);
					style.setFont(font);
					cell.setCellStyle(style);
				}else if(dataFlag && subTrMap.size() == 0){
					String sheetName = messageSource.getMessage("label.sheetName", null, currentLocale);
					String errorMsg = messageSource.getMessage("label.sheetEroorMsg_Randmization", null, currentLocale);
					sheet = wb.createSheet(sheetName);
					row = sheet.createRow(1);
					cell = row.createCell(1);
					cell.setCellValue(errorMsg);
					style.setWrapText(false);
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setBorderBottom(BorderStyle.THIN);
					style.setBorderTop(BorderStyle.THIN);
					style.setBorderLeft(BorderStyle.THIN);
					style.setBorderRight(BorderStyle.THIN);
					style.setLocked(true);
					font.setColor(Font.COLOR_RED);
					style.setFont(font);
					cell.setCellStyle(style);
					sheet.setColumnWidth(0, 100 * 256);
				}
			}else {
				if(noOfSubjects != 0) {
					int quotient = noOfSubjects / doseInfo.getNoOfStations();
					int remainder = noOfSubjects % doseInfo.getNoOfStations();
					int batchAddNo = 0;
					int batchRemainder = 0;
					if(remainder != 0) {
						batchAddNo = remainder / doseInfo.getNoOfStations();
						batchRemainder = remainder % doseInfo.getNoOfStations();
					}
					if(doseInfo.getNoOfStations() != 0 && quotient != 0) {
						Map<String, Map<String, List<TimePointsDto>>> timePointsMap = dinfDto.getTimePointsMap();
						if(timePointsMap != null && timePointsMap.size() > 0) {
							for(Map.Entry<String, Map<String, List<TimePointsDto>>> dataMap : timePointsMap.entrySet()) {
								for(Map.Entry<String, List<TimePointsDto>> tpMap : dataMap.getValue().entrySet()) {
									List<TimePointsDto> tpDtoList = tpMap.getValue();
									if(dataMap.getKey().equals("DosingTimepoints")) { 
										String sheetName = messageSource.getMessage("label.sheetDose", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("sampleTimePoins")) {
										String sheetName = messageSource.getMessage("label.sheetSampleCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("mealsTimePointInformation")) {
										String sheetName = messageSource.getMessage("label.sheetMealCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("vitalTimepointInformation")) {
										String sheetName = messageSource.getMessage("label.sheetVitalCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("SkinSensivity")) { 
										String sheetName = messageSource.getMessage("label.sheetSkinSenCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("SkinAdhesion")) {
										String sheetName = messageSource.getMessage("label.sheetSkinAdhCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("ecgTimePoins")) {
										String sheetName = messageSource.getMessage("label.sheetEcgCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									if(dataMap.getKey().equals("OtherActivity")) {
										String sheetName = messageSource.getMessage("label.sheetOtherActCol", null, currentLocale);
										sheet =  wb.createSheet(sheetName);
									}
									int rowNo =0;
//									for(TimePointsDto tpDto : tpDtoList) {
										TreeMap<Integer, Integer> subMap = new TreeMap<>();
										for(int j=1; j<=doseInfo.getNoOfStations(); j++) {
											int batchSubjects = quotient;
											if(batchAddNo != 0) 
												batchSubjects = batchSubjects + batchAddNo;
											if(batchRemainder != 0) {
												if(j==1)
													batchSubjects = batchSubjects + batchRemainder;
											}
											subMap.put(j, batchSubjects);
										}
										if(subMap != null && subMap.size() > 0) {
											int cellNo =0;
											int subNo =1;
											for(Map.Entry<Integer, Integer> smap : subMap.entrySet()) {
												cellNo =0;
												if(smap.getKey() ==1) {
													for(int j=1; j<=doseInfo.getNoOfStations()+2; j++) {
														row = sheet.createRow(rowNo++);
														cellNo =0;
														for(int i=1; i<=smap.getValue()+2; i++) {
															cell = row.createCell(cellNo++);
															if(j==1 && i==1) {
																String cellName = messageSource.getMessage("label.sheetColProject", null, currentLocale);
																cell.setCellValue(cellName+" "+doseInfo.getProjects().getProjectNo());
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
															}else if(j==2 && i==1) {
																String cellName = messageSource.getMessage("label.sheetColDate", null, currentLocale);
																cell.setCellValue(cellName);
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
																sheet.setColumnWidth(0, 25 * 256); 
															}else if(j==2 && i==2) {
																String tpStr = messageSource.getMessage("label.sheetColTp", null, currentLocale);
																cell.setCellValue(tpStr);
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
																sheet.setColumnWidth(1, 25 * 256); 
															}else if(j==2 && i == 3) {
																String subStr = messageSource.getMessage("label.sheetColSub", null, currentLocale);
																cell.setCellValue(subStr);
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
															}else if(j > 2 && i >= 3) {
																if(subNo < 10)
																	cell.setCellValue("0"+subNo);
																else cell.setCellValue(subNo+"");
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
																subNo++;
															}else {
																cell.setCellValue(" ");
																style.setWrapText(false);
																style.setVerticalAlignment(VerticalAlignment.CENTER);
																style.setAlignment(HorizontalAlignment.CENTER);
																style.setBorderBottom(BorderStyle.THIN);
																style.setBorderTop(BorderStyle.THIN);
																style.setBorderLeft(BorderStyle.THIN);
																style.setBorderRight(BorderStyle.THIN);
																style.setLocked(true);
																cell.setCellStyle(style);
															}
														}
													}
												}
											}	
											if(tpDtoList != null && tpDtoList.size() >0) {
												int subno = subMap.get(1);
												for(TimePointsDto tp : tpDtoList) {
													row = sheet.createRow(rowNo++);
													cellNo =0;
													int tpIntNo = 0;
													SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
													Date doseDate = doseInfo.getDosingDate();
													String tpDate = "";
													for(int j=1; j<=subno+2; j++) {
														cell = row.createCell(cellNo++);
														Calendar cal = Calendar.getInstance();
														cal.setTime(doseDate);
														tpDate = sdf.format(doseDate);
														if(j==1) {
															String[] arr = tp.getTpVal().split("\\.");
															if(arr.length > 0){
																int hrVal = 0;
																if(arr.length ==1)
																	hrVal = getAddingHours(arr[0], "0");
																else if(arr.length ==2) {
																	if(arr[0].equals("") && !arr[1].equals("")) 
																		hrVal = getAddingHours("0", arr[1]);
																}
																//hrVal = getAddingHours(arr[0], arr[1]);
																if(hrVal != 0) {
																	int daysQutient = 0;
																	int dayRem = 0;
																	if(hrVal != 0) {
																		Calendar calendar = Calendar.getInstance();
																		calendar.setTime(doseDate);
																		if(hrVal >= 24) {
																			daysQutient = hrVal /24;
																			dayRem = hrVal %24;
																			if(daysQutient != 0)
																				calendar.add(Calendar.DAY_OF_MONTH, daysQutient);
																		}else 
																			calendar.add(Calendar.HOUR, hrVal);
																		tpDate = sdf.format(calendar.getTime());
																	}
																}
															}
															cell.setCellValue(tpDate);
															style.setWrapText(false);
															style.setVerticalAlignment(VerticalAlignment.CENTER);
															style.setAlignment(HorizontalAlignment.CENTER);
															style.setBorderBottom(BorderStyle.THIN);
															style.setBorderTop(BorderStyle.THIN);
															style.setBorderLeft(BorderStyle.THIN);
															style.setBorderRight(BorderStyle.THIN);
															style.setLocked(true);
															cell.setCellStyle(style);
															sheet.setColumnWidth(0, 25 * 256); 
														}else if(j==2){
															cell.setCellValue(tp.getTpVal());
															style.setWrapText(false);
															style.setVerticalAlignment(VerticalAlignment.CENTER);
															style.setAlignment(HorizontalAlignment.CENTER);
															style.setBorderBottom(BorderStyle.THIN);
															style.setBorderTop(BorderStyle.THIN);
															style.setBorderLeft(BorderStyle.THIN);
															style.setBorderRight(BorderStyle.THIN);
															style.setLocked(true);
															cell.setCellStyle(style);
															sheet.setColumnWidth(1, 25 * 256); 
														}else if(j >= 3) {
															if(dataMap.getKey().equals("DosingTimepoints")) {
																if(j==3)
																	cal.add(Calendar.MINUTE, 0);
																else {
																	tpIntNo =tpIntNo+tp.getIntravelBetweenSubjecs();
																	cal.add(Calendar.MINUTE, tpIntNo);
																}
															}else {
																String[] tpArr = tp.getTpVal().split("\\.");
																if(tpArr.length > 0){
																	int addHr = getAddingHours(tpArr[0], tpArr[1]);
																	int addMin = getAddingMinutes(tpArr[1]);
																	if(addHr < 24) {
																		if(j==3) {
																			if(addHr != 0)
																				 cal.add(Calendar.HOUR, addHr);
																			if(addMin != 0)
																				cal.add(Calendar.MINUTE, addMin);
																		}else {
																			if(addHr != 0)
																				 cal.add(Calendar.HOUR, addHr);
																			tpIntNo =tpIntNo+tp.getIntravelBetweenSubjecs();
																			if(addMin != 0)
																				cal.add(Calendar.MINUTE, addMin+tpIntNo);
																			else 
																				cal.add(Calendar.MINUTE, tpIntNo);
																		}
																	}else {
																		int daysQutient = 0;
																		int dayRem = 0;
																		if(addHr != 0) {
																			daysQutient = addHr /24;
																			dayRem = addHr %24;
																		}
																		if(daysQutient != 0)
																			cal.add(Calendar.DAY_OF_MONTH, daysQutient); 
																		if(dayRem != 0)
																			cal.add(Calendar.HOUR, dayRem);
																		if(j==3) {
																			if(addMin != 0)
																				cal.add(Calendar.MINUTE, addMin);
																			else 
																				cal.add(Calendar.MINUTE, 0);
																		}else {
																			tpIntNo =tpIntNo+tp.getIntravelBetweenSubjecs();
																			if(addMin != 0)
																				cal.add(Calendar.MINUTE, addMin+tpIntNo);
																			else 
																				cal.add(Calendar.MINUTE, tpIntNo);
																		}
																		
																	}
																}
																
															}
															int hours = cal.get(Calendar.HOUR);
															int minutes = cal.get(Calendar.MINUTE);
															String hoursStr = "";
															String minStr = "";
															if(hours < 10)
																hoursStr = "0"+hours;
															else hoursStr = hours+"";
															if(minutes < 10)
																minStr = "0"+minutes;
															else minStr = minutes+"";
															
															cell.setCellValue(hoursStr+":"+minStr);
															style.setWrapText(false);
															style.setVerticalAlignment(VerticalAlignment.CENTER);
															style.setAlignment(HorizontalAlignment.CENTER);
															style.setBorderBottom(BorderStyle.THIN);
															style.setBorderTop(BorderStyle.THIN);
															style.setBorderLeft(BorderStyle.THIN);
															style.setBorderRight(BorderStyle.THIN);
															style.setLocked(true);
															cell.setCellStyle(style);
														}
													}
												}
											}
											sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheet.getRow(1).getLastCellNum()-1));
											sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, sheet.getRow(2).getLastCellNum()-1));
											sheet.addMergedRegion(new CellRangeAddress(1, doseInfo.getNoOfStations()+1, 0, 0));
											sheet.addMergedRegion(new CellRangeAddress(1, doseInfo.getNoOfStations()+1, 1, 1));
										}
//									}
									sheet.protectSheet(user.getUsername());
								}
							}
						}else {
							String sheetName = messageSource.getMessage("label.sheetName", null, currentLocale);
							String errorMsg = messageSource.getMessage("label.sheetTpErrorMsg", null, currentLocale);
							sheet = wb.createSheet(sheetName);
							row = sheet.createRow(1);
							cell = row.createCell(1);
							cell.setCellValue(errorMsg);
							style.setWrapText(false);
							style.setVerticalAlignment(VerticalAlignment.CENTER);
							style.setAlignment(HorizontalAlignment.CENTER);
							style.setBorderBottom(BorderStyle.THIN);
							style.setBorderTop(BorderStyle.THIN);
							style.setBorderLeft(BorderStyle.THIN);
							style.setBorderRight(BorderStyle.THIN);
							style.setLocked(true);
							font.setColor(Font.COLOR_RED);
							style.setFont(font);
							cell.setCellStyle(style);
							sheet.setColumnWidth(0, 50 * 256);
						}
						
					}
				}else {
					String sheetName = messageSource.getMessage("label.sheetName", null, currentLocale);
					String errorMsg = messageSource.getMessage("label.sheetSubErrorMsg", null, currentLocale);
					sheet = wb.createSheet(sheetName);
					row = sheet.createRow(1);
					cell = row.createCell(1);
					cell.setCellValue(errorMsg);
					style.setWrapText(false);
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setBorderBottom(BorderStyle.THIN);
					style.setBorderTop(BorderStyle.THIN);
					style.setBorderLeft(BorderStyle.THIN);
					style.setBorderRight(BorderStyle.THIN);
					style.setLocked(true);
					font.setColor(Font.COLOR_RED);
					style.setFont(font);
					cell.setCellStyle(style);
					sheet.setColumnWidth(0, 50 * 256);
				}
			}
			if(filepath != null && !filepath.equals("")) {
				  String workBookName = messageSource.getMessage("label.workbookName", null, currentLocale);
				  response.setHeader("Content-Disposition", "attachment; filename="+workBookName);
	              response.setContentType("application/vnd.ms-excel");
	              response.setHeader("Content-disposition", "inline;filename="+workBookName+".xlsx");
	           
	             
	              FileOutputStream out = new FileOutputStream(filepath);   
	              wb.write(out);    
	              wb.write(response.getOutputStream()); // Write workbook to response.
	              response.getOutputStream().flush();    
	              out.close();    
	              response.getOutputStream().close();
				/*FileOutputStream out = new FileOutputStream(filepath);
				wb.write(out);
				out.close();*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private int getAddingMinutes(String minutes) {
		int mins =0;
		try {
			if(minutes != null && !minutes.equals("") && !minutes.equals("0")) {
//				mins = Integer.parseInt(minutes);
				Double min = Double.parseDouble("0."+minutes);
				int finalMin = (int) (min*(60/1));
				if(finalMin > 59) {
					int diffQue = finalMin /60;
					int diffRem = finalMin % 60;
					if(diffRem != 0) 
						mins = mins + diffRem;
				}else mins = finalMin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mins;
	}


	private int getAddingHours(String hours, String minutes) {
		int hr =0;
		try {
			if(hours != null && !hours.equals("") && !hours.equals("0")) {
				hr = Integer.parseInt(hours);
			}
			if(minutes != null && !minutes.equals("") && !minutes.equals("0")) {
				Double min = Double.parseDouble("0."+minutes);
				int finalMin = (int) (min*(60/1));
				if(finalMin > 59) {
					int diffQue = finalMin /60;
					int diffRem = finalMin % 60;
					if(diffQue != 0) 
						hr = hr+diffQue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hr;
	}

	

}
