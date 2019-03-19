package com.bytecodeio.looker.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisConfig {

	@JsonProperty("type")
	private String type;

    public String getType() {
        return type;
    }

	public String findTypeString() {
	    switch (type) {
	        case "looker_line":
	            return "Trendline";
            case "table":
                return "List";
            case "looker_map":
                return "HeatMap";
            default:
                return "";
	    }
	}

	public void setType(String type) {
		this.type = type;
	}

}
