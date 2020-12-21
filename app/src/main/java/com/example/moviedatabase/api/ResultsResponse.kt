package com.example.moviedatabase.api

import com.example.moviedatabase.MovieItem
import com.google.gson.annotations.SerializedName

class ResultsResponse {
    @SerializedName("results")
    lateinit var movieItems: List<MovieItem>
}