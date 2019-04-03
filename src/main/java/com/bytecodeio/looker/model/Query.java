package com.bytecodeio.looker.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {

	@JsonProperty("id")
	private long id;

	@JsonProperty("view")
	private String view;

	@JsonProperty("fields")
	private List<String> fields;

	@JsonProperty("vis_config")
	private VisConfig visConfig;

	@JsonProperty("filters")
	private HashMap <String, String>filters;

    @JsonProperty("sorts")
    private List<String> sorts;

    @JsonProperty("limit")
    private String limit;

    @JsonProperty("model")
    private String model;

    public Query() {

    }


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public VisConfig getVisConfig() {
        return visConfig;
    }

    public void setVisConfig(VisConfig visConfig) {
        this.visConfig = visConfig;
    }

    public HashMap <String, String> getQueryFilters() {
        return filters;
    }

    public void setQueryFilters(HashMap <String, String> filters) {
        this.filters = filters;
    }

	public List<String> getSorts() {
		return sorts;
	}

	public void setSorts(List<String> sorts) {
		this.sorts = sorts;
	}

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
