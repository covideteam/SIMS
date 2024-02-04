package com.covideinfo.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.covideinfo.clinical.dao.ClinicalDao;
import com.covideinfo.dao.StudyCreationDao;
import com.covideinfo.dao.StudyDao;
import com.covideinfo.dao.UserDao;
import com.covideinfo.dto.CentrifugationDateDto;
import com.covideinfo.dto.SeggrigationDataDto;
import com.covideinfo.dummy.TempCentrifuge;
import com.covideinfo.model.CentrifugationData;
import com.covideinfo.model.CentrifugationDataMaster;
import com.covideinfo.model.LoadedVacutinersInCentrifuge;
import com.covideinfo.model.RackWithVials;
import com.covideinfo.model.SampleContainer;
import com.covideinfo.model.SampleContainerVials;
import com.covideinfo.model.SampleStorageData;
import com.covideinfo.model.SampleStorageDataRack;
import com.covideinfo.model.SampleStorateDataMaster;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.SubejectDosePerameter;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectECGTimePoints;
import com.covideinfo.model.SubjectMealsTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePoints;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectSampleSeparationTimePointsData;
import com.covideinfo.model.SubjectVitalTimePoints;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VialSeparationData;
import com.covideinfo.model.Volunteer;

@Service("studyClinicalBo")
@Transactional
@PropertySource(value = { "classpath:application.properties" })
public class StudyClinicalBo {
	@Autowired
	private StudyCreationDao studyCreationDao;
	@Autowired
	private Environment environment;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ClinicalDao clinicalDao;
	@Autowired
	private StudyDao studyDao;

	public List<StudyPeriodMaster> studyPeriods(StudyMaster sm) {
		// TODO Auto-generated method stub
		return studyCreationDao.allPeriodsExceptAdmission(sm);
	}

	public List<Volunteer> studyAllSubjects(StudyMaster sm) {
		// TODO Auto-generated method stub
		return studyCreationDao.studyAllSubjects(sm);
	}

	public List<SubjectSampleCollectionTimePoints> subjectSampleCollectionTimePointsSubjectData(Long periodId,
			int volid) {
		// TODO Auto-generated method stub
		return studyCreationDao.subjectSampleCollectionTimePointsSubjectData(periodId, volid);
	}

	public StudyPeriodMaster studyPeriod(Long periodId) {
		// TODO Auto-generated method stub
		return studyCreationDao.studyPeriod(periodId);
	}

	public Volunteer volunteerBySeqNo(StudyMaster sm, int seqNo) {
		// TODO Auto-generated method stub
		return studyCreationDao.volunteerBySeqNo(sm, seqNo);
	}

	public SubjectDoseTimePoints subjectDoseTimePoints(Long periodId, int volid) {
		// TODO Auto-generated method stub
		return studyCreationDao.subjectDoseTimePoints(periodId, volid);
	}

	public List<SubjectDoseTimePoints> subjectAllDoseTimePoints(SubjectDoseTimePoints sdtp) {
		return studyCreationDao.subjectAllDoseTimePoints(sdtp);
	}

	public List<SubejectDosePerameter> subejectDosePerameter(SubjectDoseTimePoints sdtp) {
		// TODO Auto-generated method stub
		return clinicalDao.subejectDosePerameter(sdtp);
	}

	public List<SubjectVitalTimePoints> subjectVitalTimePoints(Long periodId, int volid) {
		return clinicalDao.subjectVitalTimePoints(periodId, volid);
	}

	public List<SubjectSampleSeparationTimePointsData> subjectSampleSeparationTimePointsData(Long periodId, int volid) {

		return clinicalDao.subjectSampleSeparationTimePointsData(periodId, volid);
	}

	public Map<Integer, List<SubjectMealsTimePoints>> subjectMealsTimePoints(Long periodId, int volid,
			boolean daywise) {
		return clinicalDao.subjectMealsTimePoints(periodId, volid, daywise);
	}

	public Map<Integer, List<SubjectECGTimePoints>> subjectEcgTimePoints(Long periodId, int volid, boolean daywise) {
		return clinicalDao.subjectEcgTimePoints(periodId, volid, daywise);
	}

