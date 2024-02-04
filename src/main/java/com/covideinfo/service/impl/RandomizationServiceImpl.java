package com.covideinfo.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.internal.resources.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.covideinfo.dao.RandomizationDao;
import com.covideinfo.dao.StudyDesignDao;
import com.covideinfo.dto.RandomizationViewDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.ProjectDetailsRandomization;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.RandomizationFileStatus;
import com.covideinfo.model.RandomizationReviewAudit;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.RandomizationService;
import com.covideinfo.service.UserService;

@Service("randomizationService")
public class RandomizationServiceImpl implements RandomizationService {

	@Autowired
	RandomizationDao randomizationDao;
	@Autowired
	UserService userService;
	@Autowired
	StudyDesignDao studyDesignDao;

	@Override
	public String saveUploadRandomization(CommonsMultipartFile file, HttpSession session, Long projectNoVal,
			String username, String commentsf) throws IOException {
		String flag = "error";
		String path = session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();

		try {
			// I have need noOfPeriods and noOfSubjects
			// StudyMaster sm=randomizationDao.getStudyMasterWithId(projectNoVal);
			Projects poject = randomizationDao.getProjectsWithProjectId(projectNoVal);
			ProjectsDetails noofsub = randomizationDao.getProjectsDetailsForNoofSubjectWithProject(poject);
			ProjectsDetails noofperiod = randomizationDao.getProjectsDetailsForNoofPeriodWithProject(poject);
			List<ProjectsDetails> randomizationCode = randomizationDao.getProjectsDetailsRandomizationCode(poject);
			List<String> codeType = new ArrayList<>();
			for (ProjectsDetails comet : randomizationCode) {
				codeType.add(comet.getFieldValue());
			}
			StatusMaster statusm = studyDesignDao.getStatusMasterForSubmit(StudyStatus.SUBMIT.toString());
			poject.setRandamizationStatus(statusm);
			// I have need randamizationCode Type Ex: A,B AND T,R
			/*
			 * List<TreatmentInfo> ti=randomizationDao.getTreatmentInfoWithStudyId(sm);
			 * List<String> treatmentcode=new ArrayList<>(); for(TreatmentInfo tid:ti) {
			 * treatmentcode.add(tid.getRandamizationCode()); }
			 */
			List<ProjectDetailsRandomization> pdrdata = new ArrayList<>();
			byte barr[] = file.getBytes();
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + filename));
			bout.write(barr);
			bout.flush();
			bout.close();

			FileInputStream fis = null;
			File fileforfis = new File(path + "/" + filename);
			fis = new FileInputStream(fileforfis);

