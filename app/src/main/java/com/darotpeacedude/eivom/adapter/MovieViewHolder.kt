package com.darotpeacedude.eivom.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.utils.Constant
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.MovieItemLayoutBinding

class MovieViewHolder(private val binding: MovieItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    var movie: Movie? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(movie: Movie?, listener: (Movie) -> Unit) {
        this.movie = movie
        binding.movieIv.load(Constant.BASE_IMAGE_URL + movie?.backdropPath) {
            crossfade(true)
            placeholder(R.drawable.sample_image)
            transformations(RoundedCornersTransformation(10F, 10F, 10F, 10F))
        }
        binding.movieIv.clipToOutline = true
        binding.movieTitleTv.text = movie?.title
        binding.movieStoryTv.text = movie?.overview
        binding.movieRatingTv.text = "${movie?.voteAverage}"
        binding.root.setOnClickListener {
            if (movie != null) {
                listener(movie)
            }
        }
    }
}
