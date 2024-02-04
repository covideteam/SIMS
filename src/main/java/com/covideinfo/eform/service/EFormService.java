package com.covideinfo.eform.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.covideinfo.eform.model.EForm;
import com.covideinfo.eform.model.EFormEleCaliculation;
import com.covideinfo.eform.model.EFormMapplingTableColumnMap;
import com.covideinfo.eform.model.EFormRule;
import com.covideinfo.eform.model.EFormVisibility;
import com.covideinfo.model.UserMaster;

public interface EFormService {

	List<EForm> findAllEForm();

	EForm readCrfExcelFile(FileInputStream fis, String filename)throws IOException;

	void saveEForm(EForm crf);

	List<EFormVisibility> findAllEFormVisibility();

	EForm eformForView(Long eformId);

	String sectionEleSelect(Long id);

	String groupEleSelectForVisibility(Long id);

	String sections(Long id);

	String groups(Long id);

	String saveEFormVisibilitySave(String username, String name, String desc, String compareWith, Long crfid,
			Long secId, Long groupId, String fieldValue, String condition, Long crfidAction, Long secIdAction,
			Long groupIdAction);

	List<EForm> findAllCrfsContainsSelectTable();

	Object findAllMappingTables();

	Object allCrfMapplingTableColumnMap();

	String eformSectionElementsSelectTable(Long id);

	String eformMappingTableColumns(Long id);

	EFormMapplingTableColumnMap mapTableToCrfSave(Long crfid, Long secEleId, Long tableId, Long columnId,
			String userName);

	List<EFormEleCaliculation> eformEleCaliculationList();

	void uploadEFormCaliculationFile(String path, String username) throws SAXException, IOException, ParserConfigurationException;

	EForm changeEFormStatus(Long crfId, String username);

	UserMaster getUserMasterWithName(String username);

	List<EFormRule> findAllEFormRules();

	String groupEleSelect(Long id);

	String eformRuleElements(int rowId);

	String otherEFormSectionElements(Long id, int count);

	String otherEFormGroupElements(Long id, int count);

	String saveEFormRule(String username, HttpServletRequest request);

	String eformRuleChangeStatus(Long id);

	String eformVisibilityChangeStatus(Long id);
	
	

}
