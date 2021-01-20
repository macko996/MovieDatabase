package com.example.moviedatabase.model

import com.example.moviedatabase.api.networkEntities.Genres
import com.google.gson.annotations.SerializedName

data class Seasons (
    var id: Int =0,
//    @SerializedName("_id") var id: Int = 0,
    var name: String= "",
    @SerializedName("overview") var description: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    var genres: ArrayList<Genres> = ArrayList(),
    @SerializedName("air_date") var airDate: String = "",
    var episodes: ArrayList<Episode> = ArrayList(),
    @SerializedName("season_number") var seasonNumber : Int = 0
)