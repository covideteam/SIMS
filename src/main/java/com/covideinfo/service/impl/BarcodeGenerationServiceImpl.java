package com.covideinfo.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.barcode.BarcodePNRfileGenerationSATOsa412SZPL;
import com.covideinfo.barcode.PrintBarcodeFile;
import com.covideinfo.barcode.VacutainerBarcodePNRfileGeneration;
import com.covideinfo.dao.BarcodeGenerationDao;
import com.covideinfo.dao.StudyDao;
import com.covideinfo.dto.BarcodeDetailsDto;
import com.covideinfo.dto.BarcodesDto;
import com.covideinfo.dto.SampleTimepointDto;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.pdf.report.TextPdf;
import com.covideinfo.service.BarcodeGenerationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("barcodeGenerationService")
public class BarcodeGenerationServiceImpl implements BarcodeGenerationService {

	@Autowired
	BarcodeGenerationDao barcodGenDao;
	@Autowired
	StudyDao studyDao;

	@Override
	public BarcodesDto getBarcodesDtoDetails(Long studyId, String barcodeType) {
		BarcodesDto bcDto = null;
		BarcodeDetailsDto bcdDto = null;
		List<Integer> studyGroupStanbys = null;
		int groupStanByCount = 0;
		try {
			bcdDto = barcodGenDao.getBarcodesDtoDetails(studyId, barcodeType);
			if (bcdDto != null) {
				bcDto = new BarcodesDto();
				bcDto.setSpmList(bcdDto.getSpmList());
				bcDto.setStudy(bcdDto.getStudy());
				bcDto.setSampTpList(bcdDto.getStpList());
				studyGroupStanbys = bcdDto.getStudyGroupStanbys();
			}
			if(studyGroupStanbys != null && studyGroupStanbys.size() > 0) {
				for(Integer sgs : studyGroupStanbys)
					groupStanByCount = groupStanByCount +sgs;
			}
			bcDto.setStudyGroupStanbysCount(groupStanByCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bcDto;
	}

	@Override
	public Map<String, String> getSubjectsMap(StudyMaster sm, String standByPrefix, String type, int groupStandByCount) {
		TreeMap<String, String> subjectNosMap = new TreeMap<String, String>();
		for (int subjectNo = 1; subjectNo <= sm.getNoOfSubjects(); subjectNo++) {
			if (subjectNo < 10)
				subjectNosMap.put("0" + subjectNo, "0" + subjectNo);
			else
				subjectNosMap.put(subjectNo + "", subjectNo + "");
		}
//		if(type != null && type.equals("Subject")) {
		for (int subjectNo = 1; subjectNo <= (sm.getNoOfStandBySubjects()+groupStandByCount); subjectNo++) {
			subjectNosMap.put(standByPrefix + subjectNo, standByPrefix + subjectNo);
		}
//		}
		return subjectNosMap;

	}

	@Override
	public BarcodesDto getBarcodesDtoDetailsForBarcodesPrint(Long studyId, Long periodId, String type) {
		BarcodesDto bcDto = null;
		BarcodeDetailsDto bcdDto = null;
		Map<String, StudySubjects> subMap = new HashMap<>();
		Map<Long, StudyPeriodMaster> spmMap = new HashMap<>();// periodId, spmPojo
		List<StudyPeriodMaster> spmList = null;
		List<DoseTimePoints> doseList = null;
		List<SubjectRandamization> srzList = null;
		Map<String, TreatmentInfo> subTrMap = new HashMap<>();
		TreeMap<Long, List<DoseTimePoints>> doseTpMap = new TreeMap<>();
		Map<String, SubjectRandamization> subRzMap = new HashMap<>();
		List<SampleTimePoints> sampleList = null;
		Map<Long, List<SampleTimePoints>> sampTpMap = new HashMap<>();
		List<Integer> studyGroupStanbys = null;
		int groupStandbyCount = 0;
		try {
			bcdDto = barcodGenDao.getBarcodesDtoDetailsForBarcodesPrint(studyId, periodId, type);
			if (bcdDto != null) {
				spmList = bcdDto.getSpmList();
				doseList = bcdDto.getDosetpList();
				srzList = bcdDto.getSrzList();
				sampleList = bcdDto.getStpList();
				studyGroupStanbys = bcdDto.getStudyGroupStanbys();
				if (spmList != null && spmList.size() > 0) {
					for (StudyPeriodMaster spm : spmList) {
						spmMap.put(spm.getId(), spm);
					}
				}
				if (srzList != null && srzList.size() > 0) {
					for (SubjectRandamization srz : srzList) {
						subTrMap.put(srz.getSubjectNo(), srz.getTreatmentInfo());
						subRzMap.put(srz.getSubjectNo(), srz);
					}
				}
				List<DoseTimePoints> doseTempList = null;
				if (doseList != null && doseList.size() > 0) {
					for (DoseTimePoints dose : doseList) {
						if (doseTpMap.containsKey(dose.getTreatmentInfo().getId())) {
							doseTempList = doseTpMap.get(dose.getTreatmentInfo().getId());
							doseTempList.add(dose);
							doseTpMap.put(dose.getTreatmentInfo().getId(), doseTempList);
						} else {
							doseTempList = new ArrayList<>();
							doseTempList.add(dose);
							doseTpMap.put(dose.getTreatmentInfo().getId(), doseTempList);
						}
					}
				}
//				List<String> tpList = new ArrayList<>();
				Map<String, List<Integer>> tpListMap = new HashMap<>();
				List<Integer> vacnoList = null;
				List<SampleTimePoints> sampTempList = null;
				if (sampleList != null && sampleList.size() > 0) {
					for (SampleTimePoints samp : sampleList) {
//						if (!tpList.contains(samp.getSign() + samp.getTimePoint())) {
						if(!tpListMap.containsKey(samp.getSign() + samp.getTimePoint()) || (tpListMap.size() > 0 && !tpListMap.get(samp.getSign() + samp.getTimePoint()).contains(samp.getVacutainerNo()))) {
							if (sampTpMap.containsKey(0L)) {
								sampTempList = sampTpMap.get(0L);
								sampTempList.add(samp);
								sampTpMap.put(0L, sampTempList);
//								tpList.add(samp.getSign() + samp.getTimePoint());
								if(tpListMap.containsKey(samp.getSign() + samp.getTimePoint()))
									vacnoList = tpListMap.get(samp.getSign() + samp.getTimePoint());
								else 
									vacnoList = new ArrayList<>();
								vacnoList.add(samp.getVacutainerNo());
								tpListMap.put(samp.getSign() + samp.getTimePoint(), vacnoList);
							} else {
								sampTempList = new ArrayList<>();
								sampTempList.add(samp);
								sampTpMap.put(0L, sampTempList);
//								tpList.add(samp.getSign() + samp.getTimePoint());
								if(tpListMap.containsKey(samp.getSign() + samp.getTimePoint()))
									vacnoList = tpListMap.get(samp.getSign() + samp.getTimePoint());
								else 
									vacnoList = new ArrayList<>();
								vacnoList.add(samp.getVacutainerNo());
								tpListMap.put(samp.getSign() + samp.getTimePoint(), vacnoList);
							}
						}
					}
				}
				if(studyGroupStanbys != null && studyGroupStanbys.size() > 0) {
					for(Integer sgs : studyGroupStanbys)
						groupStandbyCount = groupStandbyCount+sgs;
				}
				bcDto = new BarcodesDto();
				bcDto.setSpmList(bcdDto.getSpmList());
				bcDto.setStudy(bcdDto.getStudy());
				bcDto.setSubMap(subMap);
				bcDto.setSpmMap(spmMap);
				bcDto.setDoseTpMap(doseTpMap);
				bcDto.setSubTrMap(subTrMap);
				bcDto.setSubRzMap(subRzMap);
				bcDto.setSampTpMap(sampTpMap);
				bcDto.setStudyGroupStanbysCount(groupStandbyCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bcDto;
	}

	@Override
	public String generateSubjectBarcode(BarcodesDto bcDto, String subjectNo, HttpServletRequest request, Long periodId,
			String noOfColumns, String studyIdCardBarcodeLable, String barcodePrinterip, HttpServletResponse response,
			String type, Map<String, String> subjectsMap) {
		String realPath = request.getServletContext().getRealPath("/");
		String path = realPath + "//BarcodeGeneration";
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		@SuppressWarnings("unused")
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font regular = new Font(calibri.getFamily(), 11);
		StudyMaster sm = bcDto.getStudy();
		String filepath = path +"/"+sm.getId()+"_"+"sub_BarCode.pdf";
		String subNo = "";
		SortedMap<Integer, String> subjectNosMap = new TreeMap<>();
		SortedMap<Integer, String> barcodeMap = new TreeMap<>();
		try {
			/*
			 * response.setContentType("application/pdf"); OutputStream out =
			 * response.getOutputStream();
			 */
			Document document = new Document();
			document.addTitle("Subject BarCodes");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 20); // A4
			document.setMarginMirroring(false);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			Code128Writer barcodeWriter = null;
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100f);
			PdfPCell cell = null;
			TreeMap<String, String> subMap = new TreeMap<>();
			if (type.equals("Single")) {
				subNo = subjectsMap.get(subjectNo);
				subMap.put(subjectNo, subjectNo);
			} else
				subMap.putAll(subjectsMap);

			int count = 0;
			int tabColCount = 0;
			if (subMap.size() > 0) {
				for (Map.Entry<String, String> submap : subMap.entrySet()) {
					subNo = submap.getValue();
					StringBuilder sb = new StringBuilder();
					sb.append("02");
					sb.append("a");
					sb.append(subNo);
					sb.append("a");
					sb.append(sm.getId());
					sb.append("n");

					subjectNosMap.put(count, subNo);
					barcodeMap.put(count, sb.toString());
					String dispalyValue = "";
					dispalyValue = "Project No .: " + sm.getProjectNo() + "\n" + "Subject No .: " + subNo
							+ "\n"+sb.toString();

					Paragraph p = new Paragraph();
					p.add(dispalyValue);
					p.setFont(regular);

					cell = new PdfPCell();
					cell.addElement(p);

					barcodeWriter = new Code128Writer();
					BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.CODE_128, 100, 35);
					BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
					Image image = Image.getInstance(bImage, null);
					image.scaleAbsolute(100, 35);
					cell.addElement(image);

					cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					cell.setPadding(7f);
					cell.setNoWrap(false);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

//						document.add(image);
					count++;
					if (tabColCount == 3) {
						tabColCount = 1;
					} else
						tabColCount++;
				}
				if (tabColCount < 3) {
					for (int i = tabColCount; i <= 3; i++) {
						cell = new PdfPCell(new Phrase(""));
						cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
						cell.setPadding(7f);
						cell.setNoWrap(false);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);
					}
				}
			}
			document.add(table);
			document.close();
			BarcodePNRfileGenerationSATOsa412SZPL barcodeBo = new BarcodePNRfileGenerationSATOsa412SZPL();
			int cval = 0;
			List<String> fileNames = new ArrayList<>();
			for (int i = 0; i < barcodeMap.size(); i++) {
				if (i <= (barcodeMap.size() / Integer.parseInt(noOfColumns))) {
					String prnFilePath = realPath + "//SubjectBarcodes";
					File fileLocation = new File(prnFilePath);
					if (!fileLocation.exists())
						fileLocation.mkdirs();
					prnFilePath = prnFilePath + "/SubjectNo_" + (i + 1) + ".prn";
					fileNames.add(prnFilePath);
					List<String> code = new ArrayList<>();
					List<String> projectInfo = new ArrayList<>();
					List<String> subInfo = new ArrayList<>();
					for (int j = 0; j < Integer.parseInt(noOfColumns); j++) {
						String barcode = barcodeMap.get(cval);
						if (barcode == null) {
							projectInfo.add("");
							subInfo.add("");
						} else {
							code.add(barcode);
							projectInfo.add(sm.getProjectNo());
							subInfo.add(subjectNosMap.get(cval));
						}
						cval++;
					}
					if (studyIdCardBarcodeLable.equals("Yes")) {
						barcodeBo.pnrFileCreationLines(prnFilePath, Integer.parseInt(noOfColumns), code, projectInfo,subInfo);
					}

				}
			}
			if (studyIdCardBarcodeLable.equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = barcodePrinterip;
				for (String fileName : fileNames) {
					try {
						pbf.printBarcode(fileName);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}

//			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	@SuppressWarnings("unused")
	@Override
	public String generateSachetBarcode(Long periodId, HttpServletRequest request, HttpServletResponse response,
			String noOfColumns, String studyIdCardBarcodeLable, String barcodePrinterip, String claietName,
			BarcodesDto bcDto, Map<String, String> subjectsMap) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fpath = realPath + "//BarcodeGeneration";
		File file = new File(fpath);
		if (!file.exists())
			file.mkdirs();
		String filepath = fpath + "/sachet_BarCode.pdf";
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
//		Font actHeadFont = new Font(calibri.getFamily(), 12, Font.BOLD, BaseColor.BLACK);
//		BaseColor bgColor = new BaseColor(211, 211, 211); // #D3D3D3
		Font regular = new Font(calibri.getFamily(), 11);
		StudyPeriodMaster period = null;
		TreeMap<Long, List<DoseTimePoints>> doseTpMap = null;
		StudyMaster sm = null;
		Map<String, TreatmentInfo> subTrMap = null;
		List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
		Map<String, SubjectRandamization> subRzMap = null;
		Code128Writer barcodeWriter = null;
		Image image = null;
		try {
			Document document = new Document();
			document.addTitle("Sachet Barcodes");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 20); // A4
			document.setMarginMirroring(false);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100f);
			PdfPCell cell = null;
			sm = bcDto.getStudy();
			period = bcDto.getSpmMap().get(periodId);
			subTrMap = bcDto.getSubTrMap();
			doseTpMap = bcDto.getDoseTpMap();
			subRzMap = bcDto.getSubRzMap();

//			int indexpage = 1;
			int tabCellCount = 1;
			int count = 1;
			if (bcDto.getSubTrMap().size() > 0) {
				TreeMap<String, String> subMap = new TreeMap<>();
				subMap.putAll(subjectsMap);
//				for (Map.Entry<Long, List<DoseTimePoints>> dosemap : doseTpMap.entrySet()) {
					for (Map.Entry<String, String> sub : subMap.entrySet()) {
						count = 1;
						TreatmentInfo treatment = subTrMap.get(sub.getValue());
						List<DoseTimePoints> doseList = null;
						if(treatment != null) {
							doseList = doseTpMap.get(treatment.getId());
						}
						if(doseList != null && doseList.size() > 0) {
							for (DoseTimePoints dose : doseList) {
								int index = 1;
								if (treatment != null && treatment.getId() == dose.getTreatmentInfo().getId()) {
									if (count == 1) {
										/*cell = new PdfPCell(
												new Phrase(dose.getTreatmentInfo().getTreatmentName() + "", actHeadFont));
										cell.setBorderColor(new BaseColor(73, 74, 82)); // ##494c52
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										cell.setBackgroundColor(bgColor);
										cell.setPaddingTop(3f);
										cell.setPaddingBottom(7f);
										cell.setPaddingLeft(7f);
										cell.setPaddingRight(7f);

										cell.setNoWrap(false);
										cell.setColspan(3);
										table.addCell(cell);*/
//									    table.getDefaultCell().setBackgroundColor(bgColor);
										count++;
									}
									StringBuffer sb = new StringBuffer();
									sb.append("03");
									sb.append("a");
									sb.append(period.getId());
									sb.append("a");
									sb.append(treatment.getId());
									sb.append("a");
									sb.append(sub.getValue());
									sb.append("a");
									sb.append(dose.getId());
									sb.append("n");
									String timepoint = "(" + dose.getTimePoint() + " hr) ";
									String actualTp = dose.getTimePoint();
									StringBuffer seqNo = new StringBuffer();
									seqNo.append(sub.getValue());
									seqNo.append(period.getPeriodNo());
									if (dose.getTimePointNo() < 10)
										seqNo.append("0" + dose.getTimePointNo());
									else
										seqNo.append(dose.getTimePointNo());
									seqNo.append(treatment.getRandamizationCode());
									sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(), sm.getProjectNo(),
											sub.getValue(), period.getPeriodNo() + "", timepoint, "",
											dose.getTreatmentInfo().getRandamizationCode(),
											subRzMap.get(sub.getValue()).getSeqNo(), dose.getTimePointNo(),
											seqNo.toString(), actualTp, Integer.parseInt(sub.getValue())));
									/*
									 * if (indexpage > 5) { document.newPage(); indexpage = 1; }
									 */
									Paragraph p = new Paragraph();
									if (claietName.equals("ADV")) {
										p.add(new Paragraph(sm.getProjectNo() + "\n" + sub.getValue() + " P"
												+ period.getPeriodNo() + timepoint + "" + "\n" + seqNo.toString()
												+ "\n"+sb.toString()));
										p.setFont(regular);
										barcodeWriter = new Code128Writer();
										BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.CODE_128, 100,
												35);
										BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
										image = Image.getInstance(bImage, null);
										image.scaleAbsolute(100, 35);
//										document.add(image);
									} else {
										p.add(new Paragraph(sm.getProjectNo() + "\n" + "P" + period.getPeriodNo() + " "
												+ sub.getValue() + " " + timepoint + "" + "\n" + seqNo.toString()
												+ "\n"+sb.toString()));
										p.setFont(regular);
										barcodeWriter = new Code128Writer();
										BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.CODE_128, 100,
												35);
										BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
										image = Image.getInstance(bImage, null);
										image.scaleAbsolute(100, 35);
//										document.add(image);
									}
//									indexpage++;

									cell = new PdfPCell();
									cell.addElement(p);
									cell.addElement(image);

									cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
									cell.setPadding(7f);
									cell.setNoWrap(false);
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell);

									tabCellCount++;
									
									if (tabCellCount == 3)
										tabCellCount = 1;
								}
								index++;
							}
						}
					}
					if (tabCellCount < 3) {
						for (int i = tabCellCount; i <= 3; i++) {
							cell = new PdfPCell(new Phrase(""));
							cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							cell.setPadding(7f);
							cell.setNoWrap(false);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell);
						}
					}

