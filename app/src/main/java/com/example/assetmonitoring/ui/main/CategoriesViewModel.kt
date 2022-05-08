package com.example.assetmonitoring.ui.main

import androidx.lifecycle.ViewModel
import com.example.assetmonitoring.R
import com.example.assetmonitoring.model.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor() : ViewModel() {

    var currentIndex = 0

    val footpathItems = mutableListOf("Potholes",
        "Rough surfaces",
        "Dislodged or loose bricks and pavers",
        "Lifted or broken slabs",
        "Weeds growing through the footpath",
        "Raised wooden edging",
        "Tripping or falling hazard")

    val roadItems = mutableListOf("Line marking",
        "Falling tree",
        "Oil on road",
        "Pothole and edge-break repairs",
        "Glass or debris on the road",
        "Damaged or missing signs",
        "Dumped rubbish")

    val binItems = mutableListOf("Line marking",
        "Falling tree",
        "Oil on road",
        "Pothole and edge-break repairs",
        "Glass or debris on the road",
        "Damaged or missing signs",
        "Dumped rubbish")

    val busstopItems = mutableListOf("Line marking",
        "Falling tree",
        "Oil on road",
        "Pothole and edge-break repairs",
        "Glass or debris on the road",
        "Damaged or missing signs",
        "Dumped rubbish")

    private val categoryBank = listOf(
        Categories(R.drawable.roadimage, R.string.photo1Name, R.string.photo1ManagerID, 1, roadItems),
        Categories(R.drawable.footpathimage, R.string.photo2Name, R.string.photo2ManagerID, 2, footpathItems),
        Categories(R.drawable.binimage, R.string.photo3Name, R.string.photo3ManagerID, 3, binItems),
        Categories(R.drawable.busstopimage, R.string.photo4Name, R.string.photo4ManagerID, 4, busstopItems)
    )



    val currLocation: Categories
        get() = categoryBank[currentIndex]


}