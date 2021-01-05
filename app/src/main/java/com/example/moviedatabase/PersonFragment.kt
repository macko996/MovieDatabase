package com.example.moviedatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviedatabase.model.Cast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_person.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class PersonFragment : Fragment() {

    private val PHOTO_BASE_URL = "https://image.tmdb.org/t/p/original"

    var personId : Int = 0
    lateinit var personLiveData: MutableLiveData<Cast>
    private val args: PersonFragmentArgs by navArgs()
    private lateinit var navController: NavController
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
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personId = args.personId

        //get details about the person
        personLiveData = movieFetcher.getPersonDetails(personId)
        personLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { person ->
                val photoUrl = PHOTO_BASE_URL + person.profilePath
                Picasso.get().load(photoUrl).resize(0, 1280).into(photo)
                name.text = person.name
                birthday.text = person.birthday
                biography.text = person.biography
            }
        )
    }

}