	public Long centrifugationStartSave(Long centrifugeId, Long sampleCentrifugationId, String runningTime,
			String runningTimeWithSeconds, String instrumentBarcode, String instrumentScanTime, String speed,
			String processTime, String temperature, String condition, String vacutainers, Long userId) {
//		UserMaster user = userDao.findById(userId);
//		SimpleDateFormat sd = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
//		Map<String, String> vacutainer = new HashMap<>();
//		Map<String, SubjectSampleCollectionTimePoints> vacutainerRecard = new HashMap<>();
//		Map<String, SampleTimePoints> vacutainerTimePoint = new HashMap<>();
//		Map<Long, SampleTimePoints> vacutainerTimePointById = new HashMap<>();
//		List<TempCentrifuge> temp = new ArrayList<>();
//		String[] vac = vacutainers.split("\\,");
//		for (String s : vac) {
//			String[] v = s.split("\\--");
//			vacutainer.put(v[0], v[1]);
//			SubjectSampleCollectionTimePoints ssctp = clinicalDao.subjectSampleCollectionTimePoints(v[0]);
//			vacutainerRecard.put(v[0], ssctp);
//			vacutainerTimePoint.put(v[0], ssctp.getSampleTimePointId());
//			vacutainerTimePointById.put(ssctp.getSampleTimePointId().getId(), ssctp.getSampleTimePointId());
//			Long studyId = ssctp.getStudy().getId();
//			Long periodId = ssctp.getPeriod().getId();
//			Long timePointId = ssctp.getSampleTimePointId().getId();
//			boolean flag = true;
//			for (TempCentrifuge t : temp) {
//				if (studyId == t.getStudyId() && periodId == t.getPeriodId() && timePointId == t.getTimePotnId()) {
//					Map<String, String> vacMap = t.getVacutainer();
//					vacMap.put(v[0], v[1]);
//					t.setVacutainer(vacMap);
//					flag = false;
//				}
//			}
//			if (flag) {
//				Map<String, String> vacMap = new HashMap<>();
//				vacMap.put(v[0], v[1]);
//				temp.add(new TempCentrifuge(studyId, periodId, timePointId, vacMap));
//			}
//		}
//		CentrifugationDataMaster master = new CentrifugationDataMaster();
////		master.setCentrifuge(clinicalDao.centrifugationWithId(centrifugeId));
//		master.setCreatedBy(user);
////		data.setSampleCentrifugation(clinicalDao.sampleCentrifugation(sampleCentrifugationId));
//		master.setCentrifugeCondition(condition);
//		try {
//			master.setCentrifugeStartTime(sd.parse(sd.format(new Date()) + runningTimeWithSeconds));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			master.setCentrifugeScanTime(new Date());
//		}
//		try {
//			master.setCentrifugeScanTime(sd.parse(sd.format(new Date()) + instrumentScanTime));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			master.setCentrifugeScanTime(new Date());
//		}
//
//		List<CentrifugationData> list = new ArrayList<>();
//		for (TempCentrifuge t : temp) {
//			CentrifugationData data = new CentrifugationData();
//			data.setCentrifugationDataMaster(master);
//			data.setStudy(studyDao.findByStudyId(t.getStudyId()));
//			data.setPeriod(studyDao.periodById(t.getPeriodId()));
//			data.setSampleTimePoints(vacutainerTimePointById.get(t.getTimePotnId()));
//			List<LoadedVacutinersInCentrifuge> vacutaineres = new ArrayList<>();
//			vacutainer.forEach((k, v) -> {
//				LoadedVacutinersInCentrifuge lvic = new LoadedVacutinersInCentrifuge();
//				lvic.setCentrifugationData(data);
//				lvic.setSubjectSampleCollectionTimePoints(vacutainerRecard.get(k));
//				try {
//					lvic.setScanTime(sd.parse(sd.format(new Date()) + instrumentScanTime));
//				} catch (ParseException e) {
//					e.printStackTrace();
//					lvic.setScanTime(new Date());
//				}
//				vacutaineres.add(lvic);
//			});
//			data.setVacutaineres(vacutaineres);
//			list.add(data);
//		}
//
//		return clinicalDao.saveCentrifugationData(master, list);
		return null;
	}

	public String centrifugationEndSave(String runningTimeWithSeconds, Long centrifugeDataId) {
		return clinicalDao.saveCentrifugationData(runningTimeWithSeconds, centrifugeDataId);
	}

