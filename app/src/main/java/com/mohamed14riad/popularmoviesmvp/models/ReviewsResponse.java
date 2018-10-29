package com.mohamed14riad.popularmoviesmvp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("results")
    private List<Review> results;

    public ReviewsResponse() {
    }

    public List<Review> getResults() {
        return results;
    }
}
