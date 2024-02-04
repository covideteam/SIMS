package com.covideinfo.pdf.report;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class MergeAndDownloadSinglPdf extends PdfPageEventHelper{
	
	public void convertSinglePdf(HttpServletRequest request, HttpServletResponse response, List<String> fileNames, MessageSource messageSource, Locale currentLocale)
			throws IOException, DocumentException {
		try {
			response.setContentType("application/pdf");
		    OutputStream out = response.getOutputStream();
		    Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        document.open();
	        String blankStr =messageSource.getMessage("label.dataPdf.title", null,currentLocale);
	        document.addTitle(blankStr);
	        int totalPages = 0;
	        PdfContentByte cb = writer.getDirectContent();
			List<InputStream> inslist = new ArrayList<InputStream>();
			for (String st : fileNames) {
				InputStream stream = new FileInputStream(new File(st));
				inslist.add(stream);
				//For totalNo of pages
				PdfReader reader = new PdfReader(st);
				totalPages = totalPages + reader.getNumberOfPages();

			}
			if (inslist.size() > 0) {
				for (InputStream in : inslist) {
		            PdfReader reader = new PdfReader(in);
		            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		            	document.newPage();
		                //import the page from source pdf
		                PdfImportedPage page = writer.getImportedPage(reader, i);
		                PdfNumber rotation = PdfPage.PORTRAIT;
	                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                int pageOrientation = reader.getPageRotation(i);
		                if ((pageOrientation != 0)) {
		                	rotation = PdfPage.LANDSCAPE;
		                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                }
		                //add the page to the destination pdf
		                cb.addTemplate(page, 0, 0);
	                }
		        }
		        
		        out.flush();
		        document.close();
		        out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
