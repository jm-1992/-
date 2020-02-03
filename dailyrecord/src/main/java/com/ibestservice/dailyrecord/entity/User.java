package com.ibestservice.dailyrecord.entity;

public class User {
    //用户工号
    private Integer emplId;
    //用户域账号
    private String domainId;
    //用户姓名
    private String name;
    //用户部门
    private String department;
    //用户密码
    private String password;
	public Integer getEmplId() {
		return emplId;
	}
	public void setEmplId(Integer emplId) {
		this.emplId = emplId;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
