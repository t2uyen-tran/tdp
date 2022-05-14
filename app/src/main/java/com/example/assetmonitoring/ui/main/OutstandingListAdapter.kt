package com.example.assetmonitoring.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmonitoring.R
import java.security.cert.PolicyNode

//https://www.youtube.com/watch?v=dB9JOsVx-yY&list=PLQ9S01mirRdVvez1P38ksV8kGI8oSsMpL&index=2
//SL: setup the ReclyerView Adapter to display a list of outstanding items on the worker landing page once successfully signed in
class OutstandingListAdapter(var outstandingItemList: List<Cases>):
    RecyclerView.Adapter<OutstandingListAdapter.OutstandingListViewHolder>() {


    lateinit var mlistener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutstandingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.outstanding_item, parent, false)
        return OutstandingListViewHolder(itemView, mlistener)
    }



    override fun onBindViewHolder(holder: OutstandingListViewHolder, position: Int) {
        val currentItemData: Cases = outstandingItemList[position]
        holder.caseIDTV.text = outstandingItemList[position].caseID.toString()
        holder.casePhotoIV.setImageResource(outstandingItemList[position].photoURL)
        holder.caseCategoryTV.text = outstandingItemList[position].category
        holder.caseItemTV.text = outstandingItemList[position].item
        //holder.caseStatusTV.text = outstandingItemList[position].status
        //holder.caseLocationTV.text = outstandingItemList[position].location
        holder.caseLastUpdatedTV.text = outstandingItemList[position].lastUpdated.toString()
    }

    override fun getItemCount(): Int {
        return outstandingItemList.size
    }

    class OutstandingListViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
        val casePhotoIV: ImageView = itemView.findViewById(R.id.casePhoto_IV)
        val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
        val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
        //val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
        //val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
        val caseLastUpdatedTV: TextView = itemView.findViewById(R.id.caseLastUpdatedOn_TV)

        init {
                itemView.setOnClickListener{
                    listener.onItemClick(adapterPosition)
            }
        }


    }


}