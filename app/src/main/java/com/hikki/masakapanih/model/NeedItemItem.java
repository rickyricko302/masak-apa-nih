package com.hikki.masakapanih.model;

import com.google.gson.annotations.SerializedName;

public class NeedItemItem{

	@SerializedName("thumb_item")
	private String thumbItem;

	@SerializedName("item_name")
	private String itemName;

	public void setThumbItem(String thumbItem){
		this.thumbItem = thumbItem;
	}

	public String getThumbItem(){
		return thumbItem;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}
}