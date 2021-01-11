package com.example.moviedatabase

import com.example.moviedatabase.adapters.MovieAdapter
import com.example.moviedatabase.model.Movie
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.junit.Assert.assertThat
import org.hamcrest.core.Is.`is`


class MovieAdapterTest {

    private var adapter: MovieAdapter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val listener = mock(MovieAdapter.MOnItemClickListener::class.java)
        val movie = Movie()
        adapter = MovieAdapter(
            listOf(movie, movie),
            listener
        )
    }

    @Test
    fun getItemCount() {
        assertThat(adapter?.itemCount, `is`(2))
    }

}