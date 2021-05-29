package com.darotpeacedude.eivom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darotpeacedude.eivom.R
import com.darotpeacedude.eivom.databinding.FragmentMainBinding
import com.darotpeacedude.eivom.utils.viewBinding


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}