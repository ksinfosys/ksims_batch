package com.ksinfo.batch.dao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.vo.MailContentsDto;
import com.ksinfo.batch.vo.MailDto;
import com.ksinfo.batch.vo.MailIssueDto;
import com.ksinfo.batch.vo.MailRecipientDto;
import com.ksinfo.batch.vo.UserDto;

@Repository
@PropertySource(value = {"classpath:admin.properties", "classpath:sendEmail.properties"}, encoding = "UTF-8")
public class ReadExpirationDateAndInsertDaoImpl extends SqlSessionFactoryService implements ReadExpirationDateAndInsertDao {

	@Value("${KSBAT_PT001_BATCH_TARGET_ADMIN}")
	private String targetAdmin;
	 
	@Value("${expiration.sender}")
	private String sender;
	
	@Value("${expiration.subject}")
	private String subject;

	@Value("${expiration.content}")
	private String content;

	private final String to = "to";
	private final String cc = "cc";

	@Override
	public List<UserDto> getTargetUserList() throws Exception {
		return getSqlSessionTemplate().selectList("residenceCardMapper.getTargetUser");
		
	}

	@Override
	public void insertMail(List<UserDto> targetUser) {
		int insertCount = getSqlSessionTemplate().insert("residenceCardMapper.insertMailMgt", targetUser);
		List<MailDto> insertedTarget = getSqlSessionTemplate().selectList("residenceCardMapper.getInsertedMailMgt", insertCount);
		
		List<MailRecipientDto> recipientTarget = new ArrayList<MailRecipientDto>();
		List<MailIssueDto> issueTarget = new ArrayList<MailIssueDto>();
		List<MailContentsDto> contentsTarget = new ArrayList<MailContentsDto>();

		for (MailDto target : insertedTarget) {
			recipientTarget.add(new MailRecipientDto(target.getMailIdx(), target.getEmpId(), to));
			for (String admin : targetAdmin.split(",")){
				recipientTarget.add(new MailRecipientDto(target.getMailIdx(), admin.trim(), cc));
			} 

			LocalDate fromDate =LocalDate.parse(target.getIssueFromDate());
        	LocalDate toDate = LocalDate.parse(target.getIssueToDate());
			LocalDate current = fromDate;
			while (current.getDayOfWeek() != DayOfWeek.MONDAY) {
				current = current.plusDays(1);
			}

			while (!current.isAfter(toDate)) {
				issueTarget.add(new MailIssueDto(target.getMailIdx(), current));
				current = current.plusWeeks(1);
			}
			String userName = targetUser.stream().filter(u -> u.getEmpId().equals(target.getEmpId())).findFirst().map(UserDto::getEmpName).orElse(null);
			contentsTarget.add(new MailContentsDto(target.getMailIdx(), sender, userName + subject, content));
		}
		getSqlSessionTemplate().insert("residenceCardMapper.insertMailRecipient", recipientTarget);
		getSqlSessionTemplate().insert("residenceCardMapper.insertMailIssue", issueTarget);
		getSqlSessionTemplate().insert("residenceCardMapper.insertMailContents", contentsTarget);

		return;
	}
	
}
