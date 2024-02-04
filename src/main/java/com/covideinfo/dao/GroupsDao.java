package com.covideinfo.dao;

import java.util.List;
import java.util.Map;

import com.covideinfo.dto.GlobalGroupsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GroupDesignActivity;
import com.covideinfo.model.GroupDesignActivityDetails;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;

public interface GroupsDao {

	GlobalGroupsDto getGlobalGroupsDtoDetails(Long langId);

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	String saveGlobalValesRecords(GlobalGroups gd, List<LanguageSpecificGroupDetails> lsgdList);

	List<GlobalActivity> getGlobalActivityList();

	List<GlobalGroups> getGlobalGroupsList();

	List<GlobalParameter> getGlobalParameterList();

	List<GlobalParameter> getGlobalParameterWithGroupId(long id, long actid);

	List<GlobalGroups> getGlobalGroupsWithActivityId(long id);

	List<GlobalParameter> getGlobalParameterWithActivityId(long id);

	List<GlobalGroups> getGlobalGroupsWithParameterBased(List<Long> ids);

	GlobalGroups getGroupRowsAndColumsCount(long id);

	GlobalActivity getGlobalActivityWithId(long id);

	GlobalGroups getGlobalGroupsWithId(long id);

	GlobalParameter getGlobalParameterWithId(long id);

	boolean saveGlobalGroupDesignActivity(GroupDesignActivity gdaPojo, List<GroupDesignActivityDetails> gdadList, List<GroupDesignActivityDetails> gdUpdateList);

	Map<Long, LanguageSpecificGlobalActivityDetails> getLanguageSpecificGlobalActivityDetailsList(
			InternationalizaionLanguages language);

	Map<Long, LanguageSpecificGroupDetails> getLanguageSpecificGroupDetailsList(InternationalizaionLanguages language);

	Map<Long, LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalParameterDetailsList(
			InternationalizaionLanguages language);

	GlobalGroups getGlobalGroupWithName(String value);

	GroupDesignActivityDetails getGroupDesignActivityDetailsWithParamAndActivity(GlobalParameter gppojo,
			GlobalActivity activitypojo);

	

}
