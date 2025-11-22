package com.ksinfo.batch.vo;

public class SendMailDto {

	private Long mailIdx;
	private String empId;
	private String issueFromDate;
	private String issueToDate;
	private boolean issueFlg;
	private boolean issueAllEmpFlg;
	private boolean adminCreateFlg;
	private String recipientEmp;
	private String recipientType;
	private String empCompMail;
	private String subject;
	private String content;
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
	public boolean getIssueFlg() {
		return issueFlg;
	}
	public void setIssueFlg(boolean issueFlg) {
		this.issueFlg = issueFlg;
	}
	public boolean getIssueAllEmpFlg() {
		return issueAllEmpFlg;
	}
	public void setIssueAllEmpFlg(boolean issueAllEmpFlg) {
		this.issueAllEmpFlg = issueAllEmpFlg;
	}
	public boolean getAdminCreateFlg() {
		return adminCreateFlg;
	}
	public void setAdminCreateFlg(boolean adminCreateFlg) {
		this.adminCreateFlg = adminCreateFlg;
	}
	public String getRecipientEmp() {
		return recipientEmp;
	}
	public void setRecipientEmp(String recipientEmp) {
		this.recipientEmp = recipientEmp;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}
	public String getEmpCompMail() {
		return empCompMail;
	}
	public void setEmpCompMail(String empCompMail) {
		this.empCompMail = empCompMail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
