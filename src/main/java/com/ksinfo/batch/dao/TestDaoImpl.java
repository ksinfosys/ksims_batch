package com.ksinfo.batch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.util.MailSender;
import com.ksinfo.batch.vo.UserDto;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class TestDaoImpl extends SqlSessionFactoryService implements TestDao {

	@Autowired
	private MailSender mailSender;

	@Value("${test.sender}")
	private String sender;
	
	@Value("${test.subject}")
	private String subject;

	@Value("${test.content}")
	private String content;
	
	@Override
	public void sendMail() throws Exception {
		UserDto targetUser = getSqlSessionTemplate().selectOne("sendEmailMapper.getTestUser");

		mailSender.sendEmail(targetUser.getEmpCompMail(), sender, subject, content, false, false, "");
		return;
	}
	
}