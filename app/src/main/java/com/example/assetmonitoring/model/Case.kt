package com.example.assetmonitoring.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Case (
    val caseID: String,                         // The ID for this case **THIS IS THE KEY**
    val category: String,                       // The category this case belongs to
    val item: String,                           // The type of item the issue relates to
    val location: String,                       // The location this case refers to
    val contributors: List<CaseContributor> = emptyList(),    // A list of contributors for this case
    val status: String,                          // The current status of a test
) : Parcelable