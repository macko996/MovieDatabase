package com.example.moviedatabase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviedatabase.api.MovieNetworkSource
import com.example.moviedatabase.api.networkmapper.MovieNetworkMapper
import com.example.moviedatabase.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MovieRepository"

/**
 * This class is used for getting data about Movies.
 */
@Singleton
class MovieRepository @Inject constructor(
    private val movieNetworkMapper: MovieNetworkMapper,
    private val movieNetworkSource: MovieNetworkSource
) {

    /**
     * Gets popular movies
     */
    fun getPopularMovies(): LiveData<List<Movie>> {
        val moviesNetworkEntitiesLiveData = movieNetworkSource
            .getPopularMovies()

        val moviesLiveData : LiveData<List<Movie>> = Transformations.map(moviesNetworkEntitiesLiveData)
        { movieEntities->
            val movies = movieNetworkMapper.mapFromEntityList(movieEntities)
            return@map movies
        }

        return moviesLiveData
    }

    /**
     * Gets Movie Details
     * @param id The id of the movie
     */
    fun getMovieDetails(id:Int): LiveData<Movie> {

        val movieNetworkEntityLiveData = movieNetworkSource
            .getMovieDetails(id)

        val moviesLiveData : LiveData<Movie> = Transformations.map(movieNetworkEntityLiveData)
        { movieEntitity->
            val movie = movieNetworkMapper.mapFromEntity(movieEntitity)
            return@map movie
        }

        return moviesLiveData
    }

    /**
     * Get movie recommendations
     * @param movieId the id of the movie for which we want recommendations
     */
    fun getMovieRecommendations(movieId: Int): LiveData<List<Movie>> {


        val moviesNetworkEntitiesLiveData = movieNetworkSource
            .getMovieRecommendations(movieId)
        val moviesLiveData : LiveData<List<Movie>> = Transformations.map(moviesNetworkEntitiesLiveData)
        { movieEntities->
            val movies = movieNetworkMapper.mapFromEntityList(movieEntities)
            return@map movies
        }

        return moviesLiveData
    }

    /**
     * Search for a movie
     * @param query the query we search for
     */
    fun searchMovie(query: String): LiveData<List<Movie>> {

        val moviesNetworkEntitiesLiveData = movieNetworkSource
            .searchMovie(query)

        val moviesLiveData : LiveData<List<Movie>> = Transformations
            .map(moviesNetworkEntitiesLiveData)
            { movieEntities->
                val movies = movieNetworkMapper.mapFromEntityList(movieEntities)
                return@map movies
            }

        return moviesLiveData
    }

    /**
     * Gets the movies for which a person is credited.
     * @param personId the id of the person.
     */
    fun getPersonMovieCredits(personId: Int) : LiveData<List<Movie>> {

        val moviesNetworkEntitiesLiveData = movieNetworkSource
            .getPersonMovieCredits(personId)

        val moviesLiveData : LiveData<List<Movie>> = Transformations.map(moviesNetworkEntitiesLiveData)
        { movieEntities->
            val movies = movieNetworkMapper.mapFromEntityList(movieEntities)
            return@map movies
        }

        return moviesLiveData
    }

}