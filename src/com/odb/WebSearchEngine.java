package com.odb;

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

    static ArrayList<Result> results = new ArrayList<>();

    public static final String FILE_PATH = "\\linkTextData\\";

    public static void main(String[] args) {
        displayProjectInfo();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine().replaceAll(" ", "+");
        scanner.close();

        boolean result = WebCrawler.crawl(searchTerm);

        if (!result) return;

        Hashtable<Result, Integer> occurrs = new Hashtable<Result, Integer>();
        
        int occur = 0;
        int pg = 0;

        try {
            for (Result res : results) {
                // Searching the word given as an input.
                occur = SearchWord.wordSearch(res.getText(), searchTerm);
                occurrs.put(res, occur);
                if (occur != 0)
                    pg++;
            }

            if (pg == 0) {
                System.out.println("\n\n\n\n\n\n---------------------------------------------------");
                System.out.println("Given word not found!!");
                System.out.println("Searching for similar words.....");
                SearchWord.altWord(searchTerm);
            }
            else {
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

}