package com.covideinfo.pdf.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class SinglePdfForExportTables{
	
	public void convertSinglePdf(HttpServletRequest request, HttpServletResponse response, List<String> fileNames)
			throws IOException, DocumentException {
		Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
		try {
			response.setContentType("application/pdf");
		    OutputStream out = response.getOutputStream();
		    Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        document.open();
	        document.addTitle("Users");
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
		            	document.newPage();
		                //import the page from source pdf
		                PdfImportedPage page = writer.getImportedPage(reader, i);
		                //add the page to the destination pdf
		                cb.addTemplate(page, 0, 0);
		                /*ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_CENTER,
	  							new Phrase("Page " + i + " of " + (reader.getNumberOfPages()), regular), 550, 50,
	  							0);*/
		                ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER,
	  							new Phrase("Page " + pageCount + " of " + totalPages, regular), 550, 50,0);
		                //Water mark text
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
