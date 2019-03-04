package com.bytecodeio.looker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Look {
	@JsonProperty("id")
	private String id = null;
	
	@JsonProperty("title")
	String title;
	
	@JsonProperty("space_id")
	private String spaceId;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("query_id")
	private Long queryId = null;
	
	@JsonProperty("model")
	private LookModel lookModel;
	
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

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}

	public LookModel getLookModel() {
		return lookModel;
	}

	public void setLookModel(LookModel lookModel) {
		this.lookModel = lookModel;
	}

	public String toString(){
		return "id="+ id +",title="+ title +",spaceId="+ spaceId;
	}
}
