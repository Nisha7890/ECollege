package com.sunbi.e_college.Activity;

/**
 * Created by Acer on 5/25/2018.
 */

public class TestimonialsDataList {

   String image;
    String name;
    String stream;
    String batch;
    String message;


    public TestimonialsDataList( String image, String name, String stream, String batch, String message) {
        this.image = image;
        this.name = name;
        this.stream = stream;
        this.batch = batch;
        this.message = message;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
