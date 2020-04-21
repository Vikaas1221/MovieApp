package com.example.movieapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieData {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("original_language")
    @Expose
    var original_language: String? = null
    @SerializedName("vote_average")
    @Expose
    var vote_average: String? = null
    @SerializedName("release_date")
    @Expose
    var release_date: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdrop_path: String? = null
}
