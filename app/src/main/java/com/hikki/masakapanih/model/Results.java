package com.hikki.masakapanih.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.hikki.masakapanih.model.Author;
import com.hikki.masakapanih.model.NeedItemItem;

public class Results{

	@SerializedName("servings")
	private String servings;

	@SerializedName("times")
	private String times;

	@SerializedName("ingredient")
	private List<String> ingredient;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("author")
	private Author author;

	@SerializedName("step")
	private List<String> step;

	@SerializedName("title")
	private String title;

	@SerializedName("needItem")
	private List<NeedItemItem> needItem;

	@SerializedName("dificulty")
	private String dificulty;

	@SerializedName("desc")
	private String desc;

	public void setServings(String servings){
		this.servings = servings;
	}

	public String getServings(){
		return servings;
	}

	public void setTimes(String times){
		this.times = times;
	}

	public String getTimes(){
		return times;
	}

	public void setIngredient(List<String> ingredient){
		this.ingredient = ingredient;
	}

	public List<String> getIngredient(){
		return ingredient;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setAuthor(Author author){
		this.author = author;
	}

	public Author getAuthor(){
		return author;
	}

	public void setStep(List<String> step){
		this.step = step;
	}

	public List<String> getStep(){
		return step;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setNeedItem(List<NeedItemItem> needItem){
		this.needItem = needItem;
	}

	public List<NeedItemItem> getNeedItem(){
		return needItem;
	}

	public void setDificulty(String dificulty){
		this.dificulty = dificulty;
	}

	public String getDificulty(){
		return dificulty;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}