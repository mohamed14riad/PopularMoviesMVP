package com.mohamed14riad.popularmoviesmvp.mvp.details;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

public interface MovieDetailsPresenter {
    void setView(MovieDetailsView view);

    void showDetails(Movie movie);

    void showTrailers(Movie movie);

    void showReviews(Movie movie);

    void showFavoriteButton(Movie movie);

    void onFavoriteButtonClick(Movie movie);

    void destroy();
}
