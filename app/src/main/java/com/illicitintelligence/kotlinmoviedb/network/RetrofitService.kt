package com.illicitintelligence.kotlinmoviedb.network

import com.illicitintelligence.kotlinmoviedb.model.RepoResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/3/search/movie")
    abstract fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") keyWords: String
    ): Call<RepoResults>
}