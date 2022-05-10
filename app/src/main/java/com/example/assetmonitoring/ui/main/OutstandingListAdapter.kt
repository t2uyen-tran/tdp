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

//SL: setup the ReclyerView Adapter to display a list of outstanding items on the worker landing page once successfully signed in
class OutstandingListAdapter(var outstandingItemList: List<Cases>):
    RecyclerView.Adapter<OutstandingListAdapter.OutstandingListViewHolder>() {

    class OutstandingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
        val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
        val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
        val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
        val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
        val caseLastUpdatedTV: TextView = itemView.findViewById(R.id.caseLastUpdatedOn_TV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutstandingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.outstanding_item, parent, false)
        return OutstandingListViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: OutstandingListViewHolder, position: Int) {
        val outstandingListData: Cases = outstandingItemList[position]
        holder.caseIDTV.text = outstandingItemList[position].caseID.toString()
        holder.caseCategoryTV.text = outstandingItemList[position].category
        holder.caseItemTV.text = outstandingItemList[position].item
        holder.caseStatusTV.text = outstandingItemList[position].status
        holder.caseLocationTV.text = outstandingItemList[position].location
        holder.caseLastUpdatedTV.text = outstandingItemList[position].lastUpdated.toString()


        //SL: save the data of the curr item to shared preferences which is accessible across activities
        fun savedData() {
            val activity = holder.itemView.context as AppCompatActivity
            val sharedPreferences =
                activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putInt("CaseID_KEY", outstandingListData.caseID)
                putString("CaseCategory_KEY", outstandingListData.category)
                putString("CaseItem_KEY", outstandingListData.item)
                putString("CaseStatus_KEY", outstandingListData.status)
                putString("CaseLocation_KEY", outstandingListData.location)
                putString("CaseLastUpdated_KEY", outstandingListData.lastUpdated.toString())
            }.apply()
        }

        //set an onTouchListener on Outstanding Item
    }

    override fun getItemCount(): Int {
        return outstandingItemList.size
    }
}