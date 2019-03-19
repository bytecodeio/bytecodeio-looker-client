package com.bytecodeio.looker;

import java.util.HashMap;
import java.util.List;

import com.bytecodeio.looker.api.DashboardApi;
import com.bytecodeio.looker.api.EmbedApi;
import com.bytecodeio.looker.api.SpaceApi;
import com.bytecodeio.looker.api.UserApi;
import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.model.User;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.RestClient;

public class SpireonPortalOperations {
	
	Config config = Config.getConfig();
	DashboardApi dashboardApi;
	SpaceApi spaceApi;
	UserApi userApi;
	EmbedApi embedApi;
	String models = "popular_names";
	
	String defaultDashboardName = "Default User Dashboard";
	
	String formatSpaceName(String firstName, String lastName){
		return firstName +" "+ lastName;
	}
	
	void init(){
			
		//Spireon config
		config.CONFIG_API_KEY="tzb9dgRcqBBGfw3pvHJm";
		config.CONFIG_SECRET_KEY="pmBrGJwYmtdHhJpNH4vzRrSF";
		config.CONFIG_API_BASE_3_0 = "https://spireon.looker.com:19999/api/3.0";
		config.CONFIG_API_BASE_3_1 = "https://spireon.looker.com:19999/api/3.1";
		config.CONFIG_EMBED_SECRET_ID = "b29629bdcde937ae46da6d9b6ae1aa7a5d649af4cfbbaba4524c6a4829418b1e";
		config.CONFIG_HOST = "spireon.looker.com";
		
		//Bytecode config
		config.CONFIG_API_KEY="gtfp9GZxRZ2PzyhHPFrG";
		config.CONFIG_SECRET_KEY="XCRvfrpKgdQCjBfH9xw42Z76";
		config.CONFIG_API_BASE_3_0 = "https://bytecode.looker.com:19999/api/3.0";
		config.CONFIG_API_BASE_3_1 = "https://bytecode.looker.com:19999/api/3.1";
		config.CONFIG_EMBED_SECRET_ID = "577b70af9697d599928ad0367bd85b7da3be3721fbb607af47c1b0542daaf1c4";
		config.CONFIG_HOST = "bytecode.looker.com";
		
		dashboardApi = new DashboardApi();
		spaceApi = new SpaceApi();
		userApi = new UserApi();
		embedApi = new EmbedApi();
	}

	
	public User getEmbeddedUser(String externalUserId)throws Exception{
		User user = userApi.getUserByEmbedId(externalUserId);
		return user;
	}
	
	public String getEmbeddedUserSpaceId(String externalUserId)throws Exception{
		User user = getEmbeddedUser(externalUserId);
		if(user==null){
			return null;
		}
		
		return user.getPersonalSpaceId();
	}
	
	public boolean embeddedUserSpaceExists(String externalUserId)throws Exception{
		return getEmbeddedUserSpaceId(externalUserId)!=null;
	}

	public String getEmbeddedUserDefaultDashboardId(String externalUserId)throws Exception{
		String spaceId = getEmbeddedUserSpaceId(externalUserId);
		if(spaceId==null){
			return null;
		}
		
		/**
		 * Note, a search operation will yield multiple results, so we access dashboard by id.
		 */
		Space userSpace = spaceApi.getSpace(spaceId);
		
		for(Dashboard db: userSpace.getDashboards()){
			if(db.getTitle().equals(this.defaultDashboardName)){
				return db.getId();
			}
		}
		
		return null;
	}
	
	public Dashboard getEmbeddedUserDefaultDashboard(String externalUserId)throws Exception{
		String spaceId = getEmbeddedUserSpaceId(externalUserId);
		if(spaceId==null){
			return null;
		}
		
		/**
		 * Note, a search operation will yield multiple results, so we access dashboard by id.
		 */
		Space userSpace = spaceApi.getSpace(spaceId);
		
		for(Dashboard db: userSpace.getDashboards()){
			if(db.getTitle().equals(this.defaultDashboardName)){
				return db;
			}
		}
		
		return null;
	}

	public String getEmbedUrlWithEnvironment(String firstName, String lastName, String externalUserId)throws Exception{
		
		String targetDashboardId;
		String embedUrl;
		
		//If no space for current user exists, then ensure one is created. 
		if(!embeddedUserSpaceExists(externalUserId)){
			System.out.println("First request for embedded user, initializing environment");
			embedUrl = embedApi.getDashboardEmbedUrl(firstName, lastName, externalUserId, "37", models);
			
			int[] codes = {200,401};//Note: 401 response code returned 'only' when making Java to embed url call.
			String response = RestClient.performSimpleGETOperation(embedUrl, new HashMap(), codes);
		}
		else{
			System.out.println("Servicing request for existing embed user '"+ externalUserId +"'");
		}
		
		//Ensure a dashboard exists. 
		if(getEmbeddedUserDefaultDashboard(externalUserId)==null){
			String embedSpaceId = getEmbeddedUserSpaceId(externalUserId);
			Dashboard newDashboard = dashboardApi.createDashboard(defaultDashboardName, embedSpaceId);
			targetDashboardId = newDashboard.getId();
		}
		else{
			targetDashboardId = getEmbeddedUserDefaultDashboardId(externalUserId);
		}
		
		System.out.println("Dashboard for embedded user will be "+ targetDashboardId);
		
		embedUrl = embedApi.getDashboardEmbedUrl(firstName, lastName, externalUserId, targetDashboardId, models);
		
		return embedUrl;
	}
	
	public static void main(String[] args){
		
		try{
			
			SpireonPortalOperations service = new SpireonPortalOperations();
			service.init();
			
			//Bytecode config
			String firstName = "spireon1";
			String lastName = "test";
			String externalUserId = "111222333444555";
			
			//Spireon config
			//String firstName = "testbed";
			//String lastName = "test";
			
			//Determine if a space exists for a given user
			//boolean spaceExists = service.embeddedUserSpaceExists(externalUserId);
				//System.out.println("Embedded user space exists: "+ spaceExists);
			
			//Locate a space for an embedded user based off of their first and last names.
				//String embeddedUserSpace = service.getEmbeddedUserSpaceId(externalUserId);
				//System.out.println("Embedded user space id: "+ embeddedUserSpace);
				
			//Locate a dashboard id for a given user
				//String embeddedUserDashboardId = service.getEmbeddedUserDefaulDashboard(externalUserId);
				//System.out.println("Embedded user dashboard id: "+ embeddedUserDashboardId);
			
			//
				//service.initializeEmbedUserEnvironment(firstName, lastName, externalUserId);
				
			//
				service.getEmbedUrlWithEnvironment(firstName, lastName, externalUserId);
				
			System.out.println("Fin...");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
