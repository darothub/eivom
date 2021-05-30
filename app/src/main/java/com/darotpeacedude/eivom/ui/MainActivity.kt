package com.darotpeacedude.eivom.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darotpeacedude.eivom.databinding.ActivityMainBinding
import com.darotpeacedude.eivom.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
    }
}
