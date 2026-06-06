package com.ksinfo.batch.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.util.MailSender;
import com.ksinfo.batch.vo.UserDto;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class ReadRegularPassCheckDaoImpl extends SqlSessionFactoryService implements ReadRegularPassCheckDao {

	@Autowired
	private MailSender mailSender;

	@Value("${regularPassCheck.sender}")
	private String sender;
	
	@Value("${regularPassCheck.subject}")
	private String subject;

	@Value("${regularPassCheck.content}")
	private String content;

	@Value("${regularPassCheck.admincontent}")
	private String adminContent;

	@Value("${BATCH_TARGET_ADMIN}")
	private String targetAdmin;

	@Value("${regularPassCheck.slackEmail}")
	private String slackEmail;

	@Override
	public List<UserDto> getTargetUserList() throws Exception {
		List<UserDto> userList = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getUser");
		List<String> nonTargetUserId = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getNonTargetUserId");
		List<UserDto> resUser = userList.stream().filter(u -> !nonTargetUserId.contains(u.getEmpId())).collect(Collectors.toList());
		return resUser;
	}

	@Override
	public void insertMail(List<UserDto> targetUser) throws Exception {
		String targetNames = targetUser.stream()
				.map(u -> u.getEmpName() + " (" + u.getEmpId() + ")")
				.collect(Collectors.joining("\n"));
		String adminContent = this.adminContent.replace("$name$", targetNames);

		for (UserDto target : targetUser){
			mailSender.sendEmail(target.getEmpCompMail(), sender, subject, target.getEmpName() + content, false, false, "");
		} 

		mailSender.sendEmail(targetAdmin, sender, subject, adminContent, false, true, slackEmail);

		getSqlSessionTemplate().insert("regularPassCheckTaskletMapper.insertMailMgt", targetUser);

		return;
	}
	
}
