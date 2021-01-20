package com.example.moviedatabase.api.services

import com.example.moviedatabase.api.model.ActorNetworkEntity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorService {

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("person_id") personId: Int): Call<ActorNetworkEntity>

    @GET("tv/{tv_id}/credits")
    fun getTvShowCredits(@Path("tv_id") tvId: Int): Call<RootActorResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Int): Call<RootActorResponse>
}

class RootActorResponse {
    @SerializedName("cast")
    lateinit var actorNetworkEntities: List<ActorNetworkEntity>
}
