package com.example.assetmonitoring.model

/**
 * UT: class database for Staff (council users)
 * Define variables according to google firebase database structure
 * Reference: https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields
 */
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Staff(
    var email: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
        )
    }

    val fullName: String
        get() = "$firstName $lastName"
}


