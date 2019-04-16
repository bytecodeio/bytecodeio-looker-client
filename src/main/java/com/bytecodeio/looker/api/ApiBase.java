package com.bytecodeio.looker.api;

import java.util.HashMap;

import com.bytecodeio.looker.model.AuthToken;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;

public class ApiBase {

	private Config config;

	public ApiBase(){
		this.config = Config.getConfig();
	}

	public String getAuthToken()throws ApiException{
		//System.out.println("Requesting auth token (3.0)");

		String requestUrl=null;
		try{

			HashMap<String, String>params = new HashMap();
			params.put("client_id",  Config.CONFIG_API_KEY);
			params.put("client_secret", Config.CONFIG_SECRET_KEY);

			requestUrl = Config.CONFIG_API_BASE_3_0 +"/login";
			String authResponse = RestClient.performPOSTOperation(null, requestUrl, params);
			AuthToken authToken = new AuthToken();
			MappingUtils.populateFromJson(authResponse.toString(), authToken);
			return authToken.accessToken;
		}
		catch(Exception e){
			throw new ApiException(e.getMessage() +", request url: "+ requestUrl);
		}
	}

	public String getAuthToken_3_1()throws ApiException{
		//System.out.println("Requesting auth token (3.1)");

		String requestUrl=null;
		try{

			HashMap<String, String>params = new HashMap();
			params.put("client_id",  Config.CONFIG_API_KEY);
			params.put("client_secret", Config.CONFIG_SECRET_KEY);

			requestUrl = Config.CONFIG_API_BASE_3_1 +"/login";
			String authResponse = RestClient.performPOSTOperation(null, requestUrl, params);
			AuthToken authToken = new AuthToken();
			MappingUtils.populateFromJson(authResponse.toString(), authToken);
			return authToken.accessToken;
		}
		catch(Exception e){
			throw new ApiException(e.getMessage() +", request url: "+ requestUrl);
		}
	}

	public String getAuthToken(String embeddedUserId)throws ApiException{
		if(embeddedUserId==null){
			return getAuthToken();
		}
		//TODO: Implement function...
		return null;
	}


	public String getFieldCriteria(String[] fieldCriteria){
		StringBuilder sb = new StringBuilder();
		for(String curField : fieldCriteria){
			sb.append(curField +",");
		}
		
		String result = sb.toString();
		
		if(result.endsWith(",")){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}

}
