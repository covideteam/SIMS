package com.covideinfo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.BarcodesDto;
import com.covideinfo.dto.SampleTimepointDto;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.service.BarcodeGenerationService;
import com.covideinfo.service.StudyExecutionServiece;

@Controller
@RequestMapping("/barcodes")
public class BarcodeGenerationController {
	
	
	@Autowired
	private Environment environment;
	@Autowired
	BarcodeGenerationService barcodeGenService;
	
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getbarcodePage/{studyId}/{barcodeType}", method = RequestMethod.GET)
	public String sutyExecutionActivity(ModelMap model, @PathVariable("studyId") Long studyId,
			@PathVariable("barcodeType") String barcodeType, HttpServletRequest request) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long languageId = (Long) request.getSession().getAttribute("languageId");
		if (barcodeType.equals("SUBJECT")) {
			return stdSubjectBarcodes(model, studyId, barcodeType);
		} else if (barcodeType.equals("VACUTAINER")) {
			return getVacutainersPage(model, studyId, barcodeType);
		} else if (barcodeType.equals("VIAL")) {
			return stdVialBarcodes(model, studyId, barcodeType);
		} else if (barcodeType.equals("SACHET")) {
			return stdSachetBarcodes(model, studyId, barcodeType);
		} else if (barcodeType.equals("ECG")) {
			return stdEcgBarcodes(model, studyId, barcodeType);
		}  else{
			return null;
		}
	}
	
	private String stdEcgBarcodes(ModelMap model, Long studyId, String barcodeType) {
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetails(studyId, barcodeType);
		model.addAttribute("spmList", bcDto.getSpmList());
		return "/pages/barcode/stdEcgBarcodesPage";
	}

	public String stdSubjectBarcodes(ModelMap model, Long studyId, String barcodeType) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetails(studyId, barcodeType);
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Subject", bcDto.getStudyGroupStanbysCount());
		model.addAttribute("spmList", bcDto.getSpmList());
		model.addAttribute("subjectNosMap", subjectsMap);
		return "/pages/barcode/stdSubjectBarcodes";
	}
	
	@RequestMapping(value = "/stdSachetBarcodes", method = RequestMethod.GET)
	public String stdSachetBarcodes(ModelMap model, Long studyId, String barcodeType) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetails(studyId, barcodeType);
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Sachet", bcDto.getStudyGroupStanbysCount());
		model.addAttribute("subjectNosMap", subjectsMap);
		model.addAttribute("spmList", bcDto.getSpmList());
//		model.addAttribute("timePoints", barcodeService.clinicalSampleTimePointsExceptDose(sm));
		return "/pages/barcode/stdSachetBarcodes";
	}
	public String stdVialBarcodes(ModelMap model, Long studyId, String barcodeType) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetails(studyId, barcodeType);
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Vials", bcDto.getStudyGroupStanbysCount());
		model.addAttribute("subjectNosMap", subjectsMap);
		model.addAttribute("spmList", bcDto.getSpmList());
