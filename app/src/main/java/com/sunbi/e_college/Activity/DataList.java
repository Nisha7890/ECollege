package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 4/24/2018.
 */

public class DataList {

    String title;
    String date;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public DataList(String title, String date, String content) {

        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DataList(String title, String date) {

        this.title = title;
        this.date = date;
    }
}
