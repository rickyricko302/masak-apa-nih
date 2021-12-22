package com.hikki.masakapanih.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResepModel{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private List<ResultsResep> results;

	@SerializedName("status")
	private boolean status;

	public String getMethod(){
		return method;
	}

	public void setResults(List<ResultsResep> results) {
		this.results = results;
	}

	public List<ResultsResep> getResults(){
		return results;
	}

	public boolean isStatus(){
		return status;
	}
}