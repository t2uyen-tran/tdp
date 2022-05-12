package com.example.assetmonitoring.ui.main
//SL

import androidx.lifecycle.ViewModel
import com.example.assetmonitoring.R
import com.example.assetmonitoring.model.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor() : ViewModel() {

    var currentIndex = 0

    val footpathItems = mutableListOf("Potholes",
        "1. Rough surfaces",
        "2. Dislodged or loose bricks and pavers",
        "3. Lifted or broken slabs",
        "4. Weeds growing through the footpath",
        "5. Raised wooden edging",
        "6. Other")

    val roadItems = mutableListOf("Line marking",
        "1. Falling tree",
        "2. Oil on road",
        "3. Pothole and edge-break repairs",
        "4. Glass or debris on the road",
        "5. Damaged or missing signs",
        "6. Other")

    val binItems = mutableListOf("Line marking",
        "1. Falling tree",
        "2. Oil on road",
        "Pothole and edge-break repairs",
        "Glass or debris on the road",
        "Damaged or missing signs",
        "Other")

    val busstopItems = mutableListOf("Line marking",
        "Falling tree",
        "Oil on road",
        "Pothole and edge-break repairs",
        "Glass or debris on the road",
        "Damaged or missing signs",
        "Other")

    private val categoryBank = listOf(
        Categories(R.drawable.roadimage, R.string.photo1Name, R.string.photo1ManagerID, 1, roadItems),
        Categories(R.drawable.footpathimage, R.string.photo2Name, R.string.photo2ManagerID, 2, footpathItems),
        Categories(R.drawable.binimage, R.string.photo3Name, R.string.photo3ManagerID, 3, binItems),
        Categories(R.drawable.busstopimage, R.string.photo4Name, R.string.photo4ManagerID, 4, busstopItems)
    )



    val currLocation: Categories
        get() = categoryBank[currentIndex]


}