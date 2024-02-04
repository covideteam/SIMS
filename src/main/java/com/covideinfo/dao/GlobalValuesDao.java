package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;

public interface GlobalValuesDao {

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	String saveGlobalValesRecords(GlobalValues gv, List<LanguageSpecificValueDetails> lsvdList);

	List<LanguageSpecificValueDetails> getLanguageSpecificValueDetailsList(Long langId);

	GlobalValues getGlobalValuesWithName(String value);

	InternationalizaionLanguages getInternationalizationLanguageRecord(Long langId);

}
