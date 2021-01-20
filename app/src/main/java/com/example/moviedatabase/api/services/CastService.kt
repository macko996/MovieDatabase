package com.example.moviedatabase.api.services

import com.example.moviedatabase.model.Cast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CastService {

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("person_id") personId: Int): Call<Cast>

    @GET("tv/{tv_id}/credits")
    fun getTvShowCredits(@Path("tv_id") tvId: Int): Call<RootCastResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Int): Call<RootCastResponse>
}

class RootCastResponse {
    lateinit var cast: List<Cast>
}
