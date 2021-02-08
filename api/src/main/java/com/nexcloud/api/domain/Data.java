package com.nexcloud.api.domain;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private String resultType;
	
	private List<Result> result;

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public List<Result> getResult() {
		if( result == null )
			result = new ArrayList<Result>();
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}
}

