package com.covideinfo.barcode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class SubjectBarcodePNRfileGenerationSATOsa412SZPL {
	public PrintWriter pnrFileCreationTop2Lines(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> mInfo) throws FileNotFoundException{
		System.out.println("Pnr file : " + path);
		PrintWriter writer = new PrintWriter(path);
		writer = new PrintWriter(path);
		
		writer.println("<xpml><page quantity='0' pitch='20.1 mm'></xpml>^XA");
		writer.println("^MCY^PMN");
		writer.println("^PW915");
		writer.println("^MD0");
		writer.println("^PR4");
		writer.println("^JZY");
		writer.println("^LH0,0^LRN");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml><xpml><page quantity='1' pitch='20.1 mm'></xpml>^XA");
		
		
		for(int i = 0; i< size; i++){
//			System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
			switch (i) {
			case 0:
				lableOne(writer, barcodeVal.get(i),tInfo.get(i),mInfo.get(i));
				break;
			case 1:
				lableTwo(writer, barcodeVal.get(i),tInfo.get(i),mInfo.get(i));
				break;
			/*case 2:
				lableThree(writer, barcodeVal.get(i));
				break;
			case 3:
				lableFour(writer, barcodeVal.get(i));
				break;
			case 4:
				lableFive(writer, barcodeVal.get(i));
				break;
			case 5:
				lableSix(writer, barcodeVal.get(i));
				break;*/
			default:
				break;
			}
		}
		writer.println("^PQ1,0,1,Y");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml><xpml><end/></xpml>");
		writer.close();
		return writer;
	}
	
	public PrintWriter pnrFileCreationForSubjectsThreeLines(String path, int size, List<String> barcodeVal, List<String> projectInfo, List<String> subjectInfo) throws FileNotFoundException{
//		System.out.println("Pnr file : " + path);
		PrintWriter writer = new PrintWriter(path);
		writer = new PrintWriter(path);
		
		writer.println("<xpml><page quantity='0' pitch='20.1 mm'></xpml>^XA");
		writer.println("^MCY^PMN");
		writer.println("^PW915");
		writer.println("^MD0");
		writer.println("^PR4");
		writer.println("^JZY");
		writer.println("^LH0,0^LRN");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml><xpml><page quantity='1' pitch='20.1 mm'></xpml>^XA");
		
		for(int i = 0; i< size; i++){
//			System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
			switch (i) {
			case 0:
				if(subjectInfo.size() > 0 && !subjectInfo.get(i).equals(""))
					lableOneForSubjects(writer, barcodeVal.get(i),projectInfo.get(i),subjectInfo.get(i));
				break;
			case 1:
				if(subjectInfo.size() > 1 && !subjectInfo.get(i).equals(""))
					lableTwoForSubjects(writer, barcodeVal.get(i),projectInfo.get(i),subjectInfo.get(i));
				break;
			case 2:
				if(subjectInfo.size() > 2 && !subjectInfo.get(i).equals(""))
					lableThreeForSubjects(writer, barcodeVal.get(i),projectInfo.get(i),subjectInfo.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("^PQ1,0,1,Y");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml><xpml><end/></xpml>");
		writer.close();
		return writer;
	}

	private void lableOneForSubjects(PrintWriter writer, String code, String project,String subject) {
		writer.println("^FT80,41");
		writer.println("^CI0");
		writer.println("^A0N,29,20^FD"+project+"^FS");
		writer.println("^FO191,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT238,43");
		writer.println("^A0N,25,24^FD^FS");
		writer.println("^FT110,78");
		writer.println("^A0N,25,30^FD"+subject+"^FS");
	}
	private void lableTwoForSubjects(PrintWriter writer, String code, String project,String subject) {
		writer.println("^FT529,41");
		writer.println("^A0N,29,20^FD"+project+"^FS");
		writer.println("^FO640,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT687,43");
		writer.println("^A0N,25,24^FD^FS\r\n");
		writer.println("^FT559,78");
		writer.println("^A0N,25,30^FD"+subject+"^FS");
	}
	private void lableThreeForSubjects(PrintWriter writer, String code, String project,String subject) {
		String zero="0";
		writer.println("QRCODE 199,148,L,5,A,180,M2,S7,"+'"'+code+'"');
		writer.println("TEXT 294,142,"+'"'+zero+'"'+",180,19,14,"+'"'+subject+'"');
		writer.println("TEXT 294,196,"+'"'+zero+'"'+",180,7,8,"+'"'+project+'"');
	}

	private void lableOne(PrintWriter writer, String code, String tInfo,String mInfo) {
		String zero="0";
		writer.println("^FT80,41");
		writer.println("^CI0");
		writer.println("^A0N,29,20^FD"+tInfo+"^FS");
		writer.println("^FO191,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT238,43");
		writer.println("^A0N,25,24^FD^FS\r\n");
		writer.println("^FT110,78");
		writer.println("^A0N,25,30^FD"+mInfo+"^FS");
		
	}
	private void lableTwo(PrintWriter writer, String code, String tInfo,String mInfo) {
		writer.println("^FT529,41");
		writer.println("^CI0");
		writer.println("^A0N,29,20^FD"+tInfo+"^FS");
		writer.println("^FO640,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT687,43");
		writer.println("^A0N,25,24^FD^FS\r\n");
		writer.println("^FT559,78");
		writer.println("^A0N,25,30^FD"+mInfo+"^FS");
		
	}
	
}
