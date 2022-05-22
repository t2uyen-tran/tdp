/**
 * Code: Uyen
 * UI: Sam
 */
package com.example.assetmonitoring.ui.council

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.ui.main.*
import com.example.assetmonitoring.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OutstandingActivity : AppCompatActivity() {

    //SL: associate CasesViewModel with the UI controller by creating a reference to the
    //SL: CasesViewModel inside the UI controller
    private val casesNotUsedViewModel: CasesNotUsedViewModel by lazy {
        ViewModelProvider(this).get(CasesNotUsedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outstanding_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.outstandingItems_RV)

        //SL: get the list of outstanding items from casesViewModel to be displayed on the RecyclerView
        var outstandingItemList = mutableListOf<CasesNotUsed>()
        var caseIndex = 0
        var statusText = ""
        //SL: the hardcoded "4" should dynamically change to the total number of cases once Database is ready
        while(caseIndex < 5) {
            casesNotUsedViewModel.currentIndex = caseIndex
            var case: CasesNotUsed = casesNotUsedViewModel.currCase
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



        //SL: click "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
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