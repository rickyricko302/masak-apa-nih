package com.hikki.masakapanih.model;

import com.google.gson.annotations.SerializedName;

public class Author{

	@SerializedName("datePublished")
	private String datePublished;

	@SerializedName("user")
	private String user;

	public void setDatePublished(String datePublished){
		this.datePublished = datePublished;
	}

	public String getDatePublished(){
		return datePublished;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getUser(){
		return user;
	}
}