package com.example.moviedatabase.di

import android.util.Log
import com.example.moviedatabase.api.MovieInterceptor
import com.example.moviedatabase.api.TheMovieDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit() : TheMovieDbApi {

        Log.d("RETROFIT", "Created retrofit")
        val client = OkHttpClient.Builder()
            .addInterceptor(MovieInterceptor())
            .build()
        val BASE_URL = "https://api.themoviedb.org/3/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(TheMovieDbApi::class.java)
    }
}