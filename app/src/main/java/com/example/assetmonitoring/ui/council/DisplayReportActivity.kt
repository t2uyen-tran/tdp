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
import com.example.assetmonitoring.ui.main.ReportedCases
import com.google.firebase.database.*

class DisplayReportActivity : AppCompatActivity() {

    //https://www.youtube.com/watch?v=EMM_3Wld2jU&list=PLQ9S01mirRdVX6qb0uDYUZUuZg1rgL1IV&index=2
    var database: DatabaseReference? = null
    val reportedItemList = ArrayList<ReportedCases>()
    var firebaseDatabaseURL = "https://asset-monitoring-6b16b-default-rtdb.firebaseio.com/"

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

        //SL: get the list of reported items from casesViewModel to be displayed on the RecyclerView
        var caseIndex = 0
        var categoryText = ""
        var statusText = ""
        var caseID = ""
        var itemText = ""
        var locationText = ""
        var expectedTimeToResolveText = "1 to 2 days"
        var workNameText = "David Walker"


        //https://www.youtube.com/watch?v=EMM_3Wld2jU&list=PLQ9S01mirRdVX6qb0uDYUZUuZg1rgL1IV&index=2
        //read all children or records from Table "Cases" in FireBase
        database = FirebaseDatabase.getInstance(firebaseDatabaseURL).getReference("cases")
        database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (caseSnapshot in snapshot.children) {
                        statusText = caseSnapshot.child("status").value.toString()
                        categoryText = caseSnapshot.child("category").value.toString()
//                        Toast.makeText(this@DisplayReportActivity, "$statusText $categoryText", Toast.LENGTH_SHORT).show()

                        //only accept the record if it belongs to one of the selected categories and status
                        //https://stackoverflow.com/questions/61512929/kotlin-array-check-values
                        if (selectedCategory.contains(categoryText)) {
                            if (selectedStatus.contains(statusText)) {
//                                Toast.makeText(this@DisplayReportActivity, "$caseIndex and $categoryText", Toast.LENGTH_SHORT).show()
                                caseID = "C0000$caseIndex"
                                itemText = caseSnapshot.child("item").value.toString()
                                locationText = caseSnapshot.child("location").value.toString()
                                val case = ReportedCases(caseID,
                                                 categoryText,
                                                 itemText,
                                                 statusText,
                                                 locationText,
                                                 dateText,
                                                 expectedTimeToResolveText,
                                                 workNameText)
                                reportedItemList.add(case)
//                                Toast.makeText(this@DisplayReportActivity, case.toString(), Toast.LENGTH_SHORT).show()
                                caseIndex++
                            }
                        }
                    }
                }
                //SL: display the reported item list on the RecyclerView
                val adapter = ReportedItemListAdapter(reportedItemList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@DisplayReportActivity)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        //SL: click "Back" button to back to previous page
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            //SL: go back to the Sign-In Activity as the previous activity
            startActivity(Intent(this, OutstandingActivity::class.java))
            finish()
        }
    }

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