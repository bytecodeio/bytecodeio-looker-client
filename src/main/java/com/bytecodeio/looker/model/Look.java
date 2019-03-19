package com.bytecodeio.looker.model;

import java.util.HashMap;

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
	private HashMap <String, String>lookModel;

	@JsonProperty("query")
	private Query query;

	public Look(){

	}

	public Look(String id, String title){
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getTitle() {
        return title;
    }

	public String findTitle() {
		if (title.contains("hard_braking")) {
            return "Hard Braking";
        } else if (title.contains("hard_acceleration")) {
            return "Hard Acceleration";
        } else if (title.contains("idling")) {
            return "Idling";
        } else if (title.contains("distance_traveled")) {
            return "Distance Traveled";
        } else if (title.contains("speed_threshold")) {
            return "Speed Threshold";
        } else if (title.contains("posted_speed")) {
            return "Posted Speed";
		} else {
		    return "";
		}
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

	public HashMap <String, String> getLookModel() {
		return lookModel;
	}

	public void setLookModel(HashMap <String, String> lookModel) {
		this.lookModel = lookModel;
	}

	public String toString(){
		return "id="+ id +",title="+ title +",spaceId="+ spaceId;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

}
