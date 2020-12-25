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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        //get response from web request to https://www.themoviedb.org
        val moviesLiveData: LiveData<ArrayList<Movie>> = movieFetcher.fetchPopularMovies()
        moviesLiveData.observe(
            this,
            Observer { movieItems -> Log.d(TAG, "Response received : $movieItems")
                movieList = movieItems
                movieAdapter = MovieAdapter(movieList,this)
                movieRecyclerView.setAdapter(movieAdapter)
            })
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

        return view
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
                    val moviesLiveData = movieFetcher.searchMovie(query)
                    moviesLiveData.observe(
                        this@MoviesListFragment,
                        Observer { movieItems ->
                            Log.d(TAG, "Search Response received : $movieItems")
                            movieList.clear()
                            movieList.addAll(movieItems)
                            movieAdapter.notifyDataSetChanged()
                        })
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

        fun onMovieClickImplementaion(id: Int, activity: FragmentActivity){

            // Create new fragment and transaction
            Log.d(TAG," Clicked on the item $id")
            val newFragment: Fragment = MovieDetailFragment.newInstance(id)
            val transaction: FragmentTransaction = activity.getSupportFragmentManager().beginTransaction()

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, newFragment)
            transaction.addToBackStack(null)

            // Commit the transaction
            transaction.commit()
        }
    }

    override fun onMovieClick(id: Int) {
        onMovieClickImplementaion(id,activity!!)
    }

}