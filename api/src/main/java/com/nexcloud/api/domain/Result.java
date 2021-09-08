package com.nexcloud.api.domain;

import java.util.List;

// 사용된 곳 없음
public class Result {
	private Object metric;
	
	private List<String> value;
	
	private List<List<String>> values;

	public Object getMetric() {
		return metric;
	}

	public void setMetric(Object metric) {
		this.metric = metric;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public List<List<String>> getValues() {
		return values;
	}

	public void setValues(List<List<String>> values) {
		this.values = values;
	}
}

