package com.odb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class WebCrawler {
    static final HashSet<String> uniqueLinks = new HashSet<>();

    public static final String SEARCH_URL = "https://www.google.com/search?q=";
    public static final String USER_AGENT =  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    private static final int limit = 20;

    private static final String FILE_PATH = "\\linkTextData\\";
    private static String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

    public static boolean crawl (String keyword) {
       boolean result = crawlSearchPage(keyword);

       if (result)
        crawlSubLinks();

       return result;
    }


    private static boolean crawlSearchPage (String keyword) {
        String request = SEARCH_URL + keyword + "&num=" + limit;
        //without proper User-Agent, we will get 403 error
        System.out.println("Sending request... "+ request  + "\nPlease wait... ");

        Document doc = null;
        try {
            doc = Jsoup.connect(request).userAgent(USER_AGENT).timeout(12000).get();;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        if (doc == null) return false;

        Elements results = doc.select("div#main > div:gt(2) a[href]");

        for (Element result : results) {
            String linkHref = result.attr("href");


            if(linkHref.startsWith("/url?q=" )){
                //use regex to get domain name
                linkHref = linkHref.replace("/url?q=", "");
            }

            if(linkHref.startsWith("/imgres?imgurl=" )){
                //use regex to get domain name
                linkHref = linkHref.replace("/imgres?imgurl=", "");
            }

            Pattern patternObject = Pattern.compile(urlRegex);
            if (verifyUrl(linkHref) && patternObject.matcher(linkHref).find()) {
               uniqueLinks.add(linkHref);
            }
        }

        return true;
    }

    private static boolean verifyUrl(String url) {
        if (uniqueLinks.contains(url)) {
            return false;
        }

        if (url.endsWith(".jpeg") || url.endsWith(".jpg") || url.endsWith(".png")
                || url.endsWith(".pdf") || url.contains("#") || url.contains("?")
                || url.contains("mailto:") || url.startsWith("javascript:") || url.endsWith(".gif")
                ||url.endsWith(".xml")) {
            return false;
        }
        return true;
    }

    private static  void crawlSubLinks()
    {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            String currentURL;

        for (String uniqueLink : uniqueLinks) {
           synchronized (uniqueLinks) {
               currentURL = uniqueLink;
               executor.execute(new URLDataExtractionTask(currentURL));
           }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {   }

        System.out.println("Finished all threads");

    }

    private static class URLDataExtractionTask implements Runnable {
        private String url;

         public URLDataExtractionTask (String url) {
             this.url = url;
         }

        @Override
        public void run() {
            try {
                System.out.println("Crawling... " + this.url);
                Document document = Jsoup.connect(this.url).get();

                String fileName =  document.title().replaceAll("[^a-zA-Z0-9_-]", "")+".txt";
                String filePath = System.getProperty("user.dir") + FILE_PATH + fileName;

                String txt = document.text();
                BufferedWriter out = new BufferedWriter(
                        new FileWriter(filePath , true));

                out.write(url + "\t"  + txt);
                out.close();
            } catch (IOException e) {

            }
        }
    }
}
