package com.covideinfo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author swami.tammireddi
 *
 */
@Entity
@Table(name = "epk_SampleStorateDataMaster")
public class SampleStorateDataMaster extends CommonMaster{
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_CentrifugationDataMaster_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "sampleStorateDataMasterId")
	private long id;
	@ManyToOne
	@JoinColumn(name="centrifugationDataMasterId")
	private CentrifugationDataMaster centrifugationDataMaster;
	private Date storageTime;
	private String missingSampleStorage;
	private String missingSubjectsStorage;
	private String commentsStorage;
	private String commentsSubjectsStorage;
	private String commentStorage;
	private String shelfbarcode;
	private String deepfreezerBarcode;
	private int aloquotNo;
	@ManyToOne
	@JoinColumn(name = "storedBy")
	private UserMaster storedBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CentrifugationDataMaster getCentrifugationDataMaster() {
		return centrifugationDataMaster;
	}
	public void setCentrifugationDataMaster(CentrifugationDataMaster centrifugationDataMaster) {
		this.centrifugationDataMaster = centrifugationDataMaster;
	}
	public Date getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}
	public String getMissingSampleStorage() {
		return missingSampleStorage;
	}
	public void setMissingSampleStorage(String missingSampleStorage) {
		this.missingSampleStorage = missingSampleStorage;
	}
	public String getMissingSubjectsStorage() {
		return missingSubjectsStorage;
	}
	public void setMissingSubjectsStorage(String missingSubjectsStorage) {
		this.missingSubjectsStorage = missingSubjectsStorage;
	}
	public String getCommentsStorage() {
		return commentsStorage;
	}
	public void setCommentsStorage(String commentsStorage) {
		this.commentsStorage = commentsStorage;
	}
	public String getCommentsSubjectsStorage() {
		return commentsSubjectsStorage;
	}
	public void setCommentsSubjectsStorage(String commentsSubjectsStorage) {
		this.commentsSubjectsStorage = commentsSubjectsStorage;
	}
	public String getCommentStorage() {
		return commentStorage;
	}
	public void setCommentStorage(String commentStorage) {
		this.commentStorage = commentStorage;
	}
	public String getShelfbarcode() {
		return shelfbarcode;
	}
	public void setShelfbarcode(String shelfbarcode) {
		this.shelfbarcode = shelfbarcode;
	}
	public String getDeepfreezerBarcode() {
		return deepfreezerBarcode;
	}
	public void setDeepfreezerBarcode(String deepfreezerBarcode) {
		this.deepfreezerBarcode = deepfreezerBarcode;
	}
	
	public int getAloquotNo() {
		return aloquotNo;
	}
	public void setAloquotNo(int aloquotNo) {
		this.aloquotNo = aloquotNo;
	}
	public UserMaster getStoredBy() {
		return storedBy;
	}
	public void setStoredBy(UserMaster storedBy) {
		this.storedBy = storedBy;
	}
	
}
