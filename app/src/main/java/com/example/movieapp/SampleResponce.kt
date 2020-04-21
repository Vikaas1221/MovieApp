package com.example.movieapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SampleResponce
{
    @SerializedName("results")
    @Expose
    var movieData: List<MovieData>? = null



}
