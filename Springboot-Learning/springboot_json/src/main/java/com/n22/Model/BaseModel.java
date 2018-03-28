package com.n22.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务模型基类
 * @author miao
 * @param <T>
 * @date 2017年12月25日
 *
 */
@JsonInclude(Include.NON_NULL)
public class BaseModel {

	private String className = this.getClass().getSimpleName();
	
	/**
	 * 操作是否成功，true成功，false失败
	 */
	private Boolean success;
	
	/**
	 * 自定义操作信息
	 */
	private String message;
	
	/**
	 * 处理结果代码
	 */
	private String code;
	
	/**
	 * 数据
	 */
	private List<Object> data = new ArrayList<Object>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseModel(String code, String message, boolean success, List data){
		this.code = code;
		this.message = message;
		this.success = success;
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Object> getData() {
		return data;
	}

	public String getClassName() {
		return className;
	}
	
	public void addData(Object data){
		this.data.add(data);
	}
}
