package com.covideinfo.configuration;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;

public class UrlLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        String lang = null;
        	if(null == request.getSession().getAttribute("translationLanguage")) {
        		lang = "0";
        	}else {
        		lang = request.getSession().getAttribute("translationLanguage").toString();
        	}
        String[] temp = null;
        if(lang != null && lang != "0") {
        	temp = lang.split("\\##");
        }
        // English
        if(temp != null && temp.length == 2) {
        	locale = new Locale(temp[0].trim(), temp[1].trim());
        }else {
        	locale = new Locale("en", "US");
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // Nothing
    }

}

