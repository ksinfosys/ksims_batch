package com.ksinfo.batch.config;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class SqlSessionFactoryService
{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}



