package com.covideinfo.eform.dao;

import java.util.List;

import com.covideinfo.eform.model.EForm;
import com.covideinfo.eform.model.EFormEleCaliculation;
import com.covideinfo.eform.model.EFormGroup;
import com.covideinfo.eform.model.EFormGroupElement;
import com.covideinfo.eform.model.EFormMapplingTable;
import com.covideinfo.eform.model.EFormMapplingTableColumn;
import com.covideinfo.eform.model.EFormMapplingTableColumnMap;
import com.covideinfo.eform.model.EFormRule;
import com.covideinfo.eform.model.EFormSection;
import com.covideinfo.eform.model.EFormSectionElement;
import com.covideinfo.eform.model.EFormVisibility;
import com.covideinfo.model.UserMaster;

public interface EformDao {

	List<EForm> findAllEForm();

	EForm checkEForm(String name);

	void saveEForm(EForm crf);

	List<EFormVisibility> findAllEFormVisibility();

	EForm eformForView(Long eformId);

	List<EFormSectionElement> sectionElemets(Long id);

	List<EFormGroupElement> groupElemets(Long id);

	List<EFormSection> sections(Long id);

	List<EFormGroup> groups(Long id);

	EForm getEForm(Long crfid);

	EFormSectionElement sectionElement(Long secId);

	EFormGroupElement groupElement(Long groupId);

	EFormSection section(Long secIdAction);

	EFormGroup group(Long groupIdAction);

	void saveEFormVisibility(EFormVisibility visibilty);

	List<EForm> findAllCrfsContainsSelectTable();

	List<EFormMapplingTable> findAllMappingTables();

	List<EFormMapplingTableColumnMap> allCrfMapplingTableColumnMap();

	List<EFormSectionElement> sectionElemetsSelectTale(Long id);

	List<EFormMapplingTableColumn> eformMappingTableColumns(Long id);

	EFormMapplingTableColumnMap saveCrfMapplingTableColumnMap(EFormMapplingTableColumnMap map);

	List<EFormEleCaliculation> eformEleCaliculationList();

	EForm crfByName(String value);

	void saveEFormEleCaliculationList(List<EFormEleCaliculation> list);

	UserMaster getUserMasterWithName(String username);

	void updateEForm(EForm crf);

	List<EFormRule> findAllEFormRules();

	List<EForm> findAllEFormWithSubElemens();

	String saveEFormRule(EFormRule rule);

	EFormRule eformRule(Long id);

	void updateEFormRule(EFormRule rule);

	String eformVisibilityChangeStatus(Long id);

	EFormVisibility eformVisibility(Long id);

	void updateUpdateVisibility(EFormVisibility rule);

}
