package com.example.moviedatabase.model

/**
 * The data for a movie
 */
data class Movie(
    var id: Int =0,
    var title: String= "",
    var description: String = "",
    var posterUrl: String = "",
    var genres: ArrayList<String> = ArrayList(),
    var releaseDate: String = "",
    var averageScore: Double = 0.0,
    var revenue : Int = 0,
    var runtime : Int = 0,
    var director: String = "",
    var backdropUrl: String = ""
)