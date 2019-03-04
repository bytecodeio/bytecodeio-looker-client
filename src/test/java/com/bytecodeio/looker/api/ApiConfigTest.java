package com.bytecodeio.looker.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bytecodeio.looker.util.Config;

public class ApiConfigTest {

	@Test
	public void testApiConfig(){
		
		String CONFIG_API_KEY="aaa";
		String CONFIG_SECRET_KEY="bbb";
		String CONFIG_API_BASE_3_0 = "ccc";
		String CONFIG_API_BASE_3_1 = "ddd";
		String CONFIG_TEMPLATE_FOLDER_NAME = "eee";
		String[] CONFIG_DEFAULT_DASHBOARD_LOOKS = {"look1","look2"};
		
		Config config = Config.getConfig();
		config.setProperties(CONFIG_API_KEY, CONFIG_SECRET_KEY, CONFIG_API_BASE_3_0, CONFIG_API_BASE_3_1, CONFIG_TEMPLATE_FOLDER_NAME, CONFIG_DEFAULT_DASHBOARD_LOOKS);
		
		//assertTrue(Config.getConfig().);
		assertTrue(Config.getConfig().CONFIG_API_KEY.equals(CONFIG_API_KEY));
		assertTrue(Config.getConfig().CONFIG_SECRET_KEY.equals(CONFIG_SECRET_KEY));
		assertTrue(Config.getConfig().CONFIG_API_BASE_3_0.equals(CONFIG_API_BASE_3_0));
		assertTrue(Config.getConfig().CONFIG_API_BASE_3_1.equals(CONFIG_API_BASE_3_1));
		assertTrue(Config.getConfig().CONFIG_TEMPLATE_FOLDER_NAME.equals(CONFIG_TEMPLATE_FOLDER_NAME));
		assertTrue(Config.getConfig().CONFIG_DEFAULT_DASHBOARD_LOOKS.equals(CONFIG_DEFAULT_DASHBOARD_LOOKS));
		
	}
	
}
