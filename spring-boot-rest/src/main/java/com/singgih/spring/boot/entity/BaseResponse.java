package com.singgih.spring.boot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse<T extends Object> {
	@JsonProperty("status")
	private String status = "";
	@JsonProperty("error_message")
	private String errorMessage  = "";
	@JsonProperty("payload")
	private T payload;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public T getPayload() {
		return payload;
	}

}
