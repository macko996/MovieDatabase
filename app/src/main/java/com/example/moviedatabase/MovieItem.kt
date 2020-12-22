package com.example.moviedatabase

import com.google.gson.annotations.SerializedName

data class MovieItem(
    var id: Int,
    var title: String= "",
    @SerializedName("overview") var description: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    var genre_ids: List<Int>,
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("vote_average") var averageScore: Double = 0.0)