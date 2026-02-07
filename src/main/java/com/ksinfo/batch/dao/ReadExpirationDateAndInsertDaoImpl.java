package com.ksinfo.batch.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.util.MailSender;
import com.ksinfo.batch.vo.UserDto;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class ReadExpirationDateAndInsertDaoImpl extends SqlSessionFactoryService implements ReadExpirationDateAndInsertDao {

	@Autowired
	private MailSender mailSender;

	@Value("${expiration.sender}")
	private String sender;

	@Value("${expiration.subject}")
	private String subject;
	
	@Value("${expiration.content}")
	private String content;

	@Override
	public List<UserDto> getTargetUserList() throws Exception {
		return getSqlSessionTemplate().selectList("residenceCardMapper.getTargetUser");
	}

	@Override
	public void insertMail(List<UserDto> targetUser) throws Exception {
		List<UserDto> insertTarget = new ArrayList<UserDto>();
		for (UserDto target : targetUser) {
			mailSender.sendEmail(target.getEmpCompMail(), sender, subject, target.getEmpName() + content, true);
			
			if(target.getMailIdx() == null || target.getMailIdx().isEmpty()) {
				target.setIssueToDate(target.getStayExpirationDate());
				insertTarget.add(target);
			}
		}
		if(insertTarget.size() > 0){
			getSqlSessionTemplate().insert("residenceCardMapper.insertMailMgt", insertTarget);
		}

		return;
	}
	
}
