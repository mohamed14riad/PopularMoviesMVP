package com.mohamed14riad.popularmoviesmvp.mvp.details;

import com.mohamed14riad.popularmoviesmvp.api.ApiService;
import com.mohamed14riad.popularmoviesmvp.api.ApiUtil;
import com.mohamed14riad.popularmoviesmvp.models.Review;
import com.mohamed14riad.popularmoviesmvp.models.ReviewsResponse;
import com.mohamed14riad.popularmoviesmvp.models.Trailer;
import com.mohamed14riad.popularmoviesmvp.models.TrailersResponse;

import java.util.List;

import io.reactivex.Observable;

public class MovieDetailsInteractorImpl implements MovieDetailsInteractor {

    private ApiService apiService;

    MovieDetailsInteractorImpl() {
        apiService = ApiUtil.getApiService();
    }

    @Override
    public Observable<List<Trailer>> getTrailers(int id) {
        return apiService.getTrailers(id).map(TrailersResponse::getResults);
    }

    @Override
    public Observable<List<Review>> getReviews(int id) {
        return apiService.getReviews(id).map(ReviewsResponse::getResults);
    }
}
