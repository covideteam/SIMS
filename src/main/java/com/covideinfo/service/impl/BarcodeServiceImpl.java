package com.covideinfo.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.covideinfo.barcode.InstrumentBarcodePNRfileGeneration;
import com.covideinfo.barcode.PrintBarcodeFile;
import com.covideinfo.barcode.VacutainerBarcodePNRfileGeneration;
import com.covideinfo.clinical.dao.ClinicalDao;
import com.covideinfo.dao.BarcodeDao;
import com.covideinfo.dao.GlobalActivityDao;
import com.covideinfo.dao.StudyDao;
import com.covideinfo.dao.impl.DynamicFormParametersDaoImpl;
import com.covideinfo.dto.DoseTimePointsDto;
import com.covideinfo.dto.DosingDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MealInfoDto;
import com.covideinfo.dto.MealsTimePointsDto;
import com.covideinfo.dto.RealTimeCommunicationDto;
import com.covideinfo.dto.SampleCollectionDto;
import com.covideinfo.dto.SampleTimepointDto;
import com.covideinfo.dto.SeparationVacutinerDto;
import com.covideinfo.dto.SepatationDtoDetails;
import com.covideinfo.dto.VialRackCollectionDto;
import com.covideinfo.dto.VitalTimePointsDto;
import com.covideinfo.enums.StaticData;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.Centrifugation;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.Deepfreezer;
import com.covideinfo.model.DeepfreezerShelf;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.Instrument;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySampleCentrifugation;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyTreatmentWiseSubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;
import com.covideinfo.model.Volunteer;
import com.covideinfo.model.dummy.BarcodeBoxData;
import com.covideinfo.model.dummy.BarcodeSubjectContainerData;
import com.covideinfo.service.BarcodeService;
import com.covideinfo.service.StudyActivityService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("/barcodeService")
@PropertySource(value = { "classpath:application.properties" })
public class BarcodeServiceImpl implements BarcodeService {

	@Autowired
	BarcodeDao barcodeDao;
	@Autowired
	StudyDao studyDao;
	@Autowired
	private Environment environment;
	@Autowired
	ClinicalDao clinicalDao;
	@Autowired
	DynamicFormParametersDaoImpl dynamicFormParametersDto;
	@Autowired
	StudyActivityService studyActivityService;

	@Autowired
	GlobalActivityDao globalActivityDao;
	@Autowired
	MessageSource messageSource;

	@Override
	public List<StudyMaster> getStudyMasterList() {
		return barcodeDao.getStudyMasterList();
	}

	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterList() {
		return barcodeDao.getStudyPeriodMasterList();
	}

	@Override
	public StudyPeriodMaster getStudyPeriodMasterWithId(Long period) {
		return barcodeDao.getStudyPeriodMasterWithId(period);
	}

	@Override
	public String generateVacutainersBarcodeAll(Long periodId, String realPath) {
		String file = checktheFile(realPath, "vacutiner.pdf");

		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster period = studyDao.periodById(periodId);
		Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(period);
		List<SampleTimePoints> timepoints = barcodeDao.sampleTimePoints(period.getStudy().getId());

		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
			// int indexpage = 1;
			Map<Integer, String> subjects = subjectList(period.getStudy());
			PdfPTable mainTab = new PdfPTable(4);
			mainTab.setWidthPercentage(100f);
			Image image = null;
			PdfPCell cell = null;
			// int count =1;
			for (int i = 0; i < timepoints.size(); i++) {
				SampleTimePoints cstpp = timepoints.get(i);
				for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
					SubjectRandamization sr = randamixation.get(subject.getKey());
					boolean flag = false;
					if (sr != null) {
						if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
							flag = true;
						}
					} else {
						flag = true;
					}
					if (flag) {

						Paragraph p = new Paragraph();
						PdfPTable tab = new PdfPTable(1);
						tab.setWidthPercentage(100f);
						String treatment = "";
						/*
						 * if (cstpp.getTreatmentInfo() != null) treatment = "Treatement " +
						 * cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
						 */
						StringBuffer sb = new StringBuffer();
						sb.append("04");
						sb.append("a");
						sb.append(period.getId());
						sb.append("a");
						sb.append(cstpp.getTreatmentInfo().getId());
						sb.append("a");
						sb.append(subject.getValue());
						sb.append("a");
						sb.append(cstpp.getId());
						sb.append("n");
						String timepoint = "(" + cstpp.getSign() + " " + cstpp.getTimePoint() + " hr) ";
						StringBuffer seqNo = new StringBuffer();
						seqNo.append(subject.getValue());
						seqNo.append(period.getPeriodNo());
						if (cstpp.getTimePointNo() < 10)
							seqNo.append("0" + cstpp.getTimePointNo());
						else
							seqNo.append(cstpp.getTimePointNo());
						seqNo.append(cstpp.getVacutainerNo());
						sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
								period.getStudy().getProjectNo(), subject.getValue(), period.getPeriodNo() + "",
								timepoint, cstpp.getVacutainerNo() + "", treatment, subject.getKey(),
								cstpp.getTimePointNo(), seqNo.toString(), timepoint,
								Integer.parseInt(subject.getValue())));
						/*
						 * if (indexpage > 5) { document.newPage(); indexpage = 1; }
						 */
						if (claint.equals("ADV")) {
							p.add(period.getStudy().getProjectNo() + "\n" + subject.getValue() + " P"
									+ period.getPeriodNo() + timepoint + "" + cstpp.getVacutainerNo() + "\n"
									+ seqNo.toString() + "\n" + sb.toString());

							barcodeWriter = new QRCodeWriter();
							BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
							BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
							image = Image.getInstance(bImage, null);
						} else {
							p.add(period.getStudy().getProjectNo() + "  P" + period.getPeriodNo() + "\n"
									+ subject.getValue() + " " + timepoint + "\n" + cstpp.getVacutainerNo() + "  "
									+ seqNo.toString() + "  " + cstpp.getTreatmentInfo().getTreatmentName() + "\n"
									+ sb.toString());

							barcodeWriter = new QRCodeWriter();
							BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
							BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
							image = Image.getInstance(bImage, null);
						}
						/*
						 * cell = new PdfPCell(); cell.addElement(p);
						 * cell.setBorder(Rectangle.NO_BORDER);
						 */
						tab.addCell(p);

						Chunk chunk = new Chunk(image, 0, 0, true);
						cell = new PdfPCell();
						cell.addElement(chunk);
						cell.setBorder(Rectangle.NO_BORDER);
						tab.addCell(cell);

						cell = new PdfPCell();
						cell.addElement(tab);
						mainTab.addCell(cell);

						/*
						 * if(count == 4) { PdfPTable space = new PdfPTable(4);
						 * space.setWidthPercentage(100f); cell = new PdfPCell(new Phrase(""));
						 * cell.setBorder(Rectangle.NO_BORDER); cell.setColspan(4);
						 * cell.setFixedHeight(10f); space.addCell(cell); // mainTab.addCell(cell);
						 * document.add(space); count =0; }
						 */

						/*
						 * barcodeWriter = new QRCodeWriter(); BitMatrix bitMatrix =
						 * barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
						 * BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix); Image
						 * image = Image.getInstance(bImage, null); image.setAbsolutePosition(0, 0);
						 * image.scaleToFit(10, 10); PdfPTable table = new PdfPTable(2);
						 * table.setWidths(new int[]{1, 2});
						 * table.addCell(createTextCell(period.getStudy().getProjectNo() + "\n" +
						 * subject.getValue() + " P" + period.getPeriodNo() + "(" + cstpp.getSign() +
						 * " " + cstpp.getTimePoint() + " hr) " + cstpp.getVacutainerNo() + "\n" +
						 * seqNo.toString())); table.addCell(createImageCell(image));
						 * document.add(table);
						 */

