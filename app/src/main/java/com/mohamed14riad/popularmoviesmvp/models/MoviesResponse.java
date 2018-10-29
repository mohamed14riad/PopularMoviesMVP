package com.mohamed14riad.popularmoviesmvp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> results;

    public MoviesResponse() {
    }

    public List<Movie> getResults() {
        return results;
    }
}
