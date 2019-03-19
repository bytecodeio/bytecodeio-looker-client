package com.bytecodeio.looker.api;

import java.util.ArrayList;
import java.util.List;

import com.bytecodeio.looker.model.AuthToken;
import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.DashboardElement;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;
import com.bytecodeio.looker.api.LookApi;
public class DashboardElementApi extends ApiBase{
	
	String apiSuffix_3_1 = Config.CONFIG_API_BASE_3_1 +"/dashboard_elements";
	
	DashboardApi dashboardApi;
	
	public DashboardElementApi(){
		super();
		dashboardApi = new DashboardApi(); 
	}
	
	/**
	 * Spireon call:
	 * 
	 * Note: 3.1.0 branch call
	 * 
	 * @return
	 */
	public DashboardElement addLookToDefaultDashboard(String lookId, String dashboardId)throws ApiException{
		String jsonResponse;
		
		//First a copy of the selected look must be copied into the current user's space. 
			Dashboard dashBoard = dashboardApi.getDashboard(dashboardId);
			LookApi lookApi = new LookApi();
			Look lookCopy = lookApi.copyLookToSpace(lookId, dashBoard.getSpaceId());
		
		//Next, the copy is then added into the dashboard.
			DashboardElement newElement = new DashboardElement();
			newElement.setLookId(lookCopy.getId());
			newElement.setDashboardId(dashboardId);
			newElement.setTitle(lookCopy.getTitle());
			newElement.setType("vis");
			
			try{
				String newElementJson = MappingUtils.serializeToJson(newElement);
				jsonResponse = RestClient.performPOSTOperation(getAuthToken(), apiSuffix_3_1, newElementJson, null);
				newElement = new DashboardElement();
				MappingUtils.populateFromJson(jsonResponse, newElement); 
			}
			catch(Exception e){
				throw new ApiException("Unable to parse response from call");
			}
		
		return newElement;
	}
	
	/**
	 * Spireon call:
	 * 
	 * Note: 3.1.0 branch call
	 * 
	 * @return
	 */
	public void removeTileFromDashboard(String dashboardElementId, String dashboardId)throws ApiException{
		
		//Get reference to dashboard, and locate target element
		DashboardApi dashboardApi = new DashboardApi();
		
		Dashboard dashboard = dashboardApi.getDashboard(dashboardId); 
		Long lookId=null;
		for(DashboardElement curElement:dashboard.getDashboardElements()){
			if(curElement.getId().equals(dashboardElementId)){
				lookId = new Long(curElement.getLookId());//Note datatype from swagger API on Look element is type Long, reference in dashboard is string however....
				break;
			}
		}
		
		//Remove dashboard element.
		RestClient.performDELETEOperation(getAuthToken_3_1(), apiSuffix_3_1 +"/"+ dashboardElementId); 
		
		//Delete referenced look
		LookApi lookApi = new LookApi();
		lookApi.deleteLook(lookId);
	}
	
	public List<DashboardElement>getDashboardElements(String dashboardId){
		DashboardApi dashboardApi = new DashboardApi();
		Dashboard dashboard = dashboardApi.getDashboard(dashboardId);
		if(dashboard==null){
			
		}
		
		List<DashboardElement> elements = new ArrayList();
		for(DashboardElement curElement:dashboard.getDashboardElements()){
			elements.add(new DashboardElement(curElement.getId(), curElement.getTitle(), curElement.getType()));
		}
		return elements;
	}
	
}
