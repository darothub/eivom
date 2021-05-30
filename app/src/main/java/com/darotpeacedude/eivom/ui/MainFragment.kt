package com.darotpeacedude.eivom.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.darotpeacedude.core.utils.getName
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.local.MovieDao
import com.darotpeacedude.data.viewmodel.MainViewModel
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.FragmentMainBinding
import com.darotpeacedude.eivom.utils.viewBinding
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MainFragment : Fragment(R.layout.fragment_main) {
    private val TAG by lazy { getName() }
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()
    @Inject lateinit var movieDao: MovieDao
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.getSingleSourceMovies(1)
        lifecycleScope.launchWhenResumed {
            mainViewModel.moviesFlow.collect {
                Log.i(TAG, (it as? List<Movie>).toString())
            }
        }
    }
}
