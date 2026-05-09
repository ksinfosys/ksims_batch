package com.ksinfo.batch.util;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class MailSender {

	@Value("${BATCH_TARGET_ADMIN}")
	private String targetAdmin;
	@Value("${send.host}")
	private String host;
	@Value("${send.username}")
	private String userName;
	@Value("${send.password}")
	private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

	public void sendEmail(String email, String sender, String subject, String content, boolean sendAdminFlag, boolean sendSlackFlag, String slackEmail) throws Exception {

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

			Message mimeMessage = new MimeMessage(session); // MimeMessage生成
			mimeMessage.setFrom(new InternetAddress(userName));
			// mimeMessage.setFrom(new InternetAddress(senderAddress, sender, "UTF-8")); // 送り元設定
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 送り先設定
			if(sendAdminFlag){
				mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(targetAdmin));
			}
			if(sendSlackFlag){
				mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(slackEmail));
			}
            mimeMessage.setSubject(subject); // タイトル設定
			mimeMessage.setText(content); // 内容設定

			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients()); // javax.mail利用
			transport.close();

		} catch (Exception e) {
            throw new Exception(e);
		}
	}

}