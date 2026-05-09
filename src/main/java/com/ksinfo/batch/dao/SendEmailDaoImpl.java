package com.ksinfo.batch.dao;


import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;
import com.ksinfo.batch.vo.SendMailDto;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class SendEmailDaoImpl extends SqlSessionFactoryService implements SendEmailDao {

	@Value("${send.host}")
	String host;
	@Value("${send.username}")
	String userName;
	@Value("${send.password}")
	String password;

	@Override
	public void sendMail() throws Exception {
		
		List<SendMailDto> emailList = getSqlSessionTemplate().selectList("sendEmailMapper.findEmailList");

		if(emailList.size() == 0) {
			return;
		}

		List<Long> indexList = emailList.stream().map(SendMailDto::getMailIdx).distinct().collect(Collectors.toList());
		int port = 465; // 決まってるportを使う

		// メールアドレスを該当メールアドレスがに送信すること
		try {
			Properties props = System.getProperties(); // 情報を入れる引数生成

			// SMTP サーバー情報設定変更
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.trust", host);
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			// Session 生成
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un = userName;
				String pw = password;

				@Override
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(un, pw);
				}
			});
			session.setDebug(true); // debug

			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, password);
		
			for(Long index : indexList) {
				List<SendMailDto> target = emailList.stream().filter(t -> t.getMailIdx() == index).collect(Collectors.toList());
				Message mimeMessage = new MimeMessage(session); // MimeMessage生成
				mimeMessage.setFrom(new InternetAddress(userName)); // 送り元設定
				int empIndex = 0;
				for (SendMailDto recipentEmp: target) {
					if(empIndex == 0 && recipentEmp.getIssueAllEmpFlg()) {
						List<String> userList = getSqlSessionTemplate().selectList("sendEmailMapper.findAllUserEmailList");
						InternetAddress[] targetAddress = userList.stream().map(t -> {
							try {
								return new InternetAddress(t);
							} catch (AddressException e) {
								throw new RuntimeException(e);
							}
						}).toArray(InternetAddress[]::new);
						mimeMessage.setRecipients(Message.RecipientType.TO, targetAddress);
					} 
					if (recipentEmp.getEmpCompMail() != null) {
						 switch(recipentEmp.getRecipientType().toUpperCase()) {
				            case "TO":
								mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipentEmp.getEmpCompMail())); // 送り先設定
				                break;
				            case "CC":
								mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(recipentEmp.getEmpCompMail())); 
				                break;
				            case "BCC":
								mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress(recipentEmp.getEmpCompMail())); 
				                break;
				            default:
				                throw new IllegalArgumentException("Invalid recipient type: " + recipentEmp.getEmpCompMail());
				        }
					}
				  empIndex++;
				}
				mimeMessage.setSubject(target.get(0).getSubject()); // タイトル設定
				mimeMessage.setText(target.get(0).getContent()); // 内容設定

				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients()); // javax.mail利用
			}
			transport.close();
		} catch (Exception e) {
            throw new Exception(e);
		}
	}
}
