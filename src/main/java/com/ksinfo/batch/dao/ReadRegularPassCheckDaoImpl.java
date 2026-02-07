package com.ksinfo.batch.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class ReadRegularPassCheckDaoImpl extends SqlSessionFactoryService implements ReadRegularPassCheckDao {

	@Value("${BATCH_TARGET_ADMIN}")
	private String targetAdmin;
	 
	@Value("${regularPassCheck.sender}")
	private String sender;
	
	@Value("${regularPassCheck.subject}")
	private String subject;

	private final String to = "to";

	@Override
	public List<UserDto> getTargetUserList() throws Exception {
		List<UserDto> userList = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getUser");
		List<String> nonTargetUserId = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getNonTargetUserId");
		List<UserDto> resUser = userList.stream().filter(u -> !nonTargetUserId.contains(u.getEmpId())).collect(Collectors.toList());
		return resUser;
	}

	@Override
	public void insertMail(List<UserDto> targetUser) {
		int insertCount = getSqlSessionTemplate().insert("regularPassCheckTaskletMapper.insertMailMgt");
		MailDto insertedTarget = getSqlSessionTemplate().selectOne("regularPassCheckTaskletMapper.getInsertedMailMgt", insertCount);
		
		List<MailRecipientDto> recipientTarget = new ArrayList<MailRecipientDto>();
		List<MailIssueDto> issueTarget = new ArrayList<MailIssueDto>();
		List<MailContentsDto> contentsTarget = new ArrayList<MailContentsDto>();

		for (String admin : targetAdmin.split(",")){
			recipientTarget.add(new MailRecipientDto(insertedTarget.getMailIdx(), admin.trim(), to));
		
			LocalDate fromDate =LocalDate.parse(insertedTarget.getIssueFromDate());
			issueTarget.add(new MailIssueDto(insertedTarget.getMailIdx(), fromDate));

			int year = fromDate.getYear();
        	int month = fromDate.getMonthValue(); 
			String insertSubject = year + "年" + month+ "月" + subject;
			StringBuffer content = new StringBuffer("本メールはシステムから自動送信されています。\n\n");
			content.append("交通費未登録情報です。\n\n");
			content.append("交通費未登録社員リスト（");
			content.append(year + "年" + (month - 1)+ "月");
			content.append("）\n");
			for (UserDto regularPassTargetUser : targetUser) {
				content.append("社員番号：");
				content.append(regularPassTargetUser.getEmpId());
				content.append("、");
				content.append("社員名：");
				content.append(regularPassTargetUser.getEmpName());
				content.append("\n");
			}
			content.append("\n");

			contentsTarget.add(new MailContentsDto(insertedTarget.getMailIdx(), sender, insertSubject, content.toString()));
		} 

		getSqlSessionTemplate().insert("regularPassCheckTaskletMapper.insertMailRecipient", recipientTarget);
		getSqlSessionTemplate().insert("regularPassCheckTaskletMapper.insertMailIssue", issueTarget);
		getSqlSessionTemplate().insert("regularPassCheckTaskletMapper.insertMailContents", contentsTarget);

		return;
	}
	
}
