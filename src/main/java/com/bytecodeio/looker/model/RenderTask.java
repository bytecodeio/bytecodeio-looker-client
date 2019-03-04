package com.bytecodeio.looker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RenderTask {
	
	@JsonProperty("id")
	private String id = null;
	
	@JsonProperty("status")
	private String status = null;
	
	@JsonProperty("statusDetail")
	private String statusDetail = null;
	
	@JsonProperty("lookId")
	private Long lookId = null;
	
	@JsonProperty("dashbardId")
	private Long dashboardId = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public Long getLookId() {
		return lookId;
	}

	public void setLookId(Long lookId) {
		this.lookId = lookId;
	}

	public Long getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(Long dashboardId) {
		this.dashboardId = dashboardId;
	}
	
	public String toString(){
		return "id: "+ this.id +", status: "+ this.status +", dashboardId: "+ this.dashboardId +", lookId: "+ this.lookId;
	}
}
