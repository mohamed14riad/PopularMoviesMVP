package com.mohamed14riad.popularmoviesmvp.mvp.details;

import com.mohamed14riad.popularmoviesmvp.models.Review;
import com.mohamed14riad.popularmoviesmvp.models.Trailer;

import java.util.List;

import io.reactivex.Observable;

public interface MovieDetailsInteractor {
    Observable<List<Trailer>> getTrailers(int id);

    Observable<List<Review>> getReviews(int id);
}
