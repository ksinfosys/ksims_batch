package com.ksinfo.batch.vo;

public class MailRecipientDto {

	private Long mailIdx;
	private String recipientEmp;
	private String recipientType;
	private int logicalDelFlg;
	public MailRecipientDto(Long mailIdx, String recipientEmp, String recipientType) {
		this.mailIdx = mailIdx;
        this.recipientEmp = recipientEmp;
        this.recipientType = recipientType;
	}
	public Long getMailIdx() {
		return mailIdx;
	}
	public void setMailIdx(Long mailIdx) {
		this.mailIdx = mailIdx;
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
	public int getLogicalDelFlg() {
		return logicalDelFlg;
	}
	public void setLogicalDelFlg(int logicalDelFlg) {
		this.logicalDelFlg = logicalDelFlg;
	}
	
	
}
