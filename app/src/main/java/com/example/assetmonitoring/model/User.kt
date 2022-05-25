package com.example.assetmonitoring.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val userID: String,
    val email: String,    // The date the contribution was made
    val mobile:  String,    // Whether or not the user wishes to be notified of updates
    val userName: String    // Text comment/description of issue
) : Parcelable
