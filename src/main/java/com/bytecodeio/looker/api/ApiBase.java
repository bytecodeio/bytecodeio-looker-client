package com.bytecodeio.looker.api;

import java.net.URL;

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
		
		try{
			String requestUrl = Config.CONFIG_API_BASE_3_0 +"/login?client_id="+ Config.CONFIG_API_KEY +"&client_secret="+ Config.CONFIG_SECRET_KEY;
			String authResponse = RestClient.performPOSTOperation(null, requestUrl);
			AuthToken authToken = new AuthToken();
			MappingUtils.populateFromJson(authResponse.toString(), authToken);
			return authToken.accessToken;
		}
		catch(Exception e){
			throw new ApiException(e.getMessage());
		}
	}
	
	public String getAuthToken(String embeddedUserId)throws ApiException{
		if(embeddedUserId==null){
			return getAuthToken();
		}
		//TODO: Implement function...
		return null;
	}
	

	
}
