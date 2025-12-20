package com.ksinfo.batch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ksinfo.batch.vo.UserDto;

@Mapper
public interface ReadRegularPassCheckDao {
	
	List<UserDto> getTargetUserList() throws Exception;
	void insertMail(List<UserDto> targetUser) throws Exception;
}
