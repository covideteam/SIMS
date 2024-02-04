package com.covideinfo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.covideinfo.dto.DrugDispancingPdfDto;

public interface DrugDispensingPdfService {
	
	DrugDispancingPdfDto DrugDispancingPdfDtoDetails();

	void downloadDrugDispansingPdf(HttpServletRequest request, HttpServletResponse response, Long studyId, Long userId, Long periodId, String dateStrWithTime, String dateStr);

	

}
