package com.covideinfo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covide.sorting.MethodExecutionTimeings;
import com.covideinfo.bo.StudyClinicalBo;
import com.covideinfo.bo.StudyCreationBo;
import com.covideinfo.clinical.service.ClinicalService;
import com.covideinfo.dto.CentrificationDto;
import com.covideinfo.dto.CentrifugationDateDto;
import com.covideinfo.dto.CetrifugationPageDataDto;
import com.covideinfo.dto.DoseDataDto;
import com.covideinfo.dto.DoseTimePointsDetailsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.MealCollectoinDto;
import com.covideinfo.dto.MealsTimePointsDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.SampleCollectionDtoDetails;
import com.covideinfo.dto.SampleCollectoinDto;
import com.covideinfo.dto.SampleSeparationDetailsDto;
import com.covideinfo.dto.SeggrigationDataDto;
import com.covideinfo.dto.SegrigationDto;
import com.covideinfo.dto.SeparationVacutinerDto;
import com.covideinfo.dto.StorageVacutinerDto;
import com.covideinfo.dto.VialRackCollectionDto;
import com.covideinfo.dto.VialRackDto;
import com.covideinfo.dto.VitalCollectionDataDto;
import com.covideinfo.dto.VitalCollectionDetailsDto;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.scheduler.threads.SampleScheduleTimesCalculationThread;
import com.covideinfo.service.BarcodeService;
import com.covideinfo.service.SampleScheduleCalculationService;
import com.covideinfo.service.StudyExecutionServiece;
import com.covideinfo.service.StudyService;
import com.covideinfo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/study/clinical")
@PropertySource(value = { "classpath:application.properties" })
public class StudyClinicalController {
	static final Logger logger = Logger.getLogger(StudyClinicalController.class);
	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	@Autowired
	StudyService studyService;

	@Autowired
	ClinicalService clinicalService;

	@Autowired
	StudyCreationBo studyCreationBo;

	@Autowired
	StudyClinicalBo studyClinicalBo;

	@Autowired
	StudyExecutionServiece sexeService;
	@Autowired
	BarcodeService barcodeService;
	@Autowired
	MessageSource messageSource;

	@Autowired
	SampleScheduleCalculationService sscService;

	@RequestMapping(value = "/cpuActivity/{studyId}/{cpuActivity}", method = RequestMethod.GET)
	public String cpuActivity(@PathVariable("studyId") Long studyId, @PathVariable("cpuActivity") String cpuActivity,
			HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
		StudyMaster study = studyService.findByStudyId(studyId);
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (cpuActivity.equals("BLOODSAMPLECOLLECTION")) {
			model.addAttribute("deviations",studyCreationBo.deviationMessages("SAMPLES"));
			return "/pages/clinical/stdClinicalSampleCollection";
		} else if (cpuActivity.equals("MEALCOLLECTION")) {
			List<TreatmentInfo> tinfList = null;
			MealsTimePointsDto mtpDto = clinicalService.allMealTimepoints(study.getId());
			if (mtpDto != null)
				tinfList = mtpDto.getTinfList();
			model.addAttribute("tinfList", tinfList);
			return "/pages/clinical/measlCollection";
		} else if (cpuActivity.equals("DOSING")) {
			if (study.getTreatmentCodeOnSachet().getCode().equals(StaticData.SHOW.toString())) {
				model.addAttribute("treatmentShow", true);
			} else
				model.addAttribute("treatmentShow", false);
			model.addAttribute("doseComments", studyCreationBo.doseFromComments());
			return "/pages/clinical/stdClinicaldosing";
		} else if (cpuActivity.equals("VITALCOLLECTION")) {
			return "/pages/clinical/vitalCollection";
		} else if (cpuActivity.equals("CENTRIFUGATION")) {
			return "/pages/clinical/centrifugation";
		} else if (cpuActivity.equals("SAMPLESEPARATION")) {
			model.addAttribute("studySelection", true);
			return "/pages/clinical/sampleSeparation";
		} else if (cpuActivity.equals("SAMPLESEGREGATION")) {
			List<StudyPeriodMaster> periodList = studyService.getStudyPeriodMasterList(study.getId());
			model.addAttribute("noOfVials", clinicalService.noOfVials(studyId));
			model.addAttribute("periodList", periodList);
			return "/pages/clinical/sampleSegregationPage";
		} else if (cpuActivity.equals("SAMPLESTORAGE")) {
			model.addAttribute("studySelection", true);
			return "/pages/clinical/sampleStoratePage";
		} else if (cpuActivity.equals("SUBJECTWISESAMPLESCONTAINTER")) {
			return "/pages/clinical/subjectWiseSamplesContainter";
		} else if (cpuActivity.equals("SHIPMENT")) {
			return "/pages/clinical/shipment";
		} else if (cpuActivity.equals("VIALRACK")) {
			return "/pages/clinical/vialRackPage";
		}
		return "stdClinicalSampleCollectionPage";
	}

