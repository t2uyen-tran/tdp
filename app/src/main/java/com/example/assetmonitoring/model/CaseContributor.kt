package com.example.assetmonitoring.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaseContributor (
    val userID: String,
    val date: String,
    val notify: Boolean,
    val comment: String,
    val photo: String
) : Parcelable {}