package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 5/15/2018.
 */

public class BbaSyllabusDataList {

    String semester;
    String syllabus;
    String id;

    public BbaSyllabusDataList(String semester, String id) {
        this.semester = semester;
        this.id = id;
    }

    public BbaSyllabusDataList(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    //    public BbaSyllabusDataList(String semester, String syllabus, String id) {
//        this.semester = semester;
//        this.syllabus = syllabus;
//        this.id = id;
//    }
//
//    public BbaSyllabusDataList(String semester, String syllabus) {
//        this.semester = semester;
//        this.syllabus = syllabus;
//    }
//
//
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getSyllabus() {
//        return syllabus;
//    }
//
//    public void setSyllabus(String syllabus) {
//        this.syllabus = syllabus;
//    }
//
//    public BbaSyllabusDataList(String semester) {
//        this.semester = semester;
//    }
//
//    public String getSemester() {
//        return semester;
//    }
//
//    public void setSemester(String semester) {
//        this.semester = semester;
//    }
}