	@RequestMapping(value = "/measlCollection/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MealsTimePointsDto measlCollection(@PathVariable("studyId") Long studyId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		StudyMaster study = studyService.findByStudyId(studyId);
		MealsTimePointsDto mtpDto = clinicalService.allMealTimepoints(study.getId());
		return mtpDto;
	}

	@RequestMapping(value = "/mealsDataSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MessageDto saveMealsData(MealCollectoinDto mealsCollectionDate, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		MessageDto msgDto = new MessageDto();
		Date date = new Date();
//		MethodExecutionTimeings mt = new MethodExecutionTimeings();
		String subStr = "";
		String periodIdsStr = "";
		String tpIdsStr = "";
		if(mealsCollectionDate.getSubNoList() != null && mealsCollectionDate.getSubNoList().size() > 0) {
			for(String st : mealsCollectionDate.getSubNoList()) {
				if(subStr.equals(""))
					subStr = st;
				else subStr = subStr +","+st;
			}
		}
		if(mealsCollectionDate.getPeriodIds() != null && mealsCollectionDate.getPeriodIds().size() > 0) {
			for(Long pid : mealsCollectionDate.getPeriodIds()) {
				if(periodIdsStr.equals(""))
					periodIdsStr = pid+"";
				else periodIdsStr = periodIdsStr +","+pid;
			}
		}
		if(mealsCollectionDate.getTimePointIds() != null && mealsCollectionDate.getTimePointIds().size() > 0) {
			for(Long tpId : mealsCollectionDate.getTimePointIds()) {
				if(tpIdsStr.equals(""))
					tpIdsStr = tpId+"";
				else tpIdsStr = tpIdsStr +","+tpId;
			}
		}
//		mt.generateExecutionFile("start", "MealsCollection", subStr, tpIdsStr, periodIdsStr, request.getServletContext().getRealPath("/"));
		String result = clinicalService.measlStartTimesSave(userId, mealsCollectionDate, date);
		String msg = "";
		if (result.equals("success")) {
			msgDto.setMsgFlag(true);
			msg = "Meals Data Saved Successfully....!";
		} else {
			msgDto.setMsgFlag(false);
			msg = "Meals Data Saving Failed. Please try again.";
		}
		msgDto.setMealsMsg(msg);
//		mt.generateExecutionFile("end", "MealsCollection", mealsCollectionDate.getSubNoList().toString(), mealsCollectionDate.getTimePointIds().toString(), mealsCollectionDate.getPeriodIds().toString(), request.getServletContext().getRealPath("/"));
		return msgDto;

	}

	@RequestMapping(value = "/sampleCollectionDataDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SampleCollectionDtoDetails sampleCollectionDataDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		SampleCollectionDtoDetails scdDto = clinicalService.getSampleCollectionDtoDetails(studyId);
		return scdDto;
	}

