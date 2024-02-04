package com.covideinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.covideinfo.dto.BarcodesDto;
import com.covideinfo.dto.SampleTimepointDto;
import com.covideinfo.model.StudyMaster;

public interface BarcodeGenerationService {

	BarcodesDto getBarcodesDtoDetails(Long studyId, String barcodeType);

	Map<String, String> getSubjectsMap(StudyMaster study, String standByPrefix, String type, int groupStandByCount);

	BarcodesDto getBarcodesDtoDetailsForBarcodesPrint(Long studyId, Long periodId, String type);

	String generateSubjectBarcode(BarcodesDto bcDto, String subjectNo, HttpServletRequest request, Long periodId, String noOfColumns, String studyIdCardBarcodeLable, String barcodePrinterip, HttpServletResponse response, String type, Map<String, String> subjectsMap);

	String generateSachetBarcode(Long periodId, HttpServletRequest request, HttpServletResponse response, String noOfColumns, String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, BarcodesDto bcDto, Map<String, String> subjectsMap);

	String generateVacutainersBarcodeAll(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName,
			Map<String, String> subjectsMap, BarcodesDto bcDto);

	String generateVialBarcodePeriodWise(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, BarcodesDto bcDto,
			Map<String, String> subjectsMap, String subjNo, Long timePointId);

	String stdVacutainerBarcodePrintTimePointWise(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, Map<String, String> subjectsMap,
			BarcodesDto bcDto, String subjNo, Long timePointId);

	List<SampleTimepointDto> subjectAllTimepoints(Long studyId, Long periodId, String subjectNo);

}