	public Long sampleSeparationStartSave(Long centrifugeDataMasterId, String runningTimeWithSeconds,
			String missingSampleSeparation, String missingSubjectsSeparation, String commentsSeparation,
			String commentsSubjectsSeparation, String commentSeparation, String separaitonData, Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat sdfTime = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormatSeconds"));
		String date = sdf.format(new Date());
		UserMaster user = userDao.findById(userId);
		CentrifugationDataMaster master = clinicalDao.centrifugationDataMasterWithId(centrifugeDataMasterId);
		master.setMissingSampleSeparation(missingSampleSeparation);
		master.setMissingSubjectsSeparation(missingSubjectsSeparation);
//		master.setCommentsSubjectsSeparation(commentsSubjectsSeparation);
//		master.setCommentsSeparation(commentsSeparation);
		master.setCommentSeparation(commentSeparation);
		master.setSeparatedBy(user);
		try {
			master.setSeparationStartTime(sdfTime.parse(date + " " + runningTimeWithSeconds));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			master.setSeparationStartTime(new Date());
		}

		List<CentrifugationData> centrifugationData = clinicalDao.centrifugationData(master);

		List<TempCentrifuge> temp = new ArrayList<>();
		String[] separaitonDataArray = separaitonData.split("\\,");
		List<SubjectSampleCollectionTimePoints> sampleTimePoints = new ArrayList<>();
		List<SubjectSampleSeparationTimePointsData> sepdata = new ArrayList<>();
		for (String s : separaitonDataArray) {
			String[] eachSeparationData = s.split("##");
			String vacutainer = eachSeparationData[0];
			String vacutainerScanTime = eachSeparationData[1];
			String vials = eachSeparationData[2];
			String[] vialsData = vials.split("\\#");
			Map<String, String> vialMap = new HashMap<>();
			for (String vial : vialsData) {
				String[] val = vial.split("--");
				vialMap.put(val[0], val[1]);
			}

			SubjectSampleCollectionTimePoints ssctp = clinicalDao.subjectSampleCollectionTimePoints(vacutainer);
			try {
				ssctp.getSubjectSampleCollectionTimePointsData()
						.setVialActualTime(sdfTime.parse(date + " " + runningTimeWithSeconds));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				ssctp.getSubjectSampleCollectionTimePointsData().setVialActualTime(new Date());
			}
			CentrifugationData cd = null;
			for (CentrifugationData data : centrifugationData) {
				if (data.getStudy().getId() == ssctp.getStudy().getId()
						&& data.getPeriod().getId() == ssctp.getPeriod().getId()
						&& data.getSampleTimePoints().getId() == ssctp.getSampleTimePointId().getId()) {
					cd = data;
					break;
				}
			}
			for (Map.Entry<String, String> entry : vialMap.entrySet()) {
				String k = entry.getKey();
				String v = entry.getValue();
				SubjectSampleSeparationTimePointsData ssstpd = new SubjectSampleSeparationTimePointsData();
				ssstpd.setCentrifugationData(cd);
				ssstpd.setVialBarocde(k);
				ssstpd.setSubjectSampleCollectionTimPoints(ssctp);
				try {
					ssstpd.setVacutinerScanTime(sdfTime.parse(date + " " + vacutainerScanTime));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					ssstpd.setVacutinerScanTime(new Date());
				}
				ssstpd.setVialNo(Integer.parseInt(k.substring(22)));
				try {
					ssstpd.setVialScanTimetime(sdfTime.parse(date + " " + v));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					ssstpd.setVialScanTimetime(new Date());
				}
				ssstpd.setSeparatedBy(user);
				ssstpd.setSeparationDate(new Date());

				sepdata.add(ssstpd);
			}
			sampleTimePoints.add(ssctp);
		}

		return clinicalDao.saveSampleSeparationStart(master, sampleTimePoints, sepdata);

	}

	public String sampleSeparationEndSave(String runningTimeWithSeconds, Long centrifugeDataMasterId) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat sdfTime = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormatSeconds"));
		String date = sdf.format(new Date());
		CentrifugationDataMaster master = clinicalDao.centrifugationDataMasterWithId(centrifugeDataMasterId);
		try {
			master.setSeparationEndTime(sdfTime.parse(date + " " + runningTimeWithSeconds));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			master.setSeparationStartTime(new Date());
		}
		List<CentrifugationData> centrifugeData = clinicalDao.saveSampleSeparationEndTime(master);

