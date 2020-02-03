package com.ibestservice.dailyrecord.service.impl;

import com.ibestservice.dailyrecord.entity.User;
import com.ibestservice.dailyrecord.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ibestservice.dailyrecord.service.UserService;
import javax.annotation.Resource;
import java.util.Map;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    //根据姓名或工号查询个人信息
    public User getUserById(Map<String,Object> paramMap) {
        return userMapper.getUserById(paramMap);
    }
    
    public User getUserByIdName(Integer emplId, String name) {
        return userMapper.getUserByIdName(emplId, name);
    }

	@Override
	public int addUser(User user) {
		return userMapper.addUser(user);
	}
}
