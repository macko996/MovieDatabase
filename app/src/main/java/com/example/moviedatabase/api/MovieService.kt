package com.example.moviedatabase.api

import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.model.TvShow
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun fetchMovies(): Call<ResultsResponse>

    @GET("movie/{id}")
    fun fetchMovieDetails(@Path("id") id : Int): Call<Movie>

    @GET("movie/{movie_id}/recommendations")
    fun fetchMovieRecommendations(@Path("movie_id") movieId : Int): Call<ResultsResponse>

    @GET("search/movie/")
    fun searchMovie(@Query("query") query: String): Call<ResultsResponse>

    @GET("person/{person_id}/movie_credits")
    fun getPersonMovieCredits(@Path("person_id")personId: Int) : Call<RootCreditsResponse>
}

class ResultsResponse {
    @SerializedName("results")
    lateinit var movies: List<Movie>
}

class RootCreditsResponse {
    lateinit var cast: List<Movie>
}