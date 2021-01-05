package com.example.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class Cast (
    var id : Int = 0,
    var name: String = "",
    var character: String = "",
    @SerializedName("profile_path") var profilePath: String = ""
){
}