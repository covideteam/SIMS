package com.covideinfo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.UserCreationDao;
import com.covideinfo.dto.UsersExportDto;
import com.covideinfo.model.ApplicationConfiguration;
import com.covideinfo.model.DepartmentMaster;
import com.covideinfo.model.RoleMaster;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.UserMaster;
import com.covideinfo.pdf.report.HeaderAndFooterForExportTableAdv;
import com.covideinfo.service.UserCreationService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("userCreationService")
public class UserCreationServiceImpl implements UserCreationService {
	
	@Autowired
	UserCreationDao userCreationDao;

	@Override
	public List<DepartmentMaster> getDepartmentMasterList() {
		return userCreationDao.getDepartmentMasterList();
	}

	@Override
	public List<RoleMaster> getRolesMasterRecordsList() {
		return userCreationDao.getRolesMasterRecordsList();
	}

	@Override
	public long saveUser(String username, UserMaster user, String passwordExpireDays,String email) {
		long no = 0;
		try {
			UserMaster um=userCreationDao.getUserMasterWithName(username);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setCreatedBy(um);
			user.setCreatedOn(new Date());
			user.setEmail(email);
			//user.setConfirmPassword(confirmPassword);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt(passwordExpireDays)); // Expire Date 60 days
			Date nextDate = cal.getTime();
			cal.setTime(new Date());
			user.setAccountexprie(nextDate);
			user.setAccountNotDisable(true);
			user.setAccountNotLock(true);
			user.setPwdChangeCundition(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if(user.getTranPassword() != null && !user.getTranPassword().equals(""))
				user.setTranPassword(passwordEncoder.encode(user.getTranPassword()));
			no = userCreationDao.saveLoginUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return no;
	}

	@Override
	public UserMaster checkUserDuplication(String userId) {
		return userCreationDao.checkUserDuplication(userId);
	}

	@Override
	public List<UserMaster> getUserMasterList() {
		return userCreationDao.getUserMasterList();
	}

	@Override
	public UserMaster getUserMasterWithId(Long id) {
		return userCreationDao.getUserMasterWithId(id);
	}

	@Override
	public boolean userUpdate(String username, UserMaster um) {
		return userCreationDao.userUpdate(um);
	}

	@Override
	public List<UserMaster> findOnlyAdminRoles() {
		return userCreationDao.findOnlyAdminRoles();
	}

	@Override
	public List<UserMaster> findRolesWithOutAdminRoles() {
		return userCreationDao.findRolesWithOutAdminRoles();
	}


	@Override
	public boolean updateUserStatus(UserMaster um) {
		return userCreationDao.updateUserStatus(um);
	}

	@Override
	public List<RoleMaster> getRolesMasterRecordsListWithOutAdmin(RoleMaster role) {
		return userCreationDao.getRolesMasterRecordsListWithOutAdmin(role);
	}

	@Override
	public RoleMaster getRoleMasterWithId(Long rid) {
		return userCreationDao.getRoleMasterWithId(rid);
	}

	@Override
	public List<RoleMaster> findOnlyAdminRolesForRoles() {
		return userCreationDao.findOnlyAdminRolesForRoles();
	}

	@Override
	public List<RoleMaster> findRolesWithOutAdminRolesForRoles() {
		return userCreationDao.findRolesWithOutAdminRolesForRoles();
	}

	@Override
	public List<UserMaster> findUserListAll() {
		return userCreationDao.findUserListAll();
	}

	@Override
	public boolean updateRoleStatus(RoleMaster rm) {
		return userCreationDao.updateRoleStatus(rm);
	}

	@Override
	public List<RoleMaster> getRolesMasterRecordsListOnlyActive(StatusMaster stm) {
		return userCreationDao.getRolesMasterRecordsListOnlyActive(stm);
	}

	@Override
	public List<UserMaster> checkUsersIsAvelableInThisRole(Long value) {
		return userCreationDao.checkUsersIsAvelableInThisRole(value);
	}

	@Override
	public String generateUsersListPdf(HttpServletRequest request, HttpServletResponse response, Long userId, 
				String dateFormat, MessageSource messageSource, Locale currentLocale) {
		List<UserMaster> usersList = null;
		ApplicationConfiguration appConfig = null;
		UsersExportDto userExportDto = null;
		UserMaster user = null;
		String img = "";
		String filepath = "";
		try {
			userExportDto = userCreationDao.getuserExportDetails(userId);
			if(userExportDto != null) {
				appConfig = userExportDto.getAppConfig();
				usersList = userExportDto.getUsersList();
				user = userExportDto.getUser();
			}
			if(appConfig != null) {
				if(appConfig.getConfigCode().equals("ADV")) 
					img = request.getServletContext().getRealPath("/static/images/company.PNG");
				else 
					img = request.getServletContext().getRealPath("/static/images/AvantSanteLog.jpg");
			}else
				img = request.getServletContext().getRealPath("/static/images/company.PNG");
			
			String realPath = request.getSession().getServletContext().getRealPath("/");
			
			Font regular = new Font(FontFamily.TIMES_ROMAN, 10);
			Font heading = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			Font headTbFont = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.WHITE);
			BaseColor bgColor = new BaseColor(52,152,219);// #e6e6e6
			
			String path = realPath+"//PdfsExports";
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			filepath = path+"/Users.pdf";
//			response.setContentType("application/pdf");
//		    OutputStream out = response.getOutputStream();
			Document document = new Document();
//		    document.setPageSize(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.addTitle("Users");
			document.setPageSize(PageSize.A4);
//			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(40, 40, 20, 90); //A4
//			document.setMargins(10f, 10f, 100f, 0f);//A4 Rotation
			document.setMarginMirroring(false);
			HeaderAndFooterForExportTableAdv event1 = new HeaderAndFooterForExportTableAdv(img, user, dateFormat, regular, heading);
			writer.setPageEvent(event1);
			
			document.open();
			
			PdfPTable exportTab = new PdfPTable(6);
			exportTab.setWidthPercentage(100f);
			PdfPCell cell = null;
			String headStr = "";
			
		
		    headStr =messageSource.getMessage("label.userCrName", null,currentLocale);
			cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    exportTab.addCell(cell);
		    
		    headStr =messageSource.getMessage("label.userCrFullName", null,currentLocale);
		    cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    exportTab.addCell(cell);
		    
		    headStr =messageSource.getMessage("label.userCrRole", null,currentLocale);
		    cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    exportTab.addCell(cell);
		    
		    headStr =messageSource.getMessage("label.createdBy", null,currentLocale);
		    cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    exportTab.addCell(cell);
		    
		    headStr =messageSource.getMessage("label.createdOn", null,currentLocale);
		    cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    exportTab.addCell(cell);
		    
		    headStr =messageSource.getMessage("label.randomizationStatus", null,currentLocale);
		    cell = new PdfPCell(new Phrase(headStr, headTbFont));
		    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
		    cell.setBackgroundColor(bgColor);//#B3E2E7
		    cell.setPadding(7f);
		    cell.setNoWrap(false);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		    exportTab.addCell(cell);
		    exportTab.getDefaultCell().setBackgroundColor(bgColor);
		    
//		    userTab.getDefaultCell().setBackgroundColor(bgColor);
			if(usersList != null && usersList.size() > 0) {
				for(UserMaster um : usersList) {
					cell = new PdfPCell(new Phrase(um.getUsername(), regular));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    exportTab.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase(um.getFullName(), regular));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    exportTab.addCell(cell);
				    
				    cell = new PdfPCell(new Phrase(um.getRole().getRole(), regular));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    exportTab.addCell(cell);
				    
				    String userName = "";
				    if(um.getCreatedBy() != null)
				    	userName = um.getCreatedBy().getUsername();
				    cell = new PdfPCell(new Phrase(userName, regular));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    exportTab.addCell(cell);
				    
				    
				    String date = "";
				    if(um.getCreatedOn() != null) {
				    	 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				    	date = sdf.format(um.getCreatedOn());
				    }
				    cell = new PdfPCell(new Phrase(date, regular));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    exportTab.addCell(cell);
				    
				    String status ="Active";
				    if(um.isAccountNotDisable() == false && um.isAccountNotLock() == false)
				    	status = messageSource.getMessage("label.user.inact", null,currentLocale);
				    
				    cell = new PdfPCell(new Phrase(status, heading));
				    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
				    cell.setPadding(7f);
				    cell.setNoWrap(false);
				    cell.setVerticalAlignment(Element.ALIGN_CENTER);
				    exportTab.addCell(cell);
				}
			}else {
				headStr =messageSource.getMessage("label.noData", null,currentLocale);
			    cell = new PdfPCell(new Phrase(headStr, heading));
			    cell.setBorderColor(new BaseColor(73, 74, 82)); //##494c52
			    cell.setPadding(7f);
			    cell.setNoWrap(false);
			    cell.setColspan(7);
			    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    cell.setVerticalAlignment(Element.ALIGN_CENTER);
			    exportTab.addCell(cell);
//			    document.add(exportTab);
			}
//			userTab.setHeaderRows(2);
//			out.flush();
			exportTab.setHeaderRows(2);
		    document.add(exportTab);
			document.close();
//			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

}
