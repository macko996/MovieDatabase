package com.example.moviedatabase.api

import com.example.moviedatabase.model.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsService {

    @GET("tv/popular")
    fun getPopularTvShows(): Call<RootTVShowsResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Int): Call<TvShow>

    @GET("tv/{tv_id}/recommendations")
    fun getTvShowRecommendations(@Path("tv_id") tvShowId : Int) : Call<RootTVShowsResponse>

    @GET("person/{person_id}/tv_credits")
    fun getPersonTVShowCredits(@Path("person_id")personId: Int) : Call<RootTvShowCreditsResponse>

    @GET("search/tv")
    fun searchTvShows(@Query("query") query: String) : Call<RootTVShowsResponse>
}