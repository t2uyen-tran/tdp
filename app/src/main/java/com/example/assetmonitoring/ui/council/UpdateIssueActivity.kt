package com.example.assetmonitoring.ui.council

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assetmonitoring.databinding.UpdateIssueActivityBinding

class UpdateIssueActivity : AppCompatActivity() {

    private lateinit var binding: UpdateIssueActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UpdateIssueActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}