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
import com.ksinfo.batch.tasklet.ReadMonthlyCheckTasklet;
import com.ksinfo.batch.tasklet.ReadRegularPassCheckTasklet;
import com.ksinfo.batch.tasklet.SendEmailTasklet;
import com.ksinfo.batch.tasklet.TestTasklet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	TestTasklet TestTasklet;

	@Autowired
	ReadExpirationDateAndInsertTasklet ReadExpirationDateAndInsertTasklet;
	
	@Autowired
	ReadMonthlyCheckTasklet ReadMonthlyCheckTasklet;
	
	@Autowired
	ReadRegularPassCheckTasklet ReadRegularPassCheckTasklet;
	
	@Autowired
	SendEmailTasklet SendEmailTasklet;

/** KSIMSバッチ００番：テスト
	 * step01: テストメール発信
	 */
	@Bean
	public Job testBatch() {
		return jobBuilderFactory.get("KSBAT_PT000")
				.start(testStatus())
				.build();	
	}
	
	/** KSIMSバッチ００－1番：テストメールステップ */
	@Bean
	public Step testStatus() {
		return stepBuilderFactory.get("Test")
				.allowStartIfComplete(true)
				.tasklet(TestTasklet)
				.build();
		}

	/** KSIMSバッチ０１番：在留カード満了67日前、メールジョブ 
	 * 起動：毎日定時（JST 00:00）
	 * step01: 在留カード満了日確認
	 */
	@Bean
	public Job notiExpirationBatch() {
		return jobBuilderFactory.get("KSBAT_PT001")
				.start(readExpirationDateAndInsertStatus())
				.build();	
	}
	
	/** KSIMSバッチ０１－1番：在留カード満了67日前、メールステップ */
	@Bean
	public Step readExpirationDateAndInsertStatus() {
		return stepBuilderFactory.get("ReadExpirationDateAndInsert")
				.allowStartIfComplete(true)
				.tasklet(ReadExpirationDateAndInsertTasklet)
				.build();
		}
		
	/** KSIMSバッチ０２番：毎月2日、勤務表作未登録　メールジョブ 
	 * 起動：毎月2日（JST 07:00）
	 * step01: 関連社員一覧
	 */
	@Bean
	public Job readMonthlyCheckBatch() {
		return jobBuilderFactory.get("KSBAT_PT002")
				.start(readMonthlyCheckStatus())
				.build();	
	}
	
	/** KSIMSバッチ０２－1番：勤務表作未登録、交通費未登録社員　メールステップ */
	@Bean
	public Step readMonthlyCheckStatus() {
		return stepBuilderFactory.get("ReadMonthlyCheck")
				.allowStartIfComplete(true)
				.tasklet(ReadMonthlyCheckTasklet)
				.build();
	}	

	/** KSIMSバッチ０３番：毎月１2日、交通費未登録社員　メールジョブ 
	 * 起動：毎月１2日（JST 07:00）
	 * step01: 関連社員一覧
	 */
	@Bean
	public Job readRegularPassCheckBatch() {
		return jobBuilderFactory.get("KSBAT_PT003")
				.start(readRegularPassCheckStatus())
				.build();	
	}
	
	/** KSIMSバッチ０３－1番：交通費未登録社員　メールステップ */
	@Bean
	public Step readRegularPassCheckStatus() {
		return stepBuilderFactory.get("ReadRegularPassCheck")
				.allowStartIfComplete(true)
				.tasklet(ReadRegularPassCheckTasklet)
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
