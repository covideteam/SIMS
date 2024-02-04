package com.covideinfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.DrugDispansingEntryDao;
import com.covideinfo.dto.DrugDispansingEntryDetailsDto;
import com.covideinfo.model.DosingInfoDetails;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.service.DrugDispansingEntryService;

@Service("drugDispansingEntryService")
public class DrugDispansingEntryServiceImpl implements DrugDispansingEntryService {
	
	
	@Autowired
	DrugDispansingEntryDao ddeDao;

	/**
	 * This method will return approved studies list to display Drug Dispensing page
	 */
	@Override
	public DrugDispansingEntryDetailsDto getDrugDispansingDetails() {
		return ddeDao.getDrugDispansingDetails();
	}

	/**
	 * This method gets the required Drug dispensing information from database by using the given studyId
	 * And what's the information we gets from database that can be optimized and placed in one DTO and returns that DTO
	 */
	@Override
	public DrugDispansingEntryDetailsDto getDrugDispansingInfoDetails(Long studyId) {
		DrugDispansingEntryDetailsDto ddedDto = null;
		List<DosingInfoDetails> dinfodList = null;
		Map<Long, DosingInfoDetails> dinfMap = new HashMap<>();
		List<TreatmentInfo> tinfoList = null;
		Map<Long, TreatmentInfo> treatmentMap = new HashMap<>();
		try {
			ddedDto = ddeDao.getDrugDispansingInfoDetails(studyId);
			if(ddedDto != null) {
				dinfodList = ddedDto.getDifodList();
				tinfoList = ddedDto.getTinfoList();
				if(dinfodList != null) {
					for(TreatmentInfo tinf : tinfoList) {
						for(DosingInfoDetails dinfod : dinfodList) {
							if(tinf.getId() == dinfod.getTinfo().getId())
								dinfMap.put(tinf.getId(), dinfod);
						}
						treatmentMap.put(tinf.getId(), tinf);
					}
				}
				ddedDto.setDinfMap(dinfMap);
				ddedDto.setTreatmentMap(treatmentMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ddedDto;
	}

	/**
	 * This method is used to set the given data form UI to Drug Dispensing POJO for saving Or Updating 
	 */
	@Override
	public boolean saveDrugDispanseInfoDetails(String noOfUnits, String nameOfIp, String batchNo, Long studyId,
			String expDate, Long userId, Long treatment) {
		DosingInfoDetails didPojo = null;
		boolean flag = false;
		DrugDispansingEntryDetailsDto ddedDto = null;
		boolean updateFlag = false;
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
		try {
			ddedDto = ddeDao.getDosingInfoDetailsRecord(studyId, userId, treatment);
			if(ddedDto != null) {
				String conexpDate = "";
				if(ddedDto.getDinfodetails() != null) {
					didPojo = ddedDto.getDinfodetails();
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
					if(didPojo.getExpDate() != null) {
						conexpDate = sdf.format(didPojo.getExpDate());
					}
					if(didPojo.getTinfo().getId() != ddedDto.getTinf().getId()) {
						didPojo.setTinfo(ddedDto.getTinf());
						updateFlag = true;
					}
					
					if(!didPojo.getBatchNo().equals(batchNo)) {
						didPojo.setBatchNo(batchNo);
						updateFlag = true;
					}
					if(!didPojo.getNameOfIp().equals(nameOfIp)) {
						didPojo.setNameOfIp(nameOfIp);
						updateFlag = true;
					}
					if(!didPojo.getNoOfUnits().equals(noOfUnits)) {
						didPojo.setNoOfUnits(noOfUnits);
						updateFlag = true;
					}
					if(!conexpDate.equals(expDate)) {
						Date exDate = sdf2.parse(expDate);
						didPojo.setExpDate(exDate);
						updateFlag = true;
					}	
					if(updateFlag) {
						didPojo.setUpdatedBy(ddedDto.getUser());
						didPojo.setUpdatedOn(new Date());
						didPojo.setUpdateReason("Drug Details Updated");
						flag = ddeDao.updateDosingInfoDetails(didPojo);
					}
				}else {
					 Date exDate = sdf2.parse(expDate);
					 didPojo = new DosingInfoDetails();
					 didPojo.setBatchNo(batchNo);
					 didPojo.setExpDate(exDate);
					 didPojo.setNoOfUnits(noOfUnits);
					 didPojo.setNameOfIp(nameOfIp);
					 didPojo.setStudy(ddedDto.getStudy());
					 didPojo.setCreatedBy(ddedDto.getUser());
					 didPojo.setCreatedOn(new Date());
					 didPojo.setTinfo(ddedDto.getTinf());
					 flag = ddeDao.saveDosingInfoDetails(didPojo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
