package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mohamed14riad.popularmoviesmvp.R;
import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.mvp.sorting.SortingDialogFragment;
import com.mohamed14riad.popularmoviesmvp.mvp.details.MovieDetailsActivity;
import com.mohamed14riad.popularmoviesmvp.mvp.details.MovieDetailsFragment;
import com.mohamed14riad.popularmoviesmvp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import static com.mohamed14riad.popularmoviesmvp.mvp.listing.MoviesListingActivity.twoPaneMode;

public class MoviesListingFragment extends Fragment implements MoviesListingView {

    private MoviesListingPresenter moviesPresenter;

    /* First You Should Insert Your themoviedb.org API KEY In AppConstants Class */
    private static final String API_KEY = AppConstants.API_KEY;

    private Snackbar snackbar = null;
    private ProgressBar progressBar = null;

    private List<Movie> movieList = null;

    private MoviesListingAdapter moviesListingAdapter = null;
    private RecyclerView moviesRecyclerView = null;

    private boolean isItemSelected = false;

    public MoviesListingFragment() {
        // Required empty public constructor
    }

    public static MoviesListingFragment newInstance() {
        return new MoviesListingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        moviesPresenter = new MoviesListingPresenterImpl(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please Obtain Your API KEY First From themoviedb.org", Toast.LENGTH_LONG).show();
            return null;
        }

        moviesListingAdapter = new MoviesListingAdapter(getContext(), this);

        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        moviesRecyclerView.setAdapter(moviesListingAdapter);

        progressBar = (ProgressBar) rootView.findViewById(R.id.main_progress_bar);

        movieList = new ArrayList<>();
        movieList.clear();

//        snackbar = Snackbar.make(moviesRecyclerView, "Check Your Internet Connection!", Snackbar.LENGTH_INDEFINITE);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesPresenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_item:
                displaySortingOptions();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySortingOptions() {
        DialogFragment sortingDialogFragment = SortingDialogFragment.newInstance(moviesPresenter);
        sortingDialogFragment.show(getFragmentManager(), "Sorting");
    }

    @Override
    public void showMovies(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        moviesListingAdapter.addNewData(movies);

        progressBar.setVisibility(View.GONE);
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }

        if (twoPaneMode && !movieList.isEmpty()) {
            if (!isItemSelected) {
                loadDetailsFragment(movieList.get(0));
            }
        }

        if (movieList.isEmpty()) {
            snackbar = Snackbar.make(moviesRecyclerView, "Favorites List is Empty.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void loadingStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingFailed(String errorMessage) {
        snackbar = Snackbar.make(moviesRecyclerView, errorMessage, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        isItemSelected = true;

        if (twoPaneMode) {
            loadDetailsFragment(movie);
        } else {
            startDetailsActivity(movie);
        }
    }

    private void loadDetailsFragment(Movie movie) {
        if (movie != null) {
            MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_container, movieDetailsFragment, "MovieDetailsFragment")
                    .commit();
        }
    }

    private void startDetailsActivity(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra("selectedMovie", movie);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        moviesPresenter.destroy();
    }
}
