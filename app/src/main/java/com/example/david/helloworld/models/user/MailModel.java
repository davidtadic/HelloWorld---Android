package com.example.david.helloworld.models.user;

/**
 * Created by david on 11.2.2018..
 */

public class MailModel {
    private String subject;
    private String message;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private double rating;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
