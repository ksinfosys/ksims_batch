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

	@Override
	public List<UserDto> getTargetUserList() throws Exception {
		List<UserDto> userList = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getUser");
		List<String> nonTargetUserId = getSqlSessionTemplate().selectList("regularPassCheckTaskletMapper.getNonTargetUserId");
		List<UserDto> resUser = userList.stream().filter(u -> !nonTargetUserId.contains(u.getEmpId())).collect(Collectors.toList());
		return resUser;
	}

	@Override
	public void insertMail(List<UserDto> targetUser) throws Exception {
		for (UserDto target : targetUser){
			mailSender.sendEmail(target.getEmpCompMail(), sender, subject, target.getEmpName() + content, true);
		} 

		return;
	}
	
}
