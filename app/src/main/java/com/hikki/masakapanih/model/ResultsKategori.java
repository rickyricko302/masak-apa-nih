package com.hikki.masakapanih.model;

import com.google.gson.annotations.SerializedName;

public class ResultsKategori {

	@SerializedName("category")
	private String category;

	@SerializedName("url")
	private String url;

	@SerializedName("key")
	private String key;

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}
}