						// indexpage++;
						// count++;
					}
					index++;
				}
			}
			document.add(mainTab);
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Vacutainer_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						System.out.println(cstpp.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp.getBarcode());
						tInfo.add(cstpp.getPeriodNo());
						mInfo.add("S" + cstpp.getSubjectNo());
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp.getSubjectNo() + " P" + cstpp.getPeriodNo() + " " + cstpp.getTimePoint()
									+ "" + cstpp.getVacutinaerNo());
							bInfo.add(cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo());
						} else {
							mInfo2.add(cstpp.getSubjectNo() + " " + cstpp.getTimePoint());
							bInfo.add(cstpp.getVacutinaerNo() + " " + cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo() + " P" + cstpp.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
					if (claint.equals("ADV")) {
						/*
						 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
						 * mInfo, mInfo2, bInfo, bInfo2, strList);
						 */
					} else {
						barcodeBo.pnrFileCreationVacAndAVAN(path, noOfLableColumns, code, tInfo, mInfo, mInfo2, bInfo,
								bInfo2);
					}
				}
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	/*
	 * private PdfPCell createTextCell(String string) { PdfPCell cell = new
	 * PdfPCell(); Paragraph p = new Paragraph(string);
	 * p.setAlignment(Element.ALIGN_LEFT); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
	 * cell.setBorder(Rectangle.NO_BORDER); return cell; }
	 */

	/*
	 * private PdfPCell createImageCell(Image image) { Image img =
	 * Image.getInstance(image); img.scaleAbsolute(50, 50); PdfPCell cell = new
	 * PdfPCell(img, true); cell.setBorder(Rectangle.NO_BORDER); return cell; }
	 */

	@Override
	public String generateVacutainersBarcodeAllTimePointWise(Long periodId, Long timePoint, String realPath) {
		String file = checktheFile(realPath, "vacutiner.pdf");
		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster period = studyDao.periodById(periodId);
		SampleTimePoints cstpp = barcodeDao.getSampleTimePointsWithTiompointId(timePoint);
		try {
			Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(period);
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;

			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
			int indexpage = 1;
			Map<Integer, String> subjects = subjectList(period.getStudy());
			for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
				SubjectRandamization sr = randamixation.get(subject.getKey());
				boolean flag = false;
				if (sr != null) {
					if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
						flag = true;
					}
				} else {
					flag = true;
				}
				if (flag) {
					String treatment = "";
					if (cstpp.getTreatmentInfo() != null)
						treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
					StringBuffer sb = new StringBuffer();
					sb.append("04");
					sb.append("a");
					sb.append(period.getId());
					sb.append("a");
					sb.append(cstpp.getTreatmentInfo().getId());
					sb.append("a");
					sb.append(subject.getValue());
					sb.append("a");
					sb.append(cstpp.getId());
					sb.append("n");
					String timepoint = "(" + cstpp.getSign() + " " + cstpp.getTimePoint() + " hr) ";
					StringBuffer seqNo = new StringBuffer();
					seqNo.append(subject.getValue());
					seqNo.append(period.getPeriodNo());
					if (cstpp.getTimePointNo() < 10)
						seqNo.append("0" + cstpp.getTimePointNo());
					else
						seqNo.append(cstpp.getTimePointNo());
					seqNo.append(cstpp.getVacutainerNo());
					sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(), period.getStudy().getProjectNo(),
							subject.getValue(), period.getPeriodNo() + "", timepoint, cstpp.getVacutainerNo() + "",
							treatment, subject.getKey(), cstpp.getTimePointNo(), seqNo.toString(), timepoint,
							Integer.parseInt(subject.getValue())));
					if (indexpage > 5) {
						document.newPage();
						indexpage = 1;
					}
					if (claint.equals("ADV")) {
						document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" + subject.getValue() + " P"
								+ period.getPeriodNo() + "" + timepoint + "" + cstpp.getVacutainerNo()
								/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
								+ "\n" + seqNo.toString() + "\n" + sb.toString()));
						barcodeWriter = new QRCodeWriter();
						BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
						BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
						Image image = Image.getInstance(bImage, null);
						document.add(image);
					} else {
						document.add(new Paragraph(period.getStudy().getProjectNo() + "  P" + period.getPeriodNo()
								+ "\n" + subject.getValue() + " " + timepoint + "\n"
								/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
								+ cstpp.getVacutainerNo() + "   " + seqNo.toString() + "  "
								+ cstpp.getTreatmentInfo().getTreatmentName() + "\n" + sb.toString()));
						barcodeWriter = new QRCodeWriter();
						BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
						BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
						Image image = Image.getInstance(bImage, null);
						document.add(image);
					}
					indexpage++;
				}
			}
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Vacutainer_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp1 = sampleTimepoints.get(i + j);
						System.out.println(cstpp1.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp1.getBarcode());
						tInfo.add(cstpp1.getPeriodNo());
						mInfo.add("S" + cstpp1.getSubjectNo());
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp1.getSubjectNo() + " P" + cstpp1.getPeriodNo() + " " + cstpp1.getTimePoint()
									+ " " + cstpp1.getVacutinaerNo());
							bInfo.add(cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo());
						} else {
							mInfo2.add(cstpp1.getSubjectNo() + " " + cstpp1.getTimePoint());
							bInfo.add(cstpp1.getVacutinaerNo() + " " + cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo() + " P" + cstpp1.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
				 * mInfo, mInfo2, bInfo, bInfo2, strList);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	@Override
	public String generateVacutainersBarcodeAllSubjectWise(Long periodId, String subjectNo, String realPath) {
		String file = checktheFile(realPath, "vacutiner.pdf");
		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster period = studyDao.periodById(periodId);
		List<SampleTimePoints> timepoints = barcodeDao.sampleTimePoints(period.getStudy().getId());

		String[] selectsubject = subjectNo.split(",");
		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(period);
			int index = 1;
			int indexpage = 1;
//			Map<Integer, String> subjects = subjectList(period.getStudy());
			for (SampleTimePoints cstpp : timepoints) {
				for (String subject : selectsubject) {
					String data = subject.replaceFirst("^0*", "");

					List<String> stand = new ArrayList<>();
					stand.add("S1");
					stand.add("S2");
					stand.add("S3");
					stand.add("S4");
					stand.add("S5");
					stand.add("S6");
					stand.add("S7");
					stand.add("S8");
					stand.add("S9");
					stand.add("S10");
					stand.add("S11");
					stand.add("S12");
					SubjectRandamization sr = null;
					if (!stand.contains(data)) {
						sr = randamixation.get(Integer.parseInt(data));

						boolean flag = false;
						if (sr != null) {
							if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
								flag = true;
							}
						} else {
							flag = true;
						}
						if (flag) {
							String treatment = "";
							if (cstpp.getTreatmentInfo() != null)
								treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
							StringBuffer sb = new StringBuffer();
							sb.append("04");
							sb.append("a");
							sb.append(period.getId());
							sb.append("a");
							sb.append(cstpp.getTreatmentInfo().getId());
							sb.append("a");
							sb.append(data);
							sb.append("a");
							sb.append(cstpp.getId());
							sb.append("n");
							String timepoint = "(" + cstpp.getSign() + " " + cstpp.getTimePoint() + " hr) ";
							StringBuffer seqNo = new StringBuffer();
							seqNo.append(data);
							seqNo.append(period.getPeriodNo());
							if (cstpp.getTimePointNo() < 10)
								seqNo.append("0" + cstpp.getTimePointNo());
							else
								seqNo.append(cstpp.getTimePointNo());
							seqNo.append(cstpp.getVacutainerNo());
							sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
									period.getStudy().getProjectNo(), subject, period.getPeriodNo() + "", timepoint,
									cstpp.getVacutainerNo() + "", treatment, Integer.parseInt(data),
									cstpp.getTimePointNo(), seqNo.toString(), timepoint, Integer.parseInt(subject)));
							if (indexpage > 5) {
								document.newPage();
								indexpage = 1;
							}
							if (claint.equals("ADV")) {
								document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" + subject + " P"
										+ period.getPeriodNo() + "(" + cstpp.getSign() + " " + cstpp.getTimePoint()
										+ " hr) " + cstpp.getVacutainerNo() + "\n" + seqNo.toString() + "\n"
										+ sb.toString()));
								/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								Image image = Image.getInstance(bImage, null);
								document.add(image);
							} else {
								document.add(new Paragraph(period.getStudy().getProjectNo() + " "
										+ period.getPeriodName() + "\n" + subject + " " + timepoint + "\n"
										+ cstpp.getVacutainerNo() + "   " + seqNo.toString() + "  "
										+ cstpp.getTreatmentInfo().getTreatmentName() + "\n" + sb.toString()));
								/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								Image image = Image.getInstance(bImage, null);
								document.add(image);
							}

							indexpage++;
							index++;
						}
					}
				}
			}
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Vacutainer_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp1 = sampleTimepoints.get(i + j);
						System.out.println(cstpp1.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp1.getBarcode());
						tInfo.add(cstpp1.getPeriodNo());
						mInfo.add("S" + cstpp1.getSubjectNo());
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp1.getSubjectNo() + " P" + cstpp1.getPeriodNo() + " " + cstpp1.getTimePoint()
									+ " " + cstpp1.getVacutinaerNo());
							bInfo.add(cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo());
						} else {
							mInfo2.add(cstpp1.getSubjectNo() + " " + cstpp1.getTimePoint());
							bInfo.add(cstpp1.getVacutinaerNo() + "   " + cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo() + " P" + cstpp1.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
				 * mInfo, mInfo2, bInfo, bInfo2,strList);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	@Override
	public String generateVacutainersBarcodeAllSubjectWiseForTimePoint(Long periodId, String subjectNo,
			Long timePointId, String realPath) {
		String file = checktheFile(realPath, "vacutiner.pdf");
		String claint = environment.getRequiredProperty("claietName");
		System.out.println(file);
		StudyPeriodMaster period = studyDao.periodById(periodId);
		SampleTimePoints cstpp = barcodeDao.getSampleTimePointsWithTiompointId(timePointId);
		String[] subject = subjectNo.split(",");
		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;

			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
//			Map<Integer, String> subjects = subjectList(period.getStudy());

			String treatment = "";
			if (cstpp.getTreatmentInfo() != null)
				treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("04");
			sb.append("a");
			sb.append(period.getId());
			sb.append("a");
			sb.append(cstpp.getTreatmentInfo().getId());
			sb.append("a");
			sb.append(subject[1]);
			sb.append("a");
			sb.append(cstpp.getId());
			sb.append("n");
			String timepoint = "(" + cstpp.getSign() + " " + cstpp.getTimePoint() + " hr) ";
			StringBuffer seqNo = new StringBuffer();
			seqNo.append(subject[1]);
			seqNo.append(period.getPeriodNo());
			if (cstpp.getTimePointNo() < 10)
				seqNo.append("0" + cstpp.getTimePointNo());
			else
				seqNo.append(cstpp.getTimePointNo());
			seqNo.append(cstpp.getVacutainerNo());
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			for (int vial = 1; vial <= noOfLableColumns; vial++) {
				sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(), period.getStudy().getProjectNo(),
						subject[1], period.getPeriodNo() + "", timepoint, cstpp.getVacutainerNo() + "", treatment,
						Integer.parseInt(subject[0]), cstpp.getTimePointNo(), seqNo.toString(), timepoint,
						Integer.parseInt(subject[1])));
			}

			/*
			 * document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" +
			 * subject[1] + " P" + period.getPeriodNo() + "(" + cstpp.getSign() + " " +
			 * cstpp.getTimePoint() + " hr) " + cstpp.getVacutainerNo() + "\n" +
			 * seqNo.toString())); + seqNo.toString() + "\n" + sb.toString()));
			 * barcodeWriter = new QRCodeWriter(); BitMatrix bitMatrix =
			 * barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
			 * BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix); Image
			 * image = Image.getInstance(bImage, null); document.add(image);
			 */

			if (claint.equals("ADV")) {
				document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" + subject[1] + " P"
						+ period.getPeriodNo() + "" + timepoint + "" + +cstpp.getVacutainerNo() + "\n"
						+ seqNo.toString() + "\n" + sb.toString()));
				/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
				barcodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
				BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
				Image image = Image.getInstance(bImage, null);
				document.add(image);
			} else {
				document.add(new Paragraph(period.getStudy().getProjectNo() + " " + period.getPeriodName() + "\n"
						+ subject[1] + " " + timepoint + "\n" + cstpp.getVacutainerNo() + "   " + seqNo.toString()
						+ "  " + cstpp.getTreatmentInfo().getTreatmentName() + "\n" + sb.toString()));
				/* + "\n" + seqNo.toString() + "\n" + sb.toString())); */
				barcodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
				BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
				Image image = Image.getInstance(bImage, null);
				document.add(image);
			}
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Vacutainer_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();

