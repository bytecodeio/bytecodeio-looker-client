package com.bytecodeio.looker.api;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.bytecodeio.looker.model.AuthToken;
import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.DashboardElement;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.Query;
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
	public DashboardElement addLookToDefaultDashboard(String lookId, String dashboardId, String dateRange, String titleRef)throws ApiException{
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
			newElement.setType("vis");//TODO: Confirm this needs to be hard coded.
			newElement.setSubtitleText(titleRef);

			Query newQuery = new Query();//TODO: This has no impact on dashboard element.... Should remove..
            newQuery = lookCopy.getQuery();
            HashMap<String,String> filters = new HashMap<String, String>();
            filters.put("trip_violations.violation_begin_date", dateRange);
            newQuery.setQueryFilters(filters);

			try{
				String newElementJson = MappingUtils.serializeToJson(newElement);
				jsonResponse = RestClient.performPOSTOperation(getAuthToken_3_1(), apiSuffix_3_1, newElementJson, null);
				newElement = new DashboardElement();
				MappingUtils.populateFromJson(jsonResponse, newElement);
			}
			catch(Exception e){
				throw new ApiException("Unable to parse response from call");
			}

		return newElement;
	}

	/**
	 *  Add a look to a dashboard in the same space.
	 *
	 * @param dashboardId
	 * @return
	 */
	public DashboardElement addLookToDashboard(String dashboardId, Look look, String title, String visualizationType, String titleRef){
		Dashboard dashBoard = dashboardApi.getDashboard(dashboardId);
		LookApi lookApi = new LookApi();
		DashboardElement newElement = new DashboardElement();
		newElement.setLookId(look.getId());
		newElement.setDashboardId(dashboardId);
		newElement.setTitle(title);
		newElement.setType(visualizationType);//TODO: Confirm this needs to be hard coded.
		newElement.setSubtitleText(titleRef);

		try{
			String jsonResponse;
			String newElementJson = MappingUtils.serializeToJson(newElement);
			jsonResponse = RestClient.performPOSTOperation(getAuthToken_3_1(), apiSuffix_3_1, newElementJson, null);
			newElement = new DashboardElement();
			MappingUtils.populateFromJson(jsonResponse, newElement);
			return newElement;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call: " + e.toString());
		}
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

	public DashboardElement getDashboardElement(Long dashboardElementId){
		String jsonResponse = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_1 +"/"+ dashboardElementId);
		DashboardElement tile = new DashboardElement();
		try{
			MappingUtils.populateFromJson(jsonResponse, tile);
			return tile;
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
	}

	public DashboardElement updateDashboardElement(DashboardElement dashboardElement){

		//DashboardElement tile = new DashboardElement();
		try{

			String dashboardElementJson = MappingUtils.serializeToJson(dashboardElement);

			System.out.println("Update dashboard element:");
			System.out.println(dashboardElementJson);

			String jsonResponse = RestClient.performPUTOperation(getAuthToken(), apiSuffix_3_1 +"/"+ dashboardElement.getId(), dashboardElementJson, new HashMap());
			//String jsonResponse = RestClient.performPOSTOperation(getAuthToken(), apiSuffix_3_1 +"/"+ dashboardElement.getId());

			MappingUtils.populateFromJson(jsonResponse, dashboardElement);
			return dashboardElement;
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
	}

}
