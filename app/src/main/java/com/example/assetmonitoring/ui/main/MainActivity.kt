package com.example.assetmonitoring.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.viewModels
import com.example.assetmonitoring.databinding.MainActivityBinding
import com.example.assetmonitoring.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.assetmonitoring.R

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    ///associate LocationsViewModel with the UI controller by creating a reference to the
    //LocationsViewModel inside the UI controller
    private val categoriesViewModel: CategoriesViewModel by lazy {
        ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    //set a variable to track the index number of a location selected by a click event.
    //the index number is needed to retrieve details of a location,
    //stored as a Location object in a list on the LocationsViewModel
    private var imageIndex: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val d = Log.d(TAG, "onStart()called")
        setContentView(R.layout.main_activity)

        val categoryImage1 = findViewById<ImageView>(R.id.road_imageV)
        val categoryImage2 = findViewById<ImageView>(R.id.footpath_imageV)
        val categoryImage3 = findViewById<ImageView>(R.id.bin_imageV)
        val categoryImage4 = findViewById<ImageView>(R.id.busstop_imageV)


        //this function uses the index number to retrieve the details of the selected location from the LocationsViewModel,
        //and passes the information to the second_activity page as a parcelable object in an Intent
        fun passIntent(){
            categoriesViewModel.currentIndex = imageIndex
            val i = Intent(this, ListCategoryItems::class.java).apply {
                putExtra("result", categoriesViewModel.currLocation)
            }
            startActivity(i)
        }

        //set an onClickListener to each of the ImageViews,
        //a click will trigger the setting of imageIndex and the passIntent() function
        categoryImage1.setOnClickListener {
            imageIndex = 0
            passIntent()
        }

        categoryImage2.setOnClickListener {
            imageIndex = 1
            passIntent()
        }

        categoryImage3.setOnClickListener {
            imageIndex = 2
            passIntent()
        }

        categoryImage4.setOnClickListener {
            imageIndex = 3
            passIntent()
        }




//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = MainActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        binding.signInButon.setOnClickListener {
//            startActivity(Intent(this, SignInActivity::class.java))
//        }
    }


}