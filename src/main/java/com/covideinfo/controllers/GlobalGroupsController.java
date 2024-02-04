package com.covideinfo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.GlobalGroupsDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalGroups;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.GroupDesignActivity;
import com.covideinfo.model.GroupDesignActivityDetails;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.GroupsService;
import com.covideinfo.service.UserService;

@Controller
@RequestMapping("/groups")
public class GlobalGroupsController{
	
	@Autowired  
	MessageSource messageSource;
	
	@Autowired
	GroupsService groupsService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/groupPage", method=RequestMethod.GET)
	public String createGroup(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Locale currentLocale = LocaleContextHolder.getLocale();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		GlobalGroupsDto ggDto = groupsService.getGroupsDetails(messageSource, currentLocale,langId);
		model.addAttribute("ggDto", ggDto);
		model.addAttribute("langId", langId);
		model.addAttribute("inLagList", ggDto.getInlList());
		return "crateGroup";
	}
	
	
	@RequestMapping(value="/saveGlobalGroup", method=RequestMethod.POST)
	public String saveGlobalGroup(ModelMap model, HttpServletRequest request, @RequestParam("valueName")String name,
			@RequestParam("pvalue")List<String> pvalList,@RequestParam("type")String type,@RequestParam("order")int order, @RequestParam("lanId")List<Long> lagList,
			RedirectAttributes redirectAttributes,@RequestParam("noofrows")int noofrows,@RequestParam("noofcolums")int noofcolums) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		Locale currentLocale = LocaleContextHolder.getLocale();
		String result = groupsService.saveGlobalGroup(name, pvalList, lagList, userName, order, type,noofrows,noofcolums);
		if(result.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String ggsuccessMsg =messageSource.getMessage("label.ggSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", ggsuccessMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String ggFailMsg =messageSource.getMessage("label.ggFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", ggFailMsg);
		}
		return "redirect:/groups/groupPage";
	}
	@RequestMapping(value="/groupDesignActivity",method=RequestMethod.GET)
	public String groupDesignActivityForm(ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<GlobalActivity> gaList=groupsService.getGlobalActivityList();
		List<GlobalActivity> gaLista=groupsService.getGlobalActivityList(messageSource, currentLocale);
		//List<GlobalGroups> ggList=groupsService.getGlobalGroupsList();
		//List<GlobalGroups> ggLista=groupsService.getGlobalGroupsList(messageSource, currentLocale);
		//List<GlobalParameter> gpList=groupsService.getGlobalParameterList();
		
		model.addAttribute("gaList", gaLista);
		//model.addAttribute("ggList", ggLista);
		//model.addAttribute("gpList", gpList);
		return "groupDesignPage";
		
	}
	@RequestMapping(value="/getGroupParamList/{id}/{actid}",method=RequestMethod.GET)
	public String getGroupParamList(@PathVariable("id") long id,@PathVariable("actid") long actid,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<GlobalParameter> gpList=groupsService.getGlobalParameterWithGroupId(id,actid,currentLocale);
		//List<Long> ids=new ArrayList<Long>();
		//List<String> paramdata=new ArrayList<String>();
		String ids="";
		String paramdata="";
		for(GlobalParameter addval:gpList) {
			if(ids!="") {
				ids=""+ids+","+addval.getId();
			}else {
				ids=""+addval.getId();
			}
			if(paramdata!="") {
				paramdata=""+paramdata+","+addval.getParameterName();
			}else {
				paramdata=""+addval.getParameterName();
			}
		}
		model.addAttribute("gpList", gpList);
		model.addAttribute("ids", ids);
		model.addAttribute("paramdata", paramdata);
		return "pages/administration/global/groupParamList";
		
	}
	
	
	@RequestMapping(value="getGroupParamIdsList/{id}/{actid}",method=RequestMethod.GET)
	public String getGroupParamIdsList(@PathVariable("id") long id,@PathVariable("actid") long actid,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<GlobalParameter> gpList=groupsService.getGlobalParameterWithGroupId(id,actid,currentLocale);
		String ids="";
		String paramdata="";
		for(GlobalParameter addval:gpList) {
			if(ids!="") {
				ids=""+ids+","+addval.getId();
			}else {
				ids=""+addval.getId();
			}
			if(paramdata!="") {
				paramdata=""+paramdata+","+addval.getParameterName();
			}else {
				paramdata=""+addval.getParameterName();
			}
		}
		model.addAttribute("gpList", gpList);
		model.addAttribute("ids", ids);
		model.addAttribute("paramdata", paramdata);
		return "pages/administration/global/groupParamIdList";
		
	}
	@RequestMapping(value="getGroupListBaseonActivityParameter/{id}",method=RequestMethod.GET)
	public String getGroupListBaseonActivityParameter(@PathVariable("id") long id,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<GlobalParameter> gpList=groupsService.getGlobalParameterWithActivityId(id);
		List<Long> ids=new ArrayList<Long>();
		for(GlobalParameter gppojo:gpList) {
			if(!ids.contains(gppojo.getGroups().getId())) {
			ids.add(gppojo.getGroups().getId());
			}
		}
		List<GlobalGroups> ggList=null;
		if(ids.size()>0)
		ggList=groupsService.getGlobalGroupsWithParameterBased(ids,currentLocale);
		model.addAttribute("ggList", ggList);
		return "pages/administration/global/groupDataList";
		
	}
	@RequestMapping(value="getGroupRowsAndColumsCount/{id}",method=RequestMethod.GET)
	public @ResponseBody String getGroupRowsAndColumsCount(@PathVariable("id") long id,ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		GlobalGroups ggpojo=groupsService.getGroupRowsAndColumsCount(id);
		String data=null;
		if(ggpojo!=null)
		data=ggpojo.getNoofrows()+","+ggpojo.getNoofcolums();
		return data;
		
	}
	@RequestMapping(value="/saveGlobalGroupDesignActivity",method=RequestMethod.POST)
	public String saveGlobalGroupDesignActivity(@RequestParam("activityName") long activityName,@RequestParam("groupName") long groupName ,
			@RequestParam("rowcount") int rowcount,@RequestParam("columscount") int columscount,
			ModelMap model ,HttpServletRequest request,HttpServletResponse response
			,RedirectAttributes redirectAttributes ) {
		String userName = request.getSession().getAttribute("userName").toString();
		Locale currentLocale = LocaleContextHolder.getLocale();
		boolean flag=false;
		//ACT == Static Data( Separated Value )
		GlobalActivity activitypojo=null;
		activitypojo=groupsService.getGlobalActivityWithId(activityName);
		GlobalGroups grouppojo=null;
		 grouppojo=groupsService.getGlobalGroupsWithId(groupName);
		 GroupDesignActivity gdaPojo=new GroupDesignActivity();
		 gdaPojo.setActivityId(activitypojo);
		 gdaPojo.setGroupId(grouppojo);
		 gdaPojo.setCreatedBy(userName);
		 gdaPojo.setCreatedOn(new Date());
		 
		 List<GroupDesignActivityDetails> gdadList=new ArrayList<>();
		 List<GroupDesignActivityDetails> gdUpdateList=new ArrayList<>();
		 for (int j = 0; j < rowcount; j++) {
		      for (int k = 0; k <columscount; k++) {
		    	 // System.out.println(j+""+k+"::"+request.getParameter(j+"ACT"+k));
		    	  String paramval=request.getParameter(k+""+j+"N");
		    	  if(paramval!="") {
		    	  System.out.println("orede::"+paramval);
		    	  long id=Long.parseLong(paramval.trim());
		    	  GlobalParameter gppojo=groupsService.getGlobalParameterWithId(id);
		    	  GroupDesignActivityDetails exitPojo=groupsService.getGroupDesignActivityDetailsWithParamAndActivity(gppojo,activitypojo);
		    	      if(exitPojo!=null) {
			    		  exitPojo.setRowNumber(k+1);
			    		  exitPojo.setColumNumber(j+1);
			    		  gdUpdateList.add(exitPojo);
			    	  }else {
			    		  GroupDesignActivityDetails gdadPojo=new GroupDesignActivityDetails();
				    	  gdadPojo.setGroupDesignId(gdaPojo);
				    	  gdadPojo.setActivityId(activitypojo);
				    	  gdadPojo.setParameterId(gppojo);
				    	  gdadPojo.setCreatedBy(userName);
				    	  gdadPojo.setCreatedOn(new Date());
				    	  gdadPojo.setRowNumber(k+1);
				    	  gdadPojo.setColumNumber(j+1);
				    	  gdadList.add(gdadPojo);
			    	  }
		    	  }
		      }
		 }
		
		 flag=groupsService.saveGlobalGroupDesignActivity(gdaPojo,gdadList,gdUpdateList);
		 
	   if(flag) {
		   String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);


				String successMsg =messageSource.getMessage("label.groupDesignActivity.successMsg", null,currentLocale);
				redirectAttributes.addFlashAttribute("pageMessage", successMsg);
		}else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String failMsg =messageSource.getMessage("label.groupDesignActivity.failMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", failMsg);
		} 
	
	return "redirect:/groups/groupDesignActivity";
		
	}
	
	@RequestMapping(value="/groupsNameCheckExitOrNot/{value}",method=RequestMethod.GET)
	public @ResponseBody String groupsNameCheckExitOrNot(@PathVariable("value") String value ,ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		String chek="Yse";
		GlobalGroups gg=null;
		gg=groupsService.getGlobalGroupWithName(value);
		if(gg!=null) {
			chek="Yes";
		}else {
			chek="No";
		}
		return chek;
	}

}
