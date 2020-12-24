package com.example.moviedatabase.api

import com.example.moviedatabase.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbApi {

    @GET("movie/popular")
    fun fetchMovies(): Call<ResultsResponse>

    @GET("movie/{id}")
    fun fetchMovieDetails(@Path("id") id : Int): Call<Movie>
}