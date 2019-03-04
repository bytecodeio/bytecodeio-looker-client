package com.bytecodeio.looker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

	@JsonProperty("access_token")
	public String accessToken;
	
	public String toString(){
		return "AccessToken: "+ accessToken; 
	}
	
}
