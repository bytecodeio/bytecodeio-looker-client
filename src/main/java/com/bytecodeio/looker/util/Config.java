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
	
	private static Config configRef;
	
	public static Config getConfig(){
		if(Config.configRef==null){
			Config.configRef = new Config();
		}
		return Config.configRef;
	}
	
	public static void setProperties(String apiKey, String secretKey, String apiRef_3_0, String apiRef_3_1, String templateFolderName, String[] defaultDashboardLooks){
		
		CONFIG_API_KEY=apiKey;
		CONFIG_SECRET_KEY=secretKey;
		CONFIG_API_BASE_3_0 = apiRef_3_0;
		CONFIG_API_BASE_3_1 = apiRef_3_1;
		CONFIG_TEMPLATE_FOLDER_NAME = templateFolderName;
		CONFIG_DEFAULT_DASHBOARD_LOOKS = defaultDashboardLooks;
		
	}

}