//			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp1 = sampleTimepoints.get(i + j);
						System.out.println(cstpp1.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp1.getBarcode());
						tInfo.add(cstpp1.getPeriodNo());
						mInfo.add("S" + cstpp1.getSubjectNo());
						/*
						 * mInfo2.add(cstpp1.getSubjectNo() + " P" + cstpp1.getPeriodNo() + "(" +
						 * cstpp1.getTimePoint() + ") " + cstpp1.getVacutinaerNo());
						 * bInfo.add(cstpp1.getSeqNo()); bInfo2.add(cstpp1.getProjectNo());
						 */
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp1.getSubjectNo() + " P" + cstpp1.getPeriodNo() + " " + cstpp1.getTimePoint()
									+ " " + cstpp1.getVacutinaerNo());
							bInfo.add(cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo());
						} else {
							mInfo2.add(cstpp1.getSubjectNo() + " " + cstpp1.getTimePoint());
							bInfo.add(cstpp1.getVacutinaerNo() + "   " + cstpp1.getSeqNo());
							bInfo2.add(cstpp1.getProjectNo() + " P" + cstpp1.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
				 * mInfo, mInfo2, bInfo, bInfo2, strList);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	@Override
	public String generateVialBarcodePeriodWise(Long periodId, String realPath) {

		String file = checktheFile(realPath, "vial.pdf");
		System.out.println(file);
		StudyPeriodMaster period = studyDao.periodById(periodId);
		List<SampleTimePoints> timepoints = barcodeDao.sampleTimePoints(period.getStudy().getId());
		Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(period);
		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
			// int indexpage = 1;
			String claint = environment.getRequiredProperty("claietName");
			PdfPTable mainTab = new PdfPTable(4);
			mainTab.setWidthPercentage(100f);
			Image image = null;
			PdfPCell cell = null;
			Map<Integer, String> subjects = subjectList(period.getStudy());
			for (SampleTimePoints cstpp : timepoints) {
				for (int vial = 1; vial <= cstpp.getNoOfVials(); vial++) {
					for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
						SubjectRandamization sr = randamixation.get(subject.getKey());
						boolean flag = false;
						if (sr != null) {
							if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
								flag = true;
							}
						} else {
							flag = true;
						}
						if (flag) {
							Paragraph p = new Paragraph();
							PdfPTable tab = new PdfPTable(1);
							tab.setWidthPercentage(100f);
							String treatment = "";
							if (cstpp.getTreatmentInfo() != null)
								treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
							StringBuffer sb = new StringBuffer();
							sb.append("05");
							sb.append("a");
							sb.append(period.getId());
							sb.append("a");
							sb.append(cstpp.getTreatmentInfo().getId());
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
							String timepoint = "(" + cstpp.getSign() + "" + cstpp.getTimePoint() + " hr) ";
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
									period.getStudy().getProjectNo(), subject.getValue(), period.getPeriodNo() + "",
									timepoint, cstpp.getVacutainerNo() + "", treatment, subject.getKey(),
									cstpp.getTimePointNo(), seqNo.toString(), timepoint));
							/*
							 * if (indexpage > 5) { document.newPage(); indexpage = 1; }
							 */
							/*
							 * document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" +
							 * subject.getValue() + " P" + period.getPeriodNo() + "(" + "-" +
							 * cstpp.getTimePoint() + " hr) " + cstpp.getVacutainerNo() + "\n" +
							 * seqNo.toString()));
							 * 
							 * + cstpp.getVacutainerNo() + "\n" + seqNo.toString() + "\n" + sb.toString()));
							 * 
							 * barcodeWriter = new QRCodeWriter(); BitMatrix bitMatrix =
							 * barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
							 * BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix); Image
							 * image = Image.getInstance(bImage, null); document.add(image);
							 */
							// indexpage++;
							if (claint.equals("ADV")) {
								p.add(period.getStudy().getProjectNo() + "\n" + subject.getValue() + " P"
										+ period.getPeriodNo() + " " + timepoint + "" + cstpp.getVacutainerNo() + "\n"
										+ seqNo.toString() + "\n" + sb.toString());

								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								image = Image.getInstance(bImage, null);
							} else {
								p.add(period.getStudy().getProjectNo() + "  P" + period.getPeriodNo() + "\n"
										+ subject.getValue() + " " + timepoint + "\n" + cstpp.getVacutainerNo() + "   "
										+ seqNo.toString() + "  " + cstpp.getTreatmentInfo().getTreatmentName() + "\n"
										+ sb.toString());

								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								image = Image.getInstance(bImage, null);
							}
							/*
							 * cell = new PdfPCell(); cell.addElement(p);
							 * cell.setBorder(Rectangle.NO_BORDER);
							 */
							tab.addCell(p);

							Chunk chunk = new Chunk(image, 0, 0, true);
							cell = new PdfPCell();
							cell.addElement(chunk);
							cell.setBorder(Rectangle.NO_BORDER);
							tab.addCell(cell);

							cell = new PdfPCell();
							cell.addElement(tab);
							mainTab.addCell(cell);
						}
					}
				}

				index++;

			}
			document.add(mainTab);
			document.close();

			String path = realPath;
			path = path + "BARCODE\\vial_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "vial_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						System.out.println(cstpp.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp.getBarcode());
						tInfo.add(cstpp.getPeriodNo());
						mInfo.add("S" + cstpp.getSubjectNo());
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp.getSubjectNo() + " P" + cstpp.getPeriodNo() + "" + cstpp.getTimePoint()
									+ "" + cstpp.getVacutinaerNo());
							bInfo.add(cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo());
						} else {
							mInfo2.add(cstpp.getSubjectNo() + " " + cstpp.getTimePoint());
							bInfo.add(cstpp.getVacutinaerNo() + "   " + cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo() + "  P" + cstpp.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
				 * mInfo, mInfo2, bInfo, bInfo2, strList);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	@Override
	public String generateSachetBarcode(Long periodId, String realPath) {
		String file = checktheFile(realPath, "sachet.pdf");
		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster period = studyDao.periodById(periodId);
		List<DoseTimePoints> timepoints = barcodeDao.doseTimePoints(period.getStudy().getId());
		Map<Long, List<DoseTimePoints>> treatmentWiseTimePoints = new HashMap<>();
		for (DoseTimePoints timepoint : timepoints) {
			List<DoseTimePoints> each = treatmentWiseTimePoints.get(timepoint.getTreatmentInfo().getId());
			if (each == null)
				each = new ArrayList<>();
			each.add(timepoint);
			treatmentWiseTimePoints.put(timepoint.getTreatmentInfo().getId(), each);
		}

		List<SubjectRandamization> randamization = barcodeDao.subjectRandamizationOfPeriod(periodId);
		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int indexpage = 1;
			for (SubjectRandamization subject : randamization) {
				List<DoseTimePoints> doseTimePoints = treatmentWiseTimePoints.get(subject.getTreatmentInfo().getId());
				int index = 1;
				for (DoseTimePoints cstpp : doseTimePoints) {
					StringBuffer sb = new StringBuffer();
					sb.append("03");
					sb.append("a");
					sb.append(period.getId());
					sb.append("a");
					sb.append(subject.getTreatmentInfo().getId());
					sb.append("a");
					sb.append(subject.getSubjectNo());
					sb.append("a");
					sb.append(cstpp.getId());
					sb.append("n");
					String timepoint = "(" + cstpp.getTimePoint() + " hr) ";
					StringBuffer seqNo = new StringBuffer();
					seqNo.append(subject.getSubjectNo());
					seqNo.append(period.getPeriodNo());
					if (cstpp.getTimePointNo() < 10)
						seqNo.append("0" + cstpp.getTimePointNo());
					else
						seqNo.append(cstpp.getTimePointNo());
					seqNo.append(cstpp.getTreatmentInfo().getRandamizationCode());
					sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(), period.getStudy().getProjectNo(),
							subject.getSubjectNo(), period.getPeriodNo() + "", timepoint, "",
							cstpp.getTreatmentInfo().getRandamizationCode(), subject.getSeqNo(), cstpp.getTimePointNo(),
							seqNo.toString(), timepoint));
					if (indexpage > 5) {
						document.newPage();
						indexpage = 1;
					}
					if (claint.equals("ADV")) {
						document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" + subject.getSubjectNo()
								+ " P" + period.getPeriodNo() + timepoint + "" + "\n" + seqNo.toString() + "\n"
								+ sb.toString()));
						/* + seqNo.toString() + "\n" + sb.toString())); */
						barcodeWriter = new QRCodeWriter();
						BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
						BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
						Image image = Image.getInstance(bImage, null);
						document.add(image);
					} else {
						document.add(new Paragraph(period.getStudy().getProjectNo() + "\n" + "P" + period.getPeriodNo()
								+ " " + subject.getSubjectNo() + " " + timepoint + "" + "\n" + seqNo.toString() + "\n"
								+ sb.toString()));
						/* + seqNo.toString() + "\n" + sb.toString())); */
						barcodeWriter = new QRCodeWriter();
						BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
						BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
						Image image = Image.getInstance(bImage, null);
						document.add(image);
					}
					indexpage++;
				}

			}

			document.close();

			String path = realPath;
			path = path + "BARCODE\\Sachet_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Sachet_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						System.out.println(cstpp.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp.getBarcode());
						tInfo.add(cstpp.getPeriodNo());
						mInfo.add("S" + cstpp.getSubjectNo());
						if (claint.equals("ADV")) {
							mInfo2.add(cstpp.getSubjectNo() + " P" + cstpp.getPeriodNo() + " " + cstpp.getTimePoint()
									+ "");
						} else {
							mInfo2.add("P" + cstpp.getPeriodNo() + " " + cstpp.getSubjectNo() + " "
									+ cstpp.getTimePoint() + "");
						}
						bInfo.add(cstpp.getSeqNo());
						bInfo2.add(cstpp.getProjectNo());

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
				 * mInfo, mInfo2, bInfo, bInfo2, strList);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	private Map<Integer, String> subjectList(StudyMaster sm) {
		String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
		Map<Integer, String> subjectNosMap = new HashMap<>();
		for (int subjectNo = 1; subjectNo <= sm.getNoOfSubjects(); subjectNo++) {
			if (subjectNo < 10)
				subjectNosMap.put(subjectNo, "0" + subjectNo);
			else
				subjectNosMap.put(subjectNo, "" + subjectNo);
		}
		for (int subjectNo = 1; subjectNo <= sm.getNoOfStandBySubjects(); subjectNo++) {
			subjectNosMap.put(subjectNo + sm.getNoOfSubjects(), standByPrefix + subjectNo);
		}
		return subjectNosMap;
	}

	@Override
	public Object clinicalSampleTimePointsExceptDose(StudyMaster sm) {
		return barcodeDao.clinicalSampleTimePointsExceptDose(sm);
	}

	@Override
	public StudyMaster findByStudyId(Long activeStudyId) {
		return barcodeDao.findByStudyId(activeStudyId);
	}

	@Override
	public List<Volunteer> avilableBedNos(StudyMaster sm) {
		return barcodeDao.avilableBedNos(sm);
	}

	@Override
	public List<StudyPeriodMaster> allStudyPeriodsWithSubEle(StudyMaster sm) {
		return barcodeDao.allStudyPeriodsWithSubEle(sm);
	}

	private String checktheFile(String pdfpath, String fileName) {
		FileOutputStream fos = null;

		try {
			pdfpath = "D://" + fileName;
			// read only file with same name already exists
			File file = new File(pdfpath);
			file.createNewFile();
			/*
			 * Make sure that if the file exists, it is writable first
			 */
			if (file.exists() && !file.canWrite()) {

				System.out.println("File exists and it is read only, making it writable");
				file.setWritable(true);
			}
			if (!file.canRead())
				file.setReadable(true);
			fos = new FileOutputStream(file);

			System.out.println("File can be overwritten now!");
			return pdfpath;
		} catch (IOException fnfe) {
			fnfe.printStackTrace();

		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String generateSubjectBarcodeAll(StudyMaster sm, String realPath, SubjectRandamization st,
			List<StudyPeriodMaster> psmLista) {
		String pdfpath = checktheFile(realPath, "sub_BarCode.pdf");
		System.out.println(pdfpath);

		try {

			String standByPrefix = environment.getRequiredProperty("standBySubjectPrefix");
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfpath));
			document.open();
			QRCodeWriter barcodeWriter = null;
			SortedMap<Integer, String> subjectNosMap = new TreeMap<>();
			for (int subjectNo = 1; subjectNo <= sm.getNoOfSubjects(); subjectNo++) {
				if (subjectNo < 10)
					subjectNosMap.put(subjectNo, "0" + subjectNo);
				else
					subjectNosMap.put(subjectNo, "" + subjectNo);
			}
			for (int subjectNo = 1; subjectNo <= sm.getNoOfStandBySubjects(); subjectNo++) {
				subjectNosMap.put(subjectNo + sm.getNoOfSubjects(), standByPrefix + subjectNo);
			}
			int indexpage = 1;
			SortedMap<Integer, String> barcodesMap = new TreeMap<>();
			// for (StudyPeriodMaster ppdata : psmLista) {
			for (Map.Entry<Integer, String> subjects : subjectNosMap.entrySet()) {
				// SubjectRandamization subz =
				// barcodeDao.getSubjectRandamizationWithSubjectAndPeriod("" +
				// subjects.getValue(), ppdata);
				StringBuilder sb = new StringBuilder();
				sb.append("02");
				sb.append("a");
				sb.append(subjects.getValue());
				sb.append("a");
				sb.append(sm.getId());
				sb.append("n");
				// String dispalyValue = sm.getProjectNo() + " " + subjects.getValue();
				/*
				 * String dispalyValue = sm.getProjectNo() + " " + subjects.getValue() + "\n" +
				 * sb.toString();
				 */

				String dispalyValue = "";
				/*
				 * if (subz != null) { dispalyValue = "Project No .: " + sm.getProjectNo() +
				 * "\n" + "Period: " + ppdata.getPeriodName() + "   Subject No .: " +
				 * subjects.getValue() + "\n" + "Randomization code : " +
				 * subz.getRadamizationCode(); } else { dispalyValue = "Project No .: " +
				 * sm.getProjectNo() + "\n" + "Period: " + ppdata.getPeriodName() +
				 * "   Subject No .: " + subjects.getValue() + "\n" + "Randomization code : "; }
				 */
				dispalyValue = "Project No .: " + sm.getProjectNo() + "\n" + "Subject No .: " + subjects.getValue()
						+ "\n" + sb.toString();

				if (indexpage > 5) {
					document.newPage();
					indexpage = 1;
				}
				document.add(new Paragraph(dispalyValue));
				barcodeWriter = new QRCodeWriter();
				BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
				BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
				Image image = Image.getInstance(bImage, null);
				document.add(image);
				barcodesMap.put(subjects.getKey(), sb.toString());
				indexpage++;
			}

			// }

			document.close();

			String path = realPath;
			path = path + "BARCODE\\Subject\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			SubjectBarcodePNRfileGeneration barcodeBo = new SubjectBarcodePNRfileGeneration();

			for (int i = 0; i < barcodesMap.size(); i = i + noOfLableColumns) {
				path = folderPath + "SubjectBarcodes" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> pInfo = new ArrayList<>();
//				List<String> mInfo2 = new ArrayList<>();
//				List<String> bInfo2 = new ArrayList<>();
				List<String> randcode = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < barcodesMap.size()) {
						String barcode = barcodesMap.get(i + j);
						code.add(barcode);
						tInfo.add(sm.getProjectNo());
						pInfo.add(sm.getProjectNo());
						bInfo.add("");
						mInfo.add(subjectNosMap.get(i + j));
						randcode.add("");
//						randcode.add(st.getRadamizationCode());
					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
						mInfo.add("");
					}
				}
				/*
				 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
				 * barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, tInfo,
				 * mInfo, randcode);
				 */
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return pdfpath;
	}

	private String addSiffixZeros(String value, int i) {
		while (value.length() < i) {
			value = value + "0";
		}
		return value;
	}

	private String addPrefixZeros(String value, int i) {
		while (value.length() < i) {
			value = "0" + value;
		}
		return value;
	}

	@Override
	public String generateSubjectBarcode(StudyMaster sm, String subjectNo, String realPath, StudyPeriodMaster psm,
			SubjectRandamization st) {
		String file = checktheFile(realPath, "sub_BarCode.pdf");
		System.out.println(file);
		try {

			String[] subject = subjectNo.split(",");
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//		    document.add(new Paragraph(sm.getStudyNo() + " P-"+period + " S-"+subject));

//			Barcode128 code128 = new Barcode128();
			QRCodeWriter barcodeWriter = null;
//			code128.setGenerateChecksum(true);

			StringBuilder sb = new StringBuilder();
			sb.append("02");
			sb.append("a");
			sb.append(subject[1]);
			sb.append("a");
			sb.append(sm.getId());
			sb.append("n");
			String dispalyValue = "";
			/*
			 * if (st != null) { dispalyValue = "Project No .: " + sm.getProjectNo() + "\n"
			 * + "Period: " + psm.getPeriodName() + "   Subject No .: " + subject[1] + "\n"
			 * + "Randomization code : " + st.getRadamizationCode(); } else { dispalyValue =
			 * "Project No .: " + sm.getProjectNo() + "\n" + "Period: " +
			 * psm.getPeriodName() + "   Subject No .: " + subject[1] + "\n" +
			 * "Randomization code : "; }
			 */
			dispalyValue = "Project No .: " + sm.getProjectNo() + "\n" + "Subject No .: " + subject[1] + "\n"
					+ sb.toString();
			document.add(new Paragraph(dispalyValue));
			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Subject\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			SubjectBarcodePNRfileGeneration barcodeBo = new SubjectBarcodePNRfileGeneration();
			path = folderPath + "SubjectBarcodes" + subject[1] + ".prn";
			fileNames.add(path);
			System.out.println(path);
			List<String> code = new ArrayList<>();
			List<String> tInfo = new ArrayList<>();
			List<String> mInfo = new ArrayList<>();
			// List<String> pInfo = new ArrayList<>();
//			List<String> mInfo2 = new ArrayList<>();
//			List<String> bInfo2 = new ArrayList<>();
			List<String> randcode = new ArrayList<>();
			for (int j = 0; j < noOfLableColumns; j++) {
				code.add(sb.toString());
				tInfo.add(sm.getProjectNo());
				mInfo.add(subject[1]);
				// pInfo.add("" + psm.getPeriodNo());
				randcode.add("");
//				randcode.add(st.getRadamizationCode());
			}
			/*
			 * if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
			 * barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, tInfo,
			 * mInfo, randcode);
			 */
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	/*
	 * private String pdfBarocodeLable(StudyMaster sm, int vialNo, String barcode,
	 * SubjectSampleCollectionTimePoints cstpp, String treatment) { // TODO
	 * Auto-generated method stub StringBuilder sb = new StringBuilder();
	 * sb.append(sm.getProjectNo()).append("\n").append(cstpp.getSubjectNo()).
	 * append(" P")
	 * .append(cstpp.getPeriod().getPeriodNo()).append("(").append(cstpp.getSign())
	 * .append(cstpp.getTimePoint()).append(" hr) ").append(cstpp.getVacutainer()).
	 * append("\n")
	 * .append(treatment).append(cstpp.getSubjectOrder()).append(cstpp.getPeriod().
	 * getPeriodNo())
	 * .append(cstpp.getTimePointNo()).append(" ").append(vialNo).append("\n").
	 * append(barcode); System.out.println(sb.toString()); return sb.toString(); }
	 */

	/*
	 * private String generateVialBarcode(String subjectSampleCollectionTimePointId,
	 * int vialNo) { // TODO Auto-generated method stub StringBuffer sb = new
	 * StringBuffer(); sb.append("05").append(
	 * subjectSampleCollectionTimePointId.substring(2,
	 * subjectSampleCollectionTimePointId.length() - 1)) .append(vialNo); return
	 * sb.toString(); }
	 */

	@Override
	public List<SubjectSampleCollectionTimePoints> subjectSampleTimePoints(StudyMaster sm, long period, String volId) {
		return barcodeDao.subjectSampleTimePoints(sm, period, volId);
	}

	@Override
	public String generateSubjectContainerBarCodePrint(BarcodeSubjectContainerData bsData, String username,
			HttpServletRequest request) {

		String file = request.getSession().getServletContext().getRealPath("/") + "SubjectContainer_Barcodes.pdf";
		System.out.println(file);

		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("08");
			sb.append(addPrefixZeros(bsData.getProjectPk() + "", 6));
			sb.append(bsData.getPeriodNo());
			sb.append(addPrefixZeros(bsData.getSubjectNo() + "", 3));
			sb.append(addPrefixZeros(bsData.getAliquotPk() + "", 4));
			sb.append(addPrefixZeros(bsData.getNoofSampleNo() + "", 4));
			// return addSiffixZeros(sb.toString(), 23);

			document.add(new Paragraph(
					bsData.getProjectLabel() + "\n" + bsData.getPeriodLabel() + "\n" + bsData.getNoofSampleLabel() + ""
							+ bsData.getSubjectNo() + "\n" + addSiffixZeros(sb.toString(), 23)));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(addSiffixZeros(sb.toString(), 23), BarcodeFormat.QR_CODE, 70,
					70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			/*
			 * String path = request.getSession().getServletContext().getRealPath("/"); path
			 * = path + "BARCODE\\Vacutainer_BarCode\\"; File f1 = new File(path); if
			 * (!f1.exists() || f1.isDirectory()) f1.mkdirs(); String folderPath = path;
			 * List<String> fileNames = new ArrayList<>(); int noOfLableColumns =
			 * Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			 * VacutainerBarcodePNRfileGeneration barcodeBo = new
			 * VacutainerBarcodePNRfileGeneration();
			 * 
			 * for (int i = 0; i < vacList.size(); i = i + noOfLableColumns) { path =
			 * folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn"; fileNames.add(path);
			 * System.out.println(path); List<String> code = new ArrayList<>(); List<String>
			 * tInfo = new ArrayList<>(); List<String> mInfo = new ArrayList<>();
			 * List<String> bInfo = new ArrayList<>(); List<String> mInfo2 = new
			 * ArrayList<>(); List<String> bInfo2 = new ArrayList<>(); for (int j = 0; j <
			 * noOfLableColumns; j++) { if ((i + j) < vacList.size()) {
			 * SubjectSampleCollectionTimePoints cstpp = vacList.get(i + j);
			 * System.out.println(cstpp.getTimePoint()); // String bedNo =
			 * bedNos.get(cstpp.getSubjectNo());
			 * 
			 * code.add(cstpp.getSubjectSampleCollectionTimePointId());
			 * tInfo.add(sm.getProjectNo()); mInfo.add("S" + cstpp.getSubjectNo());
			 * mInfo2.add("P" + cstpp.getPeriod().getPeriodNo() + "(" + cstpp.getTimePoint()
			 * + " hr)"); bInfo.add(cstpp.getSubjectOrder() +
			 * cstpp.getPeriod().getPeriodNo() + "" + cstpp.getTimePointNo());
			 * bInfo2.add("");
			 * 
			 * } else { code.add(""); tInfo.add(""); bInfo.add(""); } } if
			 * (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
			 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
			 * mInfo, mInfo2, bInfo, bInfo2); } if
			 * (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
			 * PrintBarcodeFile pbf = new PrintBarcodeFile(); PrintBarcodeFile.serviceName =
			 * environment.getRequiredProperty("barcodePrinterip"); for (String fileName :
			 * fileNames) { try { pbf.printBarcode(fileName); } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	@Override
	public String generateBoxBarCodePrint(BarcodeBoxData bsData, String username, HttpServletRequest request) {
		String file = request.getSession().getServletContext().getRealPath("/") + "Box_Barcodes.pdf";
		System.out.println(file);

		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("08");
			sb.append(addPrefixZeros(bsData.getProjectPk() + "", 6));
			sb.append(bsData.getPeriodNo());
			sb.append(addPrefixZeros(bsData.getAliquotNo() + "", 3));
			sb.append(addPrefixZeros(bsData.getBoxNumber() + "", 2));
			sb.append(addPrefixZeros(bsData.getNoofSampleNo() + "", 3));
			sb.append(addPrefixZeros(bsData.getSubjectFrom() + "", 2));
			sb.append(addPrefixZeros(bsData.getSubjectTo() + "", 2));
			// return addSiffixZeros(sb.toString(), 23);

			document.add(new Paragraph(
					bsData.getProjectLabel() + "\n" + bsData.getPeriodLabel() + "\n" + bsData.getNoofSampleLabel() + ""
							+ bsData.getBoxNumber() + "\n" + addSiffixZeros(sb.toString(), 23)));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(addSiffixZeros(sb.toString(), 23), BarcodeFormat.QR_CODE, 70,
					70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			/*
			 * String path = request.getSession().getServletContext().getRealPath("/"); path
			 * = path + "BARCODE\\Vacutainer_BarCode\\"; File f1 = new File(path); if
			 * (!f1.exists() || f1.isDirectory()) f1.mkdirs(); String folderPath = path;
			 * List<String> fileNames = new ArrayList<>(); int noOfLableColumns =
			 * Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			 * VacutainerBarcodePNRfileGeneration barcodeBo = new
			 * VacutainerBarcodePNRfileGeneration();
			 * 
			 * for (int i = 0; i < vacList.size(); i = i + noOfLableColumns) { path =
			 * folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn"; fileNames.add(path);
			 * System.out.println(path); List<String> code = new ArrayList<>(); List<String>
			 * tInfo = new ArrayList<>(); List<String> mInfo = new ArrayList<>();
			 * List<String> bInfo = new ArrayList<>(); List<String> mInfo2 = new
			 * ArrayList<>(); List<String> bInfo2 = new ArrayList<>(); for (int j = 0; j <
			 * noOfLableColumns; j++) { if ((i + j) < vacList.size()) {
			 * SubjectSampleCollectionTimePoints cstpp = vacList.get(i + j);
			 * System.out.println(cstpp.getTimePoint()); // String bedNo =
			 * bedNos.get(cstpp.getSubjectNo());
			 * 
			 * code.add(cstpp.getSubjectSampleCollectionTimePointId());
			 * tInfo.add(sm.getProjectNo()); mInfo.add("S" + cstpp.getSubjectNo());
			 * mInfo2.add("P" + cstpp.getPeriod().getPeriodNo() + "(" + cstpp.getTimePoint()
			 * + " hr)"); bInfo.add(cstpp.getSubjectOrder() +
			 * cstpp.getPeriod().getPeriodNo() + "" + cstpp.getTimePointNo());
			 * bInfo2.add("");
			 * 
			 * } else { code.add(""); tInfo.add(""); bInfo.add(""); } } if
			 * (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))
			 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
			 * mInfo, mInfo2, bInfo, bInfo2); } if
			 * (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
			 * PrintBarcodeFile pbf = new PrintBarcodeFile(); PrintBarcodeFile.serviceName =
			 * environment.getRequiredProperty("barcodePrinterip"); for (String fileName :
			 * fileNames) { try { pbf.printBarcode(fileName); } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	@Override
	public String generateCentrifugationBarcode(Long barcodeId, HttpServletRequest request) {

		Centrifugation centrifugation = studyDao.centrifugationWithId(barcodeId);
		String file = request.getSession().getServletContext().getRealPath("/") + "Centrifugation_BarCode.pdf";
		System.out.println(file);
		String barcode = centrifugation.getCentrifugationBarcode();
		System.out.println(barcode);
		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append(centrifugation.getName()).append("\n").append(centrifugation.getCode()).append("\n")
					.append(barcode);
			System.out.println(sb.toString());
			document.add(new Paragraph(sb.toString()));
			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(barcode, BarcodeFormat.QR_CODE, 70, 70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			String path = request.getSession().getServletContext().getRealPath("/");
			path = path + "BARCODE\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//			SubjectBarcodePNRfileGeneration barcodeBo = new SubjectBarcodePNRfileGeneration();
			path = folderPath + "Centrifugation.prn";
			fileNames.add(path);
			System.out.println(path);
			List<String> code = new ArrayList<>();
			List<String> tInfo = new ArrayList<>();
			List<String> mInfo = new ArrayList<>();
			List<String> bInfo = new ArrayList<>();
			for (int j = 0; j < noOfLableColumns; j++) {
				code.add(barcode);
				tInfo.add(centrifugation.getName());
				bInfo.add("");
				mInfo.add(centrifugation.getCode());
			}
			// barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, tInfo,
			// mInfo);

			PrintBarcodeFile pbf = new PrintBarcodeFile();
			PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
			for (String fileName : fileNames) {
				try {
					pbf.printBarcode(fileName);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}

	@Override
	public SortedMap<Integer, String> subjectBarcodes(List<StudySubjects> subjects, StudyMaster study) {

//		SortedMap<Integer, String> subjectNosMap = new TreeMap<>();
//		for (int subjectNo = 1; subjectNo <= study.getNoOfSubjects(); subjectNo++) {
//			if (subjectNo < 10)
//				subjectNosMap.put(subjectNo, "0" + subjectNo);
//			else
//				subjectNosMap.put(subjectNo, "" + subjectNo);
//		}
//		for (int subjectNo = 1; subjectNo <= study.getNoOfStandBySubjects(); subjectNo++) {
//			subjectNosMap.put(subjectNo + study.getNoOfSubjects(), standByPrefix + subjectNo);
//		}

		SortedMap<Integer, String> barcodesMap = new TreeMap<>();
		int count = 1;
		for (StudySubjects subject : subjects) {
			StringBuilder sb = new StringBuilder();
			sb.append("02");
			sb.append("a");
			sb.append(subject.getSubjectNo());
			sb.append("a");
			sb.append(study.getId());
			sb.append("n");
			barcodesMap.put(count++, sb.toString());
		}
//		for (Map.Entry<Integer, String> subjects : subjectNosMap.entrySet()) {
//			StringBuilder sb = new StringBuilder();
//			sb.append("02");
//			sb.append("a");
//			sb.append(subjects.getValue());
//			sb.append("a");
//			sb.append(study.getId());
//			sb.append("n");
//			barcodesMap.put(subjects.getKey(), sb.toString());
//		}
		return barcodesMap;
	}

	@Override
	public SortedMap<Integer, StudyPeriodMaster> studyPeriodList(Long studyId) {
		SortedMap<Integer, StudyPeriodMaster> periods = new TreeMap<>();
		List<StudyPeriodMaster> studyPeriods = studyDao.studyPeriodMasterWithStudyId(studyId);
		studyPeriods.forEach((period) -> {
			periods.put(period.getPeriodNo(), period);
		});
		return periods;
	}

	@Override
	public SortedMap<Long, StudyPeriodMaster> studyPeriodListSampleCollection(Long studyId) {
		SortedMap<Long, StudyPeriodMaster> periods = new TreeMap<>();
		List<StudyPeriodMaster> studyPeriods = studyDao.studyPeriodMasterWithStudyId(studyId);
		studyPeriods.forEach((period) -> {
			periods.put(period.getId(), period);
		});
		return periods;
	}

	@Override
	public SortedMap<Long, SampleTimePoints> allSampleTimepoints(Long studyId, SampleCollectionDto dto) {
		SortedMap<Long, SampleTimePoints> sampleTimePoints = new TreeMap<>();
		List<SampleTimePoints> timepoints = studyDao.allSampleTimePoints(studyId);
		Set<Integer> timePointNos = new HashSet<>();
		timepoints.forEach((timepoint) -> {
			sampleTimePoints.put(timepoint.getId(), timepoint);
			String[] timeSplit = timepoint.getTimePoint().split("\\.");
			int hr = Integer.parseInt(timeSplit[0]) * 60;
			hr += ((Integer.parseInt(timeSplit[1]) * 6) / 100);
			timepoint.setTimeDesimal(hr);
			if (timepoint.getSign() == null) {
				timepoint.setSign("");
			}
			timePointNos.add(timepoint.getTimePointNo());
		});
		dto.setTimePointNos(timePointNos);
		return sampleTimePoints;
	}

	@Override
	public SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> collectedData(Long studyId,
			SampleCollectionDto dto) { // periodId, subjectno, timePoitnData
		// TODO Auto-generated method stub
		SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> data = new TreeMap<>();
		SortedMap<Long, StudyPeriodMaster> periods = dto.getPeriods();
		for (Map.Entry<Long, StudyPeriodMaster> sp : periods.entrySet()) {
			SortedMap<String, List<SubjectSampleCollectionTimePointsData>> subjectWise = new TreeMap<>();
			SortedMap<Integer, String> subjectBarocodes = dto.getSubjectBarocodes();
			subjectBarocodes.forEach((k, v) -> {
				subjectWise.put(v.substring(0, v.length() - 1).split("a")[1],
						clinicalDao.periodSubjectSampleCollectionTimePointsData(studyId, sp.getValue().getId(),
								v.substring(0, v.length() - 1).split("a")[1]));
			});
			data.put(sp.getValue().getId(), subjectWise);
		}
		return data;
	}

	@Override
	public SortedMap<String, StudyPeriodMaster> subjectPeriods(Long studyId,
			SortedMap<Integer, String> subjectBarocodes) {
		return clinicalDao.subjectPeriods(studyId, subjectBarocodes);
	}

	@Override
	public Map<String, String> replacedSubjects(List<StudySubjects> subjects, StudyMaster study) {
		// TODO Auto-generated method stub
		Map<String, String> replacedBySubjects = new HashMap<>();
		subjects.stream().forEach((subject) -> {
			if (subject.getStdSubjectId() != null)
				replacedBySubjects.put(subject.getSubjectNo(), subject.getStdSubjectId().getSubjectNo());
		});
		return replacedBySubjects;
	}

	@Override
	public List<String> droppendSubjects(List<StudySubjects> subjects, StudyMaster study) {
		// TODO Auto-generated method stub
		List<String> droppedSubjects = new ArrayList<>();
		subjects.stream().forEach((subject) -> {
			if (subject.getSubjectStatus() != null && subject.getSubjectStatus().equals("DropOut"))
				droppedSubjects.add(subject.getSubjectNo());
		});
		return droppedSubjects;
	}

	@Override
	public Map<Long, SubjectSampleCollectionTimePointsData> timePointCollectedData(SampleCollectionDto dto) {
		Map<Long, SubjectSampleCollectionTimePointsData> timePointCollectedData = new HashMap<>();
		Map<String, SubjectSampleCollectionTimePointsData> subjectPeriodTimePointCollectionDetials = new HashMap<>();
		SortedMap<Long, SortedMap<String, List<SubjectSampleCollectionTimePointsData>>> collectedData = dto
				.getCollectedData();
		collectedData.forEach((k, v) -> {
			v.forEach((key, value) -> {
				List<SubjectSampleCollectionTimePointsData> list = value;
				list.forEach((timePoint) -> {
					timePointCollectedData.put(timePoint.getId(), timePoint);
					subjectPeriodTimePointCollectionDetials
							.put(timePoint.getStudyPeriodMaster().getId() + "," + timePoint.getSubject().getSubjectNo()
									+ "," + timePoint.getSampleTimePoint().getTimePointNo(), timePoint);
				});
			});
		});
		dto.setSubjectPeriodTimePointCollectionDetials(subjectPeriodTimePointCollectionDetials);
		return timePointCollectedData;
	}

	@Override
	public SortedMap<Long, DoseTimePoints> allDoseTimepoints(Long studyId) {
		SortedMap<Long, DoseTimePoints> doseTimePoints = new TreeMap<>();
		List<DoseTimePoints> timepoints = studyDao.allDoseTimePoints(studyId);
		timepoints.forEach((timepoint) -> {
			doseTimePoints.put(timepoint.getId(), timepoint);
		});
		return doseTimePoints;
	}

	@Override
	public MealsTimePointsDto allMealTimepoints(Long studyId) {
		// This Dto will Use to Transfer required study data to meals js page
		MealsTimePointsDto dto = new MealsTimePointsDto();
		// This Dto is used for getting data from database and customize getting data
		// and put into MealsTimePointsDto
		MealInfoDto mealsInfoDto = null;
		StudyMaster study = null;
		Map<Long, MealsTimePoints> mealTimePoints = new HashMap<>();
		// unused variable
//		Map<String, List<Long>> mealTimePointIds = new HashMap<>();
		Map<Long, List<MealsTimePoints>> preDoseMap = new HashMap<>(); // treatmentId, mealsList
		Map<Long, List<MealsTimePoints>> postDoseMap = new HashMap<>();// treatmentId, mealsList
//		Map<Long, Map<Long, Map<String, SubjectMealsTimePointsData>>> smtpMap = new HashMap<>(); // periodId, mealId, subjectNo, subdoseDonePojo
		Map<Long, Map<Long, Map<String, RealTimeCommunicationDto>>> smtpMap = new HashMap<>(); // periodId, mealId,
																								// subjectNo,
																								// subdoseDonePojo
		List<MealsTimePoints> timepoints = null;
		List<SubjectMealsTimePointsData> submtpDtataList = null;
		List<TreatmentInfo> treatmentList = null;
		Map<Long, TreatmentInfo> treatmentMap = new HashMap<>();
		List<SubjectRandamization> srmzList = null;
		List<StudySubjects> subjectsList = null;
		Map<String, StudySubjects> subMap = new HashMap<>();// subjectNo, StubjectPojo
		Map<String, Map<Long, List<Long>>> twsubMap = new HashMap<>(); // SubjectNo, PeriodId, List of TreatmentPojo
		List<StudySubjectPeriods> ssubPeriodList = null;
		Map<String, StudyPeriodMaster> subjectPeriods = new HashMap<>(); // SubjectNo, periodMaster
		List<SubjectMealsTimePointsData> subColDatalList = null;
//		Map<String, SubjectMealsTimePointsData> collectedDataMap = new HashMap<>();
		Map<String, RealTimeCommunicationDto> collectedDataMap = new HashMap<>();
		Map<String, Map<Long, String>> subjectDoseMap = null;
		Map<String, Map<Long, Map<Long, SubjectDoseTimePoints>>> dosedMap = null;
		DosingDto dsDto = null;
		Map<String, Long> devionCodeMap = new HashMap<>();
		Long minTreatmentId = null;
		try {
			mealsInfoDto = studyDao.getMealsDetails(studyId);
			if (mealsInfoDto != null) {
				timepoints = mealsInfoDto.getTimepoints();
				submtpDtataList = mealsInfoDto.getSubmDataList();
				treatmentList = mealsInfoDto.getTreatmentList();
				study = mealsInfoDto.getStudy();
				subjectsList = mealsInfoDto.getSubjectsList();
				srmzList = mealsInfoDto.getSrmzList();
				ssubPeriodList = mealsInfoDto.getSsubPeriodList();
				subColDatalList = mealsInfoDto.getSubColDatalList();
				subjectDoseMap = mealsInfoDto.getSubjectDoseMap();
				dosedMap = mealsInfoDto.getDosedMap();
				dsDto = mealsInfoDto.getDsDto();
				dto.setTreatmentSpecificMeals(mealsInfoDto.getTreatmentSpecificMeals());
			}
			dto.setTinfList(treatmentList);
			dto.setSubjectDoseMap(subjectDoseMap);
			if (ssubPeriodList != null && ssubPeriodList.size() > 0) {
				for (StudySubjectPeriods ssp : ssubPeriodList) {
					/*
					 * if(ssp.getSubject().getStdSubjectId() != null)
					 * subjectPeriods.put(ssp.getSubject().getStdSubjectId().getSubjectNo(),
					 * ssp.getPeriodId()); else subjectPeriods.put(ssp.getSubject().getSubjectNo(),
					 * ssp.getPeriodId());
					 */
					subjectPeriods.put(ssp.getSubject().getSubjectNo(), ssp.getPeriodId());
				}
			}
			dto.setSubjectPerods(subjectPeriods);

			Map<Long, List<Long>> temptrMap = null;
			List<Long> trTempList = null;
			if (srmzList != null && srmzList.size() > 0) {
				for (SubjectRandamization sr : srmzList) {
					if (twsubMap.containsKey(sr.getSubjectNo())) {
						temptrMap = twsubMap.get(sr.getSubjectNo());
						if (temptrMap.containsKey(sr.getPeriod().getId())) {
							trTempList = temptrMap.get(sr.getPeriod().getId());
							trTempList.add(sr.getTreatmentInfo().getId());
							temptrMap.put(sr.getPeriod().getId(), trTempList);
							twsubMap.put(sr.getSubjectNo(), temptrMap);
						} else {
							trTempList = new ArrayList<>();
							trTempList.add(sr.getTreatmentInfo().getId());
							temptrMap.put(sr.getPeriod().getId(), trTempList);
							twsubMap.put(sr.getSubjectNo(), temptrMap);
						}
					} else {
						trTempList = new ArrayList<>();
						temptrMap = new HashMap<>();
						trTempList.add(sr.getTreatmentInfo().getId());
						temptrMap.put(sr.getPeriod().getId(), trTempList);
						twsubMap.put(sr.getSubjectNo(), temptrMap);
					}
				}
			}
			dto.setTwsubMap(twsubMap);

			if (subjectsList != null && subjectsList.size() > 0) {
				for (StudySubjects ss : subjectsList) {
					/*
					 * if(ss.getStdSubjectId() != null) {
					 * subMap.put(ss.getStdSubjectId().getSubjectNo(), ss.getStdSubjectId()); }else
					 * { subMap.put(ss.getSubjectNo(), ss); }
					 */
					subMap.put(ss.getSubjectNo(), ss);
				}
			}
			dto.setSubMap(subMap);

			/*
			 * Map<Long, Map<String, SubjectMealsTimePointsData>> smtpTempMap = null;
			 * Map<String, SubjectMealsTimePointsData> smtTemp1 = null;
			 */
			Map<Long, Map<String, RealTimeCommunicationDto>> smtpTempMap = null;
			Map<String, RealTimeCommunicationDto> smtTemp1 = null;
			if (submtpDtataList != null && submtpDtataList.size() > 0) {
				for (SubjectMealsTimePointsData smtpd : submtpDtataList) {
					RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
					rcDto.setPeriodId(smtpd.getStudyPeriodMaster().getId());
					rcDto.setSubjectNo(smtpd.getSubject().getSubjectNo());
					rcDto.setSubjectVitalId(smtpd.getId());
					rcDto.setTimePointId(smtpd.getMealsTimePoint().getId());
					rcDto.setTreatmentId(smtpd.getMealsTimePoint().getTreatmentInfo().getId());
					if (smtpd.getStartTime() != null)
						rcDto.setMealsSatartTime(smtpd.getStartTime());
					if (smtpd.getEndTime() != null)
						rcDto.setMealsEndTime(smtpd.getEndTime());

					if (smtpMap.containsKey(smtpd.getStudyPeriodMaster().getId())) {
						smtpTempMap = smtpMap.get(smtpd.getStudyPeriodMaster().getId());
						if (smtpTempMap.containsKey(smtpd.getMealsTimePoint().getId())) {
							smtTemp1 = smtpTempMap.get(smtpd.getMealsTimePoint().getId());
							if (!smtTemp1.containsKey(smtpd.getSubject().getSubjectNo())) {
								smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
								smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
								smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
							}
						} else {
							smtTemp1 = smtpTempMap.get(smtpd.getMealsTimePoint().getId());
							if (smtTemp1 == null)
								smtTemp1 = new HashMap<>();
							smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
							smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
							smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
						}
					} else {
						smtTemp1 = new HashMap<>();
						smtpTempMap = new HashMap<>();
						smtTemp1.put(smtpd.getSubject().getSubjectNo(), rcDto);
						smtpTempMap.put(smtpd.getMealsTimePoint().getId(), smtTemp1);
						smtpMap.put(smtpd.getStudyPeriodMaster().getId(), smtpTempMap);
					}
				}
			}
			if (treatmentList != null && treatmentList.size() > 0) {
				for (TreatmentInfo tin : treatmentList) {
					treatmentMap.put(tin.getId(), tin);
				}
			}
			dto.setTreatmentMap(treatmentMap);
			if (timepoints != null && timepoints.size() > 0) {
				for (MealsTimePoints mtp : timepoints) {
					mealTimePoints.put(mtp.getId(), mtp);
					List<MealsTimePoints> mtTempList = null;
					if (mtp.getSign() == null || mtp.getSign().trim().equals("")) {
						if (postDoseMap.containsKey(mtp.getTreatmentInfo().getId())) {
							mtTempList = postDoseMap.get(mtp.getTreatmentInfo().getId());
							mtTempList.add(mtp);
							Collections.sort(mtTempList);
							postDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						} else {
							mtTempList = new ArrayList<>();
							mtTempList.add(mtp);
							postDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}
					} else if (mtp.getSign().trim().equals("-")) {
						if (preDoseMap.containsKey(mtp.getTreatmentInfo().getId())) {
							mtTempList = preDoseMap.get(mtp.getTreatmentInfo().getId());
							mtTempList.add(mtp);
							Collections.sort(mtTempList);
							preDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						} else {
							mtTempList = new ArrayList<>();
							mtTempList.add(mtp);
							preDoseMap.put(mtp.getTreatmentInfo().getId(), mtTempList);
						}
					}
				}
			}
			if (subColDatalList != null && subColDatalList.size() > 0) {
				for (SubjectMealsTimePointsData smtd : subColDatalList) {
					int hours = 0;
					int minutes = 0;
					Calendar cal = Calendar.getInstance();
					cal.setTime(smtd.getStartTime());
					hours = cal.get(Calendar.HOUR);
					minutes = cal.get(Calendar.MINUTE);
					smtd.setStartTimeOnly(hours + ":" + minutes);

					RealTimeCommunicationDto rcDto = new RealTimeCommunicationDto();
					rcDto.setPeriodId(smtd.getStudyPeriodMaster().getId());
					rcDto.setSubjectNo(smtd.getSubject().getSubjectNo());
					rcDto.setSubjectVitalId(smtd.getId());
					rcDto.setTimePointId(smtd.getMealsTimePoint().getId());
					rcDto.setTreatmentId(smtd.getMealsTimePoint().getTreatmentInfo().getId());
					if (smtd.getStartTime() != null)
						rcDto.setMealsSatartTime(smtd.getStartTime());
					collectedDataMap.put(smtd.getSubject().getSubjectNo() + "," + smtd.getStudyPeriodMaster().getId()
							+ "," + smtd.getMealsTimePoint().getTreatmentInfo().getId() + ","
							+ smtd.getMealsTimePoint().getId(), rcDto);

				}
			}
			// Deviation messages
			if (dsDto != null) {
				List<DeviationMessage> devMsgList = dsDto.getDevMsgList();
				if (devMsgList != null && devMsgList.size() > 0) {
					for (DeviationMessage dvm : devMsgList) {
						devionCodeMap.put(dvm.getDeveloperCode(), dvm.getId());
					}
					dsDto.setDevionCodeMap(devionCodeMap);
				}
			}
			if (treatmentMap.size() > 0)
				minTreatmentId = Collections.min(treatmentMap.keySet());

			dto.setCollectedDataMap(collectedDataMap);
			dto.setMealsTimpointsMap(mealTimePoints);
			dto.setPreDoseMap(preDoseMap);
			dto.setPostDoseMap(postDoseMap);
			dto.setSmtpMap(smtpMap);
			dto.setStudy(study);
			dto.setDosedMap(dosedMap);
			dto.setDsDto(dsDto);
			dto.setMinTreatmentId(minTreatmentId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public Map<String, SubjectMealsTimePointsData> subjectPeriodTimePointCollectedData(
			SortedMap<Long, SortedMap<String, List<SubjectMealsTimePointsData>>> collectedData) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("timeFormat"));
		List<SubjectMealsTimePointsData> dbData = new ArrayList<>();
		collectedData.forEach((k, v) -> {
			SortedMap<String, List<SubjectMealsTimePointsData>> subjectWise = v;
			subjectWise.forEach((key, value) -> {
				value.forEach((data) -> {
					if (data.getStartTime() != null)
						data.setStartTimeOnly(sdf.format(data.getStartTime()));
					if (data.getEndTime() != null)
						data.setEndTimeOnly(sdf.format(data.getEndTime()));
					dbData.add(data);
				});
			});
		});

		Map<String, SubjectMealsTimePointsData> map = new HashMap<>();
		dbData.stream().forEach((subjectTimePoint) -> {
			map.put(subjectTimePoint.getSubject().getSubjectNo() + "," + subjectTimePoint.getStudyPeriodMaster().getId()
					+ "," + subjectTimePoint.getMealsTimePoint().getId(), subjectTimePoint);
		});
		return map;
	}

	@Override
	public SortedMap<Long, TreatmentInfo> allTreatment(Long studyId) {
		// TODO Auto-generated method stub
		return clinicalDao.allTreatment(studyId);
	}

	@Override
	public List<StudySubjects> allStudySubjects(Long studyId) {
		// TODO Auto-generated method stub
		return clinicalDao.allstudySubject(studyId);
	}

	@Override
	public Map<String, String> subjectStatusMap(List<StudySubjects> subjects, StudyMaster study) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<>();
		subjects.stream().forEach((subject) -> {
			try {
//				int sub = Integer.parseInt(subject.getSubjectNo());
				map.put(subject.getSubjectNo(), "not");
			} catch (NumberFormatException e) {
				// TODO: handle exception
				map.put(subject.getSubjectNo(), "standby");
			}
		});
		return map;
	}

	@Override
	public Map<Long, String> discontinuedSubjects(DoseTimePointsDto dto, StudyMaster study) {
		StudyPeriodMaster period = clinicalDao.periodOne(study.getId());
		List<SubjectRandamization> randamizations = clinicalDao.subjectRandamizationByPeriod(period.getId());
		Map<String, TreatmentInfo> subjectTreatment = new HashMap<>();
		randamizations.forEach((timePoint) -> {
			if (timePoint.getSubjectNo().length() == 1)
				subjectTreatment.put("0" + timePoint.getSubjectNo(), timePoint.getTreatmentInfo());
			else
				subjectTreatment.put("" + timePoint.getSubjectNo(), timePoint.getTreatmentInfo());
		});

		Map<Long, List<String>> discontinuedSubjects0 = new HashMap<>();
		List<StudySubjects> subjects = dto.getSubjects();
		subjects.stream().forEach((subject) -> {
			try {
//				int sub = Integer.parseInt(subject.getSubjectNo());
				if (subject.getSubjectStatus() != null && subject.getSubjectStatus().equals("DropOut")
						&& subject.getStdSubjectId() == null && subject.getSubjectReplace().equalsIgnoreCase("yes")) {
					TreatmentInfo treatment = subjectTreatment.get(subject.getSubjectNo());
					List<String> subs = discontinuedSubjects0.get(treatment.getId());
					if (subs == null)
						subs = new ArrayList<>();
					subs.add(subject.getSubjectNo());
					discontinuedSubjects0.put(treatment.getId(), subs);
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
		});

		Map<Long, String> discontinuedSubjects = new HashMap<>();
		discontinuedSubjects0.forEach((key, value) -> {
			List<String> subs = value;
			StringBuilder sb = new StringBuilder();
			boolean flag = false;
			subs.forEach((s) -> {
				if (flag)
					sb.append(",").append(s);
				else
					sb.append(s);
			});
			discontinuedSubjects.put(key, sb.toString());
		});
		return discontinuedSubjects;
	}

	@Override
	public GlobalparameterFromDto dynamicParameters(Long studyId, Long languageId, Locale currentLocale,
			String activityCode) {
		GlobalActivity globalActivity = globalActivityDao.getGlobalActivityByCode(activityCode);
//		com.covideinfo.model.StudyActivities studyActivity = studyDao.studyActivity(studyId, activityName);
		if (globalActivity != null)
			return studyActivityService.globalparameterFromDtoerFromDto(globalActivity.getId(), languageId, studyId,
					currentLocale);
		else
			return null;
	}

	@Override
	public List<VitalTimePointsDto> allVitalTimePoints(Long studyId) {
		// TODO Auto-generated method stub
		List<VitalTimePointsDto> list = new ArrayList<>();
		List<VitalTimePoints> timePoints = clinicalDao.allVitalTimePoints(studyId);
		for (VitalTimePoints timepoint : timePoints) {
			VitalTimePointsDto dto = new VitalTimePointsDto();
			dto.setId(timepoint.getId());
			if (timepoint.getVitalPosition() != null)
				dto.setVitalPosition(timepoint.getVitalPosition().getFieldName());
			if (timepoint.getTreatmentInfo() != null)
				dto.setTreatmentInfoId(timepoint.getTreatmentInfo().getId());
			dto.setTimePoint(timepoint.getTimePoint());
			if (timepoint.getTimePointType() != null)
				dto.setTimePointType(timepoint.getTimePointType().getCode());
			dto.setSign(timepoint.getSign());
			dto.setWindowPeriodSign(timepoint.getWindowPeriodSign());
			dto.setTreatment(timepoint.getTreatmentInfo().getId());
			dto.setWindowPeriod(timepoint.getWindowPeriod());
			if (timepoint.getWindowPeriodType() != null)
				dto.setWindowPeriodType(timepoint.getWindowPeriodType().getFieldValue());
			dto.setTimePointNo(timepoint.getTimePointNo());
			dto.setOrthostatic(timepoint.isOrthostatic());
			if (timepoint.getOrthostaticPosition() != null)
				dto.setOrthostaticPosition(timepoint.getOrthostaticPosition().getFieldValue());
			List<Long> perameterIds = new ArrayList<>();
			String[] perameterArray = timepoint.getParameterIds().split("\\,");
			for (String s : perameterArray) {
				perameterIds.add(Long.parseLong(s));
			}
			dto.setParameterIds(perameterIds);
			list.add(dto);
		}
		return list;
	}

	/*
	 * @Override public List<SubjectVitalTimePointsData>
	 * subjectVitalTimePointsData(VitalTimePointsCollectionDto dto) {
	 * List<SubjectVitalTimePointsData> list =
	 * clinicalDao.subjectVitalTimePointsData(dto.getSubjects()); Map<String,
	 * SubjectVitalTimePointsData> collectedData = new HashMap<>();
	 * list.forEach((data) -> { String key = data.getSubject().getSubjectNo() + ","
	 * + data.getPeriod().getId() + "," + data.getTimepoint().getId();
	 * collectedData.put(key, data); }); dto.setCollectedData(collectedData); return
	 * list; }
	 */

	@Override
	public Map<Long, VitalTimePointsDto> vitalTimePointsMap(List<VitalTimePointsDto> vitalTimPoints) {
		Map<Long, VitalTimePointsDto> map = new HashMap<>();
		for (VitalTimePointsDto v : vitalTimPoints) {
			map.put(v.getId(), v);
		}
		return map;
	}

	@Override
	public SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(Long studyId) {
		// TODO Auto-generated method stub
		return clinicalDao.subjectDoseTimes(studyId);
	}

	@Override
	public SortedMap<String, SubjectDoseTimePoints> subjectAllDoseTimes(long studyId) {
		// TODO Auto-generated method stub
		return clinicalDao.subjectAllDoseTimes(studyId);
	}

	@Override
	public SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes(
			SortedMap<String, SubjectDoseTimePoints> subjectDoseTimes) {
		SortedMap<String, SubjectDoseTimePoints> timePoints = new TreeMap<>();
		for (Map.Entry<String, SubjectDoseTimePoints> map : subjectDoseTimes.entrySet())
			timePoints.put(map.getValue().getPeriod().getId() + ","
					+ map.getValue().getStudySubjects().getStdSubjectId().getSubjectNo() + ","
					+ map.getValue().getDoseTimePoints().getId(), map.getValue());
		return timePoints;
	}

	@Override
	public Map<String, Map<String, SubjectMealsTimePointsData>> mealDetails(List<StudySubjects> subjects,
			Long studyId) {
		// TODO Auto-generated method stub
		List<Long> pereDoseMeals = clinicalDao.preDoseMeals(studyId);
		Map<String, Map<String, SubjectMealsTimePointsData>> map = new HashMap<>();
		List<StudyPeriodMaster> periods = studyDao.studyPeriodMasterWithStudyId(studyId);
		for (StudySubjects s : subjects) {
			for (StudyPeriodMaster p : periods) {
				List<SubjectMealsTimePointsData> preDoseMeals = clinicalDao.subjectPreDoseMeals(p, pereDoseMeals, s);
				Map<String, SubjectMealsTimePointsData> mp = new HashMap<>();
				for (SubjectMealsTimePointsData smtd : preDoseMeals) {
					if (smtd.getMealsTimePoint().getMealsType().getCode().equals(StaticData.DINNER.toString()))
						mp.put(StaticData.DINNER.toString(), smtd);
					else
						mp.put("BREAKFAST", smtd);
				}
				map.put(p.getId() + "," + s.getSubjectNo(), mp);
			}
		}
		return map;
	}

//	@Override
//	public List<Instrument> getInstrumentList(String centrifuge) {
//		return barcodeDao.getInstrumentListWithType(centrifuge);
//	}

	@Override
	public List<Instrument> getInstrumentList() {
		return barcodeDao.getInstrumentList();
	}

	@Override
	public String generateinstrumentBarcode(Long id, String realPath, HttpServletRequest request) {
		String file = request.getSession().getServletContext().getRealPath("/") + "Instrument_Barcodes.pdf";
		System.out.println(file);
		Instrument instruPojo = barcodeDao.getInstrumentWithId(id);
		try {

			Document document = new Document(new Rectangle(PageSize.A4));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("06").append("a").append(instruPojo.getId()).append("n");

			document.add(new Paragraph("  " + instruPojo.getInstrumentType().getInstrumentType() + "\n" + "  "
					+ instruPojo.getInstrumentNo() + "\n" + sb.toString()));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 60, 65);
			// BitMatrix bitMatrix = barcodeWriter.encode(addPrefixZeros(sb.toString(), 5),
			// BarcodeFormat.QR_CODE, 70,70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			String path = realPath;
			path = path + "BARCODE\\Instrument\\";
			File f1 = new File(path);
			if (!f1.exists())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			InstrumentBarcodePNRfileGeneration barcodeBo = new InstrumentBarcodePNRfileGeneration();
			path = folderPath + "/Instrument" + instruPojo.getInstrumentType().getInstrumentType() + ".prn";
			fileNames.add(path);
			System.out.println(path);
			List<String> finfo = new ArrayList<>();
			List<String> code = new ArrayList<>();
			List<String> istno = new ArrayList<>();
			for (int j = 0; j < noOfLableColumns; j++) {
				finfo.add(instruPojo.getInstrumentType().getInstrumentType());
				code.add(sb.toString());
				istno.add(instruPojo.getInstrumentNo());
			}

			if (environment.getRequiredProperty("instrumentdBarcodeLable").equals("Yes"))
				barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, finfo, istno);
			if (environment.getRequiredProperty("instrumentdBarcodeLable").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;

	}

	@Override
	public StudyTreatmentWiseSubjects getStudyTreatmentWiseSubjectsWithPeriodAndStudy(StudyMaster sm,
			StudyPeriodMaster psm, String subjectNo) {
		return barcodeDao.getStudyTreatmentWiseSubjectsWithPeriodAndStudy(sm, psm, subjectNo);
	}

	@Override
	public TreatmentInfo getTreatmentInfoWithId(TreatmentInfo treatment) {
		return barcodeDao.getStudyTreatmentWiseSubjectsWithPeriodAndStudy(treatment);
	}

	@Override
	public SubjectRandamization getSubjectRandamizationWithPeriodAndSubject(StudyPeriodMaster psm, String string) {
		return barcodeDao.getSubjectRandamizationWithPeriodAndSubject(psm, string);
	}

	@Override
	public String stdVacutainerBarcodeForAllPrint(Long periodId, String realPath) {
		String file = checktheFile(realPath, "vacutiner.pdf");
		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster perioda = studyDao.periodById(periodId);
		List<StudyPeriodMaster> periodList = studyDao.getStudyPeriodMasterWithStudy(perioda.getStudy());
		List<SampleTimePoints> timepoints = barcodeDao.sampleTimePoints(perioda.getStudy().getId());

		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
//			int indexpage = 1;
			Map<Integer, String> subjects = subjectList(perioda.getStudy());
			PdfPTable mainTab = new PdfPTable(4);
			mainTab.setWidthPercentage(100f);
			Image image = null;
			PdfPCell cell = null;
			for (StudyPeriodMaster pp : periodList) {
				Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(pp);
				for (SampleTimePoints cstpp : timepoints) {
					for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
						SubjectRandamization sr = randamixation.get(subject.getKey());
						boolean flag = false;
						if (sr != null) {
							if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
								flag = true;
							}
						} else {
							flag = true;
						}
						if (flag) {
							Paragraph p = new Paragraph();
							PdfPTable tab = new PdfPTable(1);
							tab.setWidthPercentage(100f);
							String treatment = "";
							if (cstpp.getTreatmentInfo() != null)
								treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
							StringBuffer sb = new StringBuffer();
							sb.append("04");
							sb.append("a");
							sb.append(pp.getId());
							sb.append("a");
							sb.append(cstpp.getTreatmentInfo().getId());
							sb.append("a");
							sb.append(subject.getValue());
							sb.append("a");
							sb.append(cstpp.getId());
							sb.append("n");
							String timepoint = "(" + cstpp.getSign() + "" + cstpp.getTimePoint() + " hr) ";
							StringBuffer seqNo = new StringBuffer();
							seqNo.append(subject.getValue());
							seqNo.append(pp.getPeriodNo());
							if (cstpp.getTimePointNo() < 10)
								seqNo.append("0" + cstpp.getTimePointNo());
							else
								seqNo.append(cstpp.getTimePointNo());
							seqNo.append(cstpp.getVacutainerNo());
							sampleTimepoints.add(new SampleTimepointDto(index, sb.toString(),
									pp.getStudy().getProjectNo(), subject.getValue(), pp.getPeriodNo() + "", timepoint,
									cstpp.getVacutainerNo() + "", treatment, subject.getKey(), cstpp.getTimePointNo(),
									seqNo.toString(), timepoint));
							/*
							 * if (indexpage > 5) { document.newPage(); indexpage = 1; } document.add(new
							 * Paragraph(pp.getStudy().getProjectNo() + "\n" + subject.getValue() + " P" +
							 * pp.getPeriodNo() + "(" + "-" + cstpp.getTimePoint() + " hr) " +
							 * cstpp.getVacutainerNo() + "\n" + seqNo.toString()));
							 * 
							 * + cstpp.getVacutainerNo() + "\n" + seqNo.toString() + "\n" + sb.toString()));
							 * 
							 * barcodeWriter = new QRCodeWriter(); BitMatrix bitMatrix =
							 * barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
							 * BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix); Image
							 * image = Image.getInstance(bImage, null); document.add(image); indexpage++;
							 */
							if (claint.equals("ADV")) {
								p.add(pp.getStudy().getProjectNo() + "\n" + subject.getValue() + " P" + pp.getPeriodNo()
										+ "" + timepoint + " " + cstpp.getVacutainerNo() + "\n" + seqNo.toString()
										+ "\n" + sb.toString());

								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								image = Image.getInstance(bImage, null);
							} else {
								p.add(pp.getStudy().getProjectNo() + "  P" + pp.getPeriodNo() + "\n"
										+ subject.getValue() + " " + timepoint + "\n" + cstpp.getVacutainerNo() + "   "
										+ seqNo.toString() + "  " + cstpp.getTreatmentInfo().getTreatmentName() + "\n"
										+ sb.toString());

								barcodeWriter = new QRCodeWriter();
								BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
										70);
								BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
								image = Image.getInstance(bImage, null);
							}
							/*
							 * cell = new PdfPCell(); cell.addElement(p);
							 * cell.setBorder(Rectangle.NO_BORDER);
							 */
							tab.addCell(p);

							Chunk chunk = new Chunk(image, 0, 0, true);
							cell = new PdfPCell();
							cell.addElement(chunk);
							cell.setBorder(Rectangle.NO_BORDER);
							tab.addCell(cell);

							cell = new PdfPCell();
							cell.addElement(tab);
							mainTab.addCell(cell);
						}
						index++;
					}
				}
			}
			document.add(mainTab);
			document.close();

			String path = realPath;
			path = path + "BARCODE\\Vacutainer_BarCode\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

			for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
				path = folderPath + "Vacutainer_BarCode" + (i + 1) + ".prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> code = new ArrayList<>();
				List<String> tInfo = new ArrayList<>();
				List<String> mInfo = new ArrayList<>();
				List<String> bInfo = new ArrayList<>();
				List<String> mInfo2 = new ArrayList<>();
				List<String> bInfo2 = new ArrayList<>();
				for (int j = 0; j < noOfLableColumns; j++) {
					if ((i + j) < sampleTimepoints.size()) {
						SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
						System.out.println(cstpp.getTimePoint());
//						String bedNo = bedNos.get(cstpp.getSubjectNo());
						code.add(cstpp.getBarcode());
						tInfo.add(cstpp.getPeriodNo());
						mInfo.add("S" + cstpp.getSubjectNo());

						if (claint.equals("ADV")) {
							mInfo2.add(cstpp.getSubjectNo() + " P" + cstpp.getPeriodNo() + "(" + cstpp.getTimePoint()
									+ ") " + cstpp.getVacutinaerNo());
							bInfo.add(cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo());
						} else {
							mInfo2.add(cstpp.getSubjectNo() + " " + cstpp.getTimePoint() + "");
							bInfo.add(cstpp.getVacutinaerNo() + "   " + cstpp.getSeqNo());
							bInfo2.add(cstpp.getProjectNo() + "  P" + cstpp.getPeriodNo());
						}

					} else {
						code.add("");
						tInfo.add("");
						bInfo.add("");
					}
				}
				if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes"))

					if (claint.equals("ADV")) {
						/*
						 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
						 * mInfo, mInfo2, bInfo, bInfo2, strList);
						 */
					} else {
						barcodeBo.pnrFileCreationVacAndAVAN(path, noOfLableColumns, code, tInfo, mInfo, mInfo2, bInfo,
								bInfo2);
					}
			}
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	@Override
	public String generateVialBarcodeAll(Long periodId, String realPath) {
		String file = checktheFile(realPath, "vial.pdf");
		System.out.println(file);
		String claint = environment.getRequiredProperty("claietName");
		StudyPeriodMaster perioda = studyDao.periodById(periodId);
		List<StudyPeriodMaster> periodList = studyDao.getStudyPeriodMasterWithStudy(perioda.getStudy());
		List<SampleTimePoints> timepoints = barcodeDao.sampleTimePoints(perioda.getStudy().getId());
		PdfPTable mainTab = new PdfPTable(4);
		mainTab.setWidthPercentage(100f);
		Image image = null;
		PdfPCell cell = null;
		try {
			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			QRCodeWriter barcodeWriter = null;
			List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
			int index = 1;
			int indexpage = 1;
			Map<Integer, String> subjects = subjectList(perioda.getStudy());
			for (StudyPeriodMaster pp : periodList) {
//				key-subjectNo, value -object
				Map<Integer, SubjectRandamization> randamixation = getSubjectRandamizationWithPeriodForVial(pp);
				for (SampleTimePoints cstpp : timepoints) {
					for (int vial = 1; vial <= cstpp.getNoOfVials(); vial++) {
						for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
							SubjectRandamization sr = randamixation.get(subject.getKey());
							boolean flag = false;
							if (sr != null) {
								if (sr.getTreatmentInfo().getId() == cstpp.getTreatmentInfo().getId()) {
									flag = true;
								}
							} else {
								flag = true;
							}
							if (flag) {
								Paragraph p = new Paragraph();
								PdfPTable tab = new PdfPTable(1);
								tab.setWidthPercentage(100f);

								String treatment = "";
								if (cstpp.getTreatmentInfo() != null)
									treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
								StringBuffer sb = new StringBuffer();
								sb.append("05");
								sb.append("a");
								sb.append(pp.getId());
								sb.append("a");
								sb.append(cstpp.getTreatmentInfo().getId());
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
								String timepoint = "(" + cstpp.getSign() + "" + cstpp.getTimePoint() + " hr) ";
								StringBuffer seqNo = new StringBuffer();
								seqNo.append(subject.getValue());
								seqNo.append(pp.getPeriodNo());
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
										pp.getStudy().getProjectNo(), subject.getValue(), pp.getPeriodNo() + "",
										timepoint, cstpp.getVacutainerNo() + "", treatment, subject.getKey(),
										cstpp.getTimePointNo(), seqNo.toString(), timepoint));
								if (indexpage > 5) {
									document.newPage();
									indexpage = 1;
								}
								/*
								 * document.add(new Paragraph(pp.getStudy().getProjectNo() + "\n" +
								 * subject.getValue() + " P" + pp.getPeriodNo() + "(" + "-" +
								 * cstpp.getTimePoint() + " hr) " + cstpp.getVacutainerNo() + "\n" +
								 * seqNo.toString()));
								 * 
								 * + cstpp.getVacutainerNo() + "\n" + seqNo.toString() + "\n" + sb.toString()));
								 * 
								 * barcodeWriter = new QRCodeWriter(); BitMatrix bitMatrix =
								 * barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70, 70);
								 * BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix); Image
								 * image = Image.getInstance(bImage, null); document.add(image); indexpage++;
								 */
								if (claint.equals("ADV")) {
									p.add(pp.getStudy().getProjectNo() + "\n" + subject.getValue() + " P"
											+ pp.getPeriodNo() + "" + timepoint + "" + cstpp.getVacutainerNo() + "\n"
											+ seqNo.toString() + "\n" + sb.toString());

									barcodeWriter = new QRCodeWriter();
									BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
											70);
									BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
									image = Image.getInstance(bImage, null);
								} else {
									p.add(pp.getStudy().getProjectNo() + " P" + pp.getPeriodNo() + "\n"
											+ subject.getValue() + " " + timepoint + "\n" + cstpp.getVacutainerNo()
											+ "   " + seqNo.toString() + "  "
											+ cstpp.getTreatmentInfo().getTreatmentName() + "\n" + sb.toString());

									barcodeWriter = new QRCodeWriter();
									BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 70,
											70);
									BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
									image = Image.getInstance(bImage, null);
								}
								/*
								 * cell = new PdfPCell(); cell.addElement(p);
								 * cell.setBorder(Rectangle.NO_BORDER);
								 */
								tab.addCell(p);

								Chunk chunk = new Chunk(image, 0, 0, true);
								cell = new PdfPCell();
								cell.addElement(chunk);
								cell.setBorder(Rectangle.NO_BORDER);
								tab.addCell(cell);

								cell = new PdfPCell();
								cell.addElement(tab);
								mainTab.addCell(cell);
							}

						}
					}

					index++;
				}
			}
			document.add(mainTab);
			document.close();
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")
					&& environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				String path = realPath;
				path = path + "BARCODE\\vial_BarCode\\";
				File f1 = new File(path);
				if (!f1.exists() || f1.isDirectory())
					f1.mkdirs();
				String folderPath = path;
				List<String> fileNames = new ArrayList<>();
				int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
