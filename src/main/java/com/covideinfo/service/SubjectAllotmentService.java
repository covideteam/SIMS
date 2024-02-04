package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.covideinfo.model.SubjectActivityData;

public interface SubjectAllotmentService {

	String studySubjectAllotment(SubjectActivityData sad, String userName);

	List<String> generateSubjectNo(Long studyId);

}
