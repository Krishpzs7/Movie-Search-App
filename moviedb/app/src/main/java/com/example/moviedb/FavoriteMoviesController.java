package com.example.moviedb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteMoviesController extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteListViewController adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.favoriteMoviesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        List<Movie> favoriteMovies = dbHelper.getAllFavoriteMovies();

        if (favoriteMovies.isEmpty()) {
            Toast.makeText(this, "No favorite movies found, Add Some:)", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new FavoriteListViewController(this, favoriteMovies);
            recyclerView.setAdapter(adapter);
        }

        // Initialize Back Button and set OnClickListener
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}
