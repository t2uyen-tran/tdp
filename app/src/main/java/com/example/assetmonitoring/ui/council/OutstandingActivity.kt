/**
 * UI: Sam
 * Business logic: Uyen
 * List the outstanding cases including: assigned pending cases and new cases
 */
package com.example.assetmonitoring.ui.council

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.model.Case
import com.example.assetmonitoring.model.CaseContributor
import com.example.assetmonitoring.model.Staff
import com.example.assetmonitoring.ui.LauncherActivity
import com.example.assetmonitoring.ui.main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OutstandingActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    private val adapter = OutstandingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outstanding_activity)

        database = Firebase.database.reference

        val recyclerView = findViewById<RecyclerView>(R.id.outstandingItems_RV)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(object: OutstandingListAdapter.OnItemClickListener {
            override fun onItemClick(item: Case) {
                val intent = Intent(this@OutstandingActivity,UpdateIssueActivity::class.java ).apply{
                    putExtra("Case", item)
                }
                startActivity(intent)
            }
        })

        // UT:Logout for council worker
        findViewById<Button>(R.id.logout_buton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LauncherActivity::class.java))
        }

        //SL: click "Generate Report" button to go to the "SelectReportActivity" page
        val generateReportButton: Button = findViewById(R.id.generateReport_button)
        generateReportButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, SelectReportActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        database.child("cases").get().addOnSuccessListener { dataSnapshot ->
            val contributors = mutableListOf<CaseContributor>()
            val cases = mutableListOf<Case>()
            for (child in dataSnapshot.children) {
                val contributorsSnapshot = child.child("contributors")
                for (contributorChild in contributorsSnapshot.children) {
                    contributors.add(
                        CaseContributor(
                            userID = contributorChild.key!!,
                            notify = contributorChild.child("notify").getValue<Boolean>()!!,
                            date = Date(contributorChild.child("date").getValue<String>()?.toLong()!!),
                            comment = contributorChild.child("comment").getValue<String>()!!,
                            photo = contributorChild.child("photo").getValue<String>()!!
                        )
                    )
                }
                cases.add(Case(
                    caseID = child.key!!,
                    category = child.child("category").getValue<String>()!!,
                    item = child.child("item").getValue<String>()!!,
                    location = child.child("location").getValue<String>()!!,
                    status = child.child("status").getValue<String>()!!,
                    contributors = contributors
                ))
            }
            adapter.submitList(cases.filter { it.status != "Resolved" })
        }
        database.child("staff").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            val staff = it.getValue<Staff>()
            if (staff != null) {
                findViewById<TextView>(R.id.greetingWithWorkerName_TV).text = "Hello " + staff.fullName
            }
        }
    }
}