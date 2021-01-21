package com.example.moviedatabase.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.moviedatabase.R
import com.example.moviedatabase.adapters.MovieAdapter
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.repository.MovieRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MoviesListFragment"
const val PAGE_SIZE = 20

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MoviesListFragment : Fragment(), MovieAdapter.MOnItemClickListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    @Inject
    lateinit var movieRepository : MovieRepository
    private var movieList : ArrayList<Movie> = ArrayList()
    private lateinit var navController: NavController
    private val args: MoviesListFragmentArgs by navArgs()

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
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        movieRecyclerView = view.findViewById(R.id.movies_recycler_view)
        movieRecyclerView.layoutManager = GridLayoutManager(context,2)
        movieAdapter = MovieAdapter(movieList, this)
        movieRecyclerView.adapter = movieAdapter
        movieRecyclerView.addOnScrollListener(CustomOnScrollListener())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = args.searchQuery

        if (query == null) {
            getPopularMovies(page)
        } else {
            searchMovies(query!!, page)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {

                    Log.d(TAG, "QueryTextSubmit: $query")
                    val action = MoviesListFragmentDirections
                        .actionMoviesListFragmentSelf(query)
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * @return A new instance of fragment MoviesListFragment.
         */
        fun newInstance() = MoviesListFragment()

        fun onMovieClickImplementaion(id: Int, navController: NavController){
            val action = MoviesListFragmentDirections
                .actionMoviesListFragmentToMovieDetailFragment(id)
            navController.navigate(action)
        }
    }

    override fun onMovieClick(id: Int) {
        onMovieClickImplementaion(id,navController)
    }

    /**
     * Gets movies from repository and adds them to the recyclerView adapter
     */
    private fun getPopularMovies(page: Int) {

        if (movieList.size == page * PAGE_SIZE) return
        else {
            movieRepository.getPopularMovies(page).observe(
            viewLifecycleOwner,
            Observer {
                movieList.addAll(it)
                movieAdapter.notifyDataSetChanged()
            })
        }

    }

    /**
     * Searches movies and adds them to the recyclerView
     */
    private fun searchMovies(query: String, page: Int) {
        movieRepository.searchMovie(query,page).observe(
            viewLifecycleOwner,
            Observer {movieItems ->
                movieList.addAll(movieItems)
                movieAdapter.notifyDataSetChanged()
            })
    }

    /**
     * OnScrollListener that gets new data when we scroll to the bottom.
     */
    inner class CustomOnScrollListener : RecyclerView.OnScrollListener() {

        private var lastVisibleItem = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (isScrollCompleted() && newState == SCROLL_STATE_IDLE){
                page++
                if (query == null){
                    getPopularMovies(page)
                } else {
                    searchMovies(query!!,page)
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