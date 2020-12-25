package com.example.moviedatabase.api

import com.example.moviedatabase.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("movie/popular")
    fun fetchMovies(): Call<ResultsResponse>

    @GET("movie/{id}")
    fun fetchMovieDetails(@Path("id") id : Int): Call<Movie>

    @GET("movie/{movie_id}/recommendations")
    fun fetchMovieRecommendations(@Path("movie_id") movieId : Int): Call<ResultsResponse>

    @GET("search/movie/")
    fun searchMovie(@Query("query") query: String): Call<ResultsResponse>
}