package com.covideinfo.dao;

import com.covideinfo.model.InternationalizaionLanguages;

public interface InternationalizationDao {

	InternationalizaionLanguages getInternationalizaionLanguageRecord(String langCode);

}
