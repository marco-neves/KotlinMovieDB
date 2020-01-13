package com.illicitintelligence.kotlinmoviedb.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.illicitintelligence.kotlinmoviedb.R
import com.illicitintelligence.kotlinmoviedb.model.Movie
import com.illicitintelligence.kotlinmoviedb.util.Constants
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        val args = this.arguments
        val movie:Movie ?= args?.getParcelable(Constants.RETRIEVE_MOVIE_KEY)

        //attempting to do a shared element transition (unsuccessfully)
        val transitionName = Constants.TRANSITION_NAME_BASE + movie!!.getId()
        movie_icon_imageview.setTransitionName(transitionName)
        Log.d("TAG_X", "onViewCreated: $transitionName")
        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + movie!!.getPosterPath())
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(movie_icon_imageview)

        movie_title_textview.text = (movie.title)
        if (movie.title.length > 20) {
            movie_title_textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        }
        movie_description_textview.text = (movie.getOverview())
        movie_description_textview.movementMethod = (ScrollingMovementMethod())
        movie_release_date_textview.text = (movie.getReleaseDate())
        movie_rating_textview.text  = ("Rating: " + movie.getVoteAverage())
        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + movie.getBackdropPath())
            .into(base_image_imageview)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentManager!!.popBackStack()
    }
}