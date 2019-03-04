package com.bytecodeio.looker.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UniqueTitleFormatterTest {

	@Test
	public void confirmDuclicateTitleExists() {
		
	}
	
	@Test
	public void getHigestCopyNumber() {
		String title = "sample title";
		List<String>existingTitles = new ArrayList();
		existingTitles.add("sample title (copy)");
		assertTrue(UniqueTitleFormatter.getHighestCopyNumber(title, existingTitles)==0);
		existingTitles.add("sample title (copy 1)");
		assertTrue(UniqueTitleFormatter.getHighestCopyNumber(title, existingTitles)==1);
		existingTitles.add("sample title (copy 2)");
		assertTrue(UniqueTitleFormatter.getHighestCopyNumber(title, existingTitles)==2);
		existingTitles.add("sample title (copy 3)");
		assertTrue(UniqueTitleFormatter.getHighestCopyNumber(title, existingTitles)==3);
		existingTitles.add("sample title (copy 11)");
		assertTrue(UniqueTitleFormatter.getHighestCopyNumber(title, existingTitles)==11);
		
	}
	
	@Test
	public void confirmProducesUniqueTitle(){
		String result;
		String title = "sample title";
		List<String>existing = new ArrayList();
		existing.add(title);
		
		//First scenario, ensure sequential values produced.
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		assertTrue(result.equals("sample title (copy)"));
		existing.add(result);
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		assertTrue(result.equals("sample title (copy 1)"));
		existing.add(result);
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		assertTrue(result.equals("sample title (copy 2)"));
		existing.add(result);
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		assertTrue(result.equals("sample title (copy 3)"));
		
		//Second scenario, ensure sequential numbering continues after a copy is deleted.
		existing = new ArrayList();
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		existing.add("sample title");
		existing.add("sample title (copy 1)");
		existing.add("sample title (copy 3)");
		result = UniqueTitleFormatter.getUniqueTitle(title, existing);
		assertTrue(result.equals("sample title (copy 4)"));
		
	}

}
