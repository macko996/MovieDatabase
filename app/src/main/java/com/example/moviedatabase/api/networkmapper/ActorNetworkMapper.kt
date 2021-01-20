package com.example.moviedatabase.api.networkmapper

import com.example.moviedatabase.api.model.ActorNetworkEntity
import com.example.moviedatabase.model.Actor
import com.example.moviedatabase.util.EntityMapper
import javax.inject.Inject

const val PROFILE_PHOTO_BASE_URL = "https://image.tmdb.org/t/p/w500"

class ActorNetworkMapper @Inject constructor() : EntityMapper<ActorNetworkEntity,Actor> {

    override fun mapFromEntity(entity: ActorNetworkEntity): Actor {

        val profilePhotoUrl = PROFILE_PHOTO_BASE_URL + entity.profilePath
        return Actor(
            id = entity.id,
            name = entity.name,
            profilePhotoUrl = profilePhotoUrl,
            character = entity.character,
            biography = entity.biography,
            birthday = entity.birthday,
            deathday = entity.deathday
        )
    }

    override fun mapToEntity(domainModel: Actor): ActorNetworkEntity {

        val profilePath = domainModel.profilePhotoUrl.removePrefix(PROFILE_PHOTO_BASE_URL)

        return ActorNetworkEntity(
            id = domainModel.id,
            name = domainModel.name,
            profilePath = profilePath,
            character = domainModel.character,
            biography = domainModel.biography,
            birthday = domainModel.birthday,
            deathday = domainModel.deathday
        )
    }

    override fun mapFromEntityList(entitites: List<ActorNetworkEntity>): List<Actor> {
        val mappedActors = entitites.map { mapFromEntity(it) }
        return mappedActors
    }
}