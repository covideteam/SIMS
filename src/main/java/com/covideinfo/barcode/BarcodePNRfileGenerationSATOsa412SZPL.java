package com.covideinfo.barcode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class BarcodePNRfileGenerationSATOsa412SZPL {
	public PrintWriter pnrFileCreationLines(String path, int size, List<String> barcodeVal, 
			List<String> tInfo, List<String> mInfo)  throws FileNotFoundException{
		return pnrFileCreationLines(path, size, barcodeVal, tInfo, mInfo, null, null, null, null);
//		return vmtPnrFileCreationLines(path, size, barcodeVal, tInfo, mInfo, null, null, null, null);
	}
	public PrintWriter vmtPnrFileCreationLines(String path, int size, List<String> barcodeVal, 
			List<String> tInfo, List<String> mInfo, List<String> lInfo, 
			List<String> tInfo2, List<String> mInfo2, List<String> lInfo2) throws FileNotFoundException {
		System.out.println("Pnr file : " + path);
		PrintWriter writer = new PrintWriter(path);
		writer = new PrintWriter(path);
		writer.println("<xpml><page quantity='0' pitch='20.1 mm'></xpml>^XA");
		writer.println("^MCY^PMN");
		writer.println("^PW898");
		writer.println("~JSN^MMT");
		writer.println("^JZY");
		writer.println("^LH0,0^LRN");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml><xpml><page quantity='0' pitch='20.1 mm'></xpml>~DGR:SSGFX000.GRF,11524,86,1F83FFC1F007E0030030FC03EJ0FC0018iU0FC1FFE0F803F00180187E01FJ07EI0C,3FC3FFC3FC0FF0030031FE07F8001FE0038iT01FE1FFE1FE07F8018018FF03FCI0FF001C,70E001870C1C38070063870E18003870078iT0387I0C3860E1C038031C3870C001C3803C,6060018E0618180F0066039C0C006038078iT0303I0C7030C0C07803301CE0600301C03C,6030030C06180C3F006601980C0060180D8iT030180186030C061F803300CC0600300C06C,C03006I06300C7B006001800CJ0180D8iT0601803I0318063D803I0C006K0C06C,C03006I06300C6300C001800CJ018198iT0601803I03180631806I0C006K0C0CC,C0300C001C300C0300C0018038J018318iT0601806I0E180601806I0C01CK0C18C,C0300C00F8300C0300C00301FK030318iT0601806007C18060180600180F8J01818C,C0301800FC300C0300C00301F8J030618iT060180C007E18060180600180FCJ01830C,C03018I0E300C03018006001CJ060618iT060180CI0718060180C003I0EJ03030C,C03018I07300C0301800CI0EJ0C0C18iT060180CI0398060180C006I07J06060C,C0303J03300C03018008I067E0081818iT0601818I0198060180C004I033F0040C0C,C0303J03300C0301801J067E0101FFEiT0601818I0198060180C008I033F0080IF,C0303J03300C0303006J06I0601FFEiT0601818I0198060181803J03I0300IF,6060300C031818030300C01806I0CI018iT030301806018C0C018180600C03I06J0C,6060600E061818030301801C0C0018I018iT030303007030C0C018180C00E06I0CJ0C,70E060060E1C38030303I0C1C003J018iT038703003070E1C01818180060E0018J0C,3FC06003FC0FF0030607FF87F8007FF8018iT01FE03001FE07F8018303FFC3FC003FFC00C,1F806001F007E0030607FF83EI07FF8018iU0FC03I0F803F0018303FFC1FI03FFC00C,,:::::::::::::::::::::::::::::::::0FCJ0CJ03E003jS07EJ06J01F00180,1FEJ0CJ07F003K01FFCR03iQ0FFJ06J03F8018K0FFER0180,307J0CJ0E3807K01FFER03iP01838I06J071C038K0IFR0180,603J0CJ0C180FK01807R07iP03018I06J060C078K0C038Q0380,6018I0CJ08181FK018038Q0FiP0300CI06J040C0F8K0C01CQ0780,6019818CE03180C3BK018018P03FiP0300CC0C67018C061D8K0C00CP01F80,6001818FF03180C33K0180181F01BC00607BiP03I0C0C7F818C06198K0C00C0F80DE00303D80,7801818F180180C03K0180187FC1FC006063iP03C00C0C78C00C06018K0C00C3FE0FE00303180,3F01818C080180C03K018018E0E1CL03iP01F80C0C60400C06018K0C00C7070EL0180,1FE1818C0C0180C03K018038C061CL03iQ0FF0C0C60600C06018K0C01C6030EL0180,03F1818C0C0180C03K01807180318L03iQ01F8C0C60600C06018K0C038C018CL0180,0071818C0C0180C03K01FFE180318L03iR038C0C60600C06018K0IF0C018CL0180,0019818C0C0180C03K01FF81IF18L03iS0CC0C60600C06018K0FFC0IF8CL0180,C019818C0C0180C03K018001IF18L03iP0600CC0C60600C06018K0CI0IF8CL0180,C019818C0C0180C03K01800180018L03iP0600CC0C60600C06018K0CI0CI0CL0180,E019818C0C0080803K01800180018L03iP0700CC0C60600404018K0CI0CI0CL0180,6039838C0800C1803K018001C0018L03iP0301CC1C6040060C018K0CI0EI0CL0180,7871C78E3800E3803K018I0C0318L03iP03C38E3C71C0071C018K0CI06018CL0180,3FE0FD8FF0307F003K018I0F0E18L03iP01FF07EC7F8183F8018K0CI07870CL0180,0FC0798DE0303E003K018I07FC18I06003iQ07E03CC6F0181F0018K0CI03FE0CI0300180,gH018I01F818I06003jS0CJ0FC0CI0300180,,::::::::::::::::::::::::3E008003E07C00CN021F0040080F80208iP01F004001F03E006N010F80200407C0104,7F018007F0FE00CN043F80C0181FC0604iP03F80C003F87F006N021FC0600C0FE0302,6303800630C600CN0C3181C03818C0E06iP03181C0031863006N0618C0E01C0C60703,C307800C318600CN086183C07830C1E02iP06183C00618C3006N0430C1E03C1860F01,C18D800C198300DE1B8I01860C6C0D83063603iP060C6C0060CC1806F0DCJ0C3063606C1831B0180,C189800C198300FE1F8I01860C4C0983062603iP060C4C0060CC1807F0FCJ0C3062604C183130180,C181800C198300E71CJ01060C0C0183060601iP060C0C0060CC180738EK083060600C183030080,C181800C198300C318J03060C0C01830606018iO060C0C0060CC180618CJ0183060600C1830300C0,:::::41018004108200C318J0302080C01810406018iO02080C002084100618CJ0181040600C0820300C0,6301800630C600C318J0303180C01818C06018P01C020107E1C02hL03180C003186300618CJ01818C0600C0C60300CQ0E010083F0E017F018187F0FE00C318J0183F80C0181FC0603Q03E020107E3E02hL03F80C0C3F87F00618CK0C1FC0600C0FE03018P01F010083F1F013E018183E07C00C318J0181F00C0180F80603Q02206030402206hL01F00C0C1F03E00618CK0C0F80600C07C03018P01103018201103gH018T03Q0220E07040410EiO0CT018P01107038202087gI08T02Q0410E07080410EiO04T01Q02087038402087gI0CT06Q0411A0D080011AiO06T03Q0208D0684I08DgI04T04Q04112090B80112iO02T02Q020890485C0089gI02T08Q04102010FC0102iO01T04Q020810087E0081hP04102010C40102jV02081008620081hP04102010820202jV02081008410101hP04102010020202jV02081008010101hP04102010020402jV02081008010201:hP04102010020802jV02081008010401:hP04102010821002jV02081008410801hP02202010823002jV01101008411801hP02202010442002jV01101008221001hP03E020107C7F02jV01F010083E3F81hP01C02010387F02jW0E010081C3F81");
		writer.println("<xpml></page></xpml><xpml><page quantity='1' pitch='20.1 mm'></xpml>^XA");

		for (int i = 0; i < size; i++) {
//			System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
			switch (i) {
			case 0:
				vmtLableOne(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 1:
				vmtLableTwo(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 2:
				lableThree(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 3:
				lableFour(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 4:
				lableFive(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 5:
				lableSix(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			default:
				break;
			}
		}
		writer.println("^FO60,162");
		writer.println("^XGR:SSGFX000.GRF,1,1^FS");
		writer.println("^PQ1,0,1,Y");
		writer.println("^XZ");
		writer.println("<xpml></page></xpml>^XA");
		writer.println("^IDR:SSGFX000.GRF^XZ");
		writer.println("<xpml><end/></xpml>");
		writer.close();
		return writer;
	}
	private void vmtLableOne(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		writer.println("^FO242,130");
		writer.println("^BY6^BXN,6,200,16,16,1^FD" + code + "^FS");
	}

	private void vmtLableTwo(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		writer.println("^FO679,130");
		writer.println("^BQN,2,5^FDLA," + code + "^FS");
	}
	public PrintWriter pnrFileCreationLines(String path, int size, List<String> barcodeVal, 
			List<String> tInfo, List<String> mInfo, List<String> lInfo, 
			List<String> tInfo2, List<String> mInfo2, List<String> lInfo2) throws FileNotFoundException {
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

		for (int i = 0; i < size; i++) {
//			System.out.println(tInfo.get(i)+"\t"+ bInfo.get(i));
			switch (i) {
			case 0:
				lableOne(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 1:
				lableTwo(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 2:
				lableThree(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 3:
				lableFour(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 4:
				lableFive(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
				break;
			case 5:
				lableSix(writer, barcodeVal.get(i), tInfo.get(i), mInfo.get(i), lInfo.get(i),
						tInfo2.get(i), mInfo2.get(i), lInfo2.get(i));
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

	private void lableOne(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		writer.println("^FT80,41");
		writer.println("^CI0");
		writer.println("^A0N,29,20^FD" + tInfo + " "+ tInfo2 +"^FS");
		writer.println("^FO191,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD" + code + "^FS");
		writer.println("^FT238,43");
		writer.println("^A0N,25,24^FD"+ lInfo +" "+ lInfo2 +"^FS");
		writer.println("^FT110,78");
		writer.println("^A0N,25,30^FD" + mInfo + " " + mInfo2 +"^FS");
	}

	private void lableTwo(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		writer.println("^FT529,41");
		writer.println("^CI0");
		writer.println("^A0N,29,20^FD" + tInfo + " "+ tInfo2 + "^FS");
		writer.println("^FO640,97");
		writer.println("^BY6^BXN,6,200,16,16,1^FD" + code + "^FS");
		writer.println("^FT687,43");
		writer.println("^A0N,25,24^FD"+ lInfo +" "+ lInfo2 +"^FS");
		writer.println("^FT559,78");
		writer.println("^A0N,25,30^FD" + mInfo + " " + mInfo2 + "^FS");
	}

	private void lableThree(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		// TODO Auto-generated method stub

	}

	private void lableFour(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		// TODO Auto-generated method stub

	}

	private void lableFive(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		// TODO Auto-generated method stub

	}

	private void lableSix(PrintWriter writer, String code, String tInfo, String mInfo, String lInfo, String tInfo2, String mInfo2, String lInfo2) {
		// TODO Auto-generated method stub

	}

}
