package com.example.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class MovieDetailsViewController extends AppCompatActivity {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView movieYearTextView;
    private TextView movieRatingTextView;
    private TextView movieReleasedTextView;
    private TextView movieRuntimeTextView;
    private TextView movieGenreTextView;
    private TextView movieDirectorTextView;
    private TextView movieWriterTextView;
    private TextView movieActorsTextView;
    private TextView moviePlotTextView;
    private TextView movieLanguageTextView;
    private TextView movieCountryTextView;
    private TextView movieAwardsTextView;
    private TextView movieBoxOfficeTextView;
    private Button backButton;
    private Button addToFavoritesButton;
    private Button viewFavoritesButton;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initialize views
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieYearTextView = findViewById(R.id.movieYearTextView);
        movieRatingTextView = findViewById(R.id.movieRatingTextView);
        movieReleasedTextView = findViewById(R.id.movieReleasedTextView);
        movieRuntimeTextView = findViewById(R.id.movieRuntimeTextView);
        movieGenreTextView = findViewById(R.id.movieGenreTextView);
        movieDirectorTextView = findViewById(R.id.movieDirectorTextView);
        movieWriterTextView = findViewById(R.id.movieWriterTextView);
        movieActorsTextView = findViewById(R.id.movieActorsTextView);
        moviePlotTextView = findViewById(R.id.moviePlotTextView);
        movieLanguageTextView = findViewById(R.id.movieLanguageTextView);
        movieCountryTextView = findViewById(R.id.movieCountryTextView);
        movieAwardsTextView = findViewById(R.id.movieAwardsTextView);
        movieBoxOfficeTextView = findViewById(R.id.movieBoxOfficeTextView);
        backButton = findViewById(R.id.backButton);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        viewFavoritesButton = findViewById(R.id.viewButton);

        // Get the Movie object from the Intent
        movie = (Movie) getIntent().getSerializableExtra("MOVIE");

        if (movie != null) {
            // Set the movie details to the views
            Picasso.get().load(movie.getPoster()).into(moviePosterImageView);
            movieTitleTextView.setText(movie.getTitle());
            movieYearTextView.setText("Year: " + movie.getYear());
            movieRatingTextView.setText("IMDb Rating: " + movie.getImdbRating());
            movieReleasedTextView.setText("Actors: " + movie.getReleased());
            movieRuntimeTextView.setText("Language: " + movie.getRuntime());
            movieGenreTextView.setText("Genre: " + movie.getGenre());
            movieDirectorTextView.setText("Director: " + movie.getDirector());
            movieWriterTextView.setText("Awards: " + movie.getWriter());
            movieActorsTextView.setText("Box Office: " + movie.getActors());
            moviePlotTextView.setText("Plot: " + movie.getPlot());
            movieLanguageTextView.setText("Rated: " + movie.getLanguage());
            movieCountryTextView.setText("Country: " + movie.getCountry());
            movieAwardsTextView.setText("Release Date: " + movie.getAwards());
            movieBoxOfficeTextView.setText("Run Time: " + movie.getBoxOffice());

            // Update the Add/Remove Favorites button text
            updateFavoritesButton();
        }

        backButton.setOnClickListener(v -> finish());

        addToFavoritesButton.setOnClickListener(v -> toggleFavorites());

        viewFavoritesButton.setOnClickListener(v -> viewFavorites());
    }

    private void toggleFavorites() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.isMovieFavorite(movie.getImdbID())) {
            // Movie is already a favorite; remove it
            dbHelper.removeMovieFromFavorites(movie.getImdbID());
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            // Movie is not a favorite; add it
            dbHelper.addMovieToFavorites(movie);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }

        updateFavoritesButton();
    }

    private void updateFavoritesButton() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.isMovieFavorite(movie.getImdbID())) {
            addToFavoritesButton.setText("Remove from Favorites");
        } else {
            addToFavoritesButton.setText("Add to Favorites");
        }
    }

    private void viewFavorites() {
        Intent intent = new Intent(MovieDetailsViewController.this, FavoriteMoviesController.class);
        startActivity(intent);
    }
}
