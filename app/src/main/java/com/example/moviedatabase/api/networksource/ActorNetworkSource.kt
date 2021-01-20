package com.example.moviedatabase.api.networksource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.model.ActorNetworkEntity
import com.example.moviedatabase.api.services.ActorService
import com.example.moviedatabase.api.services.RootActorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "ActorNetworkSource"

/**
 * This class is used for retrieving data about people.
 * @see ActorService
 */
@Singleton
class ActorNetworkSource @Inject constructor(private val actorService: ActorService) {

    /**
     * Gets the people who are credited a particular movies.
     * @param movieId the id of the movie.
     */
    fun getMovieCredits(movieId: Int) : LiveData<List<ActorNetworkEntity>> {

        val actorNetworkEntityLiveData : MutableLiveData<List<ActorNetworkEntity>> = MutableLiveData()
        val actorCall : Call<RootActorResponse> = actorService.getMovieCredits(movieId)
        actorCall.enqueue(object : Callback<RootActorResponse> {

            override fun onFailure(call: Call<RootActorResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(
                call: Call<RootActorResponse>,
                response: Response<RootActorResponse>){

                    val cast  = response.body()?.actorNetworkEntities
                    actorNetworkEntityLiveData.value = cast
            }
        })

        return actorNetworkEntityLiveData
    }

    /**
     * Gets details about a person
     * @param personId the id of the person.
     */
    fun getPersonDetails(personId: Int) : LiveData<ActorNetworkEntity> {

        val personLiveData : MutableLiveData<ActorNetworkEntity> = MutableLiveData()
        val personCall : Call<ActorNetworkEntity> = actorService.getPersonDetails(personId)
        personCall.enqueue(object : Callback<ActorNetworkEntity> {

            override fun onFailure(call: Call<ActorNetworkEntity>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(
                call: Call<ActorNetworkEntity>,
                response: Response<ActorNetworkEntity>){

                    val person : ActorNetworkEntity? = response.body()
                    personLiveData.value = person
            }
        })

        return personLiveData
    }

    /**
     * Gets the people who are credited for a particular TV Show.
     * @param tvShowId the id of the tv show.
     */
    fun getTvShowCredits(tvShowId: Int) : LiveData<List<ActorNetworkEntity>> {

        val actorNetworkEntityLiveData : MutableLiveData<List<ActorNetworkEntity>> = MutableLiveData()
        val actorCall : Call<RootActorResponse> = actorService.getTvShowCredits(tvShowId)
        actorCall.enqueue(object : Callback<RootActorResponse> {

            override fun onFailure(call: Call<RootActorResponse>, t: Throwable) {
                Log.e(TAG, "Failed getting the cast", t)
            }
            override fun onResponse(
                call: Call<RootActorResponse>,
                response: Response<RootActorResponse>){

                    val cast = response.body()?.actorNetworkEntities
                    actorNetworkEntityLiveData.value = cast
            }
        })

        return actorNetworkEntityLiveData
    }

}