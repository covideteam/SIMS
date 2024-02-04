package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.GlobalValuesDto;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;

public interface GlobalValuesService {

	List<InternationalizaionLanguages> getInternationalizaionLanguages();

	GlobalValuesDto getLanguageDetails(MessageSource messageSource, List<InternationalizaionLanguages> inLagList, Locale currentLocale, Long langId);

	String saveGlobalValues(String name, List<String> pvalList, List<Long> lagList, Long userId, int orderNo);

	GlobalValues getGlobalValuesWithName(String value);

}
