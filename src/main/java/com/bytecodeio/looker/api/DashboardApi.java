package com.bytecodeio.looker.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.bytecodeio.looker.model.AuthToken;
import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.DashboardElement;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.RenderTask;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class DashboardApi extends ApiBase{

	public static final String DASHBOARD_FORMAT_PDF = "pdf";
	public static final String DASHBOARD_FORMAT_CSV = "csv";
	public static final String DASHBOARD_FORMAT_HTML = "html";
	
	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 +"/dashboards";
	
	public DashboardApi(){
		super();
	}
	
	public List<Dashboard> getDashboardSummaries(String embedUserId)throws ApiException{
		
		String dashboardsJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0);
		
		List<Dashboard>dashboards = new ArrayList();
		try{ 
			MappingUtils.populateFromJson(dashboardsJson, dashboards); 
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
		return dashboards;
	}
	
	public Dashboard getDashboard(String id)throws ApiException{
		
		String dashboardJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/"+ id);
		
		Dashboard dashboard = new Dashboard();
		
		try{ MappingUtils.populateFromJson(dashboardJson, dashboard); }catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
		return dashboard;
	}
	
	/*
	public byte[] getDashboardAsHtml(String id){
		List<String>renderTasks = new ArrayList();
		RenderTask renderTask;
		RenderTaskApi renderApi = new RenderTaskApi();
		StringBuffer dashboardHtml = new StringBuffer();
		
		Dashboard dashboard = getDashboard("37");
		
		for(DashboardElement curElement:dashboard.getDashboardElements()){
			renderTask = renderApi.registerLookRenderTask(curElement.getId(), RenderTaskApi.DASHBOARD_FORMAT_HTML);
			renderTasks.add(renderTask.getId());
		}
		
		for(String renderTaskId:renderTasks){
			renderTask = renderApi.getRenderTask(renderTaskId);
			
			while(true){
				try{ Thread.sleep(1000); }catch(Exception e){} 
				renderTask = renderApi.getRenderTask(renderTask.getId());
				System.out.println(renderTask.getStatus());
				if(renderTask.getStatus().equals(RenderTaskApi.RENDER_STATUS_SUCCESS)){
					break;
				}
			}
		}
		
		for(String renderTaskId:renderTasks){
			dashboardHtml.append(new String(renderApi.getRenderTaskResult(renderTaskId)));
		}
		
		return dashboardHtml.toString().getBytes();
	}*/
	
}
