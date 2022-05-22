package com.example.assetmonitoring.ui.council

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import com.example.assetmonitoring.ui.main.ReportedCases

//https://www.youtube.com/watch?v=dB9JOsVx-yY&list=PLQ9S01mirRdVvez1P38ksV8kGI8oSsMpL&index=2
//SL: setup the ReclyerView Adapter to display a list of items selected by the worker
class ReportedItemListAdapter(var reportedItemList: List<ReportedCases>):
    RecyclerView.Adapter<ReportedItemListAdapter.ReportedListViewHolder>() {


    var mlistener: onItemClickListener? = null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportedListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.display_report_item, parent, false)
        return ReportedListViewHolder(itemView, mlistener)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportedListViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.outstanding_item, parent, false)
//        return ReportedListViewHolder(itemView, mlistener)
//    }



    override fun onBindViewHolder(holder: ReportedListViewHolder, position: Int) {
        val currentItemData: ReportedCases = reportedItemList[position]
        holder.caseIDTV.text = currentItemData.caseID
//        holder.casePhotoIV.setImageResource(reportedItemList[position].photoURL)
        holder.caseCategoryTV.text = currentItemData.category
        holder.workerNameTV.text = currentItemData.workerName
        holder.caseItemTV.text = currentItemData.item
        holder.caseStatusTV.text = currentItemData.status
        holder.caseLocationTV.text = currentItemData.location
        holder.caseUpdatedTV.text = currentItemData.lastUpdated
        holder.caseExpectedTimeToResolveTV.text = currentItemData.expectedTimeToResolve
    }

//    override fun onBindViewHolder(holder: ReportedListViewHolder, position: Int) {
//        val currentItemData: Cases = reportedItemList[position]
//        holder.caseIDTV.text = reportedItemList[position].caseID.toString()
//        holder.casePhotoIV.setImageResource(reportedItemList[position].photoURL)
//        holder.caseCategoryTV.text = reportedItemList[position].category
//        holder.caseItemTV.text = reportedItemList[position].item
//        //holder.caseStatusTV.text = outstandingItemList[position].status
//        //holder.caseLocationTV.text = outstandingItemList[position].location
//        holder.caseLastUpdatedTV.text = reportedItemList[position].lastUpdated.toString()
//    }

    override fun getItemCount(): Int {
        return reportedItemList.size
    }

    class ReportedListViewHolder(itemView: View, listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView){
        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
//        val casePhotoIV: ImageView = itemView.findViewById(R.id.casePhoto_IV)
        val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
        val workerNameTV: TextView = itemView.findViewById(R.id.workerName_TV)
        val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
        val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
        val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
        val caseUpdatedTV: TextView = itemView.findViewById(R.id.caseUpdateDate_TV)
        val caseExpectedTimeToResolveTV: TextView = itemView.findViewById(R.id.caseExpectedTimeToResolve_TV)

        init {
            itemView.setOnClickListener{
                listener?.onItemClick(adapterPosition)
            }
        }

    }

//    class ReportedListViewHolder(itemView: View, listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView){
//        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
//        val casePhotoIV: ImageView = itemView.findViewById(R.id.casePhoto_IV)
//        val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
//        val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
//        //val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
//        //val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
//        val caseLastUpdatedTV: TextView = itemView.findViewById(R.id.caseLastUpdatedOn_TV)
//
//        init {
//            itemView.setOnClickListener{
//                listener?.onItemClick(adapterPosition)
//            }
//        }
//
//
//    }


}