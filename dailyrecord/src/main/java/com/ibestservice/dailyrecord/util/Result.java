package com.ibestservice.dailyrecord.util;

public class Result {
	private static int SUCC_CODE = 0;
	private static String SUCC_MESG = "success";
	
	private static int EMPTY_CODE = 1;
	private static String EMPTY_MESG = "查询为空";
	
	private static int FAIL_CODE = 2;
	

	private int code;// 状态码
	private String mesg;// 消息
	private Object data;// 数据对象

	private Result(int code, String mesg, Object data) {
		this.code = code;
		this.mesg = mesg;
		this.data = data;
	}

	/**
	 * 成功
	 * 
	 * @param data
	 *            成功数据
	 */
	public static Result returnSuccess(Object data) {
		return new Result(SUCC_CODE, SUCC_MESG, data);
	}

	/**
	 * 查询为空
	 * @return
	 */
	public static Result returnEmptyData() {
		return new Result(EMPTY_CODE, EMPTY_MESG, null);
	}
	
	/**
	 * 失败
	 * 
	 * @param code
	 * @param massege
	 */
	public static Result returnFail(String mesg) {
		return new Result(FAIL_CODE, mesg, null);
	}
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
}
