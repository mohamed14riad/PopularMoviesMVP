package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import android.content.Context;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesListingPresenterImpl implements MoviesListingPresenter {

    private MoviesListingView view;
    private MoviesListingInteractor moviesInteractor;
    private Disposable fetchSubscription;

    MoviesListingPresenterImpl(Context context) {
        moviesInteractor = new MoviesListingInteractorImpl(context);
    }

    @Override
    public void setView(MoviesListingView view) {
        this.view = view;
        displayMovies();
    }

    @Override
    public void displayMovies() {
        showLoading();

        fetchSubscription = moviesInteractor.fetchMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieFetchSuccess, this::onMovieFetchFailed);
    }

    @Override
    public void destroy() {
        view = null;

        if (fetchSubscription != null && !fetchSubscription.isDisposed()) {
            fetchSubscription.dispose();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private void showLoading() {
        if (isViewAttached()) {
            view.loadingStarted();
        }
    }

    private void onMovieFetchSuccess(List<Movie> movies) {
        if (isViewAttached()) {
            view.showMovies(movies);
        }
    }

    private void onMovieFetchFailed(Throwable e) {
        view.loadingFailed(e.getMessage());
    }
}
