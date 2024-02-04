package com.covideinfo.eform.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.io.DOMReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.covideinfo.eform.dao.EformDao;
import com.covideinfo.eform.model.EForm;
import com.covideinfo.eform.model.EFormEleCaliculation;
import com.covideinfo.eform.model.EFormGroup;
import com.covideinfo.eform.model.EFormGroupElement;
import com.covideinfo.eform.model.EFormGroupElementValue;
import com.covideinfo.eform.model.EFormMapplingTable;
import com.covideinfo.eform.model.EFormMapplingTableColumn;
import com.covideinfo.eform.model.EFormMapplingTableColumnMap;
import com.covideinfo.eform.model.EFormRule;
import com.covideinfo.eform.model.EFormRuleWithOther;
import com.covideinfo.eform.model.EFormSection;
import com.covideinfo.eform.model.EFormSectionElement;
import com.covideinfo.eform.model.EFormSectionElementValue;
import com.covideinfo.eform.model.EFormVisibility;
import com.covideinfo.eform.service.EFormExcelSheet;
import com.covideinfo.eform.service.EFormGroupElementSheet;
import com.covideinfo.eform.service.EFormGroupSheet;
import com.covideinfo.eform.service.EFormSectionElementSheet;
import com.covideinfo.eform.service.EFormSectionSheet;
import com.covideinfo.eform.service.EFormService;
import com.covideinfo.eform.xmlfiles.EleCaliculation;
import com.covideinfo.model.UserMaster;
@Service("/eformService")
public class EformServiceImpl implements EFormService {
	
	@Autowired
	EformDao eformDao;

	@Override
	public List<EForm> findAllEForm() {
		return eformDao.findAllEForm();
	}
	
	private Map<String, EFormSection> elementSections = null; // key-sectionName value-Section
	private Map<String, EFormSection> groupSections = null; // key-sectionName value-Section
	private Map<String, EFormGroup> groupsMap = null; // key-groupName value-CrfGroup

	private EFormExcelSheet exclData;
	private List<String> sectionNames;
	private List<String> groupNames;
	private boolean flag;
	private Set<String> secNames = null;

	@Override
	public EForm readCrfExcelFile(FileInputStream inputStream, String fileName) throws IOException  {
		sectionNames = new ArrayList<>();
		groupNames = new ArrayList<>();
		flag = true;
		elementSections = new HashMap<>();
		groupSections = new HashMap<>();
		groupsMap = new HashMap<>();
		secNames = new HashSet<>();
		EForm crf = new EForm();
		Workbook guru99Workbook = null;
		// Find the file extension by splitting file name in substring and getting only
		// extension name
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			guru99Workbook = new XSSFWorkbook(inputStream);
		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of HSSFWorkbook class
			guru99Workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		crf = readCrfSheet(crf, guru99Workbook.getSheet("CRF"));
		EForm oldcrf = eformDao.checkEForm(crf.getName());
		if (oldcrf != null) {
			crf.setMessage("duplicate");
			return crf;
		}

		checkSectionSheetData(guru99Workbook.getSheet("SECTION"));
		checkGroupSheetData(guru99Workbook.getSheet("GROUP"));
		checkSectionElementSheetData(guru99Workbook.getSheet("SECTION ELEMENTS"));
		checkGroupElementSheetData(guru99Workbook.getSheet("GROUP ELEMENT"));

		if (flag) {
			crf = readCrfSectionSheet(crf, guru99Workbook.getSheet("SECTION"));
			crf = readCrfGroupSheet(crf, guru99Workbook.getSheet("GROUP"));
			crf = readCrfSectionElements(crf, guru99Workbook.getSheet("SECTION ELEMENTS"));
			crf = readCrfGroupElements(crf, guru99Workbook.getSheet("GROUP ELEMENT"));

			List<EFormSection> secl = new ArrayList<>();
			for (String sc : secNames) {
				if (elementSections.get(sc) != null)
					secl.add(elementSections.get(sc));
				else {
//					EFormSection sec  = groupSections.get(sc);
//					CrfGroup g = sec.getGroup();
//					for(CrfGroupElement e : g.getElement()) {
//						System.out.println(e);
//					}
					secl.add(groupSections.get(sc));
				}
			}
			Collections.sort(secl);
			crf.setSections(secl);

		}
		crf.setFlag(flag);
		crf.setExclData(exclData);
		return crf;
		
	}

