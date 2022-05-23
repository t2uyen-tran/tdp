package com.example.assetmonitoring.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Case (
    // Public Data
    val caseID: String,
    val category: String,
    val item: String,
    val location: String,
    val contributors: List<CaseContributor>,

    // Council Data
    var status: String,
    val lastUpdated: String,
    val expectedTimeToResolve: String,
    val workerName: String
) : Parcelable {}