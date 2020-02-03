package com.ibestservice.dailyrecord.mapper;

import com.ibestservice.dailyrecord.entity.User;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface UserMapper {

    //根据域账号或工号查询个人信息
    public User getUserById(@Param("paramMap")Map<String,Object> paramMap);
    
    public User getUserByIdName(@RequestParam("emplId")Integer emplId, @RequestParam("emplId")String name);
    
    public int addUser(User user);
  
}
