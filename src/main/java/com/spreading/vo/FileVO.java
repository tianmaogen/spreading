package com.spreading.vo;

import java.io.InputStream;

public class FileVO {
     private String fileName;
     private InputStream in;
     private Integer fileSize;
     
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the in
	 */
	public InputStream getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(InputStream in) {
		this.in = in;
	}
	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	
     
}

