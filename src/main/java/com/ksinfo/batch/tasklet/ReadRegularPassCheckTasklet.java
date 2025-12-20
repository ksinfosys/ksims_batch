package com.ksinfo.batch.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ksinfo.batch.dao.ReadRegularPassCheckDaoImpl;
import com.ksinfo.batch.vo.UserDto;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class ReadRegularPassCheckTasklet implements Tasklet{
	
	@Autowired
	public ReadRegularPassCheckDaoImpl rpcDao;
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			List<UserDto> targetUser = new ArrayList<UserDto>();
			targetUser = rpcDao.getTargetUserList();

			if(!targetUser.isEmpty()) {
				rpcDao.insertMail(targetUser);
			}
			
		return RepeatStatus.FINISHED;
	}

	
}
