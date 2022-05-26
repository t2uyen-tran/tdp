package com.example.assetmonitoring.ui.council

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.assetmonitoring.R
import com.example.assetmonitoring.databinding.UpdateIssueActivityBinding
import com.example.assetmonitoring.model.Case
import com.example.assetmonitoring.model.StaffJob
import com.example.assetmonitoring.model.dateTime
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*


class UpdateIssueActivity : AppCompatActivity() {
    private lateinit var binding: UpdateIssueActivityBinding
    private lateinit var database: DatabaseReference


    private var staffJob: StaffJob? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_issue_activity)

        binding = UpdateIssueActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        val intent = intent
        val case = intent.getParcelableExtra<Case>("Case") ?: return

        database.child("jobs").get().addOnSuccessListener {
            for (child in it.children) {
                if (child.child("case").value == case.caseID) {
                    staffJob = StaffJob(
                        jobID = child.key!!,
                        caseID = child.child("case").getValue<String>()!!,
                        staffID = child.child("sid").getValue<String>()!!,
                        estimatedTime = child.child("estimatedTime").getValue<String>()!!,
                        comment = child.child("comment").getValue<String>() ?: ""
                    )
                }
            }

            staffJob?.let { job ->
                binding.submitUpdateButton.isEnabled = true
                binding.reasonTV.setText(job.comment)

                when(job.estimatedTime) {
                    "2 to 5 days" -> binding.radioButton2Resolve.isChecked = true
                    "Over 5 days" -> binding.radioButton3Resolve.isChecked = true
                    else -> binding.radioButton1Resolve.isChecked = true
                }
            }
        }

        binding.addressTV.text = case.location

        with(binding.caseItem) {
            case.contributors.first().let { caseContributor ->
                val gsReference = Firebase.storage.getReferenceFromUrl(
                    caseContributor.photo!!
                )
                gsReference.downloadUrl.addOnSuccessListener {
                    casePhotoIV.load(it) {
                        crossfade(true)
                    }
                }

                val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
                caseLastUpdatedOnTV.text = df.format(caseContributor.dateTime)
                binding.descriptionTV.text = caseContributor.comment
            }
            caseCategoryTV.text = case.category
            caseIDTV.text = case.caseID
            caseItemTV.text = case.item
            caseLocationTV.text = case.location
            caseStatusTV.text = case.status
        }

        when(case.status) {
            "Wait for third party" -> binding.radioButton2Status.isChecked = true
            else -> binding.radioButton1Status.isChecked = true
        }

        //UT
        binding.submitUpdateButton.setOnClickListener {
            val status = when {
                binding.radioButton2Status.isChecked -> "Wait for third party"
                binding.radioButton3Status.isChecked -> "Resolved"
                else -> "In Progress"
            }
            database.child("cases").child(case.caseID).child("status").setValue(status)
            staffJob?.let { job ->
                database.child("jobs").child(job.jobID).apply {
                    child("comment").setValue(binding.reasonTV.text.toString())
                    val expectedResolve = when {
                        binding.radioButton1Resolve.isChecked -> "1 to 2 days"
                        binding.radioButton2Resolve.isChecked -> "2 to 5 days"
                        else -> "Over 5 days"
                    }
                    child("estimatedTime").setValue(expectedResolve)
                    child("sid").setValue(Firebase.auth.currentUser?.uid)
                }
            }
            finish()
        }

        //UT: click "Back" button to back to previous page
        binding.backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }

        //UT: click "Back" button to back to previous page
        binding.goToReportButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, SelectReportActivity::class.java))
            finish()
        }
    }




}