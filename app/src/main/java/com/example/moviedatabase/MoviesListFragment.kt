package com.example.moviedatabase

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MoviesListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesListFragment : Fragment(), MovieAdapter.MOnItemClickListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieFetcher = MovieFetcher()
    private var movieList : ArrayList<Movie> = ArrayList<Movie>()
    private lateinit var moviesLiveData : LiveData<ArrayList<Movie>>
    private lateinit var navController: NavController
    private val args: MoviesListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        navController = findNavController()

    }

    override fun onStart() {
        super.onStart()

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
        movieRecyclerView.setAdapter(movieAdapter)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = args.searchQuery
        //get response from web request to https://www.themoviedb.org
        if (query=="/" || query.isEmpty()) {
            moviesLiveData = movieFetcher.fetchPopularMovies()
            moviesLiveData.observe(
                viewLifecycleOwner,
                Observer { movieItems ->
                    movieList.clear()
                    movieList.addAll(movieItems)
                    movieAdapter.notifyDataSetChanged()
                })
        } else{
            moviesLiveData = movieFetcher.searchMovie(args.searchQuery)
            moviesLiveData.observe(
                viewLifecycleOwner,
                Observer {movieItems ->
                    movieList.clear()
                    movieList.addAll(movieItems)
                    movieAdapter.notifyDataSetChanged()
                })
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
                    val action = MoviesListFragmentDirections.actionMoviesListFragmentSelf(query)
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
            val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(id)
                navController.navigate(action)
        }
    }

    override fun onMovieClick(id: Int) {
        onMovieClickImplementaion(id,navController)
    }

}