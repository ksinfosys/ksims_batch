package com.ksinfo.batch.vo;

public class MailDto {

	private Long mailIdx;
	private String empId;
	private String issueFromDate;
	private String issueToDate;
	private int issueFlg;
	private int issueAllEmpFlg;
	private int adminCreateFlg;
	private String createId;
	private String createDate;
	private String updateId;
	private String updateDate;
	private int logicalDelFlg;
	
	public Long getMailIdx() {
		return mailIdx;
	}
	public void setMailIdx(Long mailIdx) {
		this.mailIdx = mailIdx;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getIssueFromDate() {
		return issueFromDate;
	}
	public void setIssueFromDate(String issueFromDate) {
		this.issueFromDate = issueFromDate;
	}
	public String getIssueToDate() {
		return issueToDate;
	}
	public void setIssueToDate(String issueToDate) {
		this.issueToDate = issueToDate;
	}
	public int getIssueFlg() {
		return issueFlg;
	}
	public void setIssueFlg(int issueFlg) {
		this.issueFlg = issueFlg;
	}
	public int getIssueAllEmpFlg() {
		return issueAllEmpFlg;
	}
	public void setIssueAllEmpFlg(int issueAllEmpFlg) {
		this.issueAllEmpFlg = issueAllEmpFlg;
	}
	public int getAdminCreateFlg() {
		return adminCreateFlg;
	}
	public void setAdminCreateFlg(int adminCreateFlg) {
		this.adminCreateFlg = adminCreateFlg;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getLogicalDelFlg() {
		return logicalDelFlg;
	}
	public void setLogicalDelFlg(int logicalDelFlg) {
		this.logicalDelFlg = logicalDelFlg;
	}
	
}
