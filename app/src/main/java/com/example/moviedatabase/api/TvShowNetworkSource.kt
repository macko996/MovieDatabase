package com.example.moviedatabase.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.networkEntities.TvShowNetworkEntity
import com.example.moviedatabase.api.services.RootTVShowsResponse
import com.example.moviedatabase.api.services.RootTvShowCreditsResponse
import com.example.moviedatabase.api.services.TvShowsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TvShowsNetworkSource"

/**
 * This class is used for retrieving data about TV Shows from network.
 * @see TvShowsService
 */
@Singleton
class TvShowNetworkSource @Inject constructor(private val tvShowsService: TvShowsService) {

    /**
     * Gets popular TV shows.
     */
    fun getPopularTvShows(): LiveData<List<TvShowNetworkEntity>> {

        val tvShowsLiveData : MutableLiveData<List<TvShowNetworkEntity>> = MutableLiveData()
        val tvShowCall: Call<RootTVShowsResponse> = tvShowsService.getPopularTvShows()
        tvShowCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }

            override fun onResponse(
                call: Call<RootTVShowsResponse>,
                response: Response<RootTVShowsResponse>
            ){

                Log.d(TAG, "onResponse: ${response.body()}")
                val tvShows = response.body()?.tvShows
                tvShowsLiveData.value = tvShows
            }
        })

        return tvShowsLiveData
    }

    /**
     * Gets details about a particular TV Show.
     * @param tvId The id of the Tv Show
     */
    fun getTvShowDetails(tvId: Int): LiveData<TvShowNetworkEntity> {

        val tvShowLiveData: MutableLiveData<TvShowNetworkEntity> = MutableLiveData()
        val tvShowDetailsCall: Call<TvShowNetworkEntity> = tvShowsService.getTvShowDetails(tvId)

        tvShowDetailsCall.enqueue(object : Callback<TvShowNetworkEntity> {

            override fun onFailure(call: Call<TvShowNetworkEntity>, t: Throwable) {
                Log.e(TAG, "Failed to fetch details about TV show $tvId", t)
            }

            override fun onResponse(
                call: Call<TvShowNetworkEntity>,
                response: Response<TvShowNetworkEntity>) {

                    Log.d(TAG, "Response received: ${response.body()}")
                    val tvShow = response.body()
                    tvShowLiveData.value = tvShow
            }
        })

        return tvShowLiveData
    }


    /**
     * Gets recommendations based on a particular TV Show.
     * @param tvId The id of the TV Show
     */
    fun getTvShowRecommendations(tvId: Int): LiveData<List<TvShowNetworkEntity>> {

        val recommendedTvShowsLiveData: MutableLiveData<List<TvShowNetworkEntity>> =
            MutableLiveData()
        val tvShowRecommendationsCall: Call<RootTVShowsResponse> = tvShowsService
            .getTvShowRecommendations(tvId)

        tvShowRecommendationsCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch recommendations about TV show $tvId", t)
            }

            override fun onResponse(
                call: Call<RootTVShowsResponse>,
                response: Response<RootTVShowsResponse>
            ) {

                val recommendedTvShows = response.body()?.tvShows
                recommendedTvShowsLiveData.value = recommendedTvShows
            }
        })

        return recommendedTvShowsLiveData
    }

    /**
     * Search for TV Show.
     * @param query the query we search for
     */
    fun searchTvShow(query: String): LiveData<List<TvShowNetworkEntity>> {

        val tvShowsLiveData : MutableLiveData<List<TvShowNetworkEntity>> = MutableLiveData()
        val tvShowSearchCall: Call<RootTVShowsResponse> = tvShowsService.searchTvShows(query)

        tvShowSearchCall.enqueue(object : Callback<RootTVShowsResponse> {

            override fun onFailure(call: Call<RootTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(
                call: Call<RootTVShowsResponse>,
                response: Response<RootTVShowsResponse>
            ){
                val tvShows= response.body()?.tvShows
                tvShowsLiveData.value = tvShows
            }
        })

        return tvShowsLiveData
    }

    /**
     * Gets the TV shows for which a person is credited.
     * @param personId the id of the credited person.
     */
    fun getPersonTvShowCredits(personId: Int) : LiveData<List<TvShowNetworkEntity>> {

        val tvShowsLiveData : MutableLiveData<List<TvShowNetworkEntity>> = MutableLiveData()
        val tvShowsCall : Call<RootTvShowCreditsResponse> = tvShowsService
            .getPersonTVShowCredits(personId)

        tvShowsCall.enqueue(object : Callback<RootTvShowCreditsResponse> {

            override fun onFailure(call: Call<RootTvShowCreditsResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(
                call: Call<RootTvShowCreditsResponse>,
                response: Response<RootTvShowCreditsResponse>
            ){
                val tvShows= response.body()?.cast
                tvShowsLiveData.value = tvShows
            }
        })

        return tvShowsLiveData
    }
}