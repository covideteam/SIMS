package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.bo.SampleCollectionDashBoard;
import com.covideinfo.bo.SampleTimePointsData;
import com.covideinfo.bo.StudyClinicalBo;
import com.covideinfo.clinical.service.ClinicalService;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectSampleSeparationTimePointsData;
import com.covideinfo.model.SubjectTimePointVitalTests;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.service.StudyService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/clinicalReport")
@PropertySource(value = { "classpath:application.properties" })
public class StudyClinicalReportConroller {
	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	@Autowired
	StudyService studyService;

	@Autowired
	ClinicalService clinicalService;

	@Autowired
	StudyClinicalBo studyClinicalBo;

	@RequestMapping(value = "/stdClinicalReport/{type}", method = RequestMethod.GET)
	public String stdClinicalSampleCollectionReport(@PathVariable("type") String type, HttpServletRequest request,
			ModelMap model, RedirectAttributes redirectAttributes) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("periods", studyClinicalBo.studyPeriods(sm));
		model.addAttribute("vols", studyClinicalBo.studyAllSubjects(sm));
		model.addAttribute("type", type);
		return "pages/clinical/stdClinicalReport";
	}

	@RequestMapping(value = "/stdClinicalSampleCollectionReportTable/{periodId}/{volid}", method = RequestMethod.GET)
	public String stdClinicalSampleCollectionReportTable(@PathVariable("periodId") Long periodId,
			@PathVariable("volid") int volid, HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		model.addAttribute("dose", studyClinicalBo.subjectDoseTimePoints(periodId, volid));

		model.addAttribute("sampleCollectionData",
				studyClinicalBo.subjectSampleCollectionTimePointsSubjectData(periodId, volid));
		return "pages/clinical/sampelCollectionReportForPeriod";
	}

	@RequestMapping(value = "/clinicalDosingReportTable/{periodId}/{volid}", method = RequestMethod.GET)
	public String clinicalDosingReportTable(@PathVariable("periodId") Long periodId, @PathVariable("volid") int volid,
			HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		SubjectDoseTimePoints sdtp = studyClinicalBo.subjectDoseTimePoints(periodId, volid);
		model.addAttribute("dose", sdtp);
		model.addAttribute("dosePerameters", studyClinicalBo.subejectDosePerameter(sdtp));

//		model.addAttribute("dosingData", studyClinicalBo.subjectAllDoseTimePoints(sdtp));
		return "pages/clinical/clinicalDosingReportTable";
	}

	@RequestMapping(value = "/clinicalVitalReportTable/{periodId}/{volid}", method = RequestMethod.GET)
	public String clinicalVitalReportTable(@PathVariable("periodId") Long periodId, @PathVariable("volid") int volid,
			HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		List<SubjectVitalTimePoints> vitalCollectionData = studyClinicalBo.subjectVitalTimePoints(periodId, volid);
		model.addAttribute("vitalCollectionData", vitalCollectionData);
		List<SubjectTimePointVitalTests> test = vitalCollectionData.get(0).getTest();
		model.addAttribute("test", test);
//		model.addAttribute("dosingData", studyClinicalBo.subjectAllDoseTimePoints(sdtp));
		return "pages/clinical/clinicalVitalReportTable";
	}

	@RequestMapping(value = "/sampleSeparation/{periodId}/{volid}", method = RequestMethod.GET)
	public String sampleSeparation(@PathVariable("periodId") Long periodId, @PathVariable("volid") int volid,
			HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		List<SubjectSampleSeparationTimePointsData> sampleSeparationData = studyClinicalBo
				.subjectSampleSeparationTimePointsData(periodId, volid);
		model.addAttribute("sampleSeparationData", sampleSeparationData);

		return "pages/clinical/sampleSeparationReportTable";
	}

	@RequestMapping(value = "/mealCollection/{periodId}/{volid}", method = RequestMethod.GET)
	public String mealCollection(@PathVariable("periodId") Long periodId, @PathVariable("volid") int volid,
			HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		Map<Integer, List<SubjectMealsTimePoints>> mealCollectionData = studyClinicalBo.subjectMealsTimePoints(periodId,
				volid, false);
		if (mealCollectionData.get(0) != null)
			model.addAttribute("mealCollectionData", mealCollectionData.get(0));
		else
			model.addAttribute("mealCollectionData", new ArrayList<>());
		return "pages/clinical/mealCollectionReportTable";
	}
	
	@RequestMapping(value = "/ecgCollection/{periodId}/{volid}", method = RequestMethod.GET)
	public String ecgCollection(@PathVariable("periodId") Long periodId, @PathVariable("volid") int volid,
			HttpServletRequest request, ModelMap model) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));
		model.addAttribute("vol", studyClinicalBo.volunteerBySeqNo(sm, volid));
		Map<Integer, List<SubjectECGTimePoints>> ecgCollectionData = studyClinicalBo.subjectEcgTimePoints(periodId,
				volid, false);
		if (ecgCollectionData.get(0) != null)
			model.addAttribute("ecgCollectionData", ecgCollectionData.get(0));
		else
			model.addAttribute("ecgCollectionData", new ArrayList<>());
		return "pages/clinical/ecgCollectionReportTable";
	}

	@RequestMapping(value = "/sampleCollectionDashBordTable/{periodId}", method = RequestMethod.GET)
	public String stdClinicalSampleCollectionDashBordTable(@PathVariable("periodId") Long periodId,
			HttpServletRequest request, ModelMap model, HttpSession session) {
//		Long activeStudyId = (Long) request.getSession().getAttribute("activeStudyId");
//		Enumeration<String> attributes = session.getAttributeNames();
//		while (attributes.hasMoreElements()) {
//		    String attribute = (String) attributes.nextElement();
//		    System.out.println(attribute+" : "+request.getSession().getAttribute(attribute));
//		}
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

		List<SampleCollectionDashBoard> heading = clinicalService.sampleTimePointsDataOfPeriodHeading(sm);
		model.addAttribute("heading", heading);
		List<SampleTimePointsData> data = clinicalService.sampleTimePointsDataOfPeriod(periodId, sm);
		Collections.sort(data);
		model.addAttribute("data", data);

		return "pages/dashboard/sampelCollectionDashBord";

	}

	@RequestMapping(value = "/dosingDashBordTable/{periodId}", method = RequestMethod.GET)
	public String dosingDashBordTable(@PathVariable("periodId") Long periodId, HttpServletRequest request,
			ModelMap model, HttpSession session) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

		List<SampleTimePointsData> heading = clinicalService.dosingTimePointsOfPeriodHeading(sm);
		model.addAttribute("heading", heading);
		List<SampleTimePointsData> data = clinicalService.doseTimePointsDataOfPeriod(periodId, sm);
		Collections.sort(data);
		model.addAttribute("data", data);

		return "pages/dashboard/dosingDashBord";

	}

	@RequestMapping(value = "/sampleSapartionDashBordTable/{periodId}", method = RequestMethod.GET)
	public String sampleSapartionDashBordTable(@PathVariable("periodId") Long periodId, HttpServletRequest request,
			ModelMap model, HttpSession session) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

