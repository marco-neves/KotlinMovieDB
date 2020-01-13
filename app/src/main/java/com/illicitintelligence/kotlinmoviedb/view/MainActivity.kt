package com.illicitintelligence.kotlinmoviedb.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.illicitintelligence.kotlinmoviedb.R
import com.illicitintelligence.kotlinmoviedb.adapter.AdapterRV
import com.illicitintelligence.kotlinmoviedb.model.Movie
import com.illicitintelligence.kotlinmoviedb.model.RepoResults
import com.illicitintelligence.kotlinmoviedb.modelview.ViewModel
import com.illicitintelligence.kotlinmoviedb.network.RetrofitInstance
import com.illicitintelligence.kotlinmoviedb.util.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterRV.MyInterface{


    var movies :ArrayList<Movie> = ArrayList<Movie>()
    lateinit var adapterRV : AdapterRV
    lateinit var fragment: Fragment
    lateinit var viewModel: ViewModel
    lateinit var myObserver: Observer<RepoResults>
    lateinit var instance: RetrofitInstance



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.sharedElementEnterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.shared_element_trans)
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        myObserver = Observer{ repoResults: RepoResults ->
                movies = repoResults.movies
                Log.d("TAG_X", "onChanged: " + movies.size)
                setUpRV()
            }

        viewModel.liveData.observe(this, myObserver)
        setUpRV()
        send_button.setOnClickListener{
            Log.d("TAG_X", "onClick: ")
            movies.clear()
            val toSearch:String = my_search_edittext.text.toString()
            if(toSearch.isEmpty())
                viewModel.getRepo(toSearch)
            else
                viewModel.getRepo(toSearch)

            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                send_button.getWindowToken(),
                0
            )
        }
    }

    private fun setUpRV() {
        adapterRV = AdapterRV(this.applicationContext, movies, this)
        Log.d("TAG_X",""+adapterRV.movies)
        movie_list_recyclerview.adapter = (adapterRV)
        movie_list_recyclerview.layoutManager=(LinearLayoutManager(this))
    }


    override fun startFrag(movie: Movie, view: ImageView) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.RETRIEVE_MOVIE_KEY, movie)
        bundle.putString("test", "test success")
        val manager = supportFragmentManager
        fragment = manager.findFragmentByTag(Constants.FRAGMENT_TAG)?:MovieDetailFragment()
        fragment.arguments = bundle

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
            .addToBackStack(fragment.tag)
            .addSharedElement(view, Constants.TRANSITION_NAME_BASE + movie.getId())
            .add(R.id.frame_layout, fragment)
            .commit()
    }
}


