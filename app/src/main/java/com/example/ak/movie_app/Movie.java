package com.example.ak.movie_app;


public class Movie {
    private String title;
    private String image_url;

    public Movie(String title, String image_url){
        this.title = title;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

}
