package com.covideinfo.pdf.report;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TextPdf {
	
	public void generateTextPdf(String message, HttpServletRequest request, HttpServletResponse response, String documentTitle) {
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font errorText = new Font(calibri.getFamily(), 10, Font.BOLD, BaseColor.RED);
		PdfPTable table = null;
		PdfPCell cell = null;
		 try {
			Document document = new Document();
			response.setContentType("application/pdf");
			OutputStream out = response.getOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.addTitle(documentTitle);
			document.open();
			
			table = new PdfPTable(1);
			cell = new PdfPCell(new Phrase(message, errorText));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			document.add(table);
			
			document.close();
			out.close();
		 } catch (Exception e) {
			e.printStackTrace();
		}
	}

}
