package com.example.assetmonitoring.ui.council

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assetmonitoring.databinding.OutstandingActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OutstandingActivity : AppCompatActivity() {
    private lateinit var binding: OutstandingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OutstandingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}