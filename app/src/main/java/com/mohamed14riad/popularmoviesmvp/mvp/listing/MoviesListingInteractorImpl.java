package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import android.content.Context;

import com.mohamed14riad.popularmoviesmvp.api.ApiService;
import com.mohamed14riad.popularmoviesmvp.api.ApiUtil;
import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.models.MoviesResponse;
import com.mohamed14riad.popularmoviesmvp.mvp.favorites.FavoritesInteractor;
import com.mohamed14riad.popularmoviesmvp.mvp.favorites.FavoritesInteractorImpl;
import com.mohamed14riad.popularmoviesmvp.mvp.sorting.SortType;
import com.mohamed14riad.popularmoviesmvp.mvp.sorting.SortingOptionStore;

import java.util.List;

import io.reactivex.Observable;

public class MoviesListingInteractorImpl implements MoviesListingInteractor {

    private ApiService apiService;
    private SortingOptionStore sortingOptionStore;
    private FavoritesInteractor favoritesInteractor;

    MoviesListingInteractorImpl(Context context) {
        apiService = ApiUtil.getApiService();
        sortingOptionStore = new SortingOptionStore(context);
        favoritesInteractor = new FavoritesInteractorImpl(context);
    }

    @Override
    public Observable<List<Movie>> fetchMovies() {
        String selectedOption = sortingOptionStore.getSelectedOption();

        switch (selectedOption) {
            case SortType.MOST_POPULAR:
                return apiService.getPopularMovies().map(MoviesResponse::getResults);
            case SortType.HIGHEST_RATED:
                return apiService.getTopRatedMovies().map(MoviesResponse::getResults);
            default:
                return Observable.just(favoritesInteractor.getFavorites());
        }
    }
}
