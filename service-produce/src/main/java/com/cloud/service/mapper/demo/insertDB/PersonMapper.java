package com.cloud.service.mapper.demo.insertDB;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.service.entity.demo.insertDB.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
}
