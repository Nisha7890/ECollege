package com.sunbi.e_college.Activity.BimQuestion;

/**
 * Created by Acer on 5/23/2018.
 */

public class Bim1stDataList {
    String year;
    String id;
    String question;

    public Bim1stDataList(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bim1stDataList(String year, String id) {
        this.year = year;
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
