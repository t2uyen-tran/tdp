package com.example.assetmonitoring.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class CaseContributor (
    val userID: String,     // The ID of the user making the contribution
    val date: Timestamp,    // The date the contribution was made
    val notify: Boolean,    // Whether or not the user wishes to be notified of updates
    val comment: String,    // Text comment/description of issue
    val photo: String       // Link to image location
) : Parcelable