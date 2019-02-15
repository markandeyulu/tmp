package com.tmp.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

public class ProfileResume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int profileId;
	private String fileName;
	private String fileFormat;
	private java.sql.Blob resume;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date createdOn;
	private int createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd") private java.util.Date updatedOn;
	private int updatedBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public java.sql.Blob getResume() {
		return resume;
	}

	public void setResume(java.sql.Blob resume) {
		this.resume = resume;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}


	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public java.util.Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public java.util.Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.util.Date updatedOn) {
		this.updatedOn = updatedOn;
	}


}
