package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

import java.util.List;

public interface MoviesListingView {
    void showMovies(List<Movie> movies);

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void onMovieClicked(Movie movie);
}
