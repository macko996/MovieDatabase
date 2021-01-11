package com.example.moviedatabase.api

import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.model.TvShow
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

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Int): Call<RootCastResponse>

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("person_id") personId: Int): Call<Cast>

    @GET("person/{person_id}/movie_credits")
    fun getPersonMovieCredits(@Path("person_id")personId: Int) : Call<RootCreditsResponse>

    @GET("tv/popular")
    fun getPopularTvShows(): Call<RootTVShowsResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Int): Call<TvShow>

    @GET("tv/{tv_id}/recommendations")
    fun getTvShowRecommendations(@Path ("tv_id") tvShowId : Int) : Call<RootTVShowsResponse>

    @GET("person/{person_id}/tv_credits")
    fun getPersonTVShowCredits(@Path("person_id")personId: Int) : Call<RootTvShowCreditsResponse>

    @GET("search/tv")
    fun searchTvShows(@Query ("query") query: String) : Call<RootTVShowsResponse>
}