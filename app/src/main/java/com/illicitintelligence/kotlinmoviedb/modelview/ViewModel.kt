package com.illicitintelligence.kotlinmoviedb.modelview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.illicitintelligence.kotlinmoviedb.model.RepoResults
import com.illicitintelligence.kotlinmoviedb.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel(application: Application): AndroidViewModel(application) {

    var liveData: MutableLiveData<RepoResults> = MutableLiveData<RepoResults>()
    var instance: RetrofitInstance = RetrofitInstance()


    fun getRepo(toSearch: String) {

        instance.getMovies(toSearch).enqueue(object : Callback<RepoResults> {
            override fun onResponse(
                call: Call<RepoResults>,
                response: Response<RepoResults>
            ) {
                Log.d("TAG_X","onResponse: ")
                var name = ""
                if (response.body()!!.movies.size.equals(0)) {
                    name = response.body()!!.movies[0].getTitle()
                }
                liveData.value = response.body()
                Log.d("TAG_X", "onResponse: $name")
            }

            override fun onFailure(call: Call<RepoResults>, t: Throwable) {
                Log.d("TAG_X", "onFailure: " + t.message)
                Log.d("TAG_X", "onFailure: $call")
            }
        })
    }

}