package com.hikki.masakapanih.model;

import com.google.gson.annotations.SerializedName;

public class ResultsSearch {

	@SerializedName("difficulty")
	private String difficulty;

	@SerializedName("times")
	private String times;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("title")
	private String title;

	@SerializedName("key")
	private String key;

	@SerializedName("serving")
	private String serving;

	public String getDifficulty(){
		return difficulty;
	}

	public String getTimes(){
		return times;
	}

	public String getThumb(){
		return thumb;
	}

	public String getTitle(){
		return title;
	}

	public String getKey(){
		return key;
	}

	public String getServing(){
		return serving;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}