package com.illicitintelligence.kotlinmoviedb.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.illicitintelligence.kotlinmoviedb.R
import com.illicitintelligence.kotlinmoviedb.model.Movie
import com.illicitintelligence.kotlinmoviedb.util.Constants
import kotlinx.android.synthetic.main.recyclerview_layout.view.*

class AdapterRV(var context: Context, var movies: List<Movie>, var myInterface: MyInterface): RecyclerView.Adapter<AdapterRV.ViewHolder>(){

    interface MyInterface{
        fun startFrag(movie: Movie, view: ImageView)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.movie_title_textview
        var icon : ImageView = itemView.movie_icon_imageview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var titleText: String = movies.get(position).getTitle()
        holder.title.text = titleText
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24f)
        var movieURL : String = movies.get(position).getPosterPath()
        movieURL=Constants.BASE_URL_IMAGE+movieURL
        Glide.with(context).load(movieURL)
            .placeholder(R.drawable.ic_photo).into(holder.icon)
        holder.icon.setOnClickListener{
            myInterface.startFrag(movies[position],holder.icon)
        }
        ViewCompat.setTransitionName(holder.icon,Constants.TRANSITION_NAME_BASE+movies.get(position).id)
    }
}

