package com.example.moviedatabase

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter (private val movieItems: List<MovieItem>) : RecyclerView.Adapter<MovieHolder>() {

    private val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_view,parent,false)

        return MovieHolder(itemView)
    }

    override fun getItemCount(): Int = movieItems.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val movieItem = movieItems[position]

        val posterUrl =  POSTER_BASE_URL + movieItem.posterPath
        Picasso.get().load(posterUrl).into(holder.poster)

        holder.title.text = movieItem.title
        holder.releaseDate.text = movieItem.releaseDate
        holder.rating.text = movieItem.averageScore.toString()
        when{
            movieItem.averageScore <4.5 -> holder.rating.setBackgroundColor(Color.RED)
            movieItem.averageScore in 4.5 .. 7.0 -> holder.rating.setBackgroundColor(Color.YELLOW)
            movieItem.averageScore >7 -> holder.rating.setBackgroundColor(Color.GREEN)

        }
    }
}


class MovieHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    var poster : ImageView = itemView.findViewById(R.id.poster)
    var title : TextView = itemView.findViewById(R.id.title)
    var releaseDate: TextView = itemView.findViewById(R.id.release_date)
    var rating: TextView = itemView.findViewById(R.id.rating)

}


