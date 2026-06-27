package com.ksinfo.batch.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.util.MailSender;
import com.ksinfo.batch.vo.MailContentsDto;
import com.ksinfo.batch.vo.MailDto;
import com.ksinfo.batch.vo.MailIssueDto;
import com.ksinfo.batch.vo.MailRecipientDto;
import com.ksinfo.batch.vo.UserDto;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class ReadMonthlyCheckDaoImpl extends SqlSessionFactoryService implements ReadMonthlyCheckDao {

	@Autowired
	private MailSender mailSender;
	
	@Value("${BATCH_TARGET_ADMIN}")
	private String targetAdmin;
	 
	@Value("${conductCheck.sender}")
	private String sender;
	
	@Value("${conductCheck.subject}")
	private String subject;

	@Value("${conductCheck.content}")
	private String content;

	@Value("${conductCheck.admincontent}")
	private String adminContent;

	@Value("${conductCheck.slackEmail}")
	private String slackEmail;

	private final String to = "to";

	private final String year = "0";
	private final String conduct = "1";

	@Override
	public List<UserDto> getTargetUserList(String target) throws Exception {
		if(target.equals(year)) {
			return getSqlSessionTemplate().selectList("monthlyCheckMapper.getYearTargetUser");
		} else if(target.equals(conduct)) {
			return getSqlSessionTemplate().selectList("monthlyCheckMapper.getConductTargetUser");
		} else {
			throw new Exception();
		}
	}

	@Override
	public void insertMail(List<UserDto> yearTargetUser, List<UserDto> conductTargetUser) {
		int insertCount = getSqlSessionTemplate().insert("monthlyCheckMapper.insertMailMgt");
		MailDto insertedTarget = getSqlSessionTemplate().selectOne("monthlyCheckMapper.getInsertedMailMgt", insertCount);
		
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
			content.append("月次確認事項及び勤務表未登録情報です。\n\n");
			if(!yearTargetUser.isEmpty()) {
				content.append("入社年次別社員リスト（");
				content.append(year + "年" + month+ "月");
				content.append("時点）\n");
				for (UserDto target : yearTargetUser) {
					content.append("社員番号：");
					content.append(target.getEmpId());
					content.append("、");
					content.append("社員名：");
					content.append(target.getEmpName());
					content.append("\n");
				}
				content.append("\n");
			}
			if(!conductTargetUser.isEmpty() ) {
				content.append("勤務表未登録社員リスト（");
				content.append(year + "年" + (month - 1)+ "月");
				content.append("）\n");
				for (UserDto target : conductTargetUser) {
					content.append("社員番号：");
					content.append(target.getEmpId());
					content.append("、");
					content.append("社員名：");
					content.append(target.getEmpName());
					content.append("\n");
				}
			}
			contentsTarget.add(new MailContentsDto(insertedTarget.getMailIdx(), sender, insertSubject, content.toString()));

		} 

		getSqlSessionTemplate().insert("monthlyCheckMapper.insertMailRecipient", recipientTarget);
		getSqlSessionTemplate().insert("monthlyCheckMapper.insertMailIssue", issueTarget);
		getSqlSessionTemplate().insert("monthlyCheckMapper.insertMailContents", contentsTarget);

		return;
	}
	
	@Override
	public void executeConduct(List<UserDto> conductTargetUser) throws Exception {
		String targetNames = conductTargetUser.stream()
				.map(u -> u.getEmpName() + " (" + u.getEmpId() + ")")
				.collect(Collectors.joining("\n"));
				
		String adminContent = this.adminContent.replace("$name$", targetNames);

		for (UserDto target : conductTargetUser){
			mailSender.sendEmail(target.getEmpCompMail(), sender, subject, target.getEmpName() + content, true, false, "");
		} 

		mailSender.sendEmail(targetAdmin, sender, subject, adminContent, false, true, slackEmail);

		getSqlSessionTemplate().insert("monthlyCheckMapper.insertMailMgt", conductTargetUser);

		return;
	}
}
