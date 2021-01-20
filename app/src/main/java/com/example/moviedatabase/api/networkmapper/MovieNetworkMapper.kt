package com.example.moviedatabase.api.networkmapper

import com.example.moviedatabase.api.networkEntities.Genres
import com.example.moviedatabase.api.networkEntities.MovieNetworkEntity
import com.example.moviedatabase.model.Movie
import com.example.moviedatabase.util.EntityMapper
import javax.inject.Inject

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280"

class MovieNetworkMapper @Inject constructor() : EntityMapper<MovieNetworkEntity,Movie> {

    override fun mapFromEntity(entity: MovieNetworkEntity): Movie {

        val posterUrl = POSTER_BASE_URL + entity.posterPath
        val backDropUrl = BACKDROP_BASE_URL + entity.backdropPath
        val genres: ArrayList<String> = ArrayList()
        entity.genres.forEach { genre -> genres.add(genre.name)}
        return Movie(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            posterUrl = posterUrl,
            genres = genres,
            releaseDate = entity.releaseDate,
            averageScore = entity.averageScore,
            revenue = entity.revenue,
            runtime = entity.runtime,
            director = entity.director,
            backdropUrl = backDropUrl
        )
    }

    override fun mapToEntity(domainModel: Movie): MovieNetworkEntity {

        val posterPath = domainModel.posterUrl.removePrefix(POSTER_BASE_URL)
        val backdropPath = domainModel.backdropUrl.removePrefix(BACKDROP_BASE_URL)
        val genres : ArrayList<Genres> = ArrayList()
        domainModel.genres.forEach { genre ->  genres.add(Genres(name = genre))}

        return MovieNetworkEntity(
        id = domainModel.id,
        title = domainModel.title,
        description = domainModel.description,
        posterPath= posterPath,
        genres = genres,
        releaseDate = domainModel.releaseDate,
        averageScore = domainModel.averageScore,
        revenue = domainModel.revenue,
        runtime = domainModel.runtime,
        director = domainModel.director,
        backdropPath = backdropPath
        )
    }

    fun mapFromEntityList(entitites: List<MovieNetworkEntity>) : ArrayList<Movie> {
        val mappedMovies = entitites.map { mapFromEntity(it) }
        return mappedMovies as ArrayList<Movie>
    }
}