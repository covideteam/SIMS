package com.covideinfo.dao;

import com.covideinfo.dto.DrugDispancingPdfDto;

public interface DrugDispensingPdfDao {
	
	DrugDispancingPdfDto DrugDispancingPdfDtoDetailsForDownlodPdf();

	DrugDispancingPdfDto getDrugDispancingPdfDetails(Long studyId, Long userId, Long periodId);

	

}
