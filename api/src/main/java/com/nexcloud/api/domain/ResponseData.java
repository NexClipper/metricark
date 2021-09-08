package com.nexcloud.api.domain;

// 응답 데이터를 담는 구조체
public class ResponseData {
	private Integer response_code;
	
	private String message;
	
	private String status;
	
	private Object data;

	public Integer getResponse_code() {
		return response_code;
	}

	public void setResponse_code(Integer response_code) {
		this.response_code = response_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