//				}
			} else {
				TextPdf tpdf = new TextPdf();
				tpdf.generateTextPdf("Randamization Not Completed. Please Try Again.", request, response,
						"Sachet Barcodes");
			}
//			table.setHeaderRows(1);
			document.add(table);
			document.close();

			String path = realPath;
			path = path + "//SachetBarCodes";
			File f1 = new File(path);
			if (!f1.exists())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			BarcodePNRfileGenerationSATOsa412SZPL barcodeBo = new BarcodePNRfileGenerationSATOsa412SZPL();
			if (sampleTimepoints.size() > 0)
				Collections.sort(sampleTimepoints);
			for (int i = 0; i < sampleTimepoints.size(); i = i + Integer.parseInt(noOfColumns)) {
				path = folderPath + "/Sachet_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> lInfo = new ArrayList<>();
				List<String> tInfo2 = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> lInfo3 = new ArrayList<>();
				
				
				List<String> subjectList = new ArrayList<>();
				List<String> noOfUnitsList = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> periodList = new ArrayList<>();
				String studyNo = "";
				for (int j = 0; j < Integer.parseInt(noOfColumns); j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						code.add(cstpp.getBarcode());
						tInfo.add("Study No : " + cstpp.getProjectNo());
						mInfo.add(cstpp.getSubjectNo() + " " + "Period No :" + cstpp.getPeriodNo());
						lInfo.add(cstpp.getSeqNo() + " " + cstpp.getTreatment());
					} else {
						periodList.add("");
						noOfUnitsList.add("");
						subjectList.add("");
						code.add("");
						tInfo.add("");
						bInfo.add("");
						studyNo = "";
					}
				}
				if (studyIdCardBarcodeLable.equals("Yes")) {
					barcodeBo.pnrFileCreationLines(path, Integer.parseInt(noOfColumns), code, 
							tInfo, mInfo, lInfo, null, null,  null);
				}
			}
			if (studyIdCardBarcodeLable.equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = barcodePrinterip;
				for (String fileName : fileNames) {
					try {
						pbf.printBarcode(fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	@SuppressWarnings("unused")
	@Override
	public String generateVacutainersBarcodeAll(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, Map<String, String> subjectsMap,
			BarcodesDto bcDto) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fpath = realPath + "//BarcodeGeneration";
		File file = new File(fpath);
		if (!file.exists())
			file.mkdirs();
		String filepath = fpath + "/Vacutainer.pdf";
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font regular = new Font(calibri.getFamily(), 11);
		StudyPeriodMaster spm = null;
		Map<Long, List<SampleTimePoints>> sampTpMap = null;
		StudyMaster sm = null;
		Map<String, TreatmentInfo> subTrMap = null;
		List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
		Map<String, SubjectRandamization> subRzMap = null;
		Code128Writer barcodeWriter = null;
		Image image = null;
		TreeMap<String, String> subMap = new TreeMap<>();
		List<StudyPeriodMaster> spmList = new ArrayList<>();
		try {
			Document document = new Document();
			document.addTitle("Vacutainer Barcodes");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 20); // A4
			document.setMarginMirroring(false);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100f);
			PdfPCell cell = null;
			sm = bcDto.getStudy();
			if (periodId != 0) {
				spm = bcDto.getSpmMap().get(periodId);
				spmList.add(spm);
			} else {
				for (Map.Entry<Long, StudyPeriodMaster> spmMap : bcDto.getSpmMap().entrySet()) {
					spmList.add(spmMap.getValue());
				}
			}
			subTrMap = bcDto.getSubTrMap();
			sampTpMap = bcDto.getSampTpMap();
			subRzMap = bcDto.getSubRzMap();
			subMap.putAll(subjectsMap);

			int index = 1;
			String treatment = "";
			int tabCellCount = 0;
			int subjectOrder = 1;
			int tpDisplayNo = 0;
			String dstimePoint = "";
			if (spmList != null) {
				for (StudyPeriodMaster period : spmList) {
					tpDisplayNo = 0;
					dstimePoint = "";
					for (Map.Entry<Long, List<SampleTimePoints>> stpMap : sampTpMap.entrySet()) {
						List<SampleTimePoints> timepoints = stpMap.getValue();
						Collections.sort(timepoints);
						for (int i = 0; i < timepoints.size(); i++) {
							SampleTimePoints cstpp = timepoints.get(i);
							if (!dstimePoint.equals(cstpp.getSign() + cstpp.getTimePoint())) {
								dstimePoint = cstpp.getSign() + cstpp.getTimePoint();
								tpDisplayNo++;
							}
							for (Map.Entry<String, String> subject : subMap.entrySet()) {
								if (stpMap.getKey() != 0) {
									if (cstpp.getTreatmentInfo() != null)
										treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
								}
								boolean numFlag = checkNumeric(subject.getValue());
								boolean conFlag = false;
								if (numFlag) {
									conFlag = numFlag;
								} else {
									if (cstpp.getSign().trim().equals("-"))
										conFlag = true;
								}
								if (conFlag) {
									Paragraph p = new Paragraph();
									Paragraph p2 = new Paragraph();
									StringBuffer sb = new StringBuffer();
									sb.append("04");
									sb.append("a");
									sb.append(period.getId());
									sb.append("a");
									sb.append(stpMap.getKey());
									sb.append("a");
									sb.append(subject.getValue());
									sb.append("a");
									sb.append(cstpp.getId());
									sb.append("n");
									String timepoint = cstpp.getSign() + " " + cstpp.getTimePoint() + " hr ";
									String actualTp = cstpp.getSign() + cstpp.getTimePoint();
									StringBuffer seqNo = new StringBuffer();
									seqNo.append(subject.getValue());
									seqNo.append(period.getPeriodNo());
									if (cstpp.getTimePointNo() < 10)
										seqNo.append("0" + cstpp.getTimePointNo());
									else
										seqNo.append(cstpp.getTimePointNo());
									seqNo.append(cstpp.getVacutainerNo());
									sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
											period.getStudy().getProjectNo(), subject.getValue(),
											period.getPeriodNo() + "", timepoint, cstpp.getVacutainerNo() + "",
											treatment, subjectOrder, cstpp.getTimePointNo(), seqNo.toString(),
											actualTp));
//									if (claietName.equals("ADV")) {
										if (tpDisplayNo < 10) {
											p.add(period.getStudy().getProjectNo() + "\n Sub :" + subject.getValue()
													+ " Per :" + period.getPeriodNo() +" Vac :"+cstpp.getVacutainerNo()+ "\n" + timepoint + " ("
													+ subject.getValue() + period.getPeriodNo() + "0" + tpDisplayNo
													+ ")"
															+ "\n"+sb.toString());
										} else {
											p.add(period.getStudy().getProjectNo() + "\n Sub :" + subject.getValue()
													+ " Per :" + period.getPeriodNo() +" Vac :"+cstpp.getVacutainerNo()+ "\n" + timepoint + " ("
													+ subject.getValue() + period.getPeriodNo() + tpDisplayNo + ")"
															+ "\n"+sb.toString());
										}
										p.setFont(regular);
										barcodeWriter = new Code128Writer();
										BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.CODE_128,
												100, 35);
										BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
										image = Image.getInstance(bImage, null);
										image.scaleAbsolute(100, 35);
										p2.add(seqNo.toString());
										p2.setFont(regular);
//									} 
								/*else {
										p.add(period.getStudy().getProjectNo() + "  P" + period.getPeriodNo() + "\n"
												+ subject.getValue() + " " + timepoint + "\n" + cstpp.getVacutainerNo()
												+ "  " + seqNo.toString() + "  " + treatment);
										p.setFont(regular);
										barcodeWriter = new Code128Writer();
										BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.CODE_128,
												100, 35);
										BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
										image = Image.getInstance(bImage, null);
										image.scaleAbsolute(100, 35);
										p2.add("");
									}*/
									cell = new PdfPCell();
									cell.addElement(p);
									cell.addElement(image);
									cell.addElement(p2);

									cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
									cell.setPadding(7f);
									cell.setNoWrap(false);
									cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									table.addCell(cell);

									if (tabCellCount == 3)
										tabCellCount = 1;
									else
										tabCellCount++;

									subjectOrder++;
									index++;
								}
							}
						}
						if (tabCellCount < 3) {
							for (int j = tabCellCount; j <= 3; j++) {
								cell = new PdfPCell(new Phrase(""));
								cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								cell.setPadding(7f);
								cell.setNoWrap(false);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell);
							}
						}
					}
				}
				document.add(table);
				document.close();
			}
			String path = realPath;
			path = path + "\\VacutainerBarCodes";
			File f1 = new File(path);
			if (!f1.exists())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			String timePoint = "";
			BarcodePNRfileGenerationSATOsa412SZPL barcodeBo = new BarcodePNRfileGenerationSATOsa412SZPL();
			for (int i = 0; i < sampleTimepoints.size(); i = i + Integer.parseInt(noOfColumns)) {
				path = folderPath + "/Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				List<String> barcodeList = new ArrayList<>();
				List<String> tlist = new ArrayList<>();
				List<String> mlist = new ArrayList<>();
				List<String> llist = new ArrayList<>();
				
				List<String> tlist2 = new ArrayList<>();
				List<String> mlist2 = new ArrayList<>();
				List<String> llist2 = new ArrayList<>();
				
				
				List<String> subjectAndPeriodList = new ArrayList<>();
				List<String> projectNoList = new ArrayList<>();
				List<String> tpStrList = new ArrayList<>();
				List<String> sequenceList = new ArrayList<>();
				int tpNo = 0;
				for (int j = 0; j < Integer.parseInt(noOfColumns); j++) {
					if ((i + j) < sampleTimepoints.size()) {
						
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						if (!timePoint.equals(cstpp.getActualTp())) {
							timePoint = cstpp.getActualTp();
							tpNo++;
						}
						barcodeList.add(cstpp.getBarcode());
//						if (claietName.equals("ADV")) {
							String tpStr = "";
							if (tpNo < 10)
								tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo()
										+ "0" + tpNo + ")";
							else
								tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo()
										+ tpNo + ")\n" + cstpp.getSeqNo();
							llist.add(tpStr);
							tlist.add(cstpp.getProjectNo());
							mlist.add("Sub :" + cstpp.getSubjectNo() + " Per :" + cstpp.getPeriodNo()+" Vac :"+cstpp.getVacutinaerNo());
//							llist.add(cstpp.getSeqNo());
//						} 
					} else {
						barcodeList.add("");
						tpStrList.add("");
						subjectAndPeriodList.add("");
						tlist.add("");
						sequenceList.add("");
					}
				}
				if (studyIdCardBarcodeLable.equals("Yes")) {
					barcodeBo.pnrFileCreationLines(path, Integer.parseInt(noOfColumns), barcodeList, 
							tlist, mlist, llist, null, null, null);
				}
			}
			if (studyIdCardBarcodeLable.equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = barcodePrinterip;
				for (String fileName : fileNames) {
					try {
						pbf.printBarcode(fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	private boolean checkNumeric(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			Integer d = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public String generateVialBarcodePeriodWise(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, BarcodesDto bcDto,
			Map<String, String> subjectsMap, String subjNo, Long timePointId) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fpath = realPath + "//BarcodeGeneration";
		File file = new File(fpath);
		if (!file.exists())
			file.mkdirs();
		String filepath = fpath + "/VialBarcodes.pdf";
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font regular = new Font(calibri.getFamily(), 11);
		Map<Long, List<SampleTimePoints>> sampTpMap = null;
		StudyMaster sm = null;
		Map<String, TreatmentInfo> subTrMap = null;
		List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
		Map<String, SubjectRandamization> subRzMap = null;
		Code128Writer barcodeWriter = null;
		Image image = null;
		TreeMap<String, String> subMap = new TreeMap<>();
		StudyPeriodMaster spm = null;
		List<StudyPeriodMaster> spmList = new ArrayList<>();
		try {
			Document document = new Document();
			document.addTitle("Vial Barcodes");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 20); // A4
			document.setMarginMirroring(false);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100f);
			PdfPCell cell = null;
			sm = bcDto.getStudy();
			if (periodId != 0) {
				spm = bcDto.getSpmMap().get(periodId);
				spmList.add(spm);
			} else {
				for (Map.Entry<Long, StudyPeriodMaster> spmMap : bcDto.getSpmMap().entrySet()) {
					spmList.add(spmMap.getValue());
				}
			}
			subTrMap = bcDto.getSubTrMap();
			sampTpMap = bcDto.getSampTpMap();
			subRzMap = bcDto.getSubRzMap();
			subMap.putAll(subjectsMap);

			int index = 1;
			String treatment = "";
			int tabCellCount = 1;
			int subjectOrder = 1;
			int tpCountNo = 0;
			String timePointStr = "";
			SampleTimePoints smapleTimepoint = null;
			if (timePointId != null && timePointId != 0) {
				smapleTimepoint = studyDao.sampleTimePointsWithId(timePointId);
			}
			if (spmList != null) {
				for (StudyPeriodMaster period : spmList) {
					for (Map.Entry<Long, List<SampleTimePoints>> stpMap : sampTpMap.entrySet()) {
						List<SampleTimePoints> timepoints = stpMap.getValue();
						Collections.sort(timepoints);
						boolean tpFlag = true;
						for (SampleTimePoints cstpp : timepoints) {
								if (smapleTimepoint != null) {
									if ((cstpp.getSign()+cstpp.getTimePoint()).equals(smapleTimepoint.getSign()+smapleTimepoint.getTimePoint()))
										tpFlag = true;
									else
										tpFlag = false;
								}
								
								if (tpFlag) {
									if (!timePointStr.equals(cstpp.getSign() + cstpp.getTimePoint())) {
										timePointStr = cstpp.getSign() + cstpp.getTimePoint();
										tpCountNo++;
									}
									for (int vial = 1; vial <= cstpp.getNoOfVials(); vial++) {
										for (Map.Entry<String, String> subject : subMap.entrySet()) {
											boolean subFlag = true;
											if (subjNo != null && !subjNo.equals("") && !subjNo.equals("0")) {
												String[] strArr = subjNo.split("\\,");
												List<String> subList = new ArrayList<>();
												if (strArr.length > 0) {
													for (String st : strArr) {
														subList.add(st);
													}
												}

												if (subList.contains(subject.getValue())) {
													subFlag = true;
												} else
													subFlag = false;
											} else
												subFlag = true;
											if(subFlag) {
												try {
													Integer.parseInt(subject.getValue());
												}catch (Exception e) {
													if(!cstpp.getSign().contains("-"))
														subFlag = false;
												}
											}
											
											boolean numFlag = checkNumeric(subject.getValue());
											boolean conFlag = false;
											if (numFlag) {
												conFlag = numFlag;
											} else {
												if (cstpp.getSign().trim().equals("-"))
													conFlag = true;
											}
											if (conFlag && subFlag) {
												Paragraph p = new Paragraph();
												Paragraph p2 = new Paragraph();
												PdfPTable tab = new PdfPTable(1);
												tab.setWidthPercentage(100f);
												if (stpMap.getKey() != 0) {
													if (cstpp.getTreatmentInfo() != null)
														treatment = "Treatement "
																+ cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
												}
												StringBuffer sb = new StringBuffer();
												sb.append("05");
												sb.append("a");
												sb.append(period.getId());
												sb.append("a");
												sb.append(stpMap.getKey());
												sb.append("a");
												sb.append(subject.getValue());
												sb.append("a");
												sb.append(cstpp.getId());
												sb.append("a");
												if (vial < 10)
													sb.append("0" + vial);
												else
													sb.append(vial);
												sb.append("n");
												String timepoint = cstpp.getSign() + "" + cstpp.getTimePoint() + " hr ";
												String actualTp = cstpp.getSign() + cstpp.getTimePoint();
												StringBuffer seqNo = new StringBuffer();
												seqNo.append(subject.getValue());
												seqNo.append(period.getPeriodNo());
												if (cstpp.getTimePointNo() < 10)
													seqNo.append("0" + cstpp.getTimePointNo());
												else
													seqNo.append(cstpp.getTimePointNo());
												seqNo.append(cstpp.getVacutainerNo());
												if (vial < 10)
													seqNo.append("0" + vial);
												else
													seqNo.append(vial);
												sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
														period.getStudy().getProjectNo(), subject.getValue(),
														period.getPeriodNo() + "", timepoint,
														cstpp.getVacutainerNo() + "", treatment, subjectOrder,
														cstpp.getTimePointNo(), seqNo.toString(), actualTp));
												if (claietName.equals("ADV")) {
													String vialNo = seqNo.substring(seqNo.length() - 2);
													if (tpCountNo < 10) {
														p.add(period.getStudy().getProjectNo() + "\n Sub :"
																+ subject.getValue() + " Per :" + period.getPeriodNo()
																+ " ALi :" + vialNo + " \n" + timepoint + " ("
																+ subject.getValue() + period.getPeriodNo() + "0"
																+ tpCountNo + ")"
																		+ "\n"+sb.toString());
													} else {
														p.add(period.getStudy().getProjectNo() + "\n Sub :"
																+ subject.getValue() + " Per :" + period.getPeriodNo()
																+ " ALi :" + vialNo + " \n" + timepoint + " ("
																+ subject.getValue() + period.getPeriodNo() + tpCountNo
																+ ")"
																		+ "\n"+sb.toString());

													}
													p.setFont(regular);
													barcodeWriter = new Code128Writer();
													BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(),
															BarcodeFormat.CODE_128, 100, 35);
													BufferedImage bImage = MatrixToImageWriter
															.toBufferedImage(bitMatrix);
													image = Image.getInstance(bImage, null);
													image.scaleAbsolute(100, 35);
													p2.add(seqNo.toString());
													p2.setFont(regular);
												} else {
													p.add(period.getStudy().getProjectNo() + "  P"
															+ period.getPeriodNo() + "\n" + subject.getValue() + " "
															+ timepoint + "\n" + cstpp.getVacutainerNo() + "   "
															+ seqNo.toString() + "  " + treatment
															+ "\n"+sb.toString());

													barcodeWriter = new Code128Writer();
													BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(),
															BarcodeFormat.CODE_128, 100, 35);
													BufferedImage bImage = MatrixToImageWriter
															.toBufferedImage(bitMatrix);
													image = Image.getInstance(bImage, null);
													image.scaleAbsolute(100, 35);
												}
												cell = new PdfPCell();
												cell.addElement(p);
												cell.addElement(image);
												cell.addElement(p2);

												cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
												cell.setPadding(7f);
												cell.setNoWrap(false);
												cell.setHorizontalAlignment(Element.ALIGN_LEFT);
												cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
												table.addCell(cell);

												if (tabCellCount == 3)
													tabCellCount = 1;
												else
													tabCellCount++;
											}

										}
									}

								}
						}
					}
					if (tabCellCount < 4) {
						for (int j = tabCellCount; j <= 3; j++) {
							cell = new PdfPCell(new Phrase(""));
							cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							cell.setPadding(7f);
							cell.setNoWrap(false);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell);
						}
					}
				}
				document.add(table);
				document.close();
			}
			String path = realPath;
			path = path + "//VialPrnFiles";
			File f1 = new File(path);
			if (!f1.exists())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();
			int tpNo = 1;
			String timePoint = "";
			for (int i = 0; i < sampleTimepoints.size(); i = i + Integer.parseInt(noOfColumns)) {
				path = folderPath + "/vial_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				List<String> barcodeList = new ArrayList<>();
				List<String> subjectAndPeriodList = new ArrayList<>();
				List<String> projectNoList = new ArrayList<>();
				List<String> tpStrList = new ArrayList<>();
				List<String> sequenceList = new ArrayList<>();
				for (int j = 0; j < Integer.parseInt(noOfColumns); j++) {
					if ((i + j) < sampleTimepoints.size()) {

						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						if (!timePoint.equals(cstpp.getActualTp())) {
							timePoint = cstpp.getActualTp();
							tpNo++;
						}
						barcodeList.add(cstpp.getBarcode());
						String tpStr = "";
						if (tpNo < 10)
							tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo() + "0"
									+ tpNo + ")";
						else
							tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo() + tpNo
									+ ")";
						tpStrList.add(tpStr);
//						if (claietName.equals("ADV")) {
							String vialNo = cstpp.getSeqNo().substring(cstpp.getSeqNo().length() - 2);
							subjectAndPeriodList.add("Sub :" + cstpp.getSubjectNo() + " Per :" + cstpp.getPeriodNo() + " " + "ALI :"
									+ vialNo);
							sequenceList.add(cstpp.getSeqNo());
							projectNoList.add(cstpp.getProjectNo());
//						} 
					} else {
						barcodeList.add("");
						tpStrList.add("");
						projectNoList.add("");
						subjectAndPeriodList.add("");
						sequenceList.add("");
					}
				}
				if (studyIdCardBarcodeLable.equals("Yes")) {
					/*barcodeBo.pnrFileCreationVacAndVial(path, Integer.parseInt(noOfColumns), barcodeList, subjectAndPeriodList,
							sequenceList, projectNoList, tpStrList);*/
					barcodeBo.pnrFileCreationVacAndVialForThreeLabels(path, Integer.parseInt(noOfColumns), barcodeList, subjectAndPeriodList,
							sequenceList, projectNoList, tpStrList);
				}
					
			}
			if (studyIdCardBarcodeLable.equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = barcodePrinterip;
				for (String fileName : fileNames) {
					try {
						pbf.printBarcode(fileName);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	@SuppressWarnings("unused")
	@Override
	public String stdVacutainerBarcodePrintTimePointWise(Long periodId, HttpServletRequest request, String noOfColumns,
			String studyIdCardBarcodeLable, String barcodePrinterip, String claietName, Map<String, String> subjectsMap,
			BarcodesDto bcDto, String subjNo, Long timePointId) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String fpath = realPath + "//BarcodeGeneration";
		File file = new File(fpath);
		if (!file.exists())
			file.mkdirs();
		String filepath = fpath + "/Vacutainer.pdf";
		int fontNos = FontFactory.registerDirectory("C:\\WINDOWS\\Fonts");
		Font calibri = FontFactory.getFont("Calibri");
		Font regular = new Font(calibri.getFamily(), 11);
		StudyPeriodMaster spm = null;
		Map<Long, List<SampleTimePoints>> sampTpMap = null;
		StudyMaster sm = null;
		Map<String, TreatmentInfo> subTrMap = null;
		List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
		Map<String, SubjectRandamization> subRzMap = null;
		Code128Writer barcodeWriter = null;
		Image image = null;
		TreeMap<String, String> subMap = new TreeMap<>();
		List<StudyPeriodMaster> spmList = new ArrayList<>();
		try {
			Document document = new Document();
			document.addTitle("Vacutainer Barcodes");
			document.setPageSize(PageSize.A4);
			document.setMargins(40, 40, 20, 20); // A4
			document.setMarginMirroring(false);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
//			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100f);
			PdfPCell cell = null;
			sm = bcDto.getStudy();
			if (periodId != 0) {
				spm = bcDto.getSpmMap().get(periodId);
				spmList.add(spm);
			} else {
				for (Map.Entry<Long, StudyPeriodMaster> spmMap : bcDto.getSpmMap().entrySet()) {
					spmList.add(spmMap.getValue());
				}
			}
			subTrMap = bcDto.getSubTrMap();
			sampTpMap = bcDto.getSampTpMap();
			subRzMap = bcDto.getSubRzMap();
			subMap.putAll(subjectsMap);

			int index = 1;
			String treatment = "";
			int tabCellCount = 1;
			int subjectOrder = 1;
			int tpDisplayNo = 0;
			String dstimePoint = "";
			if (spmList != null) {
				SampleTimePoints smapleTimepoint = null;
				if (timePointId != null && timePointId != 0) {
					smapleTimepoint = studyDao.sampleTimePointsWithId(timePointId);
				}
				for (StudyPeriodMaster period : spmList) {
					tpDisplayNo = 0;
					dstimePoint = "";
					for (Map.Entry<Long, List<SampleTimePoints>> stpMap : sampTpMap.entrySet()) {
						List<SampleTimePoints> timepoints = stpMap.getValue();
						Collections.sort(timepoints);
						boolean tpFlag = true;
						for (int i = 0; i < timepoints.size(); i++) {
							SampleTimePoints cstpp = timepoints.get(i);
							if (smapleTimepoint != null) {
								if ((cstpp.getSign() + cstpp.getTimePoint())
										.equals(smapleTimepoint.getSign() + smapleTimepoint.getTimePoint()))
									tpFlag = true;
								else
									tpFlag = false;
							}
							if (tpFlag) {
								if (!dstimePoint.equals(cstpp.getSign() + cstpp.getTimePoint())) {
									dstimePoint = cstpp.getSign() + cstpp.getTimePoint();
									tpDisplayNo++;
								}
								boolean subFlag = true;
								for (Map.Entry<String, String> subject : subMap.entrySet()) {
									if (subjNo != null && !subjNo.equals("") && !subjNo.equals("0")) {
										String[] strArr = subjNo.split("\\,");
										List<String> subList = new ArrayList<>();
										if (strArr.length > 0) {
											for (String st : strArr) {
												subList.add(st);
											}
										}

										if (subList.contains(subject.getValue())) {
											boolean numFlag = checkNumeric(subject.getValue());
											if (numFlag) {
												subFlag = numFlag;
											} else {
												if (cstpp.getSign().trim().equals("-"))
													subFlag = true;
											}
										} else
											subFlag = false;
									} else
										subFlag = false;

									if (subFlag) {
										try {
											Integer.parseInt(subject.getValue());
										} catch (Exception e) {
											if (!cstpp.getSign().contains("-"))
												subFlag = false;
										}
									}

									if (subFlag) {
										if (stpMap.getKey() != 0) {
											if (cstpp.getTreatmentInfo() != null)
												treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo()
														+ "\n";
										}
										Paragraph p = new Paragraph();
										Paragraph p2 = new Paragraph();
										StringBuffer sb = new StringBuffer();
										sb.append("04");
										sb.append("a");
										sb.append(period.getId());
										sb.append("a");
										sb.append(stpMap.getKey());
										sb.append("a");
										sb.append(subject.getValue());
										sb.append("a");
										sb.append(cstpp.getId());
										sb.append("n");
										String timepoint = cstpp.getSign() + " " + cstpp.getTimePoint() + " hr ";
										String actualTp = cstpp.getSign() + cstpp.getTimePoint();
										StringBuffer seqNo = new StringBuffer();
										seqNo.append(subject.getValue());
										seqNo.append(period.getPeriodNo());
										if (cstpp.getTimePointNo() < 10)
											seqNo.append("0" + cstpp.getTimePointNo());
										else
											seqNo.append(cstpp.getTimePointNo());
										seqNo.append(cstpp.getVacutainerNo());
										sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
												period.getStudy().getProjectNo(), subject.getValue(),
												period.getPeriodNo() + "", timepoint, cstpp.getVacutainerNo() + "",
												treatment, subjectOrder, cstpp.getTimePointNo(), seqNo.toString(),
												actualTp));
										if (claietName.equals("ADV")) {
											if (tpDisplayNo < 10) {
												p.add(period.getStudy().getProjectNo() + "\n Sub :" + subject.getValue()
														+ " Per :" + period.getPeriodNo() + "\n" + timepoint + " ("
														+ subject.getValue() + period.getPeriodNo() + "0" + tpDisplayNo
														+ ")"
																+ "\n"+sb.toString());
											} else {
												p.add(period.getStudy().getProjectNo() + "\n Sub :" + subject.getValue()
														+ " Per :" + period.getPeriodNo() + "\n" + timepoint + " ("
														+ subject.getValue() + period.getPeriodNo() + tpDisplayNo
														+ ")"
																+ "\n"+sb.toString());
											}
											p.setFont(regular);
											barcodeWriter = new Code128Writer();
											BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(),
													BarcodeFormat.CODE_128, 100, 35);
											BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
											image = Image.getInstance(bImage, null);
											image.scaleAbsolute(100, 35);
											p2.add(seqNo.toString());
											p2.setFont(regular);
										} else {
											p.add(period.getStudy().getProjectNo() + "  P" + period.getPeriodNo() + "\n"
													+ subject.getValue() + " " + timepoint + "\n"
													+ cstpp.getVacutainerNo() + "  " + seqNo.toString() + "  "
													+ treatment
													+ "\n"+sb.toString());
											p.setFont(regular);
											barcodeWriter = new Code128Writer();
											BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(),
													BarcodeFormat.CODE_128, 100, 35);
											BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
											image = Image.getInstance(bImage, null);
											image.scaleAbsolute(100, 35);
											p2.add("");
										}
										cell = new PdfPCell();
										cell.addElement(p);
										cell.addElement(image);
										cell.addElement(p2);

										cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
										cell.setPadding(7f);
										cell.setNoWrap(false);
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
										cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
										table.addCell(cell);

										if (tabCellCount == 3)
											tabCellCount = 1;
										else
											tabCellCount++;

										subjectOrder++;
										index++;
									}
								}
							}

						}
						if (tabCellCount < 3) {
							for (int j = tabCellCount; j <= 3; j++) {
								cell = new PdfPCell(new Phrase(""));
								cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								cell.setPadding(7f);
								cell.setNoWrap(false);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								table.addCell(cell);
							}
						}
					}
				}
				document.add(table);
				document.close();
			}
			String path = realPath;
			path = path + "\\VacutainerBarCodes";
			File f1 = new File(path);
			if (!f1.exists())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			String timePoint = "";
			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();
			for (int i = 0; i < sampleTimepoints.size(); i = i + Integer.parseInt(noOfColumns)) {
				path = folderPath + "/Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				
				List<String> barcodeList = new ArrayList<>();
				List<String> subjectAndPeriodList = new ArrayList<>();
				List<String> projectNoList = new ArrayList<>();
				List<String> tpStrList = new ArrayList<>();
				List<String> sequenceList = new ArrayList<>();
				int tpNo = 0;
				for (int j = 0; j < Integer.parseInt(noOfColumns); j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						if (!timePoint.equals(cstpp.getActualTp())) {
							timePoint = cstpp.getActualTp();
							tpNo++;
						}
						barcodeList.add(cstpp.getBarcode());
						if (claietName.equals("ADV")) {
							String tpStr = "";
							if (tpNo < 10)
								tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo()
										+ "0" + tpNo + ")";
							else
								tpStr = cstpp.getTimePoint() + " " + "(" + cstpp.getSubjectNo() + cstpp.getPeriodNo()
										+ tpNo + ")";
							tpStrList.add(tpStr);
							projectNoList.add(cstpp.getProjectNo());
							subjectAndPeriodList.add("Sub :" + cstpp.getSubjectNo() + " Per :" + cstpp.getPeriodNo());
							sequenceList.add(cstpp.getSeqNo());
						} else {
							mInfo2.add(cstpp.getSubjectNo() + " " + cstpp.getTimePoint());
							bInfo.add(cstpp.getVacutinaerNo() + " " + cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo() + " P" + cstpp.getPeriodNo());
						}

					} else {
						barcodeList.add("");
						tpStrList.add("");
						projectNoList.add("");
						subjectAndPeriodList.add("");
						sequenceList.add("");
					}
				}
				if (studyIdCardBarcodeLable.equals("Yes")) {
					/*if (claietName.equals("ADV")) {
						barcodeBo.pnrFileCreationVacAndVial(path, Integer.parseInt(noOfColumns), barcodeList, subjectAndPeriodList,
								sequenceList, projectNoList, tpStrList);
					} else {
						barcodeBo.pnrFileCreationVacAndAVAN(path, Integer.parseInt(noOfColumns), code, tInfo, mInfo,
								mInfo2, bInfo, bInfo2);
					}*/
					barcodeBo.pnrFileCreationVacAndVialForThreeLabels(path, Integer.parseInt(noOfColumns), barcodeList, subjectAndPeriodList,
							sequenceList, projectNoList, tpStrList);
				}
			}
			if (studyIdCardBarcodeLable.equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = barcodePrinterip;
				for (String fileName : fileNames) {
					try {
						pbf.printBarcode(fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	@Override
	public List<SampleTimepointDto> subjectAllTimepoints(Long studyId, Long periodId, String subjectNo) {
//		StudySubjects subject = studyDao.studySubject(studyId, subjectNo);
		SubjectRandamization subjectRandamization = studyDao.subjectRandamization(periodId, subjectNo);
		List<SampleTimePoints> subjectTime = null;
		try {
			Integer.parseInt(subjectNo);
			subjectTime = studyDao.sampleTimePoints(studyId, subjectRandamization, false);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			subjectTime = studyDao.sampleTimePoints(studyId, subjectRandamization, true);
		}
		List<SampleTimepointDto> dto = new ArrayList<>();
		for (SampleTimePoints timePoint : subjectTime) {
			dto.add(new SampleTimepointDto(timePoint.getId(), timePoint.getSign() + "" + timePoint.getTimePoint()));
		}
		return dto;
	}

}
