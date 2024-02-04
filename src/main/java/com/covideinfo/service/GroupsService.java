package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.GlobalGroupsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GroupDesignActivity;
import com.covideinfo.model.GroupDesignActivityDetails;

public interface GroupsService {

	GlobalGroupsDto getGroupsDetails(MessageSource messageSource, Locale currentLocale, Long langId);

	String saveGlobalGroup(String name, List<String> pvalList, List<Long> lagList, String userName, int order, String type, int noofrows, int noofcolums);

	List<GlobalActivity> getGlobalActivityList();

	List<GlobalGroups> getGlobalGroupsList();

	List<GlobalParameter> getGlobalParameterList();

	List<GlobalParameter> getGlobalParameterWithGroupId(long id, long actid, Locale currentLocale);

	List<GlobalGroups> getGlobalGroupsWithActivityId(long id);

	List<GlobalParameter> getGlobalParameterWithActivityId(long id);

	List<GlobalGroups> getGlobalGroupsWithParameterBased(List<Long> ids, Locale currentLocale);

	GlobalGroups getGroupRowsAndColumsCount(long id);

	GlobalActivity getGlobalActivityWithId(long activityName);

	GlobalGroups getGlobalGroupsWithId(long groupName);

	GlobalParameter getGlobalParameterWithId(long id);

	boolean saveGlobalGroupDesignActivity(GroupDesignActivity gdaPojo, List<GroupDesignActivityDetails> gdadList, List<GroupDesignActivityDetails> gdUpdateList);

	List<GlobalActivity> getGlobalActivityList(MessageSource messageSource, Locale currentLocale);

	List<GlobalGroups> getGlobalGroupsList(MessageSource messageSource, Locale currentLocale);

	GlobalGroups getGlobalGroupWithName(String value);

	GroupDesignActivityDetails getGroupDesignActivityDetailsWithParamAndActivity(GlobalParameter gppojo,
			GlobalActivity activitypojo);

}
