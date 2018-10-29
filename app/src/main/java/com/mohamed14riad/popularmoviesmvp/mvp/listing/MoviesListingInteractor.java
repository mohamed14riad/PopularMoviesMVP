package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface MoviesListingInteractor {
    Observable<List<Movie>> fetchMovies();
}
