package com.example.moviedb;


import java.util.List;

public class ApiResponse {
    private List<Movie> Search;

    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        Search = search;
    }
}