//		List<SampleTimePointsData> heading = clinicalService.sampleTimePointsDataOfPeriodHeading(sm);
//		model.addAttribute("heading", heading);
//		List<SampleTimePointsData> data = clinicalService.sampleSeparationTimePointsDataOfPeriod(periodId, sm);
//		Collections.sort(data);
//		model.addAttribute("data", data);

		return "pages/dashboard/sampelCollectionDashBord";

	}

	@RequestMapping(value = "/mealCollectionDashBordTable/{periodId}/{type}", method = RequestMethod.GET)
	public String mealCollectionDashBordTable(@PathVariable("periodId") Long periodId,
			@PathVariable("type") String type, HttpServletRequest request, ModelMap model, HttpSession session) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

		List<SampleTimePointsData> heading = clinicalService.mealTimePointsDataOfPeriodHeading(sm);
		model.addAttribute("heading", heading);
		List<SampleTimePointsData> data = clinicalService.mealTimePointsDataOfPeriod(periodId, sm);
		Collections.sort(data);
		model.addAttribute("data", data);
		model.addAttribute("type", type);
		return "pages/dashboard/sampelCollectionDashBord";

	}
	
	@RequestMapping(value = "/ecgCollectionDashBordTable/{periodId}/{type}", method = RequestMethod.GET)
	public String ecgCollectionDashBordTable(@PathVariable("periodId") Long periodId,
			@PathVariable("type") String type, HttpServletRequest request, ModelMap model, HttpSession session) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

		List<SampleTimePointsData> heading = clinicalService.ecgTimePointsDataOfPeriodHeading(sm);
		model.addAttribute("heading", heading);
		List<SampleTimePointsData> data = clinicalService.ecgTimePointsDataOfPeriod(periodId, sm);
		Collections.sort(data);
		model.addAttribute("data", data);
		model.addAttribute("type", type);
		return "pages/dashboard/sampelCollectionDashBord";

	}
	
	@RequestMapping(value = "/vitalCollectionDashBordTable/{periodId}/{type}", method = RequestMethod.GET)
	public String vitalCollectionDashBordTable(@PathVariable("periodId") Long periodId,
			@PathVariable("type") String type, HttpServletRequest request, ModelMap model, HttpSession session) {
		Long activeStudyId = Long.parseLong(request.getSession().getAttribute("studyId").toString());
		StudyMaster sm = studyService.findByStudyId(activeStudyId);
		model.addAttribute("study", sm);
		model.addAttribute("period", studyClinicalBo.studyPeriod(periodId));

		List<SampleTimePointsData> heading = clinicalService.vitalTimePointsDataOfPeriodHeading(sm);
		model.addAttribute("heading", heading);
		List<SampleTimePointsData> data = clinicalService.vitalTimePointsDataOfPeriod(periodId, sm);
		Collections.sort(data);
		model.addAttribute("data", data);
		model.addAttribute("type", type);
		return "pages/dashboard/sampelCollectionDashBord";

	}

}
