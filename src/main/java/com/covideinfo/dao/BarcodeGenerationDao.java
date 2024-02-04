package com.covideinfo.dao;

import com.covideinfo.dto.BarcodeDetailsDto;

public interface BarcodeGenerationDao {

	BarcodeDetailsDto getBarcodesDtoDetails(Long studyId, String barcodeType);

	BarcodeDetailsDto getBarcodesDtoDetailsForBarcodesPrint(Long studyId, Long periodId, String type);

}
