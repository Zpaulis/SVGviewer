package com.example.svgviewer

import androidx.lifecycle.liveData
import androidx.lifecycle.MutableLiveData
import com.example.svgviewer.CountryServiceProvider.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Loaded<T> : Resource<T>()
    class Error<T>(val message: String?) : Resource<T>()
}

    object CountryRepository {

        private val service: CountryService by lazy { retrofit }

        fun getAllCountries() = request { service.getAllCountries() }

//    fun getCountriesByName(name: String) = request { service.getCountriesByName(name) }

//    fun getImage(): LiveData<CountryInfo>{
//        val data = MutableLiveData<CountryInfo>()
//
//        service.getAllCountries().enqueue(object : Callback<List<CountryInfo>> {
//            override fun onResponse(
//                call: Call<List<CountryInfo>>,
//                response: Response<List<CountryInfo>>
//            ) {
//                println(response.body())
//                data.postValue(response.body()?.first())
//            }
//
//            override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
//                println(t.message)
//            }
//        }
//        )
//        return data
//    }


    private fun <T> request(request: suspend () -> T) = liveData<Resource<T>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(request()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        } finally {
            emit(Resource.Loaded())
        }
    }
}