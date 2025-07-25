package com.example.moviedb;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiUtility {

    private static final String API_KEY = "d123e4b8";
    private static final String BASE_URL = "https://www.omdbapi.com/";

    public interface OnMoviesFetchedListener {
        void onMoviesFetched(List<Movie> movies);
        void onError(String message);
    }

    public interface OnMovieDetailsFetchedListener {
        void onMovieDetailsFetched(Movie movie);
        void onError(String message);
    }

    public static void searchMoviesByName(String movieName, OnMoviesFetchedListener listener) {
        String url = BASE_URL + "?apikey=" + API_KEY + "&s=" + movieName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError("Failed to fetch movies: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray searchArray = jsonObject.getJSONArray("Search");
                        List<Movie> movies = new ArrayList<>();
                        Map<String, Movie> movieMap = new HashMap<>();

                        // Collect IMDb IDs for all movies
                        for (int i = 0; i < searchArray.length(); i++) {
                            JSONObject movieObject = searchArray.getJSONObject(i);
                            String title = movieObject.getString("Title");
                            String year = movieObject.getString("Year");
                            String imdbID = movieObject.getString("imdbID");
                            String poster = movieObject.getString("Poster");

                            // Create a movie object with basic info
                            Movie movie = new Movie(title, year, imdbID, poster, null, null, null,null,null, null,null,null,null,null,null,null,null);
                            movies.add(movie);
                            movieMap.put(imdbID, movie);
                        }

                        // Fetch details (including rating) for each movie
                        for (String imdbID : movieMap.keySet()) {
                            getMovieDetailsById(imdbID, new OnMovieDetailsFetchedListener() {
                                @Override
                                public void onMovieDetailsFetched(Movie detailedMovie) {
                                    // Update the movie in the list with detailed info
                                    for (int j = 0; j < movies.size(); j++) {
                                        if (movies.get(j).getImdbID().equals(detailedMovie.getImdbID())) {
                                            movies.set(j, detailedMovie);
                                            break;
                                        }
                                    }

                                    // Notify listener when all movies have been updated
                                    if (movies.size() == movieMap.size()) {
                                        listener.onMoviesFetched(movies);
                                    }
                                }

                                @Override
                                public void onError(String message) {
                                    listener.onError("Failed to fetch movie details: " + message);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        listener.onError("Failed to parse movies: " + e.getMessage());
                    }
                } else {
                    listener.onError("Failed to fetch movies: " + response.message());
                }
            }
        });
    }

    public static void getMovieDetailsById(String imdbID, OnMovieDetailsFetchedListener listener) {
        String url = BASE_URL + "?apikey=" + API_KEY + "&i=" + imdbID;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError("Failed to fetch movie details: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);

                        String title = jsonObject.optString("Title", "N/A");
                        String year = jsonObject.optString("Year", "N/A");
                        String imdbID = jsonObject.optString("imdbID", "N/A");
                        String poster = jsonObject.optString("Poster", "N/A");
                        String imdbRating = jsonObject.optString("imdbRating", "N/A");
                        String country = jsonObject.optString("Country", "N/A");
                        String plot = jsonObject.optString("Plot", "N/A");
                        String genre = jsonObject.optString("Genre", "N/A");
                        String director = jsonObject.optString("Director", "N/A");
                        String writer = jsonObject.optString("Writer", "N/A");
                        String actors = jsonObject.optString("Actors", "N/A");
                        String language = jsonObject.optString("Language", "N/A");
                        String awards = jsonObject.optString("Awards", "N/A");
                        String boxOffice = jsonObject.optString("BoxOffice", "N/A");
                        String rated = jsonObject.optString("Rated", "N/A");
                        String released = jsonObject.optString("Released", "N/A");
                        String runtime = jsonObject.optString("Runtime", "N/A");

                        // Create a movie object with detailed info
                        Movie movie = new Movie(
                                title, year, imdbID, poster, imdbRating, country, plot, genre, director,
                                writer, actors, language, awards, boxOffice, rated, released, runtime
                        );
                        listener.onMovieDetailsFetched(movie);

                    } catch (JSONException e) {
                        listener.onError("Failed to parse movie details: " + e.getMessage());
                    }
                } else {
                    listener.onError("Failed to fetch movie details: " + response.message());
                }
            }
        });
    }

}
