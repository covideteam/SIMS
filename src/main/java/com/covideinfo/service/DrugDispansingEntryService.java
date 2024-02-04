package com.covideinfo.service;

import com.covideinfo.dto.DrugDispansingEntryDetailsDto;

public interface DrugDispansingEntryService {

	DrugDispansingEntryDetailsDto getDrugDispansingDetails();

	DrugDispansingEntryDetailsDto getDrugDispansingInfoDetails(Long studyId);

	boolean saveDrugDispanseInfoDetails(String noOfUnits, String nameOfIp, String batchNoVal, Long studyId, String expDate,
			Long userId, Long treatment);

}
