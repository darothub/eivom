package com.darotpeacedude.eivom.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.darotpeacedude.core.utils.getName
import com.darotpeacedude.data.viewmodel.MainViewModel
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.adapter.MovieAdapter
import com.darotpeacedude.eivom.databinding.FragmentMainBinding
import com.darotpeacedude.eivom.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val TAG by lazy { getName() }
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()
    val movieAdapter = MovieAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()
        setupView()
//        lifecycleScope.launch {
//            mainViewModel.getSingleSourceMovies()
//        }
    }

    override fun onResume() {
        super.onResume()
//        lifecycleScope.launchWhenResumed {
//            mainViewModel.allMovies.collect {
//                Log.i(TAG, (it as? List<Movie>).toString())
//            }
//        }
    }
    private fun setupView() {
        lifecycleScope.launch {
            mainViewModel.allMovies().collectLatest {
                Log.i(TAG, it.toString())
                movieAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        binding.movieRcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
    }
}
