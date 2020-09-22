package com.example.svgviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(CountryService::class.java)

    fun getImage(): LiveData<CountryInfo>{
        val data = MutableLiveData<CountryInfo>()

        service.getImage().enqueue(object : Callback<List<CountryInfo>> {
            override fun onResponse(
                call: Call<List<CountryInfo>>,
                response: Response<List<CountryInfo>>
            ) {
                println(response.body())
                data.postValue(response.body()?.first())
            }

            override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
                println(t.message)
            }
        }
        )
        return data
    }

}