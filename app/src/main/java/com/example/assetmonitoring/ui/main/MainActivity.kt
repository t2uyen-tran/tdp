package com.example.assetmonitoring.ui.main
//SL

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.example.assetmonitoring.R
import com.example.assetmonitoring.databinding.MainActivityBinding
import com.example.assetmonitoring.ui.council.OutstandingActivity
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

        //SL: This is for the Test button to test the Council OutstandingActivity - to be deleted after
        val testButton: Button = findViewById(R.id.test_buton)
        testButton.setOnClickListener {
            //SL: go to Council OutstandingActivity page to test as "Sign In" activity not ready
            startActivity(Intent(this@MainActivity, OutstandingActivity::class.java))
            finish()
        }
    }

    //SL: this function uses the index number to retrieve the details of the selected category from the CategoriesViewModel,
    //SL: and passes the information to the second_activity page as a parcelable object in an Intent
    private fun passIntent(index: Int) {
        categoriesViewModel.currentIndex = index
        val i = Intent(this, ListCategoryItems::class.java).apply {
            putExtra("result", categoriesViewModel.currLocation)
        }
        startActivity(i)
    }

}
