package com.example.moviedatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.model.Actor
import com.squareup.picasso.Picasso

class ActorAdapter (private val actors: List<Actor>, private val listener: OnPersonClickListener)
    : RecyclerView.Adapter<ActorAdapter.CastHolder>() {
    
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

        override fun getItemCount(): Int = actors.size

        override fun onBindViewHolder(holder: CastHolder, position: Int) {

            val actor = actors[position]

            Picasso.get().load(actor.profilePhotoUrl).into(holder.photo)

            holder.name.text = actor.name
            holder.character.text = actor.character

        }


    inner class CastHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                val id = actors[position].id
                listener.onPersonClick(id)
            }
        }

        var photo: ImageView = itemView.findViewById(R.id.photo)
        var name: TextView = itemView.findViewById(R.id.name)
        var character: TextView = itemView.findViewById(R.id.character)

    }

    interface OnPersonClickListener{
        fun onPersonClick(personId: Int)
    }
}
