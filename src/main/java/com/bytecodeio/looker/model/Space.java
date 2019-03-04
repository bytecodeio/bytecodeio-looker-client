package com.bytecodeio.looker.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Space {
	
	@JsonProperty("id")
	private String id = null;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("is_user_root")
	private boolean isUserRoot;
	
	@JsonProperty("looks")
	private List<Look>looks = new ArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUserRoot() {
		return isUserRoot;
	}

	public void setUserRoot(boolean isUserRoot) {
		this.isUserRoot = isUserRoot;
	}

	public List<Look> getLooks() {
		return looks;
	}

	public void setLooks(List<Look> looks) {
		this.looks = looks;
	}
}
