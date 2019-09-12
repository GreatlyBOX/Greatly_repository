package com.n22.shiro.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;


@JsonInclude(Include.NON_NULL)
public @Data class BaseModel implements Serializable{

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private String className = this.getClass().getSimpleName();


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
	private Object data;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseModel(String code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public BaseModel(){

	}


}
