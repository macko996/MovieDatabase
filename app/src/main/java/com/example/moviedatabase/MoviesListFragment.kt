package com.example.moviedatabase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.api.TheMovieDbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "MoviesListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesListFragment : Fragment() {
    private lateinit var movieRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get response from web request to https://www.themoviedb.org
        val moviesLiveData: LiveData<String> = MovieDBFetcher().fetchContents()
        moviesLiveData.observe(
            this,
            Observer { responseString -> Log.d(TAG, "Response received : $responseString")
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * @return A new instance of fragment MoviesListFragment.
         */
        fun newInstance() = MoviesListFragment()
    }

}