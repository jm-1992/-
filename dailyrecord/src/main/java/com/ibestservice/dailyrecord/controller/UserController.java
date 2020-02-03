package com.ibestservice.dailyrecord.controller;

import com.alibaba.fastjson.JSONObject;
import com.ibestservice.dailyrecord.entity.User;
import com.ibestservice.dailyrecord.service.UserService;
import com.ibestservice.dailyrecord.util.Result;
import com.ibestservice.dailyrecord.util.PwdUtil;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController{

    @Resource
    UserService userService;

    //获取用户信息
    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public Result getUser(Integer emplId, String name){
    	try {
    		Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("emplId",emplId);
            paramMap.put("name",name);
            User user = userService.getUserById(paramMap);
            if(StringUtils.isEmpty(user)) {
            	return Result.returnEmptyData();
            }
            return Result.returnSuccess(user);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.returnFail(e.getMessage());
    	}
    }
    
    //获取用户信息
    @RequestMapping(value = "getUserByIdName", method = RequestMethod.GET)
    public Result getUserByIdName(Integer emplId, String name){
    	try {
    		User user = userService.getUserByIdName(emplId, name);
            if(StringUtils.isEmpty(user)) {
            	return Result.returnEmptyData();
            }
            return Result.returnSuccess(user);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.returnFail(e.getMessage());
    	}
    }
    
  //获取用户信息
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public Result addUser(@RequestBody String json){
    	try {
    		User user = JSONObject.parseObject(json, User.class);
            if(1 == userService.addUser(user)) {
            	return Result.returnSuccess("新增成功!");
            }
            return Result.returnFail("新增失败!");
    	}catch(Exception e) {
    		e.printStackTrace();
    		return Result.returnFail(e.getMessage());
    	}
    }
    
}
