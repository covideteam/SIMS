package com.covideinfo.pdf.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

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
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class MultipuleToSinglePdfConvertion extends PdfPageEventHelper{
	
	public String convertSinglePdf(HttpServletRequest request, HttpServletResponse response, List<String> fileNames, MessageSource messageSource, Locale currentLocale, String savingFolderName, String savingFileName, String footerRightSideText, String documentTitle)
			throws IOException, DocumentException {
		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		String filepath = "";
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String path = realPath+"//"+savingFolderName;
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/"+savingFileName+".pdf";
//			response.setContentType("application/pdf");
//		    OutputStream out = response.getOutputStream();
		    Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	        document.open();
//	        String blankStr =messageSource.getMessage("label.dataPdf.title", null,currentLocale);
	        document.addTitle(documentTitle);
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
		                	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(footerRightSideText, regular), 550, 725, 90);
		        			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase(pageStr+" " + pageCount + " "+pageOfStr+" " + totalPages, regular), 568, 753, 90);
		                }else {
		                	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(footerRightSideText, regular), 465, 50, 0);
		        			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase(pageStr+" " + pageCount + " "+pageOfStr+" " + totalPages, regular), 510, 30,0);
		                }
		                //import the page from source pdf
		                PdfImportedPage page = writer.getImportedPage(reader, i);
		                cb.addTemplate(page, 0, 0);
		               pageCount++;
		            }
		        }
		        
//		        out.flush();
		        document.close();
//		        out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

}
