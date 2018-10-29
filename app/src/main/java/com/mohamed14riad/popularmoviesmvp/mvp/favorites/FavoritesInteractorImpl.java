package com.mohamed14riad.popularmoviesmvp.mvp.favorites;

import android.content.Context;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesInteractorImpl implements FavoritesInteractor {

    private FavoritesHelper favoritesHelper;

    public FavoritesInteractorImpl(Context context) {
        favoritesHelper = new FavoritesHelper(context);
    }

    @Override
    public List<Movie> getFavorites() {
        try {
            return favoritesHelper.getFavorites();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void setFavorite(Movie movie) {
        favoritesHelper.setFavorite(movie);
    }

    @Override
    public void unFavorite(String id) {
        favoritesHelper.unFavorite(id);
    }

    @Override
    public boolean isFavorite(String id) {
        return favoritesHelper.isFavorite(id);
    }
}
