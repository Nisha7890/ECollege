package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 5/18/2018.
 */

public class BimSyllabusDataList {


    String semester;
    String id;
    String syllabus;


    public BimSyllabusDataList(String semester, String id) {
        this.semester = semester;
        this.id = id;
    }

    public BimSyllabusDataList(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
