package com.covideinfo.barcode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class VacutainerBarcodePNRfileGeneration {
	public PrintWriter sacheBarcodesPrint(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> mInfo, List<String> mInfo2, 
			List<String> bInfo, List<String> bInfo2) throws FileNotFoundException{
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
				//System.out.println(mInfo.get(i)+"      "+ mInfo2.get(i));
				lableOneForSche(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), mInfo2.get(i), bInfo.get(i), bInfo2.get(i));
				break;
			case 1:
				try {
				//System.out.println(mInfo.get(i)+"      "+ mInfo2.get(i));
				lableTwoForSache(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), mInfo2.get(i), bInfo.get(i), bInfo2.get(i));
				}catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				lableThree(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 3:
				lableFour(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 4:
				lableFive(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 5:
				lableSix(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
	}
	public PrintWriter pnrFileCreationVacAndVial(String path, int size, List<String> barcodeList, List<String> subjectAndPeriodList,
			List<String> sequenceList, List<String> projectNoList, List<String> tpStrList) throws FileNotFoundException{
//		System.out.println("Pnr file : " + path);
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
			switch (i) {
			case 0:
				lableOne(writer, barcodeList.get(i), subjectAndPeriodList.get(i), sequenceList.get(i), projectNoList.get(i), tpStrList.get(i));
				break;
			case 1:
				lableTwo(writer, barcodeList.get(i), subjectAndPeriodList.get(i), sequenceList.get(i), projectNoList.get(i), tpStrList.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
	}
	
	public PrintWriter pnrFileCreationVacAndVialForThreeLabels(String path, int size, List<String> barcodeList, List<String> subjectAndPeriodList,
			List<String> sequenceList, List<String> projectNoList, List<String> tpStrList) throws FileNotFoundException{
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
			switch (i) {
			case 0:
				if(tpStrList.size() > 0 && !tpStrList.get(i).equals("")) 
					lableOneForThreeLabels(writer, barcodeList.get(i), subjectAndPeriodList.get(i), sequenceList.get(i), projectNoList.get(i), tpStrList.get(i));
				break;
			case 1:
				if(tpStrList.size() > 1 && !tpStrList.get(i).equals("")) 
					lableTwoForThreeLabels(writer, barcodeList.get(i), subjectAndPeriodList.get(i), sequenceList.get(i), projectNoList.get(i), tpStrList.get(i));
				break;
			case 2:
				if(tpStrList.size() > 2 && !tpStrList.get(i).equals("")) 
					lableThreeForThreeLabels(writer, barcodeList.get(i), subjectAndPeriodList.get(i), sequenceList.get(i), projectNoList.get(i), tpStrList.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
	}
	private void lableThreeForThreeLabels(PrintWriter writer, String barcode, String subjectAndPeriod, String sequence, String projectNo, String tpStr) {
		String zero="0";
        writer.println("QRCODE 130,116,L,5,A,180,M2,S7,"+'"'+barcode+'"');
        writer.println("TEXT 326,199,"+'"'+zero+'"'+",180,7,8,"+'"'+projectNo+'"');
		writer.println("TEXT 331,152,"+'"'+zero+'"'+",180,7,8,"+'"'+subjectAndPeriod+'"');
		writer.println("TEXT 337,97,"+'"'+zero+'"'+",180,7,8,"+'"'+tpStr+'"');
		writer.println("TEXT 244,46,"+'"'+zero+'"'+",180,7,8,"+'"'+sequence+'"');
		
	}
	private void lableTwoForThreeLabels(PrintWriter writer, String barcode, String subjectAndPeriod, String sequence, String projectNo, String tpStr) {
		String zero="0";
		writer.println("QRCODE 500,116,L,5,A,180,M2,S7,"+'"'+barcode+'"');
		writer.println("TEXT 696,199,"+'"'+zero+'"'+",180,7,8,"+'"'+projectNo+'"');
		writer.println("TEXT 701,152,"+'"'+zero+'"'+",180,7,8,"+'"'+subjectAndPeriod+'"');
		writer.println("TEXT 707,97,"+'"'+zero+'"'+",180,7,8,"+'"'+tpStr+'"');
		writer.println("TEXT 614,46,"+'"'+zero+'"'+",180,7,8,"+'"'+sequence+'"');
	}
	private void lableOneForThreeLabels(PrintWriter writer, String barcode, String subjectAndPeriod, String sequence, String projectNo, String tpStr) {
		String zero="0";
        writer.println("QRCODE 870,116,L,5,A,180,M2,S7,"+'"'+barcode+'"');
		
	    writer.println("TEXT 1066,199,"+'"'+zero+'"'+",180,7,8,"+'"'+projectNo+'"');
		writer.println("TEXT 1071,152,"+'"'+zero+'"'+",180,7,8,"+'"'+subjectAndPeriod+'"');
		writer.println("TEXT 1077,97,"+'"'+zero+'"'+",180,7,8,"+'"'+tpStr+'"');
		writer.println("TEXT 984,46,"+'"'+zero+'"'+",180,7,8,"+'"'+sequence+'"');
		
	}
	
	/*private void lableOne(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
        writer.println("QRCODE 733,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
        writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
		writer.println("TEXT 734,180,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
		
		
		writer.println("^FT87,39");
		writer.println("^CI0");
		writer.println("^A0N,25,33^FD"+ tInfo +"^FS");
		writer.println("^FO167,92");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT104,211");
		writer.println("^A0N,25,31^FD"+bInfo+"^FS");
		writer.println("^FT87,74");
		writer.println("^A0N,25,33^FD"+mInfo+"^FS");
		writer.println("^FT150,74");
		writer.println("^A0N,25,33^FD"+mInfo2+"^FS");		
		writer.println("^FT278,211");
		writer.println("^A0N,25,33^FD"+bInfo2+"^FS");
	}*/
	private void lableOneForSche(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
        writer.println("QRCODE 733,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
        writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
        writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
        writer.println("TEXT 734,180,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
        writer.println("TEXT 740,52,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
		
		writer.println("^FT87,39");
		writer.println("^CI0");
		writer.println("^A0N,25,33^FD"+ tInfo +"^FS");
		writer.println("^FO167,92");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT104,211");
		writer.println("^A0N,25,31^FD"+bInfo+"^FS");
		writer.println("^FT87,74");
		writer.println("^A0N,25,33^FD"+mInfo+"^FS");
		writer.println("^FT150,74");
		writer.println("^A0N,25,33^FD"+mInfo2+"^FS");		
		writer.println("^FT278,211");
		writer.println("^A0N,25,33^FD"+bInfo2+"^FS");
	}
	private void lableTwoForSache(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
		writer.println("QRCODE 287,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
        writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
		writer.println("TEXT 291,180,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 297,52,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}
	private void lableOne(PrintWriter writer, String barcode, String subjectAndPeriod, String sequence, String projectNo, String tpStr) {
		String zero="0";
        writer.println("QRCODE 733,140,L,3,A,180,M2,S7,"+'"'+barcode+'"');
		
	    writer.println("TEXT 740,275,"+'"'+zero+'"'+",180,7,6,"+'"'+projectNo+'"');
		writer.println("TEXT 790,250,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectAndPeriod+'"');
		writer.println("TEXT 790,216,"+'"'+zero+'"'+",180,7,6,"+'"'+tpStr+'"');
		writer.println("TEXT 740,52,"+'"'+zero+'"'+",180,7,6,"+'"'+sequence+'"');
	}
/*	private void lableTwo(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
		writer.println("QRCODE 287,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
        writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
		writer.println("TEXT 291,180,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}*/
	
	private void lableTwo(PrintWriter writer, String barcode, String subjectAndPeriod, String sequence, String projectNo, String tpStr) {
		String zero="0";
		writer.println("QRCODE 287,140,L,3,A,180,M2,S7,"+'"'+barcode+'"');
	
		writer.println("TEXT 294,275,"+'"'+zero+'"'+",180,7,6,"+'"'+projectNo+'"');
		writer.println("TEXT 344,250,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectAndPeriod+'"');
		writer.println("TEXT 344,216,"+'"'+zero+'"'+",180,7,6,"+'"'+tpStr+'"');
		writer.println("TEXT 297,52,"+'"'+zero+'"'+",180,7,6,"+'"'+sequence+'"');
		
	}
	private void lableThree(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		String zero="0";
        writer.println("QRCODE 733,180,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}
	
	private void lableFour(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		String zero="0";
        writer.println("QRCODE 733,180,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}
	
	private void lableFive(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		String zero="0";
        writer.println("QRCODE 733,180,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}
	
	private void lableSix(PrintWriter writer, String code, String tInfo,
			String bInfo) {
		// TODO Auto-generated method stub
		String zero="0";
        writer.println("QRCODE 733,180,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}

	public PrintWriter pnrFileCreationVacAndAVAN(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> mInfo, List<String> mInfo2, 
			List<String> bInfo, List<String> bInfo2)throws FileNotFoundException {
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
				//System.out.println(mInfo.get(i)+"      "+ mInfo2.get(i));
				lableOneAvan(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), mInfo2.get(i), bInfo.get(i), bInfo2.get(i));
				break;
			case 1:
				try {
				//System.out.println(mInfo.get(i)+"      "+ mInfo2.get(i));
				lableTwoAvan(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), mInfo2.get(i), bInfo.get(i), bInfo2.get(i));
				}catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				lableThree(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 3:
				lableFour(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 4:
				lableFive(writer, barcodeVal.get(i), tInfo.get(i), bInfo.get(i));
				break;
			case 5:
				lableSix(writer, barcodeVal.get(i), tInfo.get(1), bInfo.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("PRINT 1,1");
		writer.close();
		return writer;
		
	}
	private void lableOneAvan(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
        writer.println("QRCODE 733,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		/*writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');*/
        writer.println("TEXT 734,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
		writer.println("TEXT 734,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
		writer.println("TEXT 734,180,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
		
		
		/*writer.println("^FT87,39");
		writer.println("^CI0");
		writer.println("^A0N,25,33^FD"+ tInfo +"^FS");
		writer.println("^FO167,92");
		writer.println("^BY6^BXN,6,200,16,16,1^FD"+code+"^FS");
		writer.println("^FT104,211");
		writer.println("^A0N,25,31^FD"+bInfo+"^FS");
		writer.println("^FT87,74");
		writer.println("^A0N,25,33^FD"+mInfo+"^FS");
		writer.println("^FT150,74");
		writer.println("^A0N,25,33^FD"+mInfo2+"^FS");		
		writer.println("^FT278,211");
		writer.println("^A0N,25,33^FD"+bInfo2+"^FS");*/
	}
	private void lableTwoAvan(PrintWriter writer, String code, String tInfo, String mInfo, String mInfo2,
			String bInfo, String bInfo2) {
		String zero="0";
		writer.println("QRCODE 287,140,L,3,A,180,M2,S7,"+'"'+code+'"');
		
		/*writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+tInfo+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');*/
        writer.println("TEXT 291,260,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo2+'"');
		writer.println("TEXT 291,216,"+'"'+zero+'"'+",180,7,6,"+'"'+mInfo2+'"');
		writer.println("TEXT 291,180,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
	}

	
public PrintWriter generateAdvitySachetBarcodePrint(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> subjectList, List<String> periodList, 
				List<String> bInfo, List<String> noOfUnitsList, String studyNo) throws FileNotFoundException{
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
//				System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
				switch (i) {
				case 0:
					//System.out.println(mInfo.get(i)+"      "+ mInfo2.get(i));
//					lableOneForSche(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), mInfo2.get(i), bInfo.get(i), bInfo2.get(i));
					labelOneSachetgeneration(writer, barcodeVal.get(i), tInfo.get(i), subjectList.get(i), periodList.get(i), bInfo.get(i), noOfUnitsList.get(i), studyNo);
					break;
				case 1:
					try {
						labelTwoSachetgeneration(writer, barcodeVal.get(i), tInfo.get(i), subjectList.get(i), periodList.get(i), bInfo.get(i), noOfUnitsList.get(i), studyNo);
					}catch(Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
			writer.println("PRINT 1,1");
			writer.close();
			return writer;
		}
public PrintWriter generateAdvitySachetBarcodeForThreeLabelsPrint(String path, int size, List<String> barcodeVal, List<String> tInfo, List<String> subjectList, List<String> periodList, 
		List<String> bInfo, List<String> noOfUnitsList, String studyNo) throws FileNotFoundException{
//	System.out.println("Pnr file : " + path);
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
//		System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
		switch (i) {
		case 0:
			if(subjectList.size() > 0 && !subjectList.get(i).equals(""))
				labelOneSachetForThreeLabelsgeneration(writer, barcodeVal.get(i), tInfo.get(i), subjectList.get(i), periodList.get(i), bInfo.get(i), noOfUnitsList.get(i), studyNo);
			break;
		case 1:
			if(subjectList.size() > 1 && !subjectList.get(i).equals(""))
				labelTwoSachetForThreeLabelsgeneration(writer, barcodeVal.get(i), tInfo.get(i), subjectList.get(i), periodList.get(i), bInfo.get(i), noOfUnitsList.get(i), studyNo);
			break;
		case 2:
			if(subjectList.size() > 2 && !subjectList.get(i).equals(""))
				labelThreeSachetForThreeLabelsgeneration(writer, barcodeVal.get(i), tInfo.get(i), subjectList.get(i), periodList.get(i), bInfo.get(i), noOfUnitsList.get(i), studyNo);
			break;
		default:
			break;
		}
	}
	writer.println("PRINT 1,1");
	writer.close();
	return writer;
}
private void labelOneSachetForThreeLabelsgeneration(PrintWriter writer, String code, String treatmentCode, String subjectNo, String periodNo,
		String bInfo, String noOfUnits, String studyNo) {
		String zero="0";
		String subjectText = "Subject No: ";
		writer.println("QRCODE 876,126,L,5,A,180,M2,S7,"+'"'+code+'"');
		writer.println("TEXT 859,189,"+'"'+zero+'"'+",180,25,11,"+'"'+treatmentCode+'"');
		writer.println("BOX 768,137,866,199,4");
		writer.println("TEXT 928,109,"+'"'+zero+'"'+",180,11,8,"+'"'+subjectNo+'"');
		writer.println("TEXT 1085,195,"+'"'+zero+'"'+",180,5,6,"+'"'+studyNo+'"');
		writer.println("TEXT 1085,152,"+'"'+zero+'"'+",180,7,6,"+'"'+periodNo+'"');
		writer.println("TEXT 1085,105,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectText+'"');
		writer.println("TEXT 1085,62,"+'"'+zero+'"'+",180,7,6,"+'"'+noOfUnits+'"');
		writer.println("TEXT 968,28,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
		
	
}
	private void labelTwoSachetForThreeLabelsgeneration(PrintWriter writer, String code, String treatmentCode, String subjectNo, String periodNo,
			String bInfo, String noOfUnits, String studyNo) {
			String zero="0";
			String subjectText = "Subject No: ";
			writer.println("QRCODE 506,126,L,5,A,180,M2,S7,"+'"'+code+'"');
			
			writer.println("TEXT 489,189,"+'"'+zero+'"'+",180,25,11,"+'"'+treatmentCode+'"');
			writer.println("BOX 398,137,496,199,4");
			writer.println("TEXT 558,109,"+'"'+zero+'"'+",180,11,8,"+'"'+subjectNo+'"');
			writer.println("TEXT 715,195,"+'"'+zero+'"'+",180,5,6,"+'"'+studyNo+'"');
			writer.println("TEXT 715,152,"+'"'+zero+'"'+",180,7,6,"+'"'+periodNo+'"');
			writer.println("TEXT 715,105,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectText+'"');
			writer.println("TEXT 715,62,"+'"'+zero+'"'+",180,7,6,"+'"'+noOfUnits+'"');
			writer.println("TEXT 598,28,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
		
	}
	private void labelThreeSachetForThreeLabelsgeneration(PrintWriter writer, String code, String treatmentCode, String subjectNo, String periodNo,
				String bInfo, String noOfUnits, String studyNo) {
				String zero="0";
				String subjectText = "Subject No: ";
				writer.println("QRCODE 136,126,L,5,A,180,M2,S7,"+'"'+code+'"');
				
				writer.println("TEXT 119,189,"+'"'+zero+'"'+",180,25,11,"+'"'+treatmentCode+'"');
				writer.println("BOX 28,137,126,199,4");
				writer.println("TEXT 188,109,"+'"'+zero+'"'+",180,11,8,"+'"'+subjectNo+'"');
				writer.println("TEXT 345,195,"+'"'+zero+'"'+",180,5,6,"+'"'+studyNo+'"');
				writer.println("TEXT 345,152,"+'"'+zero+'"'+",180,7,6,"+'"'+periodNo+'"');
				writer.println("TEXT 345,105,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectText+'"');
				writer.println("TEXT 345,62,"+'"'+zero+'"'+",180,7,6,"+'"'+noOfUnits+'"');
				writer.println("TEXT 228,28,"+'"'+zero+'"'+",180,7,6,"+'"'+bInfo+'"');
			
		}
private void labelTwoSachetgeneration(PrintWriter writer, String code, String treatmentCode, String subjectNo, String periodNo,
		String bInfo, String noOfUnits, String studyNo) {
		String zero="0";
		String subjectText = "Subject No: ";
		writer.println("QRCODE 145,127,L,3,A,180,M2,S7,"+'"'+code+'"');
		
        writer.println("TEXT 422,248,"+'"'+zero+'"'+",180,7,6,"+'"'+studyNo+'"');
		writer.println("TEXT 417,210,"+'"'+zero+'"'+",180,7,6,"+'"'+periodNo+'"');
		writer.println("TEXT 417,158,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectText+'"');
		writer.println("TEXT 417,108,"+'"'+zero+'"'+",180,7,6,"+'"'+noOfUnits+'"');
		writer.println("TEXT 119,229,"+'"'+zero+'"'+",180,16,14,"+'"'+treatmentCode+'"');
		writer.println("BOX 62,151,148,252,4");
		writer.println("TEXT 220,48,"+'"'+zero+'"'+",180,5,5,"+'"'+bInfo+'"');
		writer.println("TEXT 279,167,"+'"'+zero+'"'+",180,11,10,"+'"'+subjectNo+'"');
}

private void labelOneSachetgeneration(PrintWriter writer, String code, String treatmentCode, String subjectNo, String periodNo,
			String bInfo, String noOfUnits, String studyNo) {
		String zero="0";
		String subjectText = "Subject No: ";
        writer.println("QRCODE 588,127,L,3,A,180,M2,S7,"+'"'+code+'"');
		
        writer.println("TEXT 865,248,"+'"'+zero+'"'+",180,7,6,"+'"'+studyNo+'"');
		writer.println("TEXT 860,210,"+'"'+zero+'"'+",180,7,6,"+'"'+periodNo+'"');
		writer.println("TEXT 860,158,"+'"'+zero+'"'+",180,7,6,"+'"'+subjectText+'"');
		writer.println("TEXT 860,108,"+'"'+zero+'"'+",180,7,6,"+'"'+noOfUnits+'"');
		writer.println("TEXT 562,229,"+'"'+zero+'"'+",180,16,14,"+'"'+treatmentCode+'"');
		writer.println("BOX 505,151,591,252,4");
		writer.println("TEXT 663,48,"+'"'+zero+'"'+",180,5,5,"+'"'+bInfo+'"');
		writer.println("TEXT 722,167,"+'"'+zero+'"'+",180,11,10,"+'"'+subjectNo+'"');
	}
}
