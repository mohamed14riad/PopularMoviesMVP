package com.mohamed14riad.popularmoviesmvp.api;

import com.mohamed14riad.popularmoviesmvp.models.MoviesResponse;
import com.mohamed14riad.popularmoviesmvp.models.ReviewsResponse;
import com.mohamed14riad.popularmoviesmvp.models.TrailersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MoviesResponse> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Observable<TrailersResponse> getTrailers(@Path("id") int id);

    @GET("movie/{id}/reviews")
    Observable<ReviewsResponse> getReviews(@Path("id") int id);
}
