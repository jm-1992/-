package com.ibestservice.dailyrecord.service;


import com.ibestservice.dailyrecord.entity.User;
import java.util.Map;

public interface UserService {
    //根据域账号或工号查询个人信息
    public User getUserById(Map<String,Object> paramMap);
    

    public User getUserByIdName(Integer emplId, String name);
    
    public int addUser(User user);

}