//		model.addAttribute("timePoints", barcodeService.clinicalSampleTimePointsExceptDose(sm));
		return "/pages/barcode/stdVialBarcodes";

	}
	
	public String getVacutainersPage(ModelMap model, Long studyId, String barcodeType) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetails(studyId, barcodeType);
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Vacutainer", bcDto.getStudyGroupStanbysCount());
		model.addAttribute("subjectNosMap", subjectsMap);
		model.addAttribute("spmList", bcDto.getSpmList());
		model.addAttribute("timePoints", bcDto.getSampTpList());
		return "/pages/barcode/vacutainersBarcodePage";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/stdSubjectBarcodePrint/{studyId}/{type}/{subjectNo}/{periodId}", method = RequestMethod.GET)
	public StreamingResponseBody stdSubjectBarcodePrint(@PathVariable("studyId") Long studyId,
			@PathVariable("type") String type, @PathVariable("subjectNo") String subjectNo,@PathVariable("periodId") Long periodId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes)
			throws FileNotFoundException {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId,periodId, "Subject");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Subject", bcDto.getStudyGroupStanbysCount());
		Locale currentLocale = LocaleContextHolder.getLocale();
		String fileName = barcodeGenService.generateSubjectBarcode(bcDto, subjectNo, request,periodId, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, response, type, subjectsMap);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=SubjectBarcodes.pdf");
			response.setContentLength((int) file.length());
			return outputStream -> {
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			};
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/stdSachetBarcodesPrint/{studyId}/{periodId}", method = RequestMethod.GET)
	public void stdSachetBarcodesPrint(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId, HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String claietName = environment.getRequiredProperty("claietName");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId, periodId, "Sachet");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Sachet", bcDto.getStudyGroupStanbysCount());
		String fileName = barcodeGenService.generateSachetBarcode(periodId, request, response, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, claietName, bcDto, subjectsMap);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=sachet_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/stdVacutainerBarcodePrint/{studyId}/{periodId}", method = RequestMethod.GET)
	public StreamingResponseBody stdVacutainerBarcodePrint(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String claietName = environment.getRequiredProperty("claietName");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId, periodId, "Vacutainer");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Vacutainer", bcDto.getStudyGroupStanbysCount());
		String fileName = barcodeGenService.generateVacutainersBarcodeAll(periodId, request, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, claietName, subjectsMap, bcDto);
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
	@RequestMapping(value = "/stdVacutainerBarcodePrintTimePointWise/{studyId}/{periodId}/{subjectNo}/{timePointId}", method = RequestMethod.GET)
	public StreamingResponseBody stdVacutainerBarcodePrintTimePointWise(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId,
			@PathVariable("subjectNo") String subjNo,@PathVariable("timePointId") Long timePointId,HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String claietName = environment.getRequiredProperty("claietName");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId, periodId, "Vacutainer");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Vacutainer", bcDto.getStudyGroupStanbysCount());
		String fileName = barcodeGenService.stdVacutainerBarcodePrintTimePointWise(periodId, request, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, claietName, subjectsMap, bcDto, subjNo, timePointId);
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
	@SuppressWarnings("unused")
	@RequestMapping(value = "/stdVialBarcodesPrint/{studyId}/{periodId}/{subjectNo}/{timePointId}", method = RequestMethod.GET)
	public void stdVialBarcodesPrint(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId,
			@PathVariable("subjectNo") String subjNo,@PathVariable("timePointId") Long timePointId,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
			RedirectAttributes redirectAttributes) throws FileNotFoundException {
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String claietName = environment.getRequiredProperty("claietName");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId,periodId, "Vacutainer");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Vial", bcDto.getStudyGroupStanbysCount());
		String fileName = barcodeGenService.generateVialBarcodePeriodWise(periodId, request, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, claietName, bcDto, subjectsMap,
				subjNo, timePointId);
		Locale currentLocale = LocaleContextHolder.getLocale();
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=Vial_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/stdEcgBarcodesGeneration/{studyId}/{periodId}", method = RequestMethod.GET)
	public void stdEcgBarcodesGeneration(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId, HttpServletRequest request, HttpServletResponse response) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String noOfColumns = environment.getRequiredProperty("noOfLableColumns");
		String studyIdCardBarcodeLable = environment.getRequiredProperty("barcodeLablePrint");
		String barcodePrinterip = environment.getRequiredProperty("barcodePrinterip");
		String claietName = environment.getRequiredProperty("claietName");
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		BarcodesDto bcDto = barcodeGenService.getBarcodesDtoDetailsForBarcodesPrint(studyId, periodId, "Sachet");
		Map<String, String> subjectsMap = barcodeGenService.getSubjectsMap(bcDto.getStudy(), standByPrefix, "Sachet", bcDto.getStudyGroupStanbysCount());
		String fileName = barcodeGenService.generateSachetBarcode(periodId, request, response, noOfColumns, studyIdCardBarcodeLable, barcodePrinterip, claietName, bcDto, subjectsMap);
		try {
			File file = new File(fileName);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=sachet_Barcodes.pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "/subjectSampleTimePoitns/{studyId}/{periodId}/{subjectNo}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<SampleTimepointDto> measlCollection(@PathVariable("studyId") Long studyId,@PathVariable("periodId") Long periodId,@PathVariable("subjectNo") String subjectNo,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws FileNotFoundException {
		List<SampleTimepointDto> stpDto = barcodeGenService.subjectAllTimepoints(studyId, periodId, subjectNo);
		return stpDto;
	}
}
