package com.example.svgviewer

//data class CountryInfo(
//    val id: String,
//    val url: String,
//    val width: Long,
//    val height: Long
//)


data class CountryInfo(
    val name: String,
    val topLevelDomain: List<String>,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val population: Long,
    val latlng: List<Double>,
    val demonym: String,
    val area: Double?,
    val gini: Double?,
    val timezones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String?,
    val currencies: List<Currency>,
    val languages: String?,
    val translations: String?,
    val url: String,
    val regionalBlocs: List<RegionalBloc>,
    val cioc: String?
)

data class Currency(
    val code: String?,
    val name: String?,
    val symbol: String?
)

data class RegionalBloc(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>,
    val otherNames: List<String>
)