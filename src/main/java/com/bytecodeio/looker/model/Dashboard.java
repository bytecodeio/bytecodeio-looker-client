package com.bytecodeio.looker.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dashboard {

	@JsonProperty("id")
	private String id = null;

	@JsonProperty("title")
	private String title = null;

	@JsonProperty("description")
	private String description  = null;

	@JsonProperty("space_id")
    private String spaceId;

	@JsonProperty("dashboard_elements")
	private List<DashboardElement> dashboardElements = new ArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DashboardElement> getDashboardElements() {
		return dashboardElements;
	}

	public void setDashboardElements(List<DashboardElement> dashboardElements) {
		this.dashboardElements = dashboardElements;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String toString(){
		return "id: "+ id +",title: "+ title +", description: "+ description +", dashboard element count: "+ this.dashboardElements.size();
	}
}
