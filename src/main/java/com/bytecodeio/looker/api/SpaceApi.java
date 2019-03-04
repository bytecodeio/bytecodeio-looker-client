package com.bytecodeio.looker.api;

import java.util.ArrayList;
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
		
		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/"+ spaceId +"/looks");
		
		try{
			List<Look>results = new ArrayList();	
			MappingUtils.populateFromJson(responseJson, results);
			return results;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
	}
	
	public List<Space> searchSpaces(String name){
		
		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 +"/search?name="+ name);
		
		try{
			List<Space> spaces = new ArrayList();
			MappingUtils.populateFromJson(responseJson, spaces);
			return spaces;
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
	}
	
}
