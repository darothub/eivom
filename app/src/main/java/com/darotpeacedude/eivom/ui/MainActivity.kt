package com.darotpeacedude.eivom.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.darotpeacedude.core.utils.hide
import com.darotpeacedude.core.utils.hideSystemUI
import com.darotpeacedude.core.utils.show
import com.darotpeacedude.eivom.databinding.ActivityMainBinding
import com.darotpeacedude.eivom.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProgressBarUpdate {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private var progressBar: ProgressBar? = null
    lateinit var progressBarUpdate: ProgressBarUpdate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        progressBarUpdate = this
        hideSystemUI()
    }

    override fun update(update: Boolean) {
        if (update) binding.progressbar.show()
        else binding.progressbar.hide()
    }
}
