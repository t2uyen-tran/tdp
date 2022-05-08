package com.example.assetmonitoring.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.assetmonitoring.databinding.MainActivityBinding
import com.example.assetmonitoring.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private val categoriesViewModel by viewModels<CategoriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roadImageV.setOnClickListener {
            passIntent(0)
        }

        binding.footpathImageV.setOnClickListener {
            passIntent(1)
        }

        binding.binImageV.setOnClickListener {
            passIntent(2)
        }

        binding.busstopImageV.setOnClickListener {
            passIntent(3)
        }

        binding.signInButon.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    //this function uses the index number to retrieve the details of the selected location from the LocationsViewModel,
    //and passes the information to the second_activity page as a parcelable object in an Intent
    private fun passIntent(index: Int) {
        categoriesViewModel.currentIndex = index
        val i = Intent(this, ListCategoryItems::class.java).apply {
            putExtra("result", categoriesViewModel.currLocation)
        }
        startActivity(i)
    }



}
