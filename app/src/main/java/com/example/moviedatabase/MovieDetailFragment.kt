package com.example.moviedatabase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.MoviesListFragment.Companion.onMovieClickImplementaion
import com.squareup.picasso.Picasso

private const val TAG = "MovieDetailsFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment(private val movieId: Int) : Fragment(),
    MovieAdapter.MOnItemClickListener {

    private val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280"

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieFetcher = MovieFetcher()

    private lateinit var backdrop: ImageView
    private lateinit var title: TextView
    private lateinit var director: TextView
    private lateinit var releaseDate: TextView
    private lateinit var runtime: TextView
    private lateinit var rating: TextView
    private lateinit var description: TextView
    private lateinit var genres: TextView
    private lateinit var revenue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()


        //get details about the movie
        val movieLiveData: LiveData<Movie> = movieFetcher.fetchMovieDetails(movieId)
        movieLiveData.observe(
            this,
            Observer { movie ->
                val backdropUrl = BACKDROP_BASE_URL + movie.backdropPath
                Picasso.get().load(backdropUrl).into(backdrop)
                title.text = movie.title
                director.text = movie.director
                releaseDate.text = movie.releaseDate
                val runtimeString = getString(R.string.runtime, movie.runtime)
                runtime.text = runtimeString
                rating.text = movie.averageScore.toString()
                description.text = movie.description
                movie.genres.forEach { genre ->
                    genres.append(genre.name + " | ")
                }
                val revenueString = getString(R.string.box_office_revenue, movie.revenue)
                revenue.text = revenueString
            })

        //get recommended movies
        val recommendedMoviesLiveData = movieFetcher.fetchMovieRecommendations(movieId)
        recommendedMoviesLiveData.observe(
            this,
            Observer { recommendedMovies ->
                Log.d(TAG, "Received recommended movies: $recommendedMovies")
                movieAdapter = MovieAdapter(recommendedMovies, this)
                movieRecyclerView.setAdapter(movieAdapter)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        backdrop = view.findViewById(R.id.backdrop)
        title = view.findViewById(R.id.title)
        director = view.findViewById(R.id.director)
        releaseDate = view.findViewById(R.id.release_date)
        runtime = view.findViewById(R.id.runtime)
        rating = view.findViewById(R.id.rating)
        description = view.findViewById(R.id.description)
        genres = view.findViewById(R.id.genres)
        revenue = view.findViewById(R.id.revenue)

        movieRecyclerView = view.findViewById(R.id.recommended_movies_recycler_view)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieRecyclerView.layoutManager = linearLayoutManager
        return view
    }

    companion object {
        fun newInstance(id: Int) = MovieDetailFragment(id)
    }


    override fun onMovieClick(id: Int) {

        onMovieClickImplementaion(id, activity!!)
    }
}