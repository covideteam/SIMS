package com.covideinfo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.covideinfo.dao.PDFReportDao;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyActivityData;
import com.itextpdf.text.DocumentException;

public interface PDFReportService {

	StudyActivityData getStudyActivityDataWithId(long id);

	String generateCrfInPdfFormat(HttpServletResponse response, HttpServletRequest request, StudyActivityData sad, GlobalActivity ga, PDFReportService pdfReportService, PDFReportDao pdfReportDao) throws FileNotFoundException, DocumentException, MalformedURLException, IOException;

}
