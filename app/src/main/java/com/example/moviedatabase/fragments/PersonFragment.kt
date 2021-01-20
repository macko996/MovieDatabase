package com.example.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.adapters.MovieAdapter
import com.example.moviedatabase.adapters.TvShowsAdapter
import com.example.moviedatabase.model.Cast
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.model.TvShow
import com.example.moviedatabase.repository.CastRepository
import com.example.moviedatabase.repository.MovieRepository
import com.example.moviedatabase.repository.TvShowsRepository
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_person.*
import org.joda.time.LocalDate
import org.joda.time.Period
import org.joda.time.PeriodType
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class PersonFragment : Fragment(),
    MovieAdapter.MOnItemClickListener , TvShowsAdapter.OnTvShowClickListener{

    private val PHOTO_BASE_URL = "https://image.tmdb.org/t/p/original"

    var personId : Int = 0
    lateinit var personLiveData: LiveData<Cast>
    lateinit var personMovieCredits: LiveData<List<Movie>>
    lateinit var personTVShowsCredits: LiveData<List<TvShow>>
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowsRecyclerView: RecyclerView
    private lateinit var tvShowsAdapter: TvShowsAdapter
    private val args: PersonFragmentArgs by navArgs()
    private lateinit var navController: NavController
    @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var castRepository: CastRepository
    @Inject
    lateinit var tvShowRepository: TvShowsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person, container, false)

        movieRecyclerView = view.findViewById(R.id.movies_recycler_view)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieRecyclerView.layoutManager = linearLayoutManager

        tvShowsRecyclerView = view.findViewById(R.id.tv_shows_recycler_view)
        val tvShowsLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tvShowsRecyclerView.layoutManager = tvShowsLinearLayoutManager

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personId = args.personId

        //get details about the person
        personLiveData = castRepository.getPersonDetails(personId)
        personLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { person ->
                val photoUrl = PHOTO_BASE_URL + person.profilePath
                Picasso.get().load(photoUrl).resize(0, 1280).into(photo)

                name.text = person.name
                birthday.text = person.birthday
                biography.text = person.biography

              val birthdayLocalDate : LocalDate = LocalDate.parse(person.birthday)
              val ageInt = Period(birthdayLocalDate,LocalDate.now(), PeriodType.years()).years
              age.text = getString(R.string.age,ageInt)
            }
        )

        personMovieCredits = movieRepository.getPersonMovieCredits(personId)
        personMovieCredits.observe(
            viewLifecycleOwner,
            Observer { movies ->
                number_of_movies.text = getString(R.string.number_of_movies,movies.size)
                movieAdapter = MovieAdapter(movies, this)
                movieRecyclerView.setAdapter(movieAdapter)
            }
        )

        personTVShowsCredits = tvShowRepository.getPersonTvShowCredits(personId)
        personTVShowsCredits.observe(
            viewLifecycleOwner,
            Observer {tvShows ->
                number_of_tv_shows.text = getString(R.string.number_of_tv_shows, tvShows.size)
                tvShowsAdapter = TvShowsAdapter(tvShows, this)
                tvShowsRecyclerView.adapter = tvShowsAdapter
            }
        )

    }

    override fun onMovieClick(id: Int) {
        val action = PersonFragmentDirections
            .actionPersonFragmentToMovieDetailFragment(id)
        navController.navigate(action)
    }

    override fun onTvShowClick(tvShowId: Int) {
        val action = PersonFragmentDirections
            .actionPersonFragmentToTvShowDetailsFragment(tvShowId)
        navController.navigate(action)
    }
}