package com.example.moviedatabase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import javax.inject.Inject

private const val TAG = "MovieDetailsFragment"

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class MovieDetailFragment() : Fragment(),
    MovieAdapter.MOnItemClickListener {

    private val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280"

    lateinit var movieLiveData: MutableLiveData<Movie>
    lateinit var recommendedMoviesLiveData : MutableLiveData<ArrayList<Movie>>
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    @Inject
    lateinit var movieFetcher: MovieFetcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)

        movieRecyclerView = view.findViewById(R.id.recommended_movies_recycler_view)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieRecyclerView.layoutManager = linearLayoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId: Int = args.movieId

        //get details about the movie
        movieLiveData = movieFetcher.fetchMovieDetails(movieId)
        movieLiveData.observe(
            viewLifecycleOwner,
            Observer { movie ->
                val backdropUrl = BACKDROP_BASE_URL + movie.backdropPath
                Picasso.get().load(backdropUrl).into(backdrop)
                title.text = movie.title
                director.text = movie.director
                release_date.text = movie.releaseDate
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
        recommendedMoviesLiveData = movieFetcher.fetchMovieRecommendations(movieId)
        recommendedMoviesLiveData.observe(
            viewLifecycleOwner,
            Observer { recommendedMovies ->
                Log.d(TAG, "Received recommended movies: $recommendedMovies")
                movieAdapter = MovieAdapter(recommendedMovies, this)
                movieRecyclerView.setAdapter(movieAdapter)
            })
    }

    override fun onMovieClick(id: Int) {

        val action = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(id)
        navController.navigate(action)
    }
}