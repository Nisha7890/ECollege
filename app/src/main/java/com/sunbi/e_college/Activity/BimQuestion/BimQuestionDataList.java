package com.sunbi.e_college.Activity.BimQuestion;

/**
 * Created by Acer on 5/23/2018.
 */

public class BimQuestionDataList {

    String subject;
    String id;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BimQuestionDataList(String subject, String id) {
        this.subject = subject;
        this.id = id;


    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
