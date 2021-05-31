package com.darotpeacedude.eivom.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.darotpeacedude.core.utils.gotoUp
import com.darotpeacedude.core.utils.hide
import com.darotpeacedude.core.utils.show
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.utils.Constant
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.FragmentMovieDetailsBinding
import com.darotpeacedude.eivom.utils.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val arg: MovieDetailsFragmentArgs by navArgs()
    lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.bs.root)
        bottomSheetBehaviour()
        setData(arg.movie)

        binding.backbtnIv.setOnClickListener {
            gotoUp()
        }
    }

    private fun setData(movie: Movie?) {
        movie?.apply {
            binding.blurBgIv.load(Constant.BASE_IMAGE_URL + backdropPath) {
                crossfade(true)
                placeholder(R.drawable.sample_image)
            }
            binding.bs.movieIv.load(Constant.BASE_IMAGE_URL + backdropPath) {
                crossfade(true)
                placeholder(R.drawable.sample_image)
            }
            binding.bs.movieIv.clipToOutline = true
            binding.bs.introductionTextTv.text = overview
            binding.bs.movieTitleTv.text = movie.title
            val rating = voteAverage / 2
            binding.bs.ratingBarRb.rating = rating.toFloat()
            binding.bs.movieRatingTv.text = "$voteAverage"
        }
    }

    private fun bottomSheetBehaviour() {
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.animationView.hide()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.animationView.show()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.animationView.hide()
                    }
                }
            }
        })
    }
}
