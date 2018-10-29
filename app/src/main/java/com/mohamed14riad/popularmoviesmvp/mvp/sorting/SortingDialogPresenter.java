package com.mohamed14riad.popularmoviesmvp.mvp.sorting;

public interface SortingDialogPresenter {
    void setView(SortingDialogView view);

    void setLastSavedOption();

    void onPopularMoviesSelected();

    void onHighestRatedMoviesSelected();

    void onFavoritesSelected();

    void destroy();
}
