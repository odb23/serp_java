package com.odb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {

	public static int wordSearch(File filePath, String word){
		int counter=0;
		String data="";
		try
		{
			BufferedReader Object = new BufferedReader(new FileReader(filePath));
			String line = null;
			
			while ((line = Object.readLine()) != null){
				data= data+line;
			}
			Object.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		// Finding the position of the word...............
		String txt = data;
			
		int offset1a = 0;
		
		for (int loc = 0; loc <= txt.length(); loc += offset1a + word.length()) 
		{
			offset1a = searchWord(word, txt.substring(loc));
			if ((offset1a + loc) < txt.length()) {
				counter++;
				System.out.println("\n"+word+ " at position " + (offset1a + loc));  //printing position of word
			}
		}
		if(counter!=0)	{		
			System.out.println("-------------------------------------------------");
			System.out.println("\nFound in "+filePath.getName()); // Founded from which text file..
			System.out.println("-------------------------------------------------");
		}
		return counter;
	}

	private static int searchWord(String pat, String txt) {
		BoyerMoore b = new BoyerMoore(pat);
		int offset = b.search(txt);
		return offset;
	}
	
	//finds strings with similar pattern and calls edit distance() on those strings
	public static void findData(File sourceFile, int fileNumber, Matcher matcher, String p1) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		EditDistance.findWord(sourceFile, fileNumber, matcher, p1);
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
		
		File dir = new File(System.getProperty("user.dir")+ WebSearchEngine.FILE_PATH);
		File[] fileArray = dir.listFiles();
		for(int i=0 ; i<fileArray.length ; i++)
		{
			try
			{
				findData(fileArray[i],fileNumber,m3,p1);
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
		for(Map.Entry entry: WebSearchEngine.numbers.entrySet()){
			if(allowedDistance == entry.getValue()) {
				i++;
				System.out.print("("+i+") "+entry.getKey()+"\n");
				matchFound = true;
			}
		}	        
		if(!matchFound) System.out.println("Entered word cannot be resolved.");
	}
}
