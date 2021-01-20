package com.example.moviedatabase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviedatabase.api.TvShowNetworkSource
import com.example.moviedatabase.api.networkmapper.TvShowNetworkMapper
import com.example.moviedatabase.model.TvShow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "TvShowsRepository"

/**
 * This class is used for retrieving data about TV Shows.
 */
@Singleton
class TvShowsRepository @Inject constructor(
    private val tvShowNetworkSource: TvShowNetworkSource,
    private val mapper: TvShowNetworkMapper
) {

    /**
     * Gets popular TV shows.
     */
    fun getPopularTvShows(): LiveData<List<TvShow>> {

        val tvShowsNetworkEntitiesLiveData = tvShowNetworkSource
            .getPopularTvShows()

        val tvShowsLiveData : LiveData<List<TvShow>> = Transformations.map(tvShowsNetworkEntitiesLiveData)
        { tvShowsEntities->
            val tvShows = mapper.mapFromEntityList(tvShowsEntities)
            return@map tvShows
        }

        return tvShowsLiveData
    }

    /**
     * Gets details about a particular TV Show.
     * @param tvId The id of the Tv Show
     */
    fun getTvShowDetails(tvId: Int): LiveData<TvShow> {

        val tvShowNetworkLiveData = tvShowNetworkSource
            .getTvShowDetails(tvId)

        val tvShowLiveData : LiveData<TvShow> = Transformations.map(tvShowNetworkLiveData)
        { tvShowNetworkEntity->
            val tvShow = mapper.mapFromEntity(tvShowNetworkEntity)
            return@map tvShow
        }

        return tvShowLiveData
    }


    /**
     * Gets recommendations based on a particular TV Show.
     * @param tvId The id of the TV Show
     */
    fun getTvShowRecommendations(tvId: Int): LiveData<List<TvShow>> {

        val tvShowsNetworkEntitiesLiveData = tvShowNetworkSource
            .getTvShowRecommendations(tvId)

        val tvShowsLiveData : LiveData<List<TvShow>> = Transformations.map(tvShowsNetworkEntitiesLiveData)
        { tvShowsEntities->
            val tvShows = mapper.mapFromEntityList(tvShowsEntities)
            return@map tvShows
        }

        return tvShowsLiveData
    }

    /**
     * Search for TV Show.
     * @param query the query we search for
     */
    fun searchTvShow(query: String): LiveData<List<TvShow>> {


        val tvShowsNetworkEntitiesLiveData = tvShowNetworkSource
            .searchTvShow(query)

        val tvShowsLiveData : LiveData<List<TvShow>> = Transformations.map(tvShowsNetworkEntitiesLiveData)
        { tvShowsEntities->
            val tvShows = mapper.mapFromEntityList(tvShowsEntities)
            return@map tvShows
        }

        return tvShowsLiveData
    }

    /**
     * Gets the TV shows for which a person is credited.
     * @param personId the id of the credited person.
     */
    fun getPersonTvShowCredits(personId: Int) : LiveData<List<TvShow>> {

        val tvShowsNetworkEntitiesLiveData = tvShowNetworkSource
            .getPersonTvShowCredits(personId)

        val tvShowsLiveData : LiveData<List<TvShow>> = Transformations.map(tvShowsNetworkEntitiesLiveData)
        { tvShowsEntities->
            val tvShows = mapper.mapFromEntityList(tvShowsEntities)
            return@map tvShows
        }

        return tvShowsLiveData
    }
}