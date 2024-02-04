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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateSiglePdfAction {

	public void mergePdf(HttpServletRequest request, HttpServletResponse response, List<String> fileStrList) throws IOException, DocumentException {
		response.setContentType("application/pdf");
		OutputStream out = response.getOutputStream();
		Document document = new Document();
		document.setPageSize(PageSize.A4);
		document.addTitle("VS Pdf");
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		document.open();
		PdfContentByte cb = pdfWriter.getDirectContent();
		List<InputStream> list = new ArrayList<InputStream>();
		for (String st : fileStrList) {
			InputStream stream = new FileInputStream(new File(st));
			list.add(stream);
		}

		if (list.size() > 0) {
			PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
			for (InputStream inputStream : list) {
				PdfReader pdfReader = new PdfReader(inputStream);
				for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
					document.newPage();
					 PdfImportedPage page = pdfWriter.getImportedPage(pdfReader, i);
					  Image image = Image.getInstance(page); 
//	                  image.setRotationDegrees(270); 
//	                  image.setAbsolutePosition(0, 0); 
	                  document.add(image); 
	                  ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_CENTER,
  							new Phrase("Page " + i + " of " + (pdfReader.getNumberOfPages())), 550, 30,
  							0);
				      //pdfContentByte.addTemplate(page, 0, 0);
				}
			}

			out.flush();
			document.close();
			out.close();
		}
	
	}

}
