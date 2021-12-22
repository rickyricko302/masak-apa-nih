package com.hikki.masakapanih.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchModel{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private List<ResultsSearch> results;

	@SerializedName("status")
	private boolean status;

	public String getMethod(){
		return method;
	}

	public List<ResultsSearch> getResults(){
		return results;
	}

	public boolean isStatus(){
		return status;
	}

	public void setResults(List<ResultsSearch> results) {
		this.results = results;
	}
}