package com.example.assetmonitoring.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.assetmonitoring.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DataTestActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_test)

        database = Firebase.database.reference

        val displayCases: Boolean = true
        val displayJobs: Boolean = false
        var result = "ERROR"

        // DISPLAY CASES
        if (displayCases) {
            database.child("cases").get().addOnSuccessListener {
                result = "DISPLAYING CASES:\n\n"

                for (case in it.children) {
                    val id: String =
                        if (case.key != null) case.key.toString() else "ERROR"
                    val category: String =
                        if (case.child("category") != null) case.child("category").value.toString() else "ERROR"
                    val item: String =
                        if (case.child("item") != null) case.child("item").value.toString() else "ERROR"
                    val location: String =
                        if (case.child("location") != null) case.child("location").value.toString() else "ERROR"
                    val status: String =
                        if (case.child("status") != null) case.child("status").value.toString() else "ERROR"

                    result += "CASE ID: $id\n" +
                            "CATEGORY: $category\n" +
                            "ITEM: $item\n" +
                            "LOCATION: $location\n" +
                            "CONTRIBUTORS:\n"

                    for (contributor in case.child("contributors").children) {
                        val uid: String =
                            if (contributor.key != null) contributor.key.toString() else "ERROR"
                        val date: String =
                            if (contributor.child("date") != null) contributor.child("date").value.toString() else "ERROR"
                        val photo: String =
                            if (contributor.child("photo") != null) contributor.child("photo").value.toString() else "ERROR"
                        val comment: String =
                            if (contributor.child("comment") != null) contributor.child("comment").value.toString() else "ERROR"
                        val notify: String =
                            if (contributor.child("notify") != null) contributor.child("notify").value.toString() else "ERROR"

                        result += "    USER ID: $uid \n" +
                                "    DATE: $date \n" +
                                "    PHOTO: $photo \n" +
                                "    COMMENT: $comment \n" +
                                "    NOTIFY: $notify \n" +
                                "    ----------\n"
                    }

                    result += "STATUS: $status \n" +
                            "----------\n"

                    findViewById<TextView>(R.id.textView).text = result
                }
            }.addOnFailureListener {
                result = "ERROR GETTING DATABASE"

                findViewById<TextView>(R.id.textView).text = result
            }
        }

        // DISPLAY JOBS
        if (displayJobs) {
            database.child("jobs").get().addOnSuccessListener {
                result = "DISPLAYING JOBS:\n\n"

                for (job in it.children) {
                    val jobID: String = if (job.key != null) job.key.toString() else "ERROR"
                    val caseID: String = if (job.child("case") != null) job.child("case").value.toString() else "ERROR"
                    val staffID: String = if (job.child("sid") != null) job.child("sid").value.toString() else "ERROR"
                    val estimatedTime: String = if (job.child("estimatedTime") != null) job.child("estimatedTime").value.toString() else "ERROR"
                    val comment: String = if (job.child("comment") != null) job.child("comment").value.toString() else "ERROR"

                    result += "JOB ID: $jobID \n" +
                            "CASE ID: $caseID \n" +
                            "ASSIGNED STAFF ID: $staffID \n" +
                            "ESTIMATED COMPLETION: $estimatedTime \n" +
                            "REASON FOR OVER 5 DAYS: $comment \n" +
                            "----------\n"
                }

                findViewById<TextView>(R.id.textView).text = result
            }.addOnFailureListener {
                result = "ERROR GETTING DATABASE"

                findViewById<TextView>(R.id.textView).text = result
            }
        }

        findViewById<TextView>(R.id.textView).text = result
    }
}