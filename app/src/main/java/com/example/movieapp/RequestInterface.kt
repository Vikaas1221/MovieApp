package com.example.movieapp

import retrofit2.Call
import retrofit2.http.GET

interface RequestInterface {


    @get:GET("3/movie/top_rated?api_key=1f59ebe04c42625fc6c290dccb75e0d5&language=en-US&page=1")
    val json: Call<SampleResponce>

    @get:GET("3/movie/upcoming?api_key=1f59ebe04c42625fc6c290dccb75e0d5&language=en-US&page=1")
    val upcoming: Call<SampleResponce>
}
