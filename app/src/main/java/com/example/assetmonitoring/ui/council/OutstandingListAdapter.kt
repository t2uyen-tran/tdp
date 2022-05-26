package com.example.assetmonitoring.ui.council

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.assetmonitoring.R
import com.example.assetmonitoring.model.Case
import com.example.assetmonitoring.model.dateTime
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

//https://www.youtube.com/watch?v=dB9JOsVx-yY&list=PLQ9S01mirRdVvez1P38ksV8kGI8oSsMpL&index=2
//SL: setup the ReclyerView Adapter to display a list of outstanding items on the worker landing page once successfully signed in
class OutstandingListAdapter: ListAdapter<Case, OutstandingListAdapter.OutstandingListViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK  = object: DiffUtil.ItemCallback<Case>() {
            override fun areItemsTheSame(oldItem: Case, newItem: Case): Boolean {
                return oldItem.caseID == newItem.caseID
            }

            override fun areContentsTheSame(oldItem: Case, newItem: Case): Boolean {
                return oldItem.caseID == newItem.caseID && oldItem.status == newItem.status
            }
        }
    }

    //private var outstandingItemList: List<Case> = emptyList()

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: Case)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutstandingListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.outstanding_item, parent, false)
        return OutstandingListViewHolder(itemView,  mListener)
    }

    override fun onBindViewHolder(holder: OutstandingListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)

    }

    class OutstandingListViewHolder(itemView: View, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView){
        private val caseIDTV: TextView = itemView.findViewById(R.id.caseID_TV)
        private val casePhotoIV: ImageView = itemView.findViewById(R.id.casePhoto_IV)
        private val caseCategoryTV: TextView = itemView.findViewById(R.id.caseCategory_TV)
        private val caseItemTV: TextView = itemView.findViewById(R.id.caseItem_TV)
        private val caseStatusTV: TextView = itemView.findViewById(R.id.caseStatus_TV)
        private val caseLocationTV: TextView = itemView.findViewById(R.id.caseLocation_TV)
        private val caseLastUpdatedTV: TextView = itemView.findViewById(R.id.caseLastUpdatedOn_TV)

        fun binding(item: Case) {
            itemView.setOnClickListener{
                listener?.onItemClick(item)
            }

            caseIDTV.text = item.caseID

            caseCategoryTV.text = item.category
            caseItemTV.text = item.item

            item.contributors.first().let { caseContributor ->
                val gsReference = Firebase.storage.getReferenceFromUrl(
                    caseContributor.photo!!
                )
                gsReference.downloadUrl.addOnSuccessListener {
                    casePhotoIV.load(it) {
                        crossfade(true)
                    }
                }

                val df = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US)
                caseLastUpdatedTV.text = df.format(caseContributor.dateTime)

            }

            caseStatusTV.text = item.status
            caseLocationTV.text = item.location
        }

    }


}