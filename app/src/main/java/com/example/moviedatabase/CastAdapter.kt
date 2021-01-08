package com.example.moviedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.model.Cast
import com.squareup.picasso.Picasso

class CastAdapter (private val cast: List<Cast>, private val listener: OnPersoClickListener)
    : RecyclerView.Adapter<CastAdapter.CastHolder>() {
    
        private val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cast_item_view,parent,false)

            // here we override the inflated view's height to be half the recyclerview size
            val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = (parent.getWidth() / 2) - layoutParams.leftMargin - layoutParams.rightMargin

            itemView.layoutParams = layoutParams
            return CastHolder(itemView)
        }

        override fun getItemCount(): Int = cast.size

        override fun onBindViewHolder(holder: CastHolder, position: Int) {

            val castItem = cast[position]

            val photoUrl = POSTER_BASE_URL + castItem.profilePath
            Picasso.get().load(photoUrl).into(holder.photo)

            holder.name.text = castItem.name
            holder.character.text = castItem.character

        }


    inner class CastHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                val id = cast[position].id
                listener.onPersonClick(id)
            }
        }

        var photo: ImageView = itemView.findViewById(R.id.photo)
        var name: TextView = itemView.findViewById(R.id.name)
        var character: TextView = itemView.findViewById(R.id.character)

    }

    interface OnPersoClickListener{
        fun onPersonClick(personId: Int)
    }
}