//				VacutainerBarcodePNRfileGeneration barcodeBo = new VacutainerBarcodePNRfileGeneration();

				for (int i = 0; i < sampleTimepoints.size(); i = i + noOfLableColumns) {
					path = folderPath + "vial_BarCode" + (i + 1) + ".prn";
					fileNames.add(path);
					System.out.println(path);
					List<String> code = new ArrayList<>();
					List<String> tInfo = new ArrayList<>();
					List<String> mInfo = new ArrayList<>();
					List<String> bInfo = new ArrayList<>();
					List<String> mInfo2 = new ArrayList<>();
					List<String> bInfo2 = new ArrayList<>();
					for (int j = 0; j < noOfLableColumns; j++) {
						if ((i + j) < sampleTimepoints.size()) {
							SampleTimepointDto cstpp = sampleTimepoints.get(i + j);
							System.out.println(cstpp.getTimePoint());
//							String bedNo = bedNos.get(cstpp.getSubjectNo());
							code.add(cstpp.getBarcode());
							tInfo.add(cstpp.getPeriodNo());
							mInfo.add("S" + cstpp.getSubjectNo());
							if (claint.equals("ADV")) {
								mInfo2.add(cstpp.getSubjectNo() + " P" + cstpp.getPeriodNo() + "" + cstpp.getTimePoint()
										+ "" + cstpp.getVacutinaerNo());
								bInfo.add(cstpp.getSeqNo());
								bInfo2.add(cstpp.getProjectNo());
							} else {
								mInfo2.add(cstpp.getSubjectNo() + " " + cstpp.getTimePoint());
								bInfo.add(cstpp.getVacutinaerNo() + "  " + cstpp.getSeqNo());
								bInfo2.add(cstpp.getProjectNo() + " P" + cstpp.getPeriodNo());
							}

						} else {
							code.add("");
							tInfo.add("");
							bInfo.add("");
						}
					}
					/*
					 * barcodeBo.pnrFileCreationVacAndVial(path, noOfLableColumns, code, tInfo,
					 * mInfo, mInfo2, bInfo, bInfo2, strList);
					 */
				}
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;
	}

	private Map<Integer, SubjectRandamization> getSubjectRandamizationWithPeriodForVial(StudyPeriodMaster pp) {
		Map<Integer, SubjectRandamization> randamixation = new HashMap<>();
		List<SubjectRandamization> sublist = barcodeDao.getSubjectRandamizationWithPeriodForVial(pp);
		for (SubjectRandamization subl : sublist) {
			randamixation.put(Integer.parseInt(subl.getSubjectNo()), subl);
		}
		return randamixation;
	}

	@Override
	public SeparationVacutinerDto separationVacutinerDto(Long studyId) {
		SeparationVacutinerDto dto = new SeparationVacutinerDto();
		Map<Long, SampleTimePoints> timeIdTimePointMap = new HashMap<>();
		List<SampleTimepointDto> sampleTimepoints = new ArrayList<>();
		SepatationDtoDetails spDto = null;
		StudyMaster study = null;
		List<StudyPeriodMaster> periodList = null;
		List<SampleTimePoints> timepoints = null;
		Map<Integer, String> subjects = null;
		Map<Long, String> timPointsMap = new HashMap<>();
		List<CentrifugationDataMaster> centrifugationList = null;
		Map<String, StudySubjects> subMap = new HashMap<>();
		List<StudySubjects> subList = null;
		Map<String, StudySubjects> replaceSubMap = new HashMap<>();
		try {
			spDto = studyDao.getSepatationDtoDetails(studyId);
			if (spDto != null) {
				study = spDto.getStudy();
				periodList = spDto.getPeriodList();
				timepoints = spDto.getTimepoints();
				centrifugationList = spDto.getCentrifugationList();
				subList = spDto.getSubjectsList();
			}
			for (SampleTimePoints cstpp : timepoints) {
				timeIdTimePointMap.put(cstpp.getId(), cstpp);
			}
			dto.setTimeIdTimePointMap(timeIdTimePointMap);
			int index = 1;
			subjects = subjectList(study);
			for (StudyPeriodMaster pp : periodList) {
				for (SampleTimePoints cstpp : timepoints) {
					for (int vial = 1; vial <= cstpp.getNoOfVials(); vial++) {
						for (Map.Entry<Integer, String> subject : subjects.entrySet()) {
							String treatment = "";
							if (cstpp.getTreatmentInfo() != null)
								treatment = "Treatement " + cstpp.getTreatmentInfo().getTreatmentNo() + "\n";
							StringBuffer sb = new StringBuffer();
							sb.append("04");
							sb.append("a");
							sb.append(pp.getId());
							sb.append("a");
							sb.append(cstpp.getTreatmentInfo().getId());
							sb.append("a");
							sb.append(subject.getValue());
							sb.append("a");
							sb.append(cstpp.getId());
							sb.append("n");
							String timepoint = "(" + cstpp.getSign() + "" + cstpp.getTimePoint() + " hr) ";
							StringBuffer seqNo = new StringBuffer();
							seqNo.append(subject.getValue());
							seqNo.append(pp.getPeriodNo());
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
									pp.getStudy().getProjectNo(), subject.getValue(), pp.getPeriodNo() + "", timepoint,
									cstpp.getVacutainerNo() + "", treatment, subject.getKey(), cstpp.getTimePointNo(),
									seqNo.toString(), timepoint));
						}
					}
					index++;
					timPointsMap.put(cstpp.getId(), cstpp.getSign() + cstpp.getTimePoint());
				}
			}
			if (subList != null && subList.size() > 0) {
				for (StudySubjects ss : subList) {
					subMap.put(ss.getSubjectNo(), ss);
					if (ss.getStdSubjectId() != null) {
						replaceSubMap.put(ss.getStdSubjectId().getSubjectNo(), ss);
						if (!subMap.containsKey(ss.getReportingId().getSubjectNo()))
							subMap.put(ss.getReportingId().getSubjectNo(), ss);
					}
				}
			}
			dto.setSampleTimePoitns(sampleTimepoints);
			dto.setTimePointsMap(timPointsMap);
			dto.setCentrifugationList(centrifugationList);
			dto.setSubMap(subMap);
			dto.setReplaceSubMap(replaceSubMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

//	@Override
//	public CentrificationDto colectedSampleVacutainers(Long studyId) {
//		CentrificationDto cfd = null;
//		List<SubjectSampleCollectionTimePointsData> ssctpdList = null;
//		Map<String, Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>>> ssctpdMap = new HashMap<>();
//		Map<Long, Map<Long, SubjectSampleCollectionTimePointsData>> periodMap = null;
//		Map<Long, SubjectSampleCollectionTimePointsData> tpMap = null;
//		CentrificationDetailsDto cfdPojo = null;
//		StudyMaster sm = null;
//		List<Long> spmIdsList = null;
//		try {
//			cfdPojo = clinicalDao.getCollectedSamplesList(studyId);
//			if(cfdPojo != null) {
//				ssctpdList = cfdPojo.getSsctpdList();
//				sm = cfdPojo.getSm();
//				spmIdsList = cfdPojo.getSpmIdsList();
//				if(ssctpdList != null && ssctpdList.size() > 0) {
//					for(SubjectSampleCollectionTimePointsData ssctpd : ssctpdList) {
//						if(ssctpdMap.containsKey(ssctpd.getSubject().getSubjectNo())) {
//							periodMap = ssctpdMap.get(ssctpd.getSubject().getSubjectNo());
//							if(periodMap.containsKey(ssctpd.getStudyPeriodMaster().getId())) {
//								tpMap = periodMap.get(ssctpd.getStudyPeriodMaster().getId());
//								if(!tpMap.containsKey(ssctpd.getSampleTimePoint().getId())) {
//									tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
//									periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
//									ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
//								}
//							}else {
//								tpMap = new HashMap<>();
//								tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
//								periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
//								ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
//							}
//						}else {
//							tpMap = new HashMap<>();
//							periodMap = new HashMap<>();
//							tpMap.put(ssctpd.getSampleTimePoint().getId(), ssctpd);
//							periodMap.put(ssctpd.getStudyPeriodMaster().getId(), tpMap);
//							ssctpdMap.put(ssctpd.getSubject().getSubjectNo(), periodMap);
//						}
//					}
//				}
//				cfd = new CentrificationDto();
//				cfd.setSsctpMap(ssctpdMap);
//				cfd.setSm(sm);
//				cfd.setSpmIdsList(spmIdsList);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return cfd;
//	}

	@Override
	public List<Deepfreezer> getDeepfreezerList() {
		List<Deepfreezer> deep = null;
		deep = barcodeDao.getDeepfreezerList();
		return deep;
	}

	@Override
	public String generateDeepfreezerBarcode(Long id, String realPath, HttpServletRequest request) {
		String file = request.getSession().getServletContext().getRealPath("/") + "Deepfreezer_Barcodes.pdf";
		System.out.println(file);
		Deepfreezer deeppojo = barcodeDao.getDeepfreezerWithId(id);
		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("07").append("a").append(deeppojo.getId()).append("n");

			document.add(new Paragraph("  " + deeppojo.getInstrument().getInstrumentNo() + "\n" + "  " + "Rack : "
					+ deeppojo.getRackNo()));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 60, 65);
			// BitMatrix bitMatrix = barcodeWriter.encode(addPrefixZeros(sb.toString(), 5),
			// BarcodeFormat.QR_CODE, 70,70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			String path = realPath;
			path = path + "BARCODE\\Deepfreezer\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			InstrumentBarcodePNRfileGeneration barcodeBo = new InstrumentBarcodePNRfileGeneration();
			path = folderPath + "Deepfreezer" + deeppojo.getInstrument().getInstrumentNo() + "_" + deeppojo.getRackNo()
					+ ".prn";
			fileNames.add(path);
			System.out.println(path);
			List<String> finfo = new ArrayList<>();
			List<String> code = new ArrayList<>();
			List<String> istno = new ArrayList<>();
			for (int j = 0; j < noOfLableColumns; j++) {
				finfo.add(deeppojo.getInstrument().getInstrumentNo());
				code.add(sb.toString());
				istno.add("Rack : " + deeppojo.getRackNo());
			}

			if (environment.getRequiredProperty("DeepfreezerBarcodeLable").equals("Yes"))
				barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, finfo, istno);
			if (environment.getRequiredProperty("DeepfreezerBarcodeLable").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;

	}

	@Override
	public String generateDeepfreezerShelfBarcode(Long id, String realPath, HttpServletRequest request) {
		String file = request.getSession().getServletContext().getRealPath("/") + "Deepfreezer_Shelf_Barcodes.pdf";
		System.out.println(file);
		DeepfreezerShelf deeppojo = barcodeDao.getDeepfreezerShelfById(id);
		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("09").append("a").append(deeppojo.getId()).append("n");

			document.add(new Paragraph("  " + deeppojo.getInstrument().getInstrumentNo() + "\n" + "  " + "Shelf No : "
					+ deeppojo.getShelfNo()));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 60, 65);
			// BitMatrix bitMatrix = barcodeWriter.encode(addPrefixZeros(sb.toString(), 5),
			// BarcodeFormat.QR_CODE, 70,70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();

			String path = realPath;
			path = path + "BARCODE\\Deepfreezer\\";
			File f1 = new File(path);
			if (!f1.exists() || f1.isDirectory())
				f1.mkdirs();
			String folderPath = path;
			List<String> fileNames = new ArrayList<>();
			int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
			InstrumentBarcodePNRfileGeneration barcodeBo = new InstrumentBarcodePNRfileGeneration();
			path = folderPath + "Deepfreezer" + deeppojo.getInstrument().getInstrumentNo() + "_" + deeppojo.getShelfNo()
					+ ".prn";
			fileNames.add(path);
			System.out.println(path);
			List<String> finfo = new ArrayList<>();
			List<String> code = new ArrayList<>();
			List<String> istno = new ArrayList<>();
			for (int j = 0; j < noOfLableColumns; j++) {
				finfo.add(deeppojo.getInstrument().getInstrumentNo());
				code.add(sb.toString());
				istno.add("Rack : " + deeppojo.getShelfNo());
			}

			if (environment.getRequiredProperty("a").equals("Yes"))
				barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, finfo, istno);
			if (environment.getRequiredProperty("DeepfreezerBarcodeLable").equals("Yes")) {
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;

	}

	@Override
	public List<Long> getStudyPeriodMasterWithStudy(StudyMaster sm) {
		// TODO Auto-generated method stub
		return barcodeDao.getStudyPeriodMasterWithStudy(sm);
	}

	@Override
	public List<SubjectRandamization> getSubjectRandamizationWithPeriods(List<Long> psmList) {
		// TODO Auto-generated method stub
		return barcodeDao.getSubjectRandamizationWithPeriods(psmList);
	}

	@Override
	public List<StudyPeriodMaster> getStudyPeriodMasterWithStudyList(StudyMaster sm) {
		return barcodeDao.getStudyPeriodMasterWithStudyList(sm);
	}

	@Override
	public StudySampleCentrifugation studySampleCentrifugationDetails(Long studyId) {
		return studyDao.studySampleCentrifugationDetails(studyId);
	}

