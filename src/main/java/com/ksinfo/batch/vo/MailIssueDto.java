package com.ksinfo.batch.vo;

import java.time.LocalDate;

public class MailIssueDto {

	private Long mailIdx;
	private LocalDate issueDate;
	private int logicalDelFlg;

	public MailIssueDto(Long mailIdx, LocalDate issueDate) {
		this.mailIdx = mailIdx;
		this.issueDate = issueDate;
	}
	
	public Long getMailIdx() {
		return mailIdx;
	}
	public void setMailIdx(Long mailIdx) {
		this.mailIdx = mailIdx;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public int getLogicalDelFlg() {
		return logicalDelFlg;
	}
	public void setLogicalDelFlg(int logicalDelFlg) {
		this.logicalDelFlg = logicalDelFlg;
	}
	
}
