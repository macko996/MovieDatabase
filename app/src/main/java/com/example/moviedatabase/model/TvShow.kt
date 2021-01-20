package com.example.moviedatabase.model

/**
 * The data for a TV Show
 */
data class TvShow (
    var id: Int =0,
    var name: String= "",
    var description: String = "",
    var posterUrl: String = "",
    var genres: ArrayList<String> = ArrayList(),
    var firstAirDate: String = "",
    var lastAirDate: String = "",
    var lastEpisodeToAir: Episode? = Episode(),
    var nextEpisodeToAir: Episode? = Episode(),
    var episodeRuntime: Int = 0,
    var averageScore: Double = 0.0,
    var numberOfEpisodes: Int = 0,
    var numberOfSeasons: Int = 0,
    var status : String = "",
    var backdropUrl: String = ""
)