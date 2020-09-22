package com.example.svgviewer

import retrofit2.Call
import retrofit2.http.GET

interface CountryService {
    @GET("images/search")
    fun getImage(): Call<List<CountryInfo>>
}