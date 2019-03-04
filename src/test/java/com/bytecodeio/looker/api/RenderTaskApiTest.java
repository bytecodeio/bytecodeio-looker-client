package com.bytecodeio.looker.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bytecodeio.looker.util.Config;


public class RenderTaskApiTest {

	RenderTaskApi api = new RenderTaskApi();
	
	@Test
	public void testFormatLookHtmlFragment(){
		
		String html = "<head><div>sample</div></head>";
		String expResult = "<html><body><div>sample</div></body></html>";
		
		String result = api.formatLookHtmlFragment(html);
		
		assertTrue(result.equals(expResult));
		
	}
}
