package com.example.moviedatabase.di

import com.example.moviedatabase.api.MovieInterceptor
import com.example.moviedatabase.api.services.CastService
import com.example.moviedatabase.api.services.MovieService
import com.example.moviedatabase.api.services.TvShowsService
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

    const val BASE_URL = "https://api.themoviedb.org/3/"

    /**
     * Provides OkHttpClient
     */
    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(movieInterceptor: MovieInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(movieInterceptor)
            .build()
    }

    /**
     * Provides instance of Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Provides instance of MovieService
     */
    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) : MovieService {

        return retrofit.create(MovieService::class.java)
    }

    /**
     * Provides instance of TvShowsService
     */
    @Singleton
    @Provides
    fun provideTvShowService(retrofit: Retrofit) : TvShowsService {

        return retrofit.create(TvShowsService::class.java)
    }

    /**
     * Provides instance of CastService
     */
    @Singleton
    @Provides
    fun provideCastService(retrofit: Retrofit) : CastService {

        return retrofit.create(CastService::class.java)
    }
}