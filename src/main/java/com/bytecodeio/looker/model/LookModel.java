package com.bytecodeio.looker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LookModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name = null;

    @JsonProperty("project_name")
	private String projectName = null;

	@JsonProperty("allowed_db_connection_names")
	private List<String> allowedDbConnectionNames = null;

	@JsonProperty("unlimited_db_connections")
	private Boolean unlimitedDbConnections = null;

	@JsonProperty("has_content")
	private Boolean hasContent = null;

	@JsonProperty("label")
	private String label = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<String> getAllowedDbConnectionNames() {
		return allowedDbConnectionNames;
	}

	public void setAllowedDbConnectionNames(List<String> allowedDbConnectionNames) {
		this.allowedDbConnectionNames = allowedDbConnectionNames;
	}

	public Boolean getUnlimitedDbConnections() {
		return unlimitedDbConnections;
	}

	public void setUnlimitedDbConnections(Boolean unlimitedDbConnections) {
		this.unlimitedDbConnections = unlimitedDbConnections;
	}

	public Boolean getHasContent() {
		return hasContent;
	}

	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
