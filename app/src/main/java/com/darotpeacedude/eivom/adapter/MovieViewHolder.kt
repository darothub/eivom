package com.darotpeacedude.eivom.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.utils.Constant
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.MovieItemLayoutBinding
import kotlin.math.roundToInt

class MovieViewHolder(private val binding: MovieItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    var movie: Movie? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(movie: Movie?, listener: (Movie) -> Unit) {
        this.movie = movie
        binding.movieIv.load(Constant.BASE_IMAGE_URL + movie?.posterPath) {
            crossfade(true)
            placeholder(R.drawable.sample_image)
        }
        binding.movieIv.clipToOutline = true
        binding.movieTitleTv.text = movie?.title
        binding.movieStoryTv.text = movie?.overview
        val rating = movie?.voteAverage?.div(2)?.roundToInt()?.toFloat()
        binding.movieRatingTv.text = "$rating"
        binding.root.setOnClickListener {
            if (movie != null) {
                listener(movie)
            }
        }
    }
}
