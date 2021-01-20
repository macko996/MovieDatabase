package com.example.moviedatabase.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.networkEntities.MovieNetworkEntity
import com.example.moviedatabase.api.services.MovieService
import com.example.moviedatabase.api.services.ResultsResponse
import com.example.moviedatabase.api.services.RootCreditsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RemoteMovieSource"

/**
 * This class is used for executing the Call objects from MovieService
 * @see MovieService
 */
@Singleton
class MovieNetworkSource @Inject constructor(private val movieService: MovieService) {

    /**
     * Gets popular movies
     */
    fun getPopularMovies(): LiveData<List<MovieNetworkEntity>>{

        val moviesLiveData : MutableLiveData<List<MovieNetworkEntity>> = MutableLiveData()
        val movieCall: Call<ResultsResponse> = movieService.fetchMovies()

        movieCall.enqueue(object : Callback<ResultsResponse> {
            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies ", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG, "Received movies: ${response.body()}")
                val resultsResponse : ResultsResponse? = response.body()
                val movies = resultsResponse?.movies
                moviesLiveData.value = movies
            }
        })
        return moviesLiveData
    }

    /**
     * Gets Movie Details
     * @param id The id of the movie
     */
    fun getMovieDetails(id:Int): LiveData<MovieNetworkEntity> {

        val movieDetailsLiveData : MutableLiveData<MovieNetworkEntity> = MutableLiveData()
        val movieDetailsCall: Call<MovieNetworkEntity> = movieService.fetchMovieDetails(id)

        movieDetailsCall.enqueue(object : Callback<MovieNetworkEntity> {

            override fun onFailure(call: Call<MovieNetworkEntity>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movie details about movie $id", t)
            }

            override fun onResponse(call: Call<MovieNetworkEntity>, response: Response<MovieNetworkEntity>) {

                Log.d(TAG, "Response received: ${response.body()}")
                movieDetailsLiveData.value = response.body()
            }
        })

        return movieDetailsLiveData
    }

    /**
     * Get movie recommendations
     * @param movieId the id of the movie for which we want recommendations
     */
    fun getMovieRecommendations(movieId: Int): LiveData<List<MovieNetworkEntity>> {

        val moviesLiveData : MutableLiveData<List<MovieNetworkEntity>> = MutableLiveData()
        val movieRecommendationsCall: Call<ResultsResponse> = movieService
            .fetchMovieRecommendations(movieId)

        movieRecommendationsCall.enqueue(object : Callback<ResultsResponse> {
            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies recommendations", t)
            }
            override fun onResponse(
                call: Call<ResultsResponse>,
                response: Response<ResultsResponse>){

                    Log.d(TAG, "Received recommended movies: ${response.body()}")
                    val movies = response.body()?.movies
                    moviesLiveData.value = movies
            }
        })
        return moviesLiveData
    }

    /**
     * Search for a movie
     * @param query the query we search for
     */
    fun searchMovie(query: String): LiveData<List<MovieNetworkEntity>> {

        val moviesLiveData : MutableLiveData<List<MovieNetworkEntity>> = MutableLiveData()
        val movieSearchCall: Call<ResultsResponse> = movieService.searchMovie(query)

        movieSearchCall.enqueue(object : Callback<ResultsResponse> {

            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG,"Received search ${response.body()}")
                val movies = response.body()?.movies
                moviesLiveData.value = movies
            }
        })

        return moviesLiveData
    }

    /**
     * Gets the movies for which a person is credited.
     * @param personId the id of the person.
     */
    fun getPersonMovieCredits(personId: Int) : LiveData<List<MovieNetworkEntity>> {

        val moviesLiveData : MutableLiveData<List<MovieNetworkEntity>> = MutableLiveData()
        val personCreditsCall: Call<RootCreditsResponse> = movieService.getPersonMovieCredits(personId)

        personCreditsCall.enqueue(object : Callback<RootCreditsResponse> {

            override fun onFailure(call: Call<RootCreditsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(call: Call<RootCreditsResponse>, response: Response<RootCreditsResponse>){
                Log.d(TAG,"Received search ${response.body()}")
                val movies = response.body()?.cast
                moviesLiveData.value = movies
            }
        })

        return moviesLiveData
    }
}