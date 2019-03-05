package com.bytecodeio.looker.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("vis_config")
	private HashMap<String, String>visConfig;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, String> getVisConfig() {
		return visConfig;
	}

	public void setVisConfig(HashMap<String, String> visConfig) {
		this.visConfig = visConfig;
	}
	
}
