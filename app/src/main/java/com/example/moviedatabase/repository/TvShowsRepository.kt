package com.example.moviedatabase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.RootTVShowsResponse
import com.example.moviedatabase.api.TheMovieDbApi
import com.example.moviedatabase.model.TvShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TvShowsRepository"

/**
 * This class is used for retrieving data about TV Shows.
 * @see TheMovieDbApi
 */
@Singleton
class TvShowsRepository @Inject constructor(private val theMovieDbAPI: TheMovieDbApi) {

    /**
     * Gets popular TV shows.
     */
    fun getPopularTvShows(): LiveData<ArrayList<TvShow>> {

        val responseLiveData : MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val tvShowCall: Call<RootTVShowsResponse> = theMovieDbAPI.getPopularTvShows()
        tvShowCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }

            override fun onResponse(
                call: Call<RootTVShowsResponse>,
                response: Response<RootTVShowsResponse>){

                Log.d(TAG, "onResponse: ${response.body()}")
                val resultsResponse : RootTVShowsResponse? = response.body()
                val tvShows : ArrayList<TvShow>? =
                    (resultsResponse?.tvShows ?: mutableListOf()) as ArrayList<TvShow>?

                responseLiveData.value = tvShows
            }
        })

        return responseLiveData
    }

    /**
     * Gets details about a particular TV Show.
     * @param tvId The id of the Tv Show
     */
    fun getTvShowDetails(tvId: Int): MutableLiveData<TvShow> {

        val tvShowLiveData: MutableLiveData<TvShow> = MutableLiveData()
        val tvShowDetailsCall: Call<TvShow> = theMovieDbAPI.getTvShowDetails(tvId)

        tvShowDetailsCall.enqueue(object : Callback<TvShow> {

            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                Log.e(TAG, "Failed to fetch details about TV show $tvId", t)
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {

                Log.d(TAG, "Response received: ${response.body()}")
                val movie = response.body()
                tvShowLiveData.value = movie
            }
        })

        return tvShowLiveData
    }


    /**
     * Gets recommendations based on a particular TV Show.
     * @param tvId The id of the TV Show
     */
    fun getTvShowRecommendations(tvId: Int): MutableLiveData<ArrayList<TvShow>> {

        val recommendedTvShowsLiveData: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val tvShowRecommendationsCall: Call<RootTVShowsResponse> = theMovieDbAPI.getTvShowRecommendations(tvId)

        tvShowRecommendationsCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch recommendations about TV show $tvId", t)
            }

            override fun onResponse(call: Call<RootTVShowsResponse>, response: Response<RootTVShowsResponse>) {

                val recommendedTvShows = response.body()?.tvShows
                recommendedTvShowsLiveData.value = recommendedTvShows as ArrayList<TvShow>?
            }
        })

        return recommendedTvShowsLiveData
    }

    /**
     * Search for TV Show.
     * @param query the query we search for
     */
    fun searchTvShow(query: String): LiveData<ArrayList<TvShow>> {

        val tvShowListLiveData : MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val tvShowSearchCall: Call<RootTVShowsResponse> = theMovieDbAPI.searchTvShows(query)

        tvShowSearchCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(
                call: Call<RootTVShowsResponse>,
                response: Response<RootTVShowsResponse>
            ){

                val resultsResponse : RootTVShowsResponse? = response.body()
                val tvShows : ArrayList<TvShow>? = (resultsResponse?.tvShows ?: mutableListOf()) as ArrayList<TvShow>

                tvShowListLiveData.value = tvShows
            }
        })

        return tvShowListLiveData
    }
}