package com.bytecodeio.looker.api;

import java.util.HashMap;
import java.util.List;

import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.model.User;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;
import com.bytecodeio.looker.util.RestClient;

public class UserApi extends ApiBase{
	
	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 + "/users";
	
	public User getUserByEmbedId(String embedId){
	
		HashMap<String, String>params = new HashMap();
		int[] validCodes = {200, 404};
		String responseJson = RestClient.performGETOperation(getAuthToken(), apiSuffix_3_0 + "/credential/embed/"+ embedId, params, validCodes);
	
		if(responseJson==null){
			return null;
		}
		
		try{ 
			User user = new User();
			MappingUtils.populateFromJson(responseJson, user);
			return user;
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}
		
	}

}
