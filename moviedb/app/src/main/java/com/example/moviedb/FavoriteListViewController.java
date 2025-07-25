package com.example.moviedb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteListViewController extends RecyclerView.Adapter<FavoriteListViewController.ViewHolder> {
    private final Context context;
    private final List<Movie> movieList;
    private final DatabaseHelper databaseHelper;

    public FavoriteListViewController(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_favorite_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        Log.d("FavoriteMoviesAdapter", "Binding Movie Title: " + movie.getTitle());

        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());
        String rateInfo = "IMDb Rating:  " + movie.getImdbRating()+"/10";

        holder.ratingTextView.setText(rateInfo);
        Picasso.get().load(movie.getPoster()).into(holder.posterImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsViewController.class);
            intent.putExtra("MOVIE", movie);
            context.startActivity(intent);
        });

        holder.removeButton.setOnClickListener(v -> {
            removeMovie(position);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void removeMovie(int position) {
        Movie movie = movieList.get(position);
        databaseHelper.removeMovieFromFavorites(movie.getImdbID());
        movieList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;
        TextView ratingTextView;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.moviePoster);
            titleTextView = itemView.findViewById(R.id.movieTitle);
            yearTextView = itemView.findViewById(R.id.movieYear);
            ratingTextView = itemView.findViewById(R.id.movieRating);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

}
