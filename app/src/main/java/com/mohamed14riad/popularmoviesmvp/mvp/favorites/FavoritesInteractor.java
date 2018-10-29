package com.mohamed14riad.popularmoviesmvp.mvp.favorites;

import com.mohamed14riad.popularmoviesmvp.models.Movie;

import java.util.List;

public interface FavoritesInteractor {
    List<Movie> getFavorites();

    void setFavorite(Movie movie);

    void unFavorite(String id);

    boolean isFavorite(String id);
}