	@RequestMapping(value = "/stdClinicalSampleCollectionSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MessageDto stdClinicalSampleCollectionSave(SampleCollectoinDto scd, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		Date date = new Date();
		MethodExecutionTimeings mt = new MethodExecutionTimeings();
		mt.generateExecutionFile("start", "SampleCollection", scd.getSubject(), scd.getVacutainer(), "Period In barcode", request.getServletContext().getRealPath("/"));
		
		String result = clinicalService.saveSampleCollection(userId, scd, date);
//		SampleCollectionDtoDetails scdDto = clinicalService.getSampleCollectionDtoDetails(scd.getStudyId());
		MessageDto msgDto = new MessageDto();
		String msg = "";
		if (result.equals("success")) {
			msgDto.setMsgFlag(true);
			msg = "Data submitted successfully";
		} else if (result.equals("Duplicate")) {
			msgDto.setMsgFlag(false);
			msg = "Sample already collected for the subject and timepoint";
		} else {
			msgDto.setMsgFlag(false);
			msg = "Failed to save the data. Please try again or contact administrator";
		}
		msgDto.setMealsMsg(msg);
		mt.generateExecutionFile("end", "SampleCollection", scd.getSubject(), scd.getVacutainer(), "Period In barcode", request.getServletContext().getRealPath("/"));
		return msgDto;
	}

	@RequestMapping(value = "/vitalCollectionDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody VitalCollectionDetailsDto studySubjects(@PathVariable("studyId") Long studyId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		Date startTime = new Date();
		logger.warn("Run Time :" + request.getSession().getAttribute("userName") + " \t, Vital Collection start time : "
				+ startTime + " MS : " + startTime.getTime());
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		VitalCollectionDetailsDto vcdDto = clinicalService.getVitalCollectionDetailsDtoDetails(studyId, languageId);
		Date endTime = new Date();
		logger.warn("Run Time :" + request.getSession().getAttribute("userName") + " \t, Vital Collection start end : "
				+ endTime + " MS : " + (endTime.getTime() - startTime.getTime()));
		return vcdDto;
	}

	@RequestMapping(value = "/vitalCollectionSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody MessageDto vitalCollectionSave(VitalCollectionDataDto colletion, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		Date startTime = new Date();
		logger.warn("Run Time :" + request.getSession().getAttribute("userName")
				+ " \t, Vital Collection saving start time : " + startTime + " MS : " + startTime.getTime());
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		MessageDto msgDto = new MessageDto();
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		MethodExecutionTimeings mt = new MethodExecutionTimeings();
		mt.generateExecutionFile("start", "VitalCollection", colletion.getSubject(), colletion.getTimePointPk()+"", colletion.getPerodId()+"", request.getServletContext().getRealPath("/"));
		
		String result = clinicalService.saveVitalCollection(userId, colletion, messageSource, currentLocale, languageId,
				dateFormat);
		String msg = "";
		if (result.equals("success")) {
			msgDto.setMsgFlag(true);
			msg = "Vitals Data Saved Successfully....!";
		} else if (result.equals("Duplicate")) {
			msgDto.setMsgFlag(false);
			msg = "Vital Data Already Exists.";
		} else {
			msgDto.setMsgFlag(false);
			msg = "Vitals Data Saving Failed. Please try again.";
		}
		msgDto.setMealsMsg(msg);
		Date endTime = new Date();
		logger.warn("Run Time :" + request.getSession().getAttribute("userName") + " \t, Vital Collection saving end : "
				+ endTime + " MS : " + (endTime.getTime() - startTime.getTime()));
		return msgDto;

	}

	@RequestMapping(value = "/dosingCollection/{studyId}", method = RequestMethod.GET)
	public String dosingCollection(HttpServletRequest request, ModelMap model, @PathVariable("studyId") Long studyId) {
		StudyMaster study = studyService.findByStudyId(studyId);
		DosingDto dsDto = studyService.getDosingDtoDetails(studyId, "DOSING");
		dsDto.setStudy(study);
		if (dsDto.getStudy().getTreatmentCodeOnSachet().getCode().equals(StaticData.SHOW.toString())) {
			model.addAttribute("treatmentShow", true);
		} else
			model.addAttribute("treatmentShow", false);
		model.addAttribute("doseComments", dsDto.getDevMsgList());
		return "/pages/dynamicForms/stdClinicaldosing";
	}

