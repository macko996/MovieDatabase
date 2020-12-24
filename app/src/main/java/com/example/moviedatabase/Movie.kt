package com.example.moviedatabase

import com.google.gson.annotations.SerializedName

/**
 * The data for a movie
 */
data class Movie(
    var id: Int =0,
    var title: String= "",
    @SerializedName("overview") var description: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    var genre_ids: List<Int> = ArrayList<Int>(),
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("vote_average") var averageScore: Double = 0.0,
    var revenue : Int = 0,
    var runtime : Int = 0,
    var director: String = "",
    @SerializedName("backdrop_path") var backdropPath: String = ""
)
