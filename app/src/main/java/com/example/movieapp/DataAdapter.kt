package com.example.movieapp

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

import java.util.ArrayList

class DataAdapter(private val movieList: ArrayList<MovieData>?) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    override fun getItemCount(): Int
    {
       return movieList?.size!!;
    }


    internal var context: Context? = null

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelayout, parent, false)
        context = parent.getContext()
        return ViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = movieList?.get(position)
        holder.movie_name.setText(obj?.title)
        holder.movie_date.setText(obj?.release_date)
        holder.movie_language.setText(obj?.original_language)
        holder.movie_rating.setText(obj?.vote_average)
        val s:String= obj!!.backdrop_path.toString()
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + s).into(holder.movie_image)
        holder.movie_desc.setText(obj.overview)


        holder.movie_desc_icon.setOnClickListener(object : View.OnClickListener {
            @Override
            override fun onClick(v: View)
            {
                if (holder.movie_desc.getVisibility() === View.GONE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
                        holder.movie_desc.setVisibility(View.VISIBLE)
                        holder.movie_desc_icon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
                        holder.movie_desc.setVisibility(View.GONE)
                        holder.movie_desc_icon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                    }
                }

            }
        })


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movie_name: TextView
        val movie_desc: TextView
        val movie_rating: TextView
        val movie_date: TextView
        val movie_language: TextView
        val movie_image: ImageView
        val movie_desc_icon: ImageView
        val cardView: CardView


        init {

            movie_name = itemView.findViewById(R.id.movie_title) as TextView
            movie_image = itemView.findViewById(R.id.movie_poster) as ImageView
            movie_desc = itemView.findViewById(R.id.movie_desc) as TextView
            movie_date = itemView.findViewById(R.id.date)
            movie_rating = itemView.findViewById(R.id.rating)
            movie_language = itemView.findViewById(R.id.language)
            movie_desc_icon = itemView.findViewById(R.id.descrption)
            cardView = itemView.findViewById(R.id.card)


        }
    }
}