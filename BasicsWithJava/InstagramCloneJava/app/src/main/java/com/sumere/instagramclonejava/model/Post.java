package com.sumere.instagramclonejava.model;

public class Post {
    private String email;
    private String comment;
    private String downloadURL;

    public Post(String email, String comment, String downloadURL) {
        this.email = email;
        this.comment = comment;
        this.downloadURL = downloadURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
