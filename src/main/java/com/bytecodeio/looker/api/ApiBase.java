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

	public String getAuthToken(String apiVersion)throws ApiException{

		String requestUrl=null;
		try{

			HashMap<String, String>params = new HashMap();
			params.put("client_id",  Config.CONFIG_API_KEY);
			params.put("client_secret", Config.CONFIG_SECRET_KEY);

			requestUrl = apiVersion == "3.0" ? Config.CONFIG_API_BASE_3_0 +"/login" : Config.CONFIG_API_BASE_3_1 + "/login";
			String authResponse = RestClient.performPOSTOperation(null, requestUrl, params);
			AuthToken authToken = new AuthToken();
			MappingUtils.populateFromJson(authResponse.toString(), authToken);
			return authToken.accessToken;
		}
		catch(Exception e){
			throw new ApiException(e.getMessage() +", request url: "+ requestUrl);
		}
	}

//	public String getAuthToken(String embeddedUserId)throws ApiException{
		//if(embeddedUserId==null){
//			return getAuthToken(embeddedUserId);
		//}
		//TODO: Implement function...
//		return null;
//	}



}
