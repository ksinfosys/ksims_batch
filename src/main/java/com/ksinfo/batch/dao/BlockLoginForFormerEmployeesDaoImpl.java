package com.ksinfo.batch.dao;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.ksinfo.batch.config.SqlSessionFactoryService;

@Repository
@PropertySource(value = "classpath:sendEmail.properties", encoding = "UTF-8")
public class BlockLoginForFormerEmployeesDaoImpl extends SqlSessionFactoryService implements BlockLoginForFormerEmployeesDao {

	@Override
	public void blockLoginForFormerEmployees() throws Exception {
		getSqlSessionTemplate().update("blockLoginForFormerEmployeesMapper.blockLogin");
	}
}
