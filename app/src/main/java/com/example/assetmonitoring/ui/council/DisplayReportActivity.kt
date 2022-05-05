package com.example.assetmonitoring.ui.council

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assetmonitoring.databinding.DisplayReportActivityBinding
import com.example.assetmonitoring.databinding.OutstandingActivityBinding

class DisplayReportActivity : AppCompatActivity() {

    private lateinit var binding: DisplayReportActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DisplayReportActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}