package com.hikki.masakapanih.model;

import com.google.gson.annotations.SerializedName;

public class DetailResepModel{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private Results results;

	@SerializedName("status")
	private boolean status;

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return method;
	}

	public void setResults(Results results){
		this.results = results;
	}

	public Results getResults(){
		return results;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}