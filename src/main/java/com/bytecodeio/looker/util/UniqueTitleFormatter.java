package com.bytecodeio.looker.util;

import java.util.List;

/**
 * 
 * Formatting unique titles:
 * Given a title and a collection of titles, ensure a unique title is produces based on a naming convention.
 * All copies are prefixed with the 'copy' keyword.
 * 
 * Example:
 * sample title
 * sample title (copy)
 * sample title (copy 1)
 * sample title (copy 2)
 * sample title (copy 3)
 * 
 * In the event that a title and subsequent copies are made, and then the original title is changed, then the next insertion 
 * would result in the original title. 
 * 
 * In the event that any of the copies have been renamed, the new name will inherit the next largest sequence number in the 
 * series of copies. For example, given
 * 
 * sample title
 * sample title (copy 1)
 * sample title (copy 3)
 * 
 * The next insertion of 'sample title' would result in the value 'title (copy 4)'.
 * 
 * 
 * @author jefe
 *
 */
public class UniqueTitleFormatter {
	
	/**
	 * Determine if a title exists in a given set of titles.
	 * 
	 */
	public static boolean titleExists(String title, List<String> existingTitles){
		for(String curTitle:existingTitles){
			if(curTitle.equalsIgnoreCase(title)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Given a collection of titles, 
	 * 
	 */
	public static String getUniqueTitle(String title, List<String> existingTitles){
		
		if(!titleExists(title, existingTitles)){
			return title;
		}
		
		int highestCopyNumber = getHighestCopyNumber(title, existingTitles);
		if(highestCopyNumber==-1){
			return title +" (copy)";
		}
		else if(highestCopyNumber==0){
			return title +" (copy 1)";
		}
		
		highestCopyNumber++;
		return title +" (copy "+  highestCopyNumber +")";
	}
	
	public static int getHighestCopyNumber(String title, List<String> existingTitles){
		int highestCopy = -1, curVersion;
		for(String curTitle:existingTitles){
			if(curTitle.toLowerCase().startsWith(title.toLowerCase())&&curTitle.toLowerCase().endsWith("(copy)")){
				highestCopy = 0;
			}
			else if(curTitle.toLowerCase().startsWith(title.toLowerCase())&&curTitle.toLowerCase().indexOf("(copy")>-1&&!curTitle.toLowerCase().equals(title.toLowerCase())){
				int i = curTitle.indexOf("(copy");
				String numberToken = curTitle.toLowerCase().substring(curTitle.toLowerCase().indexOf("(copy") + 6, curTitle.toLowerCase().length()-1);
				curVersion = new Integer(numberToken);
				if(curVersion>highestCopy){
					highestCopy = curVersion;
				}
			}
		}
		return highestCopy;
	}

}
