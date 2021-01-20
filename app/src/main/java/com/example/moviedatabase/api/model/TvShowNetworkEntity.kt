package com.example.moviedatabase.api.model

import com.example.moviedatabase.model.Episode
import com.google.gson.annotations.SerializedName

/**
 * The data for a TV Show from network.
 */
data class TvShowNetworkEntity(
        var id: Int =0,
        var name: String= "",
        @SerializedName("overview") var description: String = "",
        @SerializedName("poster_path") var posterPath: String = "",
        @SerializedName("genre_ids") var genreIds: List<Int> = ArrayList(),
        var genres: ArrayList<Genres> = ArrayList(),
        @SerializedName("first_air_date") var firstAirDate: String = "",
        @SerializedName("last_air_date") var lastAirDate: String = "",
        @SerializedName("last_episode_to_air") var lastEpisodeToAir: Episode? = Episode(),
        @SerializedName("next_episode_to_air") var nextEpisodeToAir: Episode? = Episode(),
        @SerializedName("episode_run_time") var episodeRuntime: List<Int> = ArrayList(),
        @SerializedName("vote_average") var averageScore: Double = 0.0,
        @SerializedName("number_of_episodes") var numberOfEpisodes: Int = 0,
        @SerializedName("number_of_seasons") var numberOfSeasons: Int = 0,
        var status: String = "",
        @SerializedName("backdrop_path") var backdropPath: String = ""
)