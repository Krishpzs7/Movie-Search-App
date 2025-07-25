package com.example.moviedb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchViewController extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private ListViewController movieAdapter;
    private List<Movie> movieList;
    private Button viewFavButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        viewFavButton = findViewById(R.id.viewFav);

        // Initialize movie list and adapter
        movieList = new ArrayList<>();
        movieAdapter = new ListViewController(movieList, movie -> {
            Intent intent = new Intent(SearchViewController.this, MovieDetailsViewController.class);
            intent.putExtra("MOVIE", movie);
            startActivity(intent);
        });

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        // Set up button listeners
        viewFavButton.setOnClickListener(v -> viewFavorites());
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                if (isNetworkAvailable()) {
                    searchMovies(query);
                } else {
                    Toast.makeText(SearchViewController.this, "No internet connection available.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SearchViewController.this, "Please enter a search query.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks if the device has an active internet connection.
     *
     * @return true if the device is connected to the internet, false otherwise.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Initiates a search for movies based on the provided query.
     *
     * @param query The search query entered by the user.
     */
    private void searchMovies(String query) {
        ApiUtility.searchMoviesByName(query, new ApiUtility.OnMoviesFetchedListener() {
            @Override
            public void onMoviesFetched(final List<Movie> movies) {
                runOnUiThread(() -> {
                    if (movies != null && !movies.isEmpty()) {
                        movieList.clear();
                        movieList.addAll(movies);
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SearchViewController.this, "No movies found for the given query.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(final String errorMessage) {
                runOnUiThread(() -> {
                    Log.e("SearchViewController", errorMessage);
                    Toast.makeText(SearchViewController.this, "Error fetching movies: " + errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * Navigates to the FavoriteMoviesController activity to display the user's favorite movies.
     */
    private void viewFavorites() {
        Intent intent = new Intent(SearchViewController.this, FavoriteMoviesController.class);
        startActivity(intent);
    }
}
