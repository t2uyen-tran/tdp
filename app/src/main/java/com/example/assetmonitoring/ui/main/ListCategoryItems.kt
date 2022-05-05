package com.example.assetmonitoring.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.assetmonitoring.R

private const val TAG = "ListCategoryItems"

class ListCategoryItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onStart()called")
        setContentView(R.layout.list_category_items)

        val categoryNameTV = findViewById<TextView>(R.id.categoryListTitle_textV)

        //received the details of the selected category which is a parcelable object included in the intent
        val result = intent.getParcelableExtra<Categories>("result")
        val categoryName = result?.name
        val categoryItems: MutableList<String>
        categoryItems = result?.itemList!!

        categoryNameTV.text = categoryName?.let { getString(it) }

        val listView = findViewById<ListView>(R.id.footpathitems_listV)
        val uploadOrTakePhoto = findViewById<TextView>(R.id.uploadOrTakePhoto_textV)

        //create the list of items for selection
        //https://www.youtube.com/watch?v=rxRuW2qZ2ZY


        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            categoryItems
        )

        listView.adapter = arrayAdapter

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE



        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "Item Selected: " + categoryItems[i], Toast.LENGTH_SHORT )
                .show()
        }



    }

}