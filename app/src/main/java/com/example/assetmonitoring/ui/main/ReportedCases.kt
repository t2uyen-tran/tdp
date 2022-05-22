package com.example.assetmonitoring.ui.main
//SL

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReportedCases(
    val caseID: String,
    val category: String,
    val item: String,
    var status: String,
    val location: String,
    val lastUpdated: String,
    val expectedTimeToResolve: String,
    val workerName: String
//    val contributors: Contributors? = null

) : Parcelable {}



