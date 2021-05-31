package com.darotpeacedude.eivom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.eivom.databinding.MovieItemLayoutBinding

class MovieAdapter(var listener: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {
    var list = ArrayList<Movie>()
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(list[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    fun setData(newList: List<Movie>) {
        val diffCallback = MovieDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class MovieDiffCallback(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id === newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (_, value, name) = oldList[oldPosition]
            val (_, value1, name1) = newList[newPosition]

            return name == name1 && value == value1
        }

        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }

    override fun getItemCount(): Int = list.size
}
