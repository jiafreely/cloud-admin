package com.cloud.service.service.demo.insertDB.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service.entity.demo.insertDB.Person;
import com.cloud.service.mapper.demo.insertDB.PersonMapper;
import com.cloud.service.service.demo.insertDB.PersonService;
import org.springframework.stereotype.Service;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: PersonServiceImpl
 * @date 2021/12/23 16:49
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
}
