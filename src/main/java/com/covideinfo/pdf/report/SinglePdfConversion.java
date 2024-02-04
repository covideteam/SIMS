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

import com.covideinfo.model.Projects;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class SinglePdfConversion{
	
	public void convertSinglePdf(HttpServletRequest request, HttpServletResponse response, List<String> fileNames, Projects project, MessageSource messageSource, Locale currentLocale)
			throws IOException, DocumentException {
		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		try {
			response.setContentType("application/pdf");
		    OutputStream out = response.getOutputStream();
		    Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        document.open();
	        String blankStr =messageSource.getMessage("label.pdf.title", null,currentLocale);
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
			
			int pageCount=1;
			
//			Phrase phrase = new Phrase("Draft", new Font( FontFamily.TIMES_ROMAN, 230, Font.NORMAL, new GrayColor(0.85f)));
			String draftStr =messageSource.getMessage("label.pdf.draft", null,currentLocale);
			Phrase phrase = new Phrase(draftStr, new Font( FontFamily.TIMES_ROMAN, 130, Font.NORMAL, new BaseColor(242, 242, 242)));
			if (inslist.size() > 0) {
				for (InputStream in : inslist) {
		            PdfReader reader = new PdfReader(in);
		            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		            	String pageStr  =messageSource.getMessage("label.avanFooter.page", null,currentLocale);
		                String pageOfStr  =messageSource.getMessage("label.avanFooter.pageOf", null,currentLocale);
		            	document.newPage();
		            	PdfNumber rotation = PdfPage.PORTRAIT;
	                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                int pageOrientation = reader.getPageRotation(i);
		                if ((pageOrientation != 0)) {
		                	rotation = PdfPage.LANDSCAPE;
		                	writer.addPageDictEntry(PdfName.ROTATE, rotation);
		                	 ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 550, 725, 90);
		        			 ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER,
			  							new Phrase(pageStr+" " + pageCount + " "+pageOfStr+" " + totalPages, regular), 568, 753, 90);
		                	//Water mark text
		                	 if(project.getStatus() != null && !project.getStatus().getStatusCode().equals("APPROVED")) {
		                		 ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER, //Keep waterMark center aligned
					                        phrase, 350f, 430f,130f); // 45f is the rotation angle 
		                	 }
		                }else {
		                	 ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CONFIDENTIAL", regular), 483, 50, 0);
		        			 ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER,
			  							new Phrase(pageStr+" " + pageCount + " "+pageOfStr+" " + totalPages, regular), 510, 30,0);
		                	//Water mark text
		                	 if(project.getStatus() != null && !project.getStatus().getStatusCode().equals("APPROVED")) {
		                		 ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER, //Keep waterMark center aligned
					                        phrase, 350f, 430f,50f); // 45f is the rotation angle
		                	 }
		                }
		                //import the page from source pdf
		                PdfImportedPage page = writer.getImportedPage(reader, i);
		                cb.addTemplate(page, 0, 0);
		               pageCount++;
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
