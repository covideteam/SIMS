package com.covideinfo.controllers;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.FilterConfig;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covideinfo.dto.ModuleAccessDto;
import com.covideinfo.model.ApplicationMenus;
import com.covideinfo.model.ApplictionSideMenus;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.RolesWiseModules;
import com.covideinfo.service.ModulesAccessService;
import com.covideinfo.util.SidemeusXmlFile;

@Controller
@RequestMapping("/modulesAccess")
public class ModulesAccessController {
	
	@Autowired
	ModulesAccessService modulesAccessService;
	@Autowired  
	MessageSource messageSource;
	
	private ReentrantLock mutex = new ReentrantLock();
	
	
	@RequestMapping(value="/getModuleAccessForm",method=RequestMethod.GET)
	public String getModuleAccessForm(ModelMap module,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes ) {
		String rolename = (String) request.getSession().getAttribute("userRole");
		List<RoleMaster> roleslist=null;
		if(rolename.equals("ADMIN")) {
			 roleslist=modulesAccessService.getAllRoleMasterWithOutSuperadmin();
		}else {
			 roleslist=modulesAccessService.getAllRoleMaster();
		}
		module.addAttribute("roleslist", roleslist);
		return "modulesAccessForm";
	}
	
	@RequestMapping(value="/addLinkNames",method=RequestMethod.GET)
	public String addLinkName(ModelMap model, HttpServletRequest request,HttpServletResponse response,RedirectAttributes
			redirectAttributes) {
		List<ApplicationMenus> amList=modulesAccessService.getApplicationMenusList();
		Long langId = (Long) request.getSession().getAttribute("languageId");
		Locale currentLocale = LocaleContextHolder.getLocale();
		List<InternationalizaionLanguages> inLagList = modulesAccessService.getInternationalizaionLanguages();
		ModuleAccessDto mDto = modulesAccessService.getLanguageDetails(messageSource, inLagList, currentLocale, langId);
		model.addAttribute("mDto", mDto);
		model.addAttribute("inLagList", inLagList);
		model.addAttribute("amList", amList);
		return "addLinkNames";
		
	}
	@RequestMapping(value="/checkNamedDuplication/{value}", method=RequestMethod.GET)
	public  String checkNamedDuplication(ModelMap model, @PathVariable("value")String name) {
		ApplictionSideMenus user = modulesAccessService.checkNameDuplication(name);
		if(user == null)
			model.addAttribute("result", "");
		else
			model.addAttribute("result", name +" Already Exists.");
		return "pages/users/userDuplicateMsgPage";
	}
	
	@RequestMapping(value="/getAllModuleLinksRole/{roleId}", method=RequestMethod.GET)
	public String getAllModuleLinksRole(ModelMap model, @PathVariable("roleId") Long roleId) {
		
		Map<String, List<ApplictionSideMenus>> map = modulesAccessService.getApplicationSideMenusList(roleId);
	    Map<Long, List<ApplictionSideMenus>> menusMap = modulesAccessService.getSideMenusList();
	    Map<Long, Map<Long, List<RolesWiseModules>>> statusMap = modulesAccessService.getRolesWiseModulesRecordsListBasedOnRole(roleId);
		model.addAttribute("statusMap", statusMap);
		model.addAttribute("menusMap", menusMap);
		model.addAttribute("roleId", roleId);
		model.addAttribute("map", map);
		return "pages/modulesAccess/allModulesList";
	}
	
	@RequestMapping(value="/viewRolewiseModules/{roleId}", method=RequestMethod.GET)
	public String viewRolewiseModules(ModelMap model, @PathVariable("roleId") Long roleId, RedirectAttributes redirectAttributes) {
		model.addAllAttributes(redirectAttributes.getFlashAttributes());
		Map<String, Map<String, List<RolesWiseModules>>> menusMap = modulesAccessService.getRolesWiseModulesRecordsList(roleId);
		model.addAttribute("menusMap", menusMap);
		model.addAttribute("roleId", roleId);
		return "pages/modulesAccess/roleWiseMenuDetials";
	}
	
	@RequestMapping(value="/saveAddLinkNames",method=RequestMethod.POST)
	public String saveAddLinkNames(ModelMap model,RedirectAttributes redirectAttributes,@RequestParam ("linkName") String linkName,
			@RequestParam("lagLinks")List<String> lagLinks, @RequestParam("lanId")List<Long> lagList,@RequestParam ("sideMenu") Long sideMenu,HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String flag  = modulesAccessService.saveAddLinkNames(linkName,lagLinks,lagList,sideMenu,userName);
		Locale currentLocale = LocaleContextHolder.getLocale();
		
		if(flag.equals("success")) {
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String mlSuccessMsg =messageSource.getMessage("label.mlSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", mlSuccessMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);


			String mlFailMsg =messageSource.getMessage("label.mlFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", mlFailMsg);
		}
		return "redirect:/modulesAccess/addLinkNames";
	}
	
	@RequestMapping(value="/saveAllSubLinksIds",method=RequestMethod.POST)
	public String saveAllSubLinksIds(ModelMap model,RedirectAttributes redirectAttributes,
			@RequestParam("finalstr")List<String> finalstr, @RequestParam("roleIdVal") Long roleId, HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("longinUserName");
		String flag  = modulesAccessService.saveAllSubLinksIds(finalstr, roleId,userName);
		Locale currentLocale = LocaleContextHolder.getLocale();
		if(flag.equals("success")) {
			try {
				mutex.lock();
				String patha = request.getSession().getServletContext().getRealPath("/");
				String fileName = patha+"\\xmlFile.xml";
				File file = new File(fileName);  
				file.delete();
				FilterConfig config=null;
				SidemeusXmlFile.createApplicationMenu(config,patha);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				  mutex.unlock();
			}
			
			String success_Msg =messageSource.getMessage("label.success", null,currentLocale);
			redirectAttributes.addFlashAttribute("success", success_Msg);

			String mlSuccessMsg =messageSource.getMessage("label.mlSuccessMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageMessage", mlSuccessMsg);
		}
		else {
			String error_Msg =messageSource.getMessage("label.faild", null,currentLocale);
			redirectAttributes.addFlashAttribute("error", error_Msg);

			String mlFailMsg =messageSource.getMessage("label.mlFailMsg", null,currentLocale);
			redirectAttributes.addFlashAttribute("pageError", mlFailMsg);
		}
		 return "redirect:/modulesAccess/getModuleAccessForm";
	}
}
