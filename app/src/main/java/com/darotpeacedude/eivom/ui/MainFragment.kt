package com.darotpeacedude.eivom.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darotpeacedude.core.utils.getName
import com.darotpeacedude.core.utils.goto
import com.darotpeacedude.data.local.Movie
import com.darotpeacedude.data.viewmodel.MainViewModel
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.adapter.MovieAdapter
import com.darotpeacedude.eivom.adapter.MoviePagingAdapter
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
    lateinit var moviePagingAdapter: MoviePagingAdapter
    lateinit var movieAdapter: MovieAdapter
    lateinit var progressBarUpdate: ProgressBarUpdate
    private var mLinearLayoutManager: LinearLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviePagingAdapter = MoviePagingAdapter {
            gotoDetailsFragment(it)
        }
        movieAdapter = MovieAdapter {
            gotoDetailsFragment(it)
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.netWorkStateFlow.collectLatest {
                if (it) {
                    progressBarUpdate.update(false)
                    setPagingList()
                    setupPagingView()
                    fetchOnScrollToEnd()
                } else {
                    progressBarUpdate.update(true)
                    setList()
                    Toast.makeText(
                        requireContext(),
                        "There is no network",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun fetchOnScrollToEnd() {
        binding.movieRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    setPagingList()
                    setupPagingView()
                }
            }
        })
    }

    private suspend fun setList() {
        val local = mainViewModel.allTheMovies()
        if (local.isNotEmpty()) {
            movieAdapter.setData(local.toList())
            setupList()
        }
    }

    private fun gotoDetailsFragment(it: Movie) {
        val direction = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(it)
        goto(direction)
    }

    private fun setPagingList() {
        lifecycleScope.launch {
            mainViewModel.remoteData().collectLatest {
                Log.i(TAG, it.toString())
                moviePagingAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        binding.movieRcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
            binding.fab.setOnClickListener {
                mLinearLayoutManager = LinearLayoutManager(requireContext())
                binding.movieRcv.layoutManager = mLinearLayoutManager
                mLinearLayoutManager!!.scrollToPosition((adapter as MovieAdapter).itemCount - 1)
            }
        }
    }
    private fun setupPagingView() {
        binding.movieRcv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviePagingAdapter
            binding.fab.setOnClickListener {
                mLinearLayoutManager = LinearLayoutManager(requireContext())
                binding.movieRcv.layoutManager = mLinearLayoutManager
                mLinearLayoutManager!!.scrollToPosition((adapter as MoviePagingAdapter).itemCount - 1)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        progressBarUpdate = requireActivity() as ProgressBarUpdate
    }
}

interface ProgressBarUpdate {
    fun update(update: Boolean)
}
