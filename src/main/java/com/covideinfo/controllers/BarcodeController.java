package com.covideinfo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.dummy.BarcodeBoxData;
import com.covideinfo.model.dummy.BarcodeSubjectContainerData;
import com.covideinfo.service.BarcodeService;
import com.covideinfo.service.StudyExecutionServiece;
import com.covideinfo.service.StudyService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/barcodelabels")
@PropertySource(value = { "classpath:application.properties" })
public class BarcodeController {
	@Autowired
	private Environment environment;
	@Autowired
	BarcodeService barcodeService;
	@Autowired
	StudyService studyService;
	@Autowired  
	MessageSource messageSource;

	@Autowired
	StudyExecutionServiece sexeService;

	@RequestMapping(value = "/barcodelabelsPage", method = RequestMethod.GET)
	public String barcodelabels(RedirectAttributes redirectAttributes, ModelMap model, HttpServletRequest request) {
		Long userId = (Long) request.getSession().getAttribute("userId");
		List<StudyMaster> smList = sexeService.getStudyMasterList(userId);
		model.addAttribute("smList", smList);
		model.addAttribute("screenType", "activity");
		return "barcodelabelsPrintPage";
	}

	@RequestMapping(value = "/getbarcodePage/{studyId}/{barcodeType}", method = RequestMethod.GET)
	public String sutyExecutionActivity(ModelMap model, @PathVariable("studyId") Long studyId,
			@PathVariable("barcodeType") String barcodeType, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		if (barcodeType.equals("SUBJECT")) {
			return stdSubjectBarcodes(model, studyId);
		} else if (barcodeType.equals("VACUTAINER")) {
			return getVacutainersPage(model, studyId);
		} else if (barcodeType.equals("VIAL")) {
			return stdVialBarcodes(model, studyId);
		} else if (barcodeType.equals("SACHET")) {
			return stdSachetBarcodes(model, studyId);
		} else{
			
			return null;
		}
	}
	
	@RequestMapping(value = "/stdSachetBarcodes", method = RequestMethod.GET)
	public String stdSachetBarcodes(ModelMap model, Long studyId) {
		StudyMaster sm = barcodeService.findByStudyId(studyId);
		model.addAttribute("subjectNosMap", subjectList(sm));
		model.addAttribute("spmList", barcodeService.allStudyPeriodsWithSubEle(sm));
		model.addAttribute("timePoints", barcodeService.clinicalSampleTimePointsExceptDose(sm));
		return "/pages/barcode/stdSachetBarcodes";
	}
	public String stdVialBarcodes(ModelMap model, Long studyId) {
		StudyMaster sm = barcodeService.findByStudyId(studyId);
		model.addAttribute("subjectNosMap", subjectList(sm));
		model.addAttribute("spmList", barcodeService.allStudyPeriodsWithSubEle(sm));
		model.addAttribute("timePoints", barcodeService.clinicalSampleTimePointsExceptDose(sm));
		return "/pages/barcode/stdVialBarcodes";

	}
	
	public String getVacutainersPage(ModelMap model, Long studyId) {
		StudyMaster sm = barcodeService.findByStudyId(studyId);
		model.addAttribute("subjectNosMap", subjectList(sm));
		model.addAttribute("spmList", barcodeService.allStudyPeriodsWithSubEle(sm));
		model.addAttribute("timePoints", barcodeService.clinicalSampleTimePointsExceptDose(sm));
		return "/pages/barcode/vacutainersBarcodePage";
	}

	public String stdSubjectBarcodes(ModelMap model, Long studyId) {
		StudyMaster sm = studyService.findByStudyId(studyId);
		model.addAttribute("spmList", barcodeService.allStudyPeriodsWithSubEle(sm));
		model.addAttribute("subjectNosMap", subjectList(sm));
		return "/pages/barcode/stdSubjectBarcodes";
	}

