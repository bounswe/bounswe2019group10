package com.example.yallp_android.models;

public class Rating {
    private int numberOfRatings;
    private double rating;

    public Rating(int numberOfRatings, double rating) {
        this.numberOfRatings = numberOfRatings;
        this.rating = rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public double getRating() {
        return rating;
    }
}
