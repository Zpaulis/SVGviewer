package com.example.svgviewer

import androidx.lifecycle.ViewModel
import com.example.svgviewer.inet.CountryRepository

class MainViewModel (private val repository: CountryRepository = CountryRepository) :ViewModel(){
        fun getAllCountries() = repository.getAllCountries()
//    fun getCountriesByName() = repository.getCountriesByName(CountryInfo.name)
    }
