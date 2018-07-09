package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 6/28/2018.
 */

public class PostedBooksDataList {

    String  bookname,course,semester,useful,date,publication,book_edition,edition_year,price,book_price,photo;

    public PostedBooksDataList(String bookname, String course, String semester, String useful, String date, String publication, String book_edition, String edition_year, String price, String book_price, String photo) {
        this.bookname = bookname;
        this.course = course;
        this.semester = semester;
        this.useful = useful;
        this.date = date;
        this.publication = publication;
        this.book_edition = book_edition;
        this.edition_year = edition_year;
        this.price = price;
        this.book_price = book_price;
        this.photo = photo;
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

    public String getUseful() {
        return useful;
    }

    public void setUseful(String useful) {
        this.useful = useful;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getBook_edition() {
        return book_edition;
    }

    public void setBook_edition(String book_edition) {
        this.book_edition = book_edition;
    }

    public String getEdition_year() {
        return edition_year;
    }

    public void setEdition_year(String edition_year) {
        this.edition_year = edition_year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
