package com.mohamed14riad.popularmoviesmvp.mvp.details;

import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.models.Review;
import com.mohamed14riad.popularmoviesmvp.models.Trailer;

import java.util.List;

public interface MovieDetailsView {
    void showDetails(Movie movie);

    void showTrailers(List<Trailer> trailers);

    void showReviews(List<Review> reviews);

    void showFavorite();

    void showUnFavorite();
}
