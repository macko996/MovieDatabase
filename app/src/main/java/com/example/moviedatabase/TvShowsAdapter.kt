package com.example.moviedatabase

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.model.TvShow
import com.squareup.picasso.Picasso

class TvShowsAdapter (private val tvShows: List<TvShow>)
    : RecyclerView.Adapter<TvShowsAdapter.TvShowHolder>() {

    private val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_show_item_view, parent, false)

        // here we override the inflated view's height to be half the recyclerview size
        val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width =
            (parent.getWidth() / 2) - layoutParams.leftMargin - layoutParams.rightMargin

        itemView.layoutParams = layoutParams
        return TvShowHolder(itemView)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {

        val tvShow = tvShows[position]

        val posterUrl = POSTER_BASE_URL + tvShow.posterPath
        Picasso.get().load(posterUrl).into(holder.poster)

        holder.name.text = tvShow.name
        holder.firstAirDate.text = tvShow.firstAirDate
        holder.rating.text = tvShow.averageScore.toString()
        when {
            tvShow.averageScore < 4.5 -> holder.rating.setBackgroundColor(Color.RED)
            tvShow.averageScore in 4.5..7.0 -> holder.rating.setBackgroundColor(Color.YELLOW)
            tvShow.averageScore > 7 -> holder.rating.setBackgroundColor(Color.GREEN)

        }
    }


    inner class TvShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var poster: ImageView = itemView.findViewById(R.id.poster)
        var name: TextView = itemView.findViewById(R.id.name)
        var firstAirDate: TextView = itemView.findViewById(R.id.first_air_date)
        var rating: TextView = itemView.findViewById(R.id.rating)

    }
}


