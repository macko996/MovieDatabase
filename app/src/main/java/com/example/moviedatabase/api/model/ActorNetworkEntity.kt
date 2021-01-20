package com.example.moviedatabase.api.model

import com.google.gson.annotations.SerializedName

data class ActorNetworkEntity(
    var id: Int = 0,
    var name: String = "",
    var character: String = "",
    @SerializedName("profile_path") var profilePath: String = "",
    var birthday: String = "",
    var deathday: String? = null,
    var biography: String = ""
){
}