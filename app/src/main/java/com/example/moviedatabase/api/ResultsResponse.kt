package com.example.moviedatabase.api

import com.example.moviedatabase.Movie
import com.google.gson.annotations.SerializedName

class ResultsResponse {
    @SerializedName("results")
    lateinit var movies: List<Movie>
}