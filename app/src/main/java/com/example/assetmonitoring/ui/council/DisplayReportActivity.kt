package com.example.assetmonitoring.ui.council

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.ui.LauncherActivity
import com.example.assetmonitoring.ui.main.ReportedCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DisplayReportActivity : AppCompatActivity() {
    //SL: this activity is to display a list of cases based on the parameters selected by the council worker from the "SelectReportActivity"

    var reportedItemList = ArrayList<ReportedCases>()
    var categoryText = ""
    var statusText = ""
    var caseIDText = ""
    var itemText = ""
    var locationText = ""
    var expectedTimeToResolveText = "Expected to resolve in "
    var workNameText = ""
    var staffIDText = ""

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_report_activity)

        var intent: Intent = getIntent()
        var selectedStatus = ArrayList<String>()
        var selectedCategory = ArrayList<String>()
        var startDateMth = ""
        var startDateDay = ""
        var startDateYear = ""

        selectedStatus = (intent?.getStringArrayListExtra("SelectedStatus") as ArrayList<String>?)!!
        selectedCategory =
            (intent?.getStringArrayListExtra("SelectedCategory") as ArrayList<String>?)!!
        startDateMth = intent?.getStringExtra("StartDateMth").toString()
        startDateMth = convertToMonth(startDateMth)
        startDateDay = intent?.getStringExtra("StartDateDay").toString()
        startDateYear = intent?.getStringExtra("StartDateYear").toString()

        val dateText = "$startDateDay $startDateMth $startDateYear"


        val startDateTV = findViewById<TextView>(R.id.startDate_TV)
        val statusTV = findViewById<TextView>(R.id.selectedStatus_TV)
        val categoryTV = findViewById<TextView>(R.id.selectedCategory_TV)


        startDateTV.setText(dateText)
        statusTV.setText(unpackArrayList(selectedStatus))
        categoryTV.setText(unpackArrayList(selectedCategory))

        val recyclerView = findViewById<RecyclerView>(R.id.reportedItems_RV)



        //read all children or records from Node "cases" in FireBase
        database = Firebase.database.reference
        database.child("cases").get().addOnSuccessListener {
            for (case in it.children) {
                caseIDText =
                    if (case.key != null) case.key.toString() else "ERROR"
                categoryText =
                    if (case.child("category") != null) case.child("category").value.toString() else "ERROR"
                itemText =
                    if (case.child("item") != null) case.child("item").value.toString() else "ERROR"
                locationText =
                    if (case.child("location") != null) case.child("location").value.toString() else "ERROR"
                statusText =
                    if (case.child("status") != null) case.child("status").value.toString() else "ERROR"

                //keep those match the selected "category" and "status", and store them in a list "reportedItemList"
                if ((selectedCategory.contains(categoryText)) and (selectedStatus.contains(
                        statusText)))  {
                    val reportedCase = ReportedCases(caseIDText,
                        categoryText,
                        itemText,
                        statusText,
                        locationText,
                        dateText,
                        expectedTimeToResolveText,
                        staffIDText,
                        workNameText)
                    reportedItemList!!.add(reportedCase)
                }
            }
        }

        //read all children or records from Node "jobs" in FireBase
        database.child("jobs").get().addOnSuccessListener {

            for (job in it.children) {
                val jobID: String = if (job.key != null) job.key.toString() else "ERROR"
                val jobCaseID: String =
                    if (job.child("case") != null) job.child("case").value.toString() else "ERROR"
                val jobStaffID: String =
                    if (job.child("sid") != null) job.child("sid").value.toString() else "ERROR"
                val estimatedTime: String =
                    if (job.child("estimatedTime") != null) job.child("estimatedTime").value.toString() else "ERROR"
                val comment: String =
                    if (job.child("comment") != null) job.child("comment").value.toString() else "ERROR"

                //compare each job to the list of the cases in "reportedItemList" by caseID/jobCaseID,
                //and update the "estimatedTime" and "workerID" for the case when there is a match
                for (case in reportedItemList) {
                    if (case.caseID == jobCaseID) {
                        case.expectedTimeToResolve = "$expectedTimeToResolveText $estimatedTime"
                        case.workerID = jobStaffID
                    }
                }
            }
        }

        //read all children or records from Node "staff" in FireBase
        database.child("staff").get().addOnSuccessListener {

            for (staff in it.children) {
                val jobStaffID: String = if (staff.key != null) staff.key.toString() else "ERROR"
                val jobStaffFirstName: String =
                    if (staff.child("firstName") != null) staff.child("firstName").value.toString() else "ERROR"
                val jobStaffLastName: String =
                    if (staff.child("lastName") != null) staff.child("lastName").value.toString() else "ERROR"

                //compare each staff to the list of the cases in "reportedItemList" by wrokerID/jobStaffID,
                //and update the "workerName" for the case when there is a match
                for (case in reportedItemList) {
                    if (case.workerID == jobStaffID) {
                        case.workerName = "$jobStaffFirstName $jobStaffLastName"
                    }
                }

                //SL: display the reported item list on the RecyclerView
                val adapter = ReportedItemListAdapter(reportedItemList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@DisplayReportActivity)
            }
        }

        //SL: click "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }

        // UT:Logout for council worker
        findViewById<Button>(R.id.logout_buton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LauncherActivity::class.java))
        }

    }

    //To unpack the String ArrayList in the Intent passed from the previous activity "SelectReportActivity"
    fun unpackArrayList(arr: ArrayList<String>): String {
        var str = ""
        var index = 0
        while (index < arr.size) {
            str += arr[index]
            if(index < (arr.size-1)){
                str += "/"
            }
            index++
        }
        return str
    }

    fun convertToMonth(selectedMth: String): String {
        var mth = selectedMth.toInt()
        var mthStr = ""
        when(mth){
            1 -> mthStr = "Jan"
            2 -> mthStr = "Feb"
            3 -> mthStr = "Mar"
            4 -> mthStr = "Apr"
            5 -> mthStr = "May"
            6 -> mthStr = "Jun"
            7 -> mthStr = "Jul"
            8 -> mthStr = "Aug"
            9 -> mthStr = "Sep"
            10 -> mthStr = "Oct"
            11 -> mthStr = "Nov"
            12 -> mthStr = "Dec"
        }
        return mthStr
    }

}