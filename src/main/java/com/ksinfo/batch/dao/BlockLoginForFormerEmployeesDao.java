package com.ksinfo.batch.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockLoginForFormerEmployeesDao {
	void blockLoginForFormerEmployees() throws Exception;
}
