package com.ksinfo.batch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ksinfo.batch.vo.UserDto;

@Mapper
public interface ReadMonthlyCheckDao {
	
	List<UserDto> getTargetUserList(String target) throws Exception;
	void insertMail(List<UserDto> yearTargetUser, List<UserDto> conductTargetUser) throws Exception;
}
