package com.example.moviedatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.api.*
import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.model.TvShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MovieFetcher"

/**
 * This class is used for executing the Call objects from TheMovieDbApi
 * @see TheMovieDbApi
 */
@Singleton
class MovieFetcher @Inject constructor(private val theMovieDbAPI: TheMovieDbApi) {


    /**
     * Gets popular movies
     */
    fun fetchPopularMovies(): LiveData<ArrayList<Movie>> {
        val responseLiveData : MutableLiveData<ArrayList<Movie>> = MutableLiveData()
        val popularMoviesCall: Call<ResultsResponse> = theMovieDbAPI.fetchMovies()

        popularMoviesCall.enqueue(object : Callback<ResultsResponse> {
            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG, "Response received: ${response.body()}")
                val resultsResponse : ResultsResponse? = response.body()
                val movies : ArrayList<Movie>? = (resultsResponse?.movies ?: mutableListOf()) as ArrayList<Movie>?

                responseLiveData.value = movies
            }
        })
        return responseLiveData
    }

    /**
     * Gets Movie Details
     * @param id The id of the movie
     */
    fun fetchMovieDetails(id:Int): MutableLiveData<Movie> {

        val movieLiveData: MutableLiveData<Movie> = MutableLiveData()
        val movieDetailsCall: Call<Movie> = theMovieDbAPI.fetchMovieDetails(id)

        movieDetailsCall.enqueue(object : Callback<Movie> {

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movie details about movie $id", t)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

                Log.d(TAG, "Response received: ${response.body()}")
                val movie = response.body()
                movieLiveData.value = movie
            }
        })

        return movieLiveData
    }

    /**
     * Get movie recommendations
     * @param movieId the id of the movie for which we want recommendations
     */
    fun fetchMovieRecommendations(movieId: Int): MutableLiveData<ArrayList<Movie>> {

        val movieListLiveData : MutableLiveData<ArrayList<Movie>> = MutableLiveData()
        val movieRecommendationsCall: Call<ResultsResponse> = theMovieDbAPI.fetchMovieRecommendations(movieId)

        movieRecommendationsCall.enqueue(object : Callback<ResultsResponse> {
            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies recommendations", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG, "Received recommended movies: ${response.body()}")
                val resultsResponse : ResultsResponse? = response.body()
                val movies : ArrayList<Movie>? = (resultsResponse?.movies ?: mutableListOf()) as ArrayList<Movie>?

                movieListLiveData.value = movies
            }
        })
        return movieListLiveData
    }

    /**
     * Search for a movie
     * @param query the query we search for
     */
    fun searchMovie(query: String): LiveData<ArrayList<Movie>> {

        val movieListLiveData : MutableLiveData<ArrayList<Movie>> = MutableLiveData()
        val movieSearchCall: Call<ResultsResponse> = theMovieDbAPI.searchMovie(query)

        movieSearchCall.enqueue(object : Callback<ResultsResponse> {

            override fun onFailure(call: Call<ResultsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(call: Call<ResultsResponse>, response: Response<ResultsResponse>){
                Log.d(TAG,"Received search ${response.body()}")
                val resultsResponse : ResultsResponse? = response.body()
                val movies : ArrayList<Movie>? = (resultsResponse?.movies ?: mutableListOf()) as ArrayList<Movie>?

                movieListLiveData.value = movies
            }
        })

        return movieListLiveData
    }

    /**
     * Gets the people who are credited a particular movies.
     * @param movieId the id of the movie.
     */
    fun getMovieCredits(movieId: Int) : MutableLiveData<ArrayList<Cast>> {
        val castLiveData : MutableLiveData<ArrayList<Cast>> = MutableLiveData()
        val castCall : Call<RootCastResponse> = theMovieDbAPI.getMovieCredits(movieId)
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
        val personCall : Call<Cast> = theMovieDbAPI.getPersonDetails(personId)
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
     * Gets the movies for which a person is credited.
     * @param personId the id of the person.
     */
    fun getPersonMovieCredits(personId: Int) : MutableLiveData<ArrayList<Movie>> {
        val movieListLiveData : MutableLiveData<ArrayList<Movie>> = MutableLiveData()
        val personCreditsCall: Call<RootCreditsResponse> = theMovieDbAPI.getPersonMovieCredits(personId)

        personCreditsCall.enqueue(object : Callback<RootCreditsResponse> {

            override fun onFailure(call: Call<RootCreditsResponse>, t: Throwable) {
                Log.e(TAG, "Failed search", t)
            }
            override fun onResponse(call: Call<RootCreditsResponse>, response: Response<RootCreditsResponse>){
                Log.d(TAG,"Received search ${response.body()}")
                val rootCreditsResponse : RootCreditsResponse? = response.body()
                val movies : ArrayList<Movie>? = (rootCreditsResponse?.cast ?: mutableListOf()) as ArrayList<Movie>?

                movieListLiveData.value = movies
            }
        })

        return movieListLiveData
    }

    /**
     * Gets popular TV shows.
     */
    fun getPopularTvShows(): LiveData<ArrayList<TvShow>> {

        val responseLiveData : MutableLiveData<ArrayList<TvShow>> = MutableLiveData()
        val popularTvShowCall: Call<RootPopularTVShowsResponse> = theMovieDbAPI.getPopularTvShows()
        popularTvShowCall.enqueue(object : Callback<RootPopularTVShowsResponse> {

            override fun onFailure(call: Call<RootPopularTVShowsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }

            override fun onResponse(
                call: Call<RootPopularTVShowsResponse>,
                response: Response<RootPopularTVShowsResponse>){

                    Log.d(TAG, "onResponse: ${response.body()}")
                    val resultsResponse : RootPopularTVShowsResponse? = response.body()
                    val tvShows : ArrayList<TvShow>? =
                        (resultsResponse?.tvShows ?: mutableListOf()) as ArrayList<TvShow>?

                    responseLiveData.value = tvShows
            }
        })

        return responseLiveData
    }

}