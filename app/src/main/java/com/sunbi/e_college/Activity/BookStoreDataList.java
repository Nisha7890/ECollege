package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 6/18/2018.
 */

public class BookStoreDataList {

    String bookimg, bookname, course, semester,yesno, price,marketprice,publication, bookedition,editionyear, sellerfirstname,sellerlastname,college,email,contact,address,date;

    public BookStoreDataList(String bookimg, String bookname, String course, String semester, String yesno, String price,String marketprice,String publication, String bookedition, String editionyear, String sellerfirstname,String sellerlastname,String college,String email, String contact, String address,String date) {
        this.bookimg = bookimg;
        this.bookname = bookname;
        this.course = course;
        this.semester = semester;
        this.yesno = yesno;
        this.price = price;
        this.sellerfirstname = sellerfirstname;
        this.sellerlastname = sellerlastname;
        this.date = date;
        this.marketprice = marketprice;
        this.publication = publication;
        this.bookedition = bookedition;
        this.editionyear = editionyear;
        this.college = college;
        this.email = email;
        this.contact = contact;
        this.address = address;

    }

    public String getSellerfirstname() {
        return sellerfirstname;
    }

    public void setSellerfirstname(String sellerfirstname) {
        this.sellerfirstname = sellerfirstname;
    }

    public String getSellerlastname() {
        return sellerlastname;
    }

    public void setSellerlastname(String sellerlastname) {
        this.sellerlastname = sellerlastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getBookedition() {
        return bookedition;
    }

    public void setBookedition(String bookedition) {
        this.bookedition = bookedition;
    }

    public String getEditionyear() {
        return editionyear;
    }

    public void setEditionyear(String editionyear) {
        this.editionyear = editionyear;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYesno() {
        return yesno;
    }

    public void setYesno(String yesno) {
        this.yesno = yesno;
    }

    public String getBookimg() {
        return bookimg;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
