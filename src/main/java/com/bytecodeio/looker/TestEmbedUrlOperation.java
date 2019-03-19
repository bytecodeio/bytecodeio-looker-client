package com.bytecodeio.looker;

import com.bytecodeio.looker.api.EmbedApi;
import com.bytecodeio.looker.util.Config;

public class TestEmbedUrlOperation {
	
	public static void main(String[] args){
		try{
			
			Config config = Config.getConfig(); 
			config.CONFIG_EMBED_SECRET_ID  = "577b70af9697d599928ad0367bd85b7da3be3721fbb607af47c1b0542daaf1c4";
			config.CONFIG_HOST = "bytecode.looker.com";
			
			EmbedApi embedApi = new EmbedApi();
			
			String embedUrl = embedApi.getDashboardEmbedUrl("testembedmatthew", "test", "1234567890", "37","popular_names");
			
			System.out.println(embedUrl);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