		if (centrifugeData.size() > 1) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					"<table><tr><td><select name='centrifugeId' id='centrifugeId' onchange=\"storageData(\'centrifugeId\')\" >");
			centrifugeData.stream().forEach((obj) -> {
				sb.append("<option value=\"" + obj.getId() + "\">").append(obj.getStudy().getProjectNo())
						.append("</option>");
			});
			sb.append("</select></td></tr></table><div id='centrifugationDataDiv'></div>");
			return sb.toString();
		} else {
			CentrifugationData obj = centrifugeData.get(0);
			return centrifugeInfoFoStorage(obj);
		}

	}

	public String centrifugeData(Long centrifugeDataId) {
		CentrifugationData obj = clinicalDao.centrifugationDataWithId(centrifugeDataId);

		return centrifugeInfoFoStorage(obj);
	}

	private String centrifugeInfoFoStorage(CentrifugationData obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div id='centrifugationDataDiv'><table><tr><th>Project No</th><td>")
				.append(obj.getStudy().getProjectNo()).append("</td></tr>");
		sb.append("<tr><th>Period</th><td>").append(obj.getPeriod().getPeriodNo()).append("</td></tr>");
		sb.append("<tr><th>Time Point</th><td>").append(obj.getSampleTimePoints().getTimePoint()).append("</td></tr>");
		List<LoadedVacutinersInCentrifuge> loadedVacutainers = clinicalDao.loadedVacutinersInCentrifuge(obj);
		sb.append("<tr><th>Total Samples</th><td>").append(loadedVacutainers.size()).append("</td></tr>");
		if (obj.getSampleTimePoints().getNoOfVials() == 1) {
			sb.append("<tr><th>Aliquot</th><td>")
					.append("<input type='text' readonly='readonly' name='aloquotNo' name='aloquotNo' value=\"" + 1
							+ "\" readonly='readonly'/>")
					.append("</td></tr>");
		} else {
			sb.append("<tr><th>Aliquot</th><td>").append("<select name='aloquotNo' id='aloquotNo'>");
			sb.append("<option value=\"-1\">--Select--</option>");
			for (int no = 1; no <= obj.getSampleTimePoints().getNoOfVials(); no++) {
				sb.append("<option value=\"" + no + "\">").append(no).append("</option>");
			}
			sb.append("<font color='red' id='aloquotNoMsg'></font></td><tr>");
		}
		List<SubjectSampleSeparationTimePointsData> separatedVails = clinicalDao
				.subjectSampleSeparationTimePointsData(obj);
		sb.append("<tr><th>Total Samples</th><td>").append(separatedVails.size()).append("</td></tr>");
		sb.append("</td></tr></table></div>");
		return sb.toString();
	}

	public Object shelfDetails(String shelfBarode) {
		// TODO Auto-generated method stub
		return shelfBarode;
	}

	public Object deepfreezerDetails(String deepfreezerBarode) {
		// TODO Auto-generated method stub
		return deepfreezerBarode;
	}

	public Object smapleStorageConformSave(Long centrifugeDataMasterId, String runningTimeWithSeconds,
			String missingSampleStorage, String missingSubjectsStorage, String commentsStorage,
			String commentsSubjectsStorage, String commentStorage, String shelfbarcode, String deepfreezerBarcode,
			int aloquotNo, Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SimpleDateFormat sdfTime = new SimpleDateFormat(environment.getRequiredProperty("dateTimeFormatSeconds"));
		String date = sdf.format(new Date());
		UserMaster user = userDao.findById(userId);
		CentrifugationDataMaster master = clinicalDao.centrifugationDataMasterWithId(centrifugeDataMasterId);
		SampleStorateDataMaster storageMaster = new SampleStorateDataMaster();
		storageMaster.setCentrifugationDataMaster(master);
		storageMaster.setMissingSampleStorage(missingSampleStorage);
		storageMaster.setMissingSubjectsStorage(missingSubjectsStorage);
		storageMaster.setCommentsStorage(commentsStorage);
		storageMaster.setCommentsSubjectsStorage(commentsSubjectsStorage);
		storageMaster.setCommentsStorage(commentsStorage);
		storageMaster.setShelfbarcode(shelfbarcode);
		storageMaster.setDeepfreezerBarcode(deepfreezerBarcode);
		storageMaster.setAloquotNo(aloquotNo);
		storageMaster.setStoredBy(user);
		try {
			storageMaster.setStorageTime(sdfTime.parse(date + " " + runningTimeWithSeconds));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			storageMaster.setStorageTime(new Date());
		}
		return clinicalDao.storageMaster(storageMaster);
	}

	public String centrifugationSave(CentrifugationDateDto centrifugationData, Long userId) { // TempCentrifuge,
		String msg = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sd.format(new Date());
		String curDateWithStartTime = currentDate+" "+centrifugationData.getStartTime();
		String curDateWithEndTime = currentDate+" "+centrifugationData.getEndTime();
		String curDateWithScanTime = currentDate+" "+centrifugationData.getEndTime();
		
		Map<String, SubjectSampleCollectionTimePointsData> vacutainerRecard = new HashMap<>();
		Map<String, String> vacutinerAndScanTimes = new HashMap<>();
		List<LoadedVacutinersInCentrifuge> vacutaineres = new ArrayList<>();
		StudyMaster study = null;
		UserMaster user = null;
		List<String> vacutinerBarcodesScanTimes =  null;
		StudyPeriodMaster period = null;
		SampleTimePoints sampleTimePoints = null;
		List<String> vacutinerBarcodes = null;
		StringBuilder sb = new StringBuilder();
		boolean sbFlag = false;
		Long masterId = null;
		try {
			// CentrifugationData
			study = studyDao.findByStudyId(centrifugationData.getStudyId());
			user = userDao.findById(userId);
			vacutinerBarcodesScanTimes = centrifugationData.getVacutainerScanTimes();
			vacutinerBarcodesScanTimes.stream().forEach((data) -> {
				String s[] = data.split("\\#");
				vacutinerAndScanTimes.put(s[0], s[1]);
			});
			vacutinerBarcodes = centrifugationData.getVacutinerBarcodes();
			for (String vac : vacutinerBarcodes) {
				SubjectSampleCollectionTimePointsData ssctp = clinicalDao.subjectSampleCollectionTimePointsData(vac,
						study.getId());
				if (sbFlag) {
					sb.append(",").append(ssctp.getSubject().getSubjectNo());
				} else {
					sb.append(ssctp.getSubject().getSubjectNo());
					sbFlag = true;
				}
				vacutainerRecard.put(vac, ssctp);
				if (sampleTimePoints == null) {
					period = ssctp.getStudyPeriodMaster();
					sampleTimePoints = ssctp.getSampleTimePoint();
				}
			}
			String temp = centrifugationData.getCentrifugeBarcode().replaceAll("a", "#");
			String centrifugeBarcode[] = temp.split("\\#");
			String centrifuteId = centrifugeBarcode[1].replace("n", "");
			CentrifugationDataMaster master = new CentrifugationDataMaster();
			master.setStudy(study);
			master.setSampleTimePoints(sampleTimePoints);
			master.setTimePoitns(sampleTimePoints.getSign() + "" + sampleTimePoints.getTimePoint());
			master.setCentrifugeMissedSubjects(centrifugationData.getCentrifugeMissedSubjects());
			master.setCentrifugeSubjects(sb.toString());
			master.setInstrument(clinicalDao.centrifugationWithId(Long.parseLong(centrifuteId)));
			master.setPeriod(period);
			master.setCentrifugeBy(user);
			master.setCentrifugeCondition(centrifugationData.getBufferStorage());
			master.setCentrifugeStartTime(sdf.parse(curDateWithStartTime));
			master.setCentrifugeEndTime(sdf.parse(curDateWithEndTime));
			master.setCentrifugeScanTime(sdf.parse(curDateWithScanTime));
			vacutainerRecard.forEach((vacutainerBaroCode, timePointData) -> {
				LoadedVacutinersInCentrifuge lvic = new LoadedVacutinersInCentrifuge();
				lvic.setCentrifugationDataMaster(master);
				lvic.setSubjectSampleCollectionTimePointsData(timePointData);
				lvic.setScanBy(master.getCentrifugeBy());
				try {
					lvic.setScanTime(sdf.parse(sd.format(new Date()) + vacutinerAndScanTimes.get(vacutainerBaroCode)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				vacutaineres.add(lvic);
			});
			masterId = clinicalDao.saveCentrifugationData(master, vacutaineres);
			if (masterId > 0l) {
				msg = "{\"Success\": \"" + true
						+ "\",\"Message\":\"Centrifuge Data saved successfully\", \"centrifugeId\":\"" + masterId + "\"}";
			} else
				msg = "{\"Success\": \"" + false + "\",\"Message\":\"Failed To save Centrifuge Data\", \"centrifugeId\":\""
						+ masterId + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return msg;
	}

	public String sampleSeparationSave(CentrifugationDateDto centrifugationData, Long userId) {
		String resultMsg = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sd.format(new Date());
		String curDateWithSatartTime = currentDate+" "+centrifugationData.getStartTime();
		String curDateWithEndTime = currentDate+" "+centrifugationData.getEndTime();
		
		Map<String, String> vacutinerAndScanTimes = new HashMap<>();
		Map<Long, SubjectSampleCollectionTimePointsData> vacutainerRecard = new HashMap<>();
		Map<String, SubjectSampleCollectionTimePointsData> subjectSampleCollectionTimePointsDataMap = new HashMap<>();
		List<Long> subjectSampleCollectionTimePointsDataIds = new ArrayList<>();
		Map<String, LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifugeMap = new HashMap<>();
		List<VialSeparationData> vialData = new ArrayList<>();
		List<String> vialScanTimes = centrifugationData.getVialScanTimes();
		CentrifugationDataMaster master = null;
		StudyMaster study = null;
		UserMaster user = null;
		List<String> vacutinerBarcodesScanTimes = null;
		List<String> vacutinerBarcodes = null;
		StringBuilder sb = new StringBuilder();
		List<LoadedVacutinersInCentrifuge> loadedVacutinersInCentrifuges = null;
		boolean sbFlag = false;
		SubjectSampleCollectionTimePointsData ssctp = null;
		Long masterId = null;
		try {
			 master = clinicalDao.centrifugationDataMaster(centrifugationData.getCentrifugationId());
			 if (centrifugationData.getStudyId() == null) {
					centrifugationData.setStudyId(master.getStudy().getId());
				}
				study = studyDao.findByStudyId(centrifugationData.getStudyId());
				user = userDao.findById(userId);
				vacutinerBarcodesScanTimes = centrifugationData.getVacutainerScanTimes();
				vacutinerBarcodesScanTimes.stream().forEach((data) -> {
					String s[] = data.split("\\#");
					vacutinerAndScanTimes.put(s[0], s[1]);
				});
				
				Map<String, String> vialAndScanTimes = new HashMap<>();
				vialScanTimes.stream().forEach((data) -> {
					String s[] = data.split("\\#");
					vialAndScanTimes.put(s[0], s[1]);
				});
				vacutinerBarcodes = centrifugationData.getVacutinerBarcodes();
				for (String vac : vacutinerBarcodes) {
					ssctp = clinicalDao.subjectSampleCollectionTimePointsData(vac,study.getId());
					if (sbFlag) {
						sb.append(",").append(ssctp.getSubject().getSubjectNo());
					} else {
						sb.append(ssctp.getSubject().getSubjectNo());
						sbFlag = true;
					}
					vacutainerRecard.put(ssctp.getId(), ssctp);
					subjectSampleCollectionTimePointsDataIds.add(ssctp.getId());
					StringBuilder sb1 = new StringBuilder();
					sb1.append("04");
					sb1.append("a");
					sb1.append(ssctp.getStudyPeriodMaster().getId());
					//This was commented for treatment specific is removed in barcodes
					/*sb1.append("a");
					sb1.append(ssctp.getSampleTimePoint().getTreatmentInfo().getId());*/
					sb1.append("a");
					sb1.append(ssctp.getSubject().getSubjectNo());
					sb1.append("a");
					sb1.append(ssctp.getSampleTimePoint().getId());
					subjectSampleCollectionTimePointsDataMap.put(sb1.toString(), ssctp);
				}
				loadedVacutinersInCentrifuges = clinicalDao.loadedVacutinersInCentrifuges(subjectSampleCollectionTimePointsDataIds);
				loadedVacutinersInCentrifuges.forEach((each) -> {
					StringBuilder sb1 = new StringBuilder();
					sb1.append("04");
					sb1.append("a");
					sb1.append(each.getSubjectSampleCollectionTimePointsData().getStudyPeriodMaster().getId());
					//This was commented for treatment specific is removed in barcodes
					/*sb1.append("a");
					sb1.append(each.getSubjectSampleCollectionTimePointsData().getSampleTimePoint().getTreatmentInfo().getId());
					*/
					sb1.append("a");
					sb1.append(each.getSubjectSampleCollectionTimePointsData().getSubject().getSubjectNo());
					sb1.append("a");
					sb1.append(each.getSubjectSampleCollectionTimePointsData().getSampleTimePoint().getId());
					loadedVacutinersInCentrifugeMap.put(sb1.toString(), each);
				});
				master.setSeparatedBy(user);
				master.setMissingSampleSeparation(centrifugationData.getSeparationMissedSubjects());
				master.setSeparationStartTime(sdf.parse(curDateWithSatartTime));
				master.setSeparationEndTime(sdf.parse(curDateWithEndTime));
				for(Map.Entry<String, String> vast : vialAndScanTimes.entrySet()) {
					String vialBarcode = vast.getKey();
					String scanTime = vast.getValue();
					String temp = vialBarcode.replaceAll("a", "#");
					String vialBarcodeSplit[] = temp.split("\\#");
					StringBuilder sb1 = new StringBuilder();
					sb1.append("04");
					sb1.append("a");
					sb1.append(vialBarcodeSplit[1]);
					//This was commented for treatment specific is removed in barcodes
					/*sb1.append("a");
					sb1.append(vialBarcodeSplit[2]);*/
					sb1.append("a");
					sb1.append(vialBarcodeSplit[3]);
					sb1.append("a");
					//This was commented for treatment specific is removed in barcodes
					sb1.append(vialBarcodeSplit[4]);
					String aliquot = vialBarcodeSplit[5].replace("n", "");
					LoadedVacutinersInCentrifuge lvic = loadedVacutinersInCentrifugeMap.get(sb1.toString());
					VialSeparationData data = new VialSeparationData();
					data.setCentrifugationDataMaster(master);
					data.setLoadedVacutinersInCentrifuge(lvic);
					data.setSubjectSampleCollectionTimePointsData(subjectSampleCollectionTimePointsDataMap.get(sb1.toString()));
					data.setAliquot(aliquot);
					String scTime = currentDate+" "+scanTime;
					data.setScanTime(sdf.parse(scTime));
					data.setScanBy(user);
					vialData.add(data);
				}
				masterId = clinicalDao.saveSampleSeparationData(master, vialData);
				if (masterId != null && masterId > 0l) {
					resultMsg = "{\"Success\": \"" + true
							+ "\",\"Message\":\"Centrifuge Data saved successfully\", \"centrifugeId\":\"" + masterId + "\"}";
				} else
					resultMsg = "{\"Success\": \"" + false + "\",\"Message\":\"Failed To save Centrifuge Data\", \"centrifugeId\":\""
							+ masterId + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMsg;
		
	}

	public String sampleStorageSave(CentrifugationDateDto centrifugationDate, Long userId) {
		Map<String, String> barcodeTimes = new HashMap<>();
		List<String> rackBarocodesScanTimes = centrifugationDate.getRackBarocodesScanTimes();
		for (String rackBarocodesScanTime : rackBarocodesScanTimes) {
			String s[] = rackBarocodesScanTime.split("\\_");
			barcodeTimes.put(s[0], s[1]);
		}
		StudyMaster study = studyDao.findByStudyId(centrifugationDate.getStudyId());
		UserMaster user = userDao.findById(userId);
		SimpleDateFormat sd = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		SampleStorageData data = new SampleStorageData();
		data.setStudy(study);
		data.setPeriod(studyDao.periodById(centrifugationDate.getPeriodId()));
		data.setStorageMissedSubjects(centrifugationDate.getStorageMissedSubjects());
		if(study.isTreatmentSpecificSampleTimepoints())
			data.setTimePoint(clinicalDao.sampleTimePointsWithId(Long.parseLong(centrifugationDate.getTimepointId())));
		else {
			data.setTimePoitnOnlye(centrifugationDate.getTimepointId());
		}
		data.setAliquot(centrifugationDate.getAliquot());
		data.setShelf(clinicalDao.deepfreezerShelfsById(
				Long.parseLong((centrifugationDate.getShelfBarocode().split("a")[1].replaceAll("n", "")))));
		data.setShelfBarocode(centrifugationDate.getShelfBarocode());
		data.setStoredBy(user);
		data.setStorageTime(new Date());

		try {
			data.setShelfScanTime(sd.parse(sd.format(new Date()) + centrifugationDate.getShelfScanTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.setShelfScanTime(new Date());
		}

		List<SampleStorageDataRack> ssdrList = new ArrayList<>();
		centrifugationDate.getRackBarocodes().forEach((rackBarcode) -> {
//			Deepfreezer rack = clinicalDao.deepfreezers(Long.parseLong(rackBarcode.split("\\a")[1]));
			List<RackWithVials> racks = clinicalDao
					.rackWithVialsWithRackId(Long.parseLong(rackBarcode.split("a")[1].replaceAll("n", "")));
			for (RackWithVials rack : racks) {
				SampleStorageDataRack ssdr = new SampleStorageDataRack();
				ssdr.setRackWithVials(rack);
				ssdr.setSampleStorageData(data);
				ssdr.setStudyId(study.getId());
				try {
					ssdr.setScanTime(sd.parse(sd.format(new Date()) + barcodeTimes.get(rackBarcode)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					data.setShelfScanTime(new Date());
				}
				ssdrList.add(ssdr);
			}

		});
		data.setSampleStorageDataRack(ssdrList);
		Long masterId = clinicalDao.saveSampleStorageData(null, data);
		if (masterId > 0l) {
			return "{\"Success\": \"" + true + "\",\"Message\":\"Storage Data saved successfully\", \"centrifugeId\":\""
					+ masterId + "\"}";
		} else
			return "{\"Success\": \"" + false + "\",\"Message\":\"Failed To save Storage Data\", \"centrifugeId\":\""
					+ masterId + "\"}";
	}

	public SampleContainer seggrigationSave(SeggrigationDataDto dto, Long userId) {
		SimpleDateFormat dateTimeFormatSeconds = new SimpleDateFormat(
				environment.getRequiredProperty("dateTimeFormatSeconds"));
		SimpleDateFormat sdf = new SimpleDateFormat(environment.getRequiredProperty("dateFormat"));
		String onyDate = sdf.format(new Date());
		SampleContainer container = new SampleContainer();
		container.setStudy(studyDao.findByStudyId(dto.getStudyId()));
		container.setPeriod(studyDao.periodById(dto.getPeriodId()));
		if (dto.getSubject() != null && !dto.getSubject().trim().equals("")) {
			if (!dto.getSubject().contains(","))
				container.setSubject(studyDao.studySubject(dto.getStudyId().toString(), dto.getSubject()));
		}
		container.setSubjects(dto.getSubject());
		container.setAllSubject(dto.getAllSubjectb());
		container.setVialNo(Integer.parseInt(dto.getAliquot()));
		container.setCleanArea(dto.getCleanArea());
		container.setDataLogger(dto.getDataLogger());
		container.setAliquot(dto.getAliquot());
		container.setCreatedBy(userDao.findById(userId));
		container.setCreatedOn(new Date());
		List<String> scanedVialsTimes = dto.getScanedVialsTimes();
		List<String> subs = new ArrayList<>();
		List<SampleContainerVials> vialsList = new ArrayList<>();
		for(String svt : scanedVialsTimes) {
			String sv[] = svt.split("\\_");
			SampleContainerVials scv = new SampleContainerVials();
			scv.setStudy(container.getStudy());
			scv.setPeriod(container.getPeriod());
			scv.setSampleContainer(container);
			scv.setBarcode(sv[0]);
			try {
				scv.setScanTime(dateTimeFormatSeconds.parse(onyDate + " " + sv[1]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String barcode = sv[0];
			barcode = barcode.replaceAll("n", "");
			String vals[] = barcode.split("a");
			scv.setTimePoint(clinicalDao.sampleTimePointsWithId(Long.parseLong(vals[4])));
			scv.setSubject(studyDao.studySubject(container.getStudy().getId()+"", vals[3]));
			subs.add(scv.getSubject().getSubjectNo());
			vialsList.add(scv);
		}
		container.setSubjects(String.join(", ", subs));
		container.setVialsList(vialsList);
		return clinicalDao.saveSampleContainer(container);
	}

}
