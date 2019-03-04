package com.bytecodeio.looker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardElement {

	@JsonProperty("id")
	String id;
	
	@JsonProperty("dashboard_id")
	private String dashboardId = null;
	
	@JsonProperty("look_id")
	private String lookId = null;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("look")
	private Look look;
	
	public DashboardElement(){
		
	}
	
	public DashboardElement(String id, String title, String type){
		this.id = id;
		this.title = title;
		this.type = type; 
	}
	
	public String toString(){
		return "id: "+ id +",title: "+ title +", type: "+ type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getLookId() {
		return lookId;
	}

	public void setLookId(String lookId) {
		this.lookId = lookId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Look getLook() {
		return look;
	}

	public void setLook(Look look) {
		this.look = look;
	}
	
}