//	@Override
//	public List<StudyPeriodMaster> getStudyPeriodMasterList(long id) {
//		return studyDao.getStudyPeriodMasterList(id);
//	}

	@Override
	public List<DeepfreezerShelf> getDeepfreezerShelfList() {
		// TODO Auto-generated method stub
		return studyDao.getDeepfreezerShelfList();
	}

	@Override
	public List<SampleTimePoints> getSampleTimePointDataWithStudyId(long id) {
		return studyDao.getSampleTimePointDataWithStudyId(id);
	}

	@Override
	public VialRackCollectionDto getSampleTimePointDataWithStudyIdForKeyAndValue(long studyId,
			VialRackCollectionDto dto) {
		return studyDao.getSampleTimePointDataWithStudyIdForKeyAndValue(studyId, dto);
	}

	@Override
	public Map<Long, GlobalparameterFromDto> getVitalGlobalParameters(Long studyId, Long languageId) {
		Map<Long, GlobalparameterFromDto> gpDtoMap = new HashMap<>();
		List<VitalTimePoints> vptList = null;
		try {
			vptList = studyDao.getVitalTimePoint(studyId);
			if (vptList != null && vptList.size() > 0) {
				for (VitalTimePoints vpt : vptList) {
					List<Long> vptParamIds = new ArrayList<>();
					String parametersStr = vpt.getParameterIds();
					if (parametersStr != null && !parametersStr.equals("")) {
						String[] tempArr = parametersStr.split("\\,");
						if (tempArr.length > 0) {
							for (String st : tempArr)
								vptParamIds.add(Long.parseLong(st));
						}
					}
					if (vptParamIds.size() > 0) {
						GlobalparameterFromDto gpfDto = studyDao.getGlobalParameterDetails(languageId, vptParamIds,
								StudyActivities.StudyExecutionVitals.toString());
						if (gpfDto != null) {
							gpDtoMap.put(vpt.getId(), gpfDto);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gpDtoMap;
	}

	public String generateSampleContainerBarcode(SampleContainer container, String realPath,
			HttpServletRequest request) {
		String file = request.getSession().getServletContext().getRealPath("/") + "Sample_Container_Barcodes.pdf";
		System.out.println(file);
		try {

			Document document = new Document(new Rectangle(PageSize.A4));
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
//			Barcode128 code128 = null;
			QRCodeWriter barcodeWriter = null;
			StringBuilder sb = new StringBuilder();
			sb.append("10").append("a").append(container.getId()).append("n");

			document.add(new Paragraph("Study : " + container.getStudy().getProjectNo() + "\nPeriod : "
					+ container.getPeriod().getPeriodNo() + "\nSubject : " + container.getSubjects() + "\nAliquot : "
					+ container.getAliquot() + "\nNo.Of Samples : " + container.getVialsList().size()));

			barcodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 60, 65);
			// BitMatrix bitMatrix = barcodeWriter.encode(addPrefixZeros(sb.toString(), 5),
			// BarcodeFormat.QR_CODE, 70,70);
			BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			Image image = Image.getInstance(bImage, null);
			document.add(image);

			document.close();
			if (environment.getRequiredProperty("barcodeLablePrint").equals("Yes")) {
				String path = realPath;
				path = path + "BARCODE\\SampleContainer\\";
				File f1 = new File(path);
				if (!f1.exists() || f1.isDirectory())
					f1.mkdirs();
				String folderPath = path;
				List<String> fileNames = new ArrayList<>();
				int noOfLableColumns = Integer.parseInt(environment.getRequiredProperty("noOfLableColumns"));
				InstrumentBarcodePNRfileGeneration barcodeBo = new InstrumentBarcodePNRfileGeneration();
				path = folderPath + "SampleContainer.prn";
				fileNames.add(path);
				System.out.println(path);
				List<String> finfo = new ArrayList<>();
				List<String> code = new ArrayList<>();
				List<String> istno = new ArrayList<>();

				for (int j = 0; j < noOfLableColumns; j++) {
					finfo.add("Study : " + container.getStudy().getProjectNo() + "\nPeriod : "
							+ container.getPeriod().getPeriodNo() + "\nSubject : " + container.getSubjects()
							+ "\nAliquot : " + container.getAliquot() + "\nNo.Of Samples : "
							+ container.getVialsList().size());
					code.add(sb.toString());
					istno.add("");
				}

				barcodeBo.pnrFileCreationTop2Lines(path, noOfLableColumns, code, finfo, istno);
				PrintBarcodeFile pbf = new PrintBarcodeFile();
				PrintBarcodeFile.serviceName = environment.getRequiredProperty("barcodePrinterip");
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
		return file;

	}

	@Override
	public SortedMap<Long, SortedMap<String, List<SubjectMealsTimePointsData>>> collectedData(Long studyId,
			MealsTimePointsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, SubjectMealsTimePointsData> timePointCollectedData(MealsTimePointsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
}
