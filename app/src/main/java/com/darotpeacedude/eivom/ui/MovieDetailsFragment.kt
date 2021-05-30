package com.darotpeacedude.eivom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.FragmentMovieDetailsBinding
import com.darotpeacedude.eivom.utils.viewBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
