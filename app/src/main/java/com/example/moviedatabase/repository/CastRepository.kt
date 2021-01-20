package com.example.moviedatabase.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.services.CastService
import com.example.moviedatabase.api.services.MovieService
import com.example.moviedatabase.api.services.RootCastResponse
import com.example.moviedatabase.model.Cast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "CastRepository"

/**
 * This class is used for retrieving data about people.
 * @see MovieService
 */
@Singleton
class CastRepository @Inject constructor(private val castService: CastService) {

    /**
     * Gets the people who are credited a particular movies.
     * @param movieId the id of the movie.
     */
    fun getMovieCredits(movieId: Int) : MutableLiveData<ArrayList<Cast>> {
        val castLiveData : MutableLiveData<ArrayList<Cast>> = MutableLiveData()
        val castCall : Call<RootCastResponse> = castService.getMovieCredits(movieId)
        castCall.enqueue(object : Callback<RootCastResponse> {

            override fun onFailure(call: Call<RootCastResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(call: Call<RootCastResponse>, response: Response<RootCastResponse>){
                Log.d(TAG,"Received cast ${response.body()}")
                val rootCastResponse : RootCastResponse? = response.body()
                val cast : ArrayList<Cast>? = (rootCastResponse?.cast ?: mutableListOf()) as ArrayList<Cast>?

                castLiveData.value = cast
            }
        })

        return castLiveData
    }

    /**
     * Gets details about a person
     * @param personId the id of the person.
     */
    fun getPersonDetails(personId: Int) : MutableLiveData<Cast> {
        val personLiveData : MutableLiveData<Cast> = MutableLiveData()
        val personCall : Call<Cast> = castService.getPersonDetails(personId)
        personCall.enqueue(object : Callback<Cast> {

            override fun onFailure(call: Call<Cast>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(call: Call<Cast>, response: Response<Cast>){
                Log.d(TAG,"Received cast ${response.body()}")
                val person : Cast? = response.body()
                personLiveData.value = person
            }
        })

        return personLiveData
    }

    /**
     * Gets the people who are credited for a particular TV Show.
     * @param tvShowId the id of the tv show.
     */
    fun getTvShowCredits(tvShowId: Int) : MutableLiveData<ArrayList<Cast>> {
        val castLiveData : MutableLiveData<ArrayList<Cast>> = MutableLiveData()
        val castCall : Call<RootCastResponse> = castService.getTvShowCredits(tvShowId)
        castCall.enqueue(object : Callback<RootCastResponse> {

            override fun onFailure(call: Call<RootCastResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(call: Call<RootCastResponse>, response: Response<RootCastResponse>){
                Log.d(TAG,"Received cast ${response.body()}")
                val rootCastResponse : RootCastResponse? = response.body()
                val cast : ArrayList<Cast>? = (rootCastResponse?.cast ?: mutableListOf()) as ArrayList<Cast>?

                castLiveData.value = cast
            }
        })

        return castLiveData
    }

}