package com.example.assetmonitoring.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assetmonitoring.databinding.ReportIssueActivityBinding

class ReportIssueActivity : AppCompatActivity() {

    private lateinit var binding: ReportIssueActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ReportIssueActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}