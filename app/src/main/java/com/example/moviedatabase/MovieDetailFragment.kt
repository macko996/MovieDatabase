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
import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "MovieDetailsFragment"

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class MovieDetailFragment() : Fragment(),
    MovieAdapter.MOnItemClickListener, CastAdapter.OnPersonClickListener{

    private val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280"

    lateinit var movieLiveData: MutableLiveData<Movie>
    lateinit var recommendedMoviesLiveData : MutableLiveData<ArrayList<Movie>>
    lateinit var castLiveData: MutableLiveData<ArrayList<Cast>>
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var castAdapter : CastAdapter
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

        castRecyclerView = view.findViewById(R.id.cast_recycler_view)
        val castLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerView.layoutManager = castLinearLayoutManager
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
                val revenueFormatted = NumberFormat.getNumberInstance(Locale.ENGLISH)
                    .format(movie.revenue).toString()
                val revenueString = getString(R.string.box_office_revenue, revenueFormatted)
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

        //get cast
        castLiveData = movieFetcher.getMovieCredits(movieId)
        castLiveData.observe(
            viewLifecycleOwner,
            Observer {cast ->
                Log.d(TAG, "Received cast: $cast")
                castAdapter = CastAdapter(cast,this)
                castRecyclerView.adapter = castAdapter
        })
    }

    override fun onMovieClick(id: Int) {

        val action = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(id)
        navController.navigate(action)
    }

    override fun onPersonClick(personId: Int) {
        val action = MovieDetailFragmentDirections
            .actionMovieDetailFragmentToPersonFragment(personId)
        navController.navigate(action)
    }
}