	@RequestMapping(value = "/getDosingCollectionDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody DoseTimePointsDetailsDto getDosingCollectionDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		String mealType = environment.getRequiredProperty("mealType");
		Locale currentLocale = LocaleContextHolder.getLocale();
		DoseTimePointsDetailsDto dto = studyService.getDoseTimePointsDtoDetails(studyId,
				StudyActivities.DosingCollection.toString(), languageId, currentLocale, mealType);
		return dto;
	}

	@RequestMapping(value = "/saveDosingCollectionData", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String saveDosingCollectionData(DoseDataDto doseDataDto, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		Date date = new Date();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		SubjectDoseTimePoints sdtp = clinicalService.saveDosingCollectionData(userId, date, doseDataDto, messageSource,
				currentLocale, languageId, dateFormat);
		String result = "";
		if (sdtp != null) {
			SampleScheduleTimesCalculationThread.calculateScheduleTimesBasedOnSubjectDoseTime(sdtp, sscService, userId,
					"calculateScheduleTimes");
			String successMsg = messageSource.getMessage("label.dc.success", null, currentLocale);
			result = "{\"Success\": \"" + true + "\",\"Message\":\"" + successMsg + "\"}";
		} else {
			String failureMsg = messageSource.getMessage("label.dc.failure", null, currentLocale);
			result = "{\"Success\": \"" + false + "\",\"Message\":\"" + failureMsg + "\"}";
		}
		return result;

	}

	/**
	 * Controller to fetch required data for Centrifugation
	 * 
	 * @param studyId
	 * @param request
	 * @return CetrifugationPageDataDto (contains all the VacutainerBarcodes,
	 *         , Current study centrifugation done details, available All Centrifuge instrument details,
	 *         , Collected Subject sample time point data )
	 */
	@RequestMapping(value = "/cetrifugationPageData/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody CetrifugationPageDataDto cetrifugationPageData(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		try {
			SeparationVacutinerDto separationVacutinerDto = barcodeService.separationVacutinerDto(studyId);
			StudySampleCentrifugation studySampleCentrifugation = barcodeService
					.studySampleCentrifugationDetails(studyId);
			StorageVacutinerDto storageVacutinerDto = new StorageVacutinerDto();
			storageVacutinerDto.setInstruments(clinicalService.getInstrumentList("Centrifuge"));
			CentrificationDto centrificationDto = clinicalService.collectedSampleVacutainers(studyId);

			CetrifugationPageDataDto dto = new CetrifugationPageDataDto();
			dto.setSeparationVacutinerDto(separationVacutinerDto);
			dto.setStudySampleCentrifugation(studySampleCentrifugation);
			dto.setStorageVacutinerDto(storageVacutinerDto);
			dto.setCentrificationDto(centrificationDto);
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return new CetrifugationPageDataDto();
		}
	}

