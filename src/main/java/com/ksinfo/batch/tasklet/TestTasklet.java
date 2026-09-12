package com.ksinfo.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ksinfo.batch.dao.TestDaoImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class TestTasklet implements Tasklet{
	
	@Autowired
	public TestDaoImpl testDao;
	
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		testDao.sendMail();

		return RepeatStatus.FINISHED;
	}
}
