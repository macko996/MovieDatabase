package com.example.moviedatabase.api

import retrofit2.Call
import retrofit2.http.GET

interface TheMovieDbApi {

    @GET("3/movie/popular" +
            "?api_key=0135a8ca1095d143aa2e758506c9cb02" +
            "&language=en-US" +
            "&page=1")
    fun fetchMovies(): Call<ResultsResponse>
}