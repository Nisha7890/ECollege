package com.sunbi.e_college.Activity.BimQuestion;

/**
 * Created by Acer on 6/3/2018.
 */

public class Bim8thDataList {

    String year;
    String id;
    String question;

    public Bim8thDataList(String year, String id) {
        this.year = year;
        this.id = id;
    }

    public Bim8thDataList(String question) {
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
