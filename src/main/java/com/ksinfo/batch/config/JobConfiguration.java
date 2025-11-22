package com.ksinfo.batch.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ksinfo.batch.tasklet.ReadExpirationDateAndInsertTasklet;
import com.ksinfo.batch.tasklet.SendEmailTasklet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	ReadExpirationDateAndInsertTasklet ReadExpirationDateAndInsertTasklet;

	@Autowired
	SendEmailTasklet SendEmailTasklet;

	/** KSIMSバッチ０１番：在留カード満了67日前、メール内容登録ジョブ 
	 * 起動：毎日定時（JST 00:00）
	 * step01: 在留カード満了日確認及びメール内容登録
	 */
	@Bean
	public Job notiExpirationBatch() {
		return jobBuilderFactory.get("KSBAT_PT001")
				.start(readExpirationDateAndInsertStatus())
				.build();	
	}
	
	/** KSIMSバッチ０１－1番：在留カード満了67日前、メール内容登録ステップ */
	@Bean
	public Step readExpirationDateAndInsertStatus() {
		return stepBuilderFactory.get("ReadExpirationDateAndInsert")
				.allowStartIfComplete(true)
				.tasklet(ReadExpirationDateAndInsertTasklet)
				.build();
		}

	/** KSIMSバッチ99番：メール送信ジョブ 
	 * 起動：毎日定時（JST 08:00）
	 * step01: メール送信
	 */
	@Bean
	public Job sendEmailBatch() {
		return jobBuilderFactory.get("KSBAT_PT099")
				.start(sendEmailStatus())
				.build();	
	}
	
	/** KSIMSバッチ99－1番：メール送信ステップ */
	// @Bean
	public Step sendEmailStatus() {
		return stepBuilderFactory.get("sendEmail")
				.allowStartIfComplete(true)
				.tasklet(SendEmailTasklet)
				.build();
	}	
}
