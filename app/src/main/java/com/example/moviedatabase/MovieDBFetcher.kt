package com.example.moviedatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.TheMovieDbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "MovieDBFetcher"

class MovieDBFetcher {

    private val theMovieDbAPI: TheMovieDbApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.themoviedb.org/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        theMovieDbAPI= retrofit.create(TheMovieDbApi::class.java)
    }

    fun fetchContents(): LiveData<String> {
        val responseLiveData : MutableLiveData<String> = MutableLiveData()
        val theMovieDBHomePageRequest: Call<String> = theMovieDbAPI.fetchContents()

        theMovieDBHomePageRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }
            override fun onResponse(call: Call<String>, response: Response<String>){
                Log.d(TAG, "Response received: ${response.body()}")
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }


}