	private EForm readCrfGroupElements(EForm crf, Sheet sheet) {
		try {
			List<EFormGroupElement> eles = new ArrayList<EFormGroupElement>();
			// Find number of rows in excel file
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				String secName = "";
				if (row != null) {
					EFormGroupElement ele = new EFormGroupElement();
					ele.setEform(crf);
					eles.add(ele);
					// Create a loop to print cell values in a row
					for (int j = 0; j < row.getLastCellNum(); j++) {
						// Print Excel data in console
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (j + 1) {
							case 1:
								Double d = cell.getNumericCellValue();
								int sno = d.intValue();
								ele.setSno(sno + "");
								System.out.print(cell.getNumericCellValue() + "\t");
								break;
							case 2:
								ele.setName(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 3:
								ele.setTitle(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 4:
								d = cell.getNumericCellValue();
								ele.setColumnNo(d.intValue());
								System.out.print(d + "\t");
								break;
							case 5:
								d = cell.getNumericCellValue();
								ele.setRowNo(d.intValue());
								break;
							case 6:
								ele.setType(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 7:
								if (cell.getStringCellValue() != null) {
									ele.setResponceType(cell.getStringCellValue());
									String[] sr = ele.getResponceType().split("##");
									List<EFormGroupElementValue> list = new ArrayList<>();
									for (int k = 0; k < sr.length; k++) {
										EFormGroupElementValue v = new EFormGroupElementValue();
										v.setGroupElement(ele);
										v.setValue(sr[k]);
										list.add(v);
									}
									ele.setElementValues(list);
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 8:
								if (cell.getStringCellValue() != null && cell.getStringCellValue().equals("vertical")) {
									ele.setDisplay("vertical");
									System.out.print(cell.getStringCellValue() + "\t");
								}
							case 9:
								if (cell.getStringCellValue() != null) {
									ele.setValues(cell.getStringCellValue());
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 10:
								// DISPLAY VALUES PATTREN REQUIRED SECTION NAME GROUP NAME
								if (cell.getStringCellValue() != null) {
									ele.setPattren(cell.getStringCellValue());
								}
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 11:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setRequired(true);
								System.out.print(d + "\t");
								break;
							case 12:
								secName = cell.getStringCellValue().trim();
								System.out.print(secName + "\t");
								break;
							case 13:
								String gname = cell.getStringCellValue().trim();
								System.out.print(gname + "\t");
								EFormSection sec = groupSections.get(secName);
								EFormGroup g = sec.getGroup();
								EFormGroup g1 = groupsMap.get(gname);
								if (g.getName().equals(g1.getName())) {
									List<EFormGroupElement> element = g.getElement();
									if (element == null)
										element = new ArrayList<>();
									element.add(ele);
									g1.setElement(element);
									groupsMap.put(g.getName(), g1);
								}
								break;

							case 14:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setDiscrepancy(true);
								System.out.print(d + "\t");
								break;
							case 15:
								d = cell.getNumericCellValue();
								ele.setCommentRequired(true);
								break;
							case 16:
								if (cell.getStringCellValue() != null) {
									ele.setCommentValue(cell.getStringCellValue());
								}
								break;
							case 17:
								d = cell.getNumericCellValue();
								ele.setTargetField(d.intValue());
								break;
							default:
								break;
							}
						}
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crf;
	}

	private EForm readCrfSectionElements(EForm crf, Sheet sheet) {
		try {
			List<EFormSectionElement> eles = new ArrayList<EFormSectionElement>();
			// Find number of rows in excel file
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					EFormSectionElement ele = new EFormSectionElement();
					ele.setEform(crf);
					eles.add(ele);
					// Create a loop to print cell values in a row
					for (int j = 0; j < row.getLastCellNum(); j++) {
						// Print Excel data in console
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (j + 1) {
							case 1:
								Double d = cell.getNumericCellValue();
								int sno = d.intValue();
								ele.setSno(sno + "");
								break;
							case 2:
								ele.setName(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 3:
								ele.setLeftDesc(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 4:
								ele.setRigtDesc(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 5:
								ele.setMiddeDesc(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 6:
								ele.setTotalDesc(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 7:
								d = cell.getNumericCellValue();
								ele.setColumnNo(d.intValue());
								System.out.print(d + "\t");
								break;
							case 8:
								d = cell.getNumericCellValue();
								ele.setRowNo(d.intValue());
								System.out.print(d + "\t");
								break;
							case 9:
								if (cell.getStringCellValue() != null) {
									ele.setTopDesc(cell.getStringCellValue());
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 10:
								if (cell.getStringCellValue() != null) {
									ele.setBottemDesc(cell.getStringCellValue());
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 11:
								ele.setType(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 12:
								if (cell.getStringCellValue() != null) {
									ele.setResponceType(cell.getStringCellValue());
									String[] sr = ele.getResponceType().split("##");
									List<EFormSectionElementValue> list = new ArrayList<>();
									for (int k = 0; k < sr.length; k++) {
										EFormSectionElementValue v = new EFormSectionElementValue();
										v.setSectionElement(ele);
										v.setValue(sr[k]);
										list.add(v);
									}
									ele.setElementValues(list);
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 13:
								if (cell.getStringCellValue() != null && cell.getStringCellValue().equals("vertical")) {
									ele.setDisplay("vertical");
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 14:
								if (cell.getStringCellValue() != null) {
									ele.setValues(cell.getStringCellValue());
									System.out.print(cell.getStringCellValue() + "\t");
								}
								break;
							case 15:
								if (cell.getStringCellValue() != null) {
									ele.setPattren(cell.getStringCellValue());
								}
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 16:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setRequired(true);
								System.out.print(d + "\t");
								break;
							case 17:
								String secname = cell.getStringCellValue().trim();
								EFormSection sec = elementSections.get(secname);
								List<EFormSectionElement> element = sec.getElement();
								if (element == null)
									element = new ArrayList<>();
								element.add(ele);
								sec.setElement(element);
								elementSections.put(sec.getName(), sec);
								break;
							case 18:
								String dataType = cell.getStringCellValue().trim();
								ele.setDataType(dataType);
								break;
							case 19:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setDiscrepancy(true);
								System.out.print(d + "\t");
								break;
							case 20:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setCommentRequired(true);
								break;
							case 21:
								if (cell.getStringCellValue() != null) {
									ele.setCommentValue(cell.getStringCellValue());
								}
								break;
							case 22:
								d = cell.getNumericCellValue();
								ele.setTargetField(d.intValue());
								break;
							case 23:
								ele.setTypeOfTime(cell.getStringCellValue());
								break;
							case 24:
								ele.setRoles(cell.getStringCellValue());
								break;
							case 25:
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setVisibility(true);
							case 26:
								d = cell.getNumericCellValue();
								ele.setControllerWidth(d.intValue() + "");
							    break;
							case 27:
								d = cell.getNumericCellValue();
								ele.setLableWidth(d.intValue() + "");
								break;
							case 28:
								d = cell.getNumericCellValue();
								ele.setColumnWidth(d.intValue() + "");
								break;
							case 29:
								d = cell.getNumericCellValue();
								ele.setPdfControllerWidth(d.intValue() + "");
								break;
							case 30:
								d = cell.getNumericCellValue();
								ele.setPdflLableWidth(d.intValue() + "");
								break;
							case 31:
							    d = cell.getNumericCellValue();
							    ele.setPdfColumnWidth(d.intValue() + "");
							    break;
							default:
								break;
							}
						}
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crf;
	}

	private EForm readCrfGroupSheet(EForm crf, Sheet sheet) {
		try {
			List<EFormGroup> groups = new ArrayList<EFormGroup>();
			// Find number of rows in excel file
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					EFormGroup group = new EFormGroup();
					group.setEform(crf);
					// Create a loop to print cell values in a row
					for (int j = 0; j < row.getLastCellNum(); j++) {
						// Print Excel data in console
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (j + 1) {
							case 1:
								group.setName(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 2:
								group.setTitle(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 3:
								group.setHedding(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 4:
								group.setSubHedding(cell.getStringCellValue());
								System.out.print(cell.getStringCellValue() + "\t");
								break;
							case 5:
								d = cell.getNumericCellValue();
								group.setMinRows(d.intValue());
								System.out.print(d + "\t");
								break;
							case 6:
								d = cell.getNumericCellValue();
								group.setMaxRows(d.intValue());
								System.out.print(d + "\t");
								break;
							case 7:
								d = cell.getNumericCellValue();
								group.setMaxColumns(d.intValue());
								System.out.print(d + "\t");
								break;
							case 8:
								String secname = cell.getStringCellValue().trim();
								EFormSection sec = groupSections.get(secname);
								sec.setGroup(group);
								groupSections.put(sec.getName(), sec);
								break;
							default:
								break;
							}
						}
					}
					System.out.println();
					groups.add(group);
					groupsMap.put(group.getName(), group);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return crf;
	}

	private EForm readCrfSectionSheet(EForm crf, Sheet sheet) {
		secNames = new HashSet<>();
		List<EFormSection> sections = null;
		if (crf.getSections() == null)
			sections = new ArrayList<EFormSection>();
		else
			sections = crf.getSections();
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				EFormSection section = new EFormSection();
				section.setEform(crf);
				// Create a loop to print cell values in a row
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// Print Excel data in console
					Cell cell = row.getCell(j);
					if (cell != null) {
						switch (j + 1) {
						case 1:
							section.setName(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 2:
							section.setTitle(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 3:
							section.setHedding(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 4:
							section.setSubHedding(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 5:
							try {
								d = cell.getNumericCellValue();
								section.setMaxRows(d.intValue());
								System.out.print(d + "\t");
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case 6:
							try {
								d = cell.getNumericCellValue();
								section.setMaxColumns(d.intValue());
								System.out.print(d + "\t");
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case 7:
							try {
								d = cell.getNumericCellValue();
								section.setOrder(d.intValue());
								System.out.print(d + "\t");
							} catch (Exception e) {
								e.printStackTrace();
							}

							break;
						case 8:
							section.setGender(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 9:
							section.setRoles(cell.getStringCellValue().trim());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 10:
							if (cell.getStringCellValue().trim().equals("YES")) {
								section.setContainsGroup(true);
								groupSections.put(section.getName(), section);
							} else {
								elementSections.put(section.getName(), section);
							}

							System.out.print(cell.getStringCellValue() + "\t");
							break;
						default:
							break;
						}
					}
				}
				section.setElement(new ArrayList<>());
				System.out.println();
				sections.add(section);
				secNames.add(section.getName());
			}
		}
		crf.setSections(sections);
		return crf;
	}

	private EForm readCrfSheet(EForm crf, Sheet sheet) {
		flag = true;
		exclData = new EFormExcelSheet();
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			if (i < 2) {
				Row row = sheet.getRow(i);
				// Create a loop to print cell values in a row
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// Print Excel data in console
					Cell cell = row.getCell(j);
					if (cell != null) {
						switch (j + 1) {
						case 1:
							crf.setName(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 2:
							crf.setTitle(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 3:
							crf.setType(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 4:
							crf.setGender(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 5:
							crf.setVersion(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						case 6:
							crf.setEformKey(cell.getStringCellValue());
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						default:
							break;
						}
					}
				}
				System.out.println();
			}
		}
		return crf;
	}

	private void checkGroupElementSheetData(Sheet sheet) {
		List<String> names = new ArrayList<>();
		List<EFormGroupElementSheet> crfgroupElementSheet = new ArrayList<>();
		crfgroupElementSheet.add(new EFormGroupElementSheet());
		try {
			List<EFormGroupElement> eles = new ArrayList<EFormGroupElement>();
			// Find number of rows in excel file
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				String secName = "";
				if (row != null) {
					EFormGroupElementSheet ele = new EFormGroupElementSheet(i + 1);
					// Create a loop to print cell values in a row
					for (int j = 0; j < row.getLastCellNum(); j++) {
						// Print Excel data in console
						Cell cell = row.getCell(j);
						if (cell != null) {
							String s = "";
							switch (j + 1) {
							case 1:
								Double d = cell.getNumericCellValue();
								int sno = d.intValue();
								ele.setSno(sno + "");
								break;
							case 2:
								s = cell.getStringCellValue().trim();
								if (s.equals(""))
									flag = false;
								else if (names.contains(s)) {
									flag = false;
									ele.setName("<font color='red'>Duplicate Value</font>");
								} else {
									names.add(s);
									ele.setName(s);
								}
								break;
							case 3:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									ele.setDesc(s);
								break;
							case 4:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() < 1) {
										flag = false;
										ele.setColumnNo("<font color='red'>Value Must grater then 0</font>");
									} else
										ele.setColumnNo(d.intValue() + "");
								} catch (Exception e) {
									flag = false;
									ele.setColumnNo("<font color='red'>Required only number</font>");
								}
								break;
							case 5:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() < 1) {
										flag = false;
										ele.setRowNo("<font color='red'>Value Must grater then 0</font>");
									} else
										ele.setRowNo(d.intValue() + "");
								} catch (Exception e) {
									flag = false;
									ele.setRowNo("<font color='red'>Required only number</font>");
								}
								break;
							case 6:
								s = cell.getStringCellValue().trim();
								if (s.equals(""))
									flag = false;
								else if (s.equals("text") || s.equals("textArea") || s.equals("radio")
										|| s.equals("checkBox") || s.equals("select") || s.equals("date")
										|| s.equals("dateAndTime") || s.equals("file") || s.equals("non")
										|| s.equals("selectTable")) {
									ele.setType(s);
								} else {
									flag = false;
									ele.setType("<font color='red'>Invalied Data</font>");
								}
								break;
							case 7:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									ele.setResponseType(s);
								break;
							case 8:
								s = cell.getStringCellValue().trim();
								if (!s.equals("")) {
									if (s.equals("vertical"))
										ele.setDisplay(s);
									else {
										flag = false;
										ele.setDisplay("<font color='red'>Invalied Data</font>");
									}
								}

							case 9:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									ele.setValues(s);
								break;
							case 10:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									ele.setPattren(s);
								break;
							case 11:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() == 1)
										ele.setRequired(d.intValue() + "");
									else
										ele.setRequired("0");
								} catch (Exception e) {
									flag = false;
									ele.setRequired("<font color='red'>Required 0 or 1 only</font>");
								}
								break;
							case 12:
								s = cell.getStringCellValue().trim();
								if (s.equals(""))
									flag = false;
								else if (sectionNames.contains(s)) {
									ele.setSectionName(s);
								} else {
									flag = false;
									ele.setSectionName("<font color='red'>Invalied Data</font>");
								}
								break;
							case 13:
								s = cell.getStringCellValue().trim();
								if (s.equals(""))
									flag = false;
								else if (groupNames.contains(s)) {
									ele.setGroupName(s);
								} else {
									flag = false;
									ele.setGroupName("<font color='red'>Invalied Data</font>");
								}
								break;
							case 14:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() == 1)
										ele.setDiscrepnecy(d.intValue() + "");
									else
										ele.setDiscrepnecy("0");
								} catch (Exception e) {
									flag = false;
									ele.setDiscrepnecy("<font color='red'>Required 0 or 1 only</font>");
								}
								break;
							case 15:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() == 1)
										ele.setCommentRequired(d.intValue() + "");
									else
										ele.setCommentRequired("0");
								} catch (Exception e) {
									flag = false;
									ele.setCommentRequired("<font color='red'>Required 0 or 1 only</font>");
								}
								break;
							case 16:
								s = cell.getStringCellValue().trim();
								ele.setCommentValue(s);
								break;
							case 17:
								d = cell.getNumericCellValue();
								ele.setTargetField(d.toString());
								break;
							default:
								break;
							}
						}
					}
					crfgroupElementSheet.add(ele);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		exclData.setCrfgroupElementSheet(crfgroupElementSheet);
		
	}

	private void checkSectionElementSheetData(Sheet sheet) {
		List<String> names = new ArrayList<>();
		List<EFormSectionElementSheet> eles = new ArrayList<>();
		eles.add(new EFormSectionElementSheet());
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				EFormSectionElementSheet ele = new EFormSectionElementSheet(i + 1);
				// Create a loop to print cell values in a row
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// Print Excel data in console
					String s = "";
					Cell cell = row.getCell(j);
					if (cell != null) {
						switch (j + 1) {
						case 1:
							Double d = cell.getNumericCellValue();
							int sno = d.intValue();
							System.out.println(sno);
							ele.setSno(sno + "");
							break;
						case 2:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = true;
							else if (names.contains(s)) {
								flag = false;
								ele.setName("<font color='red'>Duplicate Name</font>");
							} else {
								ele.setName(s);
								names.add(s);
							}
							break;
						case 3:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setLeftDesc(s);
							break;
						case 4:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setRightDesc(s);
							break;
						case 5:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setMiddeDesc(s);
							break;
						case 6:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setTotalDesc(s);
							break;
						case 7:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() < 1) {
									flag = false;
									ele.setColumnNo("<font color='red'>Value Must grater then 0</font>");
								} else
									ele.setColumnNo(d.intValue() + "");
							} catch (Exception e) {
								flag = false;
								ele.setColumnNo("<font color='red'>Required only number</font>");
							}
							break;
						case 8:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() < 1) {
									flag = false;
									ele.setRowNo("<font color='red'>Value Must grater then 0</font>");
								} else
									ele.setRowNo(d.intValue() + "");
							} catch (Exception e) {
								flag = false;
								ele.setRowNo("<font color='red'>Required only number</font>");
							}
							break;
						case 9:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setTopDesc(s);
							break;
						case 10:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setBottomDesc(s);
							break;
						case 11:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (s.equals("text") || s.equals("textArea") || s.equals("radio")
									|| s.equals("checkBox") || s.equals("select") || s.equals("date")
									|| s.equals("dateAndTime") || s.equals("file") || s.equals("non")
									|| s.equals("selectTable")) {
								ele.setType(s);
							} else {
								flag = false;
								ele.setType("<font color='red'>Invalied Data</font>");
							}
							break;
						case 12:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setResponseType(s);
							break;
						case 13:
							s = cell.getStringCellValue().trim();
							if (!s.equals("")) {
								if (s.equals("vertical"))
									ele.setDisplay(s);
								else {
									flag = false;
									ele.setDisplay("<font color='red'>Invalied Data</font>");
								}
							}
							break;
						case 14:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setValues(s);
							break;
						case 15:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								ele.setPattren(s);
							break;
						case 16:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setRequired(d.intValue() + "");
								else
									ele.setRequired("0");
							} catch (Exception e) {
								flag = false;
								ele.setRequired("<font color='red'>Required 0 or 1 only</font>");
							}
							break;
						case 17:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (sectionNames.contains(s)) {
								ele.setSectionName(s);
							} else {
								flag = false;
								ele.setSectionName("<font color='red'>Invalied Data</font>");
							}
							break;
						case 18:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (s.equals("Number") || s.equals("String")) {
								ele.setDataType(s);
							} else {
								flag = false;
								ele.setDataType("<font color='red'>Invalied Data</font>");
							}
							break;
						case 19:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setDiscrepnecy(d.intValue() + "");
								else
									ele.setDiscrepnecy("0");
							} catch (Exception e) {
								flag = false;
								ele.setDiscrepnecy("<font color='red'>Required 0 or 1 only</font>");
							}
							break;
						case 20:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setCommentRequired(d.intValue() + "");
								else
									ele.setCommentRequired("0");
							} catch (Exception e) {
								flag = false;
								ele.setCommentRequired("<font color='red'>Required 0 or 1 only</font>");
							}
							break;
						case 21:
							s = cell.getStringCellValue().trim();
							ele.setCommentValue(s);
							break;
						case 22:
							d = cell.getNumericCellValue();
							ele.setTargetField(d.toString());
							break;
						case 23:
							if (cell.getStringCellValue() != null)
								ele.setTypeOfTime(cell.getStringCellValue());
							break;
						case 24:
							if (cell.getStringCellValue() != null)
								ele.setRoles(cell.getStringCellValue());
							break;
						case 25:
							try {
								d = cell.getNumericCellValue();
								if (d.intValue() == 1)
									ele.setVisibility(d.intValue() + "");
								else
									ele.setVisibility("0");
							} catch (Exception e) {
								e.printStackTrace();
								flag = false;
								ele.setVisibility("<font color='red'>Required 0 or 1 only</font>");
							}
							break;
						case 26:
								d = cell.getNumericCellValue();
								ele.setControllerWidth(d.intValue() + "");
							break;
						case 27:
							d = cell.getNumericCellValue();
							ele.setLableWidth(d.intValue() + "");
							break;
						case 28:
							d = cell.getNumericCellValue();
							ele.setColumnWidth(d.intValue() + "");
							break;
						case 29:
							d = cell.getNumericCellValue();
							ele.setPdfControllerWidth(d.intValue() + "");
							break;
						case 30:
							d = cell.getNumericCellValue();
							ele.setPdflLableWidth(d.intValue() + "");
							break;
						case 31:
							d = cell.getNumericCellValue();
							ele.setPdfColumnWidth(d.intValue() + "");
							break;
						default:
							break;
						}
					}
				}
				eles.add(ele);
			}
		}
		exclData.setCrfSectinElementSheet(eles);
		
	}

	private void checkGroupSheetData(Sheet sheet) {
		groupNames = new ArrayList<>();
		List<EFormGroupSheet> crfGroupSheet = new ArrayList<>();
		crfGroupSheet.add(new EFormGroupSheet());
		try {
			// Find number of rows in excel file
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			// Create a loop over all the rows of excel file to read it
			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					EFormGroupSheet groupSheet = new EFormGroupSheet(i + 1);
					// Create a loop to print cell values in a row
					for (int j = 0; j < row.getLastCellNum(); j++) {
						// Print Excel data in console
						String s = "";
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (j + 1) {
							case 1:
								s = cell.getStringCellValue().trim();
								if (s.equals(""))
									flag = true;
								else if (groupNames.contains(s)) {
									flag = false;
									groupSheet.setName("<font color='red'>Duplicate Name</font>");
								} else {
									groupSheet.setName(s);
									groupNames.add(s);
								}
								break;
							case 2:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									groupSheet.setDesc(s);
								break;
							case 3:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									groupSheet.setHedding(s);
								break;
							case 4:
								s = cell.getStringCellValue().trim();
								if (!s.equals(""))
									groupSheet.setSubHedding(s);
								break;
							case 5:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() < 1) {
										flag = false;
										groupSheet.setMaxRows("<font color='red'>Value Must grater then 0</font>");
									} else
										groupSheet.setMaxRows(d.intValue() + "");
								} catch (Exception e) {
									flag = false;
									groupSheet.setMaxRows("<font color='red'>Required only number</font>");
								}
								break;
							case 6:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() < 1) {
										flag = false;
										groupSheet.setMaxRows("<font color='red'>Value Must grater then 0</font>");
									} else
										groupSheet.setMaxRows(d.intValue() + "");
								} catch (Exception e) {
									flag = false;
									groupSheet.setMaxRows("<font color='red'>Required only number</font>");
								}
								break;
							case 7:
								try {
									d = cell.getNumericCellValue();
									if (d.intValue() < 1) {
										flag = false;
										groupSheet.setMaxColumns("<font color='red'>Required only number</font>");
									} else
										groupSheet.setMaxColumns(d.intValue() + "");
								} catch (Exception e) {
									flag = false;
									groupSheet.setMaxColumns("<font color='red'>Required only number</font>");
								}
								break;
							case 8:
								s = cell.getStringCellValue().trim();
								if (sectionNames.contains(s)) {
									groupSheet.setSectionName(s);
								} else {
									flag = false;
									groupSheet.setSectionName("<font color='red'>Section Name Not Avilable</font>");
								}
								break;
							default:
								break;
							}
						}
					}
					crfGroupSheet.add(groupSheet);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		exclData.setCrfGroupSheet(crfGroupSheet);
		
	}
	private Double d = null;
	private void checkSectionSheetData(Sheet sheet) {
		sectionNames = new ArrayList<>();
		List<String> order = new ArrayList<>();
		List<EFormSectionSheet> crfSheet = new ArrayList<>();
		crfSheet.add(new EFormSectionSheet());
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				EFormSectionSheet exsheet = new EFormSectionSheet(i + 1);
				// Create a loop to print cell values in a row
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// Print Excel data in console
					Cell cell = row.getCell(j);
					if (cell != null) {
						String s = "";
						switch (j + 1) {
						case 1:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (sectionNames.contains(s)) {
								exsheet.setName("<font color='red'>Duplicate Name</font>");
								flag = false;
							} else {
								exsheet.setName(s);
								sectionNames.add(s);

							}
							break;
						case 2:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								exsheet.setDesc(s);
							break;
						case 3:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								exsheet.setHedding(s);
							break;
						case 4:
							s = cell.getStringCellValue().trim();
							if (!s.equals(""))
								exsheet.setSubHedding(s);
							break;
						case 5:
							try {
								d = cell.getNumericCellValue();
								exsheet.setMaxRows(d.intValue() + "");
							} catch (Exception e) {
								flag = false;
								exsheet.setMaxRows("<font color='red'>Required only number</font>");
							}
							break;
						case 6:
							try {
								d = cell.getNumericCellValue();
								exsheet.setMaxColumns(d.intValue() + "");
							} catch (Exception e) {
								flag = false;
								exsheet.setMaxColumns("<font color='red'>Required only number</font>");
							}
							break;
						case 7:
							try {
								d = cell.getNumericCellValue();
								if (order.contains(d.intValue() + "")) {
									flag = false;
									exsheet.setOrder("<font color='red'>Duplicate Order</font>");
								} else
									exsheet.setOrder(d.intValue() + "");
							} catch (Exception e) {
								flag = false;
								exsheet.setOrder("<font color='red'>Required only number</font>");
							}

							break;
						case 8:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (s.equals("ALL") || s.equals("MALE") || s.equals("FEMALE")) {
								exsheet.setGender(s);
							} else {
								flag = false;
								exsheet.setGender("<font color='red'>Invalied Data</font>");
							}
							break;
						case 9:
							s = cell.getStringCellValue().trim();
							if (s.equals(""))
								flag = false;
							else if (s.equals("ALL")) {
								exsheet.setRole(s);
							} else {
								flag = false;
								exsheet.setRole("<font color='red'>Invalied Data</font>");
							}
							break;
						case 10:
							String v = cell.getStringCellValue().trim();
							if (v.equals(""))
								flag = false;
							else if (v.equals("NO") || v.equals("YES")) {
								exsheet.setContainsGroup(v);
							} else {
								flag = false;
								exsheet.setContainsGroup("<font color='red'>Invalied Data</font>");
							}
							System.out.print(cell.getStringCellValue() + "\t");
							break;
						default:
							break;
						}
					}
				}
				crfSheet.add(exsheet);
			}
		}
		exclData.setCrfSectinSheet(crfSheet);
		
	}

	@Override
	public void saveEForm(EForm crf) {
		eformDao.saveEForm(crf);
		
	}

	@Override
	public List<EFormVisibility> findAllEFormVisibility() {
		return eformDao.findAllEFormVisibility();
	}

	@Override
	public EForm eformForView(Long eformId) {
		return eformDao.eformForView(eformId);
	}

	@Override
	public String sectionEleSelect(Long id) {
		List<EFormSectionElement> list = eformDao.sectionElemets(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='secEleId' id='secEleId' class='form-control' onchange='secEleSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected' >--Select--</option>");
		for (EFormSectionElement ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String groupEleSelectForVisibility(Long id) {
		List<EFormGroupElement> list = eformDao.groupElemets(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='groupEleId' id='groupEleId' class='form-control' onchange='groupEleSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected'>--Select--</option>");
		for (EFormGroupElement ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String sections(Long id) {
		List<EFormSection> list = eformDao.sections(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='secIdAction' id='secIdAction' class='form-control' onchange='sectionSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected' >--Select--</option>");
		for (EFormSection ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String groups(Long id) {
		List<EFormGroup> list = eformDao.groups(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='groupIdAction' id='groupIdAction' class='form-control' onchange='groupSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected'>--Select--</option>");
		for (EFormGroup ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String saveEFormVisibilitySave(String username, String name, String desc, String compareWith, Long crfid,
			Long secId, Long groupId, String fieldValue, String condition, Long crfidAction, Long secIdAction,
			Long groupIdAction) {
		EForm crf = eformDao.getEForm(crfid);
		EFormSectionElement sectionEle = null;
		if (secId != null)
			sectionEle = eformDao.sectionElement(secId);
		EFormGroupElement groupElement = null;
		if (groupId != null)
			groupElement = eformDao.groupElement(groupId);

		EForm targetCrf = eformDao.getEForm(crfidAction);
		EFormSection section = null;
	    EFormGroup group = null;
		if (secIdAction != null)
			section = eformDao.section(secIdAction);
		if (groupIdAction != null)
			group = eformDao.group(groupIdAction);

		EFormVisibility visibilty = new EFormVisibility();
		visibilty.setName(name);
		visibilty.setDesc(desc);
		visibilty.setCrf(crf);
		visibilty.setSecEle(sectionEle);
		visibilty.setGroupEle(groupElement);
		visibilty.setAction(compareWith);
		visibilty.setFiledValue(fieldValue);
		visibilty.setCondition(condition);

		visibilty.setTargetCrf(targetCrf);
		visibilty.setSection(section);
		visibilty.setGroup(group);
		visibilty.setStatus(true);
		eformDao.saveEFormVisibility(visibilty);
		return visibilty.getName();
	}

	@Override
	public List<EForm> findAllCrfsContainsSelectTable() {
		return eformDao.findAllCrfsContainsSelectTable();
		 
	}

	@Override
	public List<EFormMapplingTable> findAllMappingTables() {
		// TODO Auto-generated method stub
		return eformDao.findAllMappingTables();
	}
	//Get Mapping Data
	@Override
	public List<EFormMapplingTableColumnMap> allCrfMapplingTableColumnMap() {
		return eformDao.allCrfMapplingTableColumnMap();
	}
   //Get SectionElements Data
	@Override
	public String eformSectionElementsSelectTable(Long id) {
		List<EFormSectionElement> list = eformDao.sectionElemetsSelectTale(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='secEleId' id='secEleId' class='form-control' onchange='secEleSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected' >--Select--</option>");
		for (EFormSectionElement ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
   // Mapping table
	@Override
	public String eformMappingTableColumns(Long id) {
		List<EFormMapplingTableColumn> list = eformDao.eformMappingTableColumns(id);
		StringBuilder sb = new StringBuilder();
		sb.append("<select class='form-control' name='columnId' id='columnId' class='form-control'>")
				.append("<option value='-1' selected='selected' >--Select--</option>");
		for (EFormMapplingTableColumn ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getCloumnName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
    // Data map to eform
	@Override
	public EFormMapplingTableColumnMap mapTableToCrfSave(Long crfid, Long secEleId, Long tableId, Long columnId,
			String userName) {
		EForm crf = new EForm();
		crf.setId(crfid);
		EFormSectionElement element = new EFormSectionElement();
		element.setId(secEleId);
		EFormMapplingTable table = new EFormMapplingTable();
		table.setId(tableId);
		EFormMapplingTableColumn column = new EFormMapplingTableColumn();
		column.setId(columnId);
		EFormMapplingTableColumnMap map = new EFormMapplingTableColumnMap();
		map.setCrf(crf);
		map.setElement(element);
		map.setMappingTable(table);
		map.setMappingColumn(column);
		map.setCreatedBy(userName);
		return eformDao.saveCrfMapplingTableColumnMap(map);
	}
    // EForm calculator for crf files
	@Override
	public List<EFormEleCaliculation> eformEleCaliculationList() {
		List<EFormEleCaliculation> list = eformDao.eformEleCaliculationList();
		return list;
	}

	@Override
	public void uploadEFormCaliculationFile(String path, String username) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// create Document Builder
		DocumentBuilder builder = factory.newDocumentBuilder();

		// get inputstrem of xml file
		// file from folder location
		String filePath = path;
		File xmlFile = new File(filePath);
		// file from class path location
		ClassLoader cl = DOMReader.class.getClassLoader();
		InputStream is = cl.getResourceAsStream("com/covide/crf/xmlfiles/caliculation2.xml");

		// parse xml file and get Document object
		// Document document = builder.parse(is);

		Document document = builder.parse(xmlFile);

		// get Root element of xml doc
		Element rootElement = document.getDocumentElement();

		// get <Eform> tag value
		Node first = rootElement.getFirstChild();
		Node sibling = first.getNextSibling();
		Node finalNode = sibling.getFirstChild();
		String value = finalNode.getNodeValue();

		System.out.println(value);
		NodeList nodeList = document.getElementsByTagName("EleCaliculation");
		List<EleCaliculation> empList = new ArrayList<EleCaliculation>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			empList.add(getEleCaliculation(nodeList.item(i)));
		}

		EForm crf = eformDao.crfByName(value);
		List<EFormEleCaliculation> list = new ArrayList<>();
		// lets print Employee list information
		for (EleCaliculation emp : empList) {
			System.out.println(emp.toString());
			EFormEleCaliculation cec = new EFormEleCaliculation();
			cec.setResultField(emp.getResultField());
			cec.setCaliculation(emp.getRule());
			cec.setCrf(crf);
			cec.setCreatedOn(new Date());
			cec.setCreatedBy(username);
			list.add(cec);
		}

		eformDao.saveEFormEleCaliculationList(list);
		
	}

	private static EleCaliculation getEleCaliculation(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		EleCaliculation emp = new EleCaliculation();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			emp.setResultField(getTagValue("resultField", element));
			emp.setRule(getTagValue("rule", element));
//            emp.setAge(Integer.parseInt(getTagValue("age", element)));
		}

		return emp;
	}
	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	@Override
	public EForm changeEFormStatus(Long crfId, String username) {
		try {
			UserMaster user=eformDao.getUserMasterWithName(username);
			EForm crf = eformDao.getEForm(crfId);
			if (crf.isActive())
				crf.setActive(false);
			else {
				EForm old = eformDao.checkEForm(crf.getName());
				if (old == null)
					crf.setActive(true);
				else {
					old = new EForm();
					old.setId(0);
					old.setName(crf.getName());
					return old;
				}
			}
			crf.setUpdatedBy(user);
			crf.setUpdatedOn(new Date());
			eformDao.updateEForm(crf);
			return crf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserMaster getUserMasterWithName(String username) {
		return eformDao.getUserMasterWithName(username);
	}

	@Override
	public List<EFormRule> findAllEFormRules() {
		return eformDao.findAllEFormRules();
	}

	@Override
	public String groupEleSelect(Long id) {
		List<EFormGroupElement> list = eformDao.groupElemets(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='groupEleId' id='groupEleId' class='form-control' onchange='groupEleSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected'>--Select--</option>");
		for (EFormGroupElement ele : list) {
			sb.append("<option value='" + ele.getId() + "," + ele.getGroup().getMaxRows() + ","
					+ ele.getGroup().getMaxColumns() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String eformRuleElements(int rowId) {
		rowId++;
		List<EForm> list = eformDao.findAllEFormWithSubElemens();
		StringBuilder sb = new StringBuilder();
		sb.append("<tr id='AddRow" + rowId + "'>");
		sb.append("<td><select class='form-control' name='otherCrf" + rowId + "' id='otherCrf" + rowId
				+ "' class='form-control' onchange='otherCrf(this.id, this.value, " + rowId + ")'>");
		sb.append("<option value='-1' selected='selected'>--Select--</option>");
		for (EForm crf : list) {
			sb.append("<option value='" + crf.getId() + "'>" + crf.getName() + "</option>");
		}
		sb.append("</select><font color='red' id='otherCrf" + rowId + "msg'></font></td>");

		sb.append("<td id='secEleIdTd" + rowId + "'><select class='form-control' name='otherCrfSecEle" + rowId
				+ "' class='form-control' id='otherCrfSecEle" + rowId
				+ "' onchange='secEleSelection(this.id, this.value)'> ");
		sb.append("<option value='-1' selected='selected'>--Select--</option>");
		sb.append("</select><font color='red' id='otherCrfSecEle" + rowId + "msg'></font></td>");

		sb.append("<td id='groupEleIdTd" + rowId + "'><select class='form-control' name='otherCrfGroupEle" + rowId
				+ "' class='form-control' id='otherCrfGroupEle" + rowId + "'> ");
		sb.append("<option value='-1' selected='selected'>--Select--</option>");
		sb.append("</select><font color='red' id='otherCrfGroupEle" + rowId + "msg'></font></td>");

		sb.append("<td id='groupEleIdTdRow" + rowId + "'><select class='form-control' name='otherCrfGroupEleRowNo"
				+ rowId + "' class='form-control' id='otherCrfGroupEleRowNo" + rowId + "'> ");
		sb.append("<option value='-1' selected='selected'>--Select--</option>");
		sb.append("</select></td>");

		sb.append("<td><select class='form-control' name='otherCrfContion" + rowId
				+ "' class='form-control' id='otherCrfContion" + rowId + "'> ");
		sb.append("<option value='eq' selected='selected'>=</option>");
		sb.append("<option value='ne'>!=</option>");
		sb.append("<option value='le'>Less</option>");
		sb.append("<option value='leq'>Less and eq</option>");
		sb.append("<option value='gt'>Grater</option>");
		sb.append("<option value='gte'>grater and eq</option>");
		sb.append("</select></td>");

		sb.append("<td><select class='form-control' name='otherCrfNContion" + rowId
				+ "' class='form-control' id='otherCrfNContion" + rowId + "'> ");
		sb.append("<option value='and' selected='selected'>And</option>");
		sb.append("<option value='or'>or</option>");
		sb.append("</select></td>");

		sb.append("</tr>");
		return sb.toString();
	}

	@Override
	public String otherEFormSectionElements(Long id, int count) {
		List<EFormSectionElement> list = eformDao.sectionElemets(id);
		StringBuilder sb = new StringBuilder();
		sb.append("<select class='form-control' name='otherCrfSecEle" + count + "' id='otherCrfSecEle" + count
				+ "' class='form-control' onchange='othersecEleSelection(this.id, this.value, " + count + ")'>")
				.append("<option value='-1' selected='selected' >--Select--</option>");
		for (EFormSectionElement ele : list) {
			sb.append("<option value='" + ele.getId() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select> <font color='red' id='otherCrfSecEle" + count + "msg'></font>");
		return sb.toString();
	}

	@Override
	public String otherEFormGroupElements(Long id, int count) {
		List<EFormGroupElement> list = eformDao.groupElemets(id);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<select class='form-control' name='groupEleId' id='groupEleId' class='form-control' onchange='groupEleSelection(this.id, this.value)'>")
				.append("<option value='-1' selected='selected'>--Select--</option>");
		for (EFormGroupElement ele : list) {
			sb.append("<option value='" + ele.getId() + "," + ele.getGroup().getMaxRows() + ","
					+ ele.getGroup().getMaxColumns() + "'>" + ele.getName() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	@Override
	public String saveEFormRule(String username, HttpServletRequest request) {
		Map<Long, EForm> crfMap = new HashMap<>();
		Map<Long, EFormSectionElement> secEleMap = new HashMap<>();
		Map<Long, EFormGroupElement> crfGroupEleMap = new HashMap<>();
		String name = request.getParameter("name");
		EFormRule rule = new EFormRule();
		rule.setCreatedBy(username);
		rule.setCreatedOn(new Date());
		rule.setName(name);
		rule.setRuleDesc(request.getParameter("desc"));
		rule.setMessage(request.getParameter("message"));
		EForm crf = eformDao.getEForm(Long.parseLong(request.getParameter("crfid").toString()));
		crfMap.put(crf.getId(), crf);
		rule.setCrf(crf);
		if (!request.getParameter("secEleId").equals("-1")) {
			EFormSectionElement secEle = eformDao
					.sectionElement(Long.parseLong(request.getParameter("secEleId").toString()));
			rule.setSecEle(secEle);
			secEleMap.put(secEle.getId(), secEle);
		}
		if (!request.getParameter("groupEleId").equals("-1")) {
			String[] gidinfo = request.getParameter("groupEleId").toString().split(",");
			EFormGroupElement groupEle = eformDao.groupElement(Long.parseLong(gidinfo[0]));
			rule.setGroupEle(groupEle);
			crfGroupEleMap.put(groupEle.getId(), groupEle);

//			crfGroupEleMapRowCount.put(groupEle.getId(), Integer.parseInt(request.getParameter("groupEleId").toString()))
		}
		String compareWith = request.getParameter("compareWith");
		rule.setCompareWith(compareWith);

		rule.setValue1(request.getParameter("userInput"));
		rule.setCondtion1(request.getParameter("compareWithCondition"));
		rule.setNcondtion1(request.getParameter("compareWithConditionN"));
		rule.setValue2(request.getParameter("userInput2"));
		rule.setCondtion2(request.getParameter("compareWithCondition2"));
		rule.setNcondtion2(request.getParameter("compareWithConditionN2"));

		List<EFormRuleWithOther> list = new ArrayList<>();
		if (compareWith.equals("Other CRF Field")) {
			int newRows = Integer.parseInt(request.getParameter("newRows").toString());
			for (int i = 1; i <= newRows; i++) {

				if (request.getParameter("otherCrf" + i) != null) {
					EFormRuleWithOther crwo = new EFormRuleWithOther();
					crwo.setCrfRule(rule);
					crwo.setCondtion(request.getParameter("otherCrfContion" + i));
					crwo.setNcondtion(request.getParameter("otherCrfNContion" + i));
					if (!crfMap.containsKey(Long.parseLong(request.getParameter("otherCrf" + i).toString()))) {
						EForm ocrf = eformDao.getEForm(Long.parseLong(request.getParameter("otherCrf" + i).toString()));
						crfMap.put(ocrf.getId(), ocrf);
					}
					crwo.setCrf(crfMap.get(Long.parseLong(request.getParameter("otherCrf" + i).toString())));

					if (!request.getParameter("otherCrfSecEle" + i).equals("-1")) {
						if (!secEleMap
								.containsKey(Long.parseLong(request.getParameter("otherCrfSecEle" + i).toString()))) {
							EFormSectionElement osecEle = eformDao.sectionElement(
									Long.parseLong(request.getParameter("otherCrfSecEle" + i).toString()));
							secEleMap.put(osecEle.getId(), osecEle);
						}
						crwo.setSecEle(
								secEleMap.get(Long.parseLong(request.getParameter("otherCrfSecEle" + i).toString())));
					}

					if (!request.getParameter("otherCrfGroupEle" + i).equals("-1")) {
						String[] gidinfo = request.getParameter("otherCrfGroupEle" + i).toString().split(",");
						if (!crfGroupEleMap.containsKey(Long.parseLong(gidinfo[0]))) {
							EFormGroupElement osecEle = eformDao.groupElement(Long.parseLong(gidinfo[0]));
							crfGroupEleMap.put(osecEle.getId(), osecEle);
						}
						crwo.setGroupEle(crfGroupEleMap.get(Long.parseLong(gidinfo[0])));
						crwo.setRowNo(Integer.parseInt(request.getParameter("otherCrfGroupEleRowNo" + i)));
					}
					list.add(crwo);
				}
			}
		}
		rule.setOtherCrf(list);
		name = eformDao.saveEFormRule(rule);
		return name;
	}

	@Override
	public String eformRuleChangeStatus(Long id) {
		EFormRule rule = eformDao.eformRule(id);
		if (rule.isActive())
			rule.setActive(false);
		else
			rule.setActive(true);
		try {
			eformDao.updateEFormRule(rule);
			return rule.getName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String eformVisibilityChangeStatus(Long id) {
		EFormVisibility rule = eformDao.eformVisibility(id);
		if (rule.isStatus())
			rule.setStatus(false);
		else
			rule.setStatus(true);
		try {
			eformDao.updateUpdateVisibility(rule);
			return rule.getName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
