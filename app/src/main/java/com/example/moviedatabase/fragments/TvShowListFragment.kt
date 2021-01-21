package com.example.moviedatabase.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.adapters.TvShowsAdapter
import com.example.moviedatabase.model.TvShow
import com.example.moviedatabase.repository.TvShowsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "TvShowListFragment"
private const val PAGE_SIZE = 20

/**
 * Fragment for displaying list of TV shows.
 */
@AndroidEntryPoint
class TvShowListFragment : Fragment(),
    TvShowsAdapter.OnTvShowClickListener {

    private lateinit var tvShowsRecyclerView: RecyclerView
    private lateinit var tvShowsAdapter: TvShowsAdapter
    @Inject
    lateinit var tvShowsRepository: TvShowsRepository
    private var tvShowsList : ArrayList<TvShow> = ArrayList()
    private lateinit var tvShowsLiveData : LiveData<List<TvShow>>
    private lateinit var navController: NavController
    private val args: TvShowListFragmentArgs by navArgs()

    private var page : Int = 1
    private var query: String? = null

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
        tvShowsAdapter = TvShowsAdapter(tvShowsList, this)
        tvShowsRecyclerView.setAdapter(tvShowsAdapter)
        tvShowsRecyclerView.addOnScrollListener(TvShowsOnScrollListener())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = args.searchQuery

        if (query == null) {
            getPopularTvShows(page)
        } else {
            searchTvShows(query!!, page)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {

                    Log.d(TAG, "onQueryTextSubmit: $query")
                    val action = TvShowListFragmentDirections
                        .actionTvShowListFragmentSelf(query)
                    navController.navigate(action)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $query")
                    return false
                }
            })
        }
    }

    /**
     * Navigates to detail fragment when you click on a TV Show.
     * @param tvShowId the id of the TV Show that was clicked.
     */
        override fun onTvShowClick(tvShowId: Int) {
        val action = TvShowListFragmentDirections
            .actionTvShowListFragmentToTvShowDetailsFragment(tvShowId)
        navController.navigate(action)
    }


    /**
     * Gets popular TV shows and adds them to the recyclerView adapter.
     */
    private fun getPopularTvShows(page: Int) {

        if (tvShowsList.size == page * PAGE_SIZE) return
        else {
            tvShowsRepository.getPopularTvShows(page).observe(
                viewLifecycleOwner,
                Observer {
                    tvShowsList.addAll(it)
                    tvShowsAdapter.notifyDataSetChanged()
                })
        }

    }

    /**
     * Searches TV Shows and adds them to the recyclerView.
     */
    private fun searchTvShows(query: String, page: Int) {
        tvShowsRepository.searchTvShow(query,page).observe(
            viewLifecycleOwner,
            Observer {movieItems ->
                tvShowsList.addAll(movieItems)
                tvShowsAdapter.notifyDataSetChanged()
            })
    }

    /**
     * OnScrollListener that gets new data when we scroll to the bottom.
     */
    inner class TvShowsOnScrollListener : RecyclerView.OnScrollListener() {

        private var lastVisibleItem = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (isScrollCompleted() && newState == RecyclerView.SCROLL_STATE_IDLE){
                page++
                if (query == null){
                    getPopularTvShows(page)
                } else {
                    searchTvShows(query!!,page)
                }

            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
            lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
            Log.d(TAG, "onScrolled: lastVisibleItem $lastVisibleItem")
        }

        private fun isScrollCompleted(): Boolean {
            val numberOfItems = page* PAGE_SIZE

            if ( (lastVisibleItem+3) >= numberOfItems) {
                return true
            }
            else
                return false
        }

    }

}