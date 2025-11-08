package com.ksinfo.batch.vo;

public class MailContentsDto {

	private Long mailIdx;
	private String sender;
	private String subject;
	private String content;
	private int logicalDelFlg;

	public MailContentsDto(Long mailIdx, String sender, String subject, String content) {
		this.mailIdx = mailIdx;
		this.sender = sender;
		this.subject = subject;
		this.content = content;
	}

	public Long getMailIdx() {
		return mailIdx;
	}
	public void setMailIdx(Long mailIdx) {
		this.mailIdx = mailIdx;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
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
	public int getLogicalDelFlg() {
		return logicalDelFlg;
	}
	public void setLogicalDelFlg(int logicalDelFlg) {
		this.logicalDelFlg = logicalDelFlg;
	}
	
	
}
