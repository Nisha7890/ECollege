package com.sunbi.e_college.Activity.BbaQuestion;

/**
 * Created by Acer on 6/7/2018.
 */

public class Bba3rdDataList {

    String year;
    String id;
    String question;

    public Bba3rdDataList(String year, String id) {
        this.year = year;
        this.id = id;
    }

    public Bba3rdDataList(String question) {
        this.question = question;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
