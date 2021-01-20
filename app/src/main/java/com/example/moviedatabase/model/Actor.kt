package com.example.moviedatabase.model

data class Actor (
    var id: Int = 0,
    var name: String = "",
    var character: String = "",
    var profilePhotoUrl: String = "",
    var birthday: String = "",
    var deathday: String? = null,
    var biography: String = ""
)