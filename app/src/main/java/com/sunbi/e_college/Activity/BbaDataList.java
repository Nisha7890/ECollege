package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 5/13/2018.
 */

public class BbaDataList {

    String collegename;
    String collegecontact;
    String collegeaddress;
    String collegewebsite;
    String collegeemail;

    public BbaDataList(String collegename, String collegecontact, String collegeaddress, String collegewebsite, String collegeemail) {
        this.collegename = collegename;
        this.collegecontact = collegecontact;
        this.collegeaddress = collegeaddress;
        this.collegewebsite = collegewebsite;
        this.collegeemail = collegeemail;

    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getCollegecontact() {
        return collegecontact;
    }

    public void setCollegecontact(String collegecontact) {
        this.collegecontact = collegecontact;
    }

    public String getCollegeaddress() {
        return collegeaddress;
    }

    public void setCollegeaddress(String collegeaddress) {
        this.collegeaddress = collegeaddress;
    }

    public String getCollegewebsite() {
        return collegewebsite;
    }

    public void setCollegewebsite(String collegewebsite) {
        this.collegewebsite = collegewebsite;
    }

    public String getCollegeemail() {
        return collegeemail;
    }

    public void setCollegeemail(String collegeemail) {
        this.collegeemail = collegeemail;
    }

}
