package com.mohamed14riad.popularmoviesmvp.mvp.sorting;

import android.content.Context;

public class SortingDialogInteractorImpl implements SortingDialogInteractor {

    private SortingOptionStore sortingOptionStore;

    SortingDialogInteractorImpl(Context context) {
        sortingOptionStore = new SortingOptionStore(context);
    }

    @Override
    public String getSelectedSortingOption() {
        return sortingOptionStore.getSelectedOption();
    }

    @Override
    public void setSortingOption(String option) {
        sortingOptionStore.setSelectedOption(option);
    }
}
