package com.odb;

public class Result {
    private String url;
    private String title;
    private String text;

    public Result (String url) {
        this.url = url;
    }

    public synchronized String getUrl() {
        return url;
    }

    public synchronized String getText() {
        return text;
    }
    public synchronized void setText(String text) {
        this.text = text;
    }
    public synchronized String getTitle() {
        return title;
    }
    public synchronized void setTitle(String text) {
        this.title = text;
    }

    @Override
    public String toString() {
       return "URL: " + this.url
               + "\nTitle: " + this.title
               + "\nDescription: " + (this.text.length() > 75 ? this.text.substring(0, 75)+ "..." : this.text);
    }
    
}
