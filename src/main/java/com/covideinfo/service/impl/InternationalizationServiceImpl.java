package com.covideinfo.service.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.covideinfo.dao.InternationalizationDao;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.service.InternationalizationService;

@Service("internationalizationService")
public class InternationalizationServiceImpl implements InternationalizationService {
	
	@Autowired
	InternationalizationDao inalgDao;

	
	@Override
	public String changeLocale(String locationUrl, String langCode, HttpServletRequest request,
			HttpServletResponse response, MessageSource messageSource) {
		String result = "Failed";
		try {
			locationUrl = locationUrl.replaceAll("@@@", "/");
			InternationalizaionLanguages inlagPojo = inalgDao.getInternationalizaionLanguageRecord(langCode);
			if(inlagPojo != null) {
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				Locale locale = localeResolver.resolveLocale(request);
				locale = new Locale(inlagPojo.getLangCode(), inlagPojo.getCountryCode());
				localeResolver.setLocale(request, response, locale);
				request.getSession().setAttribute("translationLanguage", inlagPojo.getLangCode()+"##"+inlagPojo.getCountryCode());
				String validationText = messageSource.getMessage("label.valReqText", null,locale);
				request.getSession().setAttribute("validationText", validationText);
				String searchLabel =messageSource.getMessage("label.search", null,locale);
				request.getSession().setAttribute("searchLabel", searchLabel);
				String showLabel =messageSource.getMessage("label.show", null,locale);
				request.getSession().setAttribute("showLabel", showLabel);
				String entriesLabel =messageSource.getMessage("label.entries", null,locale);
				request.getSession().setAttribute("entriesLabel", entriesLabel);
				String noDtaAvlLabel =messageSource.getMessage("label.nodataAvl", null,locale);
				request.getSession().setAttribute("noDtaAvlLabel", noDtaAvlLabel);
				String showingPgsLabel =messageSource.getMessage("label.showingPgs", null,locale);
				request.getSession().setAttribute("showingPgsLabel", showingPgsLabel);
				String noRcdsLabel =messageSource.getMessage("label.noRecords", null,locale);
				request.getSession().setAttribute("noRcdsLabel", noRcdsLabel);
				String filterLabel =messageSource.getMessage("label.filteredFrom", null,locale);
				request.getSession().setAttribute("filterLabel", filterLabel);
				String totRcdsLabel =messageSource.getMessage("label.totRecords", null,locale);
				request.getSession().setAttribute("totRcdsLabel", totRcdsLabel);
				String ofLabel =messageSource.getMessage("label.of", null,locale);
				request.getSession().setAttribute("ofLabel", ofLabel);
				String prevPgLabel =messageSource.getMessage("label.previousPage", null,locale);
				request.getSession().setAttribute("prevPgLabel", prevPgLabel);
				String nextLabel =messageSource.getMessage("label.next", null,locale);
				request.getSession().setAttribute("nextLabel", nextLabel);
				
				request.getSession().setAttribute("languageId", inlagPojo.getId());
				request.getSession().setAttribute("languageCode", inlagPojo.getLangCode());
				result ="success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
