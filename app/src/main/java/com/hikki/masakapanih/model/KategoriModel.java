package com.hikki.masakapanih.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KategoriModel{

	@SerializedName("method")
	private String method;

	@SerializedName("results")
	private List<ResultsKategori> results;

	@SerializedName("status")
	private boolean status;

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return method;
	}

	public void setResults(List<ResultsKategori> results){
		this.results = results;
	}

	public List<ResultsKategori> getResults(){
		return results;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}