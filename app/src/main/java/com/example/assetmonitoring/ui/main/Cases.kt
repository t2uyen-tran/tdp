package com.example.assetmonitoring.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Cases(
    val caseID: Int,
    val category: String,
    val item: String,
    val lastUpdated: Date,
    val location: String,
    val photoURL: String,
    val desc: String,
    val status: String,
    val expectedTimeToResolve: String,
    val reportCounter: Int,
    val reasonForOver5Days: String,
    val councilWorkerID: String,
    val userID: String,
    val NotifyUpdate: Boolean,
    val userEmail: String
) : Parcelable {}