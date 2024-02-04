package com.covideinfo.dao;

import com.covideinfo.dto.DrugDispansingEntryDetailsDto;
import com.covideinfo.model.DosingInfoDetails;

public interface DrugDispansingEntryDao {

	DrugDispansingEntryDetailsDto getDrugDispansingDetails();

	DrugDispansingEntryDetailsDto getDrugDispansingInfoDetails(Long studyId);

	DrugDispansingEntryDetailsDto getDosingInfoDetailsRecord(Long studyId, Long userId, Long treatment);

	boolean updateDosingInfoDetails(DosingInfoDetails didPojo);

	boolean saveDosingInfoDetails(DosingInfoDetails didPojo);

}
