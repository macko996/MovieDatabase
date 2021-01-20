package com.example.moviedatabase.api.networkEntities

import com.google.gson.annotations.SerializedName

/**
 * The data for a movie from the network
 */
data class MovieNetworkEntity(
    var id: Int =0,
    var title: String= "",
    @SerializedName("overview") var description: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    var genres: ArrayList<Genres> = ArrayList(),
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("vote_average") var averageScore: Double = 0.0,
    var revenue : Int = 0,
    var runtime : Int = 0,
    var director: String = "",
    @SerializedName("backdrop_path") var backdropPath: String = ""
)