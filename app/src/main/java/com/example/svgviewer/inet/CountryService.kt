package com.example.svgviewer.inet

import com.example.svgviewer.CountryInfo
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    suspend fun getAllCountries(): List<CountryInfo>

//    @GET("name/{name}")
//    suspend fun getCountriesByName(@Path("name") id: String):  List<CountryInfo>
}