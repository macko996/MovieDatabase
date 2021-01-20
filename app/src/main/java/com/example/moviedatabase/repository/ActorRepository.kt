package com.example.moviedatabase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviedatabase.api.model.ActorNetworkEntity
import com.example.moviedatabase.api.networkmapper.ActorNetworkMapper
import com.example.moviedatabase.api.networksource.ActorNetworkSource
import com.example.moviedatabase.api.services.MovieService
import com.example.moviedatabase.model.Actor
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ActorRepository"

/**
 * This class is used for retrieving data about people.
 * @see MovieService
 */
@Singleton
class ActorRepository @Inject constructor(
    private val networkSource: ActorNetworkSource,
    private val mapper: ActorNetworkMapper
) {

    /**
     * Gets the people who are credited a particular movies.
     * @param movieId the id of the movie.
     */
    fun getMovieCredits(movieId: Int) : LiveData<List<Actor>> {

        val castLiveData = networkSource.getMovieCredits(movieId)

        val actorsLiveData : LiveData<List<Actor>> = Transformations
            .map(castLiveData, { mapper.mapFromEntityList(it)})

        return actorsLiveData
    }

    /**
     * Gets details about a person
     * @param personId the id of the person.
     */
    fun getPersonDetails(personId: Int) : LiveData<Actor> {

        val personLiveData : LiveData<ActorNetworkEntity> = networkSource.getPersonDetails(personId)
        val actorLiveData : LiveData<Actor> = Transformations
            .map(personLiveData, {mapper.mapFromEntity(it)})

        return actorLiveData
    }

    /**
     * Gets the people who are credited for a particular TV Show.
     * @param tvShowId the id of the tv show.
     */
    fun getTvShowCredits(tvShowId: Int) : LiveData<List<Actor>> {

        val actorNetworkEntityLiveData : LiveData<List<ActorNetworkEntity>> = networkSource
            .getTvShowCredits(tvShowId)

        val actorsLiveData: LiveData<List<Actor>> = Transformations
            .map(actorNetworkEntityLiveData,{mapper.mapFromEntityList(it)})

        return actorsLiveData
    }

}