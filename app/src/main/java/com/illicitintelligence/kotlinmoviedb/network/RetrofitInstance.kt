package com.illicitintelligence.kotlinmoviedb.network

import com.illicitintelligence.kotlinmoviedb.model.RepoResults
import com.illicitintelligence.kotlinmoviedb.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance() {

    private var retrofitServiceInstance: RetrofitService

    init{
        retrofitServiceInstance = getInstanceOfMoviesDBRetrofit(getInstance())
    }

    private fun getInstanceOfMoviesDBRetrofit(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getMovies(toSearch: String): Call<RepoResults> {
        return retrofitServiceInstance.getMovies(Constants.MY_API_KEY, toSearch)
    }

}
