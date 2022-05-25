package com.example.assetmonitoring.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class CaseContributor (
    val userID: String,     // The ID of the user making the contribution
    val date: Long,    // The date the contribution was made
    val notify: Boolean,    // Whether or not the user wishes to be notified of updates
    val comment: String,    // Text comment/description of issue
    val photo: String
) : Parcelable