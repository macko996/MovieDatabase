package com.example.moviedatabase.util

interface EntityMapper <Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity( domainModel: DomainModel): Entity

    fun mapFromEntityList(entitites: List<Entity>) : List<DomainModel>
}