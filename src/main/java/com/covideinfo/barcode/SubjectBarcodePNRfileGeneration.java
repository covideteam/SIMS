package com.covideinfo.barcode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class SubjectBarcodePNRfileGeneration {
	public PrintWriter pnrFileCreationTop2Lines(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> mInfo) throws FileNotFoundException{
		System.out.println("Pnr file : " + path);
		PrintWriter writer = new PrintWriter(path);
		writer = new PrintWriter(path);
		
		writer.println("SIZE 77.5 mm, 25.1 mm");
		writer.println("SPEED 3");
		writer.println("DENSITY 15");
		writer.println("DIRECTION 0,0");
		writer.println("OFFSET 0 mm");
		writer.println("SET PEEL OFF");
		writer.println("SET CUTTER OFF");
		writer.println("SET PARTIAL_CUTTER OFF");
		writer.println("SET TEAR ON");
		writer.println("CLS");
		
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
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
	}
	
	public PrintWriter pnrFileCreationForSubjectsThreeLines(String path, int size, List<String> barcodeVal, List<String> projectInfo, List<String> subjectInfo) throws FileNotFoundException{
//		System.out.println("Pnr file : " + path);
		PrintWriter writer = new PrintWriter(path);
		writer = new PrintWriter(path);
		
		writer.println("SIZE 93.5 mm, 19.1 mm");
		writer.println("SPEED 3");
		writer.println("DENSITY 15");
		writer.println("DIRECTION 0,0");
		writer.println("REFERENCE 0,0");
		writer.println("OFFSET 0 mm");
		writer.println("SET PEEL OFF");
		writer.println("SET CUTTER OFF");
		writer.println("SET PARTIAL_CUTTER OFF");
		writer.println("SET TEAR ON");
		writer.println("CLS");
		
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
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
	}

	private void lableOneForSubjects(PrintWriter writer, String code, String project,String subject) {
			String zero="0";
			writer.println("QRCODE 939,148,L,5,A,180,M2,S7,"+'"'+code+'"');
			writer.println("TEXT 1034,142,"+'"'+zero+'"'+",180,19,14,"+'"'+subject+'"');
			writer.println("TEXT 1034,196,"+'"'+zero+'"'+",180,7,8,"+'"'+project+'"');
	}
	private void lableTwoForSubjects(PrintWriter writer, String code, String project,String subject) {
		String zero="0";
		writer.println("QRCODE 569,148,L,5,A,180,M2,S7,"+'"'+code+'"');
		writer.println("TEXT 664,142,"+'"'+zero+'"'+",180,19,14,"+'"'+subject+'"');
		writer.println("TEXT 664,196,"+'"'+zero+'"'+",180,19,14,"+'"'+project+'"');
	}
	private void lableThreeForSubjects(PrintWriter writer, String code, String project,String subject) {
		String zero="0";
		writer.println("QRCODE 199,148,L,5,A,180,M2,S7,"+'"'+code+'"');
		writer.println("TEXT 294,142,"+'"'+zero+'"'+",180,19,14,"+'"'+subject+'"');
		writer.println("TEXT 294,196,"+'"'+zero+'"'+",180,7,8,"+'"'+project+'"');
	}

	private void lableOne(PrintWriter writer, String code, String tInfo,String mInfo) {
		String zero="0";
		writer.println("QRCODE 733,100,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+""+'"');
		writer.println("TEXT 734,180,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo+'"');
		writer.println("TEXT 734,150,"+'"'+zero+'"'+",180,7,6,"+'"'+""+'"');
		
	}
	private void lableTwo(PrintWriter writer, String code, String tInfo,String mInfo) {
		String zero="0";
		writer.println("QRCODE 287,100,L,3,A,180,M2,S7,"+'"'+code+'"');
		writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+""+'"');
		writer.println("TEXT 291,180,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo+'"');
		writer.println("TEXT 291,150,"+'"'+zero+'"'+",180,7,6,"+'"'+""+'"');
		
	}
	
	/*private void lableThree(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		writer.println("^FO80,81");
		writer.println("^BQN,2,3^FDLA,"+code+"^FS");
		writer.println("^FT26,73");
		writer.println("^CI0");
		writer.println("^A0N,42,56^FD"+tInfo+"^FS");
		writer.println("^FT32,218");
		writer.println("^A0N,42,56^FD"+bInfo+"^FS");
	}
	
	private void lableFour(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		writer.println("^FO80,81");
		writer.println("^BQN,2,3^FDLA,"+code+"^FS");
		writer.println("^FT26,73");
		writer.println("^CI0");
		writer.println("^A0N,42,56^FD"+tInfo+"^FS");
		writer.println("^FT32,218");
		writer.println("^A0N,42,56^FD"+bInfo+"^FS");
	}
	
	private void lableFive(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		writer.println("^FO80,81");
		writer.println("^BQN,2,3^FDLA,"+code+"^FS");
		writer.println("^FT26,73");
		writer.println("^CI0");
		writer.println("^A0N,42,56^FD"+tInfo+"^FS");
		writer.println("^FT32,218");
		writer.println("^A0N,42,56^FD"+bInfo+"^FS");
	}
	
	private void lableSix(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		writer.println("^FO80,81");
		writer.println("^BQN,2,3^FDLA,"+code+"^FS");
		writer.println("^FT26,73");
		writer.println("^CI0");
		writer.println("^A0N,42,56^FD"+tInfo+"^FS");
		writer.println("^FT32,218");
		writer.println("^A0N,42,56^FD"+bInfo+"^FS");
	}*/
}