	private Map<String, String> subjectList(StudyMaster sm) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		//Map<String, String> subjectNosMap = new HashMap<>();
		TreeMap<String, String> subjectNosMap= new TreeMap<String, String>();
		for (int subjectNo = 1; subjectNo <= sm.getNoOfSubjects(); subjectNo++) {
			if (subjectNo < 10)
				subjectNosMap.put(subjectNo + "," + "0" + subjectNo, "0" + subjectNo);
			else
				subjectNosMap.put(subjectNo + "," + subjectNo, "" + subjectNo);
		}
		for (int subjectNo = 1; subjectNo <= sm.getNoOfStandBySubjects(); subjectNo++) {
			subjectNosMap.put(subjectNo + sm.getNoOfSubjects() + "," + standByPrefix + subjectNo,
					standByPrefix + subjectNo);
		}
		return subjectNosMap;
	}

	@RequestMapping(value = "/stdSubjectBarcodePrint/{studyId}/{type}/{subjectNo}/{periodId}", method = RequestMethod.GET)
	public StreamingResponseBody stdSubjectBarcodePrint(@PathVariable("studyId") Long studyId,
			@PathVariable("type") Integer type, @PathVariable("subjectNo") String subjectNo,@PathVariable("periodId") Long periodId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String value[]=subjectNo.split(",");
		
		StudyMaster sm = barcodeService.findByStudyId(studyId);
		//StudyPeriodMaster psm = barcodeService.getStudyPeriodMasterWithId(periodId);
		StudyPeriodMaster psm=null;
		List<StudyPeriodMaster> psmLista=barcodeService.getStudyPeriodMasterWithStudyList(sm);
		Locale currentLocale = LocaleContextHolder.getLocale();

	//	List<Long> psmList=barcodeService.getStudyPeriodMasterWithStudy(sm);
		//StudyTreatmentWiseSubjects stws=barcodeService.getStudyTreatmentWiseSubjectsWithPeriodAndStudy(sm,psm,value[1]);
		 
		
		//List<SubjectRandamization> ranList=barcodeService.getSubjectRandamizationWithPeriods(psmList);
		SubjectRandamization st=null;
		/*if (type == 1) {
         st=barcodeService.getSubjectRandamizationWithPeriodAndSubject(psm,value[0]);
		}else {
		 st=barcodeService.getSubjectRandamizationWithPeriodAndSubject(psm,value[1]);
		}*/
		
		String fileName = null;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if (type == 1)
			fileName = barcodeService.generateSubjectBarcodeAll(sm, realPath,st,psmLista);
		else {
			String bedNo = request.getParameter("subject");
			fileName = barcodeService.generateSubjectBarcode(sm, subjectNo, realPath,psm,st);
		}
		try {
			System.out.println(fileName);
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=SubjectBarcodes.pdf");
			response.setContentLength((int) file.length());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			redirectAttributes.addFlashAttribute("pageMessage", " Subject barcodes created Successfully.");
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			return null;
		}

	}

	@RequestMapping(value = "/stdVacutainerBarcodePrint/{periodId}", method = RequestMethod.GET)
	public StreamingResponseBody stdVacutainerBarcodePrint(@PathVariable("periodId") Long periodId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateVacutainersBarcodeAll(periodId, realPath);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vacutainer_Barcodes.pdf");
			response.setContentLength((int) file.length());
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//Get All Vacutainer Barcod Data
	@RequestMapping(value = "/stdVacutainerBarcodeForAllPrint/{periodId}", method = RequestMethod.GET)
	public StreamingResponseBody stdVacutainerBarcodeForAllPrint(@PathVariable("periodId") Long periodId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.stdVacutainerBarcodeForAllPrint(periodId, realPath);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vacutainer_Barcodes.pdf");
			response.setContentLength((int) file.length());
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	@RequestMapping(value = "/stdVacutainerBarcodePrintTimePointWise/{periodId}/{timepointId}", method = RequestMethod.GET)
	public StreamingResponseBody stdVacutainerBarcodePrintTimePointWise(@PathVariable("periodId") Long periodId,
			@PathVariable("timepointId") Long timepointId, HttpServletRequest request, HttpServletResponse response,
			ModelMap model, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateVacutainersBarcodeAllTimePointWise(periodId, timepointId, realPath);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vacutainer_Barcodes.pdf");
			response.setContentLength((int) file.length());
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/stdVacutainerBarcodePrintSubjectWise/{periodId}/{subjectNo}", method = RequestMethod.GET)
	public void stdVacutainerBarcodePrintSubjectWise(@PathVariable("periodId") Long periodId,
			@PathVariable("subjectNo") String subjectNo, HttpServletRequest request, HttpServletResponse response,
			ModelMap model, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateVacutainersBarcodeAllSubjectWise(periodId, subjectNo, realPath);

		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Subjct_Vacutainer_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", " Vacutainer barcodes created Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vacutainer barcodes creation Faild.");
		}

	}

	@RequestMapping(value = "/stdVacutainerBarcodePrintSubjectWiseForTimePoint/{periodId}/{subjectNo}/{timePointId}", method = RequestMethod.GET)
	public void stdVacutainerBarcodePrintSubjectWiseForTimePoint(@PathVariable("periodId") Long periodId,
			@PathVariable("subjectNo") String subjectNo, @PathVariable("timePointId") Long timePointId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateVacutainersBarcodeAllSubjectWiseForTimePoint(periodId, subjectNo, timePointId, realPath);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Subjct_Vacutainer_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", " Vacutainer barcodes created Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vacutainer barcodes creation Faild.");
		}

	}

	@RequestMapping(value = "/stdVialBarcodesPrint/{periodId}", method = RequestMethod.GET)
	public void stdVialBarcodesPrint(@PathVariable("periodId") Long periodId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
//		String fileName = barcodeService.generateVacutainersBarcodeAllSubjectWiseForTimePoint(periodId, subjectNo, timePointId, realPath);
		String fileName = barcodeService.generateVialBarcodePeriodWise(periodId, realPath);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vial_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			redirectAttributes.addFlashAttribute("pageMessage", " Vial barcodes created Successfully.");
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vial barcodes creation Faild.");
		}

	}
	
	@RequestMapping(value = "/stdSachetBarcodesPrint/{periodId}", method = RequestMethod.GET)
	public void stdSachetBarcodesPrint(@PathVariable("periodId") Long periodId, HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateSachetBarcode(periodId, realPath);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=sachet_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			redirectAttributes.addFlashAttribute("pageMessage", " Vacutainer barcodes created Successfully.");
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vacutainer barcodes creation Faild.");
		}
	}
	
	@RequestMapping(value = "/getSubjectTimePoints/{ordreNo}/{volId}", method = RequestMethod.GET)
	public String getVialTimePoints(@PathVariable("ordreNo") long period, @PathVariable("volId") String volId,
			HttpServletRequest request, ModelMap model) {
		// Long activeStudyId = (Long)
		// request.getSession().getAttribute("activeStudyId");
		Long activeStudyId = 2l;
		StudyMaster sm = barcodeService.findByStudyId(activeStudyId);
		List<SubjectSampleCollectionTimePoints> timePoints = barcodeService.subjectSampleTimePoints(sm, period, volId);
		model.addAttribute("timePoints", timePoints);
		return "pages/barcode/SubjectTimePointsInfo";
	}

	

	

	

	

	@RequestMapping(value = "/stdSubjectContainerPrint", method = RequestMethod.GET)
	public String stdSubjectContainerPrint(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {

		model.addAllAttributes(redirectAttributes.getFlashAttributes());

		return "stdSubjectContainer.tiles";
	}

	@RequestMapping(value = "/subjectContainerBarCodePrint")
	public void subjectContainerBarCodePrint(@RequestParam("jsonVal") String jsonVal, ModelMap model,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws JsonParseException, JsonMappingException, IOException {
		String username = request.getSession().getAttribute("userName").toString();
		ObjectMapper map = new ObjectMapper();
		BarcodeSubjectContainerData bsData = map.readValue(jsonVal, BarcodeSubjectContainerData.class);
		if (bsData != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();
			String fileName = barcodeService.generateSubjectContainerBarCodePrint(bsData, username, request);

			try {
				File file = new File(fileName);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=SubjectContainer_Barcodes.pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				// Copy bytes from source to destination(outputstream in this example), closes
				// both streams.
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				redirectAttributes.addFlashAttribute("pageMessage",
						" Subject Container barcodes created Successfully.");
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
			} catch (Exception e) {
				e.printStackTrace();
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
				redirectAttributes.addFlashAttribute("pageError", " Subject Container barcodes creation Faild.");
			}
		}

	}

	@RequestMapping(value = "/boxBarCodePrintPage", method = RequestMethod.GET)
	public String boxBarCodePrintPage(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {

		model.addAllAttributes(redirectAttributes.getFlashAttributes());

		return "boxBarCodePrintPage.tiles";
	}

	@RequestMapping(value = "/boxBarCodePrint", method = RequestMethod.POST)
	public void boxBarCodePrint(@RequestParam("jsonVal") String jsonVal, ModelMap model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws JsonParseException, JsonMappingException, IOException {
		String username = request.getSession().getAttribute("userName").toString();
		ObjectMapper map = new ObjectMapper();
		BarcodeBoxData bsData = map.readValue(jsonVal, BarcodeBoxData.class);
		if (bsData != null) {

			String fileName = barcodeService.generateBoxBarCodePrint(bsData, username, request);
			Locale currentLocale = LocaleContextHolder.getLocale();
			try {
				File file = new File(fileName);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=Box_Barcodes.pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				// Copy bytes from source to destination(outputstream in this example), closes
				// both streams.
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				redirectAttributes.addFlashAttribute("pageMessage", " Box  barcodes created Successfully.");
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
			} catch (Exception e) {
				e.printStackTrace();
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
				redirectAttributes.addFlashAttribute("pageError", " Box  barcodes creation Faild.");
			}
		}

	}

	@RequestMapping(value = "/saveCentrifugation/{name}/{code}/{details}", method = RequestMethod.GET)
	public @ResponseBody String saveCentrifugation(@PathVariable("name") String name, @PathVariable("code") String code,
			@PathVariable("details") String detials, HttpServletRequest request, HttpServletResponse response,
			ModelMap model, RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		return studyService.saveCentrifugation(userId, name, code, detials);
	}

	@RequestMapping(value = "/centrifugation", method = RequestMethod.GET)
	public String centrifugation(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		model.addAttribute("centrifugations", studyService.centrifugeList());
		return "pages/barcode/centrifugation";
	}

	@RequestMapping(value = "/centrifugationBarcodesPrint", method = RequestMethod.POST)
	public void centrifugationBarcodesPrint(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {

		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Long varcodeId = Long.parseLong(request.getParameter("barcodeId"));

		String fileName = barcodeService.generateCentrifugationBarcode(varcodeId, request);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);
			file.createNewFile();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Centrifuge_Barcode.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", " Vial barcodes created Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vial barcodes creation Faild.");
		}
	}
	//Get Instrument List For Barcode
	@RequestMapping(value = "/instrumentBarCode", method = RequestMethod.GET)
	public String instrumentBarCode(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<Instrument> list=barcodeService.getInstrumentList();
		model.addAttribute("list", list);

		return "instrumentBarCodePrint";
	}
	//Print Instrument Barcode 
	@RequestMapping(value = "/instrumentBarCodePrint/{id}", method = RequestMethod.GET)
	public void instrumentBarCodePrint(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fileName = barcodeService.generateinstrumentBarcode(id,realPath,request);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=instrument_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", " instrument barcodes created Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " instrument barcodes creation Faild.");
		}
	}
	
	
	@RequestMapping(value = "/stdVialBarcodesAllPrint/{periodId}", method = RequestMethod.GET)
	public void stdVialBarcodesAllPrint(@PathVariable("periodId") Long periodId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String realPath = request.getSession().getServletContext().getRealPath("/");
//		String fileName = barcodeService.generateVacutainersBarcodeAllSubjectWiseForTimePoint(periodId, subjectNo, timePointId, realPath);
		String fileName = barcodeService.generateVialBarcodeAll(periodId, realPath);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vial_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			// Copy bytes from source to destination(outputstream in this example), closes
			// both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);
			redirectAttributes.addFlashAttribute("pageMessage", " Vial barcodes created Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);
			redirectAttributes.addFlashAttribute("pageError", " Vial barcodes creation Faild.");
		}
	}
	//Get Deep Freezer List For Barcode
	@RequestMapping(value = "/deepFreezerBarCode", method = RequestMethod.GET)
	public String deepFreezerBarCode(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<Deepfreezer> list=barcodeService.getDeepfreezerList();
		model.addAttribute("list", list);

		return "deepFreezerList";
	}
	
	@RequestMapping(value = "/deepFreezerShelfBarCode", method = RequestMethod.GET)
	public String deepFreezerShelfBarCode(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		List<DeepfreezerShelf> list=barcodeService.getDeepfreezerShelfList();
		model.addAttribute("list", list);
		return "deepFreezerShelfList";
	}
	
	//Print Instrument Barcode 
		@RequestMapping(value = "/deepfreezerBarCodePrint/{id}", method = RequestMethod.GET)
		public void deepfreezerBarCodePrint(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model,
				RedirectAttributes redirectAttributes) throws FileNotFoundException {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String fileName = barcodeService.generateDeepfreezerBarcode(id,realPath,request);
			Locale currentLocale = LocaleContextHolder.getLocale();
			try {
				File file = new File(fileName);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=deepfreezer_Barcodes.pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				// Copy bytes from source to destination(outputstream in this example), closes
				// both streams.
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
				redirectAttributes.addFlashAttribute("pageMessage", " deepfreezer barcodes created Successfully.");
			} catch (Exception e) {
				e.printStackTrace();
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
				redirectAttributes.addFlashAttribute("pageError", " instrument barcodes creation Faild.");
			}
		}
		
		@RequestMapping(value = "/deepfreezerShelfBarCodePrint/{id}", method = RequestMethod.GET)
		public void deepfreezerShelfBarCodePrint(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model,
				RedirectAttributes redirectAttributes) throws FileNotFoundException {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String fileName = barcodeService.generateDeepfreezerShelfBarcode(id,realPath,request);
			Locale currentLocale = LocaleContextHolder.getLocale();
			try {
				File file = new File(fileName);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=deepfreezer_Barcodes.pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				// Copy bytes from source to destination(outputstream in this example), closes
				// both streams.
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
				redirectAttributes.addFlashAttribute("success", success_Msg);
				redirectAttributes.addFlashAttribute("pageMessage", " deepfreezer barcodes created Successfully.");
			} catch (Exception e) {
				e.printStackTrace();
				String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
				redirectAttributes.addFlashAttribute("error", error_Msg);
				redirectAttributes.addFlashAttribute("pageError", " instrument barcodes creation Faild.");
			}
		}
}
