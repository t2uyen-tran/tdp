package com.example.assetmonitoring.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R


class OutstandingListAdapter(var outstandingItemList: List<Cases>):
    RecyclerView.Adapter<OutstandingListAdapter.OutstandingListViewHolder>() {

    class OutstandingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
        val reportedCategoryTV: TextView = itemView.findViewById(R.id.reportedCategory_TV)
        val reportedItemTV: TextView = itemView.findViewById(R.id.reportedItem_TV)
        val reportedLocationTV: TextView = itemView.findViewById(R.id.reportedLocation_TV)
        val lastUpdatedTV: TextView = itemView.findViewById(R.id.lastUpdatedOn_TV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutstandingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.outstanding_item, parent, false)
        return OutstandingListViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: OutstandingListViewHolder, position: Int) {
        val outstandingListData: Cases = outstandingItemList[position]
        holder.caseIDTV.text = outstandingItemList[position].caseID.toString()
        holder.reportedCategoryTV.text = outstandingItemList[position].category
        holder.reportedItemTV.text = outstandingItemList[position].item
        holder.reportedLocationTV.text = outstandingItemList[position].location
        holder.lastUpdatedTV.text = outstandingItemList[position].lastUpdated.toString()


        //save the data of the curr item to shared preferences which is accessible across activities
        fun savedData() {
            val activity = holder.itemView.context as AppCompatActivity
            val sharedPreferences =
                activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putInt("CaseID_KEY", outstandingListData.caseID)
                putString("ReportedCategory_KEY", outstandingListData.category)
                putString("ReportedCategory_KEY", outstandingListData.category)
                putString("ReportedItem_KEY", outstandingListData.item)
                putString("ReportedLocation_KEY", outstandingListData.location)
                putString("LastUpdated_KEY", outstandingListData.lastUpdated.toString())
            }.apply()
        }

        //set an onTouchListener on Outstanding Item
    }

    override fun getItemCount(): Int {
        return outstandingItemList.size
    }
}