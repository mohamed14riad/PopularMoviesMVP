package com.mohamed14riad.popularmoviesmvp.mvp.details;

import android.content.Context;

import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.models.Review;
import com.mohamed14riad.popularmoviesmvp.models.Trailer;
import com.mohamed14riad.popularmoviesmvp.mvp.favorites.FavoritesInteractor;
import com.mohamed14riad.popularmoviesmvp.mvp.favorites.FavoritesInteractorImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter {

    private MovieDetailsView view;
    private MovieDetailsInteractor movieDetailsInteractor;
    private FavoritesInteractor favoritesInteractor;
    private Disposable trailersSubscription;
    private Disposable reviewSubscription;

    MovieDetailsPresenterImpl(Context context) {
        movieDetailsInteractor = new MovieDetailsInteractorImpl();
        favoritesInteractor = new FavoritesInteractorImpl(context);
    }

    @Override
    public void setView(MovieDetailsView view) {
        this.view = view;
    }

    @Override
    public void showDetails(Movie movie) {
        if (isViewAttached()) {
            view.showDetails(movie);
        }
    }

    @Override
    public void showTrailers(Movie movie) {
        trailersSubscription = movieDetailsInteractor.getTrailers(movie.getMovieId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetTrailersSuccess, this::onGetTrailersFailure);
    }

    @Override
    public void showReviews(Movie movie) {
        reviewSubscription = movieDetailsInteractor.getReviews(movie.getMovieId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetReviewsSuccess, this::onGetReviewsFailure);
    }

    @Override
    public void showFavoriteButton(Movie movie) {
        String id = String.valueOf(movie.getMovieId());
        boolean isFavorite = favoritesInteractor.isFavorite(id);

        if (isViewAttached()) {
            if (isFavorite) {
                view.showFavorite();
            } else {
                view.showUnFavorite();
            }
        }
    }

    @Override
    public void onFavoriteButtonClick(Movie movie) {
        if (isViewAttached()) {
            String id = String.valueOf(movie.getMovieId());
            boolean isFavorite = favoritesInteractor.isFavorite(id);

            if (isFavorite) {
                favoritesInteractor.unFavorite(id);
                view.showUnFavorite();
            } else {
                favoritesInteractor.setFavorite(movie);
                view.showFavorite();
            }
        }
    }

    @Override
    public void destroy() {
        view = null;

        if (trailersSubscription != null && !trailersSubscription.isDisposed()) {
            trailersSubscription.dispose();
        }

        if (reviewSubscription != null && !reviewSubscription.isDisposed()) {
            reviewSubscription.dispose();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private void onGetTrailersSuccess(List<Trailer> trailers) {
        if (isViewAttached()) {
            view.showTrailers(trailers);
        }
    }

    private void onGetTrailersFailure(Throwable e) {
        // Do nothing
    }

    private void onGetReviewsSuccess(List<Review> reviews) {
        if (isViewAttached()) {
            view.showReviews(reviews);
        }
    }

    private void onGetReviewsFailure(Throwable e) {
        // Do nothing
    }
}
