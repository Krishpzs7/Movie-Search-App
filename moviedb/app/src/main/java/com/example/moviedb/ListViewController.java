package com.example.moviedb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class ListViewController extends RecyclerView.Adapter<ListViewController.MovieViewHolder> {

    private List<Movie> movieList;
    private OnMovieClickListener onMovieClickListener;

    public ListViewController(List<Movie> movieList, OnMovieClickListener onMovieClickListener) {
        this.movieList = movieList;
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());
        String rateInfo = "IMDb Rating: " + movie.getImdbRating()+"/10";
        holder.ratingTextView.setText(rateInfo);

        // Load the movie poster using Picasso
        Picasso.get().load(movie.getPoster()).into(holder.posterImageView);

        // Set click listener for item
        holder.itemView.setOnClickListener(v -> onMovieClickListener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView yearTextView;
        TextView ratingTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.movieTitle);
            yearTextView = itemView.findViewById(R.id.movieYear);
            ratingTextView = itemView.findViewById(R.id.movieRating);
            posterImageView = itemView.findViewById(R.id.moviePoster);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}
