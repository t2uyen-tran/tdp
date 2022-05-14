package com.example.assetmonitoring.ui.council

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assetmonitoring.R
import com.example.assetmonitoring.databinding.UpdateIssueActivityBinding
import com.example.assetmonitoring.ui.main.ReportedItemListAdapter

class UpdateIssueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_issue_activity)


        //SL: click "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }

        //SL: click "Back" button to back to previous page
        val goToReportButton: Button = findViewById(R.id.goToReport_button)
        goToReportButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, SelectReportActivity::class.java))
            finish()
        }
    }




}