			String fileExtensionName = filename.substring(filename.indexOf("."));
			// Check condition if the file is xlsx or xls file for Randomization
			if (fileExtensionName.equals(".xlsx") || fileExtensionName.equals(".xls")) {

				Workbook workbook = new XSSFWorkbook(fis);
				Sheet firstSheet = workbook.getSheetAt(0);

				Row rowa = firstSheet.getRow(1);
				Row rowb = firstSheet.getRow(2);
				int colz = rowb.getLastCellNum();
				Cell cella = rowa.getCell(0);
				String check = cella.getStringCellValue();
				if (check.equals("Subjects")) {
					int rowCount = firstSheet.getLastRowNum();
					if (rowCount - 1 == Integer.parseInt(noofsub.getFieldValue())) {
						// if(colz-2==Integer.parseInt(noofperiod.getFieldValue())) {
						List<String> peridType = new ArrayList<>();
						for (int i = 1; i < 2; i++) {
							Row row = firstSheet.getRow(i);
							for (int j = 0; j < row.getLastCellNum(); j++) {
								Cell cell = row.getCell(j);
								switch (j) {
								case 0: // field that represents string cell type
									// System.out.print(cell.getNumericCellValue() );
									// int va= (int) cell.getNumericCellValue();
									break;
								case 1: // field that represents number cell type
									System.out.print(cell.getStringCellValue());
									break;
								default:
									if (!peridType.contains(cell.getStringCellValue())) {
										peridType.add(cell.getStringCellValue());
									}
								}
							}
						}
						if (peridType.size() == Integer.parseInt(noofperiod.getFieldValue())) {
							for (int i = 2; i < rowCount + 1; i++) {
								Row row = firstSheet.getRow(i);
								Row row2 = firstSheet.getRow(1);
								ProjectDetailsRandomization pdr = null;
								pdr = new ProjectDetailsRandomization();
								String ram = "";
								for (int j = 0; j < row.getLastCellNum(); j++) {
									Cell cell = row.getCell(j);
									Cell cell2 = row2.getCell(j);

									switch (j) {
									case 0: // field that represents string cell type
										// System.out.print(cell.getNumericCellValue() );
										int va = (int) cell.getNumericCellValue();
										pdr.setSubjectNo("" + va);
										break;
									case 1: // field that represents number cell type
										System.out.print(cell.getStringCellValue());
										break;
									default:
										if (ram != "") {
											String datav = cell.getStringCellValue();
											String[] codess = datav.split(",");
											int siz = codess.length;
											String perval = "";
											if (cell2.getStringCellValue().equals("Period1")) {
												perval = "P1";
											}
											if (cell2.getStringCellValue().equals("Period2")) {
												perval = "P2";
											}
											if (cell2.getStringCellValue().equals("Period3")) {
												perval = "P3";
											}
											if (cell2.getStringCellValue().equals("Period4")) {
												perval = "P4";
											}
											if (cell2.getStringCellValue().equals("Period5")) {
												perval = "P5";
											}
											if (siz == 2) {
												for (int a = 0; a < siz; a++) {
													ram = ram + "," + codess[a] + "##" + perval;
												}
											} else {
												ram = ram + "," + cell.getStringCellValue() + "##" + perval;
											}

										} else {
											String datav = cell.getStringCellValue();
											String[] codess = datav.split(",");
											int siz = codess.length;
											String pervalTwo = "";
											if (cell2.getStringCellValue().equals("Period1")) {
												pervalTwo = "P1";
											}
											if (cell2.getStringCellValue().equals("Period2")) {
												pervalTwo = "P2";
											}
											if (cell2.getStringCellValue().equals("Period3")) {
												pervalTwo = "P3";
											}
											if (cell2.getStringCellValue().equals("Period4")) {
												pervalTwo = "P4";
											}
											if (cell2.getStringCellValue().equals("Period5")) {
												pervalTwo = "P5";
											}
											if (siz == 2) {
												for (int a = 0; a < siz; a++) {
													ram = cell.getStringCellValue() + "##" + pervalTwo;
												}
											} else {
												ram = cell.getStringCellValue() + "##" + pervalTwo;
											}

										}
										// System.out.println("ram:"+ram);
										pdr.setRandomizationCode(ram);
									}
								}
								pdrdata.add(pdr);
							}

							List<ProjectDetailsRandomization> pdrdataFinal = new ArrayList<>();
							for (ProjectDetailsRandomization pdpojo : pdrdata) {
								ProjectDetailsRandomization pdd = null;
								String[] code = pdpojo.getRandomizationCode().split(",");
								for (int n = 0; n < code.length; n++) {
									String[] finall = code[n].split("##");
									pdd = new ProjectDetailsRandomization();
									pdd.setSubjectNo(pdpojo.getSubjectNo());
									// pdd.setPeriod("P"+(n+1));
									pdd.setPeriod(finall[1]);
									pdd.setRandomizationCode(finall[0]);
									pdd.setProjectNo(projectNoVal);
									pdd.setStatus("Active");
									pdd.setComments(commentsf);
									pdd.setCreatedBy(username);
									pdd.setCreatedOn(new Date());
									pdrdataFinal.add(pdd);
								}

							}
							List<ProjectDetailsRandomization> randomList = null;
							randomList = randomizationDao.getRandomExitOrNot(projectNoVal);

							if (randomList.size() == 0) {
								// Check Subject Order And Code Type
								List<String> sub = new ArrayList<>();
								List<String> code = new ArrayList<>();
								for (ProjectDetailsRandomization ord : pdrdataFinal) {
									if (Integer.parseInt(ord.getSubjectNo()) < 10) {
										ord.setSubjectNo(0 + "" + ord.getSubjectNo());
									} else {
										ord.setSubjectNo(ord.getSubjectNo());
									}
									if (!sub.contains(ord.getSubjectNo())) {
										sub.add(ord.getSubjectNo());
									}
									String[] result = ord.getRandomizationCode().split(",");
									if (!code.contains(result[0])) {
										code.add(result[0]);
									}
								}

								boolean chek = true;

								int countp = 1;
								for (String aa : sub) {
									if (countp == Integer.parseInt(aa)) {

									} else {
										chek = false;
									}
									countp++;
								}
								boolean codeval = true;
								for (String ccc : code) {
									if (codeType.contains(ccc)) {
									} else {
										codeval = false;
									}
								}

								if (chek) {
									if (codeval) {
										UserMaster checkLoginUser = userService.findByUsername(username);
										DraftReviewStage rrs = randomizationDao
												.getRandomizationReviewStage(checkLoginUser.getRole());
										if (rrs != null) {
											RandomizationReviewAudit rra = new RandomizationReviewAudit();
											rra.setComment("nil");
											rra.setDate(new Date());
											rra.setInTheFlow(true);
											rra.setReviewState(rrs.getId());
											rra.setRole(checkLoginUser.getRole());
											rra.setStatus("IN APPROVAL");
											rra.setProjectId(projectNoVal);
											rra.setUser(checkLoginUser);
											poject.setRandamizationRole(rrs.getToRole().getId());

											RandomizationFileStatus rfs = new RandomizationFileStatus();
											rfs.setProjectId(projectNoVal);
											StatusMaster sta = studyDesignDao
													.getStatusMasterForSubmit(StudyStatus.INREVIEW.toString());
											rfs.setStatus(sta);
											rfs.setNoofSubject(noofsub.getFieldValue());
											rfs.setNoofPeriod(noofperiod.getFieldValue());
											rfs.setCreatedBy(checkLoginUser.getUsername());
											rfs.setCreatedOn(new Date());

											flag = randomizationDao.saveUploadRandomization(pdrdataFinal, rra, poject,
													rfs);
										} else {
											flag = "reviewStageCheck";
										}
									} else {
										flag = "randomizationCodeCheck";
									}

								} else {
									flag = "subjectOrdeOrCodeType";
								}

							} else {
								boolean changStatus = randomizationDao.updateStatusRandomization(randomList, username);
								List<ProjectDetailsRandomization> addData = new ArrayList<>();
								List<ProjectDetailsRandomization> updateData = new ArrayList<>();

								for (ProjectDetailsRandomization ch : pdrdataFinal) {
									if (Integer.parseInt(ch.getSubjectNo()) < 10) {
										ch.setSubjectNo(0 + "" + ch.getSubjectNo());
									} else {
										ch.setSubjectNo(ch.getSubjectNo());
									}
									ProjectDetailsRandomization exi = randomizationDao
											.getProjectDetailsRandomizationWithSubjectAndPeriod(ch);
									if (exi == null) {
										addData.add(ch);
									} else {
										exi.setUpdatedBy(username);
										exi.setUpdatedOn(new Date());
										exi.setRandomizationCode(ch.getRandomizationCode());
										exi.setStatus("Active");
										exi.setComments(commentsf);
										updateData.add(exi);
									}
								}
								// Check Subject Order And Code Type
								List<String> sub = new ArrayList<>();
								List<String> code = new ArrayList<>();
								for (ProjectDetailsRandomization ord : pdrdataFinal) {
									if (!sub.contains(ord.getSubjectNo()))
										sub.add(ord.getSubjectNo());
									String[] result = ord.getRandomizationCode().split(",");
									if (!code.contains(result[0])) {
										code.add(result[0]);
									}
								}

								boolean chek = true;
								int countp = 1;
								for (String aa : sub) {
									if (countp == Integer.parseInt(aa)) {

									} else {
										chek = false;
									}
									countp++;
								}
								boolean codeval = true;
								for (String ccc : code) {
									if (codeType.contains(ccc)) {
									} else {
										codeval = false;
									}
								}
								if (chek) {
									if (codeval) {
										UserMaster checkLoginUser = userService.findByUsername(username);
										DraftReviewStage rrs = randomizationDao
												.getRandomizationReviewStage(checkLoginUser.getRole());
										if (rrs != null) {
											RandomizationReviewAudit rra = new RandomizationReviewAudit();
											rra.setComment("nil");
											rra.setDate(new Date());
											rra.setInTheFlow(true);
											rra.setReviewState(rrs.getId());
											rra.setRole(checkLoginUser.getRole());
											rra.setStatus("IN APPROVAL");
											rra.setProjectId(projectNoVal);
											rra.setUser(checkLoginUser);
											poject.setRandamizationRole(rrs.getToRole().getId());

											RandomizationFileStatus rfsupdate = null;
											rfsupdate = studyDesignDao
													.getRandomizationFileStatusProWithId(projectNoVal);
											if (rfsupdate != null) {
												StatusMaster sta1 = studyDesignDao
														.getStatusMasterForSubmit(StudyStatus.INREVIEW.toString());
												rfsupdate.setStatus(sta1);
												rfsupdate.setNoofSubject(noofsub.getFieldValue());
												rfsupdate.setNoofPeriod(noofperiod.getFieldValue());
												rfsupdate.setUpdateBy(checkLoginUser.getUsername());
												rfsupdate.setUpdateOn(new Date());
											}
											flag = randomizationDao.updateUploadRandomization(addData, updateData, rra,
													poject, rfsupdate);
										} else {
											flag = "reviewStageCheck";
										}
									} else {
										flag = "randomizationCodeCheck";
									}
								} else {
									flag = "subjectOrdeOrCodeType";
								}
							}
						} else {
							flag = "noofPeriodNotMatch";
						}

					} else {
						flag = "noofSubjectNotMatch";
					}
				} else {
					flag = "error";
				}
			}
			// Check condition if the file is pdf file for Randomization
			if (fileExtensionName.equals(".pdf")) {
				// Loading an existing document
				PDDocument document = Loader.loadPDF(fis);
				int numberOfPages = document.getNumberOfPages();
				PDFTextStripper pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(numberOfPages);

				// load all lines into a string
				String pages = pdfStripper.getText(document);

				// split by detecting newline
				String[] lines = pages.split("\r\n|\r|\n");
				int size = lines.length;
				String[] datab = lines[1].split(" ");
				// if(size-2==Integer.parseInt(noofsub.getFieldValue())) {
				// if(datab.length-2==Integer.parseInt(noofperiod.getFieldValue())) {
				int count = 1; // Just to indicate line number
				boolean flag1 = false;

				List<ProjectDetailsRandomization> sampl = new ArrayList<>();
				for (String temp : lines) {

					if (count > 30 && count != size + 1) {

						ProjectDetailsRandomization pdd = null;
						pdd = new ProjectDetailsRandomization();
						String[] data = temp.split(" ");
						String ram = "";
						int sub = 0;
						try {
							sub = Integer.parseInt(data[0]);
						} catch (Exception e) {
							flag1 = false;
							// Type Miss match flag false
						}
						if (flag1) {
							if (sub > 0 && sub < 300) {
								for (int i = 0; i < data.length; i++) {

									if (i == 0) {
										// subject add
										pdd.setSubjectNo(data[i]);
									}
									if (i != 0 && i != 1 && i != 2) {
										// All periods add
										if (ram != "") {
											ram = ram + "," + data[i];
										} else {
											ram = data[i];
										}
										pdd.setRandomizationCode(ram);
									}
								}
							}
						}
						// One time is true
						if (data[0].equals("Description")) {
							flag1 = true;
						}
						System.out.println(pdd.getSubjectNo() + "::" + pdd.getRandomizationCode());
						if (pdd != null && pdd.getRandomizationCode() != null) {
							sampl.add(pdd);
						}

					}

					count++;
				}

				List<ProjectDetailsRandomization> pdrdataFinal = new ArrayList<>();
				for (ProjectDetailsRandomization pdpojo : sampl) {
					ProjectDetailsRandomization pdd = null;
					String[] code = pdpojo.getRandomizationCode().split(",");
					for (int n = 0; n < code.length; n++) {
						pdd = new ProjectDetailsRandomization();
						pdd.setSubjectNo(pdpojo.getSubjectNo());
						pdd.setPeriod("P" + (n + 1));
						pdd.setRandomizationCode(code[n]);
						pdd.setProjectNo(projectNoVal);
						pdd.setStatus("Active");
						pdd.setCreatedBy(username);
						pdd.setCreatedOn(new Date());
						pdd.setComments(commentsf);
						pdrdataFinal.add(pdd);
					}

				}
				List<ProjectDetailsRandomization> randomList = null;
				randomList = randomizationDao.getRandomExitOrNot(projectNoVal);

				if (randomList.size() == 0) {
					// Check Subject Order And Code Type
					List<String> sub = new ArrayList<>();
					List<String> code = new ArrayList<>();
					for (ProjectDetailsRandomization ord : pdrdataFinal) {
						if (Integer.parseInt(ord.getSubjectNo()) < 10) {
							ord.setSubjectNo(0 + "" + ord.getSubjectNo());
						} else {
							ord.setSubjectNo(ord.getSubjectNo());
						}

						if (!sub.contains(ord.getSubjectNo()))
							sub.add(ord.getSubjectNo());
						String[] result = ord.getRandomizationCode().split(",");
						if (!code.contains(result[0])) {
							code.add(result[0]);
						}
					}

					boolean chek = true;
					int countp = 1;
					for (String aa : sub) {
						if (countp == Integer.parseInt(aa)) {

						} else {
							chek = false;
						}
						countp++;
					}
					boolean codeval = true;
					for (String ccc : code) {
						if (codeType.contains(ccc)) {
						} else {
							codeval = false;
						}
					}
					// No of Subject Check
					if (sampl.size() == Integer.parseInt(noofsub.getFieldValue())) {
						String noofp = sampl.get(0).getRandomizationCode();
						String[] periodSize = noofp.split(",");
						int psize = periodSize.length;
						// No of Periods Check
						if (psize == Integer.parseInt(noofperiod.getFieldValue())) {
							// Subject Order Check
							if (chek) {
								// Treatment Type Check
								if (codeval) {
									UserMaster checkLoginUser = userService.findByUsername(username);
									DraftReviewStage rrs = randomizationDao
											.getRandomizationReviewStage(checkLoginUser.getRole());
									if (rrs != null) {
										RandomizationReviewAudit rra = new RandomizationReviewAudit();
										rra.setComment("nil");
										rra.setDate(new Date());
										rra.setInTheFlow(true);
										rra.setReviewState(rrs.getId());
										rra.setRole(checkLoginUser.getRole());
										rra.setStatus("IN APPROVAL");
										rra.setProjectId(projectNoVal);
										rra.setUser(checkLoginUser);
										poject.setRandamizationRole(rrs.getToRole().getId());

										RandomizationFileStatus rfs = new RandomizationFileStatus();
										rfs.setProjectId(projectNoVal);
										StatusMaster sta = studyDesignDao
												.getStatusMasterForSubmit(StudyStatus.INREVIEW.toString());
										rfs.setStatus(sta);
										rfs.setNoofSubject(noofsub.getFieldValue());
										rfs.setNoofPeriod(noofperiod.getFieldValue());
										rfs.setCreatedBy(checkLoginUser.getUsername());
										rfs.setCreatedOn(new Date());

										flag = randomizationDao.saveUploadRandomization(pdrdataFinal, rra, poject, rfs);
									} else {
										flag = "reviewStageCheck";
									}
								} else {
									flag = "randomizationCodeCheck";
								}
							} else {
								flag = "subjectOrdeOrCodeType";
							}
						} else {
							flag = "noofPeriodNotMatch";
						}
					} else {
						flag = "noofSubjectNotMatch";
					}
				} else {
					boolean changStatus = randomizationDao.updateStatusRandomization(randomList, username);
					List<ProjectDetailsRandomization> addData = new ArrayList<>();
					List<ProjectDetailsRandomization> updateData = new ArrayList<>();

					for (ProjectDetailsRandomization ch : pdrdataFinal) {
						if (Integer.parseInt(ch.getSubjectNo()) < 10) {
							ch.setSubjectNo(0 + "" + ch.getSubjectNo());
						} else {
							ch.setSubjectNo(ch.getSubjectNo());
						}
						ProjectDetailsRandomization exi = randomizationDao
								.getProjectDetailsRandomizationWithSubjectAndPeriod(ch);
						if (exi == null) {
							addData.add(ch);
						} else {
							exi.setUpdatedBy(username);
							exi.setUpdatedOn(new Date());
							exi.setRandomizationCode(ch.getRandomizationCode());
							exi.setStatus("Active");
							exi.setComments(commentsf);
							updateData.add(exi);
						}
					}

					UserMaster checkLoginUser = userService.findByUsername(username);
					DraftReviewStage rrs = randomizationDao.getRandomizationReviewStage(checkLoginUser.getRole());

					// Check Subject Order And Code Type
					List<String> sub = new ArrayList<>();
					List<String> code = new ArrayList<>();
					for (ProjectDetailsRandomization ord : pdrdataFinal) {
						if (!sub.contains(ord.getSubjectNo()))
							sub.add(ord.getSubjectNo());
						String[] result = ord.getRandomizationCode().split(",");
						if (!code.contains(result[0])) {
							code.add(result[0]);
						}
					}

					boolean chek = true;
					int countp = 1;
					for (String aa : sub) {
						if (countp == Integer.parseInt(aa)) {

						} else {
							chek = false;
						}
						countp++;
					}
					boolean codeval = true;
					for (String ccc : code) {
						if (codeType.contains(ccc)) {
						} else {
							codeval = false;
						}
					}
					// No of Subject Check
					if (sampl.size() == Integer.parseInt(noofsub.getFieldValue())) {
						String noofp = sampl.get(0).getRandomizationCode();
						String[] periodSize = noofp.split(",");
						int psize = periodSize.length;
						// No of Periods Check
						if (psize == Integer.parseInt(noofperiod.getFieldValue())) {
							// Subject Order Check
							if (chek) {
								// Treatment Type Check
								if (codeval) {
									if (rrs != null) {
										RandomizationReviewAudit rra = new RandomizationReviewAudit();
										rra.setComment("nil");
										rra.setDate(new Date());
										rra.setInTheFlow(true);
										rra.setReviewState(rrs.getId());
										rra.setRole(checkLoginUser.getRole());
										rra.setStatus("IN APPROVAL");
										rra.setProjectId(projectNoVal);
										rra.setUser(checkLoginUser);
										poject.setRandamizationRole(rrs.getToRole().getId());

										RandomizationFileStatus rfsupdate = null;
										rfsupdate = studyDesignDao.getRandomizationFileStatusProWithId(projectNoVal);
										StatusMaster sta = studyDesignDao
												.getStatusMasterForSubmit(StudyStatus.INREVIEW.toString());
										rfsupdate.setStatus(sta);
										rfsupdate.setNoofSubject(noofsub.getFieldValue());
										rfsupdate.setNoofPeriod(noofperiod.getFieldValue());
										rfsupdate.setUpdateBy(checkLoginUser.getUsername());
										rfsupdate.setUpdateOn(new Date());

										flag = randomizationDao.updateUploadRandomization(addData, updateData, rra,
												poject, rfsupdate);
									} else {
										flag = "reviewStageCheck";
									}
								} else {
									flag = "randomizationCodeCheck";
								}
							} else {
								flag = "subjectOrdeOrCodeType";
							}
						} else {
							flag = "noofPeriodNotMatch";
						}
					} else {
						flag = "noofSubjectNotMatch";
					}

				}
				/*
				 * }else { flag="noofPeriodNotMatch"; } }else { flag="noofSubjectNotMatch"; }
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = "error";
		}
		return flag;
	}

	@Override
	public List<Projects> getAllProjectsList() {
		return randomizationDao.getAllProjectsList();
	}

	@Override
	public List<StudyMaster> getAllStudyList() {
		return randomizationDao.getAllStudyList();
	}

	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithStudyId(Long id) {
		return randomizationDao.getProjectDetailsRandomizationWithStudyId(id);
	}

	@Override
	public List<RandomizationReviewAudit> getRandomizationReviewAuditWithStudyId(Long id) {
		return randomizationDao.getRandomizationReviewAuditWithStudyId(id);
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithreviewState(long reviewState) {
		return randomizationDao.getRandomizationReviewStageWithreviewState(reviewState);
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithTypeFromRole(RoleMaster fromRole, String approvalType,
			long l) {
		return randomizationDao.getRandomizationReviewStageWithTypeFromRole(fromRole, approvalType, l);
	}

	@Override
	public boolean saveRandomizationApproval(DraftReviewStage stage, Long id, UserMaster checkLoginUser,
			String commentval, String approvalType, Projects poject, RandomizationFileStatus rfsupdate) {
		boolean flag = false;
		RandomizationReviewAudit rra = new RandomizationReviewAudit();
		rra.setComment(commentval);
		rra.setDate(new Date());
		rra.setInTheFlow(true);
		rra.setReviewState(stage.getId());
		rra.setRole(checkLoginUser.getRole());
		rra.setStatus(approvalType);
		rra.setProjectId(id);
		rra.setUser(checkLoginUser);
		flag = randomizationDao.saveRandomizationApproval(rra, poject, rfsupdate);

		return flag;
	}

	@Override
	public boolean saveRandomizationApprovalFinal(DraftReviewStage stage, Long id, UserMaster checkLoginUser,
			String commentsval, String approvalType, List<ProjectDetailsRandomization> randomList, Projects poject,
			long studyid) {
		boolean flag = false;
		RandomizationReviewAudit rra = new RandomizationReviewAudit();
		rra.setComment(commentsval);
		rra.setDate(new Date());
		rra.setInTheFlow(true);
		rra.setReviewState(stage.getId());
		rra.setRole(checkLoginUser.getRole());
		rra.setStatus(approvalType);
		rra.setProjectId(id);
		rra.setUser(checkLoginUser);

		List<SubjectRandamization> subjectRamList = new ArrayList<>();
		List<SubjectRandamization> subjectRamUpdate = new ArrayList<>();

		for (ProjectDetailsRandomization rd : randomList) {
			SubjectRandamization sr = new SubjectRandamization();
			StudyPeriodMaster pd = randomizationDao.getStudyPeriodMasterWithStudyAndPeriodName(studyid, rd.getPeriod());
			TreatmentInfo ti = randomizationDao.getTreatmentInfoRamdomizationCode(rd.getRandomizationCode(), studyid);
			SubjectRandamization sbexi = randomizationDao.getSubjectRandamizationExitCheck(pd, rd.getSubjectNo(), ti);
			if (sbexi != null) {
				sbexi.setUpdatedBy(checkLoginUser.getUsername());
				sbexi.setUpdatedOn(new Date());
				sbexi.setRadamizationCode(rd.getRandomizationCode());
				sbexi.setTreatmentInfo(ti);
				subjectRamUpdate.add(sbexi);
			} else {
				sr.setPeriod(pd);
				sr.setTreatmentInfo(ti);
				sr.setSubjectNo(rd.getSubjectNo());
				sr.setRadamizationCode(rd.getRandomizationCode());
				sr.setCretedBy(checkLoginUser.getUsername());
				sr.setCreatedOn(new Date());
				subjectRamList.add(sr);
			}

		}

		flag = randomizationDao.saveRandomizationApprovalFinal(rra, subjectRamList, subjectRamUpdate, poject, studyid);

		return flag;
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageFirsyRow(Long rid) {
		return randomizationDao.getRandomizationReviewStageFirsyRow(rid);
	}

	@Override
	public Projects getProjectsWithProjectNo(String projectNo) {
		// TODO Auto-generated method stub
		return randomizationDao.getProjectsWithProjectNo(projectNo);
	}

	@Override
	public List<StudyMaster> getStudyMasterForRandomization(List<String> projectNo) {
		return randomizationDao.getStudyMasterForRandomization(projectNo);
	}

	@Override
	public List<Projects> getProjectForRandomization() {
		return randomizationDao.getProjectForRandomization();
	}

	@Override
	public List<Projects> getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects() {
		return randomizationDao.getProjectListOnlySubjetcsAndPeriodsAndRandomizationInputCompletedProjects();
	}

	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationWithProjectId(Long id) {
		return randomizationDao.getProjectDetailsRandomizationWithProjectId(id);
	}

	@Override
	public List<RandomizationReviewAudit> getRandomizationReviewAuditWithProjectId(Long id) {
		return randomizationDao.getRandomizationReviewAuditWithProjectId(id);
	}

	@Override
	public Projects getProjectsWithProjectId(Long id) {
		return randomizationDao.getProjectsWithProjectId(id);
	}

	@Override
	public List<ProjectDetailsRandomization> getProjectDetailsRandomizationForSplit(RandomizationViewDto data) {
		return randomizationDao.getProjectDetailsRandomizationForSplit(data);
	}

	@Override
	public StudyMaster getStudyMasterWithProjectNo(String projectNo) {
		return randomizationDao.getStudyMasterWithProjectNo(projectNo);
	}

	@Override
	public ProjectsDetails getProjectsDetailsWithNoofPeriods(Long id) {
		return randomizationDao.getProjectsDetailsWithNoofPeriods(id);
	}

	@Override
	public DraftReviewStage getRandomizationReviewStageWithTypeAndFromRole(RoleMaster toRole, String approvalType) {
		return randomizationDao.getRandomizationReviewStageWithTypeAndFromRole(toRole, approvalType);
	}
}
