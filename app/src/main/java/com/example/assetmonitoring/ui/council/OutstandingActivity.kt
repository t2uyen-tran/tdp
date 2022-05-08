package com.example.assetmonitoring.ui.council

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.ui.main.Cases
import com.example.assetmonitoring.ui.main.CasesViewModel
import com.example.assetmonitoring.ui.main.OutstandingListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OutstandingActivity : AppCompatActivity() {

    ///associate CasesViewModel with the UI controller by creating a reference to the
    //CasesViewModel inside the UI controller
    private val casesViewModel: CasesViewModel by lazy {
        ViewModelProvider(this).get(CasesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outstanding_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.outstandingItems_RV)

        //get the list of outstanding items to be displayed on the RecyclerView
        var outstandingItemList = mutableListOf<Cases>()
        var caseIndex = 0
        while(caseIndex < 3) {
            casesViewModel.currentIndex = caseIndex
            outstandingItemList.add(casesViewModel.currCase)
            caseIndex++
        }

        //display the outstanding item list on the RecyclerView
        val adapter = OutstandingListAdapter(outstandingItemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}