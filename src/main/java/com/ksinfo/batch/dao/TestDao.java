package com.ksinfo.batch.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao {
	void sendMail() throws Exception;
}
