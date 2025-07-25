package com.example.moviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class  DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 2; // Incremented database version

    private static final String TABLE_FAVORITES = "favorites";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_IMDB_ID = "imdb_id";
    private static final String COLUMN_POSTER = "poster";
    private static final String COLUMN_IMDB_RATING = "imdb_rating";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_PLOT = "plot";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_DIRECTOR = "director";
    private static final String COLUMN_RATED = "rated";
    private static final String COLUMN_RELEASED = "released";
    private static final String COLUMN_RUNTIME = "runtime";
    private static final String COLUMN_WRITER = "writer";
    private static final String COLUMN_ACTORS = "actors";
    private static final String COLUMN_LANGUAGE = "language";
    private static final String COLUMN_AWARDS = "awards";
    private static final String COLUMN_BOX_OFFICE = "boxOffice";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_YEAR + " TEXT, " +
                COLUMN_IMDB_ID + " TEXT, " +
                COLUMN_POSTER + " TEXT, " +
                COLUMN_IMDB_RATING + " TEXT, " +
                COLUMN_COUNTRY + " TEXT, " +
                COLUMN_PLOT + " TEXT, " +
                COLUMN_GENRE + " TEXT, " +
                COLUMN_DIRECTOR + " TEXT, " +
                COLUMN_RATED + " TEXT, " +
                COLUMN_RELEASED + " TEXT, " +
                COLUMN_RUNTIME + " TEXT, " +
                COLUMN_WRITER + " TEXT, " +
                COLUMN_ACTORS + " TEXT, " +
                COLUMN_LANGUAGE + " TEXT, " +
                COLUMN_AWARDS + " TEXT, " +
                COLUMN_BOX_OFFICE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) { // Check if the database version is lower than the new one
            // Add new columns for version 2
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_RATED + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_RELEASED + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_RUNTIME + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_WRITER + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_ACTORS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_LANGUAGE + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_AWARDS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_FAVORITES + " ADD COLUMN " + COLUMN_BOX_OFFICE + " TEXT");
        }
    }

    // Method to add a movie to favorites
    public boolean addMovieToFavorites(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_IMDB_ID, movie.getImdbID());
        values.put(COLUMN_POSTER, movie.getPoster());
        values.put(COLUMN_IMDB_RATING, movie.getImdbRating());
        values.put(COLUMN_COUNTRY, movie.getCountry());
        values.put(COLUMN_PLOT, movie.getPlot());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_DIRECTOR, movie.getDirector());
        values.put(COLUMN_RATED, movie.getRated());
        values.put(COLUMN_RELEASED, movie.getReleased());
        values.put(COLUMN_RUNTIME, movie.getRuntime());
        values.put(COLUMN_WRITER, movie.getWriter());
        values.put(COLUMN_ACTORS, movie.getActors());
        values.put(COLUMN_LANGUAGE, movie.getLanguage());
        values.put(COLUMN_AWARDS, movie.getAwards());
        values.put(COLUMN_BOX_OFFICE, movie.getBoxOffice());

        long result = db.insert(TABLE_FAVORITES, null, values);
        db.close();

        return result != -1;
    }

    // Method to get all favorite movies
    public List<Movie> getAllFavoriteMovies() {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);

        if (cursor.moveToFirst()) {
            do {
                // Debug logs
                Log.d("DatabaseHelper", "Fetched Movie Title: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                Log.d("DatabaseHelper", "Fetched Movie Year: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR)));

                Movie movie = new Movie(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMDB_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMDB_RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLOT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECTOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATED)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASED)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUNTIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRITER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTORS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AWARDS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOX_OFFICE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    public boolean isMovieFavorite(String imdbID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FAVORITES,
                new String[]{COLUMN_IMDB_ID},
                COLUMN_IMDB_ID + " = ?",
                new String[]{imdbID},
                null,
                null,
                null
        );
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Method to remove a movie from favorites
    public void removeMovieFromFavorites(String imdbID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_IMDB_ID + " = ?", new String[]{imdbID});
        db.close();
    }
}
