package com.bytecodeio.looker.util;

/**
 * 
 * Singleton configuration entity.
 * 
 * Usage:
 * //Initialize static reference....
 * Config config = Config.getConfig();
 * config.setProperties(...);
 * 
 * //Obtaining a property
 * Config.getConfig().CONFIG_API_KEY....
 * 
 * @author jefe
 *
 */
public class Config {
	
	public static String CONFIG_API_KEY="";
	public static String CONFIG_SECRET_KEY="";
	public static String CONFIG_API_BASE_3_0 = "";
	public static String CONFIG_API_BASE_3_1 = "";
	public static String CONFIG_TEMPLATE_FOLDER_NAME = "";
	public static String[] CONFIG_DEFAULT_DASHBOARD_LOOKS = {};
	/** API key used for generating embed urls. */
	public static String CONFIG_EMBED_SECRET_ID = "";
	/** Host name of looker instance (bytecode.looker.com for example...) */
	public static String CONFIG_HOST = "";
	
	private static Config configRef=null;
	
	private Config(){
		
	}
	
	public static Config getConfig(){
		if(Config.configRef==null){
			Config.configRef = new Config();
		}
		return Config.configRef;
	}
	
	public static void setProperties(String apiKey, String secretKey, String apiRef_3_0, String apiRef_3_1, String templateFolderName, String[] defaultDashboardLooks){
		
		Config.CONFIG_API_KEY=apiKey;
		Config.CONFIG_SECRET_KEY=secretKey;
		Config.CONFIG_API_BASE_3_0 = apiRef_3_0;
		Config.CONFIG_API_BASE_3_1 = apiRef_3_1;
		Config.CONFIG_TEMPLATE_FOLDER_NAME = templateFolderName;
		Config.CONFIG_DEFAULT_DASHBOARD_LOOKS = defaultDashboardLooks;
		
	}
	
	public static void getConfigFromSystemProperties(Config config){
		
		config.CONFIG_API_KEY=System.getProperty("looker-api-key");
		config.CONFIG_SECRET_KEY=System.getProperty("looker-secret-key");
		config.CONFIG_API_BASE_3_0 = System.getProperty("looker-api-3_0");
		config.CONFIG_API_BASE_3_1 = System.getProperty("looker-api_3_1");
		config.CONFIG_EMBED_SECRET_ID = System.getProperty("looker-embed-id");
		config.CONFIG_HOST = System.getProperty("looker-host");
	}

}
