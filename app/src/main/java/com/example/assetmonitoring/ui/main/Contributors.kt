package com.example.assetmonitoring.ui.main
//SL

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Contributors(
    val contributorID: String,
    val comment: String,
    val date: Date,
    val photo: String,          //URL of the Photo
    val notify: Boolean
) : Parcelable {}