	@RequestMapping(value = "/fechtVacutainerBarcodes/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SeparationVacutinerDto fechtVacutainerBarcodes(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		try {
			return barcodeService.separationVacutinerDto(studyId);
		} catch (Exception e) {
			e.printStackTrace();
			return new SeparationVacutinerDto();
		}
	}

	@RequestMapping(value = "/studyCentrifugeDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StudySampleCentrifugation studyCentrifugeDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		return barcodeService.studySampleCentrifugationDetails(studyId);
	}

	/**
	 * Controller to fetch required data for Sample Separation
	 * 
	 * @param studyId
	 * @param request
	 * @return CetrifugationPageDataDto (contains Collected Subject sample time point data,
	 * 			, all the Sample Time point and centrifuged details )
	 */
	@RequestMapping(value = "/sampleSeparationDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SampleSeparationDetailsDto sampleSeparationDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		try {
			SampleSeparationDetailsDto dto = new SampleSeparationDetailsDto();
			dto.setCentrificationDto(clinicalService.collectedSampleVacutainers(studyId));
			dto.setSeparationVacutinerDto(barcodeService.separationVacutinerDto(studyId));
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
			return new SampleSeparationDetailsDto();
		}

	}

	@RequestMapping(value = "/colectedSampleVacutainers/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody CentrificationDto colectedSampleVacutainers(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		return clinicalService.collectedSampleVacutainers(studyId);
	}

	@RequestMapping(value = "/centrifugationSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String centrifugationSave(CentrifugationDateDto centrifugationDate, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		System.out.println(centrifugationDate);
		String result = studyClinicalBo.centrifugationSave(centrifugationDate, userId);
		return result;
	}

	@RequestMapping(value = "/studyPeriods/{studyId}", method = RequestMethod.GET)
	public @ResponseBody String studyPeriods(@PathVariable("studyId") Long studyId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		StudyMaster study = studyService.findByStudyId(studyId);
		return studyService.studyPeriodMasterSelectBox(study);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/periodCentrifugeDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<Long, List<CentrifugationDataMaster>> periodCentrifugeDetails(
			@PathVariable("studyId") Long studyId, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws FileNotFoundException {
		StudyMaster study = studyService.findByStudyId(studyId);
		return (Map<Long, List<CentrifugationDataMaster>>) clinicalService.centrifugationDataMasterAll(studyId);
	}

	private String addPrefixZeros(String value, int i) {
		while (value.length() < i) {
			value = "0" + value;
		}
		return value;
	}

	@RequestMapping(value = "/subjectBarcodeInfo/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String subjectBarcodeInfo(@PathVariable("barcode") String barcode,
			HttpServletRequest request) {
		Long studyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(studyId);
		return clinicalService.subjectBarcodeInfo(barcode, sm);
	}

	@RequestMapping(value = "/vacutainerBarcodeInfoWithSubject/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String vacutainerBarcodeInfoWithSubject(@PathVariable("barcode") String barcode,
			HttpServletRequest request) {
		try {
			return clinicalService.vacutainerBarcodeInfoWithSubjct(barcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1--0--Something went wrong in server.";
		}
	}

	@RequestMapping(value = "/sachetBarcodeInfo/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String sachetBarcodeInfo(@PathVariable("barcode") String barcode, HttpServletRequest request) {
		Long studyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		try {
			return clinicalService.sachetBarcodeInfo(barcode, studyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1--0--Something went wrong in server.";
		}

	}

	@RequestMapping(value = "/appropriateBox/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String appropriateBox(@PathVariable("barcode") String barcode, HttpServletRequest request) {
		return clinicalService.appropriateBox(barcode);
	}

	@RequestMapping(value = "/subjectBarcodeInfoFroDose/{barcode}/{sachet}", method = RequestMethod.GET)
	public @ResponseBody String subjectBarcodeInfoFroDose(@PathVariable("barcode") String barcode,
			@PathVariable("sachet") String sachet, HttpServletRequest request) {
		return clinicalService.subjectBarcodeInfoFroDose(barcode, sachet);
	}

	@RequestMapping(value = "/periodDoseData/{type}", method = RequestMethod.GET)
	public String periodDoseData(@PathVariable("type") String type, HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) {
		return "pages/dashboard/periodDoseDataSuperwise";
	}

	@RequestMapping(value = "/saveDoseSuperviseInfo/{id}", method = RequestMethod.GET)
	public String testDose(@PathVariable("id") String id, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		UserMaster user = userService.findById(userId);
		SubjectDoseTimePoints subjectDoseTimePoints = clinicalService.saveDoseSuperviseInfoTest(user, id);
		try {
			Map<String, String> s = new HashedMap();
			s.put("id", id);
//			s.put("data", subjectDoseTimePoints.getTimePoint()+);
			s.put("color", "blue");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(s);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, SseEmitter> map = TestWebSocket.dosingMap;
			for (Map.Entry<String, SseEmitter> emitters : map.entrySet()) {
				SseEmitter emitter = emitters.getValue();
				try {
					emitter.send(SseEmitter.event().name("dosingMap").data(json));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					TestWebSocket.dosingMap.remove(user.getUsername());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/administration/";
	}

	@RequestMapping(value = "/subjectMealsInfo/{barcode}/{selecteTimePointIds}/{periodId}", method = RequestMethod.GET)
	public @ResponseBody String subjectMealsInfo(@PathVariable("barcode") String barcode,
			@PathVariable("selecteTimePointIds") String selecteTimePointIds, @PathVariable("periodId") Long periodId,
			HttpServletRequest request) {
		Long studyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(studyId);
		String result = clinicalService.subjectMealsInfo(barcode, sm, periodId, selecteTimePointIds);
		System.out.println(result);
		return result;
	}

	@RequestMapping(value = "/stdClinicalSampleSaperation", method = RequestMethod.GET)
	public String stdClinicalSampleSaperation(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		return "pages/clinical/stdClinicalSampleSaperation";
	}

	@RequestMapping(value = "/vacutainerForSampleSeparation/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String vacutainerForSampleSeparation(@PathVariable("barcode") String barcode,
			HttpServletRequest request) {
		return clinicalService.vacutainerForSampleSeparation(barcode);
	}

	@RequestMapping(value = "/stdClinicalSampleSaperationSave", method = RequestMethod.POST)
	public String stdClinicalSampleSaperationSave(HttpServletRequest request, HttpServletResponse response,
			ModelMap model, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAttribute("PageHedding", "Sample Separation");
		model.addAttribute("activeUrl", "/study/clinical/stdClinicalSampleSaperation");

		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long studyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		Long userId = (Long) request.getSession().getAttribute("userId");

		Date date = new Date();
		String vacutainer = request.getParameter("vacutainer");
		String vacutainerScanTime = request.getParameter("vacutainerScanTime");
		int noOfVials = Integer.parseInt(request.getParameter("noOfVials"));
		Map<Integer, String> vials = new HashMap<>();
		Map<Integer, String> vialScan = new HashMap<>();
		for (int i = 1; i <= noOfVials; i++) {
			vials.put(i, request.getParameter("vial" + i));
			vialScan.put(i, request.getParameter("vialScan" + i));
		}

		String result = clinicalService.saveSampleSaperation(date, vacutainer, vials, noOfVials, studyId, userId,
				vacutainerScanTime, vialScan);
		redirectAttributes.addFlashAttribute("pageMessage", result);
		return "redirect:/study/clinical/stdClinicalSampleSaperation";
	}

	@RequestMapping(value = "/subjectVitalInfo/{barcode}/{selecteTimePointIds}/{periodId}", method = RequestMethod.GET)
	public @ResponseBody String subjectVitalInfo(@PathVariable("barcode") String barcode,
			@PathVariable("selecteTimePointIds") String selecteTimePointIds, @PathVariable("periodId") Long periodId,
			HttpServletRequest request) {
		Long studyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(studyId);
		String result = clinicalService.subjectVitalInfo(barcode, sm, periodId, selecteTimePointIds);
		System.out.println(result);
		return result;
	}

	@RequestMapping(value = "/shelfDetails/{shelfBarode}")
	public @ResponseBody String shelfDetails(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@PathVariable("shelfBarode") String shelfBarode) {
		return studyClinicalBo.shelfDetails(shelfBarode).toString();
	}

	@RequestMapping(value = "/deepfreezerDetails/{deepfreezerBarode}")
	public @ResponseBody String deepfreezerDetails(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@PathVariable("deepfreezerBarode") String deepfreezerBarode) {
		return studyClinicalBo.deepfreezerDetails(deepfreezerBarode).toString();
	}

	@RequestMapping(value = "/smapleStorageConformSave")
	public @ResponseBody String smapleStorageConformSave(HttpServletRequest request,
			RedirectAttributes redirectAttributes, @RequestParam("centrifugeDataMasterId") Long centrifugeDataMasterId,
			@RequestParam("runningTimeWithSeconds") String runningTimeWithSeconds,
			@RequestParam("missingSampleStorage") String missingSampleStorage,
			@RequestParam("missingSubjectsStorage") String missingSubjectsStorage,
			@RequestParam("commentsStorage") String commentsStorage,
			@RequestParam("commentsSubjectsSeparation") String commentsSubjectsStorage,
			@RequestParam("commentStorage") String commentStorage, @RequestParam("shelfbarcode") String shelfbarcode,
			@RequestParam("deepfreezerBarcode") String deepfreezerBarcode, @RequestParam("aloquotNo") int aloquotNo) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		return studyClinicalBo.smapleStorageConformSave(centrifugeDataMasterId, runningTimeWithSeconds,
				missingSampleStorage, missingSubjectsStorage, commentsStorage, commentsSubjectsStorage, commentStorage,
				shelfbarcode, deepfreezerBarcode, aloquotNo, userId).toString();
	}

	@RequestMapping(value = "/centrifugation", method = RequestMethod.GET)
	public String centrifugation(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long userId = (Long) request.getSession().getAttribute("userId");
		model.addAttribute("studys", sexeService.getStudyMasterList(userId));
		return "pages/clinical/centrifugation";
	}

	@RequestMapping(value = "/centrifugeDetails/{studyId}/", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StorageVacutinerDto centrifugeDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		try {
			StorageVacutinerDto dto = new StorageVacutinerDto();
			dto.setCentrifugationList(clinicalService.sentrifugationDataMasters(studyId));
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
//			return new InstumentDto();
			return null;
		}
	}

	@RequestMapping(value = "/periodWiseSampleTimePoitns/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StorageVacutinerDto periodWiseSampleTimePoitns(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		return clinicalService.periodWiseSampleTimePoitns(studyId);
	}

	@RequestMapping(value = "/instumentInfo/{centrifuge}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody StorageVacutinerDto instumentInfo(@PathVariable("centrifuge") String centrifuge,
			HttpServletRequest request) {
		try {
			StorageVacutinerDto dto = new StorageVacutinerDto();
			dto.setInstruments(clinicalService.getInstrumentList(centrifuge));
//			dto.setCentrifugationList(clinicalService.sentrifugationDataMasters());
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
//			return new InstumentDto();
			return null;
		}
	}

	@RequestMapping(value = "/centrifugeInstumentInfo/{barcode}", method = RequestMethod.GET)
	public @ResponseBody String centrifugeInstumentInfo(@PathVariable("barcode") String barcode,
			HttpServletRequest request) {
		try {
			return clinicalService.centrifugeInstumentInfo(barcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1--0--Something went wrong in server.";
		}
	}

	@RequestMapping(value = "/centrifugeVacutainerInfo/{barcode}/{studyId}", method = RequestMethod.GET)
	public @ResponseBody String centrifugeVacutainerInfo(@PathVariable("barcode") String barcode,
			@PathVariable("studyId") String studyId, HttpServletRequest request) {
		try {
			return clinicalService.centrifugeVacutainerInfo(barcode, studyId);
		} catch (Exception e) {
			e.printStackTrace();
			return "1--0--Something went wrong in server.";
		}
	}

	@RequestMapping(value = "/sampleSeparationPage/{centrifugeId}", method = RequestMethod.GET)
	public String sampleSeparationPage(@PathVariable("centrifugeId") Long centrifugeId, HttpServletRequest request,
			ModelMap model) {
		if (centrifugeId == 0l) {
			model.addAttribute("studySelection", true);
			Long userId = (Long) request.getSession().getAttribute("userId");
			model.addAttribute("studys", sexeService.getStudyMasterList(userId));
		} else {
			model.addAttribute("studySelection", false);
			model.addAttribute("centrifugationDataMaster", clinicalService.centrifugationDataMaster(centrifugeId));
		}
//		model.addAttribute("centrifugationDataMaster", clinicalService.centrifugationDataMaster(centrifugeId));
		return "/pages/clinical/sampleSeparation";
	}

	@RequestMapping(value = "/sampleSeparationSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String sampleSeparationSave(CentrifugationDateDto sampleSeparationDate,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		System.out.println(sampleSeparationDate);
		return studyClinicalBo.sampleSeparationSave(sampleSeparationDate, userId);
	}

	@RequestMapping(value = "/sampleStoratePage/{centrifugeId}", method = RequestMethod.GET)
	public String sampleStoratePage(@PathVariable("centrifugeId") Long centrifugeId, HttpServletRequest request,
			ModelMap model) {
		if (centrifugeId == 0l) {
			model.addAttribute("studySelection", true);
			Long userId = (Long) request.getSession().getAttribute("userId");
			model.addAttribute("studys", sexeService.getStudyMasterList(userId));
		} else {
			model.addAttribute("studySelection", false);
			model.addAttribute("centrifugationDataMaster", clinicalService.centrifugationDataMaster(centrifugeId));
		}
//		model.addAttribute("centrifugationDataMaster", clinicalService.centrifugationDataMaster(centrifugeId));
		return "/pages/clinical/sampleStoratePage";
	}

	@RequestMapping(value = "/sampleStorageSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String sampleStorageSave(CentrifugationDateDto sampleSeparationDate,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		System.out.println(sampleSeparationDate);
		return studyClinicalBo.sampleStorageSave(sampleSeparationDate, userId);
	}

	@RequestMapping(value = "/vialRackCollection/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody VialRackCollectionDto vialRackCollection(@PathVariable("studyId") Long studyId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		StudyMaster study = studyService.findByStudyId(studyId);
		VialRackCollectionDto dto = new VialRackCollectionDto();
		dto.setSubjects(barcodeService.allStudySubjects(studyId));
		dto.setSubjectBarocodes(barcodeService.subjectBarcodes(dto.getSubjects(), study));
		dto.setSubjectPerods(barcodeService.subjectPeriods(studyId, dto.getSubjectBarocodes()));
		dto.setSubjectDoseTimes(barcodeService.subjectDoseTimes(study.getId()));
		dto.setTreatment(barcodeService.allTreatment(study.getId()));
		dto.setVitalTimPoints(barcodeService.allVitalTimePoints(studyId));
		dto.setVitalTimePointsMap(barcodeService.vitalTimePointsMap(dto.getVitalTimPoints()));
		dto.setPerameters(barcodeService.dynamicParameters(studyId, languageId, currentLocale,
				com.covideinfo.enums.StudyActivities.StudyExecutionVitals.toString()));
		dto.setSampleTimePointCollectedData(barcodeService.getSampleTimePointDataWithStudyId(study.getId()));
		return barcodeService.getSampleTimePointDataWithStudyIdForKeyAndValue(study.getId(), dto); // key,value

	}

	@RequestMapping(value = "/vialRackSave", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String vialRackSave(VialRackDto vialRackDto, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String dateFormat = (String) request.getSession().getAttribute("clinicalDateTimeFormat");
		Date date = new Date();
		String result = clinicalService.vialRackSave(userId, vialRackDto, date, messageSource, currentLocale,
				languageId, dateFormat);
		return result;

	}

	@RequestMapping(value = "/fechtSegregationDetails/{studyId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody SegrigationDto fechtSegregationDetails(@PathVariable("studyId") Long studyId,
			HttpServletRequest request) {
		return clinicalService.separationVacutinerDto(studyId);
	}

	@RequestMapping(value = "/seggrigationSave", method = RequestMethod.POST)
//	, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
	public StreamingResponseBody seggrigationSave(SeggrigationDataDto dto, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		Long userId = (Long) request.getSession().getAttribute("userId");
		System.out.println(dto);
		SampleContainer result = studyClinicalBo.seggrigationSave(dto, userId);

		String realPath = request.getSession().getServletContext().getRealPath("/");

		String fileName = barcodeService.generateSampleContainerBarcode(result, realPath, request);
		try {
			System.out.println(fileName);
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Sample_Continer.pdf");
			response.setContentLength((int) file.length());
			Long languageId = (Long) request.getSession().getAttribute("languageId");
			Locale currentLocale = LocaleContextHolder.getLocale();
			String success_Msg = messageSource.getMessage("label.success", null, currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			redirectAttributes.addFlashAttribute("pageMessage", " Sample Continer barcodes created Successfully.");
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			return null;
		}
	}

}