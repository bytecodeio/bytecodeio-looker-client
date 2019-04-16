package com.bytecodeio.looker.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;

public class SpaceApi extends ApiBase{

	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 +"/spaces";

	private SpaceApi spaceApi;

	public SpaceApi(){
		super();
	}

	public Space getSpace(String id){

		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/"+ id);

		try{
			Space space = new Space();
			MappingUtils.populateFromJson(responseJson, space);
			return space;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
	}

	public List<Look>getLooksForSpace(String spaceId){
		return getLooksForSpace(spaceId, null);
	}
	
	public List<Look>getLooksForSpace(String spaceId, String[] fields){

		HashMap<String, String> params = new HashMap();
		
		if(fields!=null){
			params.put("fields", getFieldCriteria(fields));
		}
		
		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/"+ spaceId +"/looks", params);
		
		try{
			List<Look>results = MappingUtils.getCollectionFromJson(responseJson, Look.class);
			return results;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}

	}

	public List<Space> searchSpacesByName(String name){
		return searchSpacesByName(name, null);
	}
	
	public List<Space> searchSpacesByName(String name, String[] fields){

		HashMap<String, String> params = new HashMap();
		if(fields!=null){
			params.put("fields", getFieldCriteria(fields));
		}

		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/search", params);

		try{
			List<Space> spaces = MappingUtils.getCollectionFromJson(responseJson, Space.class);
			return spaces;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}

	}

}
