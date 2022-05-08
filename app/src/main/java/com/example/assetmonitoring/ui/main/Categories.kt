package com.example.assetmonitoring.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Categories (
    val image: Int,             // integer id of drawable
    val name: Int,              // integer id of string in String.xml
    val managerID: Int,       // integer id of worker ID for the manager of this category
    val categoryID: Int,         // ID number of this category
    val itemList: MutableList<String>     // A list of items in this category
) : Parcelable {}