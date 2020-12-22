package com.example.moviedatabase

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.ResultsResponse
import com.example.moviedatabase.api.TheMovieDbApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MovieDBFetcher"

class MovieDBFetcher {

    private val theMovieDbAPI: TheMovieDbApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        theMovieDbAPI= retrofit.create(TheMovieDbApi::class.java)
    }

    fun fetchMovies(): LiveData<List<MovieItem>> {
        val responseLiveData : MutableLiveData<List<MovieItem>> = MutableLiveData()
        val theMovieDBHomePageRequest: Call<ResultsResponse> = theMovieDbAPI.fetchMovies()

        theMovieDBHomePageRequest.enqueue(object : Callback<ResultsResponse> {
            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG, "Response received: ${response.body()}")
                val resultsResponse : ResultsResponse? = response.body()
                val movieItems : List<MovieItem>? = resultsResponse?.movieItems ?: mutableListOf()

                responseLiveData.value = movieItems
            }
        })
        return responseLiveData
    }

    @WorkerThread
    fun fetchPhoto(url: String): Bitmap? {
        val response: Response<ResponseBody> = theMovieDbAPI.fetchUrlBytes(url).execute()
        val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
        Log.i(TAG, "Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }


}