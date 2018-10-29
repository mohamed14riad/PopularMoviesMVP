package com.mohamed14riad.popularmoviesmvp.mvp.listing;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mohamed14riad.popularmoviesmvp.R;
import com.mohamed14riad.popularmoviesmvp.models.Movie;
import com.mohamed14riad.popularmoviesmvp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MoviesListingAdapter extends RecyclerView.Adapter<MoviesListingAdapter.MovieViewHolder> {
    private Context context;
    private MoviesListingView moviesListingView;

    private List<Movie> movieList;

    private static final String BASE_POSTER_URL = AppConstants.BASE_POSTER_URL;

    MoviesListingAdapter(Context context, MoviesListingView moviesListingView) {
        this.context = context;
        this.moviesListingView = moviesListingView;
        movieList = new ArrayList<>();
    }

    public void addNewData(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        Uri posterUrl = null;
        String posterPath = movie.getPosterPath();
        if (posterPath != null && !posterPath.isEmpty()) {
            posterUrl = Uri.parse(BASE_POSTER_URL.concat(posterPath));
        }

        Glide.with(context)
                .asBitmap()
                .load(posterUrl)
                .apply(new RequestOptions().placeholder(R.color.colorPrimary).error(R.color.colorPrimary))
                .into(new BitmapImageViewTarget(holder.movie_poster) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        super.onResourceReady(bitmap, transition);

                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                holder.movie_title_background.setBackgroundColor
                                        (palette.getVibrantColor(ContextCompat.getColor(context, R.color.black_translucent_60)));
                            }
                        });
                    }
                });

        holder.movie_title.setText(movie.getMovieTitle());
    }

    @Override
    public int getItemCount() {
        if (movieList.isEmpty()) {
            return 0;
        } else {
            return movieList.size();
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView movie_poster;
        private View movie_title_background;
        private TextView movie_title;

        MovieViewHolder(View itemView) {
            super(itemView);

            movie_poster = (ImageView) itemView.findViewById(R.id.movie_poster);
            movie_title_background = (View) itemView.findViewById(R.id.movie_title_background);
            movie_title = (TextView) itemView.findViewById(R.id.movie_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            moviesListingView.onMovieClicked(movieList.get(getAdapterPosition()));
        }
    }
}
