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

import com.ksinfo.batch.dao.ReadMonthlyCheckDaoImpl;
import com.ksinfo.batch.vo.UserDto;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
public class ReadMonthlyCheckTasklet implements Tasklet{
	
	@Autowired
	public ReadMonthlyCheckDaoImpl rmcDao;
	
	private final String year = "0";
	private final String conduct = "1";

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			List<UserDto> yearTargetUser = new ArrayList<UserDto>();
			List<UserDto> conductTargetUser = new ArrayList<UserDto>();

			// yearTargetUser = rmcDao.getTargetUserList(year);
			conductTargetUser = rmcDao.getTargetUserList(conduct);

			// if(!yearTargetUser.isEmpty() || !conductTargetUser.isEmpty()) {
				// rmcDao.insertMail(yearTargetUser, conductTargetUser);
			// }

			if(!conductTargetUser.isEmpty()){
				rmcDao.executeConduct(conductTargetUser);
			}
			
		return RepeatStatus.FINISHED;
	}

	
}
