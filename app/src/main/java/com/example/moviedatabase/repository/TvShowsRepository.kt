package com.example.moviedatabase.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.services.MovieService
import com.example.moviedatabase.api.services.RootTVShowsResponse
import com.example.moviedatabase.api.services.RootTvShowCreditsResponse
import com.example.moviedatabase.api.services.TvShowsService
import com.example.moviedatabase.model.TvShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TvShowsRepository"

/**
 * This class is used for retrieving data about TV Shows.
 * @see MovieService
 */
@Singleton
class TvShowsRepository @Inject constructor(
    private val movieService: MovieService
    ,private val tvShowsService: TvShowsService) {

    /**
     * Gets popular TV shows.
     */
    fun getPopularTvShows(): LiveData<ArrayList<TvShow>> {

        val responseLiveData : MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val tvShowCall: Call<RootTVShowsResponse> = tvShowsService.getPopularTvShows()
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
        val tvShowDetailsCall: Call<TvShow> = tvShowsService.getTvShowDetails(tvId)

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
        val tvShowRecommendationsCall: Call<RootTVShowsResponse> = tvShowsService
            .getTvShowRecommendations(tvId)

        tvShowRecommendationsCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch recommendations about TV show $tvId", t)
            }

            override fun onResponse(call: Call<RootTVShowsResponse>,
                                    response: Response<RootTVShowsResponse>) {

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
        val tvShowSearchCall: Call<RootTVShowsResponse> = tvShowsService.searchTvShows(query)

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

    /**
     * Gets the TV shows for which a person is credited.
     * @param personId the id of the credited person.
     */
    fun getPersonTvShowCredits(personId: Int) : MutableLiveData<ArrayList<TvShow>> {
        val tvShowsLiveData : MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val tvShowsCall : Call<RootTvShowCreditsResponse> = tvShowsService
            .getPersonTVShowCredits(personId)
        tvShowsCall.enqueue(object : Callback<RootTvShowCreditsResponse> {

            override fun onFailure(call: Call<RootTvShowCreditsResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(call: Call<RootTvShowCreditsResponse>,
                                    response: Response<RootTvShowCreditsResponse>){

                val rootResponse : RootTvShowCreditsResponse? = response.body()
                val tvShows : ArrayList<TvShow>? = (rootResponse?.cast ?: mutableListOf()) as ArrayList<TvShow>?

                tvShowsLiveData.value = tvShows
            }
        })

        return tvShowsLiveData
    }
}