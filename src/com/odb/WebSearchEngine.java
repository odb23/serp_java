package com.odb;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class WebSearchEngine {
    static ArrayList<String> key = new ArrayList<String>();
    static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
    static int n = 1;
    static Scanner sc = new Scanner(System.in);
    static int R;
    static int[] right;

    public static final String FILE_PATH = "\\linkTextData\\";
    private static File dir = new File(System.getProperty("user.dir") + FILE_PATH);

    public static void main(String[] args) {
        displayProjectInfo();
        // ensure storage directory is clean
        cleanDirectory();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine().replaceAll(" ", "+");
        scanner.close();

        boolean result = WebCrawler.crawl(searchTerm);

        if (!result) return;

        Hashtable<String, Integer> occurrs = new Hashtable<String, Integer>();
        long fileNumber = 0;
        int occur = 0;
        int pg = 0;

        try {
            File[] fileArray = dir.listFiles();
            for (int i = 0; i < fileArray.length; i++) {
                // Searching the word given as an input.
                occur = SearchWord.wordSearch(fileArray[i], searchTerm);
                occurrs.put(fileArray[i].getName(), occur);
                if (occur != 0)
                    pg++;
                fileNumber++;
            }

            if (pg == 0) {
                System.out.println("\n\n\n\n\n\n---------------------------------------------------");
                System.out.println("Given word not found!!");
                System.out.println("Searching for similar words.....");
                SearchWord.altWord(searchTerm);
            }
            else {
                hashing(occurrs, pg);
                Sorting.pageSort(occurrs,pg);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayProjectInfo () {
        System.out.println("*****************************************************");
        System.out.println("********* GOOGLE SEARCH ENGINE RESULT PAGE **********");
        System.out.println("*****************************************************");
        System.out.println("********************** GROUP 8 **********************");
        System.out.println("******************* TEAM MEMBERS ********************\n");
        System.out.println("\tMankinde Musliudeen Opeyemi - 180805021");
        System.out.println("\tOdulaja Oluwadamilare O.- 180805052");
        System.out.println("\tKassim Stephen - 180805051");
        System.out.println("\n***************************************************");
    }


    static void hashing(Hashtable<String, Integer> hashtable, Integer page){
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("| %10s | %20s", "VALUE", "KEY");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        hashtable.forEach(
                (k, v) -> {
                    System.out.format("| %10s | %20s ",  v , k);
                    System.out.println();
                });
        System.out.println("-----------------------------------------------------------------------------");
    }

    private static void cleanDirectory () {
        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (File f : files) {
            if (f.getName().endsWith(".txt")) {
                f.delete(); // may fail mysteriously - returns boolean you may want to check
            }
        }
    }

}