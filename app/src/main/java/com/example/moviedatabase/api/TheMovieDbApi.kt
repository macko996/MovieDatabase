package com.example.moviedatabase.api

import retrofit2.Call
import retrofit2.http.GET

interface TheMovieDbApi {

    @GET("/")
    fun fetchContents(): Call<String>
}