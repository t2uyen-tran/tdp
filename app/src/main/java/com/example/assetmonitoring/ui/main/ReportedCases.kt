package com.example.assetmonitoring.ui.main
//SL

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReportedCases(
    var caseID: String,
    var category: String,
    var item: String,
    var status: String,
    var location: String,
    var lastUpdated: String,
    var expectedTimeToResolve: String,
    var workerID: String,
    var workerName: String
    ): Parcelable {
     }



