package com.example.svgviewer

import com.example.svgviewer.CountryInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all?fields=name,flag")
    suspend fun getAllCountries(): List<CountryInfo>

//    @GET("name/{name}")
//    suspend fun getCountriesByName(@Path("name") id: String):  List<CountryInfo>
}