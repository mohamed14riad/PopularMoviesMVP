package com.mohamed14riad.popularmoviesmvp.mvp.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mohamed14riad.popularmoviesmvp.R;
import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.models.Review;
import com.mohamed14riad.popularmoviesmvp.models.Trailer;
import com.mohamed14riad.popularmoviesmvp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsFragment extends Fragment
        implements MovieDetailsView, View.OnClickListener, TrailersAdapter.OnTrailerClickListener {

    private MovieDetailsPresenter movieDetailsPresenter;

    private Movie movie = null;

    private View rootView = null;

    private ImageView moviePoster = null;
    private TextView movieTitle = null, movieRating = null, movieReleaseDate = null,
            movieOverview = null, trailersLabel = null, reviewsLabel = null;

    private LinearLayout reviewsLayout = null;

    private FloatingActionButton favoriteButton = null;

    private static final String BASE_BACKDROP_URL = AppConstants.BASE_BACKDROP_URL;

    private List<Trailer> trailerList = null;
    private List<Review> reviewList = null;

    private RecyclerView trailersRecyclerView = null;
    private TrailersAdapter trailersAdapter = null;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        movieDetailsFragment.setArguments(args);

        return movieDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        movieDetailsPresenter = new MovieDetailsPresenterImpl(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_details, container, false);

        moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        movieRating = (TextView) rootView.findViewById(R.id.movie_rating);
        movieReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        movieOverview = (TextView) rootView.findViewById(R.id.movie_overview);
        trailersLabel = (TextView) rootView.findViewById(R.id.trailers_label);
        reviewsLabel = (TextView) rootView.findViewById(R.id.reviews_label);

        reviewsLayout = (LinearLayout) rootView.findViewById(R.id.reviews_layout);

        favoriteButton = (FloatingActionButton) rootView.findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(this);

        trailerList = new ArrayList<>();
        reviewList = new ArrayList<>();

        trailersAdapter = new TrailersAdapter(getContext(), this);

        trailersRecyclerView = (RecyclerView) rootView.findViewById(R.id.trailers_recycler_view);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.setAdapter(trailersAdapter);

        setToolbar();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("movie")) {
            movie = getArguments().getParcelable("movie");

            if (movie != null) {
                movieDetailsPresenter.setView(this);
                movieDetailsPresenter.showDetails((movie));
                movieDetailsPresenter.showFavoriteButton(movie);
            }
        }
    }

    private void setToolbar() {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        collapsingToolbar.setTitle(getString(R.string.movie_details));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            // Not in twoPaneMode
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public void showDetails(Movie movie) {
        Uri backdropUrl = null;
        String backdropPath = movie.getBackdropPath();
        if (backdropPath != null && !backdropPath.isEmpty()) {
            backdropUrl = Uri.parse(BASE_BACKDROP_URL.concat(backdropPath));
        }

        Glide.with(this)
                .asBitmap()
                .load(backdropUrl)
                .apply(new RequestOptions().placeholder(R.color.colorPrimary).error(R.color.colorPrimary))
                .into(moviePoster);
        movieTitle.setText(movie.getMovieTitle());
        movieRating.setText(String.valueOf(movie.getVoteAverage()).concat("/10"));
        movieReleaseDate.setText("Release Date: ".concat(movie.getReleaseDate()));
        movieOverview.setText(movie.getOverview());

        movieDetailsPresenter.showTrailers(movie);
        movieDetailsPresenter.showReviews(movie);
    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        trailerList.clear();
        trailerList = trailers;

        if (trailerList.isEmpty()) {
            trailersLabel.setVisibility(View.GONE);
            trailersRecyclerView.setVisibility(View.GONE);
        } else {
            trailersLabel.setVisibility(View.VISIBLE);
            trailersRecyclerView.setVisibility(View.VISIBLE);

            trailersAdapter.addTrailers(trailerList);
        }
    }

    @Override
    public void showReviews(List<Review> reviews) {
        reviewList.clear();
        reviewList = reviews;

        if (reviewList.isEmpty()) {
            reviewsLabel.setVisibility(View.GONE);
            reviewsLayout.setVisibility(View.GONE);
        } else {
            reviewsLabel.setVisibility(View.VISIBLE);
            reviewsLayout.setVisibility(View.VISIBLE);

            reviewsLayout.removeAllViews();

            LayoutInflater inflater = getLayoutInflater();
            for (Review review : reviewList) {
                View viewContainer = inflater.inflate(R.layout.review, reviewsLayout, false);

                TextView reviewAuthor = viewContainer.findViewById(R.id.review_author);
                TextView reviewContent = viewContainer.findViewById(R.id.review_content);

                reviewAuthor.setText(review.getAuthor());
                reviewContent.setText(review.getContent());

                reviewContent.setOnClickListener(this);

                reviewsLayout.addView(viewContainer);
            }
        }
    }

    @Override
    public void showFavorite() {
        favoriteButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_white));
    }

    @Override
    public void showUnFavorite() {
        favoriteButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.review_content:
                onReviewClick(view);
                break;
            case R.id.favorite_button:
                onFavoriteClick();
                break;
        }
    }

    private void onReviewClick(View reviewContent) {
        TextView content = (TextView) reviewContent;
        if (content.getMaxLines() == 5) {
            content.setMaxLines(500);
        } else {
            content.setMaxLines(5);
        }
    }

    private void onFavoriteClick() {
        movieDetailsPresenter.onFavoriteButtonClick(movie);
    }

    @Override
    public void onTrailerClick(int position) {
        Trailer trailer = trailerList.get(position);
        String videoUrl = Trailer.getUrl(trailer);
        if (videoUrl != null && !videoUrl.isEmpty()) {
            Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(Intent.createChooser(playVideoIntent, "Open with"));
        } else {
            Snackbar.make(rootView.findViewById(R.id.details_fragment), "This Trailer Has No URL", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        movieDetailsPresenter.destroy();
    }
}
