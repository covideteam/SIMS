package com.covideinfo.dto;

import java.util.List;
import java.util.Map;

public class CentrifugationDateDto {
private Long studyId;
private Long periodId;
private String timepointId;
private String centrifugeScanTime;
private String centrifugeBarcode;
private String bufferStorage;
private List<String> vacutinerBarcodes;
private List<String> vacutainerScanTimes;
private String startTime;
private String endTime;
private String centrifugeMissedSubjects;

private String separationMissedSubjects;
private List<String> vialScanTimes;
private Long centrifugationId;

private String storageMissedSubjects;
private String aliquot;

private String shelfBarocode;
private String shelfScanTime;

private List<String> rackBarocodes;
private List<String> rackBarocodesScanTimes;



public List<String> getRackBarocodes() {
	return rackBarocodes;
}
public void setRackBarocodes(List<String> rackBarocodes) {
	this.rackBarocodes = rackBarocodes;
}

public List<String> getRackBarocodesScanTimes() {
	return rackBarocodesScanTimes;
}
public void setRackBarocodesScanTimes(List<String> rackBarocodesScanTimes) {
	this.rackBarocodesScanTimes = rackBarocodesScanTimes;
}
public Long getStudyId() {
	return studyId;
}
public void setStudyId(Long studyId) {
	this.studyId = studyId;
}
public Long getPeriodId() {
	return periodId;
}
public void setPeriodId(Long periodId) {
	this.periodId = periodId;
}

public String getTimepointId() {
	return timepointId;
}
public void setTimepointId(String timepointId) {
	this.timepointId = timepointId;
}
public String getCentrifugeScanTime() {
	return centrifugeScanTime;
}
public void setCentrifugeScanTime(String centrifugeScanTime) {
	this.centrifugeScanTime = centrifugeScanTime;
}
public String getCentrifugeBarcode() {
	return centrifugeBarcode;
}
public void setCentrifugeBarcode(String centrifugeBarcode) {
	this.centrifugeBarcode = centrifugeBarcode;
}
public String getBufferStorage() {
	return bufferStorage;
}
public void setBufferStorage(String bufferStorage) {
	this.bufferStorage = bufferStorage;
}
public List<String> getVacutinerBarcodes() {
	return vacutinerBarcodes;
}
public void setVacutinerBarcodes(List<String> vacutinerBarcodes) {
	this.vacutinerBarcodes = vacutinerBarcodes;
}
public List<String> getVacutainerScanTimes() {
	return vacutainerScanTimes;
}
public void setVacutainerScanTimes(List<String> vacutainerScanTimes) {
	this.vacutainerScanTimes = vacutainerScanTimes;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
public String getCentrifugeMissedSubjects() {
	return centrifugeMissedSubjects;
}
public void setCentrifugeMissedSubjects(String centrifugeMissedSubjects) {
	this.centrifugeMissedSubjects = centrifugeMissedSubjects;
}
public String getSeparationMissedSubjects() {
	return separationMissedSubjects;
}
public void setSeparationMissedSubjects(String separationMissedSubjects) {
	this.separationMissedSubjects = separationMissedSubjects;
}
public List<String> getVialScanTimes() {
	return vialScanTimes;
}
public void setVialScanTimes(List<String> vialScanTimes) {
	this.vialScanTimes = vialScanTimes;
}
public Long getCentrifugationId() {
	return centrifugationId;
}
public void setCentrifugationId(Long centrifugationId) {
	this.centrifugationId = centrifugationId;
}
public String getStorageMissedSubjects() {
	return storageMissedSubjects;
}
public void setStorageMissedSubjects(String storageMissedSubjects) {
	this.storageMissedSubjects = storageMissedSubjects;
}
public String getAliquot() {
	return aliquot;
}
public void setAliquot(String aliquot) {
	this.aliquot = aliquot;
}
public String getShelfBarocode() {
	return shelfBarocode;
}
public void setShelfBarocode(String shelfBarocode) {
	this.shelfBarocode = shelfBarocode;
}
public String getShelfScanTime() {
	return shelfScanTime;
}
public void setShelfScanTime(String shelfScanTime) {
	this.shelfScanTime = shelfScanTime;
}





}
