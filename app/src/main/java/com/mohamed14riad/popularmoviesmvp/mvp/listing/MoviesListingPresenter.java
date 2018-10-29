package com.mohamed14riad.popularmoviesmvp.mvp.listing;

public interface MoviesListingPresenter {
    void setView(MoviesListingView view);

    void displayMovies();

    void destroy();
}
