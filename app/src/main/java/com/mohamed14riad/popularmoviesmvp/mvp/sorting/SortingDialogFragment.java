package com.mohamed14riad.popularmoviesmvp.mvp.sorting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mohamed14riad.popularmoviesmvp.R;
import com.mohamed14riad.popularmoviesmvp.mvp.listing.MoviesListingPresenter;
import com.mohamed14riad.popularmoviesmvp.utils.AppConstants;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SortingDialogFragment extends DialogFragment
        implements SortingDialogView, RadioGroup.OnCheckedChangeListener {

    private SortingDialogPresenter sortingDialogPresenter;
    private static MoviesListingPresenter moviesListingPresenter;

    private RadioButton mostPopular = null;
    private RadioButton highestRated = null;
    private RadioButton favorites = null;
    private RadioGroup sortingOptionsGroup = null;

    private static final String API_KEY = AppConstants.API_KEY;

    public static SortingDialogFragment newInstance(MoviesListingPresenter moviesListingPresenter) {
        SortingDialogFragment.moviesListingPresenter = moviesListingPresenter;
        return new SortingDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        sortingDialogPresenter = new SortingDialogPresenterImpl(getContext());
        sortingDialogPresenter.setView(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.sorting_options, null);

        mostPopular = (RadioButton) dialogView.findViewById(R.id.most_popular);
        highestRated = (RadioButton) dialogView.findViewById(R.id.highest_rated);
        favorites = (RadioButton) dialogView.findViewById(R.id.favorites);
        sortingOptionsGroup = (RadioGroup) dialogView.findViewById(R.id.sorting_group);

        initViews();

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.sort_by);
        dialog.show();

        return dialog;
    }

    private void initViews() {
        if (API_KEY.isEmpty()) {
            mostPopular.setEnabled(false);
            highestRated.setEnabled(false);
            favorites.setEnabled(false);
        } else {
            sortingDialogPresenter.setLastSavedOption();

            sortingOptionsGroup.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.most_popular:
                sortingDialogPresenter.onPopularMoviesSelected();
                moviesListingPresenter.displayMovies();
                break;
            case R.id.highest_rated:
                sortingDialogPresenter.onHighestRatedMoviesSelected();
                moviesListingPresenter.displayMovies();
                break;
            case R.id.favorites:
                sortingDialogPresenter.onFavoritesSelected();
                moviesListingPresenter.displayMovies();
                break;
        }
    }

    @Override
    public void setPopularChecked() {
        mostPopular.setChecked(true);
    }

    @Override
    public void setHighestRatedChecked() {
        highestRated.setChecked(true);
    }

    @Override
    public void setFavoritesChecked() {
        favorites.setChecked(true);
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        sortingDialogPresenter.destroy();
    }
}
