package com.example.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class Episode (
    var id : Int = 0,
    var name: String= "",
    @SerializedName("overview") var description: String = "",
    @SerializedName("air_date") var airDate: String = "",
    @SerializedName("episode_number") var episodeNumber: Int = 0,
    @SerializedName("season_number") var seasonNumber: Int = 0,
    @SerializedName("vote_average") var averageScore: Double = 0.0,
    @SerializedName("still_path") var stillPath: String = ""
//crew / casting /actors ( just the main actors not 300000 people)
)