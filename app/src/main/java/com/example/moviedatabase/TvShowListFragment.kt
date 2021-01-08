package com.example.moviedatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.model.TvShow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment for displaying list of TV shows.
 */
@AndroidEntryPoint
class TvShowListFragment : Fragment(), TvShowsAdapter.OnTvShowClickListener {

    private lateinit var tvShowsRecyclerView: RecyclerView
    private lateinit var tvShowsAdapter: TvShowsAdapter
    @Inject
    lateinit var movieFetcher : MovieFetcher
    private var tvShowsList : ArrayList<TvShow> = ArrayList<TvShow>()
    private lateinit var tvShowsLiveData : LiveData<ArrayList<TvShow>>
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        navController = findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_show_list, container, false)
        tvShowsRecyclerView = view.findViewById(R.id.tv_shows_recycler_view)
        tvShowsRecyclerView.layoutManager = GridLayoutManager(context,2)
        tvShowsAdapter = TvShowsAdapter(tvShowsList,this)
        tvShowsRecyclerView.setAdapter(tvShowsAdapter)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            tvShowsLiveData = movieFetcher.getPopularTvShows()
            tvShowsLiveData.observe(
                viewLifecycleOwner,
                Observer { tvShows ->
                    tvShowsList.clear()
                    tvShowsList.addAll(tvShows)
                    tvShowsAdapter.notifyDataSetChanged()
                })
        }

    override fun onTvShowClick(tvShowId: Int) {
        val action = TvShowListFragmentDirections
            .actionTvShowListFragmentToTvShowDetailsFragment(tvShowId)
        navController.navigate(action)
    }
}