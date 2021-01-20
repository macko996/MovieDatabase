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
import com.example.moviedatabase.adapters.ActorAdapter
import com.example.moviedatabase.adapters.TvShowsAdapter
import com.example.moviedatabase.model.Actor
import com.example.moviedatabase.model.TvShow
import com.example.moviedatabase.repository.ActorRepository
import com.example.moviedatabase.repository.TvShowsRepository
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import javax.inject.Inject

/**
 * A Fragment for displaying details about a TV Show.
 */
@AndroidEntryPoint
class TvShowDetailsFragment : Fragment(),
    TvShowsAdapter.OnTvShowClickListener, ActorAdapter.OnPersonClickListener {

    lateinit var tvShowLiveData: LiveData<TvShow>
    lateinit var recommendedTvShowsLiveData : LiveData<List<TvShow>>
    lateinit var castLiveData: LiveData<List<Actor>>
    private val args: TvShowDetailsFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private lateinit var recommendedTvShowsRecyclerView: RecyclerView
    private lateinit var recommendedTvShowsAdapter: TvShowsAdapter
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var actorAdapter : ActorAdapter
    @Inject
    lateinit var tvShowsRepository: TvShowsRepository
    @Inject
    lateinit var actorRepository: ActorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_show_details, container, false)

        recommendedTvShowsRecyclerView = view.findViewById(R.id.recommended_tv_shows_recycler_view)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recommendedTvShowsRecyclerView.layoutManager = linearLayoutManager

        castRecyclerView = view.findViewById(R.id.cast_recycler_view)
        val castLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerView.layoutManager = castLinearLayoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvId: Int = args.tvShowId

        //get details about the movie
        tvShowLiveData = tvShowsRepository.getTvShowDetails(tvId)
        tvShowLiveData.observe(
            viewLifecycleOwner,
            Observer { tvShow ->
                Picasso.get().load(tvShow.backdropUrl).into(backdrop)
                name.text = tvShow.name
                first_air_date.text = tvShow.firstAirDate
                val runtimeString = getString(R.string.runtime, tvShow.episodeRuntime)
                runtime.text = runtimeString
                rating.text = tvShow.averageScore.toString()
                description.text = tvShow.description
                tvShow.genres.forEach { genre ->
                    genres.append(genre + " | ")
                }
                number_of_seasons.text = getString(R.string.number_of_seasons,tvShow.numberOfSeasons)
                number_of_episodes.text = getString(R.string.number_of_episodes,tvShow.numberOfEpisodes)
            })

        //get cast
        castLiveData = actorRepository.getTvShowCredits(tvId)
        castLiveData.observe(
            viewLifecycleOwner,
            Observer { cast ->
                actorAdapter = ActorAdapter(cast, this)
                castRecyclerView.setAdapter(actorAdapter)
            })

        //get recommended TV shows
        recommendedTvShowsLiveData = tvShowsRepository.getTvShowRecommendations(tvId)
        recommendedTvShowsLiveData.observe(
            viewLifecycleOwner,
            Observer { recommendedTvShows ->
                recommendedTvShowsAdapter = TvShowsAdapter(recommendedTvShows, this)
                recommendedTvShowsRecyclerView.setAdapter(recommendedTvShowsAdapter)
            })

    }

    override fun onTvShowClick(tvShowId: Int) {
        val action = TvShowDetailsFragmentDirections
            .actionTvShowDetailsFragmentSelf(tvShowId)
        navController.navigate(action)
    }

    override fun onPersonClick(personId: Int) {
        val action = TvShowDetailsFragmentDirections
            .actionTvShowDetailsFragmentToPersonFragment(personId)
        navController.navigate(action)
    }

}
