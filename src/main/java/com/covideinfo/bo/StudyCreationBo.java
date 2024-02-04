package com.covideinfo.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dto.DosingParameterDto;
import com.covideinfo.dto.OtherActivitiesDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyActivityTimpointsSavingDto;
import com.covideinfo.dto.StudyProjectDetailDto;
import com.covideinfo.dto.StudySampleCentrifugationDetailsDto;
import com.covideinfo.dto.StudySampleCentrifugationDto;
import com.covideinfo.dto.StudySampleProcessingDetailsDto;
import com.covideinfo.dto.StudySampleProcessingDto;
import com.covideinfo.dto.StudySampleStorageDetailsDto;
import com.covideinfo.dto.StudySampleStorageDto;
import com.covideinfo.dummy.ClinicalInfomation;
import com.covideinfo.dummy.SampleProcessingAndStorage;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyDesign;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.ActivityDraftReviewAudit;
import com.covideinfo.model.AdditionalAssesment;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.ECGTimePoints;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectPeriodStatus;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;
import com.covideinfo.service.StudyActivityService;

@Service("studyCreationBo")
@Transactional
public class StudyCreationBo {
	@Autowired
	private StudyCreationDao studyCreationDao;

	public StudyProjectDetailDto mapProjectDetailsToStudyMaster(Projects project) {
		StudyProjectDetailDto spdDto = null;
		try {
			StatusMaster activeStatus = null;
			List<ProjectsDetails> list = null;
			Map<String, ProjectsDetails> studyValues = new HashMap<>();
			List<ProjectsDetails> treatmentValues = new ArrayList<>();
			List<ProjectsDetails> dosingValues = new ArrayList<>();
			Map<String, ProjectsDetails> dosingNameValues = new HashMap<>();
			List<ProjectsDetails> dosingParametersValues = new ArrayList<>();
			Map<String, ProjectsDetails> dosingParametersNameValues = new HashMap<>();
			List<ProjectsDetails> sampleTimePoins = new ArrayList<>();
			Map<String, ProjectsDetails> sampleTimePoinsNameValues = new HashMap<>();
			List<ProjectsDetails> smapleInfo = new ArrayList<>();
			Map<String, ProjectsDetails> sampleInfoMap = new HashMap<>();
			List<ProjectsDetails> mealsTimePoins = new ArrayList<>();
			Map<String, ProjectsDetails> mealsTimePoinsNameValue = new HashMap<>();
			List<ProjectsDetails> mealsTimePointsInfo = new ArrayList<>();
			Map<String, ProjectsDetails> mealsTimePointsInfoNameValue = new HashMap<>();
			List<ProjectsDetails> ecgTimePoins = new ArrayList<>();
			Map<String, ProjectsDetails> ecgTimePoinsNameValue = new HashMap<>();
			List<ProjectsDetails> vitalTimePoins = new ArrayList<>();
			List<ProjectsDetails> vitalTimePoints = new ArrayList<>();
			Map<String, ProjectsDetails> vitalTimePoinsNameValue = new HashMap<>();
			List<ProjectsDetails> additionalAssesment = new ArrayList<>();
			List<ProjectsDetails> sampleProcessing = new ArrayList<>();
			List<ProjectsDetails> restrictionCompliance = new ArrayList<>();
			List<ProjectsDetails> inclustion = new ArrayList<>();
			List<ProjectsDetails> exclution = new ArrayList<>();
			List<ProjectsDetails> skinAdhesionList = new ArrayList<>();
			List<ProjectsDetails> skinSensivityList = new ArrayList<>();
			List<ProjectsDetails> otrherActivitiesList = new ArrayList<>();
			List<ProjectsDetails>  defaultactsList = new ArrayList<>();
			String ecgRequired = "";
			try {
				activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
				list = studyCreationDao.studyMasterFromProjectsDetails(project);
				for (ProjectsDetails p : list) {
					if (p.getType().equals("StudyInformation")) {
						studyValues.put(p.getFieldName(), p);
						if (!studyValues.containsKey("createdBy"))
							studyValues.put("createdBy", p);
					} else if (p.getType().equals("studyDesign")) {
						treatmentValues.add(p);
					} else if (p.getType().equals("treatmentCode")) {
						treatmentValues.add(p);
					} else if (p.getType().equals("TreatmentWiseInformation")) {
						treatmentValues.add(p);
					} else if (p.getType().equals("DosingTimepoints")) {
						dosingValues.add(p);
						if (p.getRowNo() == 0)
							dosingNameValues.put(p.getFieldName(), p);
					} else if (p.getType().equals("DosingParameters")) {
						dosingParametersValues.add(p);
						if(p.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString()))
							dosingParametersNameValues.put(p.getFieldName(), p);
					} else if (p.getType().equals("sampleInformation")) {
						smapleInfo.add(p);
						if (p.getRowNo() == 0)
							sampleInfoMap.put(p.getFieldName(), p);
					} else if (p.getType().equals("sampleTimePoins")) {
						sampleTimePoins.add(p);
						if (p.getRowNo() == 0)
							sampleTimePoinsNameValues.put(p.getFieldName(), p);
					} else if (p.getType().equals("mealsTimePoints")) {
						mealsTimePoins.add(p);
//						if (p.getFieldName().equals("teatmentSpecificMealTimepoints"))
						if (p.getRowNo() == 0)
							mealsTimePoinsNameValue.put(p.getFieldName(), p);
					} else if (p.getType().equals("mealsTimePointInformation")) {
						mealsTimePointsInfo.add(p);
						if (p.getRowNo() == 0)
							mealsTimePointsInfoNameValue.put(p.getFieldName(), p);
					} else if (p.getType().equals("ecgTimePoins")) {
						ecgTimePoins.add(p);
						if (p.getRowNo() == 0)
							ecgTimePoinsNameValue.put(p.getFieldName(), p);
					} else if (p.getType().equals("ecgTimePoins") && p.getFieldName().equals("EcgApplicable")) {
						ecgRequired = p.getFieldValue();
					} else if (p.getType().equals("vitalTimePoins")) {
						vitalTimePoins.add(p);
						if (p.getRowNo() == 0)
							vitalTimePoinsNameValue.put(p.getFieldName(), p);
					} else if (p.getType().equals("vitalTimepointInformation")) {
						vitalTimePoints.add(p);
					} else if (p.getType().equals("additionalAssesment")) {
						additionalAssesment.add(p);
					} else if (p.getType().equals("sampleProcessing")) {
						sampleProcessing.add(p);
					} else if (p.getType().equals("restrictionsComplainceMonitoring")) {
						restrictionCompliance.add(p);
					} else if (p.getType().equals("inclusionCriteria")) {
						inclustion.add(p);
					} else if (p.getType().equals("exclusionCriteria")) {
						exclution.add(p);
					} else if ((p.getType().equals("SkinAdhesion"))) {
						skinAdhesionList.add(p);
					} else if ((p.getType().equals("SkinSensivity"))) {
						skinSensivityList.add(p);
					} else if (p.getType().equals("OtherActivity")) {
						otrherActivitiesList.add(p);
					}else if(p.getType().equals("DefaultActivity")) {
						defaultactsList.add(p);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			Map<Integer, List<ProjectsDetails>> treatmentValuesRowValues = new HashMap<>(); // row wise
			for (ProjectsDetails p : treatmentValues) {
				List<ProjectsDetails> lis = treatmentValuesRowValues.get(p.getSubRowNo());
				if (lis == null)
					lis = new ArrayList<>();
				lis.add(p);
				treatmentValuesRowValues.put(p.getSubRowNo(), lis);
			}
			Map<Integer, List<ProjectsDetails>> dosingRowValues = new HashMap<>(); // TreatmentNo wise
			for (ProjectsDetails p : dosingValues) {
				if(p.getTreatmentNo() != null) {
					List<ProjectsDetails> lis = dosingRowValues.get(Integer.parseInt(p.getTreatmentNo().toString()));
					if (lis == null)
						lis = new ArrayList<>();
					lis.add(p);
					if (p.getRowNo() != 0)
						dosingRowValues.put(Integer.parseInt(p.getTreatmentNo().toString()), lis);
				}
			}
			Map<Integer, List<ProjectsDetails>> sampleTimePoinsRowValues = new HashMap<>(); // row wise
			for (ProjectsDetails p : sampleTimePoins) {
				if(p.getTreatmentNo() != null) {
					List<ProjectsDetails> lis = sampleTimePoinsRowValues.get(Integer.parseInt(p.getTreatmentNo().toString()));
					if (lis == null)
						lis = new ArrayList<>();
					lis.add(p);
					if (p.getRowNo() != 0)
						sampleTimePoinsRowValues.put(Integer.parseInt(p.getTreatmentNo().toString()), lis);
				}
			}
			Map<Integer, List<ProjectsDetails>> sampleTimePoinsInfoRowValues = new HashMap<>(); // row wise
			for (ProjectsDetails p : smapleInfo) {
				if(p.getTreatmentNo() != null) {
					List<ProjectsDetails> lis = sampleTimePoinsInfoRowValues.get(Integer.parseInt(p.getTreatmentNo().toString()));
					if (lis == null)
						lis = new ArrayList<>();
					lis.add(p);
					if (p.getRowNo() != 0)
						sampleTimePoinsInfoRowValues.put(Integer.parseInt(p.getTreatmentNo().toString()), lis);
				}
			}
			Map<Integer, List<ProjectsDetails>> mealsTimePoinsRowValues = new HashMap<>(); // row wise
			for (ProjectsDetails p : mealsTimePointsInfo) {
				if(p.getTreatmentNo() != null) {
					List<ProjectsDetails> lis = mealsTimePoinsRowValues.get(Integer.parseInt(p.getTreatmentNo().toString()));
					if (lis == null)
						lis = new ArrayList<>();
					lis.add(p);
					mealsTimePoinsRowValues.put(Integer.parseInt(p.getTreatmentNo().toString()), lis);
				}
			}
			Map<Integer, List<ProjectsDetails>> vitalTimePoinsRowValues = new HashMap<>(); // row wise
			for (ProjectsDetails p : vitalTimePoints) {
				if(p.getTreatmentNo() != null) {
					List<ProjectsDetails> lis = vitalTimePoinsRowValues.get(Integer.parseInt(p.getTreatmentNo().toString()));
					if (lis == null)
						lis = new ArrayList<>();
					lis.add(p);
					vitalTimePoinsRowValues.put(Integer.parseInt(p.getTreatmentNo().toString()), lis);
				}
			}
			Map<Integer, List<ProjectsDetails>> otherActivityMap = new HashMap<>();
			for (ProjectsDetails p : otrherActivitiesList) {
				List<ProjectsDetails> lis = otherActivityMap.get(p.getSubRowNo());
				if (lis == null)
					lis = new ArrayList<>();
				lis.add(p);
				otherActivityMap.put(p.getSubRowNo(), lis);
			}
			Map<Long, List<Long>> defaultActMap = new HashMap<>(); //activityId, List of parameters
			List<Long> parmIds = null;
			if(defaultactsList != null && defaultactsList.size() > 0) {
			    for(ProjectsDetails p : defaultactsList) {
			    	if(p.getStatus()) {
			    		if(defaultActMap.containsKey(Long.parseLong(p.getDisplayValue()))){
			    			parmIds = defaultActMap.get(Long.parseLong(p.getDisplayValue()));
			    			parmIds.add(Long.parseLong(p.getFieldValue()));
			    			defaultActMap.put(Long.parseLong(p.getDisplayValue()), parmIds);
			    		}else {
			    			parmIds = new ArrayList<>();
			    			parmIds.add(Long.parseLong(p.getFieldValue()));
			    			defaultActMap.put(Long.parseLong(p.getDisplayValue()), parmIds);
			    		}
			    	}
			    }
			}
			StudyMaster study =  null;
			if(studyValues != null && studyValues.size() > 0) {
				study = new StudyMaster();
				String projectNo = studyValues.get("projectNumber").getFieldValue();
				int seq = studyCreationDao.studyMasterMaxSeqNoWithProjectNo(projectNo);
				seq++;
				study.setCreatedBy(studyValues.get("createdBy").getCreatedBy());
				study.setCreatedOn(studyValues.get("createdBy").getCreatedOn());
				study.setProjectNo(studyValues.get("projectNumber").getFieldValue());
				study.setVersionNo("V " + seq + ".0");
				study.setSeqNo(seq);
				String pd = studyValues.get("sponsorCode").getFieldValue();
				study.setSponsorCode(pd);
				study.setStudyCategory(
						studyCreationDao.formStaticData("Masking", studyValues.get("Masking").getFieldValue()));
				study.setStudyDesing(studyCreationDao.formStaticData(studyValues.get("studyDesign").getFieldName(),
						studyValues.get("studyDesign").getFieldValue()));
				study.setStudyType(
						studyCreationDao.formStaticData("doseType", studyValues.get("doseType").getFieldValue()));
				study.setStudyDesing(
						studyCreationDao.formStaticData("studyDesign", studyValues.get("studyDesign").getFieldValue()));
				study.setStudyTitle(studyValues.get("studyTitle").getFieldValue());
				study.setNoOfPeriods(Integer.parseInt(studyValues.get("noOfPeriods").getFieldValue()));
				study.setWashoutPeriod(Integer.parseInt(studyValues.get("washoutPeriod").getFieldValue()));
				study.setDosageFrom(studyCreationDao.dosageFrom(studyValues.get("dosageForm").getFieldName(),
						studyValues.get("dosageForm").getFieldValue()));
				study.setTreatmentCodeOnSachet(
						studyCreationDao.formStaticData("treatmentCode", studyValues.get("treatmentCode").getFieldValue()));
				study.setNoOfSubjects(Integer.parseInt(studyValues.get("noOfSubjects").getFieldValue()));
				study.setNoOfStandBySubjects(Integer.parseInt(studyValues.get("noOfStandBys").getFieldValue()));
				study.setDoseType(studyCreationDao.formStaticData("doseType", studyValues.get("doseType").getFieldValue()));
				study.setNoOfTreatments(Integer.parseInt(studyValues.get("noOfTreatments").getFieldValue()));
				study.setPreDoseHr(Integer.parseInt(studyValues.get("preDoseHousing").getFieldValue()));
				study.setPostDoseHr(Integer.parseInt(studyValues.get("postDoseHousing").getFieldValue()));

				study.setStudyState(studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString()));
				study.setStudyStatus(studyCreationDao.statusMaster(StudyStatus.APPROVED.toString()));
			}
			String value = "";
			// Treatemnte Data
			List<TreatmentInfo> treatments = new ArrayList<>();
			Map<String, TreatmentInfo> treatmentMap = new HashMap<>();
			Map<Integer, TreatmentInfo> treatmentNoMap = new HashMap<>();
			List<StudyPeriodMaster> periods = new ArrayList<>();
			DosingParameterDto dosingParamDto = null;
			List<DoseTimePoints> doseTimePoints = null;
			ClinicalInfomation cliInfo = null;
			List<AdditionalAssesment> additionalAssesmentTimePoints = new ArrayList<>();
			SampleProcessingAndStorage sampleProcessingAndStorage =  null;
			Map<Integer, List<ProjectsDetails>> rescompMap = null;
			Map<Integer, List<ProjectsDetails>> inclusionMap = null;
			Map<Integer, List<ProjectsDetails>> exclusionMap = null;
            if(study != null) {
            	if (dosingNameValues.get("IsthereAnyDifferenceinDosings") != null) {
    				dosingNameValues.get("IsthereAnyDifferenceinDosings").getFieldValue();
    				if (value.equals(StaticData.YES.toString())) {
    					study.setTreatmentWiseNoOfDosing(true);
    				}
    			}
    			ProjectsDetails pdPojo = dosingParametersNameValues.get("TREATMENT");
    			if(pdPojo != null)
    				value = pdPojo.getFieldValue();
    			else value = "0";
    			if (!value.equals("0")) {
    				study.setTreatmentWiseDoseParametres(true);
    			}
    			if(sampleTimePoinsNameValues != null && sampleTimePoinsNameValues.size() > 0) {
    				value = sampleTimePoinsNameValues.get("treatmentSpecificTimepoint").getFieldValue();
        			if (value.equals(StaticData.YES.toString())) {
        				study.setTreatmentSpecificSampleTimepoints(true);
        			}
    			}
    			if(mealsTimePoinsNameValue != null && mealsTimePoinsNameValue.size() > 0) {
    				value = mealsTimePoinsNameValue.get("teatmentSpecificMealTimepoints").getFieldValue();
        			if (value.equals(StaticData.YES.toString())) {
        				study.setTreatmentWiseMealsTimepoints(true);
        			}
    			}
    			if(vitalTimePoinsNameValue != null && vitalTimePoinsNameValue.size() > 0) {
    				value = vitalTimePoinsNameValue.get("teatmentSpecificVitalTimepoints").getFieldValue();
        			if (value.equals(StaticData.YES.toString())) {
        				study.setTreatmentWiseVitalTimepoints(true);
        			}
    			}
    			if(ecgTimePoinsNameValue != null && ecgTimePoinsNameValue.size() > 0) {
    				if (ecgRequired.equalsIgnoreCase("YES")) {
        				value = ecgTimePoinsNameValue.get("EcgTreatmentSpecificTimepoints").getFieldValue();
        				if (value.equals(StaticData.YES.toString())) {
        					study.setTreatmentWiseEcgTimepoints(true);
        				}
        			}
    			}
    			// save 3
    			if(study != null) {
    				StudyPeriodMaster sp = null;
        			for (int i = 1; i <= study.getNoOfPeriods(); i++) {
        				sp = new StudyPeriodMaster();
        				sp.setCreatedBy(study.getCreatedBy());
        				sp.setCreatedOn(study.getCreatedOn());
        				sp.setStudy(study);
        				sp.setPeriodName("P" + i);
        				sp.setPeriodNo(i);
        				periods.add(sp);
        			}
        			
    			}
    			if(treatmentValuesRowValues != null && treatmentValuesRowValues.size() > 0) {
    				for (Map.Entry<Integer, List<ProjectsDetails>> pl : treatmentValuesRowValues.entrySet()) {
        				TreatmentInfo t = new TreatmentInfo();
        				t.setCreatedBy(study.getCreatedBy());
        				t.setCreatedOn(study.getCreatedOn());
        				t.setStudy(study);
        				t.setTreatmentCount(pl.getKey());
        				t.setNoOfSachetLables(1);
        				List<ProjectsDetails> pls = pl.getValue();
        				for (ProjectsDetails p : pls) {
        					if (p.getFieldName().equals(StudyDesign.TREATMENT.toString())) {
        						t.setTreatmentNo(p.getFieldValue());
        					} else if (p.getFieldName().equals("treatmentName")) {
        						t.setTreatmentName(p.getFieldValue());
        					} else if (p.getFieldName().equals("randomizationCode")) {
        						t.setRandamizationCode(p.getFieldValue());
        					} else if (p.getFieldName().equals("strength")) {
        						t.setStreanth(p.getFieldValue());
        					} else if (p.getFieldName().equals("dose")) {
        						t.setDose(p.getFieldValue());
        					} else if (p.getFieldName().equals("amountOfWaterConsumedWiththeDose")) {
        						t.setMountOfWaterConsumedWithTheDose(p.getFieldValue());
        					} else if (p.getFieldName().equals("treatmentType")) {
        						t.setFastingInfo(studyCreationDao.formStaticData(StudyDesign.TREATMENTTYPE.toString(),
        								p.getFieldValue()));
        					} else if (p.getFieldName().equals("noOfSachetLabels")) {
        						t.setNoOfSachetLables(Integer.parseInt(p.getFieldValue()));
        					}

        				}
        				treatments.add(t);
        				treatmentMap.put(t.getTreatmentName(), t);
        				treatmentNoMap.put(Integer.parseInt(t.getTreatmentNo()), t);
        			}
    			}
    			
    			if(dosingParametersValues != null && dosingParametersValues.size() > 0)
    				dosingParamDto = getStudyDoseParameters(dosingParametersValues);
    			// DOSE timepoints
    			if(dosingRowValues != null && dosingRowValues.size() > 0)
    				doseTimePoints = doseTimePoints(treatmentNoMap, dosingRowValues, study, activeStatus, dosingParamDto.getTimePointParmMap());
//    			Dosing Parameters
    			
//    			Clincial time points
    			cliInfo = timePointMeataData(study, treatmentMap, treatmentNoMap, activeStatus,
    					sampleTimePoinsRowValues, sampleTimePoinsInfoRowValues, mealsTimePoinsRowValues,
    					ecgTimePoins, vitalTimePoinsRowValues, skinAdhesionList, skinSensivityList, otherActivityMap);

//    			// Additional Assesment
    			if(sampleProcessing != null && sampleProcessing.size() > 0) 
    				sampleProcessingAndStorage = sampleProcessingAndStorage(study, sampleProcessing);
    			
    			if(restrictionCompliance != null && restrictionCompliance.size() > 0) 
    				rescompMap = restrictionCompliance(restrictionCompliance);
    			
    			if(inclustion != null && inclustion.size() > 0)
    				inclusionMap = subRowWiseFilter(inclustion);
    			if(exclution != null && exclution.size() > 0)
    				exclusionMap = subRowWiseFilter(exclution);
            }
			
			
			spdDto = new StudyProjectDetailDto();
			spdDto.setProjects(project);
			spdDto.setProList(list);
			spdDto.setAdditionalAssesmentTimePoints(additionalAssesmentTimePoints);
			spdDto.setClinicalInfomation(cliInfo);
			spdDto.setNonTimePointDosingParamMap(dosingParamDto.getNonTimePointParmMap());
			spdDto.setDoseTimePoints(doseTimePoints);
			spdDto.setExclusionCriteria(exclusionMap);
			spdDto.setInclusionCriteria(inclusionMap);
			spdDto.setRestrictionComplains(rescompMap);
			spdDto.setStudyMaster(study);
			spdDto.setStudyPeriodMasterList(periods);
			spdDto.setTreatmentInfoList(treatments);
			spdDto.setSampleProcessingAndStorage(sampleProcessingAndStorage);
			spdDto.setDefaultActMap(defaultActMap);
			spdDto.setTreatmentNoMap(treatmentNoMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spdDto;
	}

	public StudyMaster createStudyMaster(Projects project, StudyActivityService studyActivityService) throws Exception {
		StudyProjectDetailDto spDto = null;
		StudyActivitiesSavingDto sasDto = null;
		String result = "Failed";
		spDto = mapProjectDetailsToStudyMaster(project);
		StudyMaster sm = null;
		/*ActivityDraftReviewAudit adra= null;
		StudyMaster sm = studyCreationDao.saveStudyCompleteInfomation(spDto);
		if (sm != null)
			sasDto = studyActivityService.saveStudyDesignActivityDetails(sm, spDto.getRestrictionComplains(),
					spDto.getInclusionCriteria(), spDto.getExclusionCriteria(), spDto.getDefaultActMap());
		if (sasDto != null)
			result = studyCreationDao.saveStudyActivitiesData(sasDto, spDto.getDefaultActMap(), sm);
		System.out.println(result);*/
		return sm;
	}

	public ClinicalInfomation timePointMeataData(StudyMaster study, Map<String, TreatmentInfo> treatmentMap,
			Map<Integer, TreatmentInfo> treatmentNoMap, StatusMaster activeStatus,
			Map<Integer, List<ProjectsDetails>> sampleTimePoinsRowValues,
			Map<Integer, List<ProjectsDetails>> sampleInfoMap,
			Map<Integer, List<ProjectsDetails>> mealsTimePoinsRowValues,
			List<ProjectsDetails> ecgTimePoinsRowValues,
			Map<Integer, List<ProjectsDetails>> vitalTimePoinsRowValues, 
			List<ProjectsDetails> skinAdList,
			List<ProjectsDetails> skinSenList, 
			Map<Integer, List<ProjectsDetails>> otherActivityMap) {
		
		ClinicalInfomation clInfo = new ClinicalInfomation();
		// Meals Points
		List<MealsTimePoints> mtpList = new ArrayList<>();
		List<MealsTimePoints> mtList = null;
		if (mealsTimePoinsRowValues != null && mealsTimePoinsRowValues.size() > 0) {
			for (Map.Entry<Integer, List<ProjectsDetails>> tmMap : mealsTimePoinsRowValues.entrySet()) {
				int treatmentNo = tmMap.getKey();
				if (treatmentNo == 0) {
					mtList = getMealsTimePoints(treatmentNoMap, tmMap.getValue(), study);
					if (mtList.size() > 0)
						mtpList.addAll(mtList);
				} else {
					Map<Integer, TreatmentInfo> tmap = new HashMap<>();
					tmap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					mtList = getMealsTimePoints(tmap, tmMap.getValue(), study);
					if (mtList.size() > 0)
						mtpList.addAll(mtList);
				}
			}
		}
		List<SampleTimePoints> samptList = new ArrayList<>();
		List<SampleTimePoints> sampList = null;
		if (sampleTimePoinsRowValues != null && sampleTimePoinsRowValues.size() > 0) {
			for (Map.Entry<Integer, List<ProjectsDetails>> twsmap : sampleTimePoinsRowValues.entrySet()) {
				int treatmentNo = twsmap.getKey();
				if (treatmentNo == 0) {
					sampList = getSampleTimpointsRecords(treatmentNoMap, study, twsmap.getValue());
					if (sampList != null && sampList.size() > 0) {
						samptList.addAll(sampList);
					}
				} else {
					Map<Integer, TreatmentInfo> tmap = new HashMap<>();
					tmap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					sampList = getSampleTimpointsRecords(tmap, study, twsmap.getValue());
					if (sampList != null && sampList.size() > 0) {
						samptList.addAll(sampList);
					}
				}
			}
		}
		List<VitalTimePoints> vtimepointsList = new ArrayList<>();
		List<VitalTimePoints> vptList = null;
		if (vitalTimePoinsRowValues != null && vitalTimePoinsRowValues.size() > 0) {
			for (Map.Entry<Integer, List<ProjectsDetails>> vtMap : vitalTimePoinsRowValues.entrySet()) {
				int treatmentNo = vtMap.getKey();
				if (treatmentNo == 0) {
					vptList = vititalTimpointsRecords(treatmentNoMap, study, vtMap.getValue());
					if (vptList != null && vptList.size() > 0) {
						vtimepointsList.addAll(vptList);
					}
				} else {
					Map<Integer, TreatmentInfo> tmap = new HashMap<>();
					tmap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					vptList = vititalTimpointsRecords(tmap, study, vtMap.getValue());
					if (vptList != null && vptList.size() > 0) {
						vtimepointsList.addAll(vptList);
					}
				}
			}
		}
		// EcgTimePoints
		Map<Integer, ProjectsDetails> twecgMap = new HashMap<>();
			if (ecgTimePoinsRowValues != null && ecgTimePoinsRowValues.size() > 0) {
				for (ProjectsDetails pd : ecgTimePoinsRowValues) {
					if (pd.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString())) {
						if (!twecgMap.containsKey(Integer.parseInt(pd.getFieldValue())))
							twecgMap.put(Integer.parseInt(pd.getFieldValue()), pd);
					}
				}
			}
//		}
		//Ecg Timpoints for StudyActivities 
		List<StudyActivityTimpointsSavingDto> satpecgDtoList = new ArrayList<>();
		List<StudyActivityTimpointsSavingDto> satpecgList = null;
		if (twecgMap.size() > 0) {
			for (Map.Entry<Integer, ProjectsDetails> tecgMap : twecgMap.entrySet()) {
				int treatmentNo = tecgMap.getKey();
				if (treatmentNo == 0) {
					satpecgList = getSkinAdhectionDetailsList(treatmentNoMap, ecgTimePoinsRowValues);
					if (satpecgList != null && satpecgList.size() > 0) {
						satpecgDtoList.addAll(satpecgList);
					}
				} else {
					Map<Integer, TreatmentInfo> tiMap = new HashMap<>();
					tiMap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					satpecgList = getSkinAdhectionDetailsList(tiMap, ecgTimePoinsRowValues);
					if (satpecgList != null && satpecgList.size() > 0) {
						satpecgDtoList.addAll(satpecgList);
					}
				}
			}
		}
		//Finally Treatementwise StudyActivityTimpointsSavingDto ecgMap
		Map<Integer, List<StudyActivityTimpointsSavingDto>> twSatEcgMap = new HashMap<>();
		if(satpecgDtoList.size() > 0) {
			Collections.sort(satpecgDtoList);
			for(StudyActivityTimpointsSavingDto satp : satpecgDtoList) {
				if(twSatEcgMap.containsKey(satp.getTreatmentNo())) {
					List<StudyActivityTimpointsSavingDto>satsList = twSatEcgMap.get(satp.getTreatmentNo());
					satsList.add(satp);
					twSatEcgMap.put(satp.getTreatmentNo(), satsList);
				}else {
					List<StudyActivityTimpointsSavingDto>satsList = new ArrayList<>();
					satsList.add(satp);
					twSatEcgMap.put(satp.getTreatmentNo(), satsList);
				}
			}
		}
		//SkinAdhection
		//Converting treatmentwise map
		Map<Integer, ProjectsDetails> twSkinAdhectionMap = new HashMap<>();
			if (skinAdList != null && skinAdList.size() > 0) {
				for (ProjectsDetails pd : skinAdList) {
					if (pd.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString())) {
						if (!twSkinAdhectionMap.containsKey(Integer.parseInt(pd.getFieldValue())))
							twSkinAdhectionMap.put(Integer.parseInt(pd.getFieldValue()), pd);
					}
				}
			}
//		}
		//Geting saving data For StutyActivityTime Points For SkinAdhection
		List<StudyActivityTimpointsSavingDto> satpsDtoList = new ArrayList<>();
		List<StudyActivityTimpointsSavingDto> satpsList = null;
		if (twSkinAdhectionMap.size() > 0) {
			for (Map.Entry<Integer, ProjectsDetails> tesadhMap : twSkinAdhectionMap.entrySet()) {
				int treatmentNo = tesadhMap.getKey();
				if (treatmentNo == 0) {
					satpsList = getSkinAdhectionDetailsList(treatmentNoMap, skinAdList);
					if (satpsList != null && satpsList.size() > 0) {
						satpsDtoList.addAll(satpsList);
					}
				} else {
					Map<Integer, TreatmentInfo> tiMap = new HashMap<>();
					tiMap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					satpsList = getSkinAdhectionDetailsList(tiMap, skinAdList);
					if (satpsList != null && satpsList.size() > 0) {
						satpsDtoList.addAll(satpsList);
					}
				}
			}
		}
		//Finally Treatementwise StudyActivityTimpointsSavingDto skinAdhection
		Map<Integer, List<StudyActivityTimpointsSavingDto>> skinAdhFinalMap = new HashMap<>();
		if(satpsDtoList.size() > 0) {
			Collections.sort(satpsDtoList);
			for(StudyActivityTimpointsSavingDto satp : satpsDtoList) {
				if(skinAdhFinalMap.containsKey(satp.getTreatmentNo())) {
					List<StudyActivityTimpointsSavingDto>satsList = skinAdhFinalMap.get(satp.getTreatmentNo());
					satsList.add(satp);
					skinAdhFinalMap.put(satp.getTreatmentNo(), satsList);
				}else {
					List<StudyActivityTimpointsSavingDto>satsList = new ArrayList<>();
					satsList.add(satp);
					skinAdhFinalMap.put(satp.getTreatmentNo(), satsList);
				}
			}
		}
		//Skin Sescitivity
		//Converting treatmentwise map
		Map<Integer, ProjectsDetails> twSkinsenMap = new HashMap<>();
			if (skinSenList != null && skinSenList.size() > 0) {
				for (ProjectsDetails pd : skinSenList) {
					if (pd.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString())) {
						if (!twSkinsenMap.containsKey(Integer.parseInt(pd.getFieldValue())))
							twSkinsenMap.put(Integer.parseInt(pd.getFieldValue()), pd);
					}
				}
			}
//		}
		//Geting saving data For StutyActivityTime Points For SkinSensitivity
		List<StudyActivityTimpointsSavingDto> skinsenDtoList = new ArrayList<>();
		List<StudyActivityTimpointsSavingDto> skinsenList = null;
		if (twSkinsenMap.size() > 0) {
			for (Map.Entry<Integer, ProjectsDetails> tesenMap : twSkinsenMap.entrySet()) {
				int treatmentNo = tesenMap.getKey();
				if (treatmentNo == 0) {
					skinsenList = getSkinAdhectionDetailsList(treatmentNoMap, skinSenList);
					if (skinsenList != null && skinsenList.size() > 0) {
						skinsenDtoList.addAll(skinsenList);
					}
				} else {
					Map<Integer, TreatmentInfo> tiMap = new HashMap<>();
					tiMap.put(treatmentNo, treatmentNoMap.get(treatmentNo));
					satpsList = getSkinAdhectionDetailsList(tiMap, skinSenList);
					if (skinsenList != null && skinsenList.size() > 0) {
						skinsenDtoList.addAll(skinsenList);
					}
				}
			}
		}
		//Finally Treatementwise StudyActivityTimpointsSavingDto SkinSensitivity
		Map<Integer, List<StudyActivityTimpointsSavingDto>> skinSenFinalMap = new HashMap<>();
		if(skinsenDtoList.size() > 0) {
			Collections.sort(skinsenDtoList);
			for(StudyActivityTimpointsSavingDto satp : skinsenDtoList) {
				if(skinSenFinalMap.containsKey(satp.getTreatmentNo())) {
					List<StudyActivityTimpointsSavingDto>satsList = skinSenFinalMap.get(satp.getTreatmentNo());
					satsList.add(satp);
					skinSenFinalMap.put(satp.getTreatmentNo(), satsList);
				}else {
					List<StudyActivityTimpointsSavingDto>satsList = new ArrayList<>();
					satsList.add(satp);
					skinSenFinalMap.put(satp.getTreatmentNo(), satsList);
				}
			}
		}
		//OtherActivities
		List<OtherActivitiesDto> otherActWithTimePointParamList = new ArrayList<>();
		List<OtherActivitiesDto> otherActWithoutTimePointParamList = new ArrayList<>();
		if(otherActivityMap.size() > 0) {
			for(Map.Entry<Integer, List<ProjectsDetails>> otherActMap : otherActivityMap.entrySet()) {
				List<ProjectsDetails> pdList = otherActMap.getValue();
				if(pdList != null && pdList.size() > 0) {
					String actId = "";
					String timePoint = "";
					String treatment = "";
					String parameter = "";
					for(ProjectsDetails p : pdList) {
						if(p.getFieldName().equalsIgnoreCase(StudyDesign.ACTIVITY.toString())) {
							actId = p.getFieldValue();
						}else if(p.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString())) {
							treatment = p.getFieldValue();
						}else if(p.getFieldName().equalsIgnoreCase(StudyDesign.TIMEPOINT.toString())) {
							timePoint = p.getFieldValue();
						}else if(p.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
							parameter = p.getFieldValue();
						}
					}
					if(!actId.equals("") && !treatment.equals("") && !parameter.equals("")) {
						if(treatment.equals("0")) {
							String[] tpArr = timePoint.split("\\,");
							if(tpArr.length > 0) {
								for(String st : tpArr) {
									OtherActivitiesDto  oactDto = new OtherActivitiesDto();
									oactDto.setActivityId(Long.parseLong(actId));
									oactDto.setParameterId(Long.parseLong(parameter));
									oactDto.setTreatmentNo(Integer.parseInt(treatment));
									oactDto.setTimePoint(st);
									if(st.equals(""))
										otherActWithoutTimePointParamList.add(oactDto);
									else {
										oactDto.setTpVal(Double.parseDouble(st));
										oactDto.setTimePoint(st);
										otherActWithTimePointParamList.add(oactDto);
									}
								}
							}
						}else {
							String[] tpArr = timePoint.split("\\,");
							if(tpArr.length > 0) {
								for(String st : tpArr) {
									OtherActivitiesDto  oactDto = new OtherActivitiesDto();
									oactDto.setActivityId(Long.parseLong(actId));
									oactDto.setParameterId(Long.parseLong(parameter));
									oactDto.setTreatmentNo(Integer.parseInt(treatment));
									oactDto.setTimePoint(st);
									oactDto.setTpVal(Double.parseDouble(st));
									if(st.equals(""))
										otherActWithoutTimePointParamList.add(oactDto);
									else {
										oactDto.setTpVal(Double.parseDouble(st));
										oactDto.setTimePoint(st);
										otherActWithTimePointParamList.add(oactDto);
									}
								}
							}
						}
					}
				}
			}
		}
		clInfo.setSampleTimePoints(samptList);
		clInfo.setMealsTimePoints(mtpList);
		clInfo.setVitalTimePoints(vtimepointsList);
		clInfo.setTwSatEcgMap(twSatEcgMap);
		clInfo.setSkinAdhFinalMap(skinAdhFinalMap);
		clInfo.setSkinSenFinalMap(skinSenFinalMap);
		clInfo.setOtherActWithoutTimePointParamList(otherActWithoutTimePointParamList);
		clInfo.setOtherActWithTimePointParamList(otherActWithTimePointParamList);
		return clInfo;
	}

	private List<StudyActivityTimpointsSavingDto> getSkinAdhectionDetailsList(Map<Integer, TreatmentInfo> treatmentNoMap,
			List<ProjectsDetails> pdList) {
		List<StudyActivityTimpointsSavingDto> satpsList = new ArrayList<>();
		try {
			for (Map.Entry<Integer, TreatmentInfo> tmMap : treatmentNoMap.entrySet()) {
					List<String> timePointValues = new ArrayList<>();
					String windowPeriodSign = "";
					int windowPeriodValue = 0;
					String parameters = "";
					String windowPeriodTime ="";
					String postion ="";
					String orthoStaticPositnion = "";
					String orthoStatic ="";
					for (ProjectsDetails p : pdList) {
						if (p.getFieldName().equals("PARAMETERS")) {
							parameters = p.getFieldValue();
						}else if (p.getFieldName().equals(StudyDesign.TIMEPOINT.toString())) {
							String[] points = p.getFieldValue().split("\\,");
							for (String sts : points) {
								timePointValues.add(sts);
							}
						}else if(p.getFieldName().equalsIgnoreCase("WINDOWPERIODSIGN")) {
							windowPeriodSign = p.getFieldValue();
						}else if(p.getFieldName().equalsIgnoreCase("WINDOWPERIODVALUE") || p.getFieldName().equalsIgnoreCase("WINDOWPERIOD")) {
							windowPeriodValue = Integer.parseInt(p.getFieldValue());
						}else if(p.getFieldName().equalsIgnoreCase("WINDOWPERIODTIME")) {
							windowPeriodTime = p.getFieldValue();
						}else if(p.getFieldName().equalsIgnoreCase("POISITION")) {
							postion = p.getFieldValue();
						}
					}
					for (String timep : timePointValues) {
						StudyActivityTimpointsSavingDto sat = new StudyActivityTimpointsSavingDto();
						sat.setTreatmentNo(Integer.parseInt(tmMap.getValue().getTreatmentNo()));
						if(timep.contains("-")) {
							String tp = timep.replace("-", "");
							sat.setTimePoint(tp);
							sat.setSign("-");
						}else sat.setTimePoint(timep);
						sat.setOrthoStaticPosition(orthoStaticPositnion);
						sat.setParameters(parameters);
						sat.setPosition(postion);
						sat.setOrthoStatic(orthoStatic);
						sat.setWindowPeriod(windowPeriodValue);
						sat.setWindowperiodSign(windowPeriodSign);
						sat.setWindowPeriodType(windowPeriodTime);
						satpsList.add(sat);
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return satpsList;
	}
	
	private List<VitalTimePoints> vititalTimpointsRecords(Map<Integer, TreatmentInfo> treatmentNoMap, StudyMaster study,
			List<ProjectsDetails> proList) {
		List<VitalTimePoints> vtpList = new ArrayList<>();
		try {   
				List<VitalTimePoints> preDoseVital = new ArrayList<>();
				List<VitalTimePoints> postDoseVital = new ArrayList<>();
				List<VitalTimePoints> ambulatoryVital = new ArrayList<>();
				Map<Integer, List<ProjectsDetails>> subRowMap = new HashMap<>();
				List<ProjectsDetails> tempPdList = null;
				for (ProjectsDetails p : proList) {
					if(subRowMap.containsKey(p.getSubRowNo())) {
						tempPdList = subRowMap.get(p.getSubRowNo());
						tempPdList.add(p);
						subRowMap.put(p.getSubRowNo(), tempPdList);
					}else {
						tempPdList = new ArrayList<>();
						tempPdList.add(p);
						subRowMap.put(p.getSubRowNo(), tempPdList);
					}
				}
				if(subRowMap.size() > 0) {
					List<ProjectsDetails> pdList = null;
					FromStaticData timepointType = null;
					List<String> timePointValues = new ArrayList<>();
					FromStaticData positionId = null;
					boolean orthostatic = false;
					String parmIds = "";
					FromStaticData windowPeriodType = null;
					int windowPeriod = 0;
					FromStaticData orthopositionId = null;
					String windowPeriodSign = "";
					for(Map.Entry<Integer, List<ProjectsDetails>> pdMap : subRowMap.entrySet()) {
						pdList = pdMap.getValue();
						timepointType = null;
						timePointValues = new ArrayList<>();
						positionId = null;
						orthostatic = false;
						parmIds = "";
						windowPeriodType = null;
						windowPeriod = 0;
						orthopositionId = null;
						windowPeriodSign = "";
						if(pdList != null && pdList.size() > 0) {
							for(ProjectsDetails p : pdList) {
								if (p.getFieldName().equalsIgnoreCase(StudyDesign.VITALPOSITION.toString())) {
									positionId = studyCreationDao.formStaticData(StudyDesign.VITALPOSITION.toString(),
											p.getFieldValue());
								} else if (p.getFieldName().equals(StudyDesign.TIMEPOINT.toString())) {
									String[] points = p.getFieldValue().split("\\,");
									for (String sts : points) {
										timePointValues.add(sts);
									}
								} else if (p.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETER.toString())) {
									parmIds = p.getFieldValue();
								} else if (p.getFieldName().equals(StudyDesign.WINDOWPERIODTYPE.toString())) {
									windowPeriodType = studyCreationDao.formStaticData(StudyDesign.WINDOWPERIODTYPE.toString(),
											p.getFieldValue());
								} else if (p.getFieldName().equals(StudyDesign.WINDOWPERIOD.toString())) {
									windowPeriod = Integer.parseInt(p.getFieldValue());
								} else if (p.getFieldName().equals(StudyDesign.WINDOWPERIODSIGN.toString())) {
									windowPeriodSign = p.getFieldValue();
								} else if (p.getFieldName().equals(StudyDesign.ORTHOSTATIC.toString())) {
									if (p.getFieldValue().equals(StaticData.YES.toString())) {
										orthostatic = true;
									}
								} else if (p.getFieldName().equals(StudyDesign.TESTNAMES.toString())) {
									orthopositionId = studyCreationDao.formStaticData(StudyDesign.VITALPOSITION.toString(),
											p.getFieldValue());
								}
							}
							if (timePointValues.size() > 0) {
								for(Map.Entry<Integer, TreatmentInfo> twMap : treatmentNoMap.entrySet()) {
									for (String timep : timePointValues) {
										VitalTimePoints vital = new VitalTimePoints();
										vital.setStudy(study);
										vital.setCreatedBy(study.getCreatedBy());
										vital.setCreatedOn(study.getCreatedOn());
										vital.setTreatmentInfo(twMap.getValue());
										vital.setTimePointType(timepointType);
										vital.setTimePoint(timep);
										vital.setParameterIds(parmIds);
										vital.setOrthostaticPosition(orthopositionId);
										vital.setVitalPosition(positionId);
										if (timep.contains("-")) {
											vital.setSign("-");
											vital.setTimePoint(timep.replace("-", ""));
											vital.setTimePointType(studyCreationDao.formStaticData("TIMEPOINTTYPE",
													StaticData.PREDOSE.toString()));
										} else {
											vital.setTimePointType(studyCreationDao.formStaticData("TIMEPOINTTYPE",
													StaticData.POSTDOSE.toString()));
										}
										vital.setWindowPeriodSign(windowPeriodSign);
										vital.setWindowPeriod(windowPeriod);
										vital.setWindowPeriodType(windowPeriodType);
										vital.setOrthostatic(orthostatic);
										vital.setVtpVal(Double.parseDouble(vital.getSign()+vital.getTimePoint()));
										if (vital.getTimePointType() != null) {
											if (vital.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
												vital.setSign("-");
												preDoseVital.add(vital);
											} else if (vital.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString())) {
												postDoseVital.add(vital);
											} else if (vital.getTimePointType().getCode()
													.equals(StaticData.AMBULATORY.toString())) {
												ambulatoryVital.add(vital);
											}
										} else {
											if (vital.getSign().equals(""))
												postDoseVital.add(vital);
											else
												preDoseVital.add(vital);
										}
									}
								}
							}
							List<VitalTimePoints> vtList = allVitalTimepointsWithOrder(preDoseVital, postDoseVital,
									ambulatoryVital);
							if (vtList != null && vtList.size() > 0) {
								vtpList.clear();
								vtpList.addAll(vtList);
							}
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vtpList;
	}

	private List<SampleTimePoints> getSampleTimpointsRecords(Map<Integer, TreatmentInfo> treatmentNoMap,
			StudyMaster study, List<ProjectsDetails> timePointDetails) {
		List<SampleTimePoints> stpList = new ArrayList<>();
		List<SampleTimePoints> preDoseSamples = new ArrayList<>();
		List<SampleTimePoints> postDoseSamples = new ArrayList<>();
		List<SampleTimePoints> ambulatorySamples = new ArrayList<>();
		try {
			Map<Integer, List<ProjectsDetails>> rowWise = new HashMap<>();
			for (ProjectsDetails p : timePointDetails) {
				List<ProjectsDetails> list = rowWise.get(p.getSubRowNo());
				if(list == null) {
					list = new ArrayList<>();
				}
				list.add(p);
				rowWise.put(p.getSubRowNo(), list);
			}

			FromStaticData timepointType = null;
			List<String> timePointValues = new ArrayList<>();
			int noOfVacutainer = 0;
			int noOfVials = 0;
			String windowPeriodSign = "";
			int windowPeriod = 0;
			FromStaticData windowPeriodType = null;
			for(Map.Entry<Integer, List<ProjectsDetails>> mp : rowWise.entrySet()) {
				timepointType = null;
				timePointValues = new ArrayList<>();
				noOfVacutainer = 0;noOfVials = 0; windowPeriod = 0;
				windowPeriodSign = "";
				windowPeriodType = null;
				List<ProjectsDetails> timePoints = mp.getValue();
				for (ProjectsDetails p : timePoints) {
					if (p.getFieldName().equals(StudyDesign.TIMEPOINT.toString())) {
						String[] points = p.getFieldValue().split("\\,");
						for (String sts : points) {
//									timePointValues.add(sts.replace("-", ""));
							timePointValues.add(sts);
						}
					} else if (p.getFieldName().equals(StudyDesign.NOOFVACUTAINERS.toString())) {
						noOfVacutainer = Integer.parseInt(p.getFieldValue());
					}else if (p.getFieldName().equals(StudyDesign.WINDOWPERIOD.toString())) {
						windowPeriod = Integer.parseInt(p.getFieldValue());
					} else if (p.getFieldName().equals(StudyDesign.WINDOWPERIODSIGN.toString())) {
						windowPeriodSign = p.getFieldValue();
					} else if (p.getFieldName().equals(StudyDesign.WINDOWPERIODTYPE.toString())) {
						windowPeriodType = studyCreationDao.formStaticData(StudyDesign.WINDOWPERIODTYPE.toString(),
								p.getFieldValue());
					}else if (p.getFieldName().equalsIgnoreCase("NOOFVAILSFORSAMPLESEPARATION")) {
						if(!p.getFieldValue().equals(""))
							noOfVials = Integer.parseInt(p.getFieldValue());
					}
				}
				for (Map.Entry<Integer, TreatmentInfo> tmap : treatmentNoMap.entrySet()) {
					for (String timep : timePointValues) {
						for(int vacutinerNo =1; vacutinerNo <= noOfVacutainer; vacutinerNo++) {
							SampleTimePoints sample = new SampleTimePoints();
							sample.setStudy(study);
							sample.setCreatedBy(study.getCreatedBy());
							sample.setCreatedOn(study.getCreatedOn());
							sample.setTreatmentInfo(tmap.getValue());
							sample.setTimePointType(timepointType);
							sample.setTimePoint(timep);
							if (timep.contains("-")) {
								sample.setTimePoint(timep.replace("-", ""));
								sample.setSign("-");
								sample.setTimePointType(
										studyCreationDao.formStaticData("Pre Dose", StaticData.PREDOSE.toString()));
							} else {
								sample.setTimePointType(
										studyCreationDao.formStaticData("Post Dose", StaticData.POSTDOSE.toString()));
							}
							sample.setWindowPeriodSign(windowPeriodSign);
							sample.setWindowPeriod(windowPeriod);
							sample.setWindowPeriodType(windowPeriodType);
							sample.setNoOfVacutainer(noOfVacutainer);
							sample.setVacutainerNo(vacutinerNo);
							sample.setNoOfVials(noOfVials);
							if (sample.getTimePointType() != null) {
								if (sample.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
									preDoseSamples.add(sample);
								} else if (sample.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString())) {
									postDoseSamples.add(sample);
								} else if (sample.getTimePointType().getCode().equals(StaticData.AMBULATORY.toString())) {
									ambulatorySamples.add(sample);
								}
							} else {
								if (sample.getSign().equals(""))
									postDoseSamples.add(sample);
								else
									preDoseSamples.add(sample);
							}
						}
					}
					List<SampleTimePoints> spListTemp = allSampleTimepointsWithOrder(preDoseSamples, postDoseSamples,ambulatorySamples);
					if (spListTemp != null && spListTemp.size() > 0) {
						stpList.clear();
						stpList.addAll(spListTemp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stpList;
	}

	private List<MealsTimePoints> getMealsTimePoints(Map<Integer, TreatmentInfo> treatmentMap,
			List<ProjectsDetails> proList, StudyMaster study) {
		List<MealsTimePoints> mtList = new ArrayList<>();
		List<MealsTimePoints> preDoseMeals = new ArrayList<>();
		List<MealsTimePoints> postDoseMeals = new ArrayList<>();
		try {
			for (Map.Entry<Integer, TreatmentInfo> tmap : treatmentMap.entrySet()) {
					if (proList != null && proList.size() > 0) {
						FromStaticData preDose = studyCreationDao.formStaticData(StudyDesign.TIMEPOINTTYPE.toString(),
								StaticData.PREDOSE.toString());
						FromStaticData postDose = studyCreationDao.formStaticData(StudyDesign.TIMEPOINTTYPE.toString(),
								StaticData.POSTDOSE.toString());
						Map<Integer, Map<String, String>> mealMap = new HashMap<>();
						Map<String, String> tempMap = null;
						for (ProjectsDetails p : proList) {
							if(mealMap.containsKey(p.getSubRowNo())) {
								tempMap = mealMap.get(p.getSubRowNo());
								tempMap.put(p.getFieldName(), p.getFieldValue());
								mealMap.put(p.getSubRowNo(), tempMap);
							}else {
								tempMap = new HashMap<>();
								tempMap.put(p.getFieldName(), p.getFieldValue());
								mealMap.put(p.getSubRowNo(), tempMap);
							}
						}
						
						if(mealMap != null && mealMap.size() > 0) {
							for(Map.Entry<Integer, Map<String, String>> map : mealMap.entrySet()) {
								Map<String, String> meals = map.getValue();
								int windowPeriod  = 0;
								int completionTime = 0;
								if(meals != null && meals.size() > 0) {
									FromStaticData mealType = studyCreationDao.formStaticData(StudyDesign.MEALSTTYPE.toString(),
											meals.get(StudyDesign.MEALSTTYPE.toString()));
									String tpStr = meals.get(StudyDesign.TIMEPOINT.toString());
									if(meals.get(StudyDesign.WINDOWPERIOD.toString()) != null && !meals.get(StudyDesign.WINDOWPERIOD.toString()).equals("")) {
										windowPeriod  = Integer.parseInt(meals.get(StudyDesign.WINDOWPERIOD.toString()));
									}
									if(meals.get(StudyDesign.COMPLETIONTIME.toString()) != null && !meals.get(StudyDesign.COMPLETIONTIME.toString()).equals("")) {
										completionTime = Integer.parseInt(meals.get(StudyDesign.COMPLETIONTIME.toString()));
									}
									String windowPeriodSign = meals.get(StudyDesign.WINDOWPERIODSIGN.toString());
									FromStaticData completionType = studyCreationDao.formStaticData(StudyDesign.COMPLETIONTYPE.toString(),meals.get(StudyDesign.COMPLETIONTYPE.toString()));
									if (tpStr != null && !tpStr.equals("")) {
										String[] points = tpStr.split("\\,");
										for (String sts : points) {
											MealsTimePoints meal = new MealsTimePoints();
											meal.setStudy(study);
											meal.setCreatedBy(study.getCreatedBy());
											meal.setCreatedOn(study.getCreatedOn());
											meal.setTreatmentInfo(tmap.getValue());
											meal.setMealsType(mealType);
											meal.setTimePoint(sts);
											if (sts.contains("-")) {
												meal.setSign("-");
												meal.setTimePoint(sts.replace("-", ""));
											} else 
												meal.setSign("");
											
											meal.setWindowPeriodSign(windowPeriodSign);
											meal.setWindowPeriod(windowPeriod);
											meal.setCompletion(completionType);
											meal.setCompletionTime(completionTime);
											if (meal.getSign() != null && meal.getSign() != "") {
												meal.setTimePointType(preDose);
												preDoseMeals.add(meal);
											} else {
												meal.setTimePointType(postDose);
												postDoseMeals.add(meal);
											}
											System.out.println("Time point : "+meal.getSign()+meal.getTimePoint()+" \t Meal Type :"+meal.getMealsType().getCode());
										}
									} 
	
								}
							}
						}
					}
			}
			List<MealsTimePoints> mListTemp = allMealsTimepointsWithOrder(preDoseMeals, postDoseMeals);
			if (mListTemp != null && mListTemp.size() > 0)
				mtList.addAll(mListTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtList;
	}

	private Map<Integer, List<ProjectsDetails>> subRowWiseFilter(List<ProjectsDetails> criterialist) {
		Map<Integer, List<ProjectsDetails>> pdMap = new HashMap<>();
		List<ProjectsDetails> pdList = null;
		for (ProjectsDetails p : criterialist) {

			if (pdMap.containsKey(p.getSubRowNo())) {
				pdList = pdMap.get(p.getSubRowNo());
				pdList.add(p);
				pdMap.put(p.getSubRowNo(), pdList);
			} else {
				pdList = new ArrayList<>();
				pdList.add(p);
				pdMap.put(p.getSubRowNo(), pdList);
			}

		}
		return pdMap;
	}

	private Map<Integer, List<ProjectsDetails>> restrictionCompliance(List<ProjectsDetails> restrictionCompliancelist) {
		Map<Integer, List<ProjectsDetails>> rowWiseData = new HashMap<>();
		for (ProjectsDetails p : restrictionCompliancelist) {
			List<ProjectsDetails> lis = rowWiseData.get(p.getSubRowNo());
			if (lis == null)
				lis = new ArrayList<>();
			lis.add(p);
			rowWiseData.put(p.getSubRowNo(), lis);
		}
		return rowWiseData;
	}

	private SampleProcessingAndStorage sampleProcessingAndStorage(StudyMaster study,
			List<ProjectsDetails> sampleProcessinglist) {
		SampleProcessingAndStorage spas = null;
		//Centrefication Dtos
		StudySampleCentrifugationDto sscDto = new StudySampleCentrifugationDto();
		List<StudySampleCentrifugationDetailsDto> sscdDtoList = new ArrayList<>();
		//Processing Dtos
		StudySampleProcessingDto sspDto = new StudySampleProcessingDto();;
		List<StudySampleProcessingDetailsDto> sspDtoList = new ArrayList<>();
		//Strage Dtos
		StudySampleStorageDto sssDto = new StudySampleStorageDto();
		List<StudySampleStorageDetailsDto> sssdDtoList = new ArrayList<>();
		
		if(sampleProcessinglist != null && sampleProcessinglist.size() > 0) {
			spas = new SampleProcessingAndStorage();
			for(ProjectsDetails pd : sampleProcessinglist) {
				//Centrification
				if(pd.getFieldName().equals("centrifugationApplicable")) {
					String centrifugationApplicable = pd.getFieldValue();
					if(centrifugationApplicable.equals("Yes"))
						sscDto.setCentrifugationApplicable(true);
					else sscDto.setCentrifugationApplicable(false);
				}
				if(pd.getFieldName().equals("isStorageDifferent")) {
					String storage = pd.getFieldValue();
					if(storage.equals("Yes"))
						sssDto.setStorageDifferent(true);
					else sssDto.setStorageDifferent(false);
				}
			}
			//Centrification Child Details Purpose
			 StudySampleCentrifugationDetailsDto sscdDto = new StudySampleCentrifugationDetailsDto();
			 StudySampleProcessingDetailsDto sspdDto = new StudySampleProcessingDetailsDto();
			 StudySampleStorageDetailsDto sssdDto = new StudySampleStorageDetailsDto();
			for(ProjectsDetails pdc : sampleProcessinglist) {
				if(pdc.getFieldName().equals("centrifugationApplicableTo")) {
					sscdDto.setApplicableTo(pdc.getFieldValue());
				}else if(pdc.getFieldName().equals("centrifugationSpeed")) {
					sscdDto.setCentrifugationSpeed(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("centrifugationProcessTime")) {
					sscdDto.setCentrifugationProcessTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("centrifugationTemparature")) {
					sscdDto.setCentrifugationTemparature(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("centrifugationAllowedTime")) {
					sscdDto.setCentrifugationAllowedTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("centrifugationAllowedTime")) {
					sscdDto.setCentrifugationAllowedTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("")) {
					String condition = sscdDto.getConditions();
					if(condition == null || condition.equals(""))
						sscdDto.setConditions(pdc.getFieldValue());
					else sscdDto.setConditions(condition+","+pdc.getFieldValue());
				}
				
				
				// Processing 
				if(pdc.getFieldName().equals("processingAllowedTime")) {
					sspDto.setAllowedTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("processingAllowedTime")) {
					sspDto.setAllowedTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("")) {
					sspDto.setAllowedTimeFrom(pdc.getFieldValue());
				}else if(pdc.getFieldName().equals("")) {
					String condition = sscdDto.getConditions();
					if(condition == null || condition.equals(""))
						sspDto.setConditions(pdc.getFieldValue());
					else sspDto.setConditions(condition+","+pdc.getFieldValue());
				}else if(pdc.getFieldName().equals("isMatrixDifferent")) {
					String isMatrixDifferent = pdc.getFieldValue();
					if(isMatrixDifferent.equals("Yes"))
						sspDto.setMatrixDifferent(true);
					else sspDto.setMatrixDifferent(false);
				}
				
				//Processing Details
				if(pdc.getFieldName().equals("aliquotVolume")) {
					sspdDto.setAliquotVolume(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("aliquotMatrix")) {
					sspdDto.setConditon(Long.parseLong(pdc.getFieldValue()));
				}
				
				//Storage Details
				if(pdc.getFieldName().equals("storageCondition")) {
					sssdDto.setStorageCondition(Long.parseLong(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("storageAllowedTime")) {
					sssdDto.setAllowedTime(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("storageTemperature")) {
					sssdDto.setTemperature(Integer.parseInt(pdc.getFieldValue()));
				}else if(pdc.getFieldName().equals("storageTimepointCondition")) {
					sssdDto.setTimePointCondition(Long.parseLong(pdc.getFieldValue()));
				}
				
			}
			if(sscdDto != null) {
				sscdDtoList.add(sscdDto);
			}
			if(sspdDto != null)
				sspDtoList.add(sspdDto);
			if(sssdDto != null)
				sssdDtoList.add(sssdDto);
			
			spas.setSscdDtoList(sscdDtoList); 
			spas.setSscDto(sscDto);
			spas.setSspDto(sspDto);
			spas.setSspDtoList(sspDtoList);
			spas.setSssdDtoList(sssdDtoList);
			spas.setSssDto(sssDto);
		}
		return spas;
	}

	
	@SuppressWarnings("unused")
	private List<AdditionalAssesment> additionalAssesmentTimePoints(StudyMaster study,
			List<ProjectsDetails> additionalAssesmentlist) {
		Map<Integer, List<ProjectsDetails>> timepoints = new HashMap<>();

		for (ProjectsDetails p : additionalAssesmentlist) {
			List<ProjectsDetails> lis = timepoints.get(p.getSubRowNo());
			if (lis == null)
				lis = new ArrayList<>();
			lis.add(p);
			timepoints.put(p.getSubRowNo(), lis);
		}

		List<AdditionalAssesment> preDoseAdditionalAssesment = new ArrayList<>();
		List<AdditionalAssesment> postDoseAdditionalAssesment = new ArrayList<>();
		List<AdditionalAssesment> ambulatoryAdditionalAssesment = new ArrayList<>();
		for (Map.Entry<Integer, List<ProjectsDetails>> timepoint : timepoints.entrySet()) {
			List<ProjectsDetails> timePointDetails = timepoint.getValue();
			FromStaticData timepointType = null;
			List<String> timePointValues = new ArrayList<>();
			String title = null;
			String windowPeriodSign = null;
			int windowPeriod = 0;
			FromStaticData windowPeriodType = null;
			for (ProjectsDetails p : timePointDetails) {
				System.out.println(p.getFieldName() + "\t" + p.getFieldValue());
			}
			for (ProjectsDetails p : timePointDetails) {
				if (StudyDesign.TITLE.toString().equals(p.getFieldName())) {
					title = p.getFieldValue();
				} else if (StudyDesign.TIMEPOINTTYPE.toString().equals(p.getFieldName())) {
					timepointType = studyCreationDao.formStaticData(StudyDesign.TIMEPOINTTYPE.toString(),
							p.getFieldValue());
				} else if (p.getFieldName().equals(StudyDesign.TIMEPOINT.toString())) {
					String[] points = p.getFieldValue().split("\\,");
					for (String sts : points) {
						timePointValues.add(sts.replace("-", ""));
					}
				} else if (StudyDesign.WINDOWPERIODSIGN.toString().equals(p.getFieldName())) {
					windowPeriodSign = p.getFieldValue();
				} else if (StudyDesign.WINDOWPERIOD.toString().equals(p.getFieldName())) {
					windowPeriod = Integer.parseInt(p.getFieldValue());
				} else if (StudyDesign.WINDOWPERIODTYPE.toString().equals(p.getFieldName())) {
					windowPeriodType = studyCreationDao.formStaticData(StudyDesign.WINDOWPERIODTYPE.toString(),
							p.getFieldValue());
				}
			}

			for (String timep : timePointValues) {
				AdditionalAssesment aa = new AdditionalAssesment();
				aa.setStudy(study);
				aa.setTitle(title);
				aa.setTimePointType(timepointType);
				aa.setTimePoint(timep);
				aa.setWindowPeriodSign(windowPeriodSign);
				aa.setWindowPeriod(windowPeriod);
				aa.setWindowPeriodType(windowPeriodType);
				if (aa.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
					aa.setSign("-");
					preDoseAdditionalAssesment.add(aa);
				} else if (aa.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString())) {
					postDoseAdditionalAssesment.add(aa);
				} else if (aa.getTimePointType().getCode().equals(StaticData.AMBULATORY.toString())) {
					ambulatoryAdditionalAssesment.add(aa);
				}
			}
		}

		List<AdditionalAssesment> additionalAssesment = allAdditionalAssesmentTimepointsWithOrder(
				preDoseAdditionalAssesment, postDoseAdditionalAssesment, ambulatoryAdditionalAssesment);
		return additionalAssesment;
	}

	private List<AdditionalAssesment> allAdditionalAssesmentTimepointsWithOrder(
			List<AdditionalAssesment> preDoseAdditionalAssesment, List<AdditionalAssesment> postDoseAdditionalAssesment,
			List<AdditionalAssesment> ambulatoryAdditionalAssesment) {
		List<String> samples = new ArrayList<>();
		int count = 0;
		for (AdditionalAssesment sp : preDoseAdditionalAssesment) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		Collections.sort(samples, Collections.reverseOrder());
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (AdditionalAssesment sp : preDoseAdditionalAssesment) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		samples = new ArrayList<>();
		for (AdditionalAssesment sp : postDoseAdditionalAssesment) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (AdditionalAssesment sp : postDoseAdditionalAssesment) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}
		samples = new ArrayList<>();
		for (AdditionalAssesment sp : ambulatoryAdditionalAssesment) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (AdditionalAssesment sp : ambulatoryAdditionalAssesment) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		List<AdditionalAssesment> allAdditionalAssesment = new ArrayList<>(preDoseAdditionalAssesment);
		allAdditionalAssesment.addAll(postDoseAdditionalAssesment);
		allAdditionalAssesment.addAll(ambulatoryAdditionalAssesment);
		return allAdditionalAssesment;
	}

	private List<VitalTimePoints> allVitalTimepointsWithOrder(List<VitalTimePoints> preDoseVital,
			List<VitalTimePoints> postDoseVital, List<VitalTimePoints> ambulatoryVital) {
		List<String> samples = new ArrayList<>();
		int count = 0;
		for (VitalTimePoints sp : preDoseVital) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		Collections.sort(samples, Collections.reverseOrder());
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (VitalTimePoints sp : preDoseVital) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		samples = new ArrayList<>();
		for (VitalTimePoints sp : postDoseVital) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (VitalTimePoints sp : postDoseVital) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}
		samples = new ArrayList<>();
		for (VitalTimePoints sp : ambulatoryVital) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (VitalTimePoints sp : ambulatoryVital) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		List<VitalTimePoints> allVitals = new ArrayList<>(preDoseVital);
		allVitals.addAll(postDoseVital);
		allVitals.addAll(ambulatoryVital);
		return allVitals;
	}


	private List<MealsTimePoints> allMealsTimepointsWithOrder(List<MealsTimePoints> preDoseMeals,
			List<MealsTimePoints> postDoseMeals) {
		List<String> samples = new ArrayList<>();
		int count = 0;
		for (MealsTimePoints sp : preDoseMeals) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		Collections.sort(samples, Collections.reverseOrder());
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (MealsTimePoints sp : preDoseMeals) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		samples = new ArrayList<>();
		for (MealsTimePoints sp : postDoseMeals) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (MealsTimePoints sp : postDoseMeals) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		List<MealsTimePoints> allMeals = new ArrayList<>(preDoseMeals);
		allMeals.addAll(postDoseMeals);
		return allMeals;
	}

	public DosingParameterDto getStudyDoseParameters(List<ProjectsDetails> doseparamList) {
		DosingParameterDto dpDto = new DosingParameterDto();
		Map<Integer, List<Long>> timePointParmMap = new HashMap<>();
		Map<Integer, List<Long>> nonTimePointParmMap = new HashMap<>();
		if(doseparamList != null && doseparamList.size() > 0) {
			String treatment = "";
			String timePointSpecific = "";
			String parameterid = "";
			for(ProjectsDetails pd : doseparamList) {
				List<Long> tempPdIds = null;
				if(pd.getFieldName().equalsIgnoreCase(StudyDesign.TREATMENT.toString()))
					treatment = pd.getFieldValue();
				else if(pd.getFieldName().equalsIgnoreCase(StudyDesign.TIMEPOINT.toString()))
					timePointSpecific = pd.getFieldValue();
				else if(pd.getFieldName().equalsIgnoreCase(StudyDesign.PARAMETERDESC.toString()))
					parameterid = pd.getFieldValue();
				if(!treatment.equals("") && !parameterid.equals("")) {
					if(!timePointSpecific.trim().equals("") && !timePointSpecific.trim().equalsIgnoreCase("1")) {
						if(timePointParmMap.containsKey(Integer.parseInt(treatment))) {
							tempPdIds = timePointParmMap.get(Integer.parseInt(treatment));
							tempPdIds.add(Long.parseLong(parameterid));
							timePointParmMap.put(Integer.parseInt(treatment), tempPdIds);
						}else {
							tempPdIds = new ArrayList<>();
							tempPdIds.add(Long.parseLong(parameterid));
							timePointParmMap.put(Integer.parseInt(treatment), tempPdIds);
						}
					}else {
						if(nonTimePointParmMap.containsKey(Integer.parseInt(treatment))) {
							tempPdIds = nonTimePointParmMap.get(Integer.parseInt(treatment));
							tempPdIds.add(Long.parseLong(parameterid));
							nonTimePointParmMap.put(Integer.parseInt(treatment), tempPdIds);
						}else {
							tempPdIds = new ArrayList<>();
							tempPdIds.add(Long.parseLong(parameterid));
							nonTimePointParmMap.put(Integer.parseInt(treatment), tempPdIds);
						}
					}
					treatment = "";
					timePointSpecific = "";
					parameterid = "";
				}
			}
		}
		dpDto.setNonTimePointParmMap(nonTimePointParmMap);
		dpDto.setTimePointParmMap(timePointParmMap);
		return dpDto;
	}

	/*
	 * Dosing Time Points with treatment wise assigning to DosingTimePoints Pojo
	 * For that we are using treatmentNo wise filtered map projectDetails  
	 * Again treatmentNo filtermap will be filtered with subRowNo  map and iterate that projectDetails and assigning to DosingTimePoints
	 */
	public List<DoseTimePoints> doseTimePoints(Map<Integer, TreatmentInfo> treatments,
			Map<Integer, List<ProjectsDetails>> dosingRowValues, StudyMaster study, StatusMaster activeStatus, Map<Integer, List<Long>> timePointDoseParamMap) {
		List<DoseTimePoints> doseTimePoints = new ArrayList<>();
		
		if (dosingRowValues.size() > 0) {
			for (Map.Entry<Integer, List<ProjectsDetails>> proMap : dosingRowValues.entrySet()) {
				int treatmentNo = proMap.getKey();
				List<DoseTimePoints> doseList = null;
				if (dosingRowValues.size() > 0) {
					if (treatmentNo == 0) {
						List<Long> doseParams = timePointDoseParamMap.get(treatmentNo);
						doseList = getDoseTimePointsRecordsForAllTreatments(treatments, dosingRowValues, study,
								activeStatus, doseParams, treatmentNo);
						if (doseList != null && doseList.size() > 0)
							doseTimePoints.addAll(doseList);
					} else {
						Map<Integer, TreatmentInfo> tmap = new HashMap<>();
						tmap.put(treatmentNo, treatments.get(treatmentNo));
						List<Long> doseParams = timePointDoseParamMap.get(treatmentNo);
						doseList = getDoseTimePointsRecordsForAllTreatments(tmap, dosingRowValues, study,
								activeStatus, doseParams, treatmentNo);
						if (doseList != null && doseList.size() > 0)
							doseTimePoints.addAll(doseList);
					}
				}
			}
		}
		return doseTimePoints;
	}

	private List<DoseTimePoints> getDoseTimePointsRecordsForAllTreatments(Map<Integer, TreatmentInfo> tmMap,
			Map<Integer, List<ProjectsDetails>> dosingRowValues, StudyMaster study, StatusMaster activeStatus, List<Long> doseParams, int treatmentNo) {
		List<DoseTimePoints> doseList = new ArrayList<>();
		try {
			String dosingParameters = "";
			if(doseParams != null && doseParams.size() > 0) {
				for(Long dp : doseParams) {
					if(dosingParameters.equals(""))
						dosingParameters = dp+"";
					else dosingParameters = dosingParameters +","+dp;
				}
			}
			if (tmMap.size() > 0) {
				for (Map.Entry<Integer, TreatmentInfo> tm : tmMap.entrySet()) {
					List<ProjectsDetails> proList = null;
					if(treatmentNo == 0)
						proList = dosingRowValues.get(treatmentNo);
					else proList = dosingRowValues.get(tm.getKey());
					Map<Integer, List<ProjectsDetails>> subRowMap = new HashMap<>();
					List<ProjectsDetails> tempList = null;
					//Dividing ProjectDetails Details as Sub Row No Wise
					for (ProjectsDetails pl : proList) {
						if(pl.getSubRowNo() != 0) {
							if(subRowMap.containsKey(pl.getSubRowNo())) {
								 tempList = subRowMap.get(pl.getSubRowNo());
								 tempList.add(pl);
								 subRowMap.put(pl.getSubRowNo(), tempList);
							 }else {
								 tempList = new ArrayList<>();
								 tempList.add(pl);
								 subRowMap.put(pl.getSubRowNo(), tempList);
							 }
						}
					}
					int count =1;
					for(Map.Entry<Integer, List<ProjectsDetails>> proMap : subRowMap.entrySet()) {
						List<ProjectsDetails> pdList = proMap.getValue();
						DoseTimePoints dtp = new DoseTimePoints();
						dtp.setStudy(study);
						dtp.setActiveStatus(activeStatus);
						dtp.setCreatedBy(study.getCreatedBy());
						dtp.setCreatedOn(study.getCreatedOn());
						dtp.setParameters(dosingParameters);
						String fedCriteriaDuration = "";
						TreatmentInfo ti = tm.getValue();
						if(ti != null) {
							dtp.setTreatmentInfo(ti);
							int noOfSachet = ti.getNoOfSachetLables();
							dtp.setNoOfSachet(noOfSachet);
						}
						for(ProjectsDetails pl : pdList) {
							if (pl.getFieldName().equals(StudyDesign.TREATMENT.toString())) {
								/*TreatmentInfo ti = tm.getValue();
								if(ti != null) {
									dtp.setTreatmentInfo(ti);
									int noOfSachet = ti.getNoOfSachetLables();
									dtp.setNoOfSachet(noOfSachet);
								}*/
							} else if (pl.getFieldName().equals("doseTimePoint")) {
								if(pl.getFieldValue() != null && !pl.getFieldValue().equals("")) {
									dtp.setTimePoint(pl.getFieldValue());
									dtp.setDosetp(Double.parseDouble(pl.getFieldValue()));
								}
							} else if (pl.getFieldName().equals("fastingCriteria")) {
								dtp.setFastingCriteria(pl.getFieldValue());
							} else if (pl.getFieldName().equals("fastingCriteriaSign")) {
								dtp.setFastingCriteriaType(studyCreationDao
										.formStaticData(StudyDesign.WINDOWPERIODSIGN.toString(), pl.getFieldValue()));
							} else if (pl.getFieldName().equals("fedCriteria")) {
								dtp.setFedCriteria(pl.getFieldValue());
							} else if (pl.getFieldName().equals("fedCriteriaSign")) {
								dtp.setFedcriteriaType(studyCreationDao
										.formStaticData(StudyDesign.WINDOWPERIODSIGN.toString(), pl.getFieldValue()));
							} else if (pl.getFieldName().equals("fedCriteriaDuration")) {
								fedCriteriaDuration = pl.getFieldValue();
							} else if (pl.getFieldName().equals("dosingWindowPeriodSign")) {
								dtp.setWindowPeriodSign(pl.getFieldValue());
							} else if (pl.getFieldName().equals("dosingWindowPeriod")) {
								dtp.setWindowPeriod(Integer.parseInt(pl.getFieldValue()));
							} else if (pl.getFieldName().equals("dosingWindowPeriodTime")) {
								dtp.setWindowPeriodType(studyCreationDao
										.formStaticData(StudyDesign.WINDOWPERIODTYPE.toString(), pl.getFieldValue()));
							}
							if (fedCriteriaDuration.equals(StaticData.MINUTES.toString())) {
								dtp.setFedCriteria((Double.parseDouble(dtp.getFedCriteria()) / 60) + "");
							} else if (fedCriteriaDuration.equals(StaticData.DAYS.toString())) {
								dtp.setFedCriteria((Double.parseDouble(dtp.getFedCriteria()) * 24) + "");
							}
							
						}
						dtp.setTimePointNo(count);
						if(dtp.getTimePoint() != null && !dtp.getTimePoint().equals(""))
							doseList.add(dtp);
						count++;
					}
				}
			}	
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doseList;
	}

	private List<SampleTimePoints> allSampleTimepointsWithOrder(List<SampleTimePoints> preDoseSamples,
			List<SampleTimePoints> postDoseSamples, List<SampleTimePoints> ambulatorySamples) {
		List<String> samples = new ArrayList<>();
		int count = 0;
		for (SampleTimePoints sp : preDoseSamples) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		Collections.sort(samples, Collections.reverseOrder());
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (SampleTimePoints sp : preDoseSamples) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		samples = new ArrayList<>();
		for (SampleTimePoints sp : postDoseSamples) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (SampleTimePoints sp : postDoseSamples) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}
		samples = new ArrayList<>();
		for (SampleTimePoints sp : ambulatorySamples) {
			samples.add(sp.getTimePoint());
		}
		samples.sort(null);
		for (int i = 0; i < samples.size(); i++) {
			count++;
			for (SampleTimePoints sp : ambulatorySamples) {
				if (sp.getTimePoint().equals(samples.get(i))) {
					sp.setTimePointNo(count);
				}
			}
		}

		List<SampleTimePoints> allsamples = new ArrayList<>(preDoseSamples);
		allsamples.addAll(postDoseSamples);
		allsamples.addAll(ambulatorySamples);
		return allsamples;
	}

	public StatusMaster statusMaster(String string) {
		return studyCreationDao.statusMaster(string);
	}

	public Projects meargeProject(Projects project) {
		// TODO Auto-generated method stub
		return studyCreationDao.meargeProject(project);
	}

	public List<Volunteer> createVolunteers(StudyMaster study) {
		StatusMaster enrolle = studyCreationDao.statusMaster(StudyStatus.ENROLLED.toString());
		StatusMaster enrolled = studyCreationDao.statusMaster(StudyStatus.ENROLLED.toString());
		int noOfVol = study.getNoOfSubjects();
		List<Volunteer> vols = new ArrayList<>();
		for (int i = 1; i <= noOfVol; i++) {
			Volunteer vol = new Volunteer();
			vol.setStudy(study);
			vol.setVolunreerId("V" + i);
			vol.setSubjectNo("" + i);
			vol.setSeqNo(i);
			if (i < 10) {
				vol.setVolunreerId("V0" + i);
				vol.setSubjectNo("0" + i);
			}
			vol.setEnrollSatatus(enrolled);
			vol.setVolunteerSatatus(enrolle);
			vols.add(vol);
		}
		int volAndStandby = noOfVol + study.getNoOfStandBySubjects();
		for (int i = noOfVol, count = 1; i < volAndStandby; i++, count++) {
			Volunteer vol = new Volunteer();
			vol.setStudy(study);
			vol.setVolunteerType("Stand By");
			vol.setVolunreerId("V" + i);
			vol.setSubjectNo("S" + count);
			vol.setSeqNo(i);
			if (i < 10) {
				vol.setVolunreerId("V0" + i);
				vol.setSubjectNo("S0" + count);
			}
			vol.setEnrollSatatus(enrolled);
			vol.setVolunteerSatatus(enrolle);
			vols.add(vol);
		}
//		studyCreationDao.createVolunteers(vols);
		return vols;
	}

	public List<SubjectSampleCollectionTimePoints> subjectSampleCollectionTimePoints(StudyMaster study,
			Map<Long, Map<String, SubjectRandamization>> radamization) {

		FromStaticData fsd = studyCreationDao.formStaticData(StudyDesign.PERIODTYPE.toString(),
				StudyDesign.PERIOD.toString());
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyPeriodMaster> sps = studyCreationDao.getPeriod(study, fsd, activeStatus);
		List<SubjectSampleCollectionTimePoints> sampleTimepoints = new ArrayList<>();
		for (StudyPeriodMaster sp : sps) {
			Map<String, SubjectRandamization> periodRandamization = radamization.get(sp.getId());
			int noOfSubjects = study.getNoOfSubjects() + study.getNoOfStandBySubjects();
			for (int i = 1; i <= noOfSubjects; i++) {
				String subjNo = "" + i;
				if (i < 10)
					subjNo = "0" + i;
				SubjectRandamization sr = periodRandamization.get(subjNo);
				TreatmentInfo ti = null;
				if (sr != null)
					ti = sr.getTreatmentInfo();
				else {
					System.out.println();
				}
				boolean standBy = false;
				if (study.getNoOfSubjects() < i) {
					int standno = i - study.getNoOfSubjects();
					subjNo = "S" + standno;
					if (i < 10)
						subjNo = "S0" + standno;
					standBy = true;
				}
//					Map<TreatmentInfo, List<SampleTimePoints>> stpsMap = studyCreationDao.sampleTimePoints(study);

				List<SampleTimePoints> stps = studyCreationDao.studyTreatmentRandamization(study, ti);
				for (SampleTimePoints stp : stps) {
					if (stp.getTreatmentInfo() != null)
						System.out.println(stp.getTreatmentInfo().getTreatmentNo() + "\t"
								+ stp.getTreatmentInfo().getRandamizationCode() + "\t" + stp.getTimePoint() + "\t"
								+ stp.getTimePointType().getFieldValue() + "\t" + stp.getTimePointNo());
					else {
						System.out.println(stp.getTimePoint() + "\t" + stp.getTimePointType().getFieldValue() + "\t"
								+ stp.getTimePointNo());
					}
				}
				for (SampleTimePoints stp : stps) {

					boolean flag = true;
					if (standBy && !stp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
						flag = false;
					}
					if (flag) {
						int noOfVacutainer = stp.getNoOfVacutainer();
						for (int vacutainer = 1; vacutainer <= noOfVacutainer; vacutainer++) {
							SubjectSampleCollectionTimePoints sstp = new SubjectSampleCollectionTimePoints();
							sstp.setStudy(study);
							if (ti == null) {
								if (stp.getTreatmentInfo() != null)
									sstp.setTreatmentInfo(stp.getTreatmentInfo());
							} else
								sstp.setTreatmentInfo(ti);
							sstp.setPeriod(sp);
							sstp.setSampleTimePointId(stp);
							sstp.setSign(stp.getSign());
							sstp.setTimePoint(stp.getTimePoint());
							sstp.setTimePointType(stp.getTimePointType());
							sstp.setTimePointNo(stp.getTimePointNo());
							sstp.setWindowPeriod(stp.getWindowPeriod());
							sstp.setWindowPeriodSign(stp.getWindowPeriodSign());
							sstp.setWindowPeriodType(stp.getWindowPeriodType());
							sstp.setSubjectNo(subjNo);

							sstp.setSubjectOrder(i);
							sstp.setVacutainer(vacutainer);
							sstp.setSubjectSampleCollectionTimePointId(generateVacutainerBarocodeValue(sstp));
							if (sstp.getTreatmentInfo() != null)
								System.out.println(sstp.getSubjectSampleCollectionTimePointId() + "\t"
										+ sstp.getPeriod().getPeriodNo() + "\t"
										+ sstp.getTreatmentInfo().getRandamizationCode() + "\t" + sstp.getSubjectNo()
										+ "\t" + sstp.getTimePoint() + "\t" + sstp.getTimePointType().getFieldValue());
							else {
								System.out.println(sstp.getSubjectSampleCollectionTimePointId() + "\t"
										+ sstp.getPeriod().getPeriodNo() + "\t" + "\t" + sstp.getSubjectNo() + "\t"
										+ sstp.getTimePoint() + "\t" + sstp.getTimePointType().getFieldValue());
							}
							sampleTimepoints.add(sstp);
						}
					}

				}
			}

		}

		return sampleTimepoints;
//		// saving process
//		return studyCreationDao.saveSubjectSampleTimePoints(sampleTimepoints);
	}

	private String generateVacutainerBarocodeValue(SubjectSampleCollectionTimePoints cstpp) {
//		System.out.println(cstpp.getTimePointType().getCode());
		StringBuilder sb = new StringBuilder();
		sb.append("04");
		sb.append(addPrefixZeros(cstpp.getStudy().getId() + "", 6));
		sb.append(cstpp.getPeriod().getPeriodNo());
		sb.append(addPrefixZeros(cstpp.getSubjectOrder() + "", 3));
		if (cstpp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString()))
			sb.append("1");
		if (cstpp.getTimePointType().getCode().equals(StaticData.POSTDOSE.toString()))
			sb.append("3");
		else if (cstpp.getTimePointType().getCode().equals(StaticData.AMBULATORY.toString()))
			sb.append("4");
//		System.out.println(sb.toString());
//		System.out.println(cstpp.getBatchNo());
//		sb.append(cstpp.getBatchNo());
//		System.out.println(sb.toString());
		String[] t = null;
		t = cstpp.getTimePoint().split("\\.");
//		System.out.println(sb.toString());
		sb.append(addPrefixZeros(t[0] + "", 4)).append(addSiffixZeros(t[1] + "", 3));
		sb.append(cstpp.getVacutainer());
		if (cstpp.getTreatmentInfo() != null)
			sb.append(cstpp.getTreatmentInfo().getTreatmentNo());
		else
			sb.append("0");
//		System.out.println(sb.toString());
		return addSiffixZeros(sb.toString(), 23);

	}

	public String generatesubjectBarcode(Volunteer vol) {
//		System.out.println(cstpp.getTimePointType().getCode());
		StringBuilder sb = new StringBuilder();
		sb.append("01");
		sb.append(addPrefixZeros(vol.getStudy().getId() + "", 6));
		sb.append("0");
		sb.append(addPrefixZeros(vol.getSeqNo() + "", 3));
		sb.append(addPrefixZeros(vol.getId() + "", 8));
		return addSiffixZeros(sb.toString(), 23);

	}

	public String addPrefixZeros(String value, int i) {
		while (value.length() < i) {
			value = "0" + value;
		}
		return value;
	}

	public String addSiffixZeros(String value, int i) {
		while (value.length() < i) {
			value = value + "0";
		}
		return value;
	}

	public void mergeStudy(StudyMaster study) {
		// TODO Auto-generated method stub
		studyCreationDao.mergeStudy(study);
	}

	public List<SubjectVitalTimePoints> subjectVitalsTimePoints(StudyMaster study,
			Map<Long, Map<String, SubjectRandamization>> subjectRadamization) {
		FromStaticData fsd = studyCreationDao.formStaticData(StudyDesign.PERIODTYPE.toString(),
				StudyDesign.PERIOD.toString());
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyPeriodMaster> sps = studyCreationDao.getPeriod(study, fsd, activeStatus);
		List<SubjectVitalTimePoints> vitalTimepoints = new ArrayList<>();
		for (StudyPeriodMaster sp : sps) {
			Map<String, SubjectRandamization> subRadamizations = subjectRadamization.get(sp.getId());
//			Map<TreatmentInfo, List<VitalTimePoints>> stpsMap = studyCreationDao.vitalTimePoints(study);
//			for (Map.Entry<TreatmentInfo, List<VitalTimePoints>> m : stpsMap.entrySet()) {
//				

			int noOfSubjects = study.getNoOfSubjects() + study.getNoOfStandBySubjects();
			for (int i = 1; i <= noOfSubjects; i++) {
				String subjNo = "" + i;
				if (i < 10)
					subjNo = "0" + i;
				SubjectRandamization sr = subRadamizations.get(subjNo);
				TreatmentInfo ti = null;
				if (sr != null)
					ti = sr.getTreatmentInfo();
				boolean standBy = false;
				if (study.getNoOfSubjects() < i) {
					int standno = i - study.getNoOfSubjects();
					subjNo = "S" + standno;
					if (i < 10)
						subjNo = "S0" + standno;
				}
				List<VitalTimePoints> stps = studyCreationDao.subjectTreatmentVitalTimePoints(study, ti);
				for (VitalTimePoints stp : stps) {
					boolean flag = true;
					if (standBy && !stp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
						flag = false;
					}
					if (flag) {
						SubjectVitalTimePoints sstp = new SubjectVitalTimePoints();
						sstp.setStudy(study);
						if (ti == null) {
							if (stp.getTreatmentInfo() != null) {
								sstp.setTreatmentInfo(stp.getTreatmentInfo());
							}
						} else
							sstp.setTreatmentInfo(ti);
						sstp.setPeriod(sp);
						sstp.setVitalTimePointsId(stp);
						sstp.setSign(stp.getSign());
						sstp.setTimePoint(stp.getTimePoint());
						sstp.setTimePointType(stp.getTimePointType());
						sstp.setTimePointNo(stp.getTimePointNo());
						sstp.setWindowPeriod(stp.getWindowPeriod());
						sstp.setWindowPeriodSign(stp.getWindowPeriodSign());
						sstp.setWindowPeriodType(stp.getWindowPeriodType());
						sstp.setSubjectNo(subjNo);
					//	List<TimePointVitalTests> test = stp.getTest();
						List<SubjectTimePointVitalTests> stest = new ArrayList<SubjectTimePointVitalTests>();
						/*for (TimePointVitalTests t : test) {
							SubjectTimePointVitalTests stvt = new SubjectTimePointVitalTests();
							stvt.setTestId(t.getTestId());
							stvt.setVitalTimePointId(t.getVitalTimePointId());
							stvt.setSubjectVitalTimePoints(sstp);
							stvt.setTimePointVitalTests(t);
							stvt.setMaximum(t.getMaximum());
							stvt.setMinimum(t.getMinimum());
							stest.add(stvt);
						}*/
						sstp.setTest(stest);
						sstp.setSubjectOrder(i);
						vitalTimepoints.add(sstp);
					}

				}
			}
		}
		return vitalTimepoints;
//		// saving process
//		return studyCreationDao.saveSubjectVitalTimePoints(sampleTimepoints);
	}

	public List<SubjectECGTimePoints> subjectEcgTimePoints(StudyMaster study,
			Map<Long, Map<String, SubjectRandamization>> subjectRadamization) {
		FromStaticData fsd = studyCreationDao.formStaticData(StudyDesign.PERIODTYPE.toString(),
				StudyDesign.PERIOD.toString());
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyPeriodMaster> sps = studyCreationDao.getPeriod(study, fsd, activeStatus);
		List<SubjectECGTimePoints> vitalTimepoints = new ArrayList<>();
		for (StudyPeriodMaster sp : sps) {
			Map<String, SubjectRandamization> subRadamizations = subjectRadamization.get(sp.getId());
//			Map<TreatmentInfo, List<VitalTimePoints>> stpsMap = studyCreationDao.vitalTimePoints(study);
//			for (Map.Entry<TreatmentInfo, List<VitalTimePoints>> m : stpsMap.entrySet()) {
//				

			int noOfSubjects = study.getNoOfSubjects() + study.getNoOfStandBySubjects();
			for (int i = 1; i <= noOfSubjects; i++) {
				String subjNo = "" + i;
				if (i < 10)
					subjNo = "0" + i;
				SubjectRandamization sr = subRadamizations.get(subjNo);
				TreatmentInfo ti = null;
				if (sr != null)
					ti = sr.getTreatmentInfo();
				boolean standBy = false;
				if (study.getNoOfSubjects() < i) {
					int standno = i - study.getNoOfSubjects();
					subjNo = "S" + standno;
					if (i < 10)
						subjNo = "S0" + standno;
				}
				List<ECGTimePoints> stps = studyCreationDao.subjectTreatmentEcgTimePoints(study, ti);
				for (ECGTimePoints stp : stps) {
					boolean flag = true;
					if (standBy && !stp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
						flag = false;
					}
					if (flag) {
						SubjectECGTimePoints sstp = new SubjectECGTimePoints();
						sstp.setStudy(study);
						if (ti == null) {
							if (stp.getTreatmentInfo() != null) {
								sstp.setTreatmentInfo(stp.getTreatmentInfo());
							}
						} else
							sstp.setTreatmentInfo(ti);
						sstp.setPeriod(sp);
						sstp.setEcgTimePointsId(stp);
						sstp.setSign(stp.getSign());
						sstp.setTimePoint(stp.getTimePoint());
						sstp.setTimePointType(stp.getTimePointType());
						sstp.setTimePointNo(stp.getTimePointNo());
						sstp.setWindowPeriod(stp.getWindowPeriod());
						sstp.setWindowPeriodSign(stp.getWindowPeriodSign());
						sstp.setWindowPeriodType(stp.getWindowPeriodType());
						sstp.setSubjectNo(subjNo);
						sstp.setSubjectOrder(i);
						vitalTimepoints.add(sstp);
					}

				}
			}
		}
		return vitalTimepoints;
//		// saving process
//		return studyCreationDao.saveSubjectVitalTimePoints(sampleTimepoints);
	}

	public List<SubjectMealsTimePoints> subjectMealsTimePoints(StudyMaster study,
			Map<Long, Map<String, SubjectRandamization>> subjectRadamization) {
		FromStaticData fsd = studyCreationDao.formStaticData(StudyDesign.PERIODTYPE.toString(),
				StudyDesign.PERIOD.toString());
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyPeriodMaster> sps = studyCreationDao.getPeriod(study, fsd, activeStatus);
		List<SubjectMealsTimePoints> mealsTimepoints = new ArrayList<>();
		for (StudyPeriodMaster sp : sps) {
			Map<String, SubjectRandamization> subRadamizations = subjectRadamization.get(sp.getId());
//			Map<TreatmentInfo, List<MealsTimePoints>> stpsMap = studyCreationDao.mealsTimePoints(study);
//				TreatmentInfo ti = m.getKey();
//				List<MealsTimePoints> stps = m.getValue();
			int noOfSubjects = study.getNoOfSubjects() + study.getNoOfStandBySubjects();
			for (int i = 1; i <= noOfSubjects; i++) {
				String subjNo = "" + i;
				if (i < 10)
					subjNo = "0" + i;
				SubjectRandamization sr = subRadamizations.get(subjNo);
				TreatmentInfo ti = null;
				if (sr != null)
					ti = sr.getTreatmentInfo();
				boolean standBy = false;
				if (study.getNoOfSubjects() < i) {
					int standno = i - study.getNoOfSubjects();
					subjNo = "S" + standno;
					if (i < 10)
						subjNo = "S0" + standno;
				}
				List<MealsTimePoints> stps = studyCreationDao.subjectTreatmentMealsTimePoints(study, ti);
				for (MealsTimePoints stp : stps) {
					boolean flag = true;
					if (standBy && !stp.getTimePointType().getCode().equals(StaticData.PREDOSE.toString())) {
						flag = false;
					}
					if (flag) {
						SubjectMealsTimePoints sstp = new SubjectMealsTimePoints();
						sstp.setStudy(study);
						if (ti == null) {
							if (stp.getTreatmentInfo() != null) {
								sstp.setTreatmentInfo(stp.getTreatmentInfo());
							}
						} else
							sstp.setTreatmentInfo(ti);
						sstp.setPeriod(sp);
						sstp.setMealsTimePointsId(stp);
						sstp.setSign(stp.getSign());
						sstp.setTimePoint(stp.getTimePoint());
						sstp.setTimePointType(stp.getTimePointType());
						sstp.setTimePointNo(stp.getTimePointNo());
						sstp.setMealsType(stp.getMealsType());
						sstp.setWindowPeriod(stp.getWindowPeriod());
						sstp.setWindowPeriodSign(stp.getWindowPeriodSign());
						sstp.setSubjectNo(subjNo);
						sstp.setSubjectOrder(i);
						mealsTimepoints.add(sstp);
					}
				}
			}
		}
		return mealsTimepoints;
//		// saving process
//		return studyCreationDao.saveSubjectMealsTimePoints(sampleTimepoints);
	}

	public List<SubjectDoseTimePoints> subjecDoseimePoints(StudyMaster study, 
			Map<Long, Map<String, SubjectRandamization>> subjectRadamization) {
//		FromStaticData fsd = studyCreationDao.formStaticData(StudyDesign.PERIODTYPE.toString(),
//				StudyDesign.PERIOD.toString());
//		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
//		List<StudyPeriodMaster> sps = studyCreationDao.getPeriod(study, fsd, activeStatus);
//		List<SubjectDoseTimePoints> doseTimepoints = new ArrayList<>();
//		for (StudyPeriodMaster sp : sps) {
//			Map<String, SubjectRandamization> subRadamizations = subjectRadamization.get(sp.getId());
////			Map<TreatmentInfo, List<DoseTimePoints>> stpsMap = studyCreationDao.doseTimePoints(study);
////			for (Map.Entry<TreatmentInfo, List<DoseTimePoints>> m : stpsMap.entrySet()) {
////				TreatmentInfo ti = m.getKey();
////				List<DoseTimePoints> stps = m.getValue();
//			int noOfSubjects = study.getNoOfSubjects();
//			for (int i = 1; i <= noOfSubjects; i++) {
//				String subjNo = "" + i;
//				if (i < 10)
//					subjNo = "0" + i;
//				SubjectRandamization sr = subRadamizations.get(subjNo);
//				TreatmentInfo ti = null;
//				if (sr != null)
//					ti = sr.getTreatmentInfo();
//				List<DoseTimePoints> stps = studyCreationDao.subjectTreatmentDoseTimePoints(study, ti);
//				int lableNo = 1;
//				if (ti != null)
//					lableNo = ti.getNoOfSachetLables();
//				for (DoseTimePoints stp : stps) {
//					for (int noOfLables = 1; noOfLables <= lableNo; noOfLables++) {
//						SubjectDoseTimePoints sstp = new SubjectDoseTimePoints();
//						sstp.setStudy(study);
//						if (ti == null) {
//							if (stp.getTreatmentInfo() != null) {
//								sstp.setTreatmentInfo(stp.getTreatmentInfo());
//							}
//						} else
//							sstp.setTreatmentInfo(ti);
//						sstp.setPeriod(sp);
//						sstp.setDoseTimePoints(stp);
//						sstp.setTimePoint(stp.getTimePoint());
//						sstp.setTimePointNo(stp.getTimePointNo());
//						sstp.setFastingCriteria(stp.getFastingCriteria());
//						sstp.setFastingCriteriaType(stp.getFastingCriteriaType());
//						sstp.setFedCriteria(stp.getFedCriteria());
//						sstp.setFedcriteriaType(stp.getFedcriteriaType());
//						sstp.setWindowPeriod(stp.getWindowPeriod());
//						sstp.setWindowPeriodSign(stp.getWindowPeriodSign());
//						sstp.setWindowPeriodType(stp.getWindowPeriodType());
//						sstp.setSubjectNo(subjNo);
//						sstp.setLableNo(noOfLables);
//						sstp.setSubjectOrder(i);
//						if (sstp.getTreatmentInfo() == null) {
//							System.out.println();
//						}
//						sstp.setId(generateSachetBarocodeValue(sstp));
//						doseTimepoints.add(sstp);
//					}
//
//				}
//			}
//		}
//		return doseTimepoints;
		return null;
//		// saving process
//		return studyCreationDao.saveSubjectDoseimePoints(sampleTimepoints);

	}

//	private String generateSachetBarocodeValue(SubjectDoseTimePoints sstp) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("03");
//		sb.append(addPrefixZeros(sstp.getStudy().getId() + "", 6));
//		sb.append(sstp.getPeriod().getPeriodNo());
//		sb.append(addPrefixZeros(sstp.getSubjectOrder() + "", 3));
//		if (sstp.getTreatmentInfo().getRandamizationCode().equals("T"))
//			sb.append("0");
//		else
//			sb.append("1");
//		String timepoint = sstp.getDoseTimePoints().getTimePoint();
//
//		String[] tpSplit = timepoint.split(".");
//		String barcodeTimePoint = "0000000";
//		if (tpSplit.length == 2) {
//			String prefix = tpSplit[0];
//			String postfix = tpSplit[1];
//			if (tpSplit[0].length() == 0)
//				prefix = "0000";
//			else if (tpSplit[0].length() == 1)
//				prefix = "000" + tpSplit[0];
//			else if (tpSplit[0].length() == 2)
//				prefix = "00" + tpSplit[0];
//			else if (tpSplit[0].length() == 3)
//				prefix = "0" + tpSplit[0];
//
//			if (tpSplit[1].length() == 0)
//				postfix = "000";
//			else if (tpSplit[0].length() == 1)
//				postfix = "00" + tpSplit[1];
//			else if (tpSplit[1].length() == 2)
//				postfix = "0" + tpSplit[1];
//			barcodeTimePoint = prefix + postfix;
//		}
//		sb.append(barcodeTimePoint);
//		sb.append(addSiffixZeros(sstp.getDoseTimePoints().getTimePointNo() + "", 2));
//		sb.append(sstp.getLableNo());
//		return addSiffixZeros(sb.toString(), 23);
//		return null;
//	}

	public List<SubjectPeriodStatus> createSubjectPerioStatus(SortedMap<Integer, String> subjectNubers,
			StudyMaster study) {
		StatusMaster enrollStatus = studyCreationDao.statusMaster(StudyStatus.ENROLLED.toString());
		StatusMaster activeStatus = studyCreationDao.statusMaster(StudyStatus.ACTIVE.toString());
		List<StudyPeriodMaster> periods = studyCreationDao.allPeriod(study, activeStatus);
		List<SubjectPeriodStatus> subjectStautsList = new ArrayList<>();
		for (StudyPeriodMaster period : periods) {
			if (period.getPeriodNo() != 0) {
				for (Map.Entry<Integer, String> subject : subjectNubers.entrySet()) {
					if (period.getPeriodNo() == 1) {
						SubjectPeriodStatus sps = new SubjectPeriodStatus();
						sps.setStudy(period.getStudy());
						sps.setPeriod(period);
						sps.setActiveStatus(true);
						sps.setPeriodNo(period.getPeriodNo());
						sps.setSubjectNo(subject.getValue());
						sps.setSubjectSeqNo(subject.getKey());
//							sps.setVolunteer(vol);
//							sps.setStudySubject(studySubject);
						sps.setSubjectStatus(enrollStatus);
						subjectStautsList.add(sps);
					}
				}
			}
		}
//		subjectStautsList = studyCreationDao.saveSubjectPerioStatus(subjectStautsList);
		return subjectStautsList;
	}

	public void saveSubjectTimePoints(StudyMaster study, 
			List<SubjectPeriodStatus> subjectPerioStatus, List<SubjectRandamization> rendamization,
			List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints,
			List<SubjectVitalTimePoints> subjectVitalTimePoints, List<SubjectECGTimePoints> subjectEcgTimePoints,
			List<SubjectMealsTimePoints> subjectMealsTimePoints, List<SubjectDoseTimePoints> subjectDoseTimePoints) {
		// TODO Auto-generated method stub
		studyCreationDao.saveSubjectTimePoints(study,  subjectPerioStatus, rendamization, subjectSampleTimePoints,
				subjectVitalTimePoints, subjectEcgTimePoints, subjectMealsTimePoints, subjectDoseTimePoints);
	}

	public List<SubjectRandamization> createSubjectRandamization(StudyMaster study,
			SortedMap<Integer, String> subjects) {
		return studyCreationDao.createSubjectRandamization(study, subjects);
	}

	public List<FromStaticData> sampleCollectionDeviations() {
		// TODO Auto-generated method stub
		return studyCreationDao.formStaticData(StudyDesign.SAMPLECOLLECTIONDEVIATIONS.toString());
	}

	public List<FromStaticData> doseFromComments() {
		// TODO Auto-generated method stub
		return studyCreationDao.formStaticData(StudyDesign.DOSEFROMCOMMENTS.toString());
	}

	public List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificParmetersList(
			InternationalizaionLanguages inalg, List<Long> parmList) {
		return studyCreationDao.getLanguageSpecificParmetersList(inalg, parmList);
	}

	public List<DeviationMessage> deviationMessages(String collectionType) {
		return studyCreationDao.deviationMessages(collectionType);
	}

}