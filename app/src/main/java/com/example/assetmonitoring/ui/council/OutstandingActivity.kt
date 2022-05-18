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
import com.example.assetmonitoring.model.Staff
import com.example.assetmonitoring.ui.LauncherActivity
import com.example.assetmonitoring.ui.main.*
import com.example.assetmonitoring.ui.signin.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OutstandingActivity : AppCompatActivity() {

    //SL: associate CasesViewModel with the UI controller by creating a reference to the
    //SL: CasesViewModel inside the UI controller
    private val casesViewModel: CasesViewModel by lazy {
        ViewModelProvider(this).get(CasesViewModel::class.java)
    }

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outstanding_activity)

        database = Firebase.database.reference

        val key = database.child("cases").push().key

        database.child("cases").get().addOnSuccessListener {
          for (child in it.children) {
              //Timber.i()
          }
        }
        database.child("staff").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            val staff = it.getValue<Staff>()
            if (staff != null) {
                findViewById<TextView>(R.id.greetingWithWorkerName_TV).text = "Hello " + staff.fullName
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.outstandingItems_RV)

        //SL: get the list of outstanding items from casesViewModel to be displayed on the RecyclerView
        var outstandingItemList = mutableListOf<Cases>()
        var caseIndex = 0
        var statusText = ""
        //SL: the hardcoded "4" should dynamically change to the total number of cases once Database is ready
        while(caseIndex < 5) {
            casesViewModel.currentIndex = caseIndex
            var case: Cases = casesViewModel.currCase
            //SL: check status of the case to pick up only those that are not "Resolved"
            statusText = case.status
            if (statusText != "Resolved") {
                outstandingItemList.add(case)
            }
                caseIndex++
        }

        //SL: display the outstanding item list on the RecyclerView
        //SL: onclickListener to UpdateIssueActivity
        //https://www.youtube.com/watch?v=EoJX7h7lGxM&list=PLQ9S01mirRdVvez1P38ksV8kGI8oSsMpL&index=3
        val adapter = OutstandingListAdapter(outstandingItemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(object: OutstandingListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@OutstandingActivity,UpdateIssueActivity::class.java ).apply{
                    putExtra("CaseID", outstandingItemList[position].caseID)
                    putExtra("CaseCategory", outstandingItemList[position].category)
                    putExtra("CaseItem", outstandingItemList[position].item)
                    putExtra("CaseStatus", outstandingItemList[position].status)
                    putExtra("CaseLocation", outstandingItemList[position].location)
                    putExtra("CaseLastUpdated", outstandingItemList[position].lastUpdated)
                    putExtra("CasePhotoURL", outstandingItemList[position].photoURL)
                    putExtra("CaseDescription", outstandingItemList[position].desc)
                }
                startActivity(intent)
            }
        })


        // UT: Comment out the Back button - no need to go back after successfully login
        // SL: click "Back" button to back to previous page
//        val backButton: Button = findViewById(R.id.back_button)
//        backButton.setOnClickListener {
//            //SL: go back to the Sign-In Activity as the previous activity
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//        }

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


}