package com.example.moviedatabase.api.services

import com.example.moviedatabase.api.model.TvShowNetworkEntity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsService {

    @GET("tv/popular")
    fun getPopularTvShows(@Query("page") page: Int): Call<RootTVShowsResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Int): Call<TvShowNetworkEntity>

    @GET("tv/{tv_id}/recommendations")
    fun getTvShowRecommendations(@Path("tv_id") tvShowId : Int) : Call<RootTVShowsResponse>

    @GET("person/{person_id}/tv_credits")
    fun getPersonTVShowCredits(@Path("person_id")personId: Int) : Call<RootTvShowCreditsResponse>

    @GET("search/tv")
    fun searchTvShows(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Call<RootTVShowsResponse>
}

class RootTVShowsResponse {
    @SerializedName("results")
    lateinit var tvShows: List<TvShowNetworkEntity>
}

class RootTvShowCreditsResponse {
    lateinit var cast: List<TvShowNetworkEntity>
}