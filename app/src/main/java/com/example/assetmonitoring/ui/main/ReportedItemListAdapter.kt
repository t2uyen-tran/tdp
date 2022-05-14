package com.example.assetmonitoring.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R

//https://www.youtube.com/watch?v=dB9JOsVx-yY&list=PLQ9S01mirRdVvez1P38ksV8kGI8oSsMpL&index=2
//SL: setup the ReclyerView Adapter to display a list of items selected by the worker
class ReportedItemListAdapter(var reportedItemList: List<Cases>):
    RecyclerView.Adapter<ReportedItemListAdapter.ReportedListViewHolder>() {


//    lateinit var mlistener: onItemClickListener
//
//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: onItemClickListener){
//        mlistener = listener
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportedListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.display_report_item, parent, false)
        return ReportedListViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ReportedListViewHolder, position: Int) {
        val currentItemData: Cases = reportedItemList[position]
        holder.caseIDTV.text = reportedItemList[position].caseID.toString()
//        holder.casePhotoIV.setImageResource(reportedItemList[position].photoURL)
        holder.caseCategoryTV.text = reportedItemList[position].category
        holder.workerNameTV.text = reportedItemList[position].councilWorkerID
        holder.caseItemTV.text = reportedItemList[position].item
        holder.caseStatusTV.text = reportedItemList[position].status
        holder.caseLocationTV.text = reportedItemList[position].location
        holder.caseLastUpdatedTV.text = reportedItemList[position].lastUpdated.toString()
    }

    override fun getItemCount(): Int {
        return reportedItemList.size
    }

    class ReportedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
//        val casePhotoIV: ImageView = itemView.findViewById(R.id.casePhoto_IV)
        val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
        val workerNameTV: TextView = itemView.findViewById(R.id.workerName_TV)
        val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
        val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
        val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
        val caseLastUpdatedTV: TextView = itemView.findViewById(R.id.caseLastUpdatedOn_TV)



    }


}