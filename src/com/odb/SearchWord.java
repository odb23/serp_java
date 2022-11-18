package com.odb;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {

	public static int wordSearch(String data, String word){
		int counter=0;
		String txt = data;
			
		int offset1a = 0;
		
		for (int loc = 0; loc <= txt.length(); loc += offset1a + word.length()) 
		{
			offset1a = searchWord(word, txt.substring(loc));
			if ((offset1a + loc) < txt.length()) {
				counter++;
			}
		}
		
		return counter;
	}

	private static int searchWord(String pat, String txt) {
		BoyerMoore b = new BoyerMoore(pat);
		int offset = b.search(txt);
		return offset;
	}
	
	//finds strings with similar pattern and calls edit distance() on those strings
	public static void findData(Result res, int fileNumber, Matcher matcher, String p1) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		EditDistance.findWord(res, fileNumber, matcher, p1);
	}
	
	/**
	 * Finds all the words which has edit distance 1 to the provided word
	 * @param p1
	 */
	public static void altWord(String p1)
	{
		String line = " ";
		String pattern3 = "[a-zA-Z0-9]+";
		 
		// Create a Pattern object
		Pattern r3 = Pattern.compile(pattern3);
		// Now create matcher object.
		Matcher m3 = r3.matcher(line);
		int fileNumber=0;
		
		ArrayList<Result> results = WebSearchEngine.results;
		for(int i=0 ; i<results.size() ; i++)
		{
			try
			{
				findData(results.get(i),fileNumber,m3,p1);
				fileNumber++;
			} 
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Integer allowedDistance = 1;  // Edit distance allowed
		boolean matchFound = false;  // set to true if word found with edit distance equal to allowedDistance

		System.out.println("Did you mean? ");
		int i=0;
		for( Map.Entry<String, Integer> entry: WebSearchEngine.numbers.entrySet()){
			if(allowedDistance == entry.getValue()) {
				i++;
				System.out.print("("+i+") "+entry.getKey()+"\n");
				matchFound = true;
			}
		}	        
		if(!matchFound) System.out.println("Entered word cannot be resolved.");
	}
}
