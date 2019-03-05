package com.bytecodeio.looker.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.bytecodeio.looker.model.AuthToken;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;
import com.bytecodeio.looker.util.UniqueTitleFormatter;

public class LookApi extends ApiBase{
	
	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 +"/looks";

	private SpaceApi spaceApi;
	
	public LookApi(){
		super();
		spaceApi = new SpaceApi();
	}
	
	public Look getLook(String id){
		
		String jsonResponse = RestClient.performGETOperation(getAuthToken(null), apiSuffix_3_0 +"/"+ id);
		Look look = new Look();
		try{
			MappingUtils.populateFromJson(jsonResponse, look);
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		return look;
	}
	
	public Look createLook(Look look){
		String lookJson;
		Space space = spaceApi.getSpace(look.getSpaceId());
		
		List<String>existingTitles = new ArrayList();
		boolean lookTitleExists = false;
		for(Look curLook:space.getLooks()){
			existingTitles.add(curLook.getTitle());
		}
		look.setTitle(UniqueTitleFormatter.getUniqueTitle(look.getTitle(), existingTitles));
		
		try{
			lookJson = MappingUtils.serializeToJson(look);
		}catch(Exception e){
			throw new ApiException("Unable to map object to json format");
		}
		
		String jsonResponse = RestClient.performPOSTOperation(getAuthToken(null), apiSuffix_3_0, lookJson);
		look = new Look();
		
		try{
			MappingUtils.populateFromJson(jsonResponse, look);
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
		return look;
	}
	
	public Look copyLookToSpace(String lookId, String spaceId){
		String lookJson;
		
		Look look = getLook(lookId);
		Look lookCopy = new Look();
		
		try{
			BeanUtils.copyProperties(lookCopy, look);
		}
		catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
		lookCopy.setSpaceId(spaceId);
		lookCopy.setId(null);
		
		look = createLook(lookCopy);
		
		return look;
	}
	
	public void deleteLook(Long lookId){
		RestClient.performDELETEOperation(getAuthToken(null), apiSuffix_3_0 +"/"+ lookId);
	}
}
