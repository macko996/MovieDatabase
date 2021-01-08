package com.example.moviedatabase.api

import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.model.TvShow
import com.google.gson.annotations.SerializedName

class ResultsResponse {
    @SerializedName("results")
    lateinit var movies: List<Movie>
}

class RootCastResponse {
    lateinit var cast: List<Cast>
}

class RootCreditsResponse {
    lateinit var cast: List<Movie>
}

class RootPopularTVShowsResponse {
    @SerializedName("results")
    lateinit var tvShows: List<TvShow>
}