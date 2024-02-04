package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.DynamicActivity;
import com.covideinfo.model.DynamicActivityDetails;
import com.covideinfo.model.InternationalizaionLanguages;

public interface ActivityDao {

	boolean saveActivityData(DynamicActivity atpojo, List<DynamicActivityDetails> adlist);

	List<DynamicActivity> getActivityList();

	DynamicActivity getActivityWithId(long id);

	boolean updateStatus(DynamicActivity at);

	List<InternationalizaionLanguages> getInternationalizaionLanguagesRecrdsList();

	boolean saveActivityData(List<DynamicActivity> alist);

	boolean saveActivityDetails(List<DynamicActivityDetails> adlist);

	List<DynamicActivityDetails> getDynamicActivityDetailsList();

	boolean updateDynamicActivity(DynamicActivity dac);

	DynamicActivityDetails getDynamicActivityDetailsWithId(long id);

	
}
