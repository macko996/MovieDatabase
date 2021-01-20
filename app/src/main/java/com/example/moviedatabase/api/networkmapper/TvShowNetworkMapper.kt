package com.example.moviedatabase.api.networkmapper

import com.example.moviedatabase.api.networkEntities.Genres
import com.example.moviedatabase.api.networkEntities.TvShowNetworkEntity
import com.example.moviedatabase.model.TvShow
import com.example.moviedatabase.util.EntityMapper
import javax.inject.Inject


class TvShowNetworkMapper @Inject constructor() : EntityMapper<TvShowNetworkEntity, TvShow> {

    override fun mapFromEntity(entity: TvShowNetworkEntity): TvShow {

        val posterUrl = POSTER_BASE_URL + entity.posterPath
        val backDropUrl = BACKDROP_BASE_URL + entity.backdropPath
        val genres: ArrayList<String> = ArrayList()
        entity.genres.forEach { genre -> genres.add(genre.name)}

        val runtime : Int
        if (entity.episodeRuntime.size !=0) {
             runtime = entity.episodeRuntime.get(0)
        } else{
            runtime = 0
        }

        return TvShow(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            posterUrl = posterUrl,
            genres = genres,
            firstAirDate = entity.firstAirDate,
            lastAirDate = entity.lastAirDate,
            lastEpisodeToAir = entity.lastEpisodeToAir,
            nextEpisodeToAir = entity.nextEpisodeToAir,
            episodeRuntime = runtime,
//            episodeRuntime = entity.episodeRuntime.get(0),
            averageScore = entity.averageScore,
            numberOfEpisodes = entity.numberOfEpisodes,
            numberOfSeasons = entity.numberOfSeasons,
            status = entity.status,
            backdropUrl = backDropUrl
        )
    }

    override fun mapToEntity(domainModel: TvShow): TvShowNetworkEntity {

        val posterPath = domainModel.posterUrl.removePrefix(POSTER_BASE_URL)
        val backdropPath = domainModel.backdropUrl.removePrefix(BACKDROP_BASE_URL)
        val genres : ArrayList<Genres> = ArrayList()
        domainModel.genres.forEach { genre ->  genres.add(Genres(name = genre))}
//        val episodeRuntimeList = domainModel.episodeRuntime as ArrayList<Int>
        val list : ArrayList<Int> = ArrayList()
        list.add(domainModel.episodeRuntime)
        val episodeRuntimeList: List<Int> = list

        return TvShowNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            posterPath = posterPath,
            genres = genres,
            firstAirDate = domainModel.firstAirDate,
            lastAirDate = domainModel.lastAirDate,
            lastEpisodeToAir = domainModel.lastEpisodeToAir,
            nextEpisodeToAir = domainModel.nextEpisodeToAir,
            episodeRuntime = episodeRuntimeList,
            averageScore = domainModel.averageScore,
            numberOfEpisodes = domainModel.numberOfEpisodes,
            numberOfSeasons = domainModel.numberOfSeasons,
            status = domainModel.status,
            backdropPath = backdropPath
        )
    }

    override fun mapFromEntityList(entitites: List<TvShowNetworkEntity>): List<TvShow> {
        val mappedTvShows = entitites.map { mapFromEntity(it) }
        return mappedTvShows
    }
}