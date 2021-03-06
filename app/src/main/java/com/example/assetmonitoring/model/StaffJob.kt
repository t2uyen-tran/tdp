package com.example.assetmonitoring.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffJob (
    val jobID: String,          // The ID of this job **THIS IS THE KEY**
    val staffID: String,        // The ID of the staff assigned to this job
    val caseID: String,         // The ID of the case for this job
    val estimatedTime: String,  // The estimated time to complete the task
    val comment: String         // Reason for more than 5 days?
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "case" to caseID,
            "comment" to comment,
            "estimatedTime" to estimatedTime,
            "sid" to staffID
        )
    }
}