package com.mohamed14riad.popularmoviesmvp.mvp.sorting;

import android.content.Context;

public class SortingDialogPresenterImpl implements SortingDialogPresenter {

    private SortingDialogView view;
    private SortingDialogInteractor sortingDialogInteractor;

    SortingDialogPresenterImpl(Context context) {
        sortingDialogInteractor = new SortingDialogInteractorImpl(context);
    }

    @Override
    public void setView(SortingDialogView view) {
        this.view = view;
    }

    @Override
    public void setLastSavedOption() {
        if (isViewAttached()) {
            String selectedOption = sortingDialogInteractor.getSelectedSortingOption();

            switch (selectedOption) {
                case SortType.MOST_POPULAR:
                    view.setPopularChecked();
                    break;
                case SortType.HIGHEST_RATED:
                    view.setHighestRatedChecked();
                    break;
                default:
                    view.setFavoritesChecked();
                    break;
            }
        }
    }

    @Override
    public void onPopularMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.MOST_POPULAR);
            view.dismissDialog();
        }
    }

    @Override
    public void onHighestRatedMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.HIGHEST_RATED);
            view.dismissDialog();
        }
    }

    @Override
    public void onFavoritesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.FAVORITES);
            view.dismissDialog();
        }
    }

    @Override
    public void destroy() {
        view = null;
    }

    private boolean isViewAttached() {
        return view != null;
    }
}
