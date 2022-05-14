package com.example.assetmonitoring.ui.council

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.databinding.DisplayReportActivityBinding
import com.example.assetmonitoring.databinding.OutstandingActivityBinding
import com.example.assetmonitoring.ui.main.Cases
import com.example.assetmonitoring.ui.main.CasesViewModel
import com.example.assetmonitoring.ui.main.OutstandingListAdapter
import com.example.assetmonitoring.ui.main.ReportedItemListAdapter
import com.example.assetmonitoring.ui.signin.SignInActivity

class DisplayReportActivity : AppCompatActivity() {

    //SL: associate CasesViewModel with the UI controller by creating a reference to the
    //SL: CasesViewModel inside the UI controller
    private val casesViewModel: CasesViewModel by lazy {
        ViewModelProvider(this).get(CasesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_report_activity)

        val startDateTV = findViewById<TextView>(R.id.startDate_TV)
        val statusTV = findViewById<TextView>(R.id.caseStatus_TV)
        val categoryTV = findViewById<TextView>(R.id.caseCategory_TV)
        val backBtn = findViewById<Button>(R.id.back_button)

        val recyclerView = findViewById<RecyclerView>(R.id.reportedItems_RV)


        //SL: get the list of reported items from casesViewModel to be displayed on the RecyclerView
        var reportedItemList = mutableListOf<Cases>()
        var caseIndex = 0
        var statusText = ""
        //SL: the hardcoded "4" should dynamically change to the total number of cases once Database is ready
        while(caseIndex < 5) {
            casesViewModel.currentIndex = caseIndex
            var case: Cases = casesViewModel.currCase
            //SL: check status of the case to pick up only those that are not "Resolved"
            statusText = case.status
            if (statusText != "Resolved") {
                reportedItemList.add(case)
            }
            caseIndex++
        }


        //SL: display the reported item list on the RecyclerView
        val adapter = ReportedItemListAdapter(reportedItemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        //SL: click "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